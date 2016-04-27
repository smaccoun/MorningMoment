package com.example.stevenmaccoun.morningmoment;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.stevenmaccoun.morningmoment.db.RoutineTaskRepository;
import com.example.stevenmaccoun.morningmoment.utilities.DateFormatHandler;

import org.w3c.dom.Text;

import java.text.ParseException;

public class CreateRoutineTaskActivity extends AppCompatActivity {

    private Button createTaskB;
    private TextView tvName;
    private TextView tvDesc;
    private TextView tvDuration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_routine_task);

        tvName = (TextView) findViewById(R.id.nm_entry);
        tvDesc = (TextView) findViewById(R.id.desc_entry);
        tvDuration = (TextView) findViewById(R.id.duration_entry);

        createTaskB = (Button) findViewById(R.id.save);
        createTaskB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = tvName.getText().toString();
                String desc = tvDesc.getText().toString();
                long duration = DateFormatHandler.toLong(tvDuration.getText().toString());

                RoutineTask rt = new RoutineTask(name, desc, duration);
                RoutineTaskRepository rtr = new RoutineTaskRepository(getApplicationContext());
                boolean success = rtr.Save(rt);
                Toast.makeText(getApplicationContext(), "Task Created", Toast.LENGTH_LONG);
                finish();
            }
        });
    }
}
