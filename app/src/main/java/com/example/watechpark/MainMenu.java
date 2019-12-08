package com.example.watechpark;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.watechpark.ui.AddACarFragment;
import com.example.watechpark.ui.Home.HomeFragment;
import com.example.watechpark.ui.Settings.PaymentFragment;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainMenu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    RecyclerView recyclerView;
    ParkingLocationAdapter adapter;
    List<ParkingLocation> parkingList;


    private ImageView i1,i2,i3;
    private Button b1,b2,b3;
    private TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10, t11, userName, userEmail;

     FirebaseAuth mAuth;
     FirebaseUser user;

    private FirebaseStorage firebaseStorage;

    protected Button addACar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        parkingList = new ArrayList<>();

        recyclerView = findViewById(R.id.recyclerView2);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        parkingList.add(new ParkingLocation(getString(R.string.queens_parkway), getString(R.string.loc0), 1.6, 6.50 , R.drawable.queens));
        parkingList.add(new ParkingLocation(getString(R.string.humber), getString(R.string.loc1), 0.2, 8.50 , R.drawable.humber2));
        parkingList.add(new ParkingLocation(getString(R.string.bloor), getString(R.string.loc2), 3.6, 6.80 , R.drawable.bloor));
        parkingList.add(new ParkingLocation(getString(R.string.eaton), getString(R.string.loc3), 2.4, 9.00 , R.drawable.eaton));
        parkingList.add(new ParkingLocation(getString(R.string.york), getString(R.string.loc4), 3.6, 8.50 , R.drawable.yorkdale));
        parkingList.add(new ParkingLocation(getString(R.string.scotia), getString(R.string.loc5), 15.6, 7.50 , R.drawable.scotia));
        parkingList.add(new ParkingLocation(getString(R.string.mills), getString(R.string.loc6), 24.6, 10.50 , R.drawable.mills2));
        parkingList.add(new ParkingLocation(getString(R.string.yonge), getString(R.string.loc7), 11.6, 12.50 , R.drawable.yonge));
        parkingList.add(new ParkingLocation(getString(R.string.fairview), getString(R.string.loc8), 20.6, 11.75 , R.drawable.fairview));
        parkingList.add(new ParkingLocation(getString(R.string.square), getString(R.string.loc9), 8.6, 9.50 , R.drawable.square));


        adapter = new ParkingLocationAdapter(this, parkingList);
        recyclerView.setAdapter(adapter);

       // userName = findViewById(R.id.nav_user);
       // userEmail = findViewById(R.id.nav_email);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        firebaseStorage = FirebaseStorage.getInstance();
        StorageReference storageReference = firebaseStorage.getReference();


        getSupportActionBar().setTitle(getString(R.string.home_title));


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);




        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_passes, R.id.nav_orderhistory, R.id.nav_addacar, R.id.nav_manage,
                R.id.nav_payment, R.id.nav_settings, R.id.nav_help, R.id.nav_about)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);




        displayHeaderInfo();
    }


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_acc){
            Intent i = new Intent(getApplicationContext(), AccountManagement.class);
            startActivity(i);

        }else if(id == R.id.action_logout){
             logOutUser();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void logOutUser(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(getString(R.string.confirm_out));
        builder.setMessage(getString(R.string.log_outconf));
        builder.setPositiveButton(getString(R.string.y), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAuth.signOut();
                startActivity(new Intent(MainMenu.this, LoginRegister.class));
                dialog.dismiss();
            }
        });

        builder.setNegativeButton(getString(R.string.n), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }





    private void displayHeaderInfo() {
            NavigationView navigationView = findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);
            final TextView userName = headerView.findViewById(R.id.nav_user);
            final TextView userEmail = headerView.findViewById(R.id.nav_email);
            final CircleImageView navImage = headerView.findViewById(R.id.imageHeader);

            final ParkingPassInfo parkingPassInfo = new ParkingPassInfo();

            //userName.setText(user.getDisplayName());
            //userEmail.setText((user.getEmail()));

            //Picasso.get().load(user.getPhotoUrl()).fit().centerCrop().into(navImage);
            //Glide.with(this).load(user.getPhotoUrl()).apply(RequestOptions.circleCropTransform()).into(navImage);

            firebaseStorage = FirebaseStorage.getInstance();
            StorageReference storageReference = firebaseStorage.getReference();
            storageReference.child(user.getUid()).child(getString(R.string.prof_2)).getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                @Override
                public void onSuccess(Uri uri) {
                    Picasso.get().load(uri).fit().centerCrop().into(navImage);

                    userEmail.setText(user.getEmail());
                    userName.setText(user.getUid());
                }
            });

            //Glide.with(this).applyDefaultRequestOptions(RequestOptions.circleCropTransform()).asBitmap().load(user.getPhotoUrl()).into(navImage);
        }


}



