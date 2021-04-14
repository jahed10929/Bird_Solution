package com.birdsolution.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.birdsolution.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;
    public ImageView productImage, imgFav, btnminusqty, btnaddqty;
    public TextView textFav, txtproductname , txtprice, txtqty, txtDescription, txtMeasurement, btncart, txtTotalPrice;
    public LinearLayout lytsave, lytqty, lytqty1;

    String name;
    String image;
    String price;
    String qntity;
    String dis ;
    String totalP ;
    String total ;
    String favStatus ;

    private int count=0;
    private long totalAmount=0;
    private boolean save= false;
    ColorStateList oldColors;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        initialized();
    }

    private void initialized() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lytqty = findViewById(R.id.lytqty);
        productImage = findViewById(R.id.productImage);
        imgFav = findViewById(R.id.imgFav);
        if (save)
            imgFav.setImageResource(R.drawable.ic_favorite_product);
        else
            imgFav.setImageResource(R.drawable.ic_not_favorite_product);
        btnminusqty = findViewById(R.id.btnminusqty);
        btnaddqty = findViewById(R.id.btnaddqty);
        textFav = findViewById(R.id.textFav);
        txtproductname = findViewById(R.id.txtproductname);
        txtprice = findViewById(R.id.txtprice);
        txtqty= findViewById(R.id.txtqty);
        txtDescription= findViewById(R.id.txtDescription);
        txtMeasurement= findViewById(R.id.txtMeasurement);
        btncart = findViewById(R.id.btncart);
        lytqty1 = findViewById(R.id.lytqty1);
        txtTotalPrice = findViewById(R.id.txtTotalPrice);

        oldColors =  textFav.getTextColors();

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        getProduct();



    }

    private void getProduct() {

        Intent intent = getIntent();
        if (intent.getStringExtra("from").equals("fav")){
            name = intent.getStringExtra("name");
            favStatus = intent.getStringExtra("status");
            if (favStatus.equals(true))
                save = true;
            else
                save=false;
            image = intent.getStringExtra("image");
            price = intent.getStringExtra("price");
            qntity = intent.getStringExtra("shortDis");
            dis = intent.getStringExtra("dis");
            Glide.with(productImage.getContext()).load(image)
                    .apply(new RequestOptions().placeholder(R.drawable.logo))
                    .into(productImage);
            txtproductname.setText(name);
            txtDescription.setText(dis);
            txtprice.setText("\u09F3"+price);
            txtMeasurement.setText(qntity);
        }
        else if (intent.getStringExtra("from").equals("cart")){
            totalP = intent.getStringExtra("totalProduct");
            total = intent.getStringExtra("totalAmount");
            name = intent.getStringExtra("name");
            image = intent.getStringExtra("image");
            price = intent.getStringExtra("price");
            qntity = intent.getStringExtra("shortDis");
            dis = intent.getStringExtra("dis");
            Glide.with(productImage.getContext()).load(image)
                    .apply(new RequestOptions().placeholder(R.drawable.logo))
                    .into(productImage);
            txtproductname.setText(name);
            txtDescription.setText(dis);
            txtprice.setText("\u09F3"+price);
            txtMeasurement.setText(qntity);
            count = Integer.valueOf(totalP);
            txtqty.setText(String.valueOf(count));
            txtTotalPrice.setText(total);
            txtTotalPrice.setVisibility(View.VISIBLE);
        }
        else if (intent.getStringExtra("from").equals("productLIst")){
            name = intent.getStringExtra("name");
            image = intent.getStringExtra("image");
            price = intent.getStringExtra("price");
            qntity = intent.getStringExtra("shortDis");
            dis = intent.getStringExtra("dis");
            Glide.with(productImage.getContext()).load(image)
                    .apply(new RequestOptions().placeholder(R.drawable.logo))
                    .into(productImage);
            txtproductname.setText(name);
            txtDescription.setText(dis);
            txtprice.setText("\u09F3"+price);
            txtMeasurement.setText(qntity);
        }


    }
    public void OnBtnClick(View view) {
        int id = view.getId();

        switch (id) {

            case R.id.lytsave:
                chackUser();
                break;
            case R.id.btncart:
                checkUserForCart();
                break;

            case R.id.btnminusqty:

                count--;
                setQuantity();
                break;
            case R.id.btnaddqty:
                count++;
                setQuantity();
                break;
        }

    }

    //todo check User For Cart
    private void checkUserForCart() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            storeFavData();
            storeCartData();
            Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
            startActivity(intent);

        } else {
            Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class);
            startActivity(intent);
            // No user is signed in
        }
    }

    private void storeCartData() {
        String userId = mAuth.getCurrentUser().getUid();
        Log.d("TAG-------------", "fav added to : "+name);
        Log.d("TAG-------------", "fav added to : "+image);
        Log.d("TAG-------------", "fav added to : "+dis);
        Log.d("TAG-------------", "fav added to : "+price);
        Log.d("TAG-------------", "fav added to : "+qntity);
        Log.d("TAG-------------", "fav added to : "+userId);

        DocumentReference documentReference = firebaseFirestore
                .collection("Users").document(userId)
                .collection("cart").document(name);

        Map<String, Object> favProduct = new HashMap<>();
        favProduct.put("name", name);
        favProduct.put("image", image);
        favProduct.put("dis", dis);
        favProduct.put("shortDis", qntity);
        favProduct.put("price", price);
        favProduct.put("total amount", totalAmount);
        favProduct.put("total product", count);
        if (count>0)
            favProduct.put("status", "true");
        else
            favProduct.put("status", "false");
        if (save){
            favProduct.put("fav", "true");
        }
        else
            favProduct.put("fav", "false");

        documentReference.set(favProduct).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "fav added to : "+userId);
            }
        });
    }

    private void storeFavData() {
        if (save){
            String userId = mAuth.getCurrentUser().getUid();
            Log.d("TAG-------------", "fav added to : "+name);
            Log.d("TAG-------------", "fav added to : "+image);
            Log.d("TAG-------------", "fav added to : "+dis);
            Log.d("TAG-------------", "fav added to : "+price);
            Log.d("TAG-------------", "fav added to : "+qntity);
            Log.d("TAG-------------", "fav added to : "+userId);

            DocumentReference documentReference = firebaseFirestore
                    .collection("Users").document(userId)
                    .collection("favourite").document(name);

            Map<String, Object> favProduct = new HashMap<>();
            favProduct.put("name", name);
            favProduct.put("image", image);
            favProduct.put("dis", dis);
            favProduct.put("shortDis", qntity);
            favProduct.put("price", price);
            favProduct.put("status", "true");
            documentReference.set(favProduct).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("TAG", "fav added to : "+userId);
                }
            });
        }
        else{
            String userId = mAuth.getCurrentUser().getUid();
            Log.d("TAG-------------", "fav added to : "+name);
            Log.d("TAG-------------", "fav added to : "+image);
            Log.d("TAG-------------", "fav added to : "+dis);
            Log.d("TAG-------------", "fav added to : "+price);
            Log.d("TAG-------------", "fav added to : "+qntity);
            Log.d("TAG-------------", "fav added to : "+userId);

            DocumentReference documentReference = firebaseFirestore
                    .collection("Users").document(userId)
                    .collection("favourite").document(name);

            Map<String, Object> favProduct = new HashMap<>();
            favProduct.put("name", name);
            favProduct.put("image", image);
            favProduct.put("dis", dis);
            favProduct.put("shortDis", qntity);
            favProduct.put("price", price);
            favProduct.put("status", "false");
            documentReference.set(favProduct).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    Log.d("TAG", "fav added to : "+userId);
                }
            });
        }
    }

    //todo check user logged in or not
    private void chackUser(){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            // User is signed in
            if (!save){
                imgFav.setImageResource(R.drawable.ic_favorite_product);
                textFav.setTypeface(textFav.getTypeface(), Typeface.BOLD);
                textFav.setTextColor(Color.parseColor("#000000"));
                save = true;
            }
            else {
                imgFav.setImageResource(R.drawable.ic_not_favorite_product);
                textFav.setTypeface(textFav.getTypeface(), Typeface.NORMAL);
                textFav.setTextColor(oldColors);
                save = false;
            }
        } else {
            Intent intent = new Intent(ProductDetailActivity.this, LoginActivity.class);
            startActivity(intent);
            // No user is signed in
        }
    }




    //todo get cart Quantity
    private void setQuantity() {
        if(count>=0){
            totalAmount = count*Integer.valueOf(price);
            txtqty.setText(String.valueOf(count));
            btncart.setVisibility(View.VISIBLE);
            lytqty1.setVisibility(View.VISIBLE);
            txtTotalPrice.setText("\u09F3"+String.valueOf(totalAmount));
        }

        else{
            txtqty.setText("0");
            btncart.setVisibility(View.GONE);
            lytqty1.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                storeFavData();
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onBackPressed(){
        if(count>0){
            doubleBack();
        }
        else{
            storeFavData();
            super.onBackPressed();
        }

    }

    private void doubleBack() {
        new AlertDialog.Builder(this)
                .setTitle("Exit cart?")
                .setMessage("Cart will not saved")
                .setNegativeButton(android.R.string.no, null)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface arg0, int arg1) {
                        ProductDetailActivity.super.onBackPressed();
                    }
                }).create().show();
    }
}