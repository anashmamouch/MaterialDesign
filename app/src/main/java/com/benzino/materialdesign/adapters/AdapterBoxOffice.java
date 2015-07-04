package com.benzino.materialdesign.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.benzino.materialdesign.R;
import com.benzino.materialdesign.network.VolleySingelton;
import com.benzino.materialdesign.pojo.Movie;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Anas on 04/07/2015.
 */
public class AdapterBoxOffice extends RecyclerView.Adapter<AdapterBoxOffice.ViewHolderBoxOffice> {

    private ArrayList<Movie> listMovies = new ArrayList<>();
    private LayoutInflater layoutInflater;
    private VolleySingelton singelton;
    private ImageLoader imageLoader;

    public AdapterBoxOffice(Context context){
        layoutInflater = LayoutInflater.from(context);
        singelton = VolleySingelton.getInstance();
        imageLoader = VolleySingelton.getImageLoader();
    }

    public void setListMovies(ArrayList<Movie> listMovies) {
        this.listMovies = listMovies;
        notifyItemRangeChanged(0, listMovies.size());
    }

    @Override
    public ViewHolderBoxOffice onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.movie_box_office, parent, false);
        ViewHolderBoxOffice viewHolder = new ViewHolderBoxOffice(view);
        return null;
    }

    @Override
    public void onBindViewHolder(final ViewHolderBoxOffice holder, int position) {
        Movie movie = listMovies.get(position);
        holder.movieTitle.setText(movie.getTitle());
        holder.movieReleaseDate.setText(movie.getReleaseDate().toString());
        holder.movieAudienceScore.setRating(movie.getScore()/20.0F);
        String urlThumbnail = movie.getUrlThumbnail();
        if(urlThumbnail != null){
            imageLoader.get(urlThumbnail, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    holder.movieThumbnail.setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    static class ViewHolderBoxOffice extends RecyclerView.ViewHolder{
        private ImageView movieThumbnail;
        private TextView  movieTitle;
        private TextView movieReleaseDate;
        private RatingBar movieAudienceScore;

        public ViewHolderBoxOffice(View itemView) {
            super(itemView);
            movieThumbnail = (ImageView) itemView.findViewById(R.id.movieThumbnail);
            movieTitle = (TextView) itemView.findViewById(R.id.movieTitle);
            movieReleaseDate = (TextView) itemView.findViewById(R.id.movieReleaseDate);
            movieAudienceScore = (RatingBar) itemView.findViewById(R.id.movieAudienceScore);
        }
    }
}
