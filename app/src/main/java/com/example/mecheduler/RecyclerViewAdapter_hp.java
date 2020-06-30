package com.example.mecheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mecheduler.DTO.LikeHospital;

import java.util.ArrayList;


/**
 * @Founder Xinji
 * @Describe Android Zero Foundation Introduction to Proficiency Series
 * Share Expert
 */


public class RecyclerViewAdapter_hp extends RecyclerView.Adapter<RecyclerViewAdapter_hp.ViewHolder> {
    private ArrayList<LikeHospital> mDatas = null;
    private LayoutInflater mInflater = null;



    public interface OnListItemLongSelectedInterface {
        void onItemLongSelected(View v, int position);
    }

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    private OnListItemSelectedInterface mListener;
    private OnListItemLongSelectedInterface mLongListener;


    public RecyclerViewAdapter_hp(Context context, ArrayList<LikeHospital> datas, OnListItemSelectedInterface listener) {
        this.mDatas = datas;
        this.mListener = listener;
        this.mInflater = LayoutInflater.from(context);
    }

    public RecyclerViewAdapter_hp(Context context, ArrayList<LikeHospital> datas) {
        this.mDatas = datas;
        this.mInflater = LayoutInflater.from(context);
    }


    // Create a new View, called by LayoutManager
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.mypost_item_hp, parent, false);
        ViewHolder vewHolder = new ViewHolder(view);
        return vewHolder;
    }

    // Operation of binding data to interface
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.postkind.setText(mDatas.get(position).getHpname());
        holder.eventDate.setText(mDatas.get(position).getHptel());
        holder.animal_kind.setText(mDatas.get(position).getHpaddress());



    }

    // Number of data obtained
    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    // Custom ViewHolder, which holds all the interface components for each Item
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView postkind = null;
        public ImageView postimg = null;
        public TextView animal_kind = null;
        public TextView eventDate = null;
        public TextView bcode;
        public ImageView heart;

        public LinearLayout ll =null;

        public ViewHolder(View itemView) {

            super(itemView);

            postkind = (TextView) itemView.findViewById(R.id.postkind);
            animal_kind = (TextView) itemView.findViewById(R.id.animal_kind);
            eventDate= (TextView) itemView.findViewById(R.id.eventdate);
            heart= (ImageView) itemView.findViewById(R.id.heart);



            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    mListener.onItemSelected(v, getAdapterPosition());
                    return true;
                }
            });

        }



    }
}