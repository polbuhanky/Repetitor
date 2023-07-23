package com.platovco.repetitor.fragments.tutor.TutorChoiceHigh;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class TutorChoiceHighViewModel extends ViewModel {
    MutableLiveData<ArrayList<String>> highsLD = new MutableLiveData<>(new ArrayList<>());
}