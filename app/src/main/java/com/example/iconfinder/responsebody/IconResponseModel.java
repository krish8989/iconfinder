package com.example.iconfinder.responsebody;

import com.example.iconfinder.data.Icon;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class IconResponseModel extends ResponseModel {
    @SerializedName("icons")
    private ArrayList<Icon> icons;
    @SerializedName("total_count")
    private String totalCount;


    public ArrayList<Icon> getIcons() {
        return icons;
    }

    public void setIcons(ArrayList<Icon> icons) {
        this.icons = icons;
    }

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }


}
