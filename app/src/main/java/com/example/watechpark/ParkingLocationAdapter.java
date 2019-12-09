package com.example.watechpark;

import android.annotation.SuppressLint;
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
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTabHost;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ParkingLocationAdapter extends RecyclerView.Adapter<ParkingLocationAdapter.ParkingLocationViewHolder> {

    private Context context;
    private List<ParkingLocation> parkingLocationList;

    private FirebaseAuth mAuth;
    private FirebaseUser user;
    FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mDatabase;


    private FirebaseDatabase database;


    public ParkingLocationAdapter(Context context, List<ParkingLocation> parkingLocationList) {
        this.context = context;
        this.parkingLocationList = parkingLocationList;
    }


    @NonNull
    @Override
    public ParkingLocationAdapter.ParkingLocationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home, parent, false);
      return new ParkingLocationViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull final ParkingLocationAdapter.ParkingLocationViewHolder holder, final int position) {
        final ParkingLocation parkingLocation = parkingLocationList.get(position);
        holder.lotName.setText(parkingLocation.getLotName());
        holder.lotLocation.setText(parkingLocation.getLotLocation());
        holder.lotDistance.setText(String.valueOf(parkingLocation.getLotDistance() + context.getString(R.string.m)));
        holder.lotCost.setText(context.getString(R.string.dol3) + parkingLocation.getCost());

        holder.lotImage.setImageDrawable(context.getResources().getDrawable(parkingLocation.getLotImage(), null));


        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();





        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference(context.getString(R.string.data_ref));
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                ProximityData proximityData  = ds.getValue(ProximityData.class);
                                Intent intent = new Intent();
                                intent.putExtra(context.getString(R.string.put_name), parkingLocationList.get(position).getLotName());
                                intent.putExtra(context.getString(R.string.put_c), String.valueOf(parkingLocationList.get(position).getCost()));
                                intent.putExtra(context.getString(R.string.put_img), parkingLocation.getLotImage());
                                String lotName = intent.getStringExtra(context.getString(R.string.getname));
                                String lotCost = intent.getStringExtra(context.getString(R.string.getc));
                                int lotImage = intent.getIntExtra(context.getString(R.string.get_img), 0);

                                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                final View view = LayoutInflater.from(context).inflate(R.layout.activity_lot_detail, null);
                                TextView text = view.findViewById(R.id.textView36);
                                text.setText(lotName);
                                TextView cost = view.findViewById(R.id.textView41);
                                cost.setText(context.getString(R.string.setcost)+context.getString(R.string.dol4) + lotCost);
                                TextView prox = view.findViewById(R.id.textView42);
                                prox.setText(context.getString(R.string.set_proximity) + proximityData.getProximity());
                                TextView status = view.findViewById(R.id.textView37);
                                status.setText(context.getString(R.string.set_status) + context.getString(R.string.ful));

                                ImageView img = view.findViewById(R.id.imageView8);
                                img.setImageResource(lotImage);
                                dialog.setView(view);

                                AlertDialog alertDialog = dialog.create();
                                alertDialog.show();



                                }
                            }

                        }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });



            }
        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDatabase = FirebaseDatabase.getInstance().getReference(context.getString(R.string.data_ref2));
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @SuppressLint("SetTextI18n")
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        if(dataSnapshot.exists()) {
                            for (DataSnapshot ds : dataSnapshot.getChildren()) {
                                ProximityData proximityData  = ds.getValue(ProximityData.class);
                                Intent intent = new Intent();
                                intent.putExtra(context.getString(R.string.putname1), parkingLocationList.get(position).getLotName());
                                intent.putExtra(context.getString(R.string.putcost2), String.valueOf(parkingLocationList.get(position).getCost()));
                                intent.putExtra(context.getString(R.string.putcost3), parkingLocation.getLotImage());
                                String lotName = intent.getStringExtra(context.getString(R.string.getname1));
                                String lotCost = intent.getStringExtra(context.getString(R.string.getname2));
                                int lotImage = intent.getIntExtra(context.getString(R.string.getimage2), 0);



                                AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                                final View view = LayoutInflater.from(context).inflate(R.layout.activity_lot_detail, null);
                                TextView text = view.findViewById(R.id.textView36);
                                text.setText(lotName);
                                TextView cost = view.findViewById(R.id.textView41);
                                cost.setText(context.getString(R.string.setcost2)+context.getString(R.string.dol5) + lotCost);
                                TextView status = view.findViewById(R.id.textView37);
                                status.setText(context.getString(R.string.setstatus2) + context.getString(R.string.full1));
                                TextView prox = view.findViewById(R.id.textView42);
                                prox.setText(context.getString(R.string.set_prox2) + proximityData.getProximity());
                                ImageView img = view.findViewById(R.id.imageView8);
                                img.setImageResource(lotImage);
                                dialog.setView(view);

                                AlertDialog alertDialog = dialog.create();
                                alertDialog.show();



                            }
                        }

                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });


            }
        });

        holder.reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String name = parkingLocation.getLotName();
                final String location = parkingLocation.getLotLocation();
                final double distance = Double.parseDouble(String.valueOf(parkingLocation.getLotDistance()));
                final double cost = Double.parseDouble(String.valueOf(parkingLocation.getCost()));
                final int image = Integer.parseInt(String.valueOf(parkingLocation.getLotImage()));

                mDatabase = FirebaseDatabase.getInstance().getReference("ParkingLocations");

                ParkingLocation parkingLocation1 = new ParkingLocation(name,location,distance,cost,image);
                mDatabase.child(user.getUid()).setValue(parkingLocation1);
                mDatabase.child("ParkingLocations").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                        .setValue(parkingLocation1).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(context, R.string.success_notif, Toast.LENGTH_SHORT).show();

                            Notification notification = new Notification.Builder(context)
                                    .setTicker(context.getString(R.string.not))
                                    .setContentTitle(context.getString(R.string.reserved_aspot) + name)
                                    .setContentText(context.getString(R.string.check_pass))
                                    .setSmallIcon(R.drawable.logo)
                                    .build();

                            notification.flags = Notification.FLAG_AUTO_CANCEL;
                            NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
                            notificationManager.notify(0, notification);


                        } else {
                            Toast.makeText(context, R.string.data_err, Toast.LENGTH_SHORT).show();
                        }
                    }
                });



            }
        });

    }

    @Override
    public int getItemCount() {
        return parkingLocationList.size();
    }


    public class ParkingLocationViewHolder extends RecyclerView.ViewHolder {

        ImageView lotImage;
        TextView lotName, lotLocation, lotDistance, lotCost;
        Button viewDetails, reserveButton;

        TextView proximity, timestamp;


        public ParkingLocationViewHolder(@NonNull View itemView) {
            super(itemView);

            lotImage = itemView.findViewById(R.id.image_card);
            lotName = itemView.findViewById(R.id.text_locname);
            lotLocation = itemView.findViewById(R.id.textView39);
            lotDistance = itemView.findViewById(R.id.text_dist);
            lotCost = itemView.findViewById(R.id.textView40);
            viewDetails = itemView.findViewById(R.id.button11);
            reserveButton = itemView.findViewById(R.id.button10);


        }
    }






}