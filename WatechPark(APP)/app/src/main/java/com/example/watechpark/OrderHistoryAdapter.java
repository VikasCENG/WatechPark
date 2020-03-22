package com.example.watechpark;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class OrderHistoryAdapter extends RecyclerView.Adapter<OrderHistoryAdapter.OrderHistoryViewHolder> {

    private Context context;
    private List<Orders> ordersList;

    public OrderHistoryAdapter(Context context, List<Orders> ordersList) {
        this.context = context;
        this.ordersList = ordersList;
    }

    @NonNull
    @Override
    public OrderHistoryAdapter.OrderHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.orderhistory_cards, parent, false);
        return new OrderHistoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final OrderHistoryAdapter.OrderHistoryViewHolder holder,final int position) {

        final Orders orders = ordersList.get(position);

        holder.name.setText(context.getString(R.string.lot) + ordersList.get(position).getConfirmName());
        holder.timePurchased.setText(convertTimestamp(ordersList.get(position).getTimePurchased()));
        holder.oID.setText(context.getString(R.string.order_id)+ ordersList.get(position).getOrderID());
        holder.emailAddress.setText(context.getString(R.string.acct_email1) + ordersList.get(position).getEmail());
        holder.location.setText(context.getString(R.string.locations) + ordersList.get(position).getConfirmLocation());
        holder.type.setText(context.getString(R.string.type1) + ordersList.get(position).getConfirmPassType());
        holder.duration.setText(context.getString(R.string.full_dur) + ordersList.get(position).getConfrimDuration()+context.getString(R.string.hrs));
        holder.validTime.setText(context.getString(R.string.valid_time) + ordersList.get(position).getConfirmValidTime());
        holder.expiredTime.setText(context.getString(R.string.exp_time) + ordersList.get(position).getConfirmExpiryTime());
        holder.remBalance.setText(context.getString(R.string.rem_bal) + context.getString(R.string.dol1)+ordersList.get(position).getConfirmBalance());
        holder.totalCost.setText(context.getString(R.string.tot_cost) + context.getString(R.string.dol2)+ordersList.get(position).getConfirmCost());


    }

    @Override
    public int getItemCount() {
        return ordersList.size();
    }

    private String convertTimestamp(String timestamp){
        long yourSeconds = Long.valueOf(timestamp);
        Date mDate = new Date(yourSeconds * 1000);
        DateFormat df = new SimpleDateFormat(context.getString(R.string.dateformat));
        return df.format(mDate);

    }



    public class OrderHistoryViewHolder extends RecyclerView.ViewHolder {

        TextView timePurchased,name, oID,emailAddress, location, type, duration,validTime, expiredTime, remBalance, totalCost;


        public OrderHistoryViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.textView54);
            timePurchased = itemView.findViewById(R.id.textView67);
            oID = itemView.findViewById(R.id.textView59);
            emailAddress = itemView.findViewById(R.id.textView66);
            location = itemView.findViewById(R.id.textView48);
            type = itemView.findViewById(R.id.textView46);
            duration = itemView.findViewById(R.id.textView51);
            validTime = itemView.findViewById(R.id.textView52);
            expiredTime = itemView.findViewById(R.id.textView49);
            remBalance = itemView.findViewById(R.id.textView56);
            totalCost = itemView.findViewById(R.id.textView55);
        }
    }
}
