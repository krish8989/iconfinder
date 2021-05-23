package com.example.iconfinder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iconfinder.data.CategoryModel;
import com.example.iconfinder.databinding.LayoutCategoryItemBinding;
import com.example.iconfinder.listener.CategoryClickListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {
    private ArrayList<CategoryModel> categoryModels;
    private LayoutInflater inflater;
    private CategoryClickListener categoryClickListener;

    public CategoryAdapter() {

    }

    public void setCategoryClickListener(CategoryClickListener categoryClickListener) {
        this.categoryClickListener = categoryClickListener;
    }

    public void setCategoryModels(ArrayList<CategoryModel> categoryModels) {
        this.categoryModels = categoryModels;
//        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        LayoutCategoryItemBinding binding = LayoutCategoryItemBinding.inflate(inflater, parent, false);
        return new CategoryViewHolder(binding, categoryClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        holder.bind(categoryModels.get(position));
    }

    @Override
    public int getItemCount() {
        return categoryModels == null ? 0 : categoryModels.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {
        private final LayoutCategoryItemBinding binding;
        private final WeakReference<CategoryClickListener> categoryClickListenerWeakReference;

        CategoryViewHolder(@NonNull LayoutCategoryItemBinding binding, CategoryClickListener categoryClickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.categoryClickListenerWeakReference = new WeakReference<>(categoryClickListener);
            binding.categoryTextView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    categoryClickListenerWeakReference.get().categoryClicked(getBindingAdapterPosition());
                }
            });
        }

        void bind(CategoryModel categoryModel) {
            binding.setModel(categoryModel);
        }
    }
}
