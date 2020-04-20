package com.example.processapp.ui.autre1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class Autre1ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public Autre1ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Autre 1 fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}