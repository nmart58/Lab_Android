package com.example.lab2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> users = new ArrayList<String>();
    ArrayList<Integer> selectedUser = new ArrayList<Integer>();

    int sUser;
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;
    Button addUserButton;
    Button removeUserButton;
    Button logoutButton;
    TextView username;
    Intent intent;
    TextView welcome;
    SharedPreferences langSettings;
    SharedPreferences settingsUsers;
    SharedPreferences.Editor editor;
    static final String APP_PREFERENCES_NAME = "Language";
    Set<String> userList = new HashSet<String>();
    public int selectedItemId, oldPosition = 0, flag, index;

    public void loadUsers () {
        settingsUsers = getPreferences(MODE_PRIVATE);
        userList = settingsUsers.getStringSet("users", new HashSet<String>());
        users.addAll(userList);
    }

    public void saveUsers() {
        userList.clear();
        userList.addAll(users);
        settingsUsers = getPreferences(MODE_PRIVATE);
        editor = settingsUsers.edit();
        editor.clear();
        editor.putStringSet("users", userList);
        editor.apply();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Вызыван метод: ", "onCreate()");

        loadUsers(); // Загрузка списка пользователей

        addUserButton = findViewById(R.id.button1);
        removeUserButton = findViewById(R.id.button2);
        editText = findViewById(R.id.name);
        listView = findViewById(R.id.listUsers);
        username = findViewById(R.id.textView);
        logoutButton = findViewById(R.id.buttonExit);
        welcome = findViewById(R.id.textView2);

        langSettings = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE); //Читаем настройки
        if (langSettings.getString("selectLang", "Рус").equals("Рус")) {
            welcome.setText(langSettings.getString("RUS_welcome_Main", ""));
            addUserButton.setText(langSettings.getString("RUS_addButton", ""));
            removeUserButton.setText(langSettings.getString("RUS_removeButton", ""));
            editText.setHint(langSettings.getString("RUS_name", ""));
            logoutButton.setText(langSettings.getString("RUS_exit", ""));
        }
        else {
            welcome.setText(langSettings.getString("ENG_welcome_Main", ""));
            addUserButton.setText(langSettings.getString("ENG_addButton", ""));
            removeUserButton.setText(langSettings.getString("ENG_removeButton", ""));
            editText.setHint(langSettings.getString("ENG_name", ""));
            logoutButton.setText(langSettings.getString("ENG_exit", ""));
        }

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, users);
        listView.setAdapter(adapter);

        removeUserButton.setEnabled(false);

        intent = new Intent(this, LoginUser.class);

        Bundle arguments = getIntent().getExtras();
        username.setText(arguments.getString("username", "user") + ",");


        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().equals("")) {
                    users.add(editText.getText().toString());
                    editText.setText("");
                    adapter.notifyDataSetChanged();

                }
            }
        });

        removeUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //users.remove(selectedItemId);
                //adapter.notifyDataSetChanged();
                //removeUserButton.setEnabled(false);
                //listView.getChildAt(selectedItemId).setBackgroundColor(getResources().getColor(R.color.white));

                Collections.sort(selectedUser);

                for (int i=selectedUser.size()-1; i>=0; i--) {
                    sUser = selectedUser.get(i);
                    users.remove(sUser);
                    listView.getChildAt(selectedUser.get(i)).setBackgroundColor(getResources().getColor(R.color.white));
                }
                adapter.notifyDataSetChanged();
                removeUserButton.setEnabled(false);
                selectedUser.clear();
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //listView.getChildAt(oldPosition).setBackgroundColor(getResources().getColor(R.color.white));
                //selectedItemId = position;
                //listView.getChildAt(selectedItemId).setBackgroundColor(getResources().getColor(R.color.purple));
                //oldPosition = position;

                flag = 0;

                if(selectedUser.isEmpty()) {
                    listView.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.purple));
                    selectedUser.add(position);
                    removeUserButton.setEnabled(true);
                }
                else {
                    for (int j=0; j < selectedUser.size(); j++) {
                        if(selectedUser.get(j) == position) {
                            flag = 1;
                            index = j;
                            break;
                        }
                    }

                    if(flag == 1) {
                        selectedUser.remove(index);
                        listView.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.white));
                        if(selectedUser.size() == 0)
                            removeUserButton.setEnabled(false);
                    }
                    else {
                        selectedUser.add(position);
                        listView.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.purple));
                        removeUserButton.setEnabled(true);
                    }
                }
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("Вызван метод: ", "onStart()");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("Вызван метод: ", "onResume()");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("Вызван метод: ", "onPause()");
        saveUsers(); // Сохранение списка пользователей
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("Вызван метод: ", "onStop()");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("Вызван метод: ", "onDestroy()");
    }
}