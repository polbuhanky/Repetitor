package com.platovco.repetitor.fragments.general;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.platovco.repetitor.R;
import com.platovco.repetitor.managers.AppwriteManager;

public class LoadingForIdentifyUserFragment extends Fragment {
    private LoadingForIdentifyUserViewModel mViewModel;
    private final int STUDENT_USER = 1;
    private final int TUTOR_USER = 2;
    private final int NOT_REGISTERED_USER = 3;



    public static LoadingForIdentifyUserFragment newInstance() {
        return new LoadingForIdentifyUserFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_loading_for_identify_user, container, false);
    }

    private void observe(){
        mViewModel.isUserWithInformationLD.observe(getViewLifecycleOwner(), userStatus -> {
            switch (userStatus) {
                case STUDENT_USER:
                    Navigation.findNavController(requireActivity(), R.id.globalNavContainer).navigate(R.id.action_loadingForIdentifyUserFragment_to_studentMainFragment);
                    break;
                case TUTOR_USER:
                    Navigation.findNavController(requireActivity(), R.id.globalNavContainer).navigate(R.id.action_loadingForIdentifyUserFragment_to_tutorMainFragment);
                    break;
                case NOT_REGISTERED_USER:
                    Navigation.findNavController(requireActivity(), R.id.globalNavContainer).navigate(R.id.action_loadingForIdentifyUserFragment_to_userChoiceFragment);
                    break;
            }
        });

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(LoadingForIdentifyUserViewModel.class);
        observe();
        AppwriteManager.INSTANCE.isUserWithInformation(mViewModel.isUserWithInformationLD, AppwriteManager.INSTANCE.getContinuation((result, throwable) -> {
            Log.d("AppW Result: ", result);
            Log.e("AppW Exception: ", String.valueOf(throwable));
        }));
    }

}