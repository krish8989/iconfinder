package com.example.iconfinder.repository;

import androidx.lifecycle.MutableLiveData;

import com.example.iconfinder.api.RetrofitClient;
import com.example.iconfinder.api.WebServices;
import com.example.iconfinder.responsebody.IconResponseModel;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class IconRepository {
    private final WebServices webServices;
    private MutableLiveData<IconResponseModel> iconResponseModelMutableLiveData = new MutableLiveData<>();

    public IconRepository() {
        webServices = RetrofitClient.getRetrofitClient().create(WebServices.class);
    }

    public MutableLiveData<IconResponseModel> getIcons(String query, String identifier) {

        webServices.getIcons(query, 20, 0, identifier).enqueue(new Callback<IconResponseModel>() {
            @Override
            public void onResponse(Call<IconResponseModel> call, Response<IconResponseModel> response) {
                IconResponseModel iconResponseModel;
                if (response.isSuccessful()) {
                    iconResponseModel = response.body();
                    iconResponseModel.setResponseCode(200);
                } else {
                    iconResponseModel = new IconResponseModel();
                    iconResponseModel.setResponseCode(response.code());
                    iconResponseModel.setResponseMessage(response.message());
                }
                iconResponseModelMutableLiveData.setValue(iconResponseModel);
            }

            @Override
            public void onFailure(Call<IconResponseModel> call, Throwable t) {
                IconResponseModel iconResponseModel = new IconResponseModel();
                iconResponseModel.setThrowable(t);
                iconResponseModelMutableLiveData.setValue(iconResponseModel);
            }
        });
        return iconResponseModelMutableLiveData;
    }
}
