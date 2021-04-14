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
import android.widget.TextView;
import android.widget.Toast;

import com.birdsolution.R;
import com.birdsolution.adapter.CartListAdapter;
import com.birdsolution.adapter.OrderAdapter;
import com.birdsolution.model.CartListModel;
import com.birdsolution.model.OrderModel;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView orderAddress, orderMObile, orderProductPrice;
    private String uAddress, uMobile;
    float AllPrice = 0;
    private OrderAdapter orderAdapter;
    public static ArrayList<OrderModel> orderModels;
    RecyclerView orderRecycleview;
    private Button oderCon;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initialized();
    }

    private void initialized() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Order");

        orderAddress = findViewById(R.id.orderAddress);
        orderMObile = findViewById(R.id.orderMObile);
        orderProductPrice = findViewById(R.id.orderProductPrice);
        oderCon = findViewById(R.id.oderCon);

        orderRecycleview = findViewById(R.id.orderRecycleview);
        orderRecycleview.setLayoutManager(new GridLayoutManager(OrderActivity.this, 1));
        orderModels = new ArrayList<>();


        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        getCartData();
        getUser();

    }

    public void OnClickBtn(View view){
        int id = view.getId();
        switch (id){
            case R.id.oderCon:
                Log.d("Tag order========", "order click");
                confirmOrder();
                cleareCart();
        }
    }

    private void cleareCart() {
        String userId = mAuth.getCurrentUser().getUid();
        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").document(userId)
                .collection("cart").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if (!document.getId().toString().equals("initial")){
                                    firebaseFirestore.collection("Users").document(userId)
                                            .collection("cart").document(document.getId())
                                            .delete()
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Log.d("TAG", "DocumentSnapshot successfully deleted!");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Log.w("TAG", "Error deleting document", e);
                                                }
                                            });


                                }

                            }
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                            //progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void confirmOrder() {
        String userId = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore
                .collection("Users").document(userId)
                .collection("order").document();

        Map<String, Object> favProduct = new HashMap<>();
        favProduct.put("address", orderAddress.getText().toString());
        favProduct.put("mobile", orderMObile.getText().toString());
        favProduct.put("status", "true");
        documentReference.set(favProduct).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(getApplicationContext(), "Order Confirmed", Toast.LENGTH_LONG).show();
                Log.d("TAG", "fav added to : "+userId);
                startActivity(new Intent(OrderActivity.this, MainActivity.class));
            }
        });
    }


    private void getCartData() {
        String userId = mAuth.getCurrentUser().getUid();
        ArrayList<OrderModel> List = new ArrayList<OrderModel>();

        orderAdapter = new OrderAdapter(OrderActivity.this, List);
        orderRecycleview.setAdapter(orderAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Users").document(userId)
                .collection("cart").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {

                                if (!document.getId().toString().equals("initial")){
                                    if(document.get("status").toString().equals("true")){
                                        AllPrice += Float.valueOf(document.get("total amount").toString());
                                        ;
                                        List.add(new OrderModel(
                                                document.get("name").toString(),
                                                document.get("total amount").toString()
                                        ));
                                    }
                                    Log.d("TAG", document.getId() + " => " + document.getData());
                                }
                                orderAdapter.notifyDataSetChanged();
                                orderProductPrice.setText("\u09F3"+String.valueOf(AllPrice+60));
                            }
                            //progressBar.setVisibility(View.GONE);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                            //progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }

    private void getUser() {

        if (mAuth.getCurrentUser() != null) {
            String userId = mAuth.getCurrentUser().getUid();
            DocumentReference docRef = firebaseFirestore.collection("Users").document(userId);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        try {
                            orderAddress.setText(document.get("address").toString());
                            orderMObile.setText(document.get("mobile").toString());
                        }
                        catch (Exception e){
                            Log.d("TAg------",e.getMessage() );
                        }
                    } else {
                        Log.d("TAG", "Cached get failed: ", task.getException());
                    }
                }
            });
        }
    }


    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
        }
        return true;
    }
}