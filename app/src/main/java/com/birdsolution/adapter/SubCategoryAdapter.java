package com.birdsolution.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.birdsolution.R;
import com.birdsolution.model.SubCategoryModel;

import java.util.ArrayList;

public class SubCategoryAdapter extends RecyclerView.Adapter<com.birdsolution.adapter.SubCategoryAdapter.ViewHolder> {
    private ArrayList<SubCategoryModel> SubCategoryArrayList;

    public SubCategoryAdapter(ArrayList<SubCategoryModel> subCategoryArrayList) {

        SubCategoryArrayList = subCategoryArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_category_main,parent,false);
        return new com.birdsolution.adapter.SubCategoryAdapter.ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        int image = SubCategoryArrayList.get(position).getImage();
        String name = SubCategoryArrayList.get(position).getName();
        String id = SubCategoryArrayList.get(position).getId();
        holder.setSubCategoryImage(image);
        holder.setSubCategoryTittle(name);

    }

    @Override
    public int getItemCount() {return SubCategoryArrayList.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView categoryImage;
        private TextView categoryName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryTittle);
        }
        private void setSubCategoryImage(int image){
            //todo set category image
            categoryImage.setImageResource(image);
        }
        private void setSubCategoryTittle(String name){
            //todo set category image
            categoryName.setText(name);
        }
    }
}
