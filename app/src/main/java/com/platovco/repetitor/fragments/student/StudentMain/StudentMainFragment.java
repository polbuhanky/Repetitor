package com.platovco.repetitor.fragments.student.StudentMain;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.bottomnavigation.BottomNavigationView;
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
        binding = FragmentStudentMainBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StudentMainViewModel.class);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        BottomNavigationView mainBNV = binding.mainBNV;
        NavHostFragment host = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.place_holder);
        NavController navController = host.getNavController();
        NavigationUI.setupWithNavController(mainBNV, navController);
    }
}