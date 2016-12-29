package com.example.sridh.inquisitenerds;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.sridh.inquisitenerds.database.NerdsDatabase;

public class MenuActivity extends AppCompatActivity {

    private NerdsDatabase dbhelper;
    private SQLiteDatabase dbs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        dbhelper = new NerdsDatabase(this);
        dbs = dbhelper.getReadableDatabase();
    }
}
