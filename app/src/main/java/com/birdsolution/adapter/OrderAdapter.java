package com.birdsolution.adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.birdsolution.R;
import com.birdsolution.model.CartListModel;
import com.birdsolution.model.OrderModel;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {
    private List<OrderModel> orderModels;
    private Activity activity;

    public OrderAdapter(Activity activity, List<OrderModel> orderModels) {
        this.orderModels = orderModels;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_order_view,parent,false);
        return new OrderAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.orderProduct.setText(orderModels.get(position).getName());
        holder.orderProductPrice.setText(orderModels.get(position).getPrice());

    }

    @Override
    public int getItemCount() {
        return orderModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView orderProduct, orderProductPrice;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            orderProduct = itemView.findViewById(R.id.orderProduct);
            orderProductPrice = itemView.findViewById(R.id.orderProductPrice);
        }
    }
}
