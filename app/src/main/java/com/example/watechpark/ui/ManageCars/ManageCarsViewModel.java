package com.example.watechpark.ui.ManageCars;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ManageCarsViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public ManageCarsViewModel() {
        mText = new MutableLiveData<>();

    }

    public LiveData<String> getText() {
        return mText;
    }
}