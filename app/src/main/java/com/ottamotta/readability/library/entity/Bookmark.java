package com.ottamotta.readability.library.entity;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Bookmark implements Serializable {

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

    public Bookmark setId(int id) {
        this.id = id;
        return this;
    }

    public Bookmark setUserId(int userId) {
        this.userId = userId;
        return this;
    }

    public Bookmark setReadPercent(String readPercent) {
        this.readPercent = readPercent;
        return this;
    }

    public Bookmark setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
        return this;
    }

    public Bookmark setFavorite(boolean favorite) {
        this.favorite = favorite;
        return this;
    }

    public Bookmark setArticle(Article article) {
        this.article = article;
        return this;
    }

    public Bookmark setDateArchived(Date dateArchived) {
        this.dateArchived = dateArchived;
        return this;
    }

    public Bookmark setDateOpened(Date dateOpened) {
        this.dateOpened = dateOpened;
        return this;
    }

    public Bookmark setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
        return this;
    }

    public Bookmark setArticleHref(String articleHref) {
        this.articleHref = articleHref;
        return this;
    }

    public Bookmark setDateFavorited(Date dateFavorited) {
        this.dateFavorited = dateFavorited;
        return this;
    }

    public Bookmark setArchive(boolean archive) {
        this.archive = archive;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bookmark bookmark = (Bookmark) o;

        if (id != bookmark.id) return false;
        if (userId != bookmark.userId) return false;
        if (favorite != bookmark.favorite) return false;
        if (archive != bookmark.archive) return false;
        if (readPercent != null ? !readPercent.equals(bookmark.readPercent) : bookmark.readPercent != null)
            return false;
        if (dateUpdated != null ? !dateUpdated.equals(bookmark.dateUpdated) : bookmark.dateUpdated != null)
            return false;
        if (article != null ? !article.equals(bookmark.article) : bookmark.article != null)
            return false;
        if (dateArchived != null ? !dateArchived.equals(bookmark.dateArchived) : bookmark.dateArchived != null)
            return false;
        if (dateOpened != null ? !dateOpened.equals(bookmark.dateOpened) : bookmark.dateOpened != null)
            return false;
        if (dateAdded != null ? !dateAdded.equals(bookmark.dateAdded) : bookmark.dateAdded != null)
            return false;
        if (articleHref != null ? !articleHref.equals(bookmark.articleHref) : bookmark.articleHref != null)
            return false;
        return !(dateFavorited != null ? !dateFavorited.equals(bookmark.dateFavorited) : bookmark.dateFavorited != null);

    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + userId;
        result = 31 * result + (readPercent != null ? readPercent.hashCode() : 0);
        result = 31 * result + (dateUpdated != null ? dateUpdated.hashCode() : 0);
        result = 31 * result + (favorite ? 1 : 0);
        result = 31 * result + (article != null ? article.hashCode() : 0);
        result = 31 * result + (dateArchived != null ? dateArchived.hashCode() : 0);
        result = 31 * result + (dateOpened != null ? dateOpened.hashCode() : 0);
        result = 31 * result + (dateAdded != null ? dateAdded.hashCode() : 0);
        result = 31 * result + (articleHref != null ? articleHref.hashCode() : 0);
        result = 31 * result + (dateFavorited != null ? dateFavorited.hashCode() : 0);
        result = 31 * result + (archive ? 1 : 0);
        return result;
    }
}
