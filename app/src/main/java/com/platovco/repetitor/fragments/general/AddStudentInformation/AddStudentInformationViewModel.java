package com.platovco.repetitor.fragments.general.AddStudentInformation;

import android.net.Uri;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.platovco.repetitor.models.StudentAccount;
import com.platovco.repetitor.models.TutorAccount;

import java.util.ArrayList;
import java.util.Objects;

public class AddStudentInformationViewModel extends ViewModel {
    public MutableLiveData<String> photoUrl = new MutableLiveData<>();
    public MutableLiveData<Uri> photoUri = new MutableLiveData<>();
    public MutableLiveData<String> nameLD = new MutableLiveData<>();
    public MutableLiveData<String> ageLD = new MutableLiveData<>();


    public StudentAccount createStudentAccount() {
        return new StudentAccount(photoUrl.getValue(), nameLD.getValue(),
                Integer.valueOf(Objects.requireNonNull(ageLD.getValue())));
    }
}