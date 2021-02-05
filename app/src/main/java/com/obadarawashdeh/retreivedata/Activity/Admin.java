package com.obadarawashdeh.retreivedata.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.obadarawashdeh.retreivedata.R;

public class Admin extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        findViewById(R.id.emailsAdmin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this,ActEmailAdmin.class));
            }
        });

        findViewById(R.id.roomsAdmin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this,ActRooms.class));
            }
        });

        findViewById(R.id.newsAdmin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Admin.this,AdsActivity.class));
            }
        });
    }
}
