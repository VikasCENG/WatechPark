package com.example.watechpark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity {
    private EditText editFullName, editPhone, editEmail,editUserName, editPassword;
    private TextView textCreateAccount, textReg, textFullName, textPhone, textEmail, textUserName, textPassword, textPolicy;
    private Button signUp;
    private CircleImageView profilePic;
    private Toolbar toolBar;
    private CheckBox box;

    private FirebaseAuth mAuth;
    private FirebaseStorage firebaseStorage;

    private ProgressBar progressBar;
    private Uri image;
    private static int SELECT_IMAGE = 1000;
    private StorageReference storageReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findAllViews();

        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();

        storageReference = firebaseStorage.getReference();

        profilePic.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "Select Profile Image"), SELECT_IMAGE);


            }
        });

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerValidate();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == SELECT_IMAGE && resultCode == RESULT_OK && data.getData() != null){
         image = data.getData();
         try {
             Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
             profilePic.setImageBitmap(bitmap);


         } catch (IOException e) {
             e.printStackTrace();

         }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(getApplicationContext(), LoginRegister.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }



    private void registerValidate() {
        final String fullName = editFullName.getText().toString().trim();
        final String phone = editPhone.getText().toString().trim();
        final String email = editEmail.getText().toString().trim();
        final String username = editUserName.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();
        final Long time = System.currentTimeMillis()/1000;
        final String timestamp = time.toString();

        if (fullName.isEmpty()) {
            editFullName.setError(getString(R.string.full_req));
            editFullName.requestFocus();
        } else if (phone.isEmpty()) {
            editPhone.setError(getString(R.string.phone_req));
            editPhone.requestFocus();
        } else if (email.isEmpty()) {
            editEmail.setError(getString(R.string.email_req));
            editEmail.requestFocus();
        } else if (username.isEmpty()) {
            editUserName.setError(getString(R.string.user_req));
            editUserName.requestFocus();
        } else if (password.isEmpty()) {
            editPassword.setError(getString(R.string.psw_req));
        } else {

                progressBar.setVisibility(View.VISIBLE);
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        progressBar.setVisibility(View.GONE);
                        if (task.isSuccessful()) {
                            StorageReference imageRef = storageReference.child(mAuth.getUid()).child("Image").child("Profile Image");
                            final UploadTask uploadTask = imageRef.putFile(image);
                            uploadTask.addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed to upload the image...", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                    Toast.makeText(getApplicationContext(), "Profile image is uploaded!", Toast.LENGTH_SHORT).show();
                                }
                            });


                            TestUsers user = new TestUsers(fullName, phone, email, username, timestamp);

                            FirebaseDatabase.getInstance().getReference("TestUsers").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user);


                            startLoginActivity();

                        } else {
                            Toast.makeText(getApplicationContext(), getString(R.string.failed_reg), Toast.LENGTH_LONG).show();

                        }
                    }
                });
            }
        }


    private void startLoginActivity(){
        finish();
    }

    private void findAllViews(){
      textPassword = findViewById(R.id.textView25);
      textUserName = findViewById(R.id.textView24);
      textPhone = findViewById(R.id.textView23);
      textFullName = findViewById(R.id.textView10);
      textEmail = findViewById(R.id.textView20);
      textCreateAccount = findViewById(R.id.textView26);
      textPolicy = findViewById(R.id.textView27);

      editFullName = (EditText)findViewById(R.id.editText3);
      editPhone = (EditText)findViewById(R.id.editText4);
      editEmail = findViewById(R.id.editText5);
      editUserName = findViewById(R.id.editText6);
      editPassword = findViewById(R.id.editText7);

      signUp = (Button)findViewById(R.id.button5);

      box = (CheckBox)findViewById(R.id.checkBox2);
      toolBar = (Toolbar)findViewById(R.id.toolbar2);
      progressBar = findViewById(R.id.progressBar2);

      profilePic = (CircleImageView)findViewById(R.id.profile_image);
    }

}
