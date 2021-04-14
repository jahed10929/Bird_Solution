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
import com.birdsolution.adapter.CatagoryAdapter;
import com.birdsolution.adapter.ProductListAdapter;
import com.birdsolution.adapter.SuggestionFoodAdapter;
import com.birdsolution.model.Category;
import com.birdsolution.model.ProductListModel;
import com.birdsolution.model.SuggestionFoodModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class SuggationActivity extends AppCompatActivity {
    //todo global variable
    public LinearLayout suggation_bottom_lyt,lytfloating_option;
    boolean doubleBackToExitPressedOnce = false;
    Menu menu;
    Toolbar toolbar;
    //ProgressBar progressBar;
    //NestedScrollView nestedScrollView;
    View view;
    private RecyclerView productRecyclerView;
    private SuggestionFoodAdapter productListAdapter;
    public static ArrayList<SuggestionFoodModel> productListModels;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;

    //todo Button global variable
    private Button cancel, eshop,doctor, suggetion;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggation);
        initialize();

    }


    //todo create apps all view initialized method
    private void initialize(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        productRecyclerView = findViewById(R.id.foodSuggetionRecycleView);
        productRecyclerView.setLayoutManager(new GridLayoutManager(SuggationActivity.this, 1));
        suggation_bottom_lyt = findViewById(R.id.lytsuggationBottom);
        lytfloating_option = findViewById(R.id.lytfloating_option);

        getCategoty();

    }

    private void getCategoty() {
        ArrayList<SuggestionFoodModel> List = new ArrayList<SuggestionFoodModel>();

        productListAdapter = new SuggestionFoodAdapter(SuggationActivity.this, List);
        productRecyclerView.setAdapter(productListAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Foods Suggestion")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                List.add(new SuggestionFoodModel(
                                        document.get("Image").toString(),
                                        document.get("Name").toString(),
                                        document.get("Description").toString()
                                        ));
                                Log.d("TAG", document.getId() + " => " + document.get("Name").toString());
                            }
                            productListAdapter.notifyDataSetChanged();
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                        }
                    }
                });
    }
    //todo onClick method
    public void OnClickBtn(View view) {
        int id = view.getId();
        switch (id){
            case R.id.lytfood:
                break;
            case R.id.lytMedicine:
                startActivity(new Intent(SuggationActivity.this, MedicineActivity.class));
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
                    startActivity(new Intent(SuggationActivity.this, DoctorActivity.class));
                    lytfloating_option.setVisibility(View.GONE);
                    suggation_bottom_lyt.setVisibility(View.VISIBLE);
                    break;
            case R.id.eshop:
                startActivity(new Intent(SuggationActivity.this, MainActivity.class));
                finish();
                break;
                case R.id.suggetion:
                    lytfloating_option.setVisibility(View.GONE);
                    suggation_bottom_lyt.setVisibility(View.VISIBLE);
                break;

        }
    }
}