package com.platovco.repetitor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import com.platovco.repetitor.R;
import com.platovco.repetitor.managers.AppwriteClient;

import org.jetbrains.annotations.NotNull;

import io.appwrite.Client;
import io.appwrite.services.Account;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class MainActivity extends AppCompatActivity {


    Button btnBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //btnBack = findViewById(R.id.btn_back);
//
        //btnBack.setOnClickListener(view -> {
//
//
//
        //    Client client = AppwriteClient.getClient();
        //    Account account = new Account(client);
//
        //    account.deleteSessions(new Continuation<Object>() {
        //        @NotNull
        //        @Override
        //        public CoroutineContext getContext() {
        //            return EmptyCoroutineContext.INSTANCE;
        //        }
//
        //        @Override
        //        public void resumeWith(@NotNull Object o) {
        //            try {
        //                if (o instanceof Result.Failure) {
        //                } else {
        //                    Intent intent = new Intent(MainActivity.this, AuthActivity.class);
        //                    startActivity(intent);
        //                }
        //            } catch (Throwable th) {
        //                Log.e("ERROR", th.toString());
        //            }
        //        }});
        //    finish();
        //});
    }

    @Override
    public void onBackPressed() {
        Navigation.findNavController(this, R.id.fragmentContainerView).navigateUp();
    }
}