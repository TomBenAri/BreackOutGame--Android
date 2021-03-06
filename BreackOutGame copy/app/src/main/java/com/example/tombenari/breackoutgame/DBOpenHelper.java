package com.example.tombenari.breackoutgame;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by tombenari on 11/10/2016.
 */

public class DBOpenHelper extends SQLiteOpenHelper {


    public static final String TABLE_WALL = "walloffame";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_SCORE = "score";
    public static final String COLUMN_TIME = "time";

    private static final String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_WALL + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COLUMN_NAME + " TEXT, " +
            COLUMN_SCORE + " TEXT, " +
            COLUMN_TIME + " TEXT" +
            ");";

    private static final String DB_NAME = "Database.db";
    private static final int DB_VERSION = 1;


    public DBOpenHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_CONTACTS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
