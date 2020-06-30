package com.example.mecheduler;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mecheduler.DTO.Reservation_PatientVO;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter  {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    private ArrayList<Reservation_PatientVO> listViewItemList = new ArrayList<Reservation_PatientVO>() ;

    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size() ;
    }

    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, parent, false);
        }

        // 화면에 표시될 View(Layout이 inflate된)으로부터 위젯에 대한 참조 획득
        TextView titleTextView = (TextView) convertView.findViewById(R.id.myreservationhp) ;
        TextView tv2 = (TextView) convertView.findViewById(R.id.myreservationdoctor) ;
        TextView tv3 = (TextView) convertView.findViewById(R.id.myreservationdate) ;
        TextView tv4 = (TextView) convertView.findViewById(R.id.myreservationtime) ;
        TextView tv5 = (TextView) convertView.findViewById(R.id.myreservationsubject) ;

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        Reservation_PatientVO listViewItem = listViewItemList.get(position);

        // 아이템 내 각 위젯에 데이터 반영
        titleTextView.setText(listViewItem.getHpname());
        tv2.setText(listViewItem.getDname()+" 선생님");
        tv3.setText(listViewItem.getDate());
        tv4.setText(listViewItem.getTime());
        tv5.setText(listViewItem.getSubject());


        return convertView;
    }

    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position ;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(position) ;
    }

    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem( String hpcode, String dcode,String date,String time, String subject) {
        Reservation_PatientVO item = new Reservation_PatientVO();

        item.setHpname(hpcode);
        item.setDname(dcode);
        item.setSubject(subject);
        item.setTime(time);
        item.setDate(date);

        listViewItemList.add(item);
    }

}