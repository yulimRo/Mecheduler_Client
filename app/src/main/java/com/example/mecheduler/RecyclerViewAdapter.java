package com.example.mecheduler;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mecheduler.DTO.Reservation_PatientVO;

import java.util.ArrayList;


/**
 * @Founder Xinji
 * @Describe Android Zero Foundation Introduction to Proficiency Series
 * Share Expert
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
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


    public RecyclerViewAdapter(Context context, ArrayList<Reservation_PatientVO> datas, OnListItemSelectedInterface listener) {
        this.mDatas = datas;
        this.mListener = listener;
        this.mInflater = LayoutInflater.from(context);
    }

    public RecyclerViewAdapter(Context context, ArrayList<Reservation_PatientVO> datas) {
        this.mDatas = datas;
        this.mInflater = LayoutInflater.from(context);
    }


    // Create a new View, called by LayoutManager
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.listview_item, parent, false);
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


        if (state.equals("예약확인")) {

            holder.stateimg.setImageResource(R.drawable.res_confirm);
            holder.statename.setText(state);
            color = holder.itemView.getResources().getColor(R.color.sky_color);
            holder.statename.setTextColor(color);

        } else if (state.equals("예약취소")) {
            holder.stateimg.setImageResource(R.drawable.res_cancle);
            color = holder.itemView.getResources().getColor(R.color.red_color);
            holder.statename.setTextColor(color);
            holder.statename.setText(state);
        } else if (state.equals("진료완료")) {
            holder.stateimg.setImageResource(R.drawable.res_comp);
            color = holder.itemView.getResources().getColor(R.color.gray_color);
            holder.statename.setTextColor(color);
            holder.statename.setText(state);
            holder.res_cancletv.setVisibility(View.GONE);
        }
        else if (state.equals("예약접수")) {
            holder.stateimg.setImageResource(R.drawable.res_add);
            color = holder.itemView.getResources().getColor(R.color.green_color);
            holder.statename.setTextColor(color);
            holder.statename.setText(state);
        }

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
        public TextView statename;
        public ImageView stateimg;
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
            statename = (TextView) itemView.findViewById(R.id.statename);
            rcode = (TextView) itemView.findViewById(R.id.recode);
            stateimg = (ImageView) itemView.findViewById(R.id.stateimg);


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