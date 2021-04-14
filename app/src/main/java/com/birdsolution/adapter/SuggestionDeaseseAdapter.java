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
import com.birdsolution.activity.SuggFoodViewActivity;
import com.birdsolution.activity.activity_sugg_dis_view;
import com.birdsolution.model.SuggestionDieaseModel;
import com.birdsolution.model.SuggestionFoodModel;

import java.util.List;

public class SuggestionDeaseseAdapter extends RecyclerView.Adapter<SuggestionDeaseseAdapter.ViewHolder> {
    private List<SuggestionDieaseModel> categoryArrayList;
    Activity activity;
    public SuggestionDeaseseAdapter(Activity activity, List<SuggestionDieaseModel> categoryArrayList ) {
        this.categoryArrayList = categoryArrayList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.medicine_view_lyt,parent,false);
        return new SuggestionDeaseseAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = categoryArrayList.get(position).getName();
        String prevention = categoryArrayList.get(position).getPrevention();
        String symptoms = categoryArrayList.get(position).getSymptoms();
        String treatment = categoryArrayList.get(position).getTreatment();
        String image = categoryArrayList.get(position).getImage();
        holder.setSuggestionDeaseseName(name);
        holder.lytMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                intent = new Intent(activity, activity_sugg_dis_view.class);
                intent.putExtra("name", name);
                intent.putExtra("prevention", prevention);
                intent.putExtra("symptoms", symptoms);
                intent.putExtra("treatment", treatment);
                intent.putExtra("Image", image);
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
        private TextView DisName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            lytMain = itemView.findViewById(R.id.lyt_Med_Main);
            DisName = itemView.findViewById(R.id.bird_medicine_Tittle);

        }
        private void setSuggestionDeaseseName(String name){
            //todo set category image
            DisName.setText(name);
    }
}
}
