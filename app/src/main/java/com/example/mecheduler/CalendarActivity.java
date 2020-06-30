package com.example.mecheduler;

import android.os.Bundle;
import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.RadioGroup;
import android.widget.Toast;


import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class CalendarActivity extends AppCompatActivity {

    private CalendarView calendarView;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar);

        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        initViews();
    }

    private void initViews() {
        calendarView = (CalendarView) findViewById(R.id.calendar_view);

        calendarView.setSelectionType(SelectionType.SINGLE);
        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);
        Set<Long> disabledDaysSet = new HashSet<>();

        Date date = new Date(Long.parseLong("1592564400000"));
        Date date1 = new Date();

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");



        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date1);
        calendar.add(Calendar.DATE,1);


        String start = format.format(calendar.getTime());
        String end = format.format(date1);




        Log.v("start",start+"");
        Log.v("end",end+"");




        disabledDaysSet.add(System.currentTimeMillis());
        calendarView.setDisabledDays(disabledDaysSet);

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.clear_selections:
                clearSelectionsMenuClick();
                return true;

            case R.id.show_selections:
                List<Calendar> days = calendarView.getSelectedDates();

                String result="";
                for( int i=0; i<days.size(); i++)
                {
                    Calendar calendar = days.get(i);
                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                    final int month = calendar.get(Calendar.MONTH);
                    final int year = calendar.get(Calendar.YEAR);
                    String week = new SimpleDateFormat("EE").format(calendar.getTime());
                    String day_full = year + "년 "+ (month+1)  + "월 " + day + "일 " + week + "요일";
                    result += (day_full + "\n");
                }
                Toast.makeText(CalendarActivity.this, result, Toast.LENGTH_LONG).show();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }




    private void clearSelectionsMenuClick() {
        calendarView.clearSelections();

    }

//
//    @Override
//    public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
//        clearSelectionsMenuClick();
//        switch (checkedId) {
//
//            case R.id.rb_single:
//                calendarView.setSelectionType(SelectionType.SINGLE);
//                break;
//
//            case R.id.rb_multiple:
//                calendarView.setSelectionType(SelectionType.MULTIPLE);
//                break;
//
//            case R.id.rb_range:
//                calendarView.setSelectionType(SelectionType.RANGE);
//                break;
//
//            case R.id.rb_none:
//                calendarView.setSelectionType(SelectionType.NONE);
//                break;
//        }
//    }
}
