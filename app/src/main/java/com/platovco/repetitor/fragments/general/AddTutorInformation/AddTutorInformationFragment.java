package com.platovco.repetitor.fragments.general.AddTutorInformation;

import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferListener;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferObserver;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferState;
import com.amazonaws.mobileconnectors.s3.transferutility.TransferUtility;
import com.amazonaws.services.s3.AmazonS3Client;
import com.bumptech.glide.Glide;
import com.platovco.repetitor.R;
import com.platovco.repetitor.adapters.ChooseDirectionAdapter;
import com.platovco.repetitor.adapters.ChooseHighAdapter;
import com.platovco.repetitor.databinding.FragmentAddTutorInformationBinding;
import com.platovco.repetitor.managers.AppwriteManager;
import com.platovco.repetitor.managers.CompressorManager;
import com.platovco.repetitor.models.TutorAccount;
import com.platovco.repetitor.utils.CustomTextWatcher;
import com.platovco.repetitor.utils.PhotoUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import gun0912.tedimagepicker.builder.TedImagePicker;
import io.appwrite.models.User;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.processors.PublishProcessor;

public class AddTutorInformationFragment extends Fragment {

    private FragmentAddTutorInformationBinding binding;
    private ImageView ivAvatar;
    private EditText etName;
    private EditText etExperience;
    private Button btnDone;
    private EditText etHigh;
    private EditText etDirection;
    private RecyclerView rvHigh;
    private RecyclerView rvDirection;
    ArrayList<String> brands = new ArrayList<>();
    ArrayList<String> allBrands = new ArrayList<>();
    private ChooseHighAdapter adapterHigh;
    ArrayList<String> allDirections = new ArrayList<>();
    ArrayList<String> directions = new ArrayList<>();
    private ChooseDirectionAdapter adapterDirections;


    private AddTutorInformationViewModel mViewModel;

