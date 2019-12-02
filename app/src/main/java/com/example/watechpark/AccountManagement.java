package com.example.watechpark;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AccountManagement extends AppCompatActivity {


    private TextView fullName;
    private TextView phoneNumber;
    private TextView emailAddress;
    private TextView userName;
    private TextView password;
    private TextView timestamp;

    private CircleImageView profilePic;

    protected Button customButton, changePass;

     FirebaseDatabase firebaseDatabase;
     DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private Toolbar toolbar;
    private FirebaseStorage firebaseStorage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_management);
        findAllViews();
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        String userID = user.getUid();

        firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();
        storageReference.child(userID).child("Image/Profile Image").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                Picasso.get().load(uri).fit().centerCrop().into(profilePic);
            }
        });

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("TestUsers");

        readData();

    }

    public void readData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String name = dataSnapshot.child(user.getUid()).getValue(TestUsers.class).getFullName();
                String phone = dataSnapshot.child(user.getUid()).getValue(TestUsers.class).getPhone();
                String email = dataSnapshot.child(user.getUid()).getValue(TestUsers.class).getEmail();
                String userName1 = dataSnapshot.child(user.getUid()).getValue(TestUsers.class).getUsername();
                String time = dataSnapshot.child(user.getUid()).getValue(TestUsers.class).getTimestamp();
                fullName.setText(name);
                phoneNumber.setText("Phone #: " + phone);
                emailAddress.setText("E-mail: " + email);
                userName.setText("Username: " + userName1);
                timestamp.setText(convertTimestamp(time));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getApplicationContext(), " Profile data is unavailable at this time...", Toast.LENGTH_SHORT).show();
            }
        });
    }




    private String convertTimestamp(String timestamp){
        long yourSeconds = Long.valueOf(timestamp);
        Date mDate = new Date(yourSeconds * 1000);
        DateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a ");
        return df.format(mDate);

    }

    private void findAllViews(){
        fullName = findViewById(R.id.textFullName);
        phoneNumber = findViewById(R.id.textPhone);
        emailAddress = findViewById(R.id.textEmail);
        userName = findViewById(R.id.textUserName);
        password = findViewById(R.id.textPassword);
        timestamp = findViewById(R.id.textTime);
        toolbar = findViewById(R.id.toolbar5);
        customButton = findViewById(R.id.button7);
        profilePic = (CircleImageView)findViewById(R.id.imageView4);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(getApplicationContext(), MainMenu.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

}
