package com.platovco.repetitor.fragments.tutor.TutorChoiceDirection;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.platovco.repetitor.adapters.ChooseDirectionAdapter;
import com.platovco.repetitor.adapters.ChooseHighAdapter;
import com.platovco.repetitor.databinding.FragmentTutorChoiceDirectionBinding;
import com.platovco.repetitor.managers.AppwriteManager;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TutorChoiceDirectionFragment extends Fragment {

    private TutorChoiceDirectionViewModel mViewModel;
    private FragmentTutorChoiceDirectionBinding binding;
    private RecyclerView recyclerView;
    private ImageView backBTN;
    private String brand;
    private CardView searchCV;
    private TextView searchET;
    private ProgressBar progressBar;
    private ChooseDirectionAdapter adapter;
    ArrayList<String> directions = new ArrayList<>();
    ArrayList<String> allDirections = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTutorChoiceDirectionBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TutorChoiceDirectionViewModel.class);
        if (getArguments() != null) {
            brand = getArguments().getString("vuz");
        }
        init();
        AppwriteManager.INSTANCE.getAllDirections(mViewModel.directionsLD, "", AppwriteManager.INSTANCE.getContinuation((result, throwable) -> {
            Log.d("AppW Result: ", String.valueOf(result));
            Log.d("AppW Exception: ", String.valueOf(throwable));
        }));
    }

    private void init(){
        recyclerView = binding.recycleView;
        backBTN = binding.backBTN;
        searchCV = binding.searchCV;
        searchET = binding.searchET;
        progressBar = binding.loadingPB;
        initListeners();
    }

    private void initListeners() {
        mViewModel.directionsLD.observe(getViewLifecycleOwner(), s -> {
            allDirections.clear();
            allDirections.addAll(mViewModel.directionsLD.getValue());
            if (!allDirections.isEmpty()) {
                progressBar.setVisibility(View.GONE);
                searchCV.setVisibility(View.VISIBLE);
            }
            directions.clear();
            directions.addAll(mViewModel.directionsLD.getValue());
            directions = new ArrayList<>(allDirections);
            //adapter = new ChooseDirectionAdapter(getActivity(), directions, TutorChoiceDirectionFragment.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recyclerView.setAdapter(adapter);
        });
        backBTN.setOnClickListener(view -> Navigation.findNavController(view).navigateUp());

        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                AppwriteManager.INSTANCE.getAllDirections(mViewModel.directionsLD, editable.toString(), AppwriteManager.INSTANCE.getContinuation((result, throwable) -> {
                    Log.d("AppW Result: ", String.valueOf(result));
                    Log.d("AppW Exception: ", String.valueOf(throwable));
                }));
            }
        });
    }
}