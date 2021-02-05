package com.obadarawashdeh.retreivedata.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.obadarawashdeh.retreivedata.Model.EmailModel;
import com.obadarawashdeh.retreivedata.R;
import com.obadarawashdeh.retreivedata.VH.EmailVH;

public class ActEmailUser extends AppCompatActivity {
    RecyclerView eRecyclerView;
    FirebaseDatabase rFirebaseDatabase;
    DatabaseReference rRef;
    MenuItem hide,hide2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_email);

        eRecyclerView=findViewById(R.id.eRecyclerView);
        eRecyclerView.setHasFixedSize(true);
        eRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        rFirebaseDatabase=FirebaseDatabase.getInstance();
        rRef=rFirebaseDatabase.getReference("Emails");
    }

    private void firebaseSearch(String searchText){
        Query firebaseSearchQuery=rRef.orderByChild("doctor").startAt(searchText).endAt(searchText+"\uf8ff");
        FirebaseRecyclerAdapter<EmailModel, EmailVH> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<EmailModel, EmailVH>(
                EmailModel.class,
                R.layout.elayout,
                EmailVH.class,
                firebaseSearchQuery
        ) {
            @Override
            protected void populateViewHolder(EmailVH emailVH, EmailModel emailModel, int i) {
                emailVH.setDetails(getApplicationContext(),emailModel.getDoctor(),emailModel.getEmail());
            }
            @Override
            public EmailVH onCreateViewHolder(ViewGroup parent, int viewType) {
                EmailVH viewHolder= super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new EmailVH.clickListener() {
                    @Override
                    public void onItemLongClick(View view, int position) {
                        String mail= getItem(position).getEmail();
                        Intent sendMail=new Intent(Intent.ACTION_SEND);
                        sendMail.setData(Uri.parse("mailto:"));
                        sendMail.setType("text/plain");
                        sendMail.putExtra(Intent.EXTRA_EMAIL,new String[]{mail});
                        sendMail.putExtra(Intent.EXTRA_SUBJECT,"Subject....");
                        sendMail.putExtra(Intent.EXTRA_TEXT,"Hello Doctor\n" +
                                "I am the student (....) who has the university number (.....) I am learning from you the subject (.....), I would like to inquire about (.....) for (.....)");
                        startActivity(sendMail);
                    }
                });
                return viewHolder;
            }
        };
        eRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<EmailModel, EmailVH> firebaseRecyclerAdapter=new FirebaseRecyclerAdapter<EmailModel, EmailVH>(
                EmailModel.class,
                R.layout.elayout,
                EmailVH.class,
                rRef
        ) {
            @Override
            protected void populateViewHolder(EmailVH emailVH, EmailModel emailModel, int i) {
                emailVH.setDetails(getApplicationContext(),emailModel.getDoctor(),emailModel.getEmail());
            }

            @Override
            public EmailVH onCreateViewHolder(ViewGroup parent, int viewType) {
                EmailVH viewHolder= super.onCreateViewHolder(parent, viewType);
                viewHolder.setOnClickListener(new EmailVH.clickListener() {
                    @Override
                    public void onItemLongClick(View view, int position) {
                        String mail= getItem(position).getEmail();
                        Intent sendMail=new Intent(Intent.ACTION_SEND);
                        sendMail.setData(Uri.parse("mailto:"));
                        sendMail.setType("text/plain");
                        sendMail.putExtra(Intent.EXTRA_EMAIL,new String[]{mail});
                        sendMail.putExtra(Intent.EXTRA_SUBJECT,"Subject....");
                        sendMail.putExtra(Intent.EXTRA_TEXT,"Hello Doctor\n" +
                                "I am the student (....) who has the university number (.....) I am learning from you the subject (.....), I would like to inquire about (.....) for (.....)");
                        startActivity(sendMail);
                    }
                });
                return viewHolder;
            }
        };
        eRecyclerView.setAdapter(firebaseRecyclerAdapter);
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
