package com.example.iconfinder.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.iconfinder.api.RetrofitClient;
import com.example.iconfinder.api.WebServices;
import com.example.iconfinder.data.CategoryModel;
import com.example.iconfinder.responsebody.CategoryResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private WebServices webServices;

    public CategoryRepository() {
        webServices = RetrofitClient.getRetrofitClient().create(WebServices.class);
    }

    public MutableLiveData<CategoryResponseModel> getCategories(int count, String after) {
        MutableLiveData<CategoryResponseModel> mutableLiveData = new MutableLiveData<>();
        webServices.getCategories(count, "").enqueue(new Callback<CategoryResponseModel>() {
            @Override
            public void onResponse(Call<CategoryResponseModel> call, Response<CategoryResponseModel> response) {
                CategoryResponseModel categoryResponseModel;
                if (response.isSuccessful()) {
                    categoryResponseModel = response.body();
                    categoryResponseModel.setResponseCode(200);
                    CategoryModel categoryModel = new CategoryModel();
                    categoryModel.setIdentifier("");
                    categoryModel.setSelected(true);
                    categoryModel.setName("All");
                    categoryResponseModel.getCategoryModels().add(0, categoryModel);
                } else {
                    categoryResponseModel = new CategoryResponseModel();
                    categoryResponseModel.setResponseCode(response.code());
                    categoryResponseModel.setResponseMessage(response.message());
                }
                mutableLiveData.postValue(categoryResponseModel);
            }

            @Override
            public void onFailure(Call<CategoryResponseModel> call, Throwable t) {
                CategoryResponseModel categoryResponseModel = new CategoryResponseModel();
                categoryResponseModel.setThrowable(t);
                mutableLiveData.postValue(categoryResponseModel);
            }
        });
        return mutableLiveData;
    }
}
