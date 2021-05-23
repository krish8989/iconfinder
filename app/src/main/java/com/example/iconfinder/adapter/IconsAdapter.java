package com.example.iconfinder.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.iconfinder.data.Icon;
import com.example.iconfinder.databinding.LayoutIconItemBinding;
import com.example.iconfinder.listener.CategoryClickListener;
import com.example.iconfinder.listener.IconDownloadListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

public class IconsAdapter extends RecyclerView.Adapter<IconsAdapter.IconsViewHolder> {
    private ArrayList<Icon> icons;
    private LayoutInflater layoutInflater;
    private IconDownloadListener iconDownloadListener;

    public IconsAdapter() {

    }

    public void setIconDownloadListener(IconDownloadListener iconDownloadListener) {
        this.iconDownloadListener = iconDownloadListener;
    }

    public void setIcons(ArrayList<Icon> icons) {
        this.icons = icons;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public IconsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater == null) {
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        LayoutIconItemBinding binding = LayoutIconItemBinding.inflate(layoutInflater, parent, false);
        return new IconsViewHolder(binding, iconDownloadListener);
    }

    @Override
    public void onBindViewHolder(@NonNull IconsViewHolder holder, int position) {
        holder.bind(icons.get(position));
    }

    Icon getIcon(int position) {
        return icons.get(position);
    }

    @Override
    public int getItemCount() {
        return icons == null ? 0 : icons.size();
    }

    public class IconsViewHolder extends RecyclerView.ViewHolder {
        private LayoutIconItemBinding binding;
        private final WeakReference<IconDownloadListener> iconDownloadListenerWeakReference;

        public IconsViewHolder(@NonNull LayoutIconItemBinding binding, IconDownloadListener iconDownloadListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.iconDownloadListenerWeakReference = new WeakReference<>(iconDownloadListener);
            binding.downloadButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Icon icon = getIcon(getBindingAdapterPosition());
                    String url = icon.getRasterSizes().get(icon.getRasterSizes().size() - 1).getFormats().get(0).getDownloadUrl();
                    String format = icon.getRasterSizes().get(icon.getRasterSizes().size() - 1).getFormats().get(0).getFormat();
                    iconDownloadListenerWeakReference.get().downloadIcon(url, icon.getIconId(), format);
                }
            });
        }

        public void bind(Icon icon) {
            binding.setModel(icon);
        }
    }
}
