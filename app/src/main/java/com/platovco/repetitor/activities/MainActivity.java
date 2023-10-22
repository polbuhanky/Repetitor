package com.platovco.repetitor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.os.Bundle;
import android.widget.Button;

import com.platovco.repetitor.R;

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
        Navigation.findNavController(this, R.id.globalNavContainer).navigateUp();
    }
}