package com.example.capstone_1.ui.function3;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class function3ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public function3ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("기능3");
    }

    public LiveData<String> getText() {
        return mText;
    }
}