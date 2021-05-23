package com.example.iconfinder.api;

import com.example.iconfinder.responsebody.CategoryResponseModel;
import com.example.iconfinder.responsebody.IconResponseModel;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface WebServices {
    @GET("categories")
    Call<CategoryResponseModel> getCategories( @Query("after")String after);

    @GET("icons/search")
    Call<IconResponseModel> getIcons(@Query("query") String query, @Query("count") int count,
                                     @Query("offset") int offset, @Query("category") String category);


    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);
}
