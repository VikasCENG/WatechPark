package com.example.watechpark.ui.Settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.watechpark.Cars;
import com.example.watechpark.ManageCarsAdapter;
import com.example.watechpark.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ManageCarsFragment extends Fragment {

    private ManageCarsViewModel manageCarsViewModel;
    private DatabaseReference databaseReference;

    RecyclerView recyclerView;
    ManageCarsAdapter adapter;
    List<Cars> carsList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             final ViewGroup container, Bundle savedInstanceState) {
        manageCarsViewModel =
                ViewModelProviders.of(this).get(ManageCarsViewModel.class);
        View root = inflater.inflate(R.layout.manage_cars_fragment, container, false);


        recyclerView = root.findViewById(R.id.recylerView1);
        recyclerView.setHasFixedSize(true);


        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        carsList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Cars");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()){
                        for(DataSnapshot ds: dataSnapshot.getChildren()){
                            Cars c = ds.getValue(Cars.class);
                            carsList.add(c);

                        }

                        adapter = new ManageCarsAdapter(getContext(), carsList);
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

