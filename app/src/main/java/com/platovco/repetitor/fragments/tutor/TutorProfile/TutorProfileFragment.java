package com.platovco.repetitor.fragments.tutor.TutorProfile;

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
import com.platovco.repetitor.databinding.FragmentTutorProfileBinding;
import com.platovco.repetitor.managers.AppwriteManager;
import com.platovco.repetitor.managers.CompressorManager;
import com.platovco.repetitor.models.TutorAccount;
import com.platovco.repetitor.utils.PhotoUtil;

import java.io.File;
import java.util.Objects;
import java.util.concurrent.Callable;

import gun0912.tedimagepicker.builder.TedImagePicker;
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.processors.PublishProcessor;

public class TutorProfileFragment extends Fragment {

    private TutorProfileViewModel mViewModel;
    private FragmentTutorProfileBinding binding;

    TextView nameTV;
    ImageView userPhotoIV;
    TextView educationTV;
    TextView directionTV;
    TextView experienceTV;
    //ImageView editBTN;

    public static TutorProfileFragment newInstance() {
        return new TutorProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentTutorProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mViewModel = new ViewModelProvider(this).get(TutorProfileViewModel.class);
        AppwriteManager.INSTANCE.getTutor(mViewModel.tutorLD, AppwriteManager.INSTANCE.getContinuation((result, throwable) -> {
            Log.d("AppW Result: ", String.valueOf(result));
            Log.d("AppW Exception: ", String.valueOf(throwable));
        }));
        init();
        observe();
    }
    private void init(){
        nameTV = binding.tvName;
        userPhotoIV = binding.ivAvatar;

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
            AppwriteManager.INSTANCE.createFileTutorStorage(uuid, file, AppwriteManager.INSTANCE.getContinuation((result, throwable) -> {
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
                .flatMap(compressedImage -> Objects.requireNonNull(uploadImage(compressedImage, String.valueOf(Objects.requireNonNull(mViewModel.tutorLD.getValue()).getUuid())))
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
        mViewModel.tutorLD.observe(getViewLifecycleOwner(), this::drawData);
    }

    private void drawData(TutorAccount tutorAccount) {
        if (tutorAccount.getName() != null && !tutorAccount.getName().equals("null")) {
            nameTV.setText(tutorAccount.getName());
        }
        if (tutorAccount.getEducation() != null && !tutorAccount.getEducation().equals("null")) {
            educationTV.setText(tutorAccount.getEducation());
        }
        if (tutorAccount.getDirection() != null && !tutorAccount.getDirection().equals("null")) {
            directionTV.setText(tutorAccount.getDirection());
        }
        if (tutorAccount.getPhotoUrl() != null && !tutorAccount.getPhotoUrl().equals("null")) {
            Glide.with(requireContext()).load(tutorAccount.getPhotoUrl()).into(userPhotoIV);
        }
        if (tutorAccount.getExperience() != null && !tutorAccount.getExperience().equals("null")) {
            experienceTV.setText(tutorAccount.getExperience());
        }
        if (tutorAccount.getPhotoUrl() != null && !tutorAccount.getPhotoUrl().equals("null")) {
            Glide.with(requireContext())
                    .load(tutorAccount.getPhotoUrl())
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .skipMemoryCache(true)
                    .centerCrop()
                    .into(userPhotoIV);
        }
    }

}