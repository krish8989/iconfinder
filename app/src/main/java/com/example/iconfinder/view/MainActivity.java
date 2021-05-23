package com.example.iconfinder.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.iconfinder.R;
import com.example.iconfinder.adapter.CategoryAdapter;
import com.example.iconfinder.databinding.ActivityMainBinding;
import com.example.iconfinder.listener.CategoryClickListener;
import com.example.iconfinder.responsebody.CategoryResponseModel;
import com.example.iconfinder.viewmodel.CategoryViewModel;
import com.example.iconfinder.viewmodel.IconViewModel;

public class MainActivity extends AppCompatActivity implements CategoryClickListener {
    private ActivityMainBinding binding;
    private CategoryViewModel categoryViewModel;
    private IconViewModel iconViewModel;
    private CategoryAdapter categoryAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));
        categoryAdapter = new CategoryAdapter();
        categoryAdapter.setCategoryClickListener(this);
        binding.categoryRecyclerView.setAdapter(categoryAdapter);
        observeCategoryProgressbar();
        observeCategory();
        observeCategoryIdentifier();

    }

    private void observeCategory() {
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


    private void observeCategoryIdentifier() {
        categoryViewModel.getCategoryIdentifier().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
    }

    private void observeCategoryProgressbar() {
        categoryViewModel.progressBar.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    binding.progressBar.setVisibility(View.VISIBLE);
                } else {
                    binding.progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public void categoryClicked(int position) {
        categoryViewModel.updateSelected(position);
    }

}