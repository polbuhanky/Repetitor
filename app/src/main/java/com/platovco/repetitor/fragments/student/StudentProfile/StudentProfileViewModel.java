package com.platovco.repetitor.fragments.student.StudentProfile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.platovco.repetitor.models.StudentAccount;
public class StudentProfileViewModel extends ViewModel {
    MutableLiveData<StudentAccount> studentLD = new MutableLiveData<>();
}