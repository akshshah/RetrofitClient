package com.example.retrofitclient;

import com.example.retrofitclient.model.Comment;
import com.example.retrofitclient.model.Post;

import java.util.ArrayList;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface MyWebService {

    String BASE_URL = "https://jsonplaceholder.typicode.com/";
    String FEED = "posts";

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    // to get posts
    @GET(FEED)
    Call<ArrayList<Post>> getPosts();

    // to get comments
    // ------ Method 1 ------
//    @GET()
//    Call<ArrayList<Comment>> getComments(@Url String url);

    // --------  Method 2 -------
//    @GET("posts/{id}/comments")
//    Call<ArrayList<Comment>> getComments(@Path("id") int userId);

    // --------  Method 3 -------
//    @GET("comments")
//    Call<ArrayList<Comment>> getComments(@Query("postId") int postid,
//                                         @Query("_sort") String sortBy,
//                                         @Query("_order") String orderBy);

     //--------  Method 5 -------
//    @GET("comments")
//    Call<ArrayList<Comment>> getComments(@Query("postId") Integer[] postid ,
//                                         @Query("_sort") String sortBy,
//                                         @Query("_order") String orderBy);

    // Method  4
    @GET("comments")
    Call<ArrayList<Comment>> getComments(@QueryMap Map<String, String> params );


    // ------------------------- Post -------------------------------------

//    @POST("posts")
//    Call<Post> createPost(@Body Post post);
//
//    @FormUrlEncoded
//    @POST("posts")
//    Call<Post> createPost(@Field("userId") int userId ,
//                              @Field("title") String title,
//                              @Field("body") String body);

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@FieldMap Map<String, String> postMap);

    //-------------- put & Patch -----------------------

    @PUT("posts/{id}")
    Call<Post> putPost (@Path("id") int id, @Body Post post);

    @PATCH("posts/{id}")
    Call<Post> patch (@Path("id") int id, @Body Post post);

    //------------------- delete -----------------------

    @DELETE("posts/{id}")
    Call<Void> deletePost (@Path("id") int id);

}
