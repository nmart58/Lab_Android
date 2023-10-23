package com.example.lab2_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class LoginUser extends Activity {

    public String myLogin = "nmart58";
    public String myPassword = "psu";
    public EditText login;
    public EditText password;
    public Button authorizeButton;
    public Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        login = findViewById(R.id.editTextLogin);
        password = findViewById(R.id.editTextPassword);
        authorizeButton = findViewById(R.id.button);
        intent = new Intent(this, MainActivity.class);

        authorizeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(login.getText().toString().equals(myLogin) && password.getText().toString().equals(myPassword)) {
                    intent.putExtra("username", myLogin);
                    startActivity(intent);
                    Toast.makeText(LoginUser.this, "Вход выполнен", Toast.LENGTH_LONG).show();
                }
                else {
                    Toast.makeText(LoginUser.this, "Неверный логин или пароль", Toast.LENGTH_LONG).show();
                }
            }
        });
    }




}
