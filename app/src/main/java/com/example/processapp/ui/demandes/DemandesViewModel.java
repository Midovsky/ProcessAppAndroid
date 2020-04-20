package com.example.processapp.ui.demandes;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DemandesViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public DemandesViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Demandes fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}