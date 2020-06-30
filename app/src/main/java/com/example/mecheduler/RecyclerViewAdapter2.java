package com.example.mecheduler;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.mecheduler.DTO.Reservation_PatientVO;

import java.util.ArrayList;


/**
 * @Founder Xinji
 * @Describe Android Zero Foundation Introduction to Proficiency Series
 * Share Expert
 */
public class RecyclerViewAdapter2 extends RecyclerView.Adapter<RecyclerViewAdapter2.ViewHolder> {
    private ArrayList<Reservation_PatientVO> mDatas = null;
    private LayoutInflater mInflater = null;

    public interface OnListItemLongSelectedInterface {
        void onItemLongSelected(View v, int position);
    }

    public interface OnListItemSelectedInterface {
        void onItemSelected(View v, int position);
    }

    private OnListItemSelectedInterface mListener;
    private OnListItemLongSelectedInterface mLongListener;


    public RecyclerViewAdapter2(Context context, ArrayList<Reservation_PatientVO> datas, OnListItemSelectedInterface listener) {
        this.mDatas = datas;
        this.mListener = listener;
        this.mInflater = LayoutInflater.from(context);
    }

    public RecyclerViewAdapter2(Context context, ArrayList<Reservation_PatientVO> datas) {
        this.mDatas = datas;
        this.mInflater = LayoutInflater.from(context);
    }


    // Create a new View, called by LayoutManager
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listview_item2, parent, false);
        ViewHolder vewHolder = new ViewHolder(view);
        return vewHolder;
    }

    // Operation of binding data to interface
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String date = mDatas.get(position).getDate();
        String time = mDatas.get(position).getTime();
        String doctor = mDatas.get(position).getDname();
        String subject = mDatas.get(position).getSubject();
        String hp = mDatas.get(position).getHpname();
        String state = mDatas.get(position).getRes_state();
        String rcode = mDatas.get(position).getRcode();
        String who = null;
        int color =0;
        if (mDatas.get(position).getRes_who() == 1)
            who = "환자예약";
        else if (mDatas.get(position).getRes_who() != 1)
            who = "병원예약";




        holder.date.setText(date);
        holder.time.setText(time);
        holder.doctor.setText(doctor + "선생님");
        holder.subject.setText(subject);
        holder.hospital.setText(hp);
        holder.res_who.setText(who);
        holder.res_state.setText(state);
        holder.rcode.setText(rcode);
        Log.v("sdf", "succccccc");


    }

    // Number of data obtained
    @Override
    public int getItemCount() {
        return mDatas == null ? 0 : mDatas.size();
    }

    // Custom ViewHolder, which holds all the interface components for each Item
    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView date;
        public TextView time;
        public TextView doctor;
        public TextView subject;
        public TextView hospital;
        public TextView res_who;
        public TextView res_state;
        public TextView res_cancletv;
        public TextView rcode;


        public ViewHolder(View itemView) {

            super(itemView);

            date = (TextView) itemView.findViewById(R.id.myreservationdate);
            time = (TextView) itemView.findViewById(R.id.myreservationtime);
            doctor = (TextView) itemView.findViewById(R.id.myreservationdoctor);
            subject = (TextView) itemView.findViewById(R.id.myreservationsubject);
            hospital = (TextView) itemView.findViewById(R.id.myreservationhp);
            res_who = (TextView) itemView.findViewById(R.id.res_who);
            res_cancletv = (TextView) itemView.findViewById(R.id.rescancletv);
            res_state = (TextView) itemView.findViewById(R.id.res_state);
            rcode = (TextView) itemView.findViewById(R.id.recode);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    mListener.onItemSelected(v, getAdapterPosition());

                    Log.d("test", "position = " + getAdapterPosition());
                }

            });


        }


    }
}