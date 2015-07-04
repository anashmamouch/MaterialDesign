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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
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



    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentBoxOffice.
     */
    // TODO: Rename and change types and number of parameters
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

        singelton = VolleySingelton.getInstance();
        requestQueue = VolleySingelton.getRequestQueue();

        sendJSONRequest();


    }

    private void sendJSONRequest(){

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                getRequestUrl(10),
                (String)null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        listMovies =  parseJSONResponse(response);
                        adapterBoxOffice.setListMovies(listMovies);
                    }
                },new Response.ErrorListener(){

            @Override
            public void onErrorResponse(VolleyError error) {
                L.t(getActivity(), error.toString());
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
                    JSONObject movieJSON = array.getJSONObject(i);

                    Long id = movieJSON.getLong(KEY_ID);

                    String title = movieJSON.getString(KEY_TITLE);

                    JSONObject releaseDates = movieJSON.getJSONObject(KEY_RELEASE_DATES);
                    String theater = null;
                    if (releaseDates.has(KEY_THEATER)) {
                        theater = releaseDates.getString(KEY_THEATER);
                    } else {
                        theater = "NA";
                    }

                    String synopsis = movieJSON.getString(KEY_SYNOPSIS);

                    JSONObject ratings = movieJSON.getJSONObject(KEY_RATINGS);
                    int score = -1;
                    if (ratings.has(KEY_SCORE)) {
                        score = ratings.getInt(KEY_SCORE);
                    }

                    JSONObject posters = movieJSON.getJSONObject(KEY_POSTERS);
                    String urlThumbnail = null;
                    if (posters.has(KEY_THUMBNAIL)) {
                        urlThumbnail = posters.getString(KEY_THUMBNAIL);
                    }

                    Movie movie = new Movie();
                    Date date = dateFormat.parse(theater);

                    movie.setId(id);
                    movie.setTitle(title);
                    movie.setReleaseDate(date);
                    movie.setScore(score);
                    movie.setSynopsis(synopsis);
                    movie.setUrlThumbnail(urlThumbnail);

                    listMovies.add(movie);

                }


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return listMovies;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_box_office, container, false);
        listMovieHits = (RecyclerView) view.findViewById(R.id.listMovieHits);
        listMovieHits.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapterBoxOffice = new AdapterBoxOffice(getActivity());
        listMovieHits.setAdapter(adapterBoxOffice);
        sendJSONRequest();
        return view;
    }


}

