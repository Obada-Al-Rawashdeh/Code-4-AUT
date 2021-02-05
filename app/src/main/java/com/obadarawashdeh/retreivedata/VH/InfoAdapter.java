package com.obadarawashdeh.retreivedata.VH;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obadarawashdeh.retreivedata.Model.InfoModel;
import com.obadarawashdeh.retreivedata.R;

import java.util.ArrayList;


public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoVH> {

    ArrayList<InfoModel> arrayList;
    public InfoAdapter(ArrayList<InfoModel> infoModels){
        arrayList=infoModels;
    }
    @NonNull
    @Override
    public InfoVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new InfoVH(LayoutInflater.from(parent.getContext()).inflate(R.layout.information,null,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InfoVH holder, int position) {
        InfoModel model=arrayList.get(position);
        holder.imageView.setImageResource(model.getImg());
        holder.textView.setText(model.getDesc());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class InfoVH extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;
        public InfoVH(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.iImageView);
            textView=itemView.findViewById(R.id.iDescriptionTv);
        }
    }
}
