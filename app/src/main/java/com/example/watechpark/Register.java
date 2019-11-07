package com.example.watechpark;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class Register extends AppCompatActivity {
    private EditText editFullName, editPhone, editEmail,editUserName, editPassword;
    private TextView textCreateAccount, textReg, textFullName, textPhone, textEmail, textUserName, textPassword, textPolicy;
    private Button signUp;
    private Toolbar toolBar;
    private CheckBox box;

    private FirebaseAuth mAuth;

    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setSupportActionBar(toolBar);
        findAllViews();

        progressBar.setVisibility(View.GONE);

        mAuth = FirebaseAuth.getInstance();

        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerValidate();
            }
        });
    }



    private void registerValidate() {
        final String fullName = editFullName.getText().toString().trim();
        final String phone = editPhone.getText().toString().trim();
        final String email = editEmail.getText().toString().trim();
        final String username = editUserName.getText().toString().trim();
        final String password = editPassword.getText().toString().trim();

        if (fullName.isEmpty()) {
            editFullName.setError(getString(R.string.full_req));
            editFullName.requestFocus();
        } else if (phone.isEmpty()) {
            editPhone.setError(getString(R.string.phone_req));
            editPhone.requestFocus();
        } else if (email.isEmpty()) {
            editEmail.setError(getString(R.string.email_req));
            editEmail.requestFocus();
        } else if (username.isEmpty()) {
            editUserName.setError(getString(R.string.user_req));
            editUserName.requestFocus();
        } else if (password.isEmpty()) {
            editPassword.setError(getString(R.string.psw_req));
        } else {

            progressBar.setVisibility(View.VISIBLE);
            mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    progressBar.setVisibility(View.GONE);
                    if (task.isSuccessful()) {
                        TestUser user = new TestUser(fullName, phone, email, username, password);

                        FirebaseDatabase.getInstance().getReference("TestUsers").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                .setValue(user);


                        startLoginActivity();

                    } else {
                        Toast.makeText(getApplicationContext(), getString(R.string.failed_reg), Toast.LENGTH_LONG).show();

                    }
                }
            });
        }
    }

    private void startLoginActivity(){
        finish();
    }

    private void findAllViews(){
      textPassword = findViewById(R.id.textView25);
      textUserName = findViewById(R.id.textView24);
      textPhone = findViewById(R.id.textView23);
      textFullName = findViewById(R.id.textView10);
      textEmail = findViewById(R.id.textView20);
      textCreateAccount = findViewById(R.id.textView26);
      textPolicy = findViewById(R.id.textView27);

      editFullName = (EditText)findViewById(R.id.editText3);
      editPhone = (EditText)findViewById(R.id.editText4);
      editEmail = findViewById(R.id.editText5);
      editUserName = findViewById(R.id.editText6);
      editPassword = findViewById(R.id.editText7);

      signUp = (Button)findViewById(R.id.button5);

      box = (CheckBox)findViewById(R.id.checkBox2);
      toolBar = (Toolbar)findViewById(R.id.toolbar2);
      progressBar = findViewById(R.id.progressBar2);
    }

}
