package com.example.watechpark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
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
      View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card, parent, false);
      return new ParkingLocationViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ParkingLocationAdapter.ParkingLocationViewHolder holder, int position) {
            ParkingLocation parkingLocation = parkingLocationList.get(position);
            holder.lotName.setText(parkingLocation.getLotName());
            holder.lotLocation.setText(parkingLocation.getLotLocation());
            holder.lotDistance.setText(String.valueOf(parkingLocation.getLotDistance()));
            holder.lotCost.setText(String.valueOf(parkingLocation.getCost()));

            holder.lotImage.setImageDrawable(context.getResources().getDrawable(parkingLocation.getLotImage(), null));
    }

    @Override
    public int getItemCount() {
        return parkingLocationList.size();
    }

    public class ParkingLocationViewHolder extends RecyclerView.ViewHolder {

        ImageView lotImage;
        TextView lotName, lotLocation, lotDistance, lotCost;
        Button viewDetails, reserveButton;
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
