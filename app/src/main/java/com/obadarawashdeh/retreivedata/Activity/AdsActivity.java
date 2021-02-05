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
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.StorageReference;
import com.obadarawashdeh.retreivedata.Model.AdsModel;
import com.obadarawashdeh.retreivedata.R;
import com.obadarawashdeh.retreivedata.Upload;
import com.obadarawashdeh.retreivedata.VH.AdsVH;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class AdsActivity extends AppCompatActivity {

    LinearLayoutManager mLayoutManager;
    SharedPreferences msSharedPref;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ads_activity);
        mLayoutManager=new LinearLayoutManager(this);
        mRecyclerView=findViewById(R.id.recyvlerView);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mFirebaseDatabase=FirebaseDatabase.getInstance();
        mRef=mFirebaseDatabase.getReference("Data");

        msSharedPref=getSharedPreferences("SortSettings",MODE_PRIVATE);
        String mSorting=msSharedPref.getString("Sort","newest");

        if (mSorting.equals("newest")){
            mLayoutManager.setReverseLayout(true);
            mLayoutManager.setStackFromEnd(true);
        }else if (mSorting.equals("oldest")){
            mLayoutManager.setReverseLayout(false);
            mLayoutManager.setStackFromEnd(false);
        }
    }

    private void firebaseSearch(String searchText){
        Query firebaseSearchQuery=mRef.orderByChild("title").startAt(searchText).endAt(searchText+"\uf8ff");

        FirebaseRecyclerAdapter<AdsModel, AdsVH> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<AdsModel, AdsVH>(
                        AdsModel.class,
                        R.layout.row,
                        AdsVH.class,
                        firebaseSearchQuery
                ) {
                    @Override
                    protected void populateViewHolder(AdsVH viewHolder, AdsModel model, int i) {
                        viewHolder.setDetails(getApplicationContext(),model.getTitle(),model.getDescription(),model.getImage());
                    }
                    //for admin
                    @Override
                    public AdsVH onCreateViewHolder(ViewGroup parent, int viewType) {
                        AdsVH viewHolder=super.onCreateViewHolder(parent,viewType);
                        viewHolder.setOnClickListener(new AdsVH.clickListener() {
                            @Override
                            public void onItemLongClick(View view, int position) {
                                final String cTitle=getItem(position).getTitle();
                                final String cImage=getItem(position).getImage();
                                final String cDescr=getItem(position).getDescription();

                                AlertDialog.Builder builder=new AlertDialog.Builder(AdsActivity.this);
                                String[] options={getString(R.string.update),getString(R.string.delete)};
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which==0){

                                            Intent intent=new Intent(AdsActivity.this, Upload.class);
                                            intent.putExtra("cTitle",cTitle);
                                            intent.putExtra("cDescr",cDescr);
                                            intent.putExtra("cImage",cImage);
                                            startActivity(intent);
                                        }else if(which==1){
                                            showDeleteDataDialog(cTitle,cImage);
                                        }
                                    }
                                });
                                builder.create().show();
                            }
                        });
                        return viewHolder;
                    }
                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<AdsModel, AdsVH> firebaseRecyclerAdapter=
                new FirebaseRecyclerAdapter<AdsModel, AdsVH>(
                        AdsModel.class,
                        R.layout.row,
                        AdsVH.class,
                        mRef
                ) {
                    @Override
                    protected void populateViewHolder(AdsVH viewHolder, AdsModel model, int i) {
                        viewHolder.setDetails(getApplicationContext(),model.getTitle(),model.getDescription(),model.getImage());
                    }
                    //for admin
                    @Override
                    public AdsVH onCreateViewHolder(ViewGroup parent, int viewType) {
                        AdsVH viewHolder=super.onCreateViewHolder(parent,viewType);
                        viewHolder.setOnClickListener(new AdsVH.clickListener() {
                            @Override
                            public void onItemLongClick(View view, int position) {
                                final String cTitle=getItem(position).getTitle();
                                final String cImage=getItem(position).getImage();
                                final String cDescr=getItem(position).getDescription();

                                AlertDialog.Builder builder=new AlertDialog.Builder(AdsActivity.this);
                                String[] options={getString(R.string.update),getString(R.string.delete)};
                                builder.setItems(options, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if(which==0){

                                            Intent intent=new Intent(AdsActivity.this,Upload.class);
                                            intent.putExtra("cTitle",cTitle);
                                            intent.putExtra("cDescr",cDescr);
                                            intent.putExtra("cImage",cImage);
                                            startActivity(intent);
                                        }else if(which==1){
                                            showDeleteDataDialog(cTitle,cImage);
                                        }else if(which==2){
                                            startActivity(new Intent(AdsActivity.this,Upload.class));
                                        }
                                    }
                                });
                                builder.create().show();
                            }
                        });
                        return viewHolder;
                    }
                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    private void showDeleteDataDialog(final String currentTitle, final String currentImage) {
        AlertDialog.Builder builder=new AlertDialog.Builder(AdsActivity.this);
        builder.setTitle(getString(R.string.delete))
                .setMessage(R.string.delete_sure);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Query mQuery=mRef.orderByChild("title").equalTo(currentTitle);
                mQuery.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for (DataSnapshot ds:dataSnapshot.getChildren()){
                            ds.getRef().removeValue();
                        }
                        Toast.makeText(AdsActivity.this, R.string.delete_success,Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(AdsActivity.this,databaseError.getMessage(),Toast.LENGTH_SHORT).show();

                    }
                });

                StorageReference mPictureRefe=getInstance().getReferenceFromUrl(currentImage);
                mPictureRefe.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(AdsActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        SearchView searchView =(SearchView) menu.findItem(R.id.action_search).getActionView();
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
        if(id==R.id.action_sort){
            showSortDialog();
            return true;
        }else if(id==R.id.action_add){
            startActivity(new Intent(AdsActivity.this,Upload.class));
        }
        return super.onOptionsItemSelected(item);
    }

    private void showSortDialog() {
        String[] sortOptions={getString(R.string.newest),getString(R.string.oldest)};
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle(R.string.sort_by)
                .setIcon(R.drawable.ic_action_sort)
                .setItems(sortOptions, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (which==0){
                            SharedPreferences.Editor editor=msSharedPref.edit();
                            editor.putString("Sort","newest");
                            editor.apply();
                            recreate();
                        }else if (which==1){
                            SharedPreferences.Editor editor=msSharedPref.edit();
                            editor.putString("Sort","oldest");
                            editor.apply();
                            recreate();
                        }
                    }
                });
        builder.show();
    }
}