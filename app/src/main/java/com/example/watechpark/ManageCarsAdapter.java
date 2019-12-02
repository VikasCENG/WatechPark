package com.example.watechpark;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ManageCarsAdapter extends RecyclerView.Adapter<ManageCarsAdapter.ManageCarsViewHolder> {


    private Context context;
    private List<Cars> cars;

    DatabaseReference databaseReference;

    private FirebaseAuth mAuth2;
    private FirebaseUser user2;

    public ManageCarsAdapter(Context context, List<Cars> cars) {
        this.context = context;
        this.cars = cars;
    }

    @NonNull
    @Override
    public ManageCarsAdapter.ManageCarsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.manage_cards, parent, false);
        return new ManageCarsViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ManageCarsAdapter.ManageCarsViewHolder holder, final int position) {

        final Cars car = cars.get(position);
        holder.make.setText("Make: " + car.getMake());
        holder.model.setText("Model: " + car.getModel());
        holder.color.setText("Color: " +car.getColor());
        holder.licensePlate.setText(car.getLplate());
        holder.timeAdded.setText("Time Added: " + (convertTimestamp(car.getTimestamp())));

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               applyChanges();


            }
        });



        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Delete Car From Inventory");
                builder.setMessage("Are you sure you want to delete this car?");
                builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mAuth2 = FirebaseAuth.getInstance();
                        user2 = mAuth2.getCurrentUser();
                        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cars").child(user2.getUid());
                        databaseReference.removeValue();
                        dialog.dismiss();
                        Toast.makeText(context, "Car is deleted from inventory...", Toast.LENGTH_SHORT).show();
                    }
                });

                builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog alert = builder.create();
                alert.show();

            }
        });

    }



    @Override
    public int getItemCount() {
        return cars.size();
    }


    public class ManageCarsViewHolder extends RecyclerView.ViewHolder {


        TextView make, model, color, licensePlate, timeAdded;
        Button edit, delete;
        public ManageCarsViewHolder(@NonNull View itemView) {
            super(itemView);


            make = itemView.findViewById(R.id.textView43);
            model = itemView.findViewById(R.id.textView34);
            color = itemView.findViewById(R.id.textView33);
            licensePlate = itemView.findViewById(R.id.textView44);
            timeAdded = itemView.findViewById(R.id.textView35);
            edit = itemView.findViewById(R.id.button9);
            delete = itemView.findViewById(R.id.button12);

        }
    }

    private void applyChanges(){

        AlertDialog.Builder dialog = new AlertDialog.Builder(context);
        final View view = LayoutInflater.from(context).inflate(R.layout.edit_fields, null);

        dialog.setView(view);

        final EditText editMake = view.findViewById(R.id.update_make);
        final EditText editModel = view.findViewById(R.id.update_model);
        final EditText editColor = view.findViewById(R.id.update_color);
        final EditText editPlate = view.findViewById(R.id.update_number);
        final TextView editTime = view.findViewById(R.id.textView45);
        final Button applyChange = view.findViewById(R.id.button_update);

        applyChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newMake = editMake.getText().toString().trim();
                String newModel = editModel.getText().toString().trim();
                String newColor = editColor.getText().toString().trim();
                String newNum = editPlate.getText().toString().trim();
                final Long time = System.currentTimeMillis() / 1000;
                final String newTime = time.toString();

                editTime.setText(convertTimestamp(newTime));

                if(TextUtils.isEmpty(newMake)){
                    Toast.makeText(context,"Missing make", Toast.LENGTH_SHORT).show();
                }else{
                    updateChanges(newMake,newModel,newColor,newNum,newTime);
                }



            }
        });



        dialog.setTitle("Edit Car");

        AlertDialog alertDialog = dialog.create();
        alertDialog.show();

    }

    private String convertTimestamp(String timestamp){
        long yourSeconds = Long.valueOf(timestamp);
        Date mDate = new Date(yourSeconds * 1000);
        DateFormat df = new SimpleDateFormat("dd MMM yyyy hh:mm:ss a ");
        return df.format(mDate);

    }

    private void updateChanges(String make, String model, String color, String number, String time){
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Cars").child(FirebaseAuth.getInstance().getCurrentUser().getUid());

        Cars cars2 = new Cars(make,model,color,number,time);

        databaseReference.setValue(cars2);

        Toast.makeText(context, "Car is updated", Toast.LENGTH_SHORT).show();


    }



    }





