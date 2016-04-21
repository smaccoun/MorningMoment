package com.example.stevenmaccoun.morningmoment;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.stevenmaccoun.morningmoment.db.MorningRoutineDbHelper;

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

        Cursor routineLVCursor =
                db.rawQuery("SELECT _id, routine_nm, routine_desc" +
                            " FROM Routine", null);
        RoutineAdapter routineAdapter = new RoutineAdapter(this, routineLVCursor, 0);

        popularLV.setAdapter(routineAdapter);

        popularLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Cursor c = (Cursor) popularLV.getItemAtPosition(position);
                String routineNm = c.getString(c.getColumnIndexOrThrow("routine_nm"));
                RoutineTaskManager.getInstance()
                        .initializeRoutine(getApplicationContext(), routineNm);

                Intent i = new Intent(MainActivity.this, RoutineActivity.class);
                startActivity(i);
            }
        });
    }

}
