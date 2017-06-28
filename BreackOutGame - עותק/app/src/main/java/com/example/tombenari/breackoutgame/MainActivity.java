package com.example.tombenari.breackoutgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    Button letsPlayBtn;
    Button wallOfFameBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        letsPlayBtn = (Button)findViewById(R.id.buttonLetsPlay);
        wallOfFameBtn = (Button)findViewById(R.id.wallOfFameBtn);

        letsPlayBtn.setOnClickListener(this);
        wallOfFameBtn.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v==letsPlayBtn){
            Intent intent = new Intent(this, GameActivity.class);
            startActivity(intent);
        }
        if (v==wallOfFameBtn){
            //TODO bild wall of fame activity
        }
    }
}
