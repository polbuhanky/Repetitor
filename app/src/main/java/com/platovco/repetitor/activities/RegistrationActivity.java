package com.platovco.repetitor.activities;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.platovco.repetitor.R;
import com.platovco.repetitor.managers.AppwriteClient;

import org.jetbrains.annotations.NotNull;
import java.util.Map;
import java.util.UUID;
import io.appwrite.Client;
import io.appwrite.exceptions.AppwriteException;
import io.appwrite.models.User;
import kotlin.Result;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;
import io.appwrite.services.Account;

public class RegistrationActivity extends AppCompatActivity {

    Button btnRegister;
    private EditText etEmail;
    private EditText etPassword;
    private EditText etPassword2;
    private TextView tvSignIn;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
    }

    private void init(){
        etEmail = findViewById(R.id.et_email);
        etPassword = findViewById(R.id.et_password);
        etPassword2 = findViewById(R.id.et_repeat_password);
        btnRegister = findViewById(R.id.btnReg);
        tvSignIn = findViewById(R.id.tv_sign_in);
        initListener();
    }

    private void initListener(){
        btnRegister.setOnClickListener(view -> {
            if (etEmail.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Введите почту", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!isEmailValid(etEmail.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Введите корректную почту", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etPassword.getText().toString().length() < 8) {
                Toast.makeText(getApplicationContext(), "В пароле должно быть 8 и более символов", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etPassword.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Введите пароль", Toast.LENGTH_SHORT).show();
                return;
            }
            if (etPassword2.getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Подтвердите пароль", Toast.LENGTH_SHORT).show();
                return;
            }
            if (!etPassword.getText().toString().equals(etPassword2.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Подтвердите пароль", Toast.LENGTH_SHORT).show();
                return;
            }

            String id = UUID.randomUUID().toString();

            Client client = AppwriteClient.getClient();
            Account account = new Account(client);

            try {
                account.create(
                        id,
                        etEmail.getText().toString(),
                        etPassword.getText().toString(),
                        new Continuation<User<Map<String, Object>>>() {
                            @NotNull
                            @Override
                            public CoroutineContext getContext() {
                                return EmptyCoroutineContext.INSTANCE;
                            }

                            @Override
                            public void resumeWith(@NotNull Object o) {
                                try {
                                    if (o instanceof Result.Failure) {
                                        Result.Failure failure = (Result.Failure) o;
                                        throw failure.exception;
                                    } else {
                                        Intent intent = new Intent(RegistrationActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        Log.i("AAA", "прошло");
                                    }
                                } catch (Throwable th) {
                                    Log.e("ERROR", th.toString());
                                }
                            }
                        }
                );
            } catch (AppwriteException e) {
                throw new RuntimeException(e);
            }
        });

        tvSignIn.setOnClickListener(view -> {
            Intent intent = new Intent(RegistrationActivity.this, AuthActivity.class);
            startActivity(intent);
        });
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}