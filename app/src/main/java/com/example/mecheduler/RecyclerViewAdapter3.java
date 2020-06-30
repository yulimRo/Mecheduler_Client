package com.example.mecheduler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mecheduler.DTO.LikeList;
import com.example.mecheduler.DTO.MedicineVO;
import com.example.mecheduler.DTO.Reservation_PatientVO;

import java.util.ArrayList;


/**
 * @Founder Xinji
 * @Describe Android Zero Foundation Introduction to Proficiency Series
 * Share Expert
 */
public class RecyclerViewAdapter3 extends RecyclerView.Adapter<RecyclerViewAdapter3.ViewHolder> {
    private ArrayList<LikeList> mDatas = null;
    private LayoutInflater mInflater = null;

    public interface OnListItemLongSelectedInterface {
        void onItemLongSelected(View v, int position);
    }

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    private OnListItemSelectedInterface mListener;
    private OnListItemLongSelectedInterface mLongListener;


    public RecyclerViewAdapter3(Context context, ArrayList<LikeList> datas, OnListItemSelectedInterface listener) {
        this.mDatas = datas;
        this.mListener = listener;
        this.mInflater = LayoutInflater.from(context);
    }

    public RecyclerViewAdapter3(Context context, ArrayList<LikeList> datas) {
        this.mDatas = datas;
        this.mInflater = LayoutInflater.from(context);
    }


    // Create a new View, called by LayoutManager
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listview_item3, parent, false);
        ViewHolder vewHolder = new ViewHolder(view);
        return vewHolder;
    }

    // Operation of binding data to interface
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String hpname = mDatas.get(position).getHpname();
        String hpaddress = mDatas.get(position).getHpaddress();

        holder.hpname.setText(hpname);
        holder.hpaddress.setText(hpaddress);



    }

    // Number of data obtained
    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    // Custom ViewHolder, which holds all the interface components for each Item
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView hpname;
        public TextView hpaddress;


        public ViewHolder(View itemView) {

            super(itemView);

            hpname = (TextView) itemView.findViewById(R.id.likehpname);
            hpaddress = (TextView) itemView.findViewById(R.id.likehpaddress);


            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int position = getAdapterPosition();
                    mListener.onItemSelected(v, getAdapterPosition());

                    Log.d("test", "position = " + getAdapterPosition());
                    return false;
                }

            });


        }


    }
}