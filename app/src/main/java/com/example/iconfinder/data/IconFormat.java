package com.example.iconfinder.data;

import com.google.gson.annotations.SerializedName;

public class IconFormat {

    String format;
    @SerializedName("download_url")
    String downloadUrl;
    @SerializedName("preview_url")
    String previewUrl;

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }


}
