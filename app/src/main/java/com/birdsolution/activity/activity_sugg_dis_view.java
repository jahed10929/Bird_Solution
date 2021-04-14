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
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class activity_sugg_dis_view extends AppCompatActivity {

    boolean doubleBackToExitPressedOnce = false;

    Toolbar toolbar;

    ImageView suggDisViewImage;
    TextView suggDisViewName, suggDisViewPrevention,suggDisViewSymptom,suggDisViewTreatment;

    private String categoryName;
    View view;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sugg_dis_view);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Intent intent = getIntent();

        suggDisViewImage = findViewById(R.id.suggDisViewImage);
        Glide.with(suggDisViewImage.getContext()).load(intent.getStringExtra("Image"))
                .apply(new RequestOptions().placeholder(R.drawable.logo))
                .into(suggDisViewImage);
        suggDisViewName = findViewById(R.id.suggDisViewName);
        suggDisViewName.setText(intent.getStringExtra("name"));
        suggDisViewPrevention = findViewById(R.id.suggDisViewPrevention);
        suggDisViewPrevention.setText(intent.getStringExtra("prevention"));
        suggDisViewSymptom = findViewById(R.id.suggDisViewSymptoms);
        suggDisViewSymptom.setText(intent.getStringExtra("symptoms"));
        suggDisViewTreatment = findViewById(R.id.suggDisViewTreatment);
        suggDisViewTreatment.setText(intent.getStringExtra("treatment"));
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