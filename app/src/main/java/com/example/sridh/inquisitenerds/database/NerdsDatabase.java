package com.example.sridh.inquisitenerds.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.version;

/**
 * Created by sridh on 29-12-2016.
 */

public class NerdsDatabase extends SQLiteOpenHelper {

    private static final String DATABASE_NAME="beacons.db";
    private static final int DATABSE_VERSION =1;

    public NerdsDatabase(Context context) {
        super(context,DATABASE_NAME,null,DATABSE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        final String SQL_CREATE_STRING="CREATE TABLE " + NerdsContract.NerdsEntry.TABLE_NAME + " ("
                + NerdsContract.NerdsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + NerdsContract.NerdsEntry.COLUMN_BEACON + " TEXT " + ");";
sqLiteDatabase.execSQL(DATABASE_NAME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
