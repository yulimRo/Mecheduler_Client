package com.example.mecheduler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mecheduler.DTO.HospitalItem2;
import com.example.mecheduler.DTO.LikeHospital;

import java.util.ArrayList;


/**
 * @Founder Xinji
 * @Describe Android Zero Foundation Introduction to Proficiency Series
 * Share Expert
 */


public class RecyclerViewAdapter_s1 extends RecyclerView.Adapter<RecyclerViewAdapter_s1.ViewHolder> {
    private ArrayList<HospitalItem2> mDatas = null;
    private LayoutInflater mInflater = null;



    public interface OnListItemLongSelectedInterface {
        void onItemLongSelected(View v, int position);
    }

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    private OnListItemSelectedInterface mListener;
    private OnListItemLongSelectedInterface mLongListener;


    public RecyclerViewAdapter_s1(Context context, ArrayList<HospitalItem2> datas, OnListItemSelectedInterface listener) {
        this.mDatas = datas;
        this.mListener = listener;
        this.mInflater = LayoutInflater.from(context);
    }

    public RecyclerViewAdapter_s1(Context context, ArrayList<HospitalItem2> datas) {
        this.mDatas = datas;
        this.mInflater = LayoutInflater.from(context);
    }


    // Create a new View, called by LayoutManager
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.item, parent, false);
        ViewHolder vewHolder = new ViewHolder(view);
        return vewHolder;
    }

    // Operation of binding data to interface
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.textTitle.setText(mDatas.get(position).getDutyAddr());
        holder.textContent1.setText(mDatas.get(position).getDutyName());
        holder.textContent2.setText(mDatas.get(position).getDutyTel1());
        holder.textContent3.setText(mDatas.get(position).getDutyMapimg());
        holder.textContent4.setText(mDatas.get(position).getHpid());

    }

    // Number of data obtained
    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    // Custom ViewHolder, which holds all the interface components for each Item
    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textTitle;
        TextView textContent1;
        TextView textContent2;
        TextView textContent3;
        TextView textContent4;

        public LinearLayout ll =null;

        public ViewHolder(View itemView) {

            super(itemView);

            textContent1 = itemView.findViewById(R.id.hp_name_text);
            textTitle = itemView.findViewById(R.id.hp_addr_text);
            textContent3 =  itemView.findViewById(R.id.hp_Mapimg_text);
            textContent2 = itemView.findViewById(R.id.hp_tel_text);
            textContent4 = itemView.findViewById(R.id.hpid);



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