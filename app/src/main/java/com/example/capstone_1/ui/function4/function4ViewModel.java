package com.example.capstone_1.ui.function4;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class function4ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public function4ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("기능4");
    }

    public LiveData<String> getText() {
        return mText;
    }
}