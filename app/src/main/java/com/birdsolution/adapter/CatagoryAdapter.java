package com.birdsolution.adapter;


import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.birdsolution.R;
import com.birdsolution.activity.ProductListActivity;
import com.birdsolution.activity.SubCatagoryActivity;
import com.birdsolution.model.Category;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CatagoryAdapter extends RecyclerView.Adapter<com.birdsolution.adapter.CatagoryAdapter.ViewHolder>{
    private List<Category> categoryArrayList;
    Activity activity;
    public CatagoryAdapter(Activity activity, List<Category> categoryArrayList ) {
        this.categoryArrayList = categoryArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_category_main,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String image = categoryArrayList.get(position).getImage();
        String name = categoryArrayList.get(position).getName();
        holder.setCategoryImage(image);
        holder.setCategoryTittle(name);

        holder.lytMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(activity, ProductListActivity.class);
                intent.putExtra("CategoryName", name);
                activity.startActivity(intent);


            }
        });

    }



    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView categoryImage;
        private TextView categoryName;
        RelativeLayout lytMain ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lytMain = itemView.findViewById(R.id.lytMain);
            categoryImage = itemView.findViewById(R.id.categoryImage);
            categoryName = itemView.findViewById(R.id.categoryTittle);
        }
        private void setCategoryImage(String image){
            //todo set category image
            Glide.with(categoryImage.getContext()).load(image).apply(new RequestOptions().placeholder(R.drawable.logo)).into(categoryImage);
        }
        private void setCategoryTittle(String name){
            //todo set category image
            categoryName.setText(name);
        }
    }
}
