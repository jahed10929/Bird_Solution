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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.birdsolution.R;
import com.birdsolution.adapter.CartListAdapter;
import com.birdsolution.adapter.FavListAdapter;
import com.birdsolution.model.CartListModel;
import com.birdsolution.model.FavListModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    private Toolbar toolbar;
    public ImageView productImage, imgFav;
    public TextView FavItemName , FavListPrice, FavshortDis, txtstotal, txtdeliverycharge, txtsubtotal;
    public LinearLayout lytsave, imgThumb, lytempty;
    public RelativeLayout lyttotal;
    float AllPrice = 0;
    float subTotal;
    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private Button btnShowNow;
    private CartListAdapter favListAdapter;
    public static ArrayList<CartListModel> favListModels;
    RecyclerView cartrecycleview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        initialized();
    }

    private void initialized() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cart");

        productImage = findViewById(R.id.imgThumb);
        imgFav = findViewById(R.id.imgFav);
        FavItemName = findViewById(R.id.FavItemName);
        FavListPrice = findViewById(R.id.FavListPrice);
        FavshortDis = findViewById(R.id.FavshortDis);
        cartrecycleview = findViewById(R.id.cartrecycleview);
        txtstotal = findViewById(R.id.txtstotal);
        txtdeliverycharge = findViewById(R.id.txtdeliverycharge);
        txtsubtotal = findViewById(R.id.txtsubtotal);
        lytempty = findViewById(R.id.lytempty);
        lyttotal = findViewById(R.id.lyttotal);
        btnShowNow = findViewById(R.id.btnShowNow);



        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();

        favListModels = new ArrayList<>();
        cartrecycleview.setLayoutManager(new GridLayoutManager(CartActivity.this, 1));
        getCartData();

        txtdeliverycharge.setText("60");


    }

    public void OnClickBtn(View view){
        int id = view.getId();
        switch (id){
            case R.id.checkOut:
                Log.d("Tag order========", "order click");
                startActivity(new Intent(CartActivity.this, OrderActivity.class));
                case R.id.btnShowNow:
                Log.d("Tag order========", "order click");
                startActivity(new Intent(CartActivity.this, MainActivity.class));
        }
    }
    private void getCartData() {

        String userId = mAuth.getCurrentUser().getUid();
        ArrayList<CartListModel> List = new ArrayList<CartListModel>();

        favListAdapter = new CartListAdapter(CartActivity.this, List);
        cartrecycleview.setAdapter(favListAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").document(userId)
                .collection("cart").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                /*lytempty.setVisibility(View.VISIBLE);
                                lyttotal.setVisibility(View.GONE);*/
                                if (!document.getId().toString().equals("initial")){
                                    if(document.get("status").toString().equals("true")){

                                        lyttotal.setVisibility(View.VISIBLE);
                                        AllPrice += Float.valueOf(document.get("total amount").toString());
                                        txtstotal.setText(String.valueOf(AllPrice));
                                        Log.d("Tag amount---------", String.valueOf(AllPrice));
                                        List.add(new CartListModel(
                                                document.get("name").toString(),
                                                document.get("shortDis").toString(),
                                                document.get("image").toString(),
                                                document.get("price").toString(),
                                                document.get("dis").toString(),
                                                document.get("status").toString(),
                                                document.get("fav").toString(),
                                                document.get("total amount").toString(),
                                                document.get("total product").toString()
                                        ));

                                    }

                                }

                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                            txtsubtotal.setText(String.valueOf(AllPrice+60));
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
                startActivity(new Intent(CartActivity.this, MainActivity.class));
                finish();
        }
        return true;
    }
}