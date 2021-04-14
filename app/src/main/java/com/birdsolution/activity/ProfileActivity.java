package com.birdsolution.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.birdsolution.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class ProfileActivity extends AppCompatActivity {
    Toolbar toolbar;
    ProgressBar progressBar;
    ImageView userImage, changeName, changeProfile, changeAddress, changeMobile;
    TextView userName, userEmail, userPass, userAddress, userMobile,noUdata;
    Button updateUserData;
    LinearLayout userDataLyt;


    String uName, uAddress, uMobile;
    String oldImage;
    boolean changeNameClicked = false;
    boolean changeAddressClicked = false;
    boolean changeMobileClicked = false;
    private static final int PICK_IMAGE = 1;
    Uri imageUri;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    private FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        initialized();
    }

    private void initialized() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Profile");

        progressBar = findViewById(R.id.progressBar);

        userImage = findViewById(R.id.userImage);
        userName = findViewById(R.id.userName);
        userAddress = findViewById(R.id.userAddress);
        userMobile = findViewById(R.id.userMobile);
        userEmail = findViewById(R.id.userEmail);
        userDataLyt = findViewById(R.id.userDataLyt);
        noUdata = findViewById(R.id.noUdata);
        updateUserData = findViewById(R.id.updateUserData);
        changeName = findViewById(R.id.changeName);
        changeProfile = findViewById(R.id.changeProfile);
        changeAddress = findViewById(R.id.changeAddress);
        changeMobile = findViewById(R.id.changeMobile);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
        setData();
    }
    private void setData() {
        if (mAuth.getCurrentUser() != null) {
            userDataLyt.setVisibility(View.VISIBLE);
            String userId = mAuth.getCurrentUser().getUid();
            DocumentReference docRef = firebaseFirestore.collection("Users").document(userId);
            docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                @Override
                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        try {
                            oldImage = document.get("profilePic").toString();
                            uName = document.get("name").toString();
                            uAddress = document.get("address").toString();
                            uMobile = document.get("mobile").toString();
                            userName.setText(document.get("name").toString());
                            Picasso.get()
                                    .load(document.get("profilePic").toString())
                                    .placeholder(R.drawable.ic_profile)
                                    .error(R.drawable.ic_profile)
                                    .into(userImage);
                            userEmail.setText(document.get("Email").toString());
                            userAddress.setText(uAddress);
                            userMobile.setText(uMobile);
                        }
                        catch (Exception e){
                            Log.d("TAg------",e.getMessage() );
                        }
                    } else {
                        Log.d("TAG", "Cached get failed: ", task.getException());
                    }
                }
            });
        } else {
            noUdata.setVisibility(View.VISIBLE);
        }
    }
    public void OnClickBtn(View view) {
        int id = view.getId();
        switch (id){
            case R.id.changeProfile:
                Log.d("Tag =========", " image clicked");
                openGallery();
                break;
            case R.id.userImage:
                Log.d("Tag =========", " image icon clicked");
                openGallery();
                break;
            case R.id.changeName:
                if (!changeNameClicked){
                    Log.d("Tag =========", " name clicked");
                    userName.setEnabled(true);
                    userName.setText("");
                    updateUserData.setVisibility(View.VISIBLE);
                    changeNameClicked = true;
                }
                else{
                    userName.setText(uName);
                    userName.setEnabled(false);
                    changeNameClicked = false;
                }
                break;
                case R.id.changeAddress:
                if (!changeAddressClicked){
                    Log.d("Tag =========", " name clicked");
                    userAddress.setEnabled(true);
                    userAddress.setText("");
                    updateUserData.setVisibility(View.VISIBLE);
                    changeNameClicked = true;
                }
                else{
                    userAddress.setText(uAddress);
                    userAddress.setEnabled(false);
                    changeAddressClicked = false;
                }
                break;
                case R.id.changeMobile:
                if (!changeMobileClicked){
                    Log.d("Tag =========", " name clicked");
                    userMobile.setEnabled(true);
                    userMobile.setText("");
                    updateUserData.setVisibility(View.VISIBLE);
                    changeMobileClicked = true;
                }
                else{
                    userMobile.setText(uMobile);
                    userMobile.setEnabled(false);
                    changeMobileClicked = false;
                }
                break;
            case R.id.updateUserData:
                Log.d("Tag =========", " update clicked");
                chackData();
                break;
        }
    }
    private void chackData() {
        if (imageUri!=null){
            updateAllData();
        }
        else {

        }

    }
    private void updateData() {
        String userId = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(userId);
        Map<String, Object> userData = new HashMap<>();
        userData.put("name", userName.getText().toString().toUpperCase());
        userData.put("profilePic", oldImage);
        userData.put("Email", userEmail.getText().toString());
        documentReference.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "User account has been created for: "+userId);
            }
        });

        progressBar.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(getApplicationContext(), MainActivity.class));
        finish();

    }
    private void updateAllData() {
        progressBar.setVisibility(View.VISIBLE);
        Uri file = imageUri;
        StorageReference storageRef = storage.getReference();
        StorageReference riversRef = storageRef.child("images/"+file.getLastPathSegment());
        riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imageLink = String.valueOf(uri);
                        String userId = mAuth.getCurrentUser().getUid();
                        DocumentReference documentReference = firebaseFirestore.collection("Users").document(userId);
                        Map<String, Object> userData = new HashMap<>();
                        userData.put("name", userName.getText().toString().toUpperCase());
                        userData.put("Email", userEmail.getText().toString().toUpperCase());
                        userData.put("mobile", userMobile.getText().toString().toUpperCase());
                        userData.put("address", userAddress.getText().toString().toUpperCase());
                        userData.put("profilePic", imageLink);
                        documentReference.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Log.d("TAG", "User account has been created for: "+userId);
                            }
                        });

                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                });
            }
        });
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            userImage.setImageURI(imageUri);
            updateUserData.setVisibility(View.VISIBLE);
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