package com.example.watechpark;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTabHost;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class ParkingPassesAdapter extends RecyclerView.Adapter<ParkingPassesAdapter.ParkingPassesViewHolder> {

    private Context context;
    private List<ParkingPassInfo> parkingPassInfoList;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;


    public ParkingPassesAdapter(Context context, List<ParkingPassInfo> parkingPassInfoList) {
        this.context = context;
        this.parkingPassInfoList = parkingPassInfoList;
    }

    @NonNull
    @Override
    public ParkingPassesAdapter.ParkingPassesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.pass_cards, parent, false);
        return new ParkingPassesViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ParkingPassesAdapter.ParkingPassesViewHolder holder, final int position) {
        final ParkingPassInfo parkingPassInfo = parkingPassInfoList.get(position);

        holder.name.setText(context.getString(R.string.info__lot) + parkingPassInfoList.get(position).getLotName());
        holder.loc.setText(context.getString(R.string.info_loc) + parkingPassInfoList.get(position).getLotLocation());
        holder.type.setText(context.getString(R.string.info_type) + parkingPassInfoList.get(position).getPassType());
        holder.duration.setText(context.getString(R.string.info_dur) + String.valueOf(parkingPassInfoList.get(position).getDuration()+context.getString(R.string.hours2)));
        holder.valid.setText(context.getString(R.string.info_valid) + parkingPassInfoList.get(position).getVaildFrom());
        holder.expiry.setText(context.getString(R.string.infor_exp) + parkingPassInfoList.get(position).getExpiryTime());
        holder.cost.setText(context.getString(R.string.infor_cost) + context.getString(R.string.dol6)+(parkingPassInfoList.get(position).getLotCost()));
        holder.balance.setText(context.getString(R.string.info_bal) + context.getString(R.string.dol7)+(parkingPassInfoList.get(position).getBalance()));

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance().getReference();


        holder.btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String lName = parkingPassInfo.getLotName();
                final String lLoc = parkingPassInfo.getLotLocation();
                final String lType = parkingPassInfo.getPassType();
                final int lDuration = Integer.parseInt(String.valueOf(parkingPassInfo.getDuration()));
                final String lValid = parkingPassInfo.getVaildFrom();
                final String lExpiry = parkingPassInfo.getExpiryTime();
                final double lCost = Double.parseDouble(String.valueOf(parkingPassInfo.getLotCost()));
                final int lBalance = Integer.parseInt(String.valueOf(parkingPassInfo.getBalance()));

                ParkingPassInfo parkingPassInfo1 = new ParkingPassInfo(lName,lLoc,lCost,lType,lDuration,lValid,lExpiry,lBalance);
                mDatabase.child(context.getString(R.string.child_info)).child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(parkingPassInfo1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, R.string.proceed, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, R.string.fail_data, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    @Override
    public int getItemCount() {
        return parkingPassInfoList.size();
    }

    public class ParkingPassesViewHolder extends RecyclerView.ViewHolder{

        TextView name,loc,type,duration,valid,expiry,cost,balance;
        Button btnPay;

        public ParkingPassesViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textView54);
            loc = itemView.findViewById(R.id.textView48);
            type = itemView.findViewById(R.id.textView46);
            duration = itemView.findViewById(R.id.textView51);
            valid = itemView.findViewById(R.id.textView52);
            expiry = itemView.findViewById(R.id.textView49);
            cost = itemView.findViewById(R.id.textView55);
            balance = itemView.findViewById(R.id.textView56);

            btnPay = itemView.findViewById(R.id.button13);


        }
    }



}

