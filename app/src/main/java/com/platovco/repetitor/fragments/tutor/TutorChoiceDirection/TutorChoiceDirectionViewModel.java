package com.platovco.repetitor.fragments.tutor.TutorChoiceDirection;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class TutorChoiceDirectionViewModel extends ViewModel {
    MutableLiveData<ArrayList<String>> directionsLD = new MutableLiveData<>(new ArrayList<>());
}