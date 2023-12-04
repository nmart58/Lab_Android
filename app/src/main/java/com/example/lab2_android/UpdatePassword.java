package com.example.lab2_android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class UpdatePassword extends Activity {

    //Bundle arguments = getIntent().getExtras();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.update_password);

        Button buttonUpdatePwd = findViewById(R.id.buttonUpd);
        Button cancel = findViewById(R.id.button);
        TextView username = findViewById(R.id.welcomeLabel3);
        EditText newPassword = findViewById(R.id.editTextLogin);
        Intent intent;
        DataHandler db = new DataHandler(this);

        //username.setText(arguments.getString("username", "undefined"));
        intent = new Intent(this, MainActivity.class);
        Bundle arguments = getIntent().getExtras();
        username.setText(arguments.getString("userUpdPwd", "user"));
        String oldUsername = arguments.getString("oldUsername", "user");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent.putExtra("username", oldUsername);
                startActivity(intent);
            }
        });

        buttonUpdatePwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(arguments.getString("userUpdPwd", "user"));
                System.out.println(newPassword.getText().toString());
                db.updatePassword((arguments.getString("userUpdPwd", "user")), newPassword.getText().toString());
                Toast.makeText(UpdatePassword.this, "Пароль успешно изменен", Toast.LENGTH_LONG).show();
                intent.putExtra("username", oldUsername);
                startActivity(intent);
            }
        });
    }

}
