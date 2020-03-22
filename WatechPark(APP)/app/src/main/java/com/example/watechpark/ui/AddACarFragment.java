package com.example.watechpark.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.watechpark.Cars;
import com.example.watechpark.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddACarFragment extends Fragment {

    private AddACarViewModel addACarViewModel;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;

    private TextView label;
    private EditText edit_make, edit_model, edit_number, edit_color;

    Task<AuthResult> task = FirebaseAuth.getInstance().signInAnonymously();


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addACarViewModel =
                ViewModelProviders.of(this).get(AddACarViewModel.class);
        View root = inflater.inflate(R.layout.add_acar_fragment, container, false);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        edit_make = root.findViewById(R.id.edit_make);
        label = root.findViewById(R.id.textView32);
        edit_model = root.findViewById(R.id.edit_model);
        edit_number = root.findViewById(R.id.edit_number);
        edit_color = root.findViewById(R.id.edit_color);
        Button button = (Button) root.findViewById(R.id.button_add);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addCar();
            }
        });


        return root;


    }

    private void addCar() {
        final String make = edit_make.getText().toString().trim();
        final String model = edit_model.getText().toString().trim();
        final String color = edit_color.getText().toString().trim();
        final String licensePlate = edit_number.getText().toString().trim();
        final Long time = System.currentTimeMillis() / 1000;
        final String timestamp = time.toString();

        if (make.isEmpty()) {
            edit_make.setError(getString(R.string.make));
            edit_make.requestFocus();
        } else if (model.isEmpty()) {
            edit_model.setError(getString(R.string.model));
            edit_model.requestFocus();
        } else if (color.isEmpty()) {
            edit_color.setError(getString(R.string.color));
            edit_color.requestFocus();
        } else if (licensePlate.isEmpty()) {
            edit_number.setError(getString(R.string.lic));
            edit_number.requestFocus();
        } else {

            Cars cars = new Cars(make, model, color, licensePlate, timestamp);
            mDatabase.child("Cars")
                    .push().setValue(cars).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), R.string.car_added, Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), R.string.car_failed, Toast.LENGTH_SHORT).show();
                    }
                }
            });


        }
    }
}