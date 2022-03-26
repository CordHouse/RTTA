package com.example.capstone_1.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class mainViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public mainViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("메인");
    }

    public LiveData<String> getText() {
        return mText;
    }
}