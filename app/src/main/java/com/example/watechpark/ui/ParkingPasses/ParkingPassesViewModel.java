package com.example.watechpark.ui.ParkingPasses;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ParkingPassesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ParkingPassesViewModel() {
        mText = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return mText;
    }
}