package com.example.mecheduler;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private ArrayList<CustomDTO> listCustom = new ArrayList<>();

    // ListView에 보여질 Item 수
    @Override
    public int getCount() {
        return listCustom.size();
    }

    // 하나의 Item(ImageView 1, TextView 2)
    @Override
    public Object getItem(int position) {
        return listCustom.get(position);
    }

    // Item의 id : Item을 구별하기 위한 것으로 position 사용
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 실제로 Item이 보여지는 부분
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CustomViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, null, false);

            holder = new CustomViewHolder();
            holder.textContent1 = (TextView) convertView.findViewById(R.id.hp_name_text);
            holder.textTitle = (TextView) convertView.findViewById(R.id.hp_addr_text);
            holder.textContent3 = (TextView) convertView.findViewById(R.id.hp_Mapimg_text);
            holder.textContent2 = (TextView) convertView.findViewById(R.id.hp_tel_text);
            holder.textContent4 = (TextView) convertView.findViewById(R.id.hpid);

            convertView.setTag(holder);
        } else {
            holder = (CustomViewHolder) convertView.getTag();
        }

        CustomDTO dto = listCustom.get(position);


        holder.textTitle.setText(dto.getHpName());
        holder.textContent1.setText(dto.getHpAddr());
        holder.textContent2.setText(dto.getHpTel1());
        holder.textContent3.setText(dto.getHpMapImg());
        holder.textContent4.setText(dto.getHpid());

        return convertView;
    }

    class CustomViewHolder {
        TextView textTitle;
        TextView textContent1;
        TextView textContent2;
        TextView textContent3;
        TextView textContent4;
    }

    // MainActivity에서 Adapter에있는 ArrayList에 data를 추가시켜주는 함수
    public void addItem(CustomDTO dto) {
        listCustom.add(dto);
    }
}