package com.example.sridh.inquisitenerds;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.sridh.inquisitenerds.database.NerdsContract;
import com.example.sridh.inquisitenerds.database.NerdsDatabase;

import java.util.ArrayList;

public class MenuActivity extends AppCompatActivity {

    private NerdsDatabase dbhelper;
    private SQLiteDatabase dbs;
    private Cursor cursor;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> list;
    private ListView listView;
    private int j=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
       listView = (ListView) findViewById(R.id.list_view);

        dbhelper = new NerdsDatabase(this);
        dbs = dbhelper.getReadableDatabase();

        cursor = dbs.rawQuery("SELECT * FROM " + NerdsContract.NerdsEntry.TABLE_NAME, null);
       // cursor.moveToNext();
        if(!cursor.moveToNext())
            return;


        list = new ArrayList<String>();
        list.add(cursor.getString(cursor.getColumnIndex(NerdsContract.NerdsEntry.COLUMN_BEACON)));
        adapter = new ArrayAdapter<String>(MenuActivity.this,android.R.layout.simple_list_item_1,list);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

    }
}
