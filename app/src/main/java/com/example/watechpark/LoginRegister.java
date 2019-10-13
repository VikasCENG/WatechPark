package com.example.watechpark;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class LoginRegister extends AppCompatActivity {

    private TextView text, text1;
    private ImageView image, image2, image3;
    protected Button button, button1, button2, button3;
    private EditText edit, edit1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findAllViews();

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch ((v.getId())) {
                    case R.id.buttonForgot:

                        startActivity(new Intent(getApplicationContext(), ForgotYourPassword.class));

                        break;

                }
            }

        });

        button.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                switch ((v.getId())) {
                    case R.id.buttonLogin:

                        startActivity(new Intent(getApplicationContext(), MainMenu.class));

                        break;

                }
            }

        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch ((v.getId())) {
                    case R.id.buttonGoogle:
                       google();
                }
            }
        });

    }

    private void findAllViews() {
    image = (ImageView) findViewById(R.id.image2);
    image2 = (ImageView)findViewById(R.id.imageView5);
    image3 = (ImageView)findViewById(R.id.imageView6);
    button = (Button) findViewById(R.id.buttonLogin);
    button1 = (Button) findViewById(R.id.buttonReg);
    button2 = (Button) findViewById(R.id.buttonGoogle);
    button3 = (Button) findViewById(R.id.buttonForgot);
    edit = (EditText) findViewById(R.id.editUser);
    edit1 = (EditText) findViewById(R.id.editPass);
    text = findViewById(R.id.textUser);
    text1 = findViewById(R.id.textPass);
    //text2 = findViewById(R.id.textForgot);
}

    private void google(){
        String url = "https://accounts.google.com";
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse(url));
        startActivity(intent);
    }

}
