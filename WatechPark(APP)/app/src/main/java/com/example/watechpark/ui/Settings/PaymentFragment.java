package com.example.watechpark.ui.Settings;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.watechpark.Orders;
import com.example.watechpark.ParkingPassInfo;
import com.example.watechpark.R;
import com.example.watechpark.TestUsers;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PaymentFragment extends Fragment {

    private PaymentViewModel paymentViewModel;

    private EditText edit;
    private Button generateCode;
    private ImageView image;
    private FloatingActionButton fab;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    private FirebaseAuth mAuth;
    private FirebaseUser user;

    private TextView finalName,finalLocation,finalCost, passType,totalDuration,validTime,expiryTime,balance, timestamp;

    private TextView orderID, email, tax;

    private double result;
    private double taxResult;

    private double nBalance;

    private String timePurchased;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        paymentViewModel =
                ViewModelProviders.of(this).get(PaymentViewModel.class);
        View root = inflater.inflate(R.layout.payment_fragment, container, false);

        edit = root.findViewById(R.id.edit_balance);
        generateCode = root.findViewById(R.id.button_generate);
        image = root.findViewById(R.id.imageView10);
        fab = root.findViewById(R.id.floatingActionButton2);

        finalName = root.findViewById(R.id.textView57);
        finalLocation = root.findViewById(R.id.textView50);
        finalCost = root.findViewById(R.id.textView61);
        passType = root.findViewById(R.id.textView47);
        totalDuration = root.findViewById(R.id.textView53);
        validTime = root.findViewById(R.id.textView58);
        expiryTime = root.findViewById(R.id.textView60);
        balance = root.findViewById(R.id.totalBalance);
        timestamp = root.findViewById(R.id.time_stamp);

        orderID = root.findViewById(R.id.textView64);
        email = root.findViewById(R.id.textView65);
        tax = root.findViewById(R.id.textView62);

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();

        ((AppCompatActivity)getActivity()).getSupportActionBar().setTitle(getString(R.string.pay_ment));

        String userID = user.getUid();

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("ParkingLocation");

        readData();

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Snackbar.make(view,getString(R.string.confirm_order), Snackbar.LENGTH_LONG)
                      .setAction(getString(R.string.cancel), new View.OnClickListener() {
                          @Override
                          public void onClick(View v) {

                          }
                      }).setAction(getString(R.string.confirm), new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {
                    sendOrder();
                  }
              }).show();
            }
        });



        generateCode.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String text = edit.getText().toString().trim();

                if (text != null) {
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        image.setImageBitmap(bitmap);
                    } catch (WriterException e) {

                    }

                }
            }

        });




        return root;
    }

    public void readData(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String lotName = dataSnapshot.child(user.getUid()).getValue(ParkingPassInfo.class).getLotName();
                 final String lotLocation = dataSnapshot.child(user.getUid()).getValue(ParkingPassInfo.class).getLotLocation();
                 final double lotCost = Double.parseDouble(String.valueOf(dataSnapshot.child(user.getUid()).getValue(ParkingPassInfo.class).getLotCost()));
                final String lotType = dataSnapshot.child(user.getUid()).getValue(ParkingPassInfo.class).getPassType();
                final int lotDuration = Integer.parseInt(String.valueOf(dataSnapshot.child(user.getUid()).getValue(ParkingPassInfo.class).getDuration()));
                 final String validFrom = dataSnapshot.child(user.getUid()).getValue(ParkingPassInfo.class).getVaildFrom();
                 final String expiry = dataSnapshot.child(user.getUid()).getValue(ParkingPassInfo.class).getExpiryTime();
                double newBalance = Double.parseDouble(String.valueOf(dataSnapshot.child(user.getUid()).getValue(ParkingPassInfo.class).getBalance()));
                final Long time = System.currentTimeMillis() / 1000;
                final String newTime = time.toString();

                finalName.setText(lotName);
                orderID.setText(getString(R.string.oid)+user.getUid());
                email.setText((user.getEmail()));
                finalLocation.setText(lotLocation);
                finalCost.setText(getString(R.string.cost)+getString(R.string.dollar)+lotCost);
                passType.setText(getString(R.string.pass)+lotType);
                totalDuration.setText(getString(R.string.duration)+lotDuration+getString(R.string.hours));
                validTime.setText(getString(R.string.valid)+validFrom);
                expiryTime.setText(getString(R.string.ixpire)+expiry);
                balance.setText(getString(R.string.bal)+getString(R.string.dollar1)+newBalance);
                timestamp.setText(convertTimestamp(newTime));

                result = lotCost * 0.13;
                taxResult = result+lotCost;
                tax.setText(getString(R.string.tax)+ getString(R.string.dollar2)+taxResult);


                nBalance = newBalance - taxResult;
                balance.setText(getString(R.string.bal_after) +getString(R.string.minudol)+(int) nBalance);

                edit.setText(getString(R.string.dollar3)+taxResult);
                edit.setEnabled(false);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), R.string.data_unavailable, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void sendOrder(){
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                final String lotName = dataSnapshot.child(user.getUid()).getValue(ParkingPassInfo.class).getLotName();
                final String lotLocation = dataSnapshot.child(user.getUid()).getValue(ParkingPassInfo.class).getLotLocation();
                final double lotCost = taxResult;
                final String lotType = dataSnapshot.child(user.getUid()).getValue(ParkingPassInfo.class).getPassType();
                final int lotDuration = Integer.parseInt(String.valueOf(dataSnapshot.child(user.getUid()).getValue(ParkingPassInfo.class).getDuration()));
                final String validFrom = dataSnapshot.child(user.getUid()).getValue(ParkingPassInfo.class).getVaildFrom();
                final String expiry = dataSnapshot.child(user.getUid()).getValue(ParkingPassInfo.class).getExpiryTime();
                final double newBalance = nBalance;
                final Long time = System.currentTimeMillis() / 1000;
                final String newTime = time.toString();

                if (isAdded()) { // used to check if fragment includes a context due to error I was having
                    Orders orders = new Orders(user.getUid(), user.getEmail(), lotName, lotLocation, lotCost, lotType, lotDuration, validFrom, expiry, nBalance, newTime);
                    databaseReference.child(getString(R.string.orders)).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                            .setValue(orders).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(getContext(), R.string.order_success, Toast.LENGTH_SHORT).show();

                            } else {
                                Toast.makeText(getContext(), R.string.order_failed, Toast.LENGTH_SHORT).show();


                            }
                        }
                    });


                }
            }

                @Override
                public void onCancelled (@NonNull DatabaseError databaseError){
                    Toast.makeText(getContext(), R.string.data_un, Toast.LENGTH_SHORT).show();
                }
            });
        }


    private String convertTimestamp(String timestamp){
        long yourSeconds = Long.valueOf(timestamp);
        Date mDate = new Date(yourSeconds * 1000);
        DateFormat df = new SimpleDateFormat(getString(R.string.string));
        return df.format(mDate);

    }



}

