package com.birdsolution.activity;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.birdsolution.R;

public class categoryViewHolder extends RecyclerView.ViewHolder {
    ImageView categoryImage;
    TextView categoryName;
    public categoryViewHolder(@NonNull View itemView) {
        super(itemView);
        categoryImage = itemView.findViewById(R.id.categoryImage);
        categoryName = itemView.findViewById(R.id.categoryTittle);
    }
}
