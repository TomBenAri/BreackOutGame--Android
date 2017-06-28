package com.example.tombenari.breackoutgame;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class NameActivity extends AppCompatActivity implements View.OnClickListener {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name);



        Button save = (Button)findViewById(R.id.name_btn);



        save.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        DBOpenHelper helper = new DBOpenHelper(this);
        SQLiteDatabase db = helper.getWritableDatabase();
        ContentValues values = new ContentValues();
        String name = ((EditText) findViewById(R.id.edit_text_name)).getText().toString().trim();
        int scoreInt = getIntent().getIntExtra(GameActivity.SCORE, 0);
        String score = scoreInt+"";
        values.put(DBOpenHelper.COLUMN_NAME, name);
        values.put(DBOpenHelper.COLUMN_SCORE, score);
        db.insert(DBOpenHelper.TABLE_WALL, null, values);
        db.close();
//        Intent intent = new Intent(this, MainActivity.class);
//        startActivity(intent);
        finish();
    }
}
