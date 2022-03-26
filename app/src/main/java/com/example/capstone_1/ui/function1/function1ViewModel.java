package com.example.capstone_1.ui.function1;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class function1ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public function1ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("기능1");
    }

    public LiveData<String> getText() {
        return mText;
    }
}