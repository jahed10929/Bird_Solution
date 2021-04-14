package com.birdsolution.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.birdsolution.R;
import com.birdsolution.adapter.MainCatagoryAdapter;
import com.birdsolution.model.Category;
import com.birdsolution.model.MainCatagoryModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CategoryActivity extends AppCompatActivity {

    //todo declear all global variable here
    boolean doubleBackToExitPressedOnce = false;

    Toolbar toolbar;

    View view;




    private RecyclerView categoryRecycleView;

    //todo Button global variable


    //todo catagory global variable
    private MainCatagoryAdapter catagoryAdapter;
    public static ArrayList<MainCatagoryModel> categoryArrayList;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Shop by Category");
        firebaseFirestore = FirebaseFirestore.getInstance();
        getCategoty();
    }
    private void getCategoty(){
        categoryArrayList = new ArrayList<>();
        categoryRecycleView = findViewById(R.id.categoryrecycleview);
        categoryRecycleView.setLayoutManager(new GridLayoutManager(CategoryActivity.this, 1));
        progressBar = findViewById(R.id.progressBar);
        GetCategory();
    }
    private void GetCategory(){

        ArrayList<MainCatagoryModel> List = new ArrayList<MainCatagoryModel>();

        catagoryAdapter = new MainCatagoryAdapter(CategoryActivity.this, List);
        categoryRecycleView.setAdapter(catagoryAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List.add(new MainCatagoryModel(document.get("Image").toString(),
                                        document.get("Name").toString()));
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                            catagoryAdapter.notifyDataSetChanged();
                            //progressBar.setVisibility(View.GONE);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });

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