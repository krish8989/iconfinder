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
    private MutableLiveData<CategoryResponseModel> mutableLiveData = new MutableLiveData<>();

    public CategoryRepository() {
        webServices = RetrofitClient.getRetrofitClient().create(WebServices.class);
    }

    public MutableLiveData<CategoryResponseModel> getCategories(String after) {
        if (mutableLiveData.getValue() == null) {
            Log.e("livadata: ", "live data null");
        } else {
            Log.e("live data: ", "not null");
        }
        webServices.getCategories( after).enqueue(new Callback<CategoryResponseModel>() {
            @Override
            public void onResponse(Call<CategoryResponseModel> call, Response<CategoryResponseModel> response) {
//                CategoryResponseModel categoryResponseModel;
                if (response.isSuccessful()) {
                    CategoryResponseModel categoryResponseModel = response.body();

                    if (mutableLiveData.getValue() == null) {
                        categoryResponseModel.setResponseCode(200);
                        CategoryModel categoryModel = new CategoryModel();
                        categoryModel.setIdentifier("");
                        categoryModel.setSelected(true);
                        categoryModel.setName("All");
                        categoryResponseModel.getCategoryModels().add(0, categoryModel);
                        mutableLiveData.setValue(categoryResponseModel);
                    } else {
                        CategoryResponseModel categoryResponseModel1 = mutableLiveData.getValue();
                        categoryResponseModel1.getCategoryModels().addAll(categoryResponseModel1.getCategoryModels().size(), categoryResponseModel.getCategoryModels());
                        mutableLiveData.setValue(categoryResponseModel1);
                    }
                } else {
                    CategoryResponseModel categoryResponseModel;
                    if (mutableLiveData.getValue() == null) {
                        categoryResponseModel = new CategoryResponseModel();

                    } else {
                        categoryResponseModel = mutableLiveData.getValue();

                    }
                    categoryResponseModel.setResponseCode(response.code());
                    categoryResponseModel.setResponseMessage(response.message());
                    mutableLiveData.setValue(categoryResponseModel);
                }
            }

            @Override
            public void onFailure(Call<CategoryResponseModel> call, Throwable t) {
                CategoryResponseModel categoryResponseModel;
                if (mutableLiveData.getValue() == null) {
                    categoryResponseModel = new CategoryResponseModel();

                } else {
                    categoryResponseModel = mutableLiveData.getValue();

                }
                categoryResponseModel.setThrowable(t);
                mutableLiveData.setValue(categoryResponseModel);
            }
        });
        return mutableLiveData;
    }
}
