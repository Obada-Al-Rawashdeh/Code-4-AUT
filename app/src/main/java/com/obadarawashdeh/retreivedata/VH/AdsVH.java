package com.obadarawashdeh.retreivedata.VH;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.obadarawashdeh.retreivedata.R;
import com.squareup.picasso.Picasso;

public class AdsVH extends RecyclerView.ViewHolder {
    View mview;

    public AdsVH(@NonNull View itemView) {
        super(itemView);
        mview=itemView;

        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mClickListener.onItemLongClick(v,getAdapterPosition());
            }
        });

        itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mClickListener.onItemLongClick(v,getAdapterPosition());
                return true;
            }
        });
    }

    public void setDetails(Context ctx,String title,String description,String image){
        TextView mTiltleTv=mview.findViewById(R.id.rTitleTv);
        TextView mDetailTv=mview.findViewById(R.id.rDescriptionTv);
        ImageView mImageIv=mview.findViewById(R.id.rImageView);

        mTiltleTv.setText(title);
        mDetailTv.setText(description);
        //Picasso.get().load(image).into(mImageIv);
        Picasso.get().load(image).resize(700,700).centerCrop().into(mImageIv);
    }

    private AdsVH.clickListener mClickListener;

    public interface clickListener{
        void onItemLongClick(View view,int position);
    }

    public void setOnClickListener(AdsVH.clickListener clickListener){
        mClickListener=clickListener;
    }
}
