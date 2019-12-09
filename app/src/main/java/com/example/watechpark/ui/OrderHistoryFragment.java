package com.example.watechpark.ui;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watechpark.OrderHistoryAdapter;
import com.example.watechpark.Orders;
import com.example.watechpark.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class OrderHistoryFragment extends Fragment {

    private DatabaseReference databaseReference;

    RecyclerView recyclerView;
    OrderHistoryAdapter adapter;
    List<Orders> ordersList;


    private OrderHistoryViewModel orderHistoryViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        orderHistoryViewModel =
                ViewModelProviders.of(this).get(OrderHistoryViewModel.class);
        View root = inflater.inflate(R.layout.order_history_fragment, container, false);

        recyclerView = root.findViewById(R.id.recyclerView4);
        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

       ordersList = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("ParkingLocation").child("Orders");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        Orders o = ds.getValue(Orders.class);
                        ordersList.add(o);

                    }

                    adapter = new OrderHistoryAdapter(getContext(), ordersList);
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {


            }
        });



        return root;
    }
}
