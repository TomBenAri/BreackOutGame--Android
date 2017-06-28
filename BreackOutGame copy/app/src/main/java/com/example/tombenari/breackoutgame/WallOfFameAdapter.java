package com.example.tombenari.breackoutgame;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

/**
 * Created by tombenari on 11/10/2016.
 */

public class WallOfFameAdapter extends CursorAdapter {
    public WallOfFameAdapter(Context context, Cursor cursor) {
        super(context, cursor, 0);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.listi_item, parent, false);
        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.list_item_name_text_view);
        TextView scoreTextView = (TextView) view.findViewById(R.id.list_item_points_text_view);
      //  TextView timeTextView = (TextView) view.findViewById(R.id.list_item_time_text_view);
        String name = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_NAME));
        String score = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_SCORE));
        //String time = cursor.getString(cursor.getColumnIndex(DBOpenHelper.COLUMN_TIME));
        nameTextView.setText(name);
        scoreTextView.setText(score);
     //   timeTextView.setText(time);
    }
}
