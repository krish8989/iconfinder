package com.example.iconfinder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iconfinder.data.CategoryModel;
import com.example.iconfinder.databinding.LayoutCategoryItemBinding;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private ArrayList<CategoryModel> categoryModels;
    private LayoutInflater inflater;

    public CategoryAdapter() {

    }

    public void setCategoryModels(ArrayList<CategoryModel> categoryModels) {
        this.categoryModels = categoryModels;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        LayoutCategoryItemBinding binding = LayoutCategoryItemBinding.inflate(inflater, parent, false);
        return new CategoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(categoryModels.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryModels == null ? 0 : categoryModels.size();
    }

    public class CategoryViewHolder extends RecyclerView.ViewHolder {
        private LayoutCategoryItemBinding binding;

        CategoryViewHolder(@NonNull LayoutCategoryItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(CategoryModel categoryModel) {
            binding.setModel(categoryModel);
        }
    }
}
