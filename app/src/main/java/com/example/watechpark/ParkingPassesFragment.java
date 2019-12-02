package com.example.watechpark;



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

import com.example.watechpark.R;

public class ParkingPassesFragment extends Fragment {

    private ParkingPassesViewModel parkingPassesViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        parkingPassesViewModel =
                ViewModelProviders.of(this).get(ParkingPassesViewModel.class);
        View root = inflater.inflate(R.layout.parking_passes_fragment, container, false);

        return root;
    }
}