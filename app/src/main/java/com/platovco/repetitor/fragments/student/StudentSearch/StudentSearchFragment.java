package com.platovco.repetitor.fragments.student.StudentSearch;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.platovco.repetitor.adapters.CardAdapterStudent;
import com.platovco.repetitor.databinding.FragmentStudentSearchBinding;
import com.platovco.repetitor.managers.AppwriteManager;
import com.platovco.repetitor.models.cardTutor;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;


import java.util.ArrayList;

public class StudentSearchFragment extends Fragment {

    private FragmentStudentSearchBinding binding;
    private StudentSearchViewModel mViewModel;
    private CardStackView cardStack;
    ArrayList<cardTutor> allTutors = new ArrayList<>();
    ArrayList<cardTutor> tutors = new ArrayList<>();

    public static StudentSearchFragment newInstance() {
        return new StudentSearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentStudentSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StudentSearchViewModel.class);
        AppwriteManager.INSTANCE.getAllTutor(mViewModel.tutorsLD, AppwriteManager.INSTANCE.getContinuation((result, throwable) -> {
            Log.d("AppW Result: ", String.valueOf(result));
            Log.d("AppW Exception: ", String.valueOf(throwable));
        }));

        init();
        initListener();
        observe();
    }

    private void init(){
        cardStack = binding.cardStack;
    }

    private void initListener(){
        mViewModel.tutorsLD.observe(getViewLifecycleOwner(), s  ->{
            allTutors.clear();
            allTutors.addAll(mViewModel.tutorsLD.getValue());
            tutors = new ArrayList<cardTutor>(allTutors);
            CardAdapterStudent adapter = new CardAdapterStudent(tutors, this.getActivity());
            binding.cardStack.setLayoutManager(new CardStackLayoutManager(requireContext().getApplicationContext()));
            binding.cardStack.setAdapter(adapter);
        });
    }

    private void observe(){}

}