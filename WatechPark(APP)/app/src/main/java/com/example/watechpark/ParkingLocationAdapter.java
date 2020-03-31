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
import android.os.CountDownTimer;
import android.os.Handler;
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
import java.util.Timer;
import java.util.TimerTask;

/*
* Project: WatechPark
* Modified code file: ParkingLocationAdpater.java for adding in the parking lot status feature(planned for Winter 2020)
* Modified by: Vikas Sharma(Student A)
* Group Members: Elias Sabbagh, George Alexandris
* Course: CENG 355
 */


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

    // variable to be used later on to indicate exit status
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
        // get the data for the main lot and the corresponding details from the ParkingLocation data structure
        final ParkingLocation parkingLocation = parkingLocationList.get(position);
        holder.lotName.setText(parkingLocation.getLotName());
        holder.lotLocation.setText(parkingLocation.getLotLocation());
        holder.lotDistance.setText(String.valueOf(parkingLocation.getLotDistance() + context.getString(R.string.m)));
        holder.lotCost.setText(context.getString(R.string.dol3) + parkingLocation.getCost());

        holder.lotImage.setImageDrawable(context.getResources().getDrawable(parkingLocation.getLotImage(), null));


        // Firebase initialization
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();


        // View Details button
        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // read the data from each respective data structure in Firebase for further use using a DatabaseReference object
                mDatabase = FirebaseDatabase.getInstance().getReference().child("ProximityData");
                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Cars");
                mDatabase2 = FirebaseDatabase.getInstance().getReference().child("GateStatus");
                // listen for active changes in the ProximityData structure
                mDatabase.addValueEventListener(new ValueEventListener() {


                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // read and store the data inside the ProximityData structure into a object of the class
                        final ProximityData proximityData = dataSnapshot.getValue(ProximityData.class);
                        // get the data stored in the Cars data structure and store it into a Cars object


                        Cars cars = dataSnapshot.getValue(Cars.class);
                        // use a intent to send the name of the chosen lot from the main menu to the "View Details" pop-up(data screen)
                        Intent intent = new Intent();
                        intent.putExtra(context.getString(R.string.put_name), parkingLocationList.get(position).getLotName());
                        // retrieve the name of the lot and store it into a String variable
                        String lotName = intent.getStringExtra(context.getString(R.string.getname));

                        //String proximity1 = ds.getValue(ProximityData.class).getProximity();

                        // create a new AlertDialog box
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        // inflate the view for the layout from the res folder
                        final View view = LayoutInflater.from(context).inflate(R.layout.activity_lot_detail, null);
                        TextView text = view.findViewById(R.id.textView36);
                        // set the textview to the lot name using the lotName variable from the previous step
                        text.setText(lotName);
                        TextView status = view.findViewById(R.id.textView37);
                        final EditText adminPass = view.findViewById(R.id.editText16);
                        // get the value entered by the user in the EditText field, store into a string variable
                        final String plate = adminPass.getText().toString().trim();
                        //adminPass.setText("admin")

                        Button adminBtn = view.findViewById(R.id.button3);
                        // open gate button
                        adminBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // check if what is entered by the user equals "admin"
                                if (adminPass.getText().toString().equals("admin")) {

                                    // get a new object of the AdminControl class
                                    AdminControl status = new AdminControl();
                                    // if it equals/matches the "admin" string value then present a success messgae and open the gate
                                    Toast.makeText(context, "Admin Access Activated: Gate is OPEN", Toast.LENGTH_SHORT).show();

                                    // get a instance or reference of the AdminControl class and push the value of 1(open gate) to the AdminControl structure
                                    FirebaseDatabase.getInstance().getReference("AdminControl").child("adminStatus").setValue(status.getAdminEntry());
                                    ImageView adminEntry = view.findViewById(R.id.imageEntry);
                                    // set the color of the entry arrow to blue(indicate the gate is opened through admin access)
                                    adminEntry.setColorFilter(context.getResources().getColor(R.color.colorBlue));
                                } else {
                                    AdminControl status = new AdminControl();
                                    // else don't allow access and present a toast for unsuccessful entry
                                    Toast.makeText(context, "Unauthorized Admin Access! Invalid Credentials", Toast.LENGTH_SHORT).show();
                                    // set the value as false and push a 0 to the AdminControl structure in the adminStatus child
                                    FirebaseDatabase.getInstance().getReference("AdminControl").child("adminStatus").setValue(status.getAdminEntry0());
                                    ImageView adminEntry = view.findViewById(R.id.imageEntry);
                                    // set the color to red for the entry  arrow to indicate entry is not allowed
                                    adminEntry.setColorFilter(context.getResources().getColor(R.color.colorRed));

                                }


                            }
                        });


                        TextView prox = view.findViewById(R.id.textView42);
                        //get the proximity from the ProximityData structure and set the TextView
                        prox.setText("RT Proximity: " + String.valueOf(proximityData.getProximity()));

                        TextView s1 = view.findViewById(R.id.textSlot1);
                        String slot1 = String.valueOf(proximityData.getSlot1A());
                        TextView s2 = view.findViewById(R.id.textSlot2);
                        String slot2 = String.valueOf(proximityData.getSlot2B());
                        TextView s3 = view.findViewById(R.id.textSlot3);
                        String slot3 = String.valueOf(proximityData.getSlot3C());
                        TextView s4 = view.findViewById(R.id.textSlot4);
                        String slot4 = String.valueOf(proximityData.getSlot4D());


                        // reccomended way by Android Studio to basically get the long value of the proximity
                        int value = 0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            value = Math.toIntExact(proximityData.getProximity());
                        }

                        // if the proximity value is less than or equal to 2500
                        if (value <= 2500) {
                            ImageView en = view.findViewById(R.id.imageEntry);
                            // set the color of the entry arrow to green
                            en.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                            ImageView spot1 = view.findViewById(R.id.imageSpot1);
                            spot1.setBackgroundResource(R.color.colorGreen);
                            ImageView spot2 = view.findViewById(R.id.imageSpot2);
                            spot2.setBackgroundResource(R.color.colorRed);
                            ImageView spot3 = view.findViewById(R.id.imageSpot3);
                            spot3.setBackgroundResource(R.color.colorRed);
                            ImageView spot4 = view.findViewById(R.id.imageSpot4);
                            spot4.setBackgroundResource(R.color.colorRed);

                            // set the text to each corresponding status
                            s1.setText("Open");
                            s2.setText("Occupied");
                            s3.setText("Occupied");
                            s4.setText("Occupied");
                            // update the overall status of the lot
                            status.setText(context.getString(R.string.setstatus2) + " Slot 1A is available -- (1/4) ENTRY IS ALLOWED!");
                            //Toast.makeText(context, "LOT HC: ENTRY IS ALLOWED!", Toast.LENGTH_SHORT).show();

                            spot1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Snackbar.make(view, "Do you want to reserve this slot: Slot 1A?", Snackbar.LENGTH_LONG)
                                            .setAction("CANCEL", new View.OnClickListener() { // leave the reservation
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



                                                        // send a notification to the user if the lot information is sent successfully
                                                        Notification notification = new Notification.Builder(context)


                                                                .setTicker(context.getString(R.string.not))
                                                                .setContentTitle(context.getString(R.string.reserved_aspot) + name)
                                                                .setContentText("Slot 1A booked! Please proceed to Payment after selecting your Parking Pass...")
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
                                            // set the status of Slot 1A as occupied
                                            s1.setText("Occupied");
                                            // update the visual spots and arrow colors
                                            ImageView spot1 = view.findViewById(R.id.imageSpot1);
                                            spot1.setBackgroundResource(R.color.colorRed);
                                            ImageView en = view.findViewById(R.id.imageEntry);
                                            en.setColorFilter(context.getResources().getColor(R.color.colorRed));
                                            ImageView ex2 = view.findViewById(R.id.imageExit);
                                            ex2.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                                            TextView status2 = view.findViewById(R.id.textView37);
                                            // update the overall status to indiate all spots are taken
                                            status2.setText(context.getString(R.string.setstatus2) + " All slots are occupied! -- (4/4)");
                                            // push a 0 to the AdminControl data strcuture
                                            FirebaseDatabase.getInstance().getReference("AdminControl").child("adminStatus").setValue(status.getAdminEntry0());


                                        }
                                    }).show();

                                }
                            });
                            // check if proximity value is greater than 5000
                        } else if (value > 5000) {
                            // update the slot color changes
                            ImageView en2 = view.findViewById(R.id.imageEntry);
                            en2.setColorFilter(context.getResources().getColor(R.color.colorRed));
                            ImageView exit = view.findViewById(R.id.imageExit);
                            exit.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                            ImageView spot1 = view.findViewById(R.id.imageSpot1);
                            spot1.setBackgroundResource(R.color.colorRed);
                            // Slot 2B,3C,4D will remain occupied(with color red as the background)
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
                            // if the user clicks on Slot1A when all spots are occupied show a toast message
                            spot1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(context," All slots are occupied currently! Please wait a while for a opening...",Toast.LENGTH_SHORT).show();
                                }
                            });
                            // update the overall status
                            status.setText(context.getString(R.string.setstatus2) + " All slots are occupied! -- (4/4) ENTRY IS NOT ALLOWED!");
                            //Toast.makeText(context, "LOT HC: ENTRY IS NOT ALLOWED! LOT IS FULL", Toast.LENGTH_SHORT).show();

                            // initialize a new EntryStatus object
                            EntryStatus stat = new EntryStatus();
                            // get the status and store into a variablee
                            status0 = stat.getEntry();

                            // set this value to the EntryStatus structure for the lotStatus
                            FirebaseDatabase.getInstance().getReference("EntryStatus").child("lotStatus")
                                    .setValue(status0);
                            // check if proximity value is in between the range of open or occupied
                        } else if (value > 2500 && value < 5000) {
                            ImageView en3 = view.findViewById(R.id.imageEntry);
                            en3.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                            // have the Slot 1A change backgrund color to blue
                            ImageView spot1 = view.findViewById(R.id.imageSpot1);
                            spot1.setBackgroundResource(R.color.colorBlue);
                            // update the status
                            status.setText(context.getString(R.string.setstatus2) + " Vehicle is approaching the parking space");
                            // set the TextView of Slot1A to targeted(to indicate vehicle is approaching to Slot1A)
                            s1.setText("Targeted");
                            s2.setText("Occupied");
                            s3.setText("Occupied");
                            s4.setText("Occupied");


                        }

                        // set the view of the dialog to the layout

                        dialog.setView(view);


                        // show the dialog box
                        AlertDialog alertDialog = dialog.create();
                        alertDialog.show();


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });

                // read active changes occuring at the GateStatus data structure(of the IR Break Beam Sensor)
                mDatabase2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long gate = dataSnapshot.child("Status").getValue(Long.class);
                        GateStatus g = dataSnapshot.getValue(GateStatus.class);
                        final View view = LayoutInflater.from(context).inflate(R.layout.activity_lot_detail, null);
                        final TextView status = view.findViewById(R.id.textView37);
                        // check if value is equal to 1
                        if (gate == 1) {
                            final Long time = System.currentTimeMillis() / 1000;
                            final String newTime = time.toString();
                           // status.setText(context.getString(R.string.setstatus2) + "License Plate # verification completed!");
                            // retrieve the status of entry and the timestamp from the GateStatus data structure
                            Toast.makeText(context, "HC LOT: 1A5-E8K verified! Car has ENTERED the lot! Time: " + convertTimestamp(g.getTimestamp()), Toast.LENGTH_SHORT).show();
                        }






                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                }
                });
            }
        });

        // if user clicks on anywhere on the Humber College(Parking Lot) CardView in the main meenu
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // read the data from each respective data structure in Firebase for further use using a DatabaseReference object
                mDatabase = FirebaseDatabase.getInstance().getReference().child("ProximityData");
                mDatabase1 = FirebaseDatabase.getInstance().getReference().child("Cars");
                mDatabase2 = FirebaseDatabase.getInstance().getReference().child("GateStatus");
                // listen for active changes in the ProximityData structure
                mDatabase.addValueEventListener(new ValueEventListener() {


                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        // read and store the data inside the ProximityData structure into a object of the class
                        final ProximityData proximityData = dataSnapshot.getValue(ProximityData.class);
                        // get the data stored in the Cars data structure and store it into a Cars object


                        Cars cars = dataSnapshot.getValue(Cars.class);
                        // use a intent to send the name of the chosen lot from the main menu to the "View Details" pop-up(data screen)
                        Intent intent = new Intent();
                        intent.putExtra(context.getString(R.string.put_name), parkingLocationList.get(position).getLotName());
                        // retrieve the name of the lot and store it into a String variable
                        String lotName = intent.getStringExtra(context.getString(R.string.getname));

                        //String proximity1 = ds.getValue(ProximityData.class).getProximity();

                        // create a new AlertDialog box
                        final AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                        // inflate the view for the layout from the res folder
                        final View view = LayoutInflater.from(context).inflate(R.layout.activity_lot_detail, null);
                        TextView text = view.findViewById(R.id.textView36);
                        // set the textview to the lot name using the lotName variable from the previous step
                        text.setText(lotName);
                        TextView status = view.findViewById(R.id.textView37);
                        final EditText adminPass = view.findViewById(R.id.editText16);
                        // get the value entered by the user in the EditText field, store into a string variable
                        final String plate = adminPass.getText().toString().trim();
                        //adminPass.setText("admin")

                        Button adminBtn = view.findViewById(R.id.button3);
                        // open gate button
                        adminBtn.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                // check if what is entered by the user equals "admin"
                                if (adminPass.getText().toString().equals("admin")) {

                                    // get a new object of the AdminControl class
                                    AdminControl status = new AdminControl();
                                    // if it equals/matches the "admin" string value then present a success messgae and open the gate
                                    Toast.makeText(context, "Admin Access Activated: Gate is OPEN", Toast.LENGTH_SHORT).show();

                                    // get a instance or reference of the AdminControl class and push the value of 1(open gate) to the AdminControl structure
                                    FirebaseDatabase.getInstance().getReference("AdminControl").child("adminStatus").setValue(status.getAdminEntry());
                                    ImageView adminEntry = view.findViewById(R.id.imageEntry);
                                    // set the color of the entry arrow to blue(indicate the gate is opened through admin access)
                                    adminEntry.setColorFilter(context.getResources().getColor(R.color.colorBlue));
                                } else {
                                    AdminControl status = new AdminControl();
                                    // else don't allow access and present a toast for unsuccessful entry
                                    Toast.makeText(context, "Unauthorized Admin Access! Invalid Credentials", Toast.LENGTH_SHORT).show();
                                    // set the value as false and push a 0 to the AdminControl structure in the adminStatus child
                                    FirebaseDatabase.getInstance().getReference("AdminControl").child("adminStatus").setValue(status.getAdminEntry0());
                                    ImageView adminEntry = view.findViewById(R.id.imageEntry);
                                    // set the color to red for the entry  arrow to indicate entry is not allowed
                                    adminEntry.setColorFilter(context.getResources().getColor(R.color.colorRed));

                                }


                            }
                        });


                        TextView prox = view.findViewById(R.id.textView42);
                        //get the proximity from the ProximityData structure and set the TextView
                        prox.setText("RT Proximity: " + String.valueOf(proximityData.getProximity()));

                        TextView s1 = view.findViewById(R.id.textSlot1);
                        String slot1 = String.valueOf(proximityData.getSlot1A());
                        TextView s2 = view.findViewById(R.id.textSlot2);
                        String slot2 = String.valueOf(proximityData.getSlot2B());
                        TextView s3 = view.findViewById(R.id.textSlot3);
                        String slot3 = String.valueOf(proximityData.getSlot3C());
                        TextView s4 = view.findViewById(R.id.textSlot4);
                        String slot4 = String.valueOf(proximityData.getSlot4D());


                        // reccomended way by Android Studio to basically get the long value of the proximity
                        int value = 0;
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                            value = Math.toIntExact(proximityData.getProximity());
                        }

                        // if the proximity value is less than or equal to 2500
                        if (value <= 2500) {
                            ImageView en = view.findViewById(R.id.imageEntry);
                            // set the color of the entry arrow to green
                            en.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                            ImageView spot1 = view.findViewById(R.id.imageSpot1);
                            spot1.setBackgroundResource(R.color.colorGreen);
                            ImageView spot2 = view.findViewById(R.id.imageSpot2);
                            spot2.setBackgroundResource(R.color.colorRed);
                            ImageView spot3 = view.findViewById(R.id.imageSpot3);
                            spot3.setBackgroundResource(R.color.colorRed);
                            ImageView spot4 = view.findViewById(R.id.imageSpot4);
                            spot4.setBackgroundResource(R.color.colorRed);

                            // set the text to each corresponding status
                            s1.setText("Open");
                            s2.setText("Occupied");
                            s3.setText("Occupied");
                            s4.setText("Occupied");
                            // update the overall status of the lot
                            status.setText(context.getString(R.string.setstatus2) + " Slot 1A is available -- (1/4) ENTRY IS ALLOWED!");
                            //Toast.makeText(context, "LOT HC: ENTRY IS ALLOWED!", Toast.LENGTH_SHORT).show();

                            spot1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Snackbar.make(view, "Do you want to reserve this slot: Slot 1A?", Snackbar.LENGTH_LONG)
                                            .setAction("CANCEL", new View.OnClickListener() { // leave the reservation
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



                                                        // send a notification to the user if the lot information is sent successfully
                                                        Notification notification = new Notification.Builder(context)


                                                                .setTicker(context.getString(R.string.not))
                                                                .setContentTitle(context.getString(R.string.reserved_aspot) + name)
                                                                .setContentText("Slot 1A booked! Please proceed to Payment after selecting your Parking Pass...")
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
                                            // set the status of Slot 1A as occupied
                                            s1.setText("Occupied");
                                            // update the visual spots and arrow colors
                                            ImageView spot1 = view.findViewById(R.id.imageSpot1);
                                            spot1.setBackgroundResource(R.color.colorRed);
                                            ImageView en = view.findViewById(R.id.imageEntry);
                                            en.setColorFilter(context.getResources().getColor(R.color.colorRed));
                                            ImageView ex2 = view.findViewById(R.id.imageExit);
                                            ex2.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                                            TextView status2 = view.findViewById(R.id.textView37);
                                            // update the overall status to indiate all spots are taken
                                            status2.setText(context.getString(R.string.setstatus2) + " All slots are occupied! -- (4/4)");
                                            // push a 0 to the AdminControl data strcuture
                                            FirebaseDatabase.getInstance().getReference("AdminControl").child("adminStatus").setValue(status.getAdminEntry0());


                                        }
                                    }).show();

                                }
                            });
                            // check if proximity value is greater than 5000
                        } else if (value > 5000) {
                            // update the slot color changes
                            ImageView en2 = view.findViewById(R.id.imageEntry);
                            en2.setColorFilter(context.getResources().getColor(R.color.colorRed));
                            ImageView ex5 = view.findViewById(R.id.imageExit);
                            ex5.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                            ImageView spot1 = view.findViewById(R.id.imageSpot1);
                            spot1.setBackgroundResource(R.color.colorRed);
                            // Slot 2B,3C,4D will remain occupied(with color red as the background)
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
                            // if the user clicks on Slot1A when all spots are occupied show a toast message
                            spot1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    Toast.makeText(context," All slots are occupied currently! Please wait a while for a opening...",Toast.LENGTH_SHORT).show();
                                }
                            });
                            // update the overall status
                            status.setText(context.getString(R.string.setstatus2) + " All slots are occupied! -- (4/4) ENTRY IS NOT ALLOWED!");
                            //Toast.makeText(context, "LOT HC: ENTRY IS NOT ALLOWED! LOT IS FULL", Toast.LENGTH_SHORT).show();

                            // initialize a new EntryStatus object
                            EntryStatus stat = new EntryStatus();
                            // get the status and store into a variablee
                            status0 = stat.getEntry();

                            // set this value to the EntryStatus structure for the lotStatus
                            FirebaseDatabase.getInstance().getReference("EntryStatus").child("lotStatus")
                                    .setValue(status0);
                            // check if proximity value is in between the range of open or occupied
                        } else if (value > 2500 && value < 5000) {
                            ImageView en3 = view.findViewById(R.id.imageEntry);
                            en3.setColorFilter(context.getResources().getColor(R.color.colorGreen));
                            // have the Slot 1A change backgrund color to blue
                            ImageView spot1 = view.findViewById(R.id.imageSpot1);
                            spot1.setBackgroundResource(R.color.colorBlue);
                            // update the status
                            status.setText(context.getString(R.string.setstatus2) + " Vehicle is approaching the parking space");
                            // set the TextView of Slot1A to targeted(to indicate vehicle is approaching to Slot1A)
                            s1.setText("Targeted");
                            s2.setText("Occupied");
                            s3.setText("Occupied");
                            s4.setText("Occupied");


                        }

                        // set the view of the dialog to the layout

                        dialog.setView(view);


                        // show the dialog box
                        AlertDialog alertDialog = dialog.create();
                        alertDialog.show();


                    }


                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {


                    }
                });

                // read active changes occuring at the GateStatus data structure(of the IR Break Beam Sensor)
                mDatabase2.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        long gate = dataSnapshot.child("Status").getValue(Long.class);
                        GateStatus g = dataSnapshot.getValue(GateStatus.class);
                        final View view = LayoutInflater.from(context).inflate(R.layout.activity_lot_detail, null);
                        final TextView status = view.findViewById(R.id.textView37);
                        // check if value is equal to 1
                        if (gate == 1) {
                            final Long time = System.currentTimeMillis() / 1000;
                            final String newTime = time.toString();
                            // status.setText(context.getString(R.string.setstatus2) + "License Plate # verification completed!");
                            // retrieve the status of entry and the timestamp from the GateStatus data structure
                            Toast.makeText(context, "HC LOT: 1A5-E8K verified! Car has ENTERED the lot! Time: " + convertTimestamp(g.getTimestamp()), Toast.LENGTH_SHORT).show();
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
                //mDatabase.child(user.getUid()).setValue(parkingLocation1);
                mDatabase.child(FirebaseAuth.getInstance().getCurrentUser().getUid())
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

   // return the size of the array list for the parking location displayed on the main menu
    @Override
    public int getItemCount() {
        return parkingLocationList.size();
    }

    // convert the long format of the timestamp into a readable string form
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