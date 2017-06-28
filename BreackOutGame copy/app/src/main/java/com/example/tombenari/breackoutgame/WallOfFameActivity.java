package com.example.tombenari.breackoutgame;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

public class WallOfFameActivity extends AppCompatActivity {

    private WallOfFameAdapter adapter;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wall_of_fame);

        ListView listView = (ListView) findViewById(R.id.list);
        DBOpenHelper helper = new DBOpenHelper(this);

        db = helper.getReadableDatabase();
        adapter = new WallOfFameAdapter(this, getData());

        listView.setAdapter(adapter);


    }

    private Cursor getData(){
        return db.query(DBOpenHelper.TABLE_WALL, null, null, null, null, null, DBOpenHelper.COLUMN_SCORE + " DESC");
    }
}
