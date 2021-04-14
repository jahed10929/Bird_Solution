package com.birdsolution.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.birdsolution.R;
import com.birdsolution.adapter.MainCatagoryAdapter;
import com.birdsolution.model.MainCatagoryModel;

import java.util.ArrayList;

public class SubCatagoryActivity extends AppCompatActivity {

    //todo declear all global variable here
    boolean doubleBackToExitPressedOnce = false;

    Toolbar toolbar;

    View view;

    private RecyclerView categoryRecycleView;

    //todo Button global variable


    //todo catagory global variable
    private MainCatagoryAdapter catagoryAdapter;
    public static ArrayList<MainCatagoryModel> categoryArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_catagory);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(getString(R.string.shopbySubcategory));
        getSubCategoty();
    }

    private void getSubCategoty() {
        categoryArrayList = new ArrayList<>();
        categoryRecycleView = findViewById(R.id.categoryrecycleview);
        categoryRecycleView.setLayoutManager(new GridLayoutManager(SubCatagoryActivity.this, 3));
        GetSubCategoty();
    }

    private void GetSubCategoty() {

        /*ArrayList<MainCatagoryModel> List = new ArrayList<MainCatagoryModel>();
        List.add(new MainCatagoryModel("1", R.drawable.jacobin, "Bird"));
        List.add(new MainCatagoryModel("2", R.drawable.mix_food, "Food"));
        List.add(new MainCatagoryModel("3", R.drawable.boltz, "Medicine"));List.add(new MainCatagoryModel("1", R.drawable.jacobin, "Bird"));
        List.add(new MainCatagoryModel("2", R.drawable.mix_food, "Food"));
        List.add(new MainCatagoryModel("3", R.drawable.boltz, "Medicine"));List.add(new MainCatagoryModel("1", R.drawable.jacobin, "Bird"));
        List.add(new MainCatagoryModel("2", R.drawable.mix_food, "Food"));
        List.add(new MainCatagoryModel("3", R.drawable.boltz, "Medicine"));List.add(new MainCatagoryModel("1", R.drawable.jacobin, "Bird"));
        List.add(new MainCatagoryModel("2", R.drawable.mix_food, "Food"));
        List.add(new MainCatagoryModel("3", R.drawable.boltz, "Medicine"));List.add(new MainCatagoryModel("1", R.drawable.jacobin, "Bird"));
        List.add(new MainCatagoryModel("2", R.drawable.mix_food, "Food"));
        List.add(new MainCatagoryModel("3", R.drawable.boltz, "Medicine"));List.add(new MainCatagoryModel("1", R.drawable.jacobin, "Bird"));
        List.add(new MainCatagoryModel("2", R.drawable.mix_food, "Food"));
        List.add(new MainCatagoryModel("3", R.drawable.boltz, "Medicine"));List.add(new MainCatagoryModel("1", R.drawable.jacobin, "Bird"));
        List.add(new MainCatagoryModel("2", R.drawable.mix_food, "Food"));
        List.add(new MainCatagoryModel("3", R.drawable.boltz, "Medicine"));List.add(new MainCatagoryModel("1", R.drawable.jacobin, "Bird"));
        List.add(new MainCatagoryModel("2", R.drawable.mix_food, "Food"));
        List.add(new MainCatagoryModel("3", R.drawable.boltz, "Medicine"));
        catagoryAdapter = new MainCatagoryAdapter(SubCatagoryActivity.this,List);
        categoryRecycleView.setAdapter(catagoryAdapter);
        catagoryAdapter.notifyDataSetChanged();*/

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}