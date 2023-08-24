package com.platovco.repetitor.fragments.tutor.TutorProfile;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.platovco.repetitor.R;

public class TutorProfileFragment extends Fragment {

    private TutorProfileViewModel mViewModel;

    public static TutorProfileFragment newInstance() {
        return new TutorProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutor_profile, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TutorProfileViewModel.class);
        // TODO: Use the ViewModel
    }

}