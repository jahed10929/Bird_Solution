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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.birdsolution.R;
import com.birdsolution.adapter.FavListAdapter;
import com.birdsolution.adapter.ProductListAdapter;
import com.birdsolution.model.FavListModel;
import com.birdsolution.model.ProductListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import static org.jitsi.meet.sdk.JitsiMeetActivityDelegate.onBackPressed;

public class FavouriteActivity extends AppCompatActivity {

    private Toolbar toolbar;
    public ImageView productImage, imgFav;
    public TextView FavItemName , FavListPrice, FavshortDis, empyFav, txtnodata;
    public LinearLayout lytsave, imgThumb;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    private FavListAdapter favListAdapter;
    public static ArrayList<FavListModel> favListModels;

    RecyclerView favrecycleview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);


        initialized();
    }

    private void initialized() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Favourite LIst");

        productImage = findViewById(R.id.imgThumb);
        imgFav = findViewById(R.id.imgFav);
        txtnodata = findViewById(R.id.txtnodata);
        FavItemName = findViewById(R.id.FavItemName);
        FavListPrice = findViewById(R.id.FavListPrice);
        FavshortDis = findViewById(R.id.FavshortDis);
        favrecycleview = findViewById(R.id.favrecycleview);
        empyFav = findViewById(R.id.empyFav);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        favListModels = new ArrayList<>();
        favrecycleview.setLayoutManager(new GridLayoutManager(FavouriteActivity.this, 1));
        getFavData();
    }

    private void getFavData() {
        String userId = mAuth.getCurrentUser().getUid();
        ArrayList<FavListModel> List = new ArrayList<FavListModel>();

        favListAdapter = new FavListAdapter(FavouriteActivity.this, List);
        favrecycleview.setAdapter(favListAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").document(userId)
                .collection("favourite").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                empyFav.setVisibility(View.VISIBLE);
                                if (!document.getId().toString().equals("initial")){
                                    if(document.get("status").toString().equals("true")){
                                       empyFav.setVisibility(View.GONE);
                                        List.add(new FavListModel(
                                                document.get("name").toString(),
                                                document.get("shortDis").toString(),
                                                document.get("image").toString(),
                                                document.get("price").toString(),
                                                document.get("dis").toString(),
                                                document.get("status").toString()
                                        ));
                                    }
                                }

                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                            favListAdapter.notifyDataSetChanged();
                            //progressBar.setVisibility(View.GONE);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                            //progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(FavouriteActivity.this, MainActivity.class));
                finish();
        }
        return true;
    }
}