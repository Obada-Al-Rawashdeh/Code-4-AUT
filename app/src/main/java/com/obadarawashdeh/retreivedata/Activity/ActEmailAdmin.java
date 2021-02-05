package com.obadarawashdeh.retreivedata.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
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
import com.obadarawashdeh.retreivedata.Model.EmailModel;
import com.obadarawashdeh.retreivedata.R;
import com.obadarawashdeh.retreivedata.RoomUpload;
import com.obadarawashdeh.retreivedata.Upload;
import com.obadarawashdeh.retreivedata.VH.EmailVH;

public class ActEmailAdmin extends AppCompatActivity {
    RecyclerView eRecyclerView;
    FirebaseDatabase rFirebaseDatabase;
    DatabaseReference rRef;
    MenuItem hide;

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
                        final String cEmail=getItem(position).getEmail();
                        final String cDoctor=getItem(position).getDoctor();
                        final AlertDialog.Builder builder=new AlertDialog.Builder(ActEmailAdmin.this);
                        String[] options={getString(R.string.update),getString(R.string.delete)};
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    Intent intent=new Intent(ActEmailAdmin.this, ActEmailUpload.class);
                                    intent.putExtra("cEmail",cEmail);
                                    intent.putExtra("cDoctor",cDoctor);
                                    startActivity(intent);
                                }else if(which==1){
                                    DeleteEmail(cDoctor);
                                }
                            }
                        });
                        builder.create().show();
                    }
                });
                return viewHolder;
            }
        };
        eRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }
    private void DeleteEmail(final String cDoctor) {
        AlertDialog.Builder builder=new AlertDialog.Builder(ActEmailAdmin.this);
        builder.setTitle(getString(R.string.delete))
                .setMessage(R.string.delete_sure);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Query rQuery=rRef.orderByChild("doctor").equalTo(cDoctor);
                rQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds:dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(ActEmailAdmin.this,R.string.delete_success,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(ActEmailAdmin.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builder.create().show();
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
                        final String cEmail=getItem(position).getEmail();
                        final String cDoctor=getItem(position).getDoctor();
                        final AlertDialog.Builder builder=new AlertDialog.Builder(ActEmailAdmin.this);
                        String[] options={getString(R.string.update),getString(R.string.delete)};
                        builder.setItems(options, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if(which==0){
                                    Intent intent=new Intent(ActEmailAdmin.this, ActEmailUpload.class);
                                    intent.putExtra("cEmail",cEmail);
                                    intent.putExtra("cDoctor",cDoctor);
                                    startActivity(intent);
                                }else if(which==1){
                                    DeleteEmail(cDoctor);
                                }
                            }
                        });
                        builder.create().show();
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

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id=item.getItemId();
        if(id==R.id.action_add){
            startActivity(new Intent(ActEmailAdmin.this, ActEmailUpload.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}