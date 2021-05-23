package com.example.iconfinder.responsebody;

import com.example.iconfinder.data.CategoryModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class CategoryResponseModel extends ResponseModel {
    @SerializedName("total_count")
    private String totalCount;
    @SerializedName("categories")
    private ArrayList<CategoryModel> categoryModels;

    public String getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(String totalCount) {
        this.totalCount = totalCount;
    }

    public ArrayList<CategoryModel> getCategoryModels() {
        return categoryModels;
    }

    public void setCategoryModels(ArrayList<CategoryModel> categoryModels) {
        this.categoryModels = categoryModels;
    }
}
