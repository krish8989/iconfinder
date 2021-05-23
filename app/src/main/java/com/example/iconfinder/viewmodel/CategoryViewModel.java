package com.example.iconfinder.viewmodel;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.iconfinder.repository.CategoryRepository;
import com.example.iconfinder.responsebody.CategoryResponseModel;

public class CategoryViewModel extends ViewModel {
    private MutableLiveData<CategoryResponseModel> categories;
    private final CategoryRepository categoryRepository;
    private final MutableLiveData<String> categoryIdentifier;
    private int selectedPosition = 0;

    public CategoryViewModel() {
        categoryRepository = new CategoryRepository();
        categoryIdentifier = new MutableLiveData<>("");
    }

    public MutableLiveData<String> getCategoryIdentifier() {
        return categoryIdentifier;
    }

    public void updateSelected(int position) {
        CategoryResponseModel categoryResponseModel = categories.getValue();
        categoryResponseModel.getCategoryModels().get(selectedPosition).setSelected(false);
        this.selectedPosition = position;
        categoryResponseModel.getCategoryModels().get(selectedPosition).setSelected(true);
        categories.setValue(categoryResponseModel);
        categoryIdentifier.postValue(categoryResponseModel.getCategoryModels().get(selectedPosition).getIdentifier());
    }

    public MutableLiveData<CategoryResponseModel> getCategories() {
        getCategoriesData();
        return categories;
    }

    private void getCategoriesData() {
        this.categories = categoryRepository.getCategories();
    }
}
