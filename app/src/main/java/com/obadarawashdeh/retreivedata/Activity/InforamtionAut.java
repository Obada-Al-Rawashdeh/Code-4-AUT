package com.obadarawashdeh.retreivedata.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.obadarawashdeh.retreivedata.Model.InfoModel;
import com.obadarawashdeh.retreivedata.R;
import com.obadarawashdeh.retreivedata.VH.InfoAdapter;

import java.util.ArrayList;

public class InforamtionAut extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<InfoModel> infoModels;
    LinearLayoutManager linearLayoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inforamtion_aut);

        linearLayoutManager=new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView=findViewById(R.id.infoRecyclerView);
        infoModels=new ArrayList<>();
        infoModels.add(new InfoModel(R.drawable.aut1,R.string.aut1));
        infoModels.add(new InfoModel(R.drawable.aut2,R.string.aut2));
        infoModels.add(new InfoModel(R.drawable.aut3,R.string.aut3));
        infoModels.add(new InfoModel(R.drawable.aut4,R.string.aut4));
        infoModels.add(new InfoModel(R.drawable.aut5,R.string.aut5));
        InfoAdapter infoAdapter=new InfoAdapter(infoModels);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(infoAdapter);

    }
}
