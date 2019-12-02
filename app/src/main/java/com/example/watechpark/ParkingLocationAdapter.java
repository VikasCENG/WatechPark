package com.example.watechpark;

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
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ParkingLocationAdapter extends RecyclerView.Adapter<ParkingLocationAdapter.ParkingLocationViewHolder> {

    private Context context;
    private List<ParkingLocation> parkingLocationList;



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
            holder.lotDistance.setText(String.valueOf(parkingLocation.getLotDistance()+"m"));
            holder.lotCost.setText("$"+String.valueOf(parkingLocation.getCost()));

            holder.lotImage.setImageDrawable(context.getResources().getDrawable(parkingLocation.getLotImage(), null));




            holder.viewDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent();
                    intent.putExtra("lot_name", parkingLocationList.get(position).getLotName());
                    intent.putExtra("lot_cost", String.valueOf(parkingLocationList.get(position).getCost()));
                    intent.putExtra("lot_image", parkingLocation.getLotImage());
                    String lotName = intent.getStringExtra("lot_name");
                    String lotCost = intent.getStringExtra("lot_cost");
                    int lotImage = intent.getIntExtra("lot_image", 0);

                   AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    final View view = LayoutInflater.from(context).inflate(R.layout.activity_lot_detail, null);
                    TextView text = view.findViewById(R.id.textView36);
                    text.setText(lotName);
                    TextView cost = view.findViewById(R.id.textView41);
                    cost.setText("$"+lotCost);
                    ImageView img = view.findViewById(R.id.imageView8);
                    img.setImageResource(lotImage);
                    dialog.setView(view);

                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();

                }
            });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.putExtra("lot_name", parkingLocationList.get(position).getLotName());
                    intent.putExtra("lot_cost", String.valueOf(parkingLocationList.get(position).getCost()));
                    intent.putExtra("lot_image", parkingLocation.getLotImage());
                    String lotName = intent.getStringExtra("lot_name");
                    String lotCost = intent.getStringExtra("lot_cost");
                    int lotImage = intent.getIntExtra("lot_image", 0);

                    AlertDialog.Builder dialog = new AlertDialog.Builder(context);
                    final View view = LayoutInflater.from(context).inflate(R.layout.activity_lot_detail, null);
                    TextView text = view.findViewById(R.id.textView36);
                    text.setText(lotName);
                    TextView cost = view.findViewById(R.id.textView41);
                    cost.setText("$"+lotCost);
                    ImageView img = view.findViewById(R.id.imageView8);
                    img.setImageResource(lotImage);
                    dialog.setView(view);

                    AlertDialog alertDialog = dialog.create();
                    alertDialog.show();

            }
        });

        holder.reserveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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

        TextView detailName;


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
