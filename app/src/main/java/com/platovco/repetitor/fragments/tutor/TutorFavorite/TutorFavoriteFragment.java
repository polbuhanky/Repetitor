package com.platovco.repetitor.fragments.tutor.TutorFavorite;

import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.platovco.repetitor.R;

public class TutorFavoriteFragment extends Fragment {

    private TutorFavoriteViewModel mViewModel;

    public static TutorFavoriteFragment newInstance() {
        return new TutorFavoriteFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_tutor_favorite, container, false);
    }


}