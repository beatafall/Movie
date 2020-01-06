package com.example.movie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;
import com.example.movie.Classes.User;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME="user.db";
    public static final String USER_TABLE ="user_table";
    public static final String USER_ID ="ID";
    public static final String NAME="NAME";
    public static final String PASSWORD="PASSWORD";
    public static final String IMAGE="IMAGE";
    public static final String FAVORITES="FAVORITES";

    public DatabaseHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + USER_TABLE + " (USER_ID  INTEGER PRIMARY KEY AUTOINCREMENT, NAME TEXT,PASSWORD TEXT, IMAGE TEXT, FAVORITES TEXT ) " );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ USER_TABLE);
        onCreate(db);
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, user.getName());
        values.put(PASSWORD, user.getPassword());
        long result=db.insert(USER_TABLE, null, values);
        return result;
    }


    public User getUser(long ID) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selectQuery = "SELECT  * FROM " + USER_TABLE + " WHERE " + USER_ID + " = " + ID;

        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null)
            c.moveToFirst();

        User u = new User();
        u.setId(c.getInt(c.getColumnIndex(USER_ID)));
        u.setName((c.getString(c.getColumnIndex(NAME))));
        u.setPassword(c.getString(c.getColumnIndex(PASSWORD)));

        return u;
    }

    public long insertImage(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(IMAGE,user.getImage());
        return db.insert(USER_TABLE, null,values);
    }

    public boolean checkUser(String name,String pass) {
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = NAME + " = ? " +  " AND " + PASSWORD + " = ? " ;
        String[] selectionArgs = {name,pass};
        String[] columns = {NAME,PASSWORD};
        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs, null, null, null);
        int cursorCount = cursor.getCount();
        cursor.close();
        db.close();

        if (cursorCount > 0) {
            return true;
        }
        return false;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + USER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                User u = new User();
                u.setId(c.getInt(c.getColumnIndex(USER_ID)));
                u.setName((c.getString(c.getColumnIndex(NAME))));
                u.setPassword(c.getString(c.getColumnIndex(PASSWORD)));

                users.add(u);
            } while (c.moveToNext());
        }
        return users;
    }

    public List<User> getUserImg() {
        List<User> users = new ArrayList<>();
        String selectQuery = "SELECT IMAGE FROM " + USER_TABLE;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {
            do {
                User u = new User();
                u.setImage(c.getString(c.getColumnIndex(IMAGE)));

                users.add(u);
            } while (c.moveToNext());
        }
        return users;
    }


    public int updatePassword(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(PASSWORD, user.getPassword());
        return db.update(USER_TABLE, values, USER_ID + " = ?",
                new String[] { String.valueOf(user.getId())});
    }

    public void addFavorite(User user){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FAVORITES, user.getFavorite());
        db.insert(USER_TABLE, null, values);
        db.close();
    }

    public long addUserWithImage(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(NAME, user.getName());
        values.put(PASSWORD, user.getPassword());
        values.put(IMAGE, user.getImage());
        long result=db.insert(USER_TABLE, null, values);
        return result;
    }

}
