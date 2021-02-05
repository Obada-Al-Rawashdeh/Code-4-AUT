package com.obadarawashdeh.retreivedata.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import com.obadarawashdeh.retreivedata.Model.AdsModel;
import com.obadarawashdeh.retreivedata.R;
import com.obadarawashdeh.retreivedata.VH.AdsVH;

import static com.google.firebase.storage.FirebaseStorage.getInstance;

public class ActuserAds extends AppCompatActivity {

    LinearLayoutManager mLayoutManager;
    SharedPreferences msSharedPref;
    RecyclerView mRecyclerView;
    FirebaseDatabase mFirebaseDatabase;
    DatabaseReference mRef;
    MenuItem hide;

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
                    public AdsVH onCreateViewHolder(ViewGroup parent, int viewType) {
                        AdsVH viewHolder=super.onCreateViewHolder(parent,viewType);
                        viewHolder.setOnClickListener(new AdsVH.clickListener() {
                            @Override
                            public void onItemLongClick(View view, int position) {
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
                    public AdsVH onCreateViewHolder(ViewGroup parent, int viewType) {
                        AdsVH viewHolder=super.onCreateViewHolder(parent,viewType);
                        viewHolder.setOnClickListener(new AdsVH.clickListener() {
                            @Override
                            public void onItemLongClick(View view, int position) {
                            }
                        });
                        return viewHolder;
                    }

                };
        mRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        SearchView searchView =(SearchView) menu.findItem(R.id.action_search).getActionView();
        hide=menu.findItem(R.id.action_add);
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
        if(id==R.id.action_sort){
            showSortDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void editData(){

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
