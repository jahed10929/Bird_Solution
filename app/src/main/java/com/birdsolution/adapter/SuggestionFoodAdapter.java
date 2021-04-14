package com.birdsolution.adapter;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.birdsolution.R;
import com.birdsolution.activity.ProductListActivity;
import com.birdsolution.activity.SuggFoodViewActivity;
import com.birdsolution.model.ProductListModel;
import com.birdsolution.model.SuggestionFoodModel;

import java.util.List;

public class SuggestionFoodAdapter extends RecyclerView.Adapter<SuggestionFoodAdapter.ViewHolder> {
    private List<SuggestionFoodModel> categoryArrayList;
    Activity activity;

    public SuggestionFoodAdapter(Activity activity, List<SuggestionFoodModel> categoryArrayList ) {
        this.categoryArrayList = categoryArrayList;
        this.activity = activity;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lyt_suggestion_item_list,parent,false);
        return new SuggestionFoodAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String image = categoryArrayList.get(position).getImage();
        String name = categoryArrayList.get(position).getName();
        String dis = categoryArrayList.get(position).getDis();
        holder.setSuggestionName(name);
        holder.lytMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(activity, SuggFoodViewActivity.class);
                intent.putExtra("image", image);
                intent.putExtra("name", name);
                intent.putExtra("dis", dis);
                activity.startActivity(intent);
            }
        });
        Log.d("TAG sssss-------------", name);
    }

    @Override
    public int getItemCount() {
        return categoryArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout lytMain ;
        private TextView foodName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lytMain = itemView.findViewById(R.id.lyt_Food_Main);
            foodName = itemView.findViewById(R.id.bird_Food_Tittle);

        }
        private void setSuggestionName(String name){
            //todo set category image
            foodName.setText(name);
        }
    }
}
