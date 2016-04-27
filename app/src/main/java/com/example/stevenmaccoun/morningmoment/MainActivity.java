package com.example.stevenmaccoun.morningmoment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.stevenmaccoun.morningmoment.db.MorningRoutineDbHelper;
import com.example.stevenmaccoun.morningmoment.db.RoutineRepository;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView popularLV;
    private Button createRoutineB;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.deleteDatabase("MorningRoutine.db");

        initializePopularLV();
        initializeCreateRoutineB();

    }



    private void initializeCreateRoutineB(){
        createRoutineB = (Button) findViewById(R.id.create_routine_b);

        createRoutineB.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, CreateRoutineActivity.class);
                RoutineController.getInstance().refresh();
                startActivity(i);
            }
        });
    }


    private void initializePopularLV(){

        MorningRoutineDbHelper mDbHelper = MorningRoutineDbHelper.getHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        popularLV = (ListView) findViewById(R.id.popular);

        /*
        TODO: Change to using RoutineRepository.getAll and fill an array adapter
        */
        ArrayList<Routine> routines = new RoutineRepository(this).GetAll();
        RoutineAdapter routineAdapter = new RoutineAdapter(this, routines);

        popularLV.setAdapter(routineAdapter);

        popularLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Routine routine = (Routine) parent.getItemAtPosition(position);
                String routineNm = routine.getName();
                RoutineTaskManager.getInstance()
                        .initializeRoutine(getApplicationContext(), routineNm);

                Intent i = new Intent(MainActivity.this, RoutineActivity.class);
                startActivity(i);
            }
        });

        db.close();
    }

    @Override
    public void onResume()
    {
        super.onResume();
        initializePopularLV();
    }

    private class RoutineAdapter extends ArrayAdapter<Routine> {


        public RoutineAdapter(Context context, ArrayList<Routine> routines) {
            super(context, 0, routines);
        }



        @Override
        public View getView(int position, View view, ViewGroup parent) {

            Routine routine = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            if (view == null) {
                view = LayoutInflater.from(getContext()).inflate(R.layout.routine_list, parent, false);
            }

            TextView tvTitle = (TextView) view.findViewById(R.id.title);
            TextView tvDescription = (TextView) view.findViewById(R.id.description);
            TextView tvDuration = (TextView) view.findViewById(R.id.duration);


            tvTitle.setText(routine.getName());
            tvDescription.setText(routine.getDescription());
            tvDuration.setText(routine.getDurationText());

            return view;
        }
    }

}
