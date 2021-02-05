package com.obadarawashdeh.retreivedata.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.obadarawashdeh.retreivedata.R;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawerLayout = findViewById(R.id.drawerMain);
        mToggle = new ActionBarDrawerToggle(this, mDrawerLayout, R.string.open, R.string.close);

        mDrawerLayout.addDrawerListener(mToggle);
        mToggle.syncState();

        //getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        NavigationView nav_view = findViewById(R.id.nav_view);

        nav_view.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.website:
                        Intent i1 = new Intent(Intent.ACTION_VIEW);
                        i1.setData(Uri.parse("http://www.aut.edu.jo"));
                        startActivity(i1);
                        break;
                    case R.id.gate:
                        Intent i2 = new Intent(Intent.ACTION_VIEW);
                        i2.setData(Uri.parse("http://edugate.aut.edu.jo/Edugate/faces/ui/login.xhtml"));
                        startActivity(i2);
                        break;
                    case R.id.phone:
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", "+ 962 03 2092300", null));
                        startActivity(intent);
                        break;
                    case R.id.eemail:
                        Intent sendMail=new Intent(Intent.ACTION_SEND);
                        sendMail.setData(Uri.parse("mailto:"));
                        sendMail.setType("text/plain");
                        sendMail.putExtra(Intent.EXTRA_EMAIL,new String[]{"info@aut.edu.jo"});
                        startActivity(sendMail);
                        break;
                    case R.id.facebook:
                        Intent i3 = new Intent(Intent.ACTION_VIEW);
                        i3.setData(Uri.parse("https://m.facebook.com/AUT.official/"));
                        startActivity(i3);
                        break;
                    case R.id.twitter:
                        Intent i4 = new Intent(Intent.ACTION_VIEW);
                        i4.setData(Uri.parse("https://twitter.com/autjo_official"));
                        startActivity(i4);
                        break;
                    case R.id.instgram:
                        Intent i5 = new Intent(Intent.ACTION_VIEW);
                        i5.setData(Uri.parse("https://www.instagram.com/autjo_official/"));
                        startActivity(i5);
                        break;
                }
                return false;
            }
        });

        findViewById(R.id.admin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Login.class));
            }
        });

        findViewById(R.id.sAndL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,StudentAndLecture.class));
            }
        });

        findViewById(R.id.visitor).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,Visitor.class));
            }
        });

        findViewById(R.id.Exit).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
