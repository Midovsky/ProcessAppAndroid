package com.example.processapp.ui.suivi;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class SuiviViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public SuiviViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Suivi fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}