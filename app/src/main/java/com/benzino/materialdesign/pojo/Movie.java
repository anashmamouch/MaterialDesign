package com.benzino.materialdesign.pojo;

import java.util.Date;

/**
 * Created by Anas on 03/07/2015.
 */
public class Movie {

    private long id;
    private String title;
    private Date releaseDate;
    private int score;
    private String synopsis;
    private String urlThumbnail;
    private String urlSelf;
    private String urlCast;
    private String urlReviews;
    private String urlSimilar;

    public Movie() {
    }

    public Movie(long id,
                 String title,
                 Date releaseDate,
                 int score,
                 String synopsis,
                 String urlThumbnail,
                 String urlSelf,
                 String urlCast,
                 String urlReviews,
                 String urlSimilar) {
        this.id = id;
        this.title = title;
        this.releaseDate = releaseDate;
        this.score = score;
        this.synopsis = synopsis;
        this.urlThumbnail = urlThumbnail;
        this.urlSelf = urlSelf;
        this.urlCast = urlCast;
        this.urlReviews = urlReviews;
        this.urlSimilar = urlSimilar;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Date releaseDate) {
        this.releaseDate = releaseDate;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getUrlThumbnail() {
        return urlThumbnail;
    }

    public void setUrlThumbnail(String urlThumbnail) {
        this.urlThumbnail = urlThumbnail;
    }

    public String getUrlSelf() {
        return urlSelf;
    }

    public void setUrlSelf(String urlSelf) {
        this.urlSelf = urlSelf;
    }

    public String getUrlCast() {
        return urlCast;
    }

    public void setUrlCast(String urlCast) {
        this.urlCast = urlCast;
    }

    public String getUrlReviews() {
        return urlReviews;
    }

    public void setUrlReviews(String urlReviews) {
        this.urlReviews = urlReviews;
    }

    public String getUrlSimilar() {
        return urlSimilar;
    }

    public void setUrlSimilar(String urlSimilar) {
        this.urlSimilar = urlSimilar;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", releaseDate=" + releaseDate +
                ", score=" + score +
                ", synopsis='" + synopsis + '\'' +
                ", urlThumbnail='" + urlThumbnail + '\'' +
                '}';
    }
}
