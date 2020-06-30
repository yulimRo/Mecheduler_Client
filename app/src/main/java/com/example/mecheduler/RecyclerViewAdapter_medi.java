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
import com.example.mecheduler.DTO.MedicineVO;

import java.util.ArrayList;


/**
 * @Founder Xinji
 * @Describe Android Zero Foundation Introduction to Proficiency Series
 * Share Expert
 */


public class RecyclerViewAdapter_medi extends RecyclerView.Adapter<RecyclerViewAdapter_medi.ViewHolder> {
    private ArrayList<MedicineVO> mDatas = null;
    private LayoutInflater mInflater = null;



    public interface OnListItemLongSelectedInterface {
        void onItemLongSelected(View v, int position);
    }

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    private OnListItemSelectedInterface mListener;
    private OnListItemLongSelectedInterface mLongListener;


    public RecyclerViewAdapter_medi(Context context, ArrayList<MedicineVO> datas, OnListItemSelectedInterface listener) {
        this.mDatas = datas;
        this.mListener = listener;
        this.mInflater = LayoutInflater.from(context);
    }

    public RecyclerViewAdapter_medi(Context context, ArrayList<MedicineVO> datas) {
        this.mDatas = datas;
        this.mInflater = LayoutInflater.from(context);
    }


    // Create a new View, called by LayoutManager
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listview_item_medi, parent, false);
        ViewHolder vewHolder = new ViewHolder(view);
        return vewHolder;
    }

    // Operation of binding data to interface
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv1.setText(mDatas.get(position).getMname());
        holder.tv2.setText(mDatas.get(position).getInfo1());
        holder.tv3.setText(mDatas.get(position).getInfo2());
        holder.tv4.setText(mDatas.get(position).getDanger());



    }

    // Number of data obtained
    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    // Custom ViewHolder, which holds all the interface components for each Item
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView tv1 = null;
        public TextView tv2 = null;
        public TextView tv3 = null;
        public TextView tv4 = null;


        public LinearLayout ll =null;

        public ViewHolder(View itemView) {

            super(itemView);

            tv1 = (TextView) itemView.findViewById(R.id.tv1);
            tv2 = (TextView) itemView.findViewById(R.id.tv2);
            tv3= (TextView) itemView.findViewById(R.id.tv3);
            tv4= (TextView) itemView.findViewById(R.id.tv4);



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