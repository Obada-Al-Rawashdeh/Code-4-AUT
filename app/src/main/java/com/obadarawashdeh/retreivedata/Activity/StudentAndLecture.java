package com.obadarawashdeh.retreivedata.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.obadarawashdeh.retreivedata.R;

public class StudentAndLecture extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_and_lecture);

        findViewById(R.id.news).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentAndLecture.this,ActuserAds.class));
            }
        });
        findViewById(R.id.weather).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentAndLecture.this,WeatherAct.class));
            }
        });
        findViewById(R.id.emails).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentAndLecture.this,ActEmailUser.class));
            }
        });
        findViewById(R.id.rooms).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentAndLecture.this,ActRoomsUser.class));
            }
        });

        findViewById(R.id.gpa).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentAndLecture.this,GpaCalculator.class));
            }
        });
    }
}
