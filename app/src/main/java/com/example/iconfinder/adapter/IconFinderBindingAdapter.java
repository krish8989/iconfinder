package com.example.iconfinder.adapter;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

public class IconFinderBindingAdapter {
    @BindingAdapter(value = {"setImage", "placeHolder"}, requireAll = false)
    public static void setIcon(ImageView view, Object setImage, Drawable placeHolder) {
        RequestOptions requestOptions = new RequestOptions()
                .error(placeHolder)
                .placeholder(placeHolder)
                .diskCacheStrategy(DiskCacheStrategy.ALL);

        Glide.with(view.getContext())
                .load(setImage).apply(requestOptions)
                .dontAnimate()
                .into(view);
    }
}
