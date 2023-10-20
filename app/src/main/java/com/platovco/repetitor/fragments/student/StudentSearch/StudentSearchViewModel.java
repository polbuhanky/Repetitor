package com.platovco.repetitor.fragments.student.StudentSearch;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.platovco.repetitor.models.cardStudent;
import com.platovco.repetitor.models.cardTutor;

import java.util.ArrayList;

public class StudentSearchViewModel extends ViewModel {
    public MutableLiveData<ArrayList<cardTutor>> tutorsLD = new MutableLiveData<>();
}