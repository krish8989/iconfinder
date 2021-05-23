package com.example.iconfinder.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.iconfinder.repository.CategoryRepository;
import com.example.iconfinder.responsebody.CategoryResponseModel;

public class CategoryViewModel extends ViewModel {
    private MutableLiveData<CategoryResponseModel> categories;
    private CategoryRepository categoryRepository;

    public CategoryViewModel() {
        categoryRepository = new CategoryRepository();
    }

    public MutableLiveData<CategoryResponseModel> getCategories() {
        getCategoriesData();
        return categories;
    }

    private void getCategoriesData() {
        this.categories = categoryRepository.getCategories();
    }
}
