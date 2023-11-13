package com.example.lab2_android;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import org.intellij.lang.annotations.Language;

public class LoginUser extends Activity {

    public String myLogin = "nmart58";
    public String myPassword = "psu";
    public EditText login;
    public EditText password;
    public Button authorizeButton;
    public Intent intent;
    public TextView welcome;
    public static final String APP_PREFERENCES_NAME = "Language";
    public SharedPreferences langSettings;
    public SharedPreferences.Editor editor;
    public Spinner spinner;
    public int f = 0;
    public void setRussianLanguage () {
        welcome.setText(langSettings.getString("RUS_welcome", ""));
        login.setHint(langSettings.getString("RUS_username", ""));
        password.setHint(langSettings.getString("RUS_password", ""));
        authorizeButton.setText(langSettings.getString("RUS_signIn", ""));
    }
    public void setEnglishLanguage () {
        welcome.setText(langSettings.getString("ENG_welcome", ""));
        login.setHint(langSettings.getString("ENG_username", ""));
        password.setHint(langSettings.getString("ENG_password", ""));
        authorizeButton.setText(langSettings.getString("ENG_signIn", ""));
    }
    public void setLangSettings () {
        //editor = langSettings.edit();
        editor.putString("ENG_welcome", "Login");
        editor.putString("ENG_username", "Username");
        editor.putString("ENG_password", "Password");
        editor.putString("ENG_signIn", "Sign in");
        editor.putString("ENG_welcome_Main", "Welcome!");
        editor.putString("ENG_addButton", "Add");
        editor.putString("ENG_removeButton", "Remove");
        editor.putString("ENG_name", "Your name");
        editor.putString("ENG_exit", "Exit");

        editor.putString("RUS_welcome", "Авторизация");
        editor.putString("RUS_username", "Имя пользователя");
        editor.putString("RUS_password", "Пароль");
        editor.putString("RUS_signIn", "Войти");
        editor.putString("RUS_welcome_Main", "Добро пожаловать!");
        editor.putString("RUS_addButton", "Добавить");
        editor.putString("RUS_removeButton", "Удалить");
        editor.putString("RUS_name", "Ваше имя");
        editor.putString("RUS_exit", "Выйти");

        editor.apply();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);

        login = findViewById(R.id.editTextLogin);
        password = findViewById(R.id.editTextPassword);
        authorizeButton = findViewById(R.id.button);
        intent = new Intent(this, MainActivity.class);
        welcome = findViewById(R.id.welcomeLabel);
        spinner = findViewById(R.id.spinner);

        langSettings = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE);
        editor = langSettings.edit();
        //editor.clear();
        //editor.apply();

        String check = langSettings.getString("selectLang", "notInit");

        if (check.equals("notInit")) {
            setLangSettings();
        }

        if (langSettings.getString("selectLang", "Рус").equals("Рус")) {
            setRussianLanguage();
        }
        else {
            setEnglishLanguage();
        }

        //Toast.makeText(LoginUser.this, check, Toast.LENGTH_LONG).show();
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (f == 1) {
                    if (position == 0) {
                        editor.putString("selectLang", "Рус");
                        editor.apply();
                        setRussianLanguage();
                    }
                    else if (position == 1) {
                        editor.putString("selectLang", "Eng");
                        editor.apply();
                        setEnglishLanguage();
                    }
                }
                else {
                    f++;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

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
