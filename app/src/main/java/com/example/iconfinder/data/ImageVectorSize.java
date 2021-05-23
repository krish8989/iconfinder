package com.example.iconfinder.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ImageVectorSize {

    private int[] targetSizes;

    private ArrayList<IconFormat> formats;

    private String size;
    @SerializedName("size_height")
    private String sizeHeight;
    @SerializedName("size_width")
    private String sizeWidth;

    public int[] getTargetSizes() {
        return targetSizes;
    }

    public void setTargetSizes(int[] targetSizes) {
        this.targetSizes = targetSizes;
    }

    public ArrayList<IconFormat> getFormats() {
        return formats;
    }

    public void setFormats(ArrayList<IconFormat> formats) {
        this.formats = formats;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getSize_height() {
        return sizeHeight;
    }

    public void setSize_height(String size_height) {
        this.sizeHeight = size_height;
    }

    public String getSize_width() {
        return sizeWidth;
    }

    public void setSize_width(String size_width) {
        this.sizeWidth = size_width;
    }
}
