package com.platovco.repetitor.fragments.general.AddStudentInformation;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.net.Uri;
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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.platovco.repetitor.databinding.FragmentAddStudentInformationBinding;
import com.platovco.repetitor.databinding.FragmentAddTutorInformationBinding;
import com.platovco.repetitor.fragments.general.AddTutorInformation.AddTutorInformationFragment;
import com.platovco.repetitor.fragments.general.AddTutorInformation.AddTutorInformationViewModel;
import com.platovco.repetitor.managers.AppwriteManager;
import com.platovco.repetitor.managers.CompressorManager;
import com.platovco.repetitor.models.StudentAccount;
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

public class AddStudentInformationFragment extends Fragment {

    private FragmentAddStudentInformationBinding binding;
    private ImageView ivAvatar;
    private EditText etName;
    private EditText etExperience;
    private Button btnDone;
    private EditText etHigh;
    private EditText etDirection;
    ArrayList<String> brands = new ArrayList<>();
    ArrayList<String> allBrands = new ArrayList<>();
    ArrayList<String> allDirections = new ArrayList<>();
    ArrayList<String> directions = new ArrayList<>();

    private AddStudentInformationViewModel mViewModel;

    public static AddStudentInformationFragment newInstance() {
        return new AddStudentInformationFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAddStudentInformationBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(AddStudentInformationViewModel.class);
        init();
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
    }

    private void observe() {
        mViewModel.directionLD.observe(getViewLifecycleOwner(), model ->
                etDirection.setText(model));
        mViewModel.highLD.observe(getViewLifecycleOwner(), brand ->
                etHigh.setText(brand));
        mViewModel.photoUri.observe(getViewLifecycleOwner(), uri ->
                Glide.with(requireContext())
                        .load(uri)
                        .into((ImageView) ivAvatar));
    }

    private void initListener() {
        etName.addTextChangedListener( new CustomTextWatcher(mViewModel.nameLD));
        etExperience.addTextChangedListener( new CustomTextWatcher(mViewModel.experienceLD));
        etHigh.addTextChangedListener( new CustomTextWatcher(mViewModel.highLD));
        etDirection.addTextChangedListener( new CustomTextWatcher(mViewModel.directionLD));

        ivAvatar.setOnClickListener(view -> TedImagePicker.with(requireContext())
                .start(uri -> {
                    mViewModel.photoUri.setValue(uri);
                    Glide.with(requireContext())
                            .load(uri)
                            .into((ImageView) view);
                }));
        btnDone.setOnClickListener(view -> createDocument());
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
        @SuppressLint("NotifyDataSetChanged") AddStudentInformationFragment.PhotosDownloadedCallback photosDownloadedCallback = () -> {
            StudentAccount studentAccount = mViewModel.createStudentAccount();
            AppwriteManager.INSTANCE.addStudentAccount(studentAccount, AppwriteManager.INSTANCE.getContinuation((s, throwable) -> {
                if (throwable != null) Log.e("add", String.valueOf(throwable));
                Handler handler = new Handler(Looper.getMainLooper());
                handler.post(() ->
                    Navigation.findNavController(requireActivity(), R.id.fragmentContainerView).navigate(R.id.action_addStudentInformationFragment_to_studentMainFragment));
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

    public void sendPhoto(Uri uri, String uuid, AddStudentInformationFragment.PhotosDownloadedCallback photosSentCallback) {
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
        try{
            AppwriteManager.INSTANCE.createFileStudentStorage(uuid, file, AppwriteManager.INSTANCE.getContinuation((result, throwable) -> {
                Log.d("AppW Result: ", String.valueOf(result));
                Log.d("AppW Exception: ", String.valueOf(throwable));
            }));
            Callable<String> callable = () -> "http://89.253.219.76/v1/storage/buckets/65251e9c04cdd06bcec8/files/"+uuid+"/view?project=649d4dbdcf623484dd45";
            return Observable.fromCallable(callable);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
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

    private interface PhotosDownloadedCallback {
        void allPhotosDownloaded();
    }




}