package com.obadarawashdeh.retreivedata;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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
import com.obadarawashdeh.retreivedata.Activity.ActRooms;
import com.obadarawashdeh.retreivedata.Activity.AdsActivity;
import com.obadarawashdeh.retreivedata.Model.RoomModel;

public class RoomUpload extends AppCompatActivity {

     DatabaseReference databaseReference;
     EditText editText;
     Button button;
     String cRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room_upload);

        databaseReference= FirebaseDatabase.getInstance().getReference("Rooms");
        editText=findViewById(R.id.RUploadEt);
        button=findViewById(R.id.RUploadBtn);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            cRoom=bundle.getString("cRoom");
            editText.setText(cRoom);
            button.setText(R.string.update);
        }

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!editText.getText().toString().equals("")){
                    if(button.getText().equals(getString(R.string.update))){
                        updateRoom();
                    }else {
                        uploadRoom();
                    }

                }else {
                    Toast.makeText(RoomUpload.this,R.string.empty,Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void updateRoom() {
        final String room=editText.getText().toString();
        FirebaseDatabase rFirebaseDatabase=FirebaseDatabase.getInstance();
        DatabaseReference rRef =rFirebaseDatabase.getReference("Rooms");
        Query query=rRef.orderByChild("room").equalTo(cRoom);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds:dataSnapshot.getChildren()){
                    ds.getRef().child("room").setValue(room);
                }
                Toast.makeText(RoomUpload.this, R.string.database_updated, Toast.LENGTH_LONG).show();
                startActivity(new Intent(RoomUpload.this, ActRooms.class));
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void uploadRoom() {
        RoomModel roomModel=new RoomModel(editText.getText().toString());
        databaseReference.push().setValue(roomModel);
        Toast.makeText(RoomUpload.this, R.string.upload_done,Toast.LENGTH_SHORT).show();
    }
}
