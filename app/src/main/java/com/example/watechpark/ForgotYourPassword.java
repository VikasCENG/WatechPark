package com.example.watechpark;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class ForgotYourPassword extends AppCompatActivity {

    private EditText txt, txt1;
    protected Button btn;
    private TextView view1, view2, view3, view4, view5, view6;
    protected ImageButton image;
    private ImageView img2, img3;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_forgot);
            findAllViews();

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch ((v.getId())) {
                        case R.id.button:

                            startActivity(new Intent(getApplicationContext(), LoginRegister.class));

                            break;

                    }
                }

            });

            image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    switch ((v.getId())) {
                        case R.id.imageButton2:

                            startActivity(new Intent(getApplicationContext(), LoginRegister.class));

                            break;

                    }

                }
            });
        }



        private void findAllViews(){
            txt = (EditText)findViewById(R.id.editText);
            txt1 = (EditText)findViewById(R.id.editText2);
            btn = (Button)findViewById(R.id.button);
            view1 = findViewById(R.id.textView3);
            view2 = findViewById(R.id.textView4);
            view3 = findViewById(R.id.textView5);
            view4 = findViewById(R.id.textView6);
            view5 = findViewById(R.id.textView7);
            view6 = findViewById(R.id.textView8);
        image = (ImageButton)findViewById(R.id.imageButton2);
        img2 = (ImageView)findViewById(R.id.imageView7);
        img3 = (ImageView)findViewById(R.id.imageView2);



    }
}
