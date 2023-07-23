package com.platovco.repetitor.fragments.tutor.TutorChoiceHigh;

import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
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

import com.platovco.repetitor.adapters.ChooseHighAdapter;
import com.platovco.repetitor.databinding.FragmentTutorChoiceHighBinding;
import com.platovco.repetitor.managers.AppwriteManager;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class TutorChoiceHighFragment extends Fragment {


    private TutorChoiceHighViewModel mViewModel;
    private FragmentTutorChoiceHighBinding binding;
    private RecyclerView recyclerView;
    private ImageView backBTN;
    private CardView searchCV;
    private TextView searchET;
    private ProgressBar progressBar;
    private ChooseHighAdapter adapter;
    ArrayList<String> brands = new ArrayList<>();
    ArrayList<String> allBrands = new ArrayList<>();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTutorChoiceHighBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TutorChoiceHighViewModel.class);
        init();
        AppwriteManager.INSTANCE.getAllHighs(mViewModel.highsLD, AppwriteManager.INSTANCE.getContinuation((result, throwable) -> {
            Log.d("AppW Result: ", String.valueOf(result));
            Log.d("AppW Exception: ", String.valueOf(throwable));
        }));
    }

    private void initListeners(){
        mViewModel.highsLD.observe(getViewLifecycleOwner(), s -> {
            allBrands.clear();
            allBrands.addAll(mViewModel.highsLD.getValue());
            if (!allBrands.isEmpty()) {
                progressBar.setVisibility(View.GONE);
                searchCV.setVisibility(View.VISIBLE);
            }
            brands = new ArrayList<>(allBrands);
            adapter = new ChooseHighAdapter(getActivity(), brands, TutorChoiceHighFragment.this);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            recyclerView.setAdapter(adapter);
        });
        backBTN.setOnClickListener(view ->
                Navigation.findNavController(view).navigateUp());
        searchET.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void afterTextChanged(Editable editable) {
                ExecutorService executor = Executors.newSingleThreadExecutor();
                executor.execute(() ->
                {
                    brands.clear();
                    for (String brand : allBrands) {
                        if (brand.toLowerCase(Locale.ROOT).contains(editable.toString().toLowerCase(Locale.ROOT))) {
                            brands.add(brand);
                        }
                    }
                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(() -> adapter.notifyDataSetChanged());
                });
            }
        });
    }

    private void init(){
        recyclerView = binding.recycleView;
        backBTN = binding.backBTN;
        progressBar = binding.loadingPB;
        searchCV = binding.searchCV;
        searchET = binding.searchET;
        initListeners();
    }
}