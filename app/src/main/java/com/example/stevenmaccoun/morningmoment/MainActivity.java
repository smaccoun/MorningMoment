package com.example.stevenmaccoun.morningmoment;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private ListView popularLV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        popularLV = (ListView) findViewById(R.id.popular);

        initializePopularLV();

    }


    private void initializePopularLV(){

        String[] popularTimes = new String[] {
                "My favorite routine",
                "10 Minutes",
                "30 Minutes",
                "1 Hour"
        };

        ArrayAdapter<String> popularLVAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, popularTimes);

        popularLV.setAdapter(popularLVAdapter);

        popularLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String routineName = (String) popularLV.getItemAtPosition(position);

                Intent i = new Intent(MainActivity.this, RoutineActivity.class);
                i.putExtra("routine_name", routineName);
                startActivity(i);
            }
        });
    }
}
