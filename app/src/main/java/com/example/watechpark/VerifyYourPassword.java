package com.example.watechpark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class VerifyYourPassword extends AppCompatActivity {

    protected Button verifyBtn, button_send;
    private EditText verificationCode, editPhone, newPass;
    private Toolbar toolBar;

    private FirebaseUser user;

    private FirebaseAuth auth;
    private ProgressBar progressBar;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    String veri_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_your_password);
        findAllViews();

        auth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        progressBar = findViewById(R.id.progressBar4);
        toolBar = (Toolbar) findViewById(R.id.toolbar3);

        verifyBtn = (Button) findViewById(R.id.button6);
        verificationCode = (EditText) findViewById(R.id.editText8);


        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        Bundle extras = getIntent().getExtras();
        veri_code = extras.getString("vericode");

        verifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = verificationCode.getText().toString().trim();
                final String pswd = newPass.getText().toString().trim();

                if (code.isEmpty() || code.length() < 6) {
                    verificationCode.setError("Enter the code...");
                    verificationCode.requestFocus();
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                verifySentCode(code);
                newPassword(pswd);



            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(getApplicationContext(), ForgotYourPassword.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }


    private void verifyNum(){
        String input_code = verificationCode.getText().toString();
        verifyPhoneNumber(veri_code,input_code);
    }


    private void signInWithPhone(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(VerifyYourPassword.this, "Verified", Toast.LENGTH_SHORT).show();
                            Intent i = new Intent(VerifyYourPassword.this, LoginRegister.class);
                            startActivity(i);

                        } else {

                            Toast.makeText(getApplicationContext(), "Verfication code is wrong, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    private void verifySentCode(String code) {

            try {
                PhoneAuthCredential credential = PhoneAuthProvider.getCredential(veri_code, code);
                signInWithPhone(credential);
            } catch (Exception e) {
                Toast.makeText(this, "Verification code is wrong", Toast.LENGTH_SHORT).show();
            }
        }

        private void verifyPhoneNumber(String verifyCode, String input_code){
             PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verifyCode,input_code);
             signInWithPhone(credential);
        }

        private void findAllViews(){
        newPass = (EditText)findViewById(R.id.editText9);
        }

        private void newPassword(String s){
            user.updatePassword(newPass.getText().toString())
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(getApplicationContext(), "New password successfully updated", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getApplicationContext(), "Failed to update new password...", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

        }


    }

