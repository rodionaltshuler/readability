package com.ottamotta.readability.library.manager.network;

import com.ottamotta.readability.library.entity.Bookmark;

import retrofit.client.Response;
import retrofit.http.Field;
import retrofit.http.FormUrlEncoded;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

interface BookmarksRestService {

    @FormUrlEncoded
    @POST("/api/rest/v1/bookmarks")
    Response postBookmark(@Field("url") String url);

    @GET("/{location}")
    Bookmark getBookmark(@Path("location") String location);

    @GET("/api/rest/v1/bookmarks")
    BookmarksResponse getBookmarks();

}
