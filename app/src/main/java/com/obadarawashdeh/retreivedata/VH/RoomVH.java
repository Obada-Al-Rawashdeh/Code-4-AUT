package com.obadarawashdeh.retreivedata.VH;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obadarawashdeh.retreivedata.R;

public class RoomVH extends RecyclerView.ViewHolder {
    View rview;
    public RoomVH(@NonNull View itemView) {
        super(itemView);
        rview=itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rClickListener.onItemLongClick(v,getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                rClickListener.onItemLongClick(v,getAdapterPosition());
                return true;
            }
        });
    }
    public void setDetails(Context ctx, String room){
        TextView rtextView=rview.findViewById(R.id.rtextView);
        rtextView.setText(room);

    }

    private RoomVH.clickListener rClickListener;

    public interface clickListener{
        void onItemLongClick(View view,int position);
    }

    public void setOnClickListener(RoomVH.clickListener clickListener){
        rClickListener=clickListener;
    }
    
}
