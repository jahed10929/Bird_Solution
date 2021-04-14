package com.birdsolution.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.birdsolution.R;
import com.birdsolution.activity.ProductDetailActivity;
import com.birdsolution.model.CartListModel;
import com.birdsolution.model.ProductListModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CartListAdapter extends RecyclerView.Adapter<CartListAdapter.ViewHolder>{
    private List<CartListModel> categoryArrayList;
    private Activity activity;
    private String from;

    public CartListAdapter(Activity activity, List<CartListModel> categoryArrayList) {
        this.categoryArrayList = categoryArrayList;
        this.activity = activity;
        this.from = from;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_favitem_list,parent,false);
        return new CartListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final boolean[] fav = {true};
        String image = categoryArrayList.get(position).getImage();
        String name = categoryArrayList.get(position).getName();
        String price= categoryArrayList.get(position).getPrice();
        String dis = categoryArrayList.get(position).getShortDis();
        String mainDis = categoryArrayList.get(position).getDis();
        String favdata = categoryArrayList.get(position).getFav();

        holder.setFavDis(dis);
        holder.setFavPrice(categoryArrayList.get(position).getTotal());
        holder.setFavName(name);
        holder.setFavImage(image);
        if (favdata.equals("true"))
            holder.favImg.setImageResource(R.drawable.ic_favorite_product);
        else
            holder.favImg.setImageResource(R.drawable.ic_not_favorite_product);
        holder.imageThamb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fav[0]){
                    holder.favImg.setImageResource(R.drawable.ic_not_favorite_product);
                    fav[0] = false;
                }
                else {
                    holder.favImg.setImageResource(R.drawable.ic_favorite_product);
                    fav[0] = true;
                }

            }
        });
        holder.lytMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(activity, ProductDetailActivity.class);
                intent.putExtra("from", "cart");
                intent.putExtra("image", image);
                intent.putExtra("name", name);
                intent.putExtra("price", price);
                intent.putExtra("shortDis", dis);
                intent.putExtra("dis", mainDis);
                intent.putExtra("totalAmount", categoryArrayList.get(position).getTotal());
                intent.putExtra("totalProduct", categoryArrayList.get(position).getTotal_product());

                activity.startActivity(intent);
            }
        });




        Log.d("TAG ---------------", name+" "+image);
    }





    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView productImage, favImg;
        private TextView FavName;
        private TextView FavDis;
        private TextView FavPrice, txtstotal, txtdeliverycharge, txtsubtotal;
        RelativeLayout lytMain, lyttotal ;
        LinearLayout imageThamb;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lytMain = itemView.findViewById(R.id.lytmain);
            productImage = itemView.findViewById(R.id.imgThumb);
            FavName = itemView.findViewById(R.id.FavItemName);
            FavDis = itemView.findViewById(R.id.FavshortDis);
            FavPrice = itemView.findViewById(R.id.FavListPrice);
            favImg = itemView.findViewById(R.id.imgFav);
            imageThamb = itemView.findViewById(R.id.lytsave);
            lyttotal = itemView.findViewById(R.id.lyttotal);
            txtstotal = itemView.findViewById(R.id.txtstotal);
            txtdeliverycharge = itemView.findViewById(R.id.txtdeliverycharge);
            txtsubtotal = itemView.findViewById(R.id.txtsubtotal);
        }

        private void setFavImage(String image){
            //todo set category image
            Glide.with(productImage.getContext()).load(image).apply(new RequestOptions().placeholder(R.drawable.logo)).into(productImage);
        }
        private void setFavName(String name){
            //todo set category image
            FavName.setText(name);
        }
        private void setFavDis(String dis){
            FavDis.setText(dis);
            //todo set category image

        }
        private void setFavPrice(String price){
            //todo set category image
            FavPrice.setText("\u09F3"+price);
        }
    }

}
