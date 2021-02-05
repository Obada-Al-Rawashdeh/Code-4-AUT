package com.obadarawashdeh.retreivedata.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.obadarawashdeh.retreivedata.R;

public class Visitor extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visitor);
        findViewById(R.id.infAUT).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Visitor.this,InforamtionAut.class));
            }
        });

        findViewById(R.id.Reform).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Visitor.this,RegestristionForm.class));
            }
        });

        findViewById(R.id.Vweather).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Visitor.this,WeatherAct.class));
            }
        });
    }
}
