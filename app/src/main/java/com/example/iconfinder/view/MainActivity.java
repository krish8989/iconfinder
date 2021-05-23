package com.example.iconfinder.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.iconfinder.R;
import com.example.iconfinder.adapter.CategoryAdapter;
import com.example.iconfinder.adapter.IconsAdapter;
import com.example.iconfinder.databinding.ActivityMainBinding;
import com.example.iconfinder.listener.CategoryClickListener;
import com.example.iconfinder.listener.IconDownloadListener;
import com.example.iconfinder.responsebody.CategoryResponseModel;
import com.example.iconfinder.responsebody.IconResponseModel;
import com.example.iconfinder.responsebody.ResponseModel;
import com.example.iconfinder.viewmodel.CategoryViewModel;
import com.example.iconfinder.viewmodel.IconViewModel;

public class MainActivity extends AppCompatActivity implements CategoryClickListener, IconDownloadListener {
    private ActivityMainBinding binding;
    private CategoryViewModel categoryViewModel;
    private IconViewModel iconViewModel;
    private CategoryAdapter categoryAdapter;
    private IconsAdapter iconsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter();
        categoryAdapter.setCategoryClickListener(this);
        binding.categoryRecyclerView.setAdapter(categoryAdapter);

        iconsAdapter = new IconsAdapter();
        iconsAdapter.setIconDownloadListener(this);
        int spanCount = getResources().getInteger(R.integer.span_count);
        binding.iconsRecyclerView.setLayoutManager(new GridLayoutManager(this, spanCount));
        binding.iconsRecyclerView.setAdapter(iconsAdapter);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        iconViewModel = new ViewModelProvider(this).get(IconViewModel.class);


        getCategories();
        categoryUpdated();
    }


    private void getCategories() {
        binding.progressBar.setVisibility(View.VISIBLE);
        categoryViewModel.getCategories().observe(this, new Observer<CategoryResponseModel>() {
            @Override
            public void onChanged(CategoryResponseModel categoryResponseModel) {
                if (categoryResponseModel != null) {
                    if (categoryResponseModel.getThrowable() != null) {
                        Toast.makeText(MainActivity.this, categoryResponseModel.getThrowable().getMessage(),
                                Toast.LENGTH_LONG).show();
                    } else if (categoryResponseModel.getResponseCode() != 200) {
                        Toast.makeText(MainActivity.this, categoryResponseModel.getResponseMessage(),
                                Toast.LENGTH_LONG).show();
                    } else {
                        categoryAdapter.setCategoryModels(categoryResponseModel.getCategoryModels());
                    }

                }
            }
        });
    }


    private void categoryUpdated() {
        categoryViewModel.getCategoryIdentifier().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                getIcons(s);
            }
        });
    }


    @Override
    public void categoryClicked(int position) {
        categoryViewModel.updateSelected(position);
    }

    private void getIcons(String categoryIdentifier) {
        binding.progressBar.setVisibility(View.VISIBLE);
        iconViewModel.getIcons(categoryIdentifier).observe(this, new Observer<IconResponseModel>() {
            @Override
            public void onChanged(IconResponseModel iconResponseModel) {
                binding.progressBar.setVisibility(View.GONE);
                if (iconResponseModel.getThrowable() != null) {
                    Toast.makeText(MainActivity.this, iconResponseModel.getThrowable().getMessage(),
                            Toast.LENGTH_LONG).show();
                } else if (iconResponseModel.getResponseCode() != 200) {
                    Toast.makeText(MainActivity.this, iconResponseModel.getResponseMessage(),
                            Toast.LENGTH_LONG).show();
                } else {
                    iconsAdapter.setIcons(iconResponseModel.getIcons());
                }
            }
        });
    }

    @Override
    public void downloadIcon(String path, String iconIdentifier, String format) {
        binding.progressBar.setVisibility(View.VISIBLE);
        iconViewModel.downloadIcon(path, iconIdentifier, format).observe(MainActivity.this, new Observer<ResponseModel>() {
            @Override
            public void onChanged(ResponseModel responseModel) {
                Toast.makeText(MainActivity.this, responseModel.getResponseMessage(), Toast.LENGTH_LONG).show();
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }
}