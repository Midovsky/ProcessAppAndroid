package com.example.processapp.ui.autre2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Autre2ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Autre2ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Autre 2 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}