package com.birdsolution.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.birdsolution.R;
import com.birdsolution.adapter.MainCatagoryAdapter;
import com.birdsolution.adapter.ProductListAdapter;
import com.birdsolution.model.Category;
import com.birdsolution.model.MainCatagoryModel;
import com.birdsolution.model.ProductListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {
    //todo declear all global variable here
    boolean doubleBackToExitPressedOnce = false;

    Toolbar toolbar;

    View view;

    private RecyclerView productRecyclerView;

    //todo Button global variable


    //todo catagory global variable
    private ProductListAdapter productListAdapter;
    public static ArrayList<ProductListModel> productListModels;

    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    private String categoryName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        categoryName = intent.getStringExtra("CategoryName");

        getSupportActionBar().setTitle(categoryName);
        getProduct();
    }

    private void getProduct() {
        productListModels = new ArrayList<>();
        productRecyclerView = findViewById(R.id.productRecyclerView);
        productRecyclerView.setLayoutManager(new GridLayoutManager(ProductListActivity.this, 1));
        GetProduct();
    }

    private void GetProduct() {
        ArrayList<ProductListModel> List = new ArrayList<ProductListModel>();

        productListAdapter = new ProductListAdapter(ProductListActivity.this, List, categoryName);
        productRecyclerView.setAdapter(productListAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection(categoryName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List.add(new ProductListModel(

                                        document.get("Name").toString(),
                                        document.get("short description").toString(),
                                        document.get("Image").toString(),
                                        document.get("Price").toString(),
                                        document.get("Description").toString()

                                        ));
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                            productListAdapter.notifyDataSetChanged();
                            //progressBar.setVisibility(View.GONE);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                            //progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });

        /*ArrayList<ProductListModel> List = new ArrayList<ProductListModel>();

        productListAdapter = new ProductListAdapter(ProductListActivity.this,List);
        productRecyclerView.setAdapter(productListAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection(categoryName)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                List.add(new ProductListModel(document.get("Name").toString(),
                                        document.get("short description").toString(),
                                        document.get("Image").toString(),
                                        document.get("Price").toString()
                                        ));

                                Log.d("TAG", document.getId() + " => " + document.getData());

                            }
                            productListAdapter.notifyDataSetChanged();
                            //progressBar.setVisibility(View.GONE);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                            //progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });*/
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