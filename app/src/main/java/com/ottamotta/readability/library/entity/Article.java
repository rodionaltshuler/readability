package com.ottamotta.readability.library.entity;

import android.widget.ArrayAdapter;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Date;

public class Article implements Serializable {

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


    public String getId() {
        return id;
    }

    public String getDomain() {
        return domain;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getLeadImageUrl() {
        return leadImageUrl;
    }

    public String getAuthor() {
        return author;
    }

    public String getExcerpt() {
        return excerpt;
    }

    public String getDirection() {
        return direction;
    }

    public int getWordCount() {
        return wordCount;
    }

    public Date getDatePublished() {
        return datePublished;
    }

    public boolean isProcessed() {
        return processed;
    }

    public Article setId(String id) {
        this.id = id;
        return this;
    }

    public Article setDomain(String domain) {
        this.domain = domain;
        return this;
    }

    public Article setTitle(String title) {
        this.title = title;
        return this;
    }

    public Article setUrl(String url) {
        this.url = url;
        return this;
    }

    public Article setLeadImageUrl(String leadImageUrl) {
        this.leadImageUrl = leadImageUrl;
        return this;
    }

    public Article setAuthor(String author) {
        this.author = author;
        return this;
    }

    public Article setExcerpt(String excerpt) {
        this.excerpt = excerpt;
        return this;
    }

    public Article setDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public Article setWordCount(int wordCount) {
        this.wordCount = wordCount;
        return this;
    }

    public Article setDatePublished(Date datePublished) {
        this.datePublished = datePublished;
        return this;
    }

    public Article setProcessed(boolean processed) {
        this.processed = processed;
        return this;
    }

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Article article = (Article) o;

        if (wordCount != article.wordCount) return false;
        if (processed != article.processed) return false;
        if (id != null ? !id.equals(article.id) : article.id != null) return false;
        if (domain != null ? !domain.equals(article.domain) : article.domain != null) return false;
        if (title != null ? !title.equals(article.title) : article.title != null) return false;
        if (url != null ? !url.equals(article.url) : article.url != null) return false;
        if (leadImageUrl != null ? !leadImageUrl.equals(article.leadImageUrl) : article.leadImageUrl != null)
            return false;
        if (author != null ? !author.equals(article.author) : article.author != null) return false;
        if (excerpt != null ? !excerpt.equals(article.excerpt) : article.excerpt != null)
            return false;
        if (direction != null ? !direction.equals(article.direction) : article.direction != null)
            return false;
        return !(datePublished != null ? !datePublished.equals(article.datePublished) : article.datePublished != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (domain != null ? domain.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (url != null ? url.hashCode() : 0);
        result = 31 * result + (leadImageUrl != null ? leadImageUrl.hashCode() : 0);
        result = 31 * result + (author != null ? author.hashCode() : 0);
        result = 31 * result + (excerpt != null ? excerpt.hashCode() : 0);
        result = 31 * result + (direction != null ? direction.hashCode() : 0);
        result = 31 * result + wordCount;
        result = 31 * result + (datePublished != null ? datePublished.hashCode() : 0);
        result = 31 * result + (processed ? 1 : 0);
        return result;
    }
}