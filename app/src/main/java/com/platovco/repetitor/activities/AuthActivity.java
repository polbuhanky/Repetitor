package com.platovco.repetitor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.platovco.repetitor.R;
import com.platovco.repetitor.managers.AppwriteClient;

import org.jetbrains.annotations.NotNull;

import io.appwrite.Client;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.services.Account;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public class AuthActivity extends AppCompatActivity {

    Button btnSignIn;
    LinearLayout llRegistration;
    private EditText etMail;
    private EditText etPassword;
    private TextView tvForgetPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
        init();
    }

    private void init() {
        btnSignIn = findViewById(R.id.singInBTN);
        etMail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        llRegistration = findViewById(R.id.ll_reg);
        initListeners();
    }

    private void initListeners() {
        llRegistration.setOnClickListener(view ->
        {
            Intent intent = new Intent(AuthActivity.this, RegistrationActivity.class);
            startActivity(intent);
        });
        btnSignIn.setOnClickListener(view -> {
            if (etMail.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Введите почту", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etPassword.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Введите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            Client client = AppwriteClient.getClient();
            Account account = new Account(client);

            account.createEmailSession(
                    etMail.getText().toString(),
                    etPassword.getText().toString(),
                    new Continuation<Object>() {
                        @NotNull
                        @Override
                        public CoroutineContext getContext() {
                            return EmptyCoroutineContext.INSTANCE;
                        }

                        @Override
                        public void resumeWith(@NotNull Object o) {
                            if (o instanceof Result.Failure) {
                                Log.e("AppwriteError", ((Result.Failure) o).exception.toString());
                            } else {
                                Intent intent = new Intent(AuthActivity.this, MainActivity.class);
                                startActivity(intent);
                                Log.i("AAA", "прошло");
                            }
                        }
                    });
        });


    }

    @Override
    public void onBackPressed() {
    }
}