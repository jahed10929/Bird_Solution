package com.birdsolution.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.birdsolution.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class LoginActivity extends AppCompatActivity {
    String userId, userName;
    private LinearLayout lytLogin,signUpLyt;
    private  TextView tvSignUp,tvGoToLongIn;
    private Toolbar toolbar;
    EditText loginId, loginPass,edtName, edtMail, edtPassword, edtConPassword, edtAddress, edtMobile;
    View view;
    ImageView userPic;

    private static final int PICK_IMAGE = 1;
    Uri imageUri;
    String imageId;
    String ImageUri="";
    ProgressBar progressBar;

    private FirebaseAuth mAuth;
    private FirebaseFirestore firebaseFirestore;
    FirebaseStorage storage;
    StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initialize();
        lytLogin.setVisibility(View.VISIBLE);
    }
    //todo initialized all use content
    private void initialize(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        progressBar = findViewById(R.id.progressBar);
        edtName = findViewById(R.id.edtName);
        edtMail = findViewById(R.id.edtMail);
        edtPassword = findViewById(R.id.edtPassword);
        edtConPassword = findViewById(R.id.edtConPassword);
        edtMobile = findViewById(R.id.edtMobile);
        edtAddress = findViewById(R.id.edtAddress);

        loginId = findViewById(R.id.loginId);
        loginPass = findViewById(R.id.loginPass);
        userPic = findViewById(R.id.userPic);

        lytLogin = findViewById(R.id.lytLogin);
        signUpLyt = findViewById(R.id.signUpLyt);

        mAuth = FirebaseAuth.getInstance();
        firebaseFirestore = FirebaseFirestore.getInstance();
        storage = FirebaseStorage.getInstance();
    }

    public void OnBtnClick(View view) {
        int id = view.getId();
        switch (view.getId()) {
            case R.id.tvSignUp:
                signUpLyt.setVisibility(View.VISIBLE);
                lytLogin.setVisibility(View.GONE);
                break;
            case R.id.tvGoToLogin:
                signUpLyt.setVisibility(View.GONE);
                lytLogin.setVisibility(View.VISIBLE);
                break;
            case R.id.btnSubmit:
                singUp();
                break;
            case R.id.btnLogin:
                singIn();
                break;
                case R.id.userPic:
                getImage();
                break;
        }
    }

    private void getImage() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imageUri = data.getData();
            ImageUri = String.valueOf(imageUri);
            userPic.setImageURI(imageUri);

        }
    }
    private void singIn() {
        String email = loginId.getText().toString();
        String password = loginPass.getText().toString();

        if(verifyLoginData(email, password)){
            mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"something wrong",Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }
    private boolean verifyLoginData(String email, String password) {
        if(email.isEmpty()){
            loginId.setError("Email is empty");
            return false;
        }
        else if (!isEmailValid(email)){
            loginId.setError("Email is not valid");
            return false;
        }
        else if(password.isEmpty()){
            loginPass.setError("Password is empty");
            return false;
        }
        else if(password.length()<6){
            loginPass.setError("Invalid password");
            return false;
        }

        return true;
    }
    private void singUp() {
        Log.d("Tag---------------", String.valueOf(imageUri));
        String name = edtName.getText().toString();
        String email = edtMail.getText().toString();
        String address = edtAddress.getText().toString();
        String mobile = edtMobile.getText().toString();
        String password = edtPassword.getText().toString();
        String confirmPass = edtConPassword.getText().toString();


        if(verifyRegData(ImageUri, name, email, password, confirmPass, mobile, address)){
            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        //addImage();
                        Uri file = imageUri;
                        StorageReference storageRef = storage.getReference();
                        StorageReference riversRef = storageRef.child("user image/"+file.getLastPathSegment());
                        riversRef.putFile(file).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                riversRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        String imageLink = String.valueOf(uri);
                                        userId = mAuth.getCurrentUser().getUid();
                                        DocumentReference documentReference = firebaseFirestore.collection("Users").document(userId);
                                        Map<String, Object> userData = new HashMap<>();
                                        userData.put("name", name);
                                        userData.put("Email", email);
                                        userData.put("Password", confirmPass);
                                        userData.put("mobile", mobile);
                                        userData.put("address", address);
                                        userData.put("profilePic", imageLink);
                                        documentReference.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void aVoid) {
                                                Log.d("TAG", "User account has been created for: "+userId);
                                            }
                                        });
                                        DocumentReference documentReference2 = firebaseFirestore.collection("Users").document(userId).collection("favourite").document("initial");
                                        DocumentReference documentReference3 = firebaseFirestore.collection("Users").document(userId).collection("cart").document("initial");
                                        DocumentReference documentReference4 = firebaseFirestore.collection("Users").document(userId).collection("order").document("initial");
                                        documentReference2.set(new HashMap<String, Object>());
                                        documentReference3.set(new HashMap<String, Object>());
                                        documentReference4.set(new HashMap<String, Object>());
                                        Log.d("Tag IMage Link ", imageLink);
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(getApplicationContext(), "Account Created", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                        finish();
                                    }
                                });
                            }
                        });



                        /*addToDatabase(name, email, confirmPass, mobile, address);
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                        finish();*/
                    }
                    else {
                        Toast.makeText(getApplicationContext(),"something wrong",Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    private void addImage() {
        imageId = UUID.randomUUID().toString();
        ProgressDialog progressDialog
                = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();
        StorageReference ref = storageReference.child("images/" + imageId);
        ref.putFile(imageUri).addOnSuccessListener(
                        new OnSuccessListener<UploadTask.TaskSnapshot>() {

                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                // Image uploaded successfully
                                // Dismiss dialog
                                progressDialog.dismiss();
                                Toast.makeText(LoginActivity.this, "Image Uploaded!!", Toast.LENGTH_SHORT).show();
                            }
                        })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {

                        // Error, Image not uploaded
                        progressDialog.dismiss();
                        Toast
                                .makeText(LoginActivity.this,
                                        "Failed " + e.getMessage(),
                                        Toast.LENGTH_SHORT)
                                .show();
                    }
                })
                .addOnProgressListener(
                        new OnProgressListener<UploadTask.TaskSnapshot>() {

                            // Progress Listener for loading
                            // percentage on the dialog box
                            @Override
                            public void onProgress(
                                    UploadTask.TaskSnapshot taskSnapshot)
                            {
                                double progress
                                        = (100.0
                                        * taskSnapshot.getBytesTransferred()
                                        / taskSnapshot.getTotalByteCount());
                                progressDialog.setMessage(
                                        "Uploaded "
                                                + (int)progress + "%");
                            }
                        });
    }

    private void addToDatabase(String name, String email, String confirmPass, String mobile, String address) {
        userId = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = firebaseFirestore.collection("Users").document(userId);

        Map<String, Object> userData = new HashMap<>();
        userData.put("name", name);
        userData.put("Email", email);
        userData.put("Password", confirmPass);
        userData.put("mobile", mobile);
        userData.put("address", address);
        documentReference.set(userData).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Log.d("TAG", "User account has been created for: "+userId);
            }
        });
        DocumentReference documentReference2 = firebaseFirestore.collection("Users").document(userId).collection("favourite").document("initial");
        DocumentReference documentReference3 = firebaseFirestore.collection("Users").document(userId).collection("cart").document("initial");
        DocumentReference documentReference4 = firebaseFirestore.collection("Users").document(userId).collection("order").document("initial");
        documentReference2.set(new HashMap<String, Object>());
        documentReference3.set(new HashMap<String, Object>());
        documentReference4.set(new HashMap<String, Object>());

    }
    private boolean verifyRegData(String imageUri, String name, String email, String password, String confirmPass, String mobile, String address) {

        if(name.isEmpty()){
            edtName.setError("Name is empty");
            return false;
        }
        else if(address.isEmpty()){
            edtName.setError("Address is empty");
            return false;
        }
        else if(email.isEmpty()){
            edtMail.setError("Email is empty");
            return false;
        }
        else if(mobile.isEmpty()){
            edtName.setError("Mobile is empty");
            return false;
        }
        else if(mobile.length()<11){
            edtMail.setError("Invalid MObile number");
            return false;
        }
        else if (!isEmailValid(email)){
            edtMail.setError("Email is not valid");
            return false;
        }
        else if(password.isEmpty()){
            edtPassword.setError("Password is empty");
            return false;
        }
        else if(password.length()<6){
            edtPassword.setError("Password must be 6 character long");
            return false;
        }
        else if(confirmPass.isEmpty()){
            edtConPassword.setError("Confirm password");
            return false;
        }
        else if (!password.equals(confirmPass)){
            edtConPassword.setError("password not matched");
            return false;
        }

        return true;
    }

    private boolean isEmailValid(String email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
                finish();
                //onBackPressed();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}