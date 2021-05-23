package com.example.iconfinder.viewmodel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.iconfinder.api.RetrofitClient;
import com.example.iconfinder.api.WebServices;
import com.example.iconfinder.repository.CategoryRepository;
import com.example.iconfinder.responsebody.CategoryResponseModel;


public class CategoryViewModel extends ViewModel {
    private MutableLiveData<CategoryResponseModel> categories;
    private final CategoryRepository categoryRepository;
    private final MutableLiveData<String> categoryIdentifier;
    private int selectedPosition = 0;
    private WebServices webServices;

    public CategoryViewModel() {
        categoryRepository = new CategoryRepository();
        categoryIdentifier = new MutableLiveData<>("");
        webServices = RetrofitClient.getRetrofitClient().create(WebServices.class);
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

    public MutableLiveData<CategoryResponseModel> getCategories(int count, String after) {
        if (categories == null) {
            getCategoriesData(count, after);
        }
        return categories;
    }

        private void getCategoriesData (int count, String after) {
            if (this.categories == null) {
                this.categories = categoryRepository.getCategories(count, after);
            }
        }

}
