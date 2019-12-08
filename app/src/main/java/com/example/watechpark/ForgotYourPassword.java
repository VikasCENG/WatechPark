package com.example.watechpark;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class ForgotYourPassword extends AppCompatActivity {

    private EditText txt, txt1;
    protected Button btn;
    private TextView view1, view2, view3, view4, view5, view6;
    private ImageView img2, img3;
    private Toolbar toolBar;


    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback;
    String verification_code;
    FirebaseAuth mAuth1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot);
        findAllViews();


        setSupportActionBar(toolBar);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayShowTitleEnabled(false);

        mAuth1 = FirebaseAuth.getInstance();

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (txt1.isFocused()) {
                    txt.setEnabled(false);
                    final String phone = txt1.getText().toString();

                    if (phone.isEmpty()) {
                        txt1.setError(getString(R.string.phone_requ));
                        txt1.requestFocus();
                    }

                    sendSMS(phone);


                } else if(txt.isFocused()){
                    txt1.setEnabled(false);
                    resetEmail();

                }
            }
        });

        mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                Toast.makeText(getApplicationContext(), R.string.var, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verification_code = s;
                Toast.makeText(getApplicationContext(), R.string.code_sent, Toast.LENGTH_SHORT).show();
                Intent newIntent = new Intent(ForgotYourPassword.this, VerifyYourPassword.class);
                newIntent.putExtra(getString(R.string.vericode1), verification_code);
                startActivity(newIntent);
                finish();


            }
        };

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(getApplicationContext(), LoginRegister.class);
        startActivity(i);
        return super.onOptionsItemSelected(item);
    }

    private void findAllViews() {
        txt = (EditText) findViewById(R.id.editText);
        txt1 = (EditText) findViewById(R.id.editText2);
        btn = (Button) findViewById(R.id.button);
        view1 = findViewById(R.id.textView3);
        view2 = findViewById(R.id.textView4);
        view3 = findViewById(R.id.textView5);
        view4 = findViewById(R.id.textView6);
        view5 = findViewById(R.id.textView7);
        view6 = findViewById(R.id.textView8);
        img2 = (ImageView) findViewById(R.id.imageView7);
        img3 = (ImageView) findViewById(R.id.imageView2);
        toolBar = (Toolbar) findViewById(R.id.toolbar4);

    }


    private void sendSMS(String number){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                this,
                mCallback
        );

    }

    private void resetEmail(){ // email has to be registered into the database
        final String email = txt.getText().toString();
        mAuth1.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(getApplicationContext(), getString(R.string.veri_emailsent) + email, Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ForgotYourPassword.this, R.string.err, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}









