package com.example.stevenmaccoun.morningmoment;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stevenmaccoun.morningmoment.db.MorningRoutineDbHelper;
import com.example.stevenmaccoun.morningmoment.db.RoutineRepository;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView popularLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializePopularLV();

    }


    private void initializePopularLV(){

        MorningRoutineDbHelper mDbHelper = MorningRoutineDbHelper.getHelper(getApplicationContext());
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        popularLV = (ListView) findViewById(R.id.popular);

        ArrayList<Routine> routines = new RoutineRepository(this).GetAll();
        RoutineAdapter routineAdapter = new RoutineAdapter(this, routines);

        popularLV.setAdapter(routineAdapter);

        popularLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Routine routine = (Routine) parent.getItemAtPosition(position);
                RoutineTaskManager.getInstance()
                        .initializeRoutine(getApplicationContext(), routine);

                Intent i = new Intent(MainActivity.this, RoutineActivity.class);
                startActivity(i);
            }
        });

        db.close();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.action_settings:
                Intent i = new Intent(MainActivity.this, CreateRoutineActivity.class);
                RoutineController.getInstance().refresh();
                startActivity(i);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

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
