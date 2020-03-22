package com.example.watechpark;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
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
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTabHost;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
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

    private DatabaseReference mDatabase1;

    private DatabaseReference mDatabase2;


    private FirebaseDatabase database;

    private long trueCond = 1;
    private long falseCond = 0;

    private int status0;



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

                mDatabase = FirebaseDatabase.getInstance().getReference().child("ProximityData");
                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Cars");
                mDatabase2 = FirebaseDatabase.getInstance().getReference().child("GateStatus");
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ProximityData proximityData = dataSnapshot.getValue(ProximityData.class);
                        Cars cars = dataSnapshot.getValue(Cars.class);
                        Intent intent = new Intent();
                        intent.putExtra(context.getString(R.string.put_name), parkingLocationList.get(position).getLotName());
                        String lotName = intent.getStringExtra(context.getString(R.string.getname));

                        //String proximity1 = ds.getValue(ProximityData.class).getProximity();

                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        final View view = LayoutInflater.from(context).inflate(R.layout.activity_lot_detail, null);
                        TextView text = view.findViewById(R.id.textView36);
                        text.setText(lotName);
                        TextView status = view.findViewById(R.id.textView37);
                        final EditText adminPass = view.findViewById(R.id.editText16);
                        final String plate = adminPass.getText().toString().trim();
                        //adminPass.setText("admin");
                        Button adminBtn = view.findViewById(R.id.button3);
                        adminBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (adminPass.getText().toString().equals("admin")) {

                                    AdminControl status = new AdminControl();
                                    Toast.makeText(context, "Admin Access Activated: Gate is OPEN", Toast.LENGTH_LONG).show();

                                    FirebaseDatabase.getInstance().getReference("AdminControl").child("adminStatus").setValue(status.getAdminEntry());
                                    ImageView adminEntry = view.findViewById(R.id.imageEntry);
                                    adminEntry.setColorFilter(context.getResources().getColor(R.color.colorBlue));
                                } else {
                                    AdminControl status = new AdminControl();
                                    Toast.makeText(context, "Unauthorized Admin Access! Invalid Credentials", Toast.LENGTH_LONG).show();
                                    FirebaseDatabase.getInstance().getReference("AdminControl").child("adminStatus").setValue(status.getAdminEntry0());
                                    ImageView adminEntry = view.findViewById(R.id.imageEntry);
                                    adminEntry.setColorFilter(context.getResources().getColor(R.color.colorRed));

                                }


                            }
                        });

                        TextView prox = view.findViewById(R.id.textView42);
                        prox.setText("RT Proximity: " + String.valueOf(proximityData.getProximity()));
                        TextView s1 = view.findViewById(R.id.textSlot1);
                        String slot1 = String.valueOf(proximityData.getSlot1A());
                        TextView s2 = view.findViewById(R.id.textSlot2);
                        String slot2 = String.valueOf(proximityData.getSlot2B());
                        TextView s3 = view.findViewById(R.id.textSlot3);
                        String slot3 = String.valueOf(proximityData.getSlot3C());
                        TextView s4 = view.findViewById(R.id.textSlot4);
                        String slot4 = String.valueOf(proximityData.getSlot4D());


                        int value = 0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            value = Math.toIntExact(proximityData.getProximity());
                        }

                        if (value <= 2500) {
                            ImageView en = view.findViewById(R.id.imageEntry);
                            en.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                            ImageView spot1 = view.findViewById(R.id.imageSpot1);
                            spot1.setBackgroundResource(R.color.colorGreen);
                            ImageView spot2 = view.findViewById(R.id.imageSpot2);
                            spot2.setBackgroundResource(R.color.colorRed);
                            ImageView spot3 = view.findViewById(R.id.imageSpot3);
                            spot3.setBackgroundResource(R.color.colorRed);
                            ImageView spot4 = view.findViewById(R.id.imageSpot4);
                            spot4.setBackgroundResource(R.color.colorRed);


                            s1.setText("Open");
                            s2.setText("Occupied");
                            s3.setText("Occupied");
                            s4.setText("Occupied");
                            status.setText(context.getString(R.string.setstatus2) + " Slot 1A is available -- (1/4)");
                            Toast.makeText(context, "LOT HC: ENTRY IS ALLOWED", Toast.LENGTH_LONG).show();
                            Toast.makeText(context, "LOT HC: GATE IS OPEN", Toast.LENGTH_LONG).show();
                            spot1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Snackbar.make(view, "Do you want to reserve this slot: Slot 1A?", Snackbar.LENGTH_LONG)
                                            .setAction("CANCEL", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            }).setAction("RESERVE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            mDatabase = FirebaseDatabase.getInstance().getReference("ParkingLocations");

                                            final String name = parkingLocation.getLotName();
                                            final String location = parkingLocation.getLotLocation();
                                            final double distance = Double.parseDouble(String.valueOf(parkingLocation.getLotDistance()));
                                            final double cost = Double.parseDouble(String.valueOf(parkingLocation.getCost()));
                                            final int image = Integer.parseInt(String.valueOf(parkingLocation.getLotImage()));

                                            ParkingLocation parkingLocation1 = new ParkingLocation(name, location, distance, cost, image);
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
                                                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                                        notificationManager.notify(0, notification);


                                                    } else {
                                                        Toast.makeText(context, R.string.data_err, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                            AdminControl status = new AdminControl();
                                            TextView s1 = view.findViewById(R.id.textSlot1);
                                            s1.setText("Occupied");
                                            ImageView spot1 = view.findViewById(R.id.imageSpot1);
                                            spot1.setBackgroundResource(R.color.colorRed);
                                            ImageView en = view.findViewById(R.id.imageEntry);
                                            en.setColorFilter(context.getResources().getColor(R.color.colorRed));
                                            ImageView ex2 = view.findViewById(R.id.imageExit);
                                            ex2.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                                            TextView status2 = view.findViewById(R.id.textView37);
                                            Toast.makeText(context,"Slot 1A booked! Please proceed to Payment after selecting your Parking Pass...",Toast.LENGTH_LONG).show();
                                            status2.setText(context.getString(R.string.setstatus2) + " All slots are occupied! -- (4/4)");
                                            FirebaseDatabase.getInstance().getReference("AdminControl").child("adminStatus").setValue(status.getAdminEntry0());
                                            Toast.makeText(context, "LOT HC: ENTRY IS NOT ALLOWED", Toast.LENGTH_LONG).show();


                                        }
                                    }).show();

                                }
                            });
                        } else if (value > 5000) {
                            ImageView en2 = view.findViewById(R.id.imageEntry);
                            en2.setColorFilter(context.getResources().getColor(R.color.colorRed));
                            ImageView ex2 = view.findViewById(R.id.imageExit);
                            ex2.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                            ImageView spot1 = view.findViewById(R.id.imageSpot1);
                            spot1.setBackgroundResource(R.color.colorRed);
                            ImageView spot2 = view.findViewById(R.id.imageSpot2);
                            spot2.setBackgroundResource(R.color.colorRed);
                            ImageView spot3 = view.findViewById(R.id.imageSpot3);
                            spot3.setBackgroundResource(R.color.colorRed);
                            ImageView spot4 = view.findViewById(R.id.imageSpot4);
                            spot4.setBackgroundResource(R.color.colorRed);
                            s1.setText("Occupied");
                            s2.setText("Occupied");
                            s3.setText("Occupied");
                            s4.setText("Occupied");
                            spot1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(context," All slots are occupied currently! Please wait a while for a opening...",Toast.LENGTH_LONG).show();
                                }
                            });
                            status.setText(context.getString(R.string.setstatus2) + " All slots are occupied! -- (4/4)");
                            Toast.makeText(context, "LOT HC: ENTRY IS NOT ALLOWED", Toast.LENGTH_LONG).show();

                            EntryStatus stat = new EntryStatus();
                            status0 = stat.getEntry();

                            FirebaseDatabase.getInstance().getReference("EntryStatus").child("lotStatus")
                                    .setValue(status0);

                            Toast.makeText(context, "LOT HC: LOT IS FULL!", Toast.LENGTH_LONG).show();
                        } else if (value > 2500 && value < 5000) {
                            ImageView en3 = view.findViewById(R.id.imageEntry);
                            en3.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                            ImageView spot1 = view.findViewById(R.id.imageSpot1);
                            spot1.setBackgroundResource(R.color.colorBlue);
                            status.setText(context.getString(R.string.setstatus2) + cars.getLplate() + "is approaching the parking space");
                            s1.setText("Targeted");
                            s2.setText("Occupied");
                            s3.setText("Occupied");
                            s4.setText("Occupied");
                            Toast.makeText(context, "LOT HC: GATE IS CLOSED", Toast.LENGTH_LONG).show();

                        }

                        dialog.setView(view);


                        AlertDialog alertDialog = dialog.create();
                        alertDialog.show();


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });
                mDatabase2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long gate = dataSnapshot.child("Status").getValue(Long.class);
                        GateStatus g = dataSnapshot.getValue(GateStatus.class);
                        final View view = LayoutInflater.from(context).inflate(R.layout.activity_lot_detail, null);

                        if (gate == 1) {
                            final Long time = System.currentTimeMillis() / 1000;
                            final String newTime = time.toString();

                            Toast.makeText(context, "HC LOT: Car has ENTERED the lot! Time: " + convertTimestamp(g.getTimestamp()), Toast.LENGTH_LONG).show();
                        }
                        if (gate == 0) {
                            Toast.makeText(context, "HC LOT: Car has EXITED the lot!", Toast.LENGTH_LONG).show();
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
                mDatabase = FirebaseDatabase.getInstance().getReference().child("ProximityData");
                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Cars");
                mDatabase2 = FirebaseDatabase.getInstance().getReference().child("GateStatus");
                mDatabase.addValueEventListener(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        ProximityData proximityData = dataSnapshot.getValue(ProximityData.class);
                        Cars cars = dataSnapshot.getValue(Cars.class);
                        Intent intent = new Intent();
                        intent.putExtra(context.getString(R.string.put_name), parkingLocationList.get(position).getLotName());
                        String lotName = intent.getStringExtra(context.getString(R.string.getname));

                        //String proximity1 = ds.getValue(ProximityData.class).getProximity();

                        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        final View view = LayoutInflater.from(context).inflate(R.layout.activity_lot_detail, null);
                        TextView text = view.findViewById(R.id.textView36);
                        text.setText(lotName);
                        TextView status = view.findViewById(R.id.textView37);
                        final EditText adminPass = view.findViewById(R.id.editText16);
                        final String plate = adminPass.getText().toString().trim();
                        //adminPass.setText("admin");
                        Button adminBtn = view.findViewById(R.id.button3);
                        adminBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if (adminPass.getText().toString().equals("admin")) {

                                    AdminControl status = new AdminControl();
                                    Toast.makeText(context, "Admin Access Activated: Gate is OPEN", Toast.LENGTH_LONG).show();

                                    FirebaseDatabase.getInstance().getReference("AdminControl").child("adminStatus").setValue(status.getAdminEntry());
                                    ImageView adminEntry = view.findViewById(R.id.imageEntry);
                                    adminEntry.setColorFilter(context.getResources().getColor(R.color.colorBlue));
                                } else {
                                    AdminControl status = new AdminControl();
                                    Toast.makeText(context, "Unauthorized Admin Access! Invalid Credentials", Toast.LENGTH_LONG).show();
                                    FirebaseDatabase.getInstance().getReference("AdminControl").child("adminStatus").setValue(status.getAdminEntry0());
                                    ImageView adminEntry = view.findViewById(R.id.imageEntry);
                                    adminEntry.setColorFilter(context.getResources().getColor(R.color.colorRed));

                                }


                            }
                        });

                        TextView prox = view.findViewById(R.id.textView42);
                        prox.setText("RT Proximity: " + String.valueOf(proximityData.getProximity()));
                        TextView s1 = view.findViewById(R.id.textSlot1);
                        String slot1 = String.valueOf(proximityData.getSlot1A());
                        TextView s2 = view.findViewById(R.id.textSlot2);
                        String slot2 = String.valueOf(proximityData.getSlot2B());
                        TextView s3 = view.findViewById(R.id.textSlot3);
                        String slot3 = String.valueOf(proximityData.getSlot3C());
                        TextView s4 = view.findViewById(R.id.textSlot4);
                        String slot4 = String.valueOf(proximityData.getSlot4D());


                        int value = 0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            value = Math.toIntExact(proximityData.getProximity());
                        }

                        if (value <= 2500) {
                            ImageView en = view.findViewById(R.id.imageEntry);
                            en.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                            ImageView spot1 = view.findViewById(R.id.imageSpot1);
                            spot1.setBackgroundResource(R.color.colorGreen);
                            ImageView spot2 = view.findViewById(R.id.imageSpot2);
                            spot2.setBackgroundResource(R.color.colorRed);
                            ImageView spot3 = view.findViewById(R.id.imageSpot3);
                            spot3.setBackgroundResource(R.color.colorRed);
                            ImageView spot4 = view.findViewById(R.id.imageSpot4);
                            spot4.setBackgroundResource(R.color.colorRed);


                            s1.setText("Open");
                            s2.setText("Occupied");
                            s3.setText("Occupied");
                            s4.setText("Occupied");
                            status.setText(context.getString(R.string.setstatus2) + " Slot 1A is available -- (1/4)");
                            Toast.makeText(context, "LOT HC: ENTRY IS ALLOWED", Toast.LENGTH_LONG).show();
                            Toast.makeText(context, "LOT HC: GATE IS OPEN", Toast.LENGTH_LONG).show();
                            spot1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Snackbar.make(view, "Do you want to reserve this slot: Slot 1A?", Snackbar.LENGTH_LONG)
                                            .setAction("CANCEL", new View.OnClickListener() {
                                                @Override
                                                public void onClick(View v) {

                                                }
                                            }).setAction("RESERVE", new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                            mDatabase = FirebaseDatabase.getInstance().getReference("ParkingLocations");

                                            final String name = parkingLocation.getLotName();
                                            final String location = parkingLocation.getLotLocation();
                                            final double distance = Double.parseDouble(String.valueOf(parkingLocation.getLotDistance()));
                                            final double cost = Double.parseDouble(String.valueOf(parkingLocation.getCost()));
                                            final int image = Integer.parseInt(String.valueOf(parkingLocation.getLotImage()));

                                            ParkingLocation parkingLocation1 = new ParkingLocation(name, location, distance, cost, image);
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
                                                        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                                                        notificationManager.notify(0, notification);


                                                    } else {
                                                        Toast.makeText(context, R.string.data_err, Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                            AdminControl status = new AdminControl();
                                            TextView s1 = view.findViewById(R.id.textSlot1);
                                            s1.setText("Occupied");
                                            ImageView spot1 = view.findViewById(R.id.imageSpot1);
                                            spot1.setBackgroundResource(R.color.colorRed);
                                            ImageView en = view.findViewById(R.id.imageEntry);
                                            en.setColorFilter(context.getResources().getColor(R.color.colorRed));
                                            ImageView ex2 = view.findViewById(R.id.imageExit);
                                            ex2.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                                            TextView status2 = view.findViewById(R.id.textView37);
                                            Toast.makeText(context,"Slot 1A booked! Please proceed to Payment after selecting your Parking Pass...",Toast.LENGTH_LONG).show();
                                            status2.setText(context.getString(R.string.setstatus2) + " All slots are occupied! -- (4/4)");
                                            FirebaseDatabase.getInstance().getReference("AdminControl").child("adminStatus").setValue(status.getAdminEntry0());
                                            Toast.makeText(context, "LOT HC: ENTRY IS NOT ALLOWED", Toast.LENGTH_LONG).show();


                                        }
                                    }).show();

                                }
                            });
                        } else if (value > 5000) {
                            ImageView en2 = view.findViewById(R.id.imageEntry);
                            en2.setColorFilter(context.getResources().getColor(R.color.colorRed));
                            ImageView ex2 = view.findViewById(R.id.imageExit);
                            ex2.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                            ImageView spot1 = view.findViewById(R.id.imageSpot1);
                            spot1.setBackgroundResource(R.color.colorRed);
                            ImageView spot2 = view.findViewById(R.id.imageSpot2);
                            spot2.setBackgroundResource(R.color.colorRed);
                            ImageView spot3 = view.findViewById(R.id.imageSpot3);
                            spot3.setBackgroundResource(R.color.colorRed);
                            ImageView spot4 = view.findViewById(R.id.imageSpot4);
                            spot4.setBackgroundResource(R.color.colorRed);
                            s1.setText("Occupied");
                            s2.setText("Occupied");
                            s3.setText("Occupied");
                            s4.setText("Occupied");
                            spot1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(context,"All slots are occupied currently! Please wait a while for a opening...",Toast.LENGTH_LONG).show();
                                }
                            });
                            status.setText(context.getString(R.string.setstatus2) + " All slots are occupied! -- (4/4)");
                            Toast.makeText(context, "LOT HC: ENTRY IS NOT ALLOWED", Toast.LENGTH_LONG).show();

                            EntryStatus stat = new EntryStatus();
                            status0 = stat.getEntry();

                            FirebaseDatabase.getInstance().getReference("EntryStatus").child("lotStatus")
                                    .setValue(status0);

                            Toast.makeText(context, "LOT HC: LOT IS FULL!", Toast.LENGTH_LONG).show();
                        } else if (value > 2500 && value < 5000) {
                            ImageView en3 = view.findViewById(R.id.imageEntry);
                            en3.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                            ImageView spot1 = view.findViewById(R.id.imageSpot1);
                            spot1.setBackgroundResource(R.color.colorBlue);
                            status.setText(context.getString(R.string.setstatus2) + cars.getLplate() + "is approaching the parking space");
                            s1.setText("Targeted");
                            s2.setText("Occupied");
                            s3.setText("Occupied");
                            s4.setText("Occupied");
                            Toast.makeText(context, "LOT HC: GATE IS CLOSED", Toast.LENGTH_LONG).show();

                        }

                        dialog.setView(view);


                        AlertDialog alertDialog = dialog.create();
                        alertDialog.show();


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });
                mDatabase2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long gate = dataSnapshot.child("Status").getValue(Long.class);
                        GateStatus g = dataSnapshot.getValue(GateStatus.class);
                        final View view = LayoutInflater.from(context).inflate(R.layout.activity_lot_detail, null);

                        if (gate == 1) {
                            final Long time = System.currentTimeMillis() / 1000;
                            final String newTime = time.toString();

                            Toast.makeText(context, "HC LOT: Car has ENTERED the lot! Time: " + convertTimestamp(g.getTimestamp()), Toast.LENGTH_LONG).show();
                        }
                        if (gate == 0) {
                            Toast.makeText(context, "HC LOT: Car has EXITED the lot!", Toast.LENGTH_LONG).show();
                        }


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

            }
        });


    }


    @Override
    public int getItemCount() {
        return parkingLocationList.size();
    }

    private String convertTimestamp(String timestamp){
        long yourSeconds = Long.valueOf(timestamp);
        Date mDate = new Date(yourSeconds * 1000);
        DateFormat df = new SimpleDateFormat(context.getString(R.string.date_for));
        return df.format(mDate);

    }


    public class ParkingLocationViewHolder extends RecyclerView.ViewHolder {

        ImageView lotImage;
        TextView lotName, lotLocation, lotDistance, lotCost;
        Button viewDetails, reserveButton,openGate;

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
            openGate = itemView.findViewById(R.id.button3);


        }
    }






}