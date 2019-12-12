package com.example.watechpark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.Scopes;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.ThrowOnExtraProperties;

public class LoginRegister extends AppCompatActivity {

    private TextView text, text1;
    private ImageView image, image2, image3;
    protected Button button, button1, button3;
    private EditText edit, edit1;
    private CheckBox signedInStatus;

    private FirebaseAuth mLoginAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private SignInButton button2;
    int RC_SIGN_IN = 0;
    private GoogleSignInClient mGoogleSignInClient;

    private FirebaseUser currentUser;


    private ProgressBar progressBar1;
    SharedPreferences sharedPref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findAllViews();







        mLoginAuth = FirebaseAuth.getInstance();


        sharedPref = this.getPreferences(Context.MODE_PRIVATE);


        progressBar1.setVisibility(View.GONE);


        currentUser = mLoginAuth.getCurrentUser();
        if(currentUser != null){
            loadSharedPref();
            Toast.makeText(getApplicationContext(), getString(R.string.welcome1) + currentUser.getEmail() + getString(R.string.ex), Toast.LENGTH_SHORT).show();
        }


        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                FirebaseUser mUser = mLoginAuth.getCurrentUser();
                if (mUser != null) {
                    Toast.makeText(LoginRegister.this, getString(R.string.welcome_back) + mUser.getEmail(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getApplicationContext(), MainMenu.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginRegister.this, R.string.please_login, Toast.LENGTH_SHORT).show();
                    loginValidate();

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
                        saveSharedPref();

                        break;

                }
            }

        });

        loadSharedPref();


    }





    private void saveSharedPref(){
        if(signedInStatus.isChecked()) {
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.email_put), edit.getText().toString());
            editor.putString(getString(R.string.pass_put), edit1.getText().toString());
            editor.putBoolean(getString(R.string.box_put), signedInStatus.isChecked());
            editor.apply();
        }else{
            SharedPreferences.Editor editor = sharedPref.edit();
            editor.putString(getString(R.string.email_put1), "");
            editor.putString(getString(R.string.email_put2), "");
            editor.putBoolean(getString(R.string.box_put2), false);
            editor.apply();
        }
    }

    private void loadSharedPref(){
        Boolean checked = sharedPref.getBoolean(getString(R.string.check_shared), false);
        String userEmail = sharedPref.getString(getString(R.string.email_shared), "");
        String userPassword = sharedPref.getString(getString(R.string.pass_shared), "");

        edit.setText(userEmail);
        edit1.setText(userPassword);
        signedInStatus.setChecked(checked);

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

            progressBar1.setVisibility(View.VISIBLE);
            mLoginAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar1.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), getString(R.string.wel2)  + email , Toast.LENGTH_SHORT).show();

                        startActivity(new Intent(LoginRegister.this, MainMenu.class));
                    } else {
                        Toast.makeText(getApplicationContext(), R.string.TRY_AGAIN, Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    private void findAllViews() {
        image = (ImageView) findViewById(R.id.image2);
        image2 = (ImageView) findViewById(R.id.imageView5);
        image3 = (ImageView) findViewById(R.id.imageView6);
        button = (Button) findViewById(R.id.buttonLogin);
        button1 = (Button) findViewById(R.id.buttonReg);
        button3 = (Button) findViewById(R.id.buttonForgot);
        edit = (EditText) findViewById(R.id.editUser);
        edit1 = (EditText) findViewById(R.id.editPass);
        text = findViewById(R.id.textUser);
        text1 = findViewById(R.id.textPass);
        progressBar1 = (ProgressBar)findViewById(R.id.progressBar3);
        signedInStatus = findViewById(R.id.checkBox);
        //text2 = findViewById(R.id.textForgot);
    }

}
