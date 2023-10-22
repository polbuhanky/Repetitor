package com.platovco.repetitor.fragments.general;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class LoadingForIdentifyUserViewModel extends ViewModel {
    MutableLiveData<Integer> isUserWithInformationLD = new MutableLiveData<>();
}