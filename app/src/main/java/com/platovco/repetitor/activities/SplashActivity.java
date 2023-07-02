package com.platovco.repetitor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.platovco.repetitor.managers.AppwriteClient;

import org.jetbrains.annotations.NotNull;

import io.appwrite.Client;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.services.Account;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Client client = AppwriteClient.getClient();
        Account account = new Account(client);

        try {
            account.get(new Continuation<Object>() {
                @NotNull
                @Override
                public CoroutineContext getContext() {
                    return EmptyCoroutineContext.INSTANCE;
                }

                @Override
                public void resumeWith(@NotNull Object o) {
                    try {
                        if (o instanceof Result.Failure) {
                            Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
                            startActivity(intent);
                        } else {
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }
                    } catch (Throwable th) {
                        startActivity(new Intent(SplashActivity.this, AuthActivity.class));
                        Log.e("ERROR", th.toString());
                    }
                }
            });
        } catch (AppwriteException e) {
            throw new RuntimeException(e);
        }
    }
}