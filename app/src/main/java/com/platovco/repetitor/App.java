package com.platovco.repetitor;

import android.app.Application;

import com.platovco.repetitor.managers.AppwriteClient;

public class App extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        AppwriteClient.setClient(getApplicationContext());
    }
}
