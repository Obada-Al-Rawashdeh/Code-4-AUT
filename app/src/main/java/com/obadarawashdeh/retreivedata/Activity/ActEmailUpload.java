package com.obadarawashdeh.retreivedata.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.obadarawashdeh.retreivedata.Model.EmailModel;
import com.obadarawashdeh.retreivedata.Model.RoomModel;
import com.obadarawashdeh.retreivedata.R;
import com.obadarawashdeh.retreivedata.RoomUpload;

public class ActEmailUpload extends AppCompatActivity {

    DatabaseReference databaseReference;
    EditText editText1,editText2;
    Button button;
    String cEmail,cDoctor;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_email_upload);

        databaseReference= FirebaseDatabase.getInstance().getReference("Emails");
        editText1=findViewById(R.id.demail);
        editText2=findViewById(R.id.dname);
        button=findViewById(R.id.eUpload);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            cEmail=bundle.getString("cEmail");
            cDoctor=bundle.getString("cDoctor");
            editText1.setText(cEmail);
            editText2.setText(cDoctor);
            button.setText(R.string.update);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!(editText1.getText().toString().equals("") ||editText2.getText().toString().equals("") )){
                    if(button.getText().equals(getString(R.string.update))){
                        updateRoom();
                    }else {
                        uploadEmail();
                    }

                }else {
                    Toast.makeText(ActEmailUpload.this, R.string.empty,Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
    private void uploadEmail() {
        EmailModel emailModel=new EmailModel(editText2.getText().toString(),editText1.getText().toString());
        databaseReference.push().setValue(emailModel);
        Toast.makeText(ActEmailUpload.this, R.string.upload_done,Toast.LENGTH_SHORT).show();
    }

    private void updateRoom() {
        final String email=editText1.getText().toString();
        final String doctor=editText2.getText().toString();
        FirebaseDatabase rFirebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference rRef =rFirebaseDatabase.getReference("Emails");
        Query query=rRef.orderByChild("doctor").equalTo(cDoctor);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    ds.getRef().child("email").setValue(email);
                    ds.getRef().child("doctor").setValue(doctor);
                }
                Toast.makeText(ActEmailUpload.this, R.string.database_updated, Toast.LENGTH_LONG).show();
                startActivity(new Intent(ActEmailUpload.this, ActEmailAdmin.class));
                finish();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
