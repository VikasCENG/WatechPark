package com.example.watechpark;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watechpark.R;
import com.example.watechpark.ui.Home.HomeFragment;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ParkingPassesFragment extends Fragment {

    private ParkingPassesViewModel parkingPassesViewModel;

    RecyclerView recyclerView;
    ParkingPassesAdapter adapter;
    List<ParkingPassInfo> parkingPassInfoList;

    private DatabaseReference databaseReference;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        parkingPassesViewModel =
                ViewModelProviders.of(this).get(ParkingPassesViewModel.class);
        View root = inflater.inflate(R.layout.parking_passes_fragment, container, false);


        parkingPassInfoList = new ArrayList<>();


        recyclerView = root.findViewById(R.id.recylerView3);
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        databaseReference = FirebaseDatabase.getInstance().getReference(getString(R.string.data_parkingpass));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        ParkingPassInfo p = ds.getValue(ParkingPassInfo.class);
                        parkingPassInfoList.add(p);
                        parkingPassInfoList.add(new ParkingPassInfo(getString(R.string.qp), getString(R.string.lot_loc), 6.50, getString(R.string.pass1),3,getString(R.string.valid1), getString(R.string.time1), 200));
                        parkingPassInfoList.add(new ParkingPassInfo(getString(R.string.hc), getString(R.string.lot_loc1), 8.50,getString(R.string.pass2),8,getString(R.string.valid2), getString(R.string.t2), 200));
                        parkingPassInfoList.add(new ParkingPassInfo(getString(R.string.bi), getString(R.string.lot_loc2),6.50,getString(R.string.pass3),12,getString(R.string.val3), getString(R.string.t3), 200));
                        parkingPassInfoList.add(new ParkingPassInfo(getString(R.string.ec), getString(R.string.lot_loc3),9.00,getString(R.string.pass4),2,getString(R.string.val4), getString(R.string.t4), 200));
                        parkingPassInfoList.add(new ParkingPassInfo(getString(R.string.york2), getString(R.string.lot_loc4),8.50,getString(R.string.pass5),8,getString(R.string.val5), getString(R.string.t5), 200));
                        parkingPassInfoList.add(new ParkingPassInfo(getString(R.string.scotia2),getString(R.string.lot_loc5), 8.50,getString(R.string.pss6),12,getString(R.string.val6), getString(R.string.t6), 200));
                        parkingPassInfoList.add(new ParkingPassInfo(getString(R.string.mills2), getString(R.string.lot_loc6),8.50,getString(R.string.pass7),7,getString(R.string.val7), getString(R.string.t7), 200));
                        parkingPassInfoList.add(new ParkingPassInfo(getString(R.string.yong2),getString(R.string.lot_loc7),8.50,getString(R.string.pass8),9,getString(R.string.val8), getString(R.string.t8), 200));
                        parkingPassInfoList.add(new ParkingPassInfo(getString(R.string.fair2), getString(R.string.lot_loc8),8.50,getString(R.string.pass9),12,getString(R.string.val9), getString(R.string.t9), 200));
                        parkingPassInfoList.add(new ParkingPassInfo(getString(R.string.sq),getString(R.string.lot_loc9),8.50,getString(R.string.pass10),3,getString(R.string.val10), getString(R.string.t10), 200));


                    }

                    adapter = new ParkingPassesAdapter(getContext(), parkingPassInfoList);
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