package com.ottamotta.readability.library.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class Article {

    @Expose
    private String id;

    @Expose
    private String domain;

    @Expose
    private String title;

    @Expose
    private String url;

    @SerializedName("lead_image_url")
    @Expose
    private String leadImageUrl;

    @Expose
    private String author;

    @Expose
    private String excerpt;

    @Expose
    private String direction;

    @SerializedName("word_count")
    @Expose
    private int wordCount;

    @SerializedName("date_published")
    @Expose
    private Date datePublished;

    @Expose
    private boolean processed;


    @Override
    public String toString() {
        return "Article{" +
                "processed=" + processed +
                ", datePublished=" + datePublished +
                ", wordCount=" + wordCount +
                ", direction='" + direction + '\'' +
                ", excerpt='" + excerpt + '\'' +
                ", author='" + author + '\'' +
                ", leadImageUrl='" + leadImageUrl + '\'' +
                ", url='" + url + '\'' +
                ", title='" + title + '\'' +
                ", domain='" + domain + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}