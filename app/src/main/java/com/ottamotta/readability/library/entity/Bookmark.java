package com.ottamotta.readability.library.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.ottamotta.readability.library.entity.Article;

import java.util.Date;

public class Bookmark {

    @Expose
    private int id;

    @SerializedName("user_id")
    @Expose
    private int userId;

    @SerializedName("read_percent")
    @Expose

    private String readPercent;

    @SerializedName("date_updated")
    @Expose
    private Date dateUpdated;

    @Expose
    private boolean favorite;

    @Expose
    private Article article;

    @SerializedName("date_archived")
    @Expose
    private Date dateArchived;

    @SerializedName("date_opened")
    @Expose
    private Date dateOpened;

    @SerializedName("date_added")
    @Expose
    private Date dateAdded;

    @SerializedName("article_href")
    @Expose
    private String articleHref;

    @SerializedName("date_favorited")
    @Expose
    private Date dateFavorited;

    @Expose
    private boolean archive;

    @Override
    public String toString() {
        return "Bookmark{" +
                "archive=" + archive +
                ", dateFavorited=" + dateFavorited +
                ", articleHref='" + articleHref + '\'' +
                ", dateAdded=" + dateAdded +
                ", dateOpened=" + dateOpened +
                ", dateArchived=" + dateArchived +
                ", article=" + article +
                ", favorite=" + favorite +
                ", dateUpdated=" + dateUpdated +
                ", readPercent='" + readPercent + '\'' +
                ", userId=" + userId +
                ", id=" + id +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getUserId() {
        return userId;
    }

    public String getReadPercent() {
        return readPercent;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public Article getArticle() {
        return article;
    }

    public Date getDateArchived() {
        return dateArchived;
    }

    public Date getDateOpened() {
        return dateOpened;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public String getArticleHref() {
        return articleHref;
    }

    public Date getDateFavorited() {
        return dateFavorited;
    }

    public boolean isArchive() {
        return archive;
    }
}
