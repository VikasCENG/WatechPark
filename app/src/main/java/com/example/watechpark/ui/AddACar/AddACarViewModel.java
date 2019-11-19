package com.example.watechpark.ui.AddACar;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class AddACarViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public AddACarViewModel() {
        mText = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return mText;
    }
}