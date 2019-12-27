package com.example.movie.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.movie.API.Result;
import com.example.movie.Classes.User;

import java.util.ArrayList;
import java.util.List;

public class FavoritesDatabase extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="favorites.db";
    public static final String FAVORITES_TABLE ="favorites_table";
    private static final String ID = "ID";
    private static final String TITLE = "TITLE";
    private static final String DESCRIPTION = "DESCRIPTION";
    private static final String IMAGE = "IMAGE";

    public FavoritesDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_FAVORITE_TABLE = "CREATE TABLE " + FAVORITES_TABLE + " (" +
                ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                TITLE + " TEXT NOT NULL, " +
                DESCRIPTION+ " TEXT NOT NULL, " +
                IMAGE + " TEXT NOT NULL, " + " ); ";

        db.execSQL(SQL_CREATE_FAVORITE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ FAVORITES_TABLE);
        onCreate(db);
    }

    public void addFavorite(Result movie){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        User user=new User();
        values.put(ID, user.getId());
        values.put(TITLE, movie.getOriginalTitle());
        values.put(DESCRIPTION, movie.getOverview());
        values.put(IMAGE, movie.getPosterPath());
        db.insert(FAVORITES_TABLE, null, values);
        db.close();
    }

    public List<Result> getAllFavorite(){
        String[] columns = {TITLE, DESCRIPTION, IMAGE};
        List<Result> favoriteList = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(FAVORITES_TABLE, columns, null, null, null, null, null);

        if (cursor.moveToFirst()){
            do {
                Result movie = new Result();
                movie.setOriginalTitle(cursor.getString(cursor.getColumnIndex(TITLE)));
                movie.setVoteAverage(Double.parseDouble(cursor.getString(cursor.getColumnIndex(DESCRIPTION))));
                movie.setPosterPath(cursor.getString(cursor.getColumnIndex(IMAGE)));

                favoriteList.add(movie);

            }while(cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return favoriteList;
    }


}
