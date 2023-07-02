package com.platovco.repetitor.managers;

import android.content.Context;

import io.appwrite.Client;

public class AppwriteClient {

    public static Client getClient() {
        return client;
    }

    public static void setClient(Context context) {
        AppwriteClient.client = new Client(context)
                .setEndpoint("http://89.253.219.76/v1") // Your API Endpoint
                .setProject("649d4dbdcf623484dd45") // Your project ID
                .setSelfSigned(true);
    }

    static Client client;

    private AppwriteClient() {}
}
