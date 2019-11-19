package com.example.watechpark;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.SearchView;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainMenu extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;

    private ImageView i1,i2,i3;
    private Button b1,b2,b3;
    private TextView t1,t2,t3,t4,t5,t6,t7,t8,t9,t10, t11, consumerID;

    private FirebaseAuth mAuth;
    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        i1 = (ImageView)findViewById(R.id.imageView9);
        i2 = (ImageView)findViewById(R.id.imageView11);
        i3 = (ImageView)findViewById(R.id.imageView15);

        b1 = (Button)findViewById(R.id.button2);
        b2 = (Button)findViewById(R.id.button3);
        b3 = (Button)findViewById(R.id.button4);

        t1 = (TextView)findViewById(R.id.textView9);
        t2 = (TextView)findViewById(R.id.textView22);
        t3 = (TextView)findViewById(R.id.textView21);
        t4 = (TextView)findViewById(R.id.textView16);
        t5 = (TextView)findViewById(R.id.textView11);
        t6 = (TextView)findViewById(R.id.textView12);
        t7 = (TextView)findViewById(R.id.textView18);
        t8 = (TextView)findViewById(R.id.textView17);
        t9 = (TextView)findViewById(R.id.textView13);
        t10 = (TextView)findViewById(R.id.textView14);
        t11 = (TextView)findViewById(R.id.textView15);
        consumerID = (TextView)findViewById(R.id.textViewID);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();




        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNav);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);




        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,
                R.id.nav_tools, R.id.nav_share, R.id.nav_send)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
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
        builder.setTitle("Confirm Sign-Out");
        builder.setMessage("Are you sure you want to log-out from this account?");
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                mAuth.signOut();
                finish();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();

    }



}
