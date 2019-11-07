package com.example.watechpark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ThrowOnExtraProperties;

public class LoginRegister extends AppCompatActivity {

    private TextView text, text1;
    private ImageView image, image2, image3;
    protected Button button, button1, button2, button3;
    private EditText edit, edit1;


    private FirebaseAuth mLoginAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findAllViews();

        mLoginAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mUser = mLoginAuth.getCurrentUser();
                if (mLoginAuth != null) {
                    Toast.makeText(LoginRegister.this, R.string.current_log, Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginRegister.this, R.string.please_login, Toast.LENGTH_SHORT).show();

                }
            }
        };

        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch ((v.getId())) {
                    case R.id.buttonReg:
                        startActivity(new Intent(getApplicationContext(), Register.class));
                        break;
                }
            }
        });

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
                        loginValidate();

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



    private void loginValidate() {
        final String email = edit.getText().toString();
        final String password = edit1.getText().toString();

        if (edit.getText().toString().isEmpty()) {
            edit.setError(getString(R.string.user_re1));
            edit.requestFocus();
        } else if (edit1.getText().toString().isEmpty()) {
            edit1.setError(getString(R.string.pass_req1));
            edit1.requestFocus();
        } else {
            mLoginAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(),getString(R.string.welcome_msg) + email, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), MainMenu.class));
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.TRY_AGAIN, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
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
