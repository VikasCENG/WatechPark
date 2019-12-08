package com.example.watechpark.ui.Settings;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PaymentViewModel extends ViewModel {
    // TODO: Implement the ViewModel

    private MutableLiveData<String> mText;

    public PaymentViewModel() {

    }

    public LiveData<String> getText() {
        return mText;
    }
}

