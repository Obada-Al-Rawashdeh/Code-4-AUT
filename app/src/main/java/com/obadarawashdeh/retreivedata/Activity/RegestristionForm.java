package com.obadarawashdeh.retreivedata.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.obadarawashdeh.retreivedata.Model.VisitorModel;
import com.obadarawashdeh.retreivedata.R;

public class RegestristionForm extends AppCompatActivity {

    DatabaseReference databaseReference;
    Button submit;
    EditText name,national_number,pob,dob,sex,nationality,phone,email,secondary,average,year,school,country;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regestristion_form);

        databaseReference= FirebaseDatabase.getInstance().getReference("form");
        name=findViewById(R.id.Vname);
        national_number=findViewById(R.id.Vnumber);
        pob=findViewById(R.id.VplaceOfBirth);
        dob=findViewById(R.id.VDateOfBirth);
        sex=findViewById(R.id.Vsex);
        nationality=findViewById(R.id.Vnatonality);
        phone=findViewById(R.id.Vphone);
        email=findViewById(R.id.Vemail);
        secondary=findViewById(R.id.Vsecondary);
        average=findViewById(R.id.Vavareg);
        year=findViewById(R.id.Vyear);
        school=findViewById(R.id.Vschool);
        country=findViewById(R.id.Vcountry);
        submit=findViewById(R.id.Vsubmit);



        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!(   name.getText().toString().equals("")||
                        national_number.getText().toString().equals("")||
                        pob.getText().toString().equals("")||
                        dob.getText().toString().equals("")||
                        sex.getText().toString().equals("")||
                        nationality.getText().toString().equals("")||
                        phone.getText().toString().equals("")||
                        email.getText().toString().equals("")||
                        secondary.getText().toString().equals("")||
                        average.getText().toString().equals("")||
                        year.getText().toString().equals("")||
                        school.getText().toString().equals("")||
                        country.getText().toString().equals(""))){

                    VisitorModel visitorModel=new VisitorModel(name.getText().toString(),national_number.getText().toString(),
                            pob.getText().toString(),dob.getText().toString(),sex.getText().toString(),nationality.getText().toString(),
                            phone.getText().toString(),email.getText().toString(),secondary.getText().toString(),average.getText().toString(),
                            year.getText().toString(),school.getText().toString(),country.getText().toString());
                    databaseReference.push().setValue(visitorModel);
                    Toast.makeText(RegestristionForm.this, R.string.request_send,Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(RegestristionForm.this, R.string.some_empty,Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
