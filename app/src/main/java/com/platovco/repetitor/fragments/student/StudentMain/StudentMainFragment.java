package com.platovco.repetitor.fragments.student.StudentMain;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.platovco.repetitor.R;
import com.platovco.repetitor.databinding.FragmentStudentMainBinding;

public class StudentMainFragment extends Fragment {

    private StudentMainViewModel mViewModel;
    private FragmentStudentMainBinding binding;

    public static StudentMainFragment newInstance() {
        return new StudentMainFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_student_main, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StudentMainViewModel.class);
        // TODO: Use the ViewModel
    }

}