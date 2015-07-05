package com.benzino.materialdesign.fragments;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonObjectRequest;
import com.benzino.materialdesign.R;
import com.benzino.materialdesign.adapters.AdapterBoxOffice;
import com.benzino.materialdesign.app.MyApp;
import com.benzino.materialdesign.extras.Keys;
import com.benzino.materialdesign.loggings.L;
import com.benzino.materialdesign.network.VolleySingelton;
import com.benzino.materialdesign.pojo.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import static com.benzino.materialdesign.extras.UrlEndPoints.*;
import static com.benzino.materialdesign.extras.Keys.EndPointBoxOffice.* ;

public class FragmentBoxOffice extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private VolleySingelton singelton;
    private ImageLoader imageLoader;
    private RequestQueue requestQueue;
    private AdapterBoxOffice adapterBoxOffice;
    private ArrayList<Movie> listMovies = new ArrayList<>();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private RecyclerView listMovieHits;
    private TextView textVolleyError;

    public static FragmentBoxOffice newInstance(String param1, String param2) {
        FragmentBoxOffice fragment = new FragmentBoxOffice();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }
    public static String getRequestUrl(int limit){
        return  URL_BOX_OFFICE
                + URL_CHAR_QUESTION
                + URL_PARAM_API_KEY + MyApp.API_KEY +"&limit="+limit
                + URL_CHAR_AMPERSAND
                + URL_PARAM_LIMIT + limit;
    }
    public FragmentBoxOffice() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
     }

    private void sendJSONRequest(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                getRequestUrl(10),
                (String)null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        textVolleyError.setVisibility(View.GONE);
                        listMovies =  parseJSONResponse(response);
                        adapterBoxOffice.setListMovies(listMovies);
                    }
                },new Response.ErrorListener(){

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        handleVolleyError(error);
                    }
        });

        requestQueue.add(request);
    }

    private  ArrayList<Movie> parseJSONResponse(JSONObject response) {
        ArrayList<Movie> listMovies = new ArrayList<>();
        if (response != null && response.length() > 0) {

            try {
                JSONArray array = response.getJSONArray(KEY_MOVIES);
                for (int i = 0; i < array.length(); i++) {
                    long id = 0 ;
                    String title = "NA";
                    String theater = "NA";
                    int score = -1;
                    String synopsis = "NA";
                    String urlThumbnail  ="NA";

                    JSONObject movieJSON = array.getJSONObject(i);

                    if(movieJSON.has(KEY_ID) && !movieJSON.isNull(KEY_ID))
                        id = movieJSON.getLong(KEY_ID);

                    if(movieJSON.has(KEY_TITLE) && !movieJSON.isNull(KEY_TITLE))
                        title = movieJSON.getString(KEY_TITLE);

                    if(movieJSON.has(KEY_RELEASE_DATES) && !movieJSON.isNull(KEY_RELEASE_DATES)){
                        JSONObject releaseDates = movieJSON.getJSONObject(KEY_RELEASE_DATES);

                        if(releaseDates !=null && releaseDates.has(KEY_THEATER) && !releaseDates.isNull(KEY_THEATER))
                            theater = releaseDates.getString(KEY_THEATER);
                    }

                    if(movieJSON.has(KEY_SYNOPSIS) && !movieJSON.isNull(KEY_SYNOPSIS))
                        synopsis = movieJSON.getString(KEY_SYNOPSIS);

                    if(movieJSON.has(KEY_RATINGS) && !movieJSON.isNull(KEY_RATINGS)){
                        JSONObject ratings = movieJSON.getJSONObject(KEY_RATINGS);

                        if(ratings !=null && ratings.has(KEY_SCORE) && !ratings.isNull(KEY_SCORE))
                            score = ratings.getInt(KEY_SCORE);
                    }

                    if(movieJSON.has(KEY_POSTERS) && !movieJSON.isNull(KEY_POSTERS)){
                        JSONObject posters = movieJSON.getJSONObject(KEY_POSTERS);

                        if (posters != null && posters.has(KEY_THUMBNAIL) && !posters.isNull(KEY_THUMBNAIL))
                            urlThumbnail = posters.getString(KEY_THUMBNAIL);
                    }

                    Movie movie = new Movie();
                    Date date = null ;
                    try {
                        date = dateFormat.parse(theater);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    movie.setId(id);
                    movie.setTitle(title);
                    movie.setReleaseDate(date);
                    movie.setScore(score);
                    movie.setSynopsis(synopsis);
                    movie.setUrlThumbnail(urlThumbnail);

                    if(id != -1 && title.equals("NA"))
                        listMovies.add(movie);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return listMovies;
    }

    private void handleVolleyError(VolleyError error){
        textVolleyError.setVisibility(View.VISIBLE);

        if(error instanceof TimeoutError || error instanceof NoConnectionError){
            textVolleyError.setText(R.string.error_timeout);
        }else if(error instanceof AuthFailureError){
            textVolleyError.setText(R.string.error_authFailure);
        }else if(error instanceof ServerError){
            textVolleyError.setText(R.string.error_server);
        }else if(error instanceof NetworkError){
            textVolleyError.setText(R.string.error_network);
        }else if(error instanceof ParseError){
            textVolleyError.setText(R.string.error_parse);
        }
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_box_office, container, false);
        textVolleyError = (TextView) view.findViewById(R.id.textVolleyError);
        listMovieHits = (RecyclerView) view.findViewById(R.id.listMovieHits);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice = new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);
        sendJSONRequest();
        return view;
    }


}

