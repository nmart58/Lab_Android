package com.example.lab2_android;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DataHandler extends SQLiteOpenHelper {
    private final static int DATABASE_VERSION = 1;
    private final static String DATABASE_NAME = "Users";
    private final static String TABLE_USERS = "users";
    private final static String KEY_ID = "id";
    private final static String KEY_LOGIN = "login";
    private final static String KEY_PASS = "pass";

    public DataHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_USERS + "("
                + KEY_LOGIN + " TEXT PRIMARY KEY, " + KEY_PASS + " TEXT)";
        db.execSQL(CREATE_USERS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
    }

    public ArrayList<Users> getAllUsers() {
        ArrayList<Users> listUsers = new ArrayList<Users>();
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()) {
            do {
                Users user = new Users();
                user.setUsername(cursor.getString(0));
                user.setPassword(cursor.getString(1));
                listUsers.add(user);
            } while (cursor.moveToNext());
        }
        return listUsers;
    }

    public int addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        ArrayList<Users> listUsers = new ArrayList<Users>();
        listUsers = getAllUsers();
        for(Users user: listUsers) {
            if(user.getUsername().equals(username)) {
                return -1;
            }
        }
        values.put(KEY_LOGIN, username);
        values.put(KEY_PASS, password);

        db.insert(TABLE_USERS, null, values);
        db.close();

        return 0;
    }
    public void deleteUser(String username) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_USERS, KEY_LOGIN + " = ?", new String[]{username});
        db.close();
    }

    public void updatePassword(String username, String newPassword) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_LOGIN, username);
        values.put(KEY_PASS, newPassword);
        db.update(TABLE_USERS, values, KEY_LOGIN + " = ?", new String[] {username});
        db.close();
    }
    public int authUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ArrayList<Users> listUsers = new ArrayList<Users>();
        listUsers = getAllUsers();
        for(Users user: listUsers) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)) {
                return 0; //Successfull auth
            }
        }
        return -1;
    }

}