    public static AddTutorInformationFragment newInstance() {
        return new AddTutorInformationFragment();
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddTutorInformationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddTutorInformationViewModel.class);
        init();
        AppwriteManager.INSTANCE.getAllHighs(mViewModel.highsLD, "", AppwriteManager.INSTANCE.getContinuation((result, throwable) -> {
            Log.d("AppW Result: ", String.valueOf(result));
            Log.d("AppW Exception: ", String.valueOf(throwable));
        }));
        AppwriteManager.INSTANCE.getAllDirections(mViewModel.directionsLD, "", AppwriteManager.INSTANCE.getContinuation((result, throwable) -> {
            Log.d("AppW Result: ", String.valueOf(result));
            Log.d("AppW Exception: ", String.valueOf(throwable));
        }));
        observe();
        initListener();
    }

    private void init(){
        ivAvatar = binding.piAvatar;
        etName = binding.etName;
        etExperience = binding.etExperience;
        btnDone = binding.btnDone;
        etHigh = binding.etHigh;
        etDirection = binding.etDirection;
        rvHigh = binding.rvHigh;
        rvDirection = binding.rvDirection;
    }

    private void initListener(){
        etName.addTextChangedListener( new CustomTextWatcher(mViewModel.nameLD));
        etExperience.addTextChangedListener( new CustomTextWatcher(mViewModel.experienceLD));
        getParentFragmentManager().setFragmentResultListener("brandKey", this, (key, bundle) -> {
            String high = bundle.getString("high");
            etHigh.setText(high);
            mViewModel.highLD.setValue(high);
            mViewModel.directionLD.setValue(null);
        });

        getParentFragmentManager().setFragmentResultListener("modelKey", this, (key, bundle) -> {
            String direction = bundle.getString("direction");
            etDirection.setText(direction);
            mViewModel.directionLD.setValue(direction);
        });

        mViewModel.highsLD.observe(getViewLifecycleOwner(), s -> {
            allBrands.clear();
            allBrands.addAll(mViewModel.highsLD.getValue());
            //if (!allBrands.isEmpty()) {
            //    progressBar.setVisibility(View.GONE);
            //    searchCV.setVisibility(View.VISIBLE);
            //}
            brands = new ArrayList<>(allBrands);
            adapterHigh = new ChooseHighAdapter(getActivity(), brands, AddTutorInformationFragment.this);
            rvHigh.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            rvHigh.setAdapter(adapterHigh);
        });
        etHigh.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void afterTextChanged(Editable editable) {
                AppwriteManager.INSTANCE.getAllHighs(mViewModel.highsLD, editable.toString(), AppwriteManager.INSTANCE.getContinuation((result, throwable) -> {
                    Log.d("AppW Result: ", String.valueOf(result));
                    Log.d("AppW Exception: ", String.valueOf(throwable));
                }));

            }
        });

        mViewModel.directionsLD.observe(getViewLifecycleOwner(), s -> {
            allDirections.clear();
            allDirections.addAll(mViewModel.directionsLD.getValue());
            directions.clear();
            directions.addAll(mViewModel.directionsLD.getValue());
            directions = new ArrayList<>(allDirections);
            adapterDirections = new ChooseDirectionAdapter(getActivity(), directions, AddTutorInformationFragment.this);
            rvDirection.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            rvDirection.setAdapter(adapterDirections);
        });

        etDirection.addTextChangedListener(new TextWatcher() {
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



        ivAvatar.setOnClickListener(view -> TedImagePicker.with(requireContext())
                .start(uri -> {
                    mViewModel.photoUri.setValue(uri);
                    Glide.with(requireContext())
                            .load(uri)
                            .into((ImageView) view);
                }));
        btnDone.setOnClickListener(view -> createDocument());
        btnDone.setOnClickListener(view -> Navigation.findNavController(view)
                .navigate(R.id.action_addTutorInformationFragment_to_tutorMainFragment));

    }

    private void observe () {
        mViewModel.directionLD.observe(getViewLifecycleOwner(), model ->
                etDirection.setText(model));
        mViewModel.highLD.observe(getViewLifecycleOwner(), brand ->
                etHigh.setText(brand));
        mViewModel.photoUri.observe(getViewLifecycleOwner(), uri ->
                Glide.with(requireContext())
                        .load(uri)
                        .into((ImageView) ivAvatar));
    }

    private void createDocument(){
        if (mViewModel.nameLD.getValue() == null) {
            Toast.makeText(getContext(), "Введите ваше ФИО", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mViewModel.photoUri.getValue() == null) {
            Toast.makeText(getContext(), "Прикрепите ваше фото", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mViewModel.highLD.getValue() == null) {
            Toast.makeText(getContext(), "Введите ВУЗ", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mViewModel.directionLD.getValue() == null) {
            Toast.makeText(getContext(), "Введите направление", Toast.LENGTH_SHORT).show();
            return;
        }
        if (mViewModel.experienceLD.getValue() == null) {
            Toast.makeText(getContext(), "Введите стаж", Toast.LENGTH_SHORT).show();
            return;
        }
        @SuppressLint("NotifyDataSetChanged") AddTutorInformationFragment.PhotosDownloadedCallback photosDownloadedCallback = () -> {
            TutorAccount tutorAccount = mViewModel.createTutorAccount();
            AppwriteManager.INSTANCE.addTutorAccount(tutorAccount, AppwriteManager.INSTANCE.getContinuation((s, throwable) -> {
                if (throwable != null) Log.e("add", String.valueOf(throwable));
                Handler handler = new Handler(Looper.getMainLooper());
                //handler.post(() ->
                        //Navigation.findNavController(requireActivity(), R.id.globalNavContainer).navigate(R.id.action_addActiveInformationFragment_to_mainFragment));
            }));
        };
        if (mViewModel.photoUri.getValue() == null) {
            photosDownloadedCallback.allPhotosDownloaded();
        } else {
            MutableLiveData<User<Map<String, Object>>> liveData = new MutableLiveData<>();
            AppwriteManager.INSTANCE.getAccount(liveData, AppwriteManager.INSTANCE.getContinuation((s, throwable) -> {
            }));
            liveData.observe(getViewLifecycleOwner(), objectAccount ->
                    sendPhoto(mViewModel.photoUri.getValue(), objectAccount.getId(), photosDownloadedCallback));
        }
    }

    public Observable<File> compressImage(File file) {
        PublishProcessor<File> myDelayedObservable = PublishProcessor.create();
        CompressorManager.INSTANCE.compress(requireContext(), file, CompressorManager.INSTANCE.getContinuation((compressedFile, throwable) -> {
            myDelayedObservable.onNext(compressedFile);
            myDelayedObservable.onComplete();

        }));
        return Observable.fromPublisher(myDelayedObservable);
    }

    public void sendPhoto(Uri uri, String uuid, AddTutorInformationFragment.PhotosDownloadedCallback photosSentCallback) {
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() ->
                Observable.just(uri)
                        .flatMap(imageUri -> compressImage(new File(Objects.requireNonNull(PhotoUtil.getPathFromUri(requireContext(), imageUri)))))
                        .flatMap(compressedImage -> Objects.requireNonNull(uploadImage(compressedImage, uuid))
                                .subscribeOn(AndroidSchedulers.mainThread()))
                        .subscribe(
                                url -> {
                                    Log.d("Загружен URL: ", String.valueOf(url));
                                    mViewModel.photoUrl.setValue(url);
                                },
                                Throwable::printStackTrace,
                                () -> {
                                    Log.d("Загружены URL: ", "Все изображения были загружены");
                                    photosSentCallback.allPhotosDownloaded();
                                }
                        ));
    }

    private Observable<String> uploadImage(File file, String uuid) {
        try {
            BasicAWSCredentials creds = new BasicAWSCredentials("YCAJE13hLTon0cjh9HNs2w7Nw", "YCOBkvy7bLg-d413FJ2Ey-raB8LcJeg2xM-XcFWO");
            AmazonS3Client s3Client = new AmazonS3Client(creds);
            Callable<String> callable = () -> s3Client.getUrl("mentorium.userphotos", uuid).toString();
            s3Client.setEndpoint("storage.yandexcloud.net");
            TransferUtility trans = TransferUtility.builder().context(getActivity().getApplicationContext()).s3Client(s3Client).build();
            TransferObserver transferObserver = trans.upload("mentorium.userphotos", uuid, file);
            transferObserver.setTransferListener(new TransferListener() {
                @Override
                public void onStateChanged(int id, TransferState state) {
                    try {

                        callable.call();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onProgressChanged(int id, long bytesCurrent, long bytesTotal) {
                    Log.d("io", "km");
                }

                @Override
                public void onError(int id, Exception ex) {
                    Log.e("error", ex.getMessage());

                }
            });
            return Observable.fromCallable(callable);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    private interface PhotosDownloadedCallback {
        void allPhotosDownloaded();
    }
}