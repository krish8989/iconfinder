package com.example.iconfinder.api;

import com.example.iconfinder.responsebody.CategoryResponseModel;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface WebServices {
    @GET("categories")
    Call<CategoryResponseModel> getCategories();

//    @GET("icons/search")
//    Call<IconResponseModel> getIcons(@Query("query") String query, @Query("count") int count,
//                                     @Query("offset") int offset, @Query("category") String category);
}
