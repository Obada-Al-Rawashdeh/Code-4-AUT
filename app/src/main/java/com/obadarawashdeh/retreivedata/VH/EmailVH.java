package com.obadarawashdeh.retreivedata.VH;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obadarawashdeh.retreivedata.R;

public class EmailVH extends RecyclerView.ViewHolder {
    View eview;
    public EmailVH(@NonNull View itemView) {
        super(itemView);
        eview=itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eClickListener.onItemLongClick(v,getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                eClickListener.onItemLongClick(v,getAdapterPosition());
                return true;
            }
        });
    }

    public void setDetails(Context ctx, String doctor,String email){
        TextView etextView=eview.findViewById(R.id.eTV);
        TextView dtextView=eview.findViewById(R.id.dTV);
        etextView.setText(email);
        dtextView.setText(doctor);

    }
    private EmailVH.clickListener eClickListener;
    public interface clickListener{
        void onItemLongClick(View view,int position);
    }
    public void setOnClickListener(EmailVH.clickListener clickListener){
        eClickListener=clickListener;
    }
}
