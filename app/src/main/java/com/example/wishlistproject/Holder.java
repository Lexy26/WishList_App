package com.example.wishlistproject;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wishlistproject.databinding.ListItemBinding;

public class Holder extends RecyclerView.ViewHolder {

    public ListItemBinding binding;

    public Holder(@NonNull View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }
}
