package com.platovco.repetitor.fragments.general.AddTutorInformation;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.platovco.repetitor.models.TutorAccount;

public class AddTutorInformationViewModel extends ViewModel {

    public MutableLiveData<String> photoUrl = new MutableLiveData<>();
    public MutableLiveData<Uri> photoUri = new MutableLiveData<>();
    public MutableLiveData<String> experienceLD = new MutableLiveData<>();
    public MutableLiveData<String> nameLD = new MutableLiveData<>();
    public MutableLiveData<String> highLD = new MutableLiveData<>();
    public MutableLiveData<String> directionLD = new MutableLiveData<>();


    public TutorAccount createTutorAccount(){
        return new TutorAccount(photoUrl.getValue(), nameLD.getValue(),
                highLD.getValue(), directionLD.getValue(), experienceLD.getValue());
    }
}