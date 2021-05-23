package com.example.iconfinder.repository;

import android.app.Application;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.MainThread;
import androidx.lifecycle.MutableLiveData;

import com.example.iconfinder.api.RetrofitClient;
import com.example.iconfinder.api.WebServices;
import com.example.iconfinder.responsebody.ResponseModel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DownloadIconRepository {
    private Application application;
    private WebServices webServices;
    private MutableLiveData<ResponseModel> responseModelMutableLiveData;

    public DownloadIconRepository(Application application) {
        this.application = application;
        webServices = RetrofitClient.getRetrofitClient().create(WebServices.class);
    }

    public MutableLiveData<ResponseModel> downloadFile(String uri, String iconIdentifier, String format) {
        responseModelMutableLiveData = new MutableLiveData<>();
        ResponseModel responseModel = new ResponseModel();
        webServices.downloadFileWithDynamicUrlSync(uri).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    saveFile(response.body(), iconIdentifier, format);
                } else {
                    responseModel.setResponseCode(response.code());
                    responseModel.setResponseMessage(response.message());
                    responseModelMutableLiveData.setValue(responseModel);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                ResponseModel responseModel = new ResponseModel();
                responseModel.setThrowable(t);
                responseModelMutableLiveData.setValue(responseModel);
            }
        });
        return responseModelMutableLiveData;
    }

    private void saveFile(ResponseBody body, String iconIdentifier, String format) {
        Handler handler = new Handler(Looper.getMainLooper());
        ResponseModel responseModel = new ResponseModel();
        Runnable r = new Runnable() {
            @Override
            public void run() {
                final ContentValues values = new ContentValues();
                values.put(MediaStore.MediaColumns.DISPLAY_NAME, iconIdentifier.concat(".").concat(format));
                values.put(MediaStore.MediaColumns.MIME_TYPE, "image/*");
                values.put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DCIM);

                final ContentResolver resolver = application.getApplicationContext().getContentResolver();

                try {
                    final Uri contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                    Uri uri = resolver.insert(contentUri, values);
                    if (uri == null) {
                        Log.e("uri", "null");
                    } else {
                        OutputStream stream = resolver.openOutputStream(uri);
                        if (stream == null)
                            throw new IOException("Failed to open output stream.");
                        InputStream inputStream = body.byteStream();

                        byte[] fileReader = new byte[4096];
                        long fileSizeDownloaded = 0;
                        while (true) {
                            int read = inputStream.read(fileReader);
                            if (read == -1) {
                                break;
                            }
                            stream.write(fileReader, 0, read);
                            fileSizeDownloaded += read;
                        }
                        stream.flush();
                        responseModel.setResponseCode(200);
                        responseModel.setResponseMessage("Downloading completed");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    responseModel.setResponseCode(400);
                    responseModel.setResponseMessage("Failed to download icon");
                }
                handler.post(new Runnable() {
                    public void run() {
                        responseModelMutableLiveData.setValue(responseModel);
                    }
                });
            }
        };
        Thread t = new Thread(r);
        t.start();
    }
}
