package com.example.iconfinder.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.os.Build;
import android.os.Bundle;
import android.system.Os;
import android.view.Menu;
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

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;


public class MainActivity extends AppCompatActivity implements
        CategoryClickListener, IconDownloadListener {
    private static final int RC_WRITE_STORAGE = 1;
    private ActivityMainBinding binding;
    private CategoryViewModel categoryViewModel;
    private IconViewModel iconViewModel;
    private CategoryAdapter categoryAdapter;
    private IconsAdapter iconsAdapter;
    private String categoryIdentifier;
    private String lastIdentifier;
    private int categorySize;
    private String downloadPath;
    private String iconIdentifier;
    private String iconFormat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);

        binding.categoryRecyclerView.setLayoutManager(new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false));

        categoryAdapter = new CategoryAdapter();
        categoryAdapter.setCategoryClickListener(this);
        binding.categoryRecyclerView.setAdapter(categoryAdapter);


        iconsAdapter = new IconsAdapter();

        iconsAdapter.setIconDownloadListener(this);
        binding.iconsRecyclerView.setLayoutManager(new GridLayoutManager(this, getResources().getInteger(R.integer.span_count)));
        binding.iconsRecyclerView.setAdapter(iconsAdapter);

        categoryViewModel = new ViewModelProvider(this).get(CategoryViewModel.class);
        iconViewModel = new ViewModelProvider(this).get(IconViewModel.class);
        setCategoryRecyclerViewPagination();
        getCategories("");
        categoryUpdated();

    }

    private void setCategoryRecyclerViewPagination() {
        binding.categoryRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                if (linearLayoutManager != null && linearLayoutManager.findLastCompletelyVisibleItemPosition() == categorySize) {
                    getCategories(lastIdentifier);
                }
            }
        });
    }

    private void getCategories(String after) {
        binding.progressBar.setVisibility(View.VISIBLE);
        categoryViewModel.getCategories(after).observe(this, new Observer<CategoryResponseModel>() {
            @Override
            public void onChanged(CategoryResponseModel categoryResponseModel) {
                binding.progressBar.setVisibility(View.GONE);
                if (categoryResponseModel != null) {
                    if (categoryResponseModel.getThrowable() != null) {
                        Toast.makeText(MainActivity.this, categoryResponseModel.getThrowable().getMessage(),
                                Toast.LENGTH_LONG).show();
                    } else if (categoryResponseModel.getResponseCode() != 200) {
                        Toast.makeText(MainActivity.this, categoryResponseModel.getResponseMessage(),
                                Toast.LENGTH_LONG).show();
                    } else {
                        categoryAdapter.setCategoryModels(categoryResponseModel.getCategoryModels());
                        binding.categoryRecyclerView.post(new Runnable() {
                            @Override
                            public void run() {
                                categoryAdapter.notifyDataSetChanged();
                            }
                        });
                        categorySize = categoryResponseModel.getCategoryModels().size() - 1;
                        lastIdentifier = categoryResponseModel.getCategoryModels().get(categorySize).getIdentifier();
                    }
                }
            }
        });
    }


    private void categoryUpdated() {
        categoryViewModel.getCategoryIdentifier().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                categoryIdentifier = s;
                getIcons("", s);
            }
        });
    }


    @Override
    public void categoryClicked(int position) {
        categoryViewModel.updateSelected(position);
    }

    private void getIcons(String query, String categoryIdentifier) {
        binding.progressBar.setVisibility(View.VISIBLE);
        iconViewModel.getIcons(query, categoryIdentifier).observe(this, new Observer<IconResponseModel>() {
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
        this.downloadPath = path;
        this.iconIdentifier = iconIdentifier;
        this.iconFormat = format;
        if (Build.VERSION.SDK_INT < 29) {
            requestWritePermission();
        } else {
            download();
        }

    }

    public void download() {
        binding.progressBar.setVisibility(View.VISIBLE);
        iconViewModel.downloadIcon(downloadPath, iconIdentifier, iconFormat).observe(MainActivity.this, new Observer<ResponseModel>() {
            @Override
            public void onChanged(ResponseModel responseModel) {
                Toast.makeText(MainActivity.this, responseModel.getResponseMessage(), Toast.LENGTH_LONG).show();
                binding.progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_activity, menu);
        SearchView searchView =
                (SearchView) menu.findItem(R.id.search).getActionView();
        searchView.setQueryHint("Find Icons Here");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                getIcons(newText, categoryIdentifier);
                return true;
            }
        });
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                getIcons("", categoryIdentifier);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @AfterPermissionGranted(RC_WRITE_STORAGE)
    private void requestWritePermission() {
        String[] perms = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if (EasyPermissions.hasPermissions(this, perms)) {
            download();
        } else {
            EasyPermissions.requestPermissions(this, getString(R.string.write_permission_rationale),
                    RC_WRITE_STORAGE, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }
}