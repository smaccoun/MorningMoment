package com.example.stevenmaccoun.morningmoment;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.stevenmaccoun.morningmoment.db.MorningRoutineDbHelper;
import com.example.stevenmaccoun.morningmoment.db.RoutineContract;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private ListView popularLV;
    private MorningRoutineDbHelper mDbHelper;
    private SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.deleteDatabase("MorningRoutine.db");
        mDbHelper = MorningRoutineDbHelper.getHelper(getApplicationContext());
        db = mDbHelper.getWritableDatabase();

        popularLV = (ListView) findViewById(R.id.popular);

        initializePopularLV();

    }


    private void initializePopularLV(){

        Cursor routineLVCursor = db.rawQuery("SELECT * FROM Routine", null); //TODO: Change * to column names
        RoutineAdapter routineAdapter = new RoutineAdapter(this, routineLVCursor, 0);

        popularLV.setAdapter(routineAdapter);

        popularLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor c = (Cursor) popularLV.getItemAtPosition(position);
                String routineNm = c.getString(1); //TODO: Use getcolumnindex
                RoutineTaskManager.getInstance()
                        .initializeRoutine(getApplicationContext(), routineNm);

                Intent i = new Intent(MainActivity.this, RoutineActivity.class);
                startActivity(i);
            }
        });
    }

}
