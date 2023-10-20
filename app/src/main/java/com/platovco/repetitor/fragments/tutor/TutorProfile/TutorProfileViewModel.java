package com.platovco.repetitor.fragments.tutor.TutorProfile;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.platovco.repetitor.models.TutorAccount;

public class TutorProfileViewModel extends ViewModel {

    MutableLiveData<TutorAccount> tutorLD = new MutableLiveData<>();
}