package com.example.capstone_1.ui.function2;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class function2ViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public function2ViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("기능2");
    }

    public LiveData<String> getText() {
        return mText;
    }
}