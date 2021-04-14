package com.birdsolution.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.birdsolution.R;
import com.birdsolution.model.ProductListModel;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class SuggFoodViewActivity extends AppCompatActivity {
    boolean doubleBackToExitPressedOnce = false;

    Toolbar toolbar;

    ImageView suggViewImage;
    TextView suggViewName, suggViewDis;

    private String categoryName;
    View view;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugg_food_view);
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();
        categoryName = intent.getStringExtra("FoodSugName");

        suggViewImage = findViewById(R.id.suggViewImage);
        Glide.with(suggViewImage.getContext()).load(intent.getStringExtra("image"))
                .apply(new RequestOptions().placeholder(R.drawable.logo))
                .into(suggViewImage);
        suggViewName = findViewById(R.id.suggViewName);
        suggViewName.setText(intent.getStringExtra("image"));
        suggViewDis = findViewById(R.id.suggViewDis);
        suggViewDis.setText(intent.getStringExtra("dis"));
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