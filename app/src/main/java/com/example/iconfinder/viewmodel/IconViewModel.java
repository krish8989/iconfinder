package com.example.iconfinder.viewmodel;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.iconfinder.repository.DownloadIconRepository;
import com.example.iconfinder.repository.IconRepository;
import com.example.iconfinder.responsebody.IconResponseModel;
import com.example.iconfinder.responsebody.ResponseModel;

public class IconViewModel extends AndroidViewModel {
    private final IconRepository iconsRepository;
    private DownloadIconRepository downloadIconRepository;
    private MutableLiveData<IconResponseModel> icons;
    private String identifier;
    public MutableLiveData<ResponseModel> fileDownloadResponse;

    public IconViewModel(@NonNull Application application) {
        super(application);
        iconsRepository = new IconRepository();
        downloadIconRepository = new DownloadIconRepository(application);
    }

    public MutableLiveData<IconResponseModel> getIcons(String identifier) {
        this.identifier = identifier;
        getIcons();
        return icons;
    }

    private void getIcons() {
        this.icons = iconsRepository.getIcons(identifier);
    }

    public MutableLiveData<ResponseModel> downloadIcon(String url, String iconIdentifier, String format) {
        fileDownloadResponse = downloadIconRepository.downloadFile(url, iconIdentifier, format);
        return fileDownloadResponse;
    }

}
