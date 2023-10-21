package com.platovco.repetitor.fragments.general.UserChoice;

import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.platovco.repetitor.R;
import com.platovco.repetitor.activities.AuthActivity;
import com.platovco.repetitor.databinding.FragmentUserChoiceBinding;
import com.platovco.repetitor.fragments.general.AddTutorInformation.AddTutorInformationViewModel;
import com.platovco.repetitor.managers.AppwriteManager;

public class UserChoiceFragment extends Fragment {

    private UserChoiceViewModel mViewModel;

    Button btnTutor;
    Button btnStudent;
    Button btnBack;

    private FragmentUserChoiceBinding binding;

    public static UserChoiceFragment newInstance() {
        return new UserChoiceFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentUserChoiceBinding.inflate(getLayoutInflater());
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(UserChoiceViewModel.class);
        init();
    }

    private void init() {
        btnTutor = binding.btnTutor;
        btnStudent = binding.btnStudent;
        btnBack = binding.btnBack;
        btnTutor.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(R.id.action_userChoiceFragment_to_addTutorInformationFragment));
        btnStudent.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(R.id.action_userChoiceFragment_to_addStudentInformationFragment));
        btnBack.setOnClickListener(view -> AppwriteManager.INSTANCE.signOut(AppwriteManager.INSTANCE.getContinuation(
                (tokenResult, throwable) -> {
                    Log.d("AppW Result: ", String.valueOf(tokenResult));
                    Log.d("AppW Exception: ", String.valueOf(throwable));
                    startActivity(new Intent(requireActivity(), AuthActivity.class));
                })));
    }
}