package com.platovco.repetitor.fragments.tutor.TutorSearch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.platovco.repetitor.models.cardStudent;

import java.util.ArrayList;

public class TutorSearchViewModel extends ViewModel {
    public MutableLiveData<ArrayList<cardStudent>> studentsLD = new MutableLiveData<>();
}