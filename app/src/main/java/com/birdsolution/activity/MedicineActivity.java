package com.birdsolution.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.birdsolution.R;
import com.birdsolution.adapter.SuggestionDeaseseAdapter;
import com.birdsolution.adapter.SuggestionFoodAdapter;
import com.birdsolution.model.SuggestionDieaseModel;
import com.birdsolution.model.SuggestionFoodModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class MedicineActivity extends AppCompatActivity {
    //todo global variable
    public LinearLayout suggation_bottom_lyt,lytfloating_option;
    boolean doubleBackToExitPressedOnce = false;
    Menu menu;
    Toolbar toolbar;
    //ProgressBar progressBar;
    //NestedScrollView nestedScrollView;
    View view;
    private RecyclerView productRecyclerView;
    private SuggestionDeaseseAdapter productListAdapter;
    public static ArrayList<SuggestionDieaseModel> productListModels;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    //todo Button global variable
    private Button cancel, eshop,doctor, suggetion;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine);
        initialize();
    }

    //todo create apps all view initialized method
    private void initialize(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        productRecyclerView = findViewById(R.id.deaseseSuggetionRecycleView);
        productRecyclerView.setLayoutManager(new GridLayoutManager(MedicineActivity.this, 1));
        suggation_bottom_lyt = findViewById(R.id.lytsuggationBottom);
        lytfloating_option = findViewById(R.id.lytfloating_option);

        getCategoty();

    }

    private void getCategoty() {
        ArrayList<SuggestionDieaseModel> List = new ArrayList<SuggestionDieaseModel>();

        productListAdapter = new SuggestionDeaseseAdapter(MedicineActivity.this, List);
        productRecyclerView.setAdapter(productListAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Diseases")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List.add(new SuggestionDieaseModel(
                                        document.get("Name").toString(),
                                        document.get("Prevention").toString(),
                                        document.get("Symptoms").toString(),
                                        document.get("Treatment").toString(),
                                        document.get("Image").toString()
                                ));
                                Log.d("TAG", document.getId() + " => " + document.get("Name").toString());
                            }
                            productListAdapter.notifyDataSetChanged();
                            //progressBar.setVisibility(View.GONE);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                            //progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }


    //todo onClick method
    public void OnClickBtn(View view) {
        int id = view.getId();
        switch (id){
            case R.id.lytfood:
                startActivity(new Intent(MedicineActivity.this, SuggationActivity.class));
                break;
            case R.id.lytMedicine:

                Log.d("Tag food---------------", "food clicked");
                break;
            case R.id.lytfloatingbtn:
                lytfloating_option.setVisibility(View.VISIBLE);
                suggation_bottom_lyt.setVisibility(View.INVISIBLE);
                Log.d("Tag food---------------", "float clicked");
                break;
            case R.id.cancel:
                lytfloating_option.setVisibility(View.GONE);
                suggation_bottom_lyt.setVisibility(View.VISIBLE);
                break;
            case R.id.doctor:
                startActivity(new Intent(MedicineActivity.this, DoctorActivity.class));
                break;
            case R.id.eshop:
                startActivity(new Intent(MedicineActivity.this, MainActivity.class));
                finish();
                break;
            case R.id.suggetion:
                lytfloating_option.setVisibility(View.GONE);
                suggation_bottom_lyt.setVisibility(View.VISIBLE);
                break;

        }
    }
}