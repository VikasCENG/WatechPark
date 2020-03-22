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
        databaseReference = FirebaseDatabase.getInstance().getReference("ParkingLocation");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    for(DataSnapshot ds: dataSnapshot.getChildren()){
                        ParkingPassInfo p = ds.getValue(ParkingPassInfo.class);
                        parkingPassInfoList.add(p);

                        parkingPassInfoList.add(new ParkingPassInfo(getString(R.string.hc), getString(R.string.lot_loc1), 8.50,getString(R.string.pass2),8,getString(R.string.valid2), getString(R.string.t2), 200));



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