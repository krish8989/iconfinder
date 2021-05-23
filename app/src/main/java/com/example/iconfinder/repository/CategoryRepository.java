package com.example.iconfinder.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.iconfinder.api.RetrofitClient;
import com.example.iconfinder.api.WebServices;
import com.example.iconfinder.responsebody.CategoryResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryRepository {
    private WebServices webServices;

    public CategoryRepository() {
        webServices = RetrofitClient.getRetrofitClient().create(WebServices.class);
    }

    public MutableLiveData<CategoryResponseModel> getCategories() {
        MutableLiveData<CategoryResponseModel> mutableLiveData = new MutableLiveData<>();
        webServices.getCategories().enqueue(new Callback<CategoryResponseModel>() {
            @Override
            public void onResponse(Call<CategoryResponseModel> call, Response<CategoryResponseModel> response) {
                CategoryResponseModel categoryResponseModel;
                if (response.isSuccessful()) {
                    categoryResponseModel = response.body();
                    categoryResponseModel.setResponseCode(200);
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
