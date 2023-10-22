package com.platovco.repetitor.fragments.student.StudentProfile;

import androidx.lifecycle.ViewModelProvider;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.platovco.repetitor.R;
import com.platovco.repetitor.databinding.FragmentStudentProfileBinding;
import com.platovco.repetitor.fragments.tutor.TutorProfile.TutorProfileViewModel;
import com.platovco.repetitor.managers.AppwriteManager;
import com.platovco.repetitor.managers.CompressorManager;
import com.platovco.repetitor.models.StudentAccount;
import com.platovco.repetitor.models.TutorAccount;
import com.platovco.repetitor.utils.PhotoUtil;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.Callable;

import gun0912.tedimagepicker.builder.TedImagePicker;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.processors.PublishProcessor;

public class StudentProfileFragment extends Fragment {

    private StudentProfileViewModel mViewModel;
    private FragmentStudentProfileBinding binding;

    TextView nameTV;
    ImageView userPhotoIV;
    TextView educationTV;
    TextView directionTV;
    TextView experienceTV;
    //ImageView editBTN;

    public static StudentProfileFragment newInstance() {
        return new StudentProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentStudentProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(StudentProfileViewModel.class);
        AppwriteManager.INSTANCE.getStudent(mViewModel.studentLD, AppwriteManager.INSTANCE.getContinuation((result, throwable) -> {
            Log.d("AppW Result: ", String.valueOf(result));
            Log.d("AppW Exception: ", String.valueOf(throwable));
        }));
        init();
        observe();
    }

    private void init(){
        nameTV = binding.tvName;
        userPhotoIV = binding.ivAvatar;
        educationTV = binding.tvEducation;
        directionTV = binding.tvDirection;
        experienceTV = binding.tvExperience;
        userPhotoIV.setOnClickListener(view -> TedImagePicker.with(requireContext())
                .title("Выбрать")
                .start(uri -> {
                    Glide.with(requireContext())
                            .load(uri)
                            .centerCrop()
                            .into((ImageView) view);
                    updateImage(uri);
                }));
    }

    private Observable<String> uploadImage(File file, String uuid) {
        try{
            AppwriteManager.INSTANCE.createFileStudentStorage(uuid, file, AppwriteManager.INSTANCE.getContinuation((result, throwable) -> {
                Log.d("AppW Result: ", String.valueOf(result));
                Log.d("AppW Exception: ", String.valueOf(throwable));
            }));
            Callable<String> callable = () -> "http://89.253.219.76/v1/storage/buckets/652510bc23dcaf951fff/files/"+uuid+"/view?project=649d4dbdcf623484dd45";
            return Observable.fromCallable(callable);
        }
        catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @SuppressLint("CheckResult")
    private void updateImage(Uri uri){
        Observable.just(uri)
                .flatMap(imageUri -> compressImage(new File(Objects.requireNonNull(PhotoUtil.getPathFromUri(requireContext(), imageUri)))))
                .flatMap(compressedImage -> Objects.requireNonNull(uploadImage(compressedImage, String.valueOf(Objects.requireNonNull(mViewModel.studentLD.getValue()).getUuid())))
                        .subscribeOn(AndroidSchedulers.mainThread()))
                .subscribe(
                        url -> Log.d("Загружен URL: ", String.valueOf(url)),
                        Throwable::printStackTrace,
                        () -> {
                            Log.d("Загружены URL: ", "Все изображения были загружены");
                            Toast.makeText(getContext(), "Фото обновлено", Toast.LENGTH_SHORT).show();
                        }
                );
    }

    public Observable<File> compressImage(File file) {
        PublishProcessor<File> myDelayedObservable = PublishProcessor.create();
        CompressorManager.INSTANCE.compress(requireContext(), file, CompressorManager.INSTANCE.getContinuation((compressedFile, throwable) -> {
            myDelayedObservable.onNext(compressedFile);
            myDelayedObservable.onComplete();

        }));
        return Observable.fromPublisher(myDelayedObservable);
    }

    private void observe() {
        mViewModel.studentLD.observe(getViewLifecycleOwner(), this::drawData);
    }

    private void drawData(StudentAccount studentAccount) {
        if (studentAccount.getName() != null && !studentAccount.getName().equals("null")) {
            nameTV.setText(studentAccount.getName());
        }

        if (studentAccount.getPhotoUrl() != null && !studentAccount.getPhotoUrl().equals("null")) {
            Glide.with(requireContext()).load(studentAccount.getPhotoUrl()).into(userPhotoIV);
        }

        if (studentAccount.getPhotoUrl() != null && !studentAccount.getPhotoUrl().equals("null")) {
            Glide.with(requireContext())
                    .load(studentAccount.getPhotoUrl())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(userPhotoIV);
        }
    }
}