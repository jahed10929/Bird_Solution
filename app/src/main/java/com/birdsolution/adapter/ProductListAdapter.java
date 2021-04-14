package com.birdsolution.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.birdsolution.R;
import com.birdsolution.activity.ProductDetailActivity;
import com.birdsolution.activity.activity_sugg_dis_view;
import com.birdsolution.model.Category;
import com.birdsolution.model.ProductListModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class ProductListAdapter extends RecyclerView.Adapter<ProductListAdapter.ViewHolder> {
    private List<ProductListModel> categoryArrayList;
    private Activity activity;
    private String from;
    public ProductListAdapter(Activity activity, List<ProductListModel> categoryArrayList, String from ) {
        this.categoryArrayList = categoryArrayList;
        this.activity = activity;
        this.from = from;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_item_list,parent,false);
        return new ProductListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String image = categoryArrayList.get(position).getImage();
        String name = categoryArrayList.get(position).getName();
        String price= categoryArrayList.get(position).getPrice();
        String dis = categoryArrayList.get(position).getShortDis();
        String mainDis = categoryArrayList.get(position).getDis();
        holder.setProductDis(dis);
        holder.setProductPrice(price);
        holder.setProductName(name);
        holder.setProductImage(image);
        holder.lytMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(activity, ProductDetailActivity.class);

                intent.putExtra("from", "productLIst");
                intent.putExtra("image", image);
                intent.putExtra("name", name);
                intent.putExtra("price", price);
                intent.putExtra("shortDis", dis);
                intent.putExtra("dis", mainDis);
                activity.startActivity(intent);
            }
        });
        Log.d("TAG ---------------", name+" "+image);
    }




    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView productImage;
        private TextView productName;
        private TextView productDis;
        private TextView productPrice;

        RelativeLayout lytMain ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lytMain = itemView.findViewById(R.id.lytmain);
            productImage = itemView.findViewById(R.id.imgThumb);
            productName = itemView.findViewById(R.id.productName);
            productDis = itemView.findViewById(R.id.shortDis);
            productPrice = itemView.findViewById(R.id.productPrice);

        }

        private void setProductImage(String image){
            //todo set category image
            Glide.with(productImage.getContext()).load(image).apply(new RequestOptions().placeholder(R.drawable.logo)).into(productImage);
        }
        private void setProductName(String name){
            //todo set category image
            productName.setText(name);
        }
        private void setProductDis(String dis){
           productDis.setText(dis);
            //todo set category image

        }
        private void setProductPrice(String price){
            //todo set category image
            productPrice.setText("\u09F3"+price);
        }
    }
}
