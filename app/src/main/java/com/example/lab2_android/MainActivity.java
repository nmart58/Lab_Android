package com.example.lab2_android;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    ArrayList<String> users = new ArrayList<String>();
    ArrayList<Users> usersLogins = new ArrayList<Users>();
    ArrayList<Integer> selectedUser = new ArrayList<Integer>();
    ArrayAdapter<String> adapter;
    ListView listView;
    EditText editText;
    EditText editTextPass;
    Button addUserButton;
    Button removeUserButton;
    Button logoutButton;
    TextView username;
    Intent intent;
    Intent intentUpdatePwd;
    TextView welcome;
    SharedPreferences langSettings;
    SharedPreferences settingsUsers;
    SharedPreferences.Editor editor;
    static final String APP_PREFERENCES_NAME = "Language";
    Set<String> userList = new HashSet<String>();
    public int selectedItemId, oldPosition = 0, flag, index;
    DataHandler db;
    Button updatePwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d("Вызыван метод: ", "onCreate()");

        //Загрузка списка пользователей из БД
        db = new DataHandler(this);
        usersLogins = db.getAllUsers();
        for(Users user: usersLogins) {
            users.add(user.getUsername());
        }

        addUserButton = findViewById(R.id.button1);
        removeUserButton = findViewById(R.id.button2);
        editText = findViewById(R.id.name);
        editTextPass = findViewById(R.id.name2);

        listView = findViewById(R.id.listUsers);
        username = findViewById(R.id.textView);
        logoutButton = findViewById(R.id.buttonExit);
        welcome = findViewById(R.id.textView2);
        updatePwd = findViewById(R.id.buttonUpdate);

        intentUpdatePwd = new Intent(this, UpdatePassword.class);

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
        String userToIntent = arguments.getString("username", "user");

        //username.setText(arguments.getString("username", "user") + ",");
        username.setText(userToIntent + ", ");

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent);
            }
        });
        addUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Handler handler = new Handler();

                if(!(editText.getText().toString().equals("") || editTextPass.getText().toString().equals(""))) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    int res = db.addUser(editText.getText().toString(), editTextPass.getText().toString());
                                    if (res == 0) {
                                        users.add(editText.getText().toString());
                                        adapter.notifyDataSetChanged();
                                        editText.setText("");
                                        editTextPass.setText("");
                                        Toast.makeText(MainActivity.this, "Пользователь успешно зарегистрирован!", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Этот пользователь уже зарегистрирован", Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                        }
                    }).start();
                }
            }
        });

        removeUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Collections.sort(selectedUser);
                int checkBeforeDelete = users.size() - selectedUser.size();
                final Handler handler = new Handler();

                if (checkBeforeDelete != 0) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i = selectedUser.size() - 1; i >= 0; i--) {
                                        int sUser = selectedUser.get(i);
                                        db.deleteUser(users.get(sUser));
                                        Toast.makeText(MainActivity.this, "Пользователь " + users.get(sUser) + " удален", Toast.LENGTH_LONG).show();
                                        users.remove(sUser);
                                        listView.getChildAt(selectedUser.get(i)).setBackgroundColor(getResources().getColor(R.color.white));
                                    }
                                    adapter.notifyDataSetChanged();
                                    removeUserButton.setEnabled(false);
                                    selectedUser.clear();
                                }
                            });

                        }
                    }).start();
                }
                else {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            handler.post(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "Необходимо оставить хотя бы одного пользователя!", Toast.LENGTH_LONG).show();
                                }
                            });
                        }
                    }).start();
                }
            }
        });

        updatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedUser.size() == 1) {
                    String username = users.get(selectedUser.get(0));
                    intentUpdatePwd.putExtra("userUpdPwd", username);
                    intentUpdatePwd.putExtra("oldUsername", userToIntent);
                    selectedUser.clear();
                    startActivity(intentUpdatePwd);
                }
                else {
                    Toast.makeText(MainActivity.this, "Выберите только одного пользователя", Toast.LENGTH_LONG).show();
                }
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
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