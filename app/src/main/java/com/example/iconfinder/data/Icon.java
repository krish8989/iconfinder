package com.example.iconfinder.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Icon {
    @SerializedName("is_premium")
    boolean isPremium;
    @SerializedName("icon_id")
    String iconId;
    String type;
    @SerializedName("vector_sizes")
    ArrayList<ImageVectorSize> vectorSizes;
    @SerializedName("raster_sizes")
    ArrayList<IconRasterSize> rasterSizes;
    ArrayList<IconPrice> prices;

    public ArrayList<IconPrice> getPrices() {
        return prices;
    }

    public void setPrices(ArrayList<IconPrice> prices) {
        this.prices = prices;
    }

    public boolean isPremium() {
        return isPremium;
    }

    public void setPremium(boolean premium) {
        isPremium = premium;
    }

    public String getIconId() {
        return iconId;
    }

    public void setIconId(String iconId) {
        this.iconId = iconId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    public ArrayList<ImageVectorSize> getVectorSizes() {
        return vectorSizes;
    }

    public void setVectorSizes(ArrayList<ImageVectorSize> vectorSizes) {
        this.vectorSizes = vectorSizes;
    }

    public ArrayList<IconRasterSize> getRasterSizes() {
        return rasterSizes;
    }

    public void setRasterSizes(ArrayList<IconRasterSize> rasterSizes) {
        this.rasterSizes = rasterSizes;
    }


}

