package com.platovco.repetitor.utils;

import android.text.Editable;
import android.text.TextWatcher;

import androidx.lifecycle.MutableLiveData;

public class CustomTextWatcher implements TextWatcher {
    MutableLiveData<String> mutableLiveData;

    public CustomTextWatcher(MutableLiveData<String> mutableLiveData) {
        this.mutableLiveData = mutableLiveData;
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    public void afterTextChanged(Editable editable) {
        if (String.valueOf(mutableLiveData.getValue()).equals(editable.toString())) return;
        mutableLiveData.setValue(editable.toString());
    }
}
