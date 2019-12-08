package com.example.watechpark;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

public class SplashScreen extends AppCompatActivity {

    private ImageView logo, logo2;
    private TextView text1, text2, text3;
    private ProgressBar bar;
    private Handler newHandler = new Handler();

    private static final String[] PERMISSION = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_SplashScreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        if (Build.VERSION.SDK_INT >= 23) {
            requestPermission();
        }


            findAllViews();

            newHandler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Intent intent = new Intent(getApplicationContext(), LoginRegister.class);
                    startActivity(intent);
                    finish();
                }
            }, 3000);
        }


        private void findAllViews () {
            logo = (ImageView) findViewById(R.id.imageLogo);
            logo2 = (ImageView) findViewById(R.id.imageView3);
            text1 = findViewById(R.id.textStatus);
            text2 = findViewById(R.id.textDesc);
            text3 = findViewById(R.id.textView2);
            bar = (ProgressBar) findViewById(R.id.progressBar);
        }

        private void requestPermission () {
            int storage = ActivityCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.READ_EXTERNAL_STORAGE);

            int writeStorage = ActivityCompat.checkSelfPermission(SplashScreen.this, Manifest.permission.WRITE_EXTERNAL_STORAGE);

            if (storage != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SplashScreen.this, PERMISSION, 1);
            }

            if (writeStorage != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(SplashScreen.this, PERMISSION, 1);
            }
        }

}
