package com.obadarawashdeh.retreivedata.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.obadarawashdeh.retreivedata.Model.RoomModel;
import com.obadarawashdeh.retreivedata.R;
import com.obadarawashdeh.retreivedata.RoomUpload;
import com.obadarawashdeh.retreivedata.VH.RoomVH;

public class ActRoomsUser extends AppCompatActivity {

    SharedPreferences msSharedPref;
    RecyclerView rRecyclerView;
    FirebaseDatabase rFirebaseDatabase;
    DatabaseReference rRef;
    MenuItem hide,hide2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_rooms);

        rRecyclerView=findViewById(R.id.rrecyvlerView);
        rRecyclerView.setHasFixedSize(true);
        rRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        rFirebaseDatabase=FirebaseDatabase.getInstance();
        rRef=rFirebaseDatabase.getReference("Rooms");

    }

    private void firebaseSearch(String searchText){
        Query firebaseSearchQuery=rRef.orderByChild("room").startAt(searchText).endAt(searchText+"\uf8ff");
        FirebaseRecyclerAdapter<RoomModel, RoomVH> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<RoomModel, RoomVH>(
                        RoomModel.class,
                        R.layout.rlayout,
                        RoomVH.class,
                        firebaseSearchQuery

                ) {
                    @Override
                    protected void populateViewHolder(RoomVH roomVH, RoomModel roomModel, int i) {
                        roomVH.setDetails(getApplicationContext(),roomModel.getRoom());

                    }
                    @Override
                    public RoomVH onCreateViewHolder(ViewGroup parent, int viewType) {
                        RoomVH viewHolder=super.onCreateViewHolder(parent,viewType);
                        viewHolder.setOnClickListener(new RoomVH.clickListener() {
                            @Override
                            public void onItemLongClick(View view, int position) {
                            }
                        });
                        return viewHolder;
                    }

                };
        rRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }


    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<RoomModel,RoomVH> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<RoomModel, RoomVH>(
                RoomModel.class,
                R.layout.rlayout,
                RoomVH.class,
                rRef
        ) {
            @Override
            protected void populateViewHolder(RoomVH roomVH, RoomModel roomModel, int i) {
                roomVH.setDetails(getApplicationContext(),roomModel.getRoom());
            }
            @Override
            public RoomVH onCreateViewHolder(ViewGroup parent, int viewType) {
                RoomVH viewHolder=super.onCreateViewHolder(parent,viewType);
                viewHolder.setOnClickListener(new RoomVH.clickListener() {
                    @Override
                    public void onItemLongClick(View view, int position) {
                    }
                });
                return viewHolder;
            }
        };
        rRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        SearchView searchView =(SearchView) menu.findItem(R.id.action_search).getActionView();
        hide=menu.findItem(R.id.action_sort);
        hide.setVisible(false);
        hide2=menu.findItem(R.id.action_add);
        hide2.setVisible(false);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                firebaseSearch(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                firebaseSearch(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

}

