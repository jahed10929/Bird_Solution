package com.birdsolution.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.birdsolution.R;
import com.birdsolution.adapter.CatagoryAdapter;
import com.birdsolution.model.Category;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends DrawerActivity {
    //todo declear all global variable here
    boolean doubleBackToExitPressedOnce = false;
    Menu menu;
    Toolbar toolbar;
    ProgressBar progressBar;
    NestedScrollView nestedScrollView;
    View view;


    public LinearLayout lytBottom,lytfloating_option;
    public RelativeLayout layoutSearch;
    private RecyclerView categoryRecycleView,lytflashproduct,flushProductRecycleview;

    //todo Button global variable
    private Button cancel, eshop,doctor, suggetion;

    //todo catagory global variable
    private CatagoryAdapter catagoryAdapter;
    private List<Category> categoryList;


    // todo setup firebase global variable
    private FirebaseFirestore firebaseFirestore;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_main, frameLayout);


        //todo call initialized method
        initialize();


        //todo call catagory product method;
        getCategoty();



        //todo create nav drower
        drawerToggle = new ActionBarDrawerToggle
                (
                        this,
                        drawer, toolbar,
                        R.string.drawer_open,
                        R.string.drawer_close
                ) {};


    }
    //todo create apps all view initialized method
    private void initialize(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));

        progressBar = findViewById(R.id.progressBar);
        lytBottom = findViewById(R.id.lytBottom);
        layoutSearch = findViewById(R.id.layoutSearch);
        layoutSearch.setVisibility(View.VISIBLE);
        lytfloating_option = findViewById(R.id.lytfloating_option);

        nestedScrollView = findViewById(R.id.nestedScrollView);

        categoryRecycleView = findViewById(R.id.categoryRecycleView);

        // todo initialize category model


        //todo initialize firebase
        firebaseFirestore = FirebaseFirestore.getInstance();
    }



    private void getCategoty(){

        categoryList = new ArrayList<Category>();
        categoryRecycleView.setLayoutManager(new GridLayoutManager(MainActivity.this, 1));
        catagoryAdapter = new CatagoryAdapter(MainActivity.this, categoryList);
        categoryRecycleView.setAdapter(catagoryAdapter);

        firebaseFirestore = FirebaseFirestore.getInstance();
        firebaseFirestore.collection("Categories")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                categoryList.add(new Category(document.get("Image").toString(),
                                        document.get("Name").toString()));
                                Log.d("TAG", document.getId() + " => " + document.getData());
                            }
                            catagoryAdapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.GONE);
                        } else {
                            Log.d("TAG", "Error getting documents: ", task.getException());
                            progressBar.setVisibility(View.VISIBLE);
                        }
                    }
                });
    }
    //todo create menu option method here
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.menu = menu;
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }



    //todo this method for prepared header menu item
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        //menu.findItem(R.id.menu_search).setVisible(false);
        menu.findItem(R.id.menu_sort).setVisible(false);
        menu.findItem(R.id.menu_cart).setVisible(false);

        return super.onPrepareOptionsMenu(menu);
    }


    //todo this menu for get selected item from header menu item
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_cart:

                //  OpenCart();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    //todo create onclick listner method here
    public void OnClickBtn(View view) {
        int id = view.getId();
        if (id == R.id.lythome) {
            // todo start home activity
            startActivity(new Intent(MainActivity.this, MainActivity.class));
            finish();
        } else if (id == R.id.lytcategory) {
            // todo start category activity
            startActivity(new Intent(MainActivity.this, CategoryActivity.class));
        } else if (id == R.id.lytfav) {
            // todo start favorite activity
            startActivity(new Intent(MainActivity.this, FavouriteActivity.class));
        } /*else if (id == R.id.layoutSearch) {
            // todo start search activity
            startActivity(new Intent(MainActivity.this, SearchActivity.class));
        }*/ else if (id == R.id.lytcart) {
            startActivity(new Intent(MainActivity.this, CartActivity.class));
            // todo start cart activity
            //   OpenCart();
        }
        else if (id == R.id.lytfloatingbtn) {
            // todo start cart activity
            lytfloating_option.setVisibility(View.VISIBLE);
            lytBottom.setVisibility(View.INVISIBLE);
        }
        else if (id == R.id.cancel) {
            // todo start cart activity
            lytfloating_option.setVisibility(View.INVISIBLE);
            lytBottom.setVisibility(View.VISIBLE);
        }else if (id == R.id.eshop) {
            // todo start cart activity
            lytfloating_option.setVisibility(View.INVISIBLE);
            lytBottom.setVisibility(View.VISIBLE);
        }
        else if (id == R.id.doctor) {
            // todo start cart activity
            lytfloating_option.setVisibility(View.INVISIBLE);
            lytBottom.setVisibility(View.VISIBLE);
            startActivity(new Intent(MainActivity.this, DoctorActivity.class));

        }
        else if (id == R.id.suggetion) {
            // todo start cart activity
            startActivity(new Intent(MainActivity.this, SuggationActivity.class));
            finish();
        }
        else if(id == R.id.header_name){
            // todo goto main home page
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }


    //todo create back button pressed handler
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(navigationView))
            drawer.closeDrawers();
        else
            doubleBack();
    }



    //todo double back pressed for exit
    public void doubleBack() {

        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }
        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, getString(R.string.exit_msg), Toast.LENGTH_SHORT).show();
        new Handler().postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce = false;
            }
        }, 2000);
    }
}