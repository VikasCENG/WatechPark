package com.example.watechpark;

import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    private ImageView logo, logo2;
    private TextView text1, text2, text3;
    private ProgressBar bar;
    private Handler newHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme_SplashScreen);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
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
    private void findAllViews(){
        logo = (ImageView)findViewById(R.id.imageLogo);
        logo2 = (ImageView)findViewById(R.id.imageView3);
        text1 = findViewById(R.id.textStatus);
        text2 = findViewById(R.id.textDesc);
        text3 = findViewById(R.id.textView2);
        bar = (ProgressBar)findViewById(R.id.progressBar);
    }
}
