package com.platovco.repetitor.fragments.general.AddStudentInformation;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.platovco.repetitor.models.StudentAccount;
import com.platovco.repetitor.models.TutorAccount;

import java.util.ArrayList;

public class AddStudentInformationViewModel extends ViewModel {
    public MutableLiveData<String> photoUrl = new MutableLiveData<>();
    public MutableLiveData<Uri> photoUri = new MutableLiveData<>();
    public MutableLiveData<String> experienceLD = new MutableLiveData<>();
    public MutableLiveData<String> nameLD = new MutableLiveData<>();
    public MutableLiveData<String> highLD = new MutableLiveData<>();
    public MutableLiveData<String> directionLD = new MutableLiveData<>();
    MutableLiveData<ArrayList<String>> highsLD = new MutableLiveData<>(new ArrayList<>());
    MutableLiveData<ArrayList<String>> directionsLD = new MutableLiveData<>(new ArrayList<>());

    public StudentAccount createStudentAccount(){
        return new StudentAccount(photoUrl.getValue(), nameLD.getValue(),
                highLD.getValue(), directionLD.getValue(), experienceLD.getValue());
    }
}