package com.example.watechpark;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ParkingPassesViewModel extends ViewModel {
    // TODO: Implement the ViewModel


    private MutableLiveData<String> mText;

    public ParkingPassesViewModel() {


    }

    public LiveData<String> getText() {
        return mText;
    }
}

