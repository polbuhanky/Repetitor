package com.platovco.repetitor.fragments.tutor.TutorSearch;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.platovco.repetitor.adapters.CardAdapterTutor;
import com.platovco.repetitor.databinding.FragmentTutorSearchBinding;
import com.platovco.repetitor.managers.AppwriteManager;
import com.platovco.repetitor.models.cardStudent;
import com.yuyakaido.android.cardstackview.CardStackLayoutManager;
import com.yuyakaido.android.cardstackview.CardStackView;

import org.jetbrains.annotations.Contract;

import java.util.ArrayList;

public class TutorSearchFragment extends Fragment {

    private FragmentTutorSearchBinding binding;
    private TutorSearchViewModel mViewModel;
    private CardStackView cardStack;
    ArrayList<cardStudent> allStudents = new ArrayList<>();
    ArrayList<cardStudent> students = new ArrayList<>();

    public static TutorSearchFragment newInstance() {
        return new TutorSearchFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTutorSearchBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TutorSearchViewModel.class);
        AppwriteManager.INSTANCE.getAllStudent(mViewModel.studentsLD, AppwriteManager.INSTANCE.getContinuation((result, throwable) -> {
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
        mViewModel.studentsLD.observe(getViewLifecycleOwner(), s  ->{
            allStudents.clear();
            allStudents.addAll(mViewModel.studentsLD.getValue());
            students = new ArrayList<cardStudent>(allStudents);
            CardAdapterTutor adapter = new CardAdapterTutor(students, this.getActivity());
            binding.cardStack.setLayoutManager(new CardStackLayoutManager(requireContext().getApplicationContext()));
            binding.cardStack.setAdapter(adapter);
        });
    }

    private void observe(){}

}