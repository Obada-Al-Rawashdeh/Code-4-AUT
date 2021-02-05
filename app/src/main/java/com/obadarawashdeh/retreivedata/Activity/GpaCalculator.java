package com.obadarawashdeh.retreivedata.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.obadarawashdeh.retreivedata.R;

public class GpaCalculator extends AppCompatActivity {

    EditText Smark,Shour,Pgpa,Phour;
    TextView textView;
    double mark=0,gpa=0,result=0;
    int Bhour=0,Ahour=0;
    public static final String CALCULATOR_PACKAGE ="com.android.calculator2";
    public static final String CALCULATOR_CLASS ="com.android.calculator2.Calculator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpa_calculator);

        Smark=findViewById(R.id.smark);
        Shour=findViewById(R.id.shour);
        Pgpa=findViewById(R.id.pgpa);
        Phour=findViewById(R.id.phour);
        textView=findViewById(R.id.result);

        findViewById(R.id.gpaCal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mark=Double.parseDouble(Smark.getText().toString());
                    gpa=Double.parseDouble(Pgpa.getText().toString());
                    Bhour=Integer.parseInt(Phour.getText().toString());
                    Ahour=Integer.parseInt(Shour.getText().toString());
                    if(Bhour<=0 || Ahour<=0){
                        textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(35)});
                        textView.setText(R.string.zero_or_less);

                    }else {
                        textView.setFilters(new InputFilter[]{new InputFilter.LengthFilter(4)});
                        result=((mark*Ahour)+(gpa*Bhour))/(Bhour+Ahour);
                        textView.setText(String.valueOf(result));
                    }

                }catch (Exception e){
                    Toast.makeText(getBaseContext(),R.string.some_empty,Toast.LENGTH_SHORT).show();
                }

            }
        });

        findViewById(R.id.semestercla).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder =new AlertDialog.Builder(GpaCalculator.this)
                        .setMessage(R.string.semester_cal);
                builder.setPositiveButton(R.string.calculate, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            Intent intent = new Intent();

                            intent.setAction(Intent.ACTION_MAIN);
                            intent.addCategory(Intent.CATEGORY_LAUNCHER);
                            intent.setComponent(new ComponentName(
                                    CALCULATOR_PACKAGE,
                                    CALCULATOR_CLASS));

                            startActivity(intent);
                        }catch (Exception e){

                        }
                    }
                });
                builder.create().show();
            }
        });
    }
}
