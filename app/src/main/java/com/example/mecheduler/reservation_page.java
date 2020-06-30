package com.example.mecheduler;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.OrientationHelper;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.applikeysolutions.cosmocalendar.utils.SelectionType;
import com.example.mecheduler.DTO.Constants;
import com.example.mecheduler.DTO.DoctorVO;
import com.example.mecheduler.DTO.PatientVO;
import com.example.mecheduler.DTO.Reservation_PatientVO;
import com.example.mecheduler.DTO.TeacherscheduleVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class reservation_page extends AppCompatActivity {

    private RequestQueue requestQueue;
    private String hpcode;


    private com.applikeysolutions.cosmocalendar.view.CalendarView calendarView;
    private TimePicker timePicker;
    private TextView textView, confirmDoctor, confirmDate, confirmTime, confirmSubject, confirmHp;
    private List<DoctorVO> doctorlist;
    private Button reservation_btn, reservation_go;
    private DoctorVO doctorVO;
    private Spinner spinnerDname, spinnerSubject;
    private List<String> dnames;
    private List<String> subjects;
    private List<TeacherscheduleVO> teacherscheduleVOS;
    private TeacherscheduleVO teacherscheduleVO;
    private String reservation_time, minute, hour, selectSubject, selectdate, hpname;
    public String selectDoctor, selectDoctorname;
    private PatientVO loginpatientVO;
    private View dialogView;
    private AlertDialog.Builder dlg;
    private String n_month = "", n_day = "";
    private ImageView backtohome;
    private String url, result_select,result_select2;


    private String getAmPm(int hour) {
        if (hour >= 12)
            return "오전";
        else
            return "오후";
    }

    private void setMinute(int min) {
        if (min >= 10)
            minute = min + "";
        else
            minute = "0" + min;
    }

    private void setHour(int h) {
        if (h >= 10)
            hour = h + "";
        else
            hour = "0" + h;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reservation_page);

        calendarView = (com.applikeysolutions.cosmocalendar.view.CalendarView) findViewById(R.id.select_Cal);

        calendarView.setSelectionType(SelectionType.SINGLE);
        calendarView.setCalendarOrientation(OrientationHelper.HORIZONTAL);


        calendarView.setVisibility(View.GONE);

        timePicker = findViewById(R.id.selectClock);
        backtohome = findViewById(R.id.backtohome);

        backtohome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(reservation_page.this, info_hospital.class);
                intent.putExtra("loginpatient", loginpatientVO);
                intent.putExtra("find_hpid", getIntent().getExtras().getString("reservation_hpcode"));
                intent.putExtra("page", getIntent().getExtras().getString("page"));
            }
        });
        textView = findViewById(R.id.selectDate);


        spinnerDname = (Spinner) findViewById(R.id.selectDoctor);
        spinnerSubject = (Spinner) findViewById(R.id.selectSubject);


        reservation_btn = (Button) findViewById(R.id.btn_reservation_go);

        dnames = new ArrayList<>();
        subjects = new ArrayList<>();


        dnames.add("선생님 선택");
        subjects.add("진료과목 선택");


        loginpatientVO = (PatientVO) getIntent().getParcelableExtra("loginpatient");
        hpcode = getIntent().getExtras().getString("hpcode");
        hpname = getIntent().getExtras().getString("hpname");


        url = "http://" + Constants.IP + ":8081/findalldoctorJson?hpcode=" + hpcode;
        Log.v("url", url);


        doctorlist = new ArrayList<>();
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();
        JsonObjectRequest
                jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        try {
                            JSONArray obj = response.getJSONArray("doctors");
                            for (int i = 0; i < obj.length(); i++) {

                                doctorVO = new DoctorVO();
                                JSONObject jObject = obj.getJSONObject(i);

                                doctorVO.setDcode(jObject.getString("dcode"));
                                doctorVO.setDgemder(jObject.getString("dgender"));
                                doctorVO.setDname(jObject.getString("dname"));
                                doctorVO.setSubject1(jObject.getString("subject1"));
                                doctorVO.setSubject2(jObject.getString("subject2"));

                                doctorlist.add(doctorVO);
                                dnames.add(doctorVO.getDname());
                            }

                            ArrayAdapter<String> adapter_spinner_doctor = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, dnames);
                            spinnerDname.setAdapter(adapter_spinner_doctor);
                            ArrayAdapter<String> adapter_spinner_subject = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, subjects);
                            spinnerSubject.setAdapter(adapter_spinner_subject);

                            spinnerDname.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                                    if (!dnames.get(position).equals("선생님 선택")) {
                                        subjects.clear();
                                        subjects.add(doctorlist.get(position - 1).getSubject1());
                                        subjects.add(doctorlist.get(position - 1).getSubject2());
                                        ArrayAdapter<String> adapter_spinner_subject = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, subjects);
                                        spinnerSubject.setAdapter(adapter_spinner_subject);
                                        selectDoctor = doctorlist.get(position - 1).getDcode();
                                        selectDoctorname = doctorlist.get(position - 1).getDname();
                                    }

                                }


                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }

                            });


                            teacherscheduleVOS = new ArrayList<>();
                            textView.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    if(selectDoctorname == null){
                                        Toast.makeText(getApplicationContext(),"의사선생님을 선택해주세요",Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        calendarView.setVisibility(View.VISIBLE);


                                        requestQueue = Volley.newRequestQueue(reservation_page.this);
                                        requestQueue.start();

                                        url = "http://" + Constants.IP + ":8081/readDocScheJson?dname=" + selectDoctorname;
                                        Log.v("url", url);
                                        JsonObjectRequest
                                                jsonObjectRequest2 = new JsonObjectRequest
                                                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                                    @Override
                                                    public void onResponse(JSONObject response) {
                                                        JSONArray obj = null;
                                                        try {
                                                            obj = response.getJSONArray("docschedules");

                                                            Set<Long> disabledDaysSet = new HashSet<>();


                                                            Log.v("lg", String.valueOf(response.length()));
                                                            for (int i = 0; i < obj.length(); i++) {
                                                                teacherscheduleVO = new TeacherscheduleVO();
                                                                JSONObject jObject = obj.getJSONObject(i);
                                                                teacherscheduleVO.setStartdate(jObject.getString("startdate"));
                                                                teacherscheduleVO.setEnddate(jObject.getString("enddate"));


                                                                for(int j = 0; ;j++){
                                                                    Date startdate = new Date(Long.parseLong(teacherscheduleVO.getStartdate()));
                                                                    Date enddate  = new Date(Long.parseLong(teacherscheduleVO.getEnddate()));


                                                                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");



                                                                    Calendar calendar = Calendar.getInstance();
                                                                    calendar.setTime(startdate);
                                                                    calendar.add(Calendar.DATE,j);

                                                                    Calendar calendar2 = Calendar.getInstance();
                                                                    calendar2.setTime(enddate);
                                                                    calendar2.add(Calendar.DATE,1);


                                                                    String start = format.format(calendar.getTime());
                                                                    String end = format.format(calendar2.getTime());

                                                                    if(start.equals(end)){
                                                                        break;
                                                                    }
                                                                    disabledDaysSet.add(format.parse(start).getTime());
                                                                }


                                                            }

                                                            calendarView.setDisabledDays(disabledDaysSet);


                                                        } catch (JSONException | ParseException e) {
                                                            e.printStackTrace();
                                                        }


                                                    }
                                                }, new Response.ErrorListener() {
                                                    @Override
                                                    public void onErrorResponse(VolleyError error) {
                                                        error.getStackTrace();
                                                    }
                                                });

                                        // Access the RequestQueue through your singleton class.
                                        requestQueue.add(jsonObjectRequest2);
                                    }


                                }
                            });


                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                        Toast.makeText(getApplicationContext(), "인터넷 연결이 되지 않습니다.", Toast.LENGTH_SHORT).show();

                    }
                });

        // Access the RequestQueue through your singleton class.
        requestQueue.add(jsonObjectRequest);


        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {


            @Override
            public void onTimeChanged(TimePicker timePicker, int h, int min) {
                if (h > 12)
                    h -= 12;

                setHour(h);
                setMinute(min);
                reservation_time = getAmPm(h) + hour + ":" + minute;

            }

        });

        spinnerSubject.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectSubject = subjects.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectSubject = subjects.get(1);
            }

        });


        reservation_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                List<Calendar> days = calendarView.getSelectedDates();

                result_select = "";
                result_select2 = "";
                for (int i = 0; i < days.size(); i++) {
                    Calendar calendar = days.get(i);
                    final int day = calendar.get(Calendar.DAY_OF_MONTH);
                    final int month = calendar.get(Calendar.MONTH);
                    final int year = calendar.get(Calendar.YEAR);
                    String week = new SimpleDateFormat("EE").format(calendar.getTime());
                    String day_full = year + "년 " + (month + 1) + "월 " + day + "일 " + week + "요일";
                    if(month <10 )
                        n_month = "0"+(month+1);
                    String day_full2 = year + "년" + n_month + "월" + day + "일";
                    result_select += (day_full + "\n");
                    result_select2 += (day_full2 + "\n");
                }

                if(result_select == null | selectDoctorname == null || selectSubject == null){
                    Toast.makeText(getApplicationContext(),"선택 되지 않은 정보가 있습니다.",Toast.LENGTH_SHORT).show();
                }
                else{
                    dialogView = (View) View.inflate(reservation_page.this, R.layout.dialog, null);
                    dlg = new AlertDialog.Builder(reservation_page.this);

                    reservation_go = (Button) dialogView.findViewById(R.id.reservation_go);
                    confirmDoctor = (TextView) dialogView.findViewById(R.id.confirmDoctor);
                    confirmSubject = (TextView) dialogView.findViewById(R.id.confirmSubject);
                    confirmDate = (TextView) dialogView.findViewById(R.id.confirmDate);
                    confirmTime = (TextView) dialogView.findViewById(R.id.confirmTime);
                    confirmHp = (TextView) dialogView.findViewById(R.id.confirmhp);

                    confirmTime.setText(reservation_time);


                    confirmDate.setText(result_select);
                    confirmDoctor.setText(selectDoctorname);
                    confirmSubject.setText(selectSubject);
                    confirmHp.setText(hpname);

                    dlg.setNegativeButton("취소", null);


                    dlg.setView(dialogView);
                    dlg.show();


                    reservation_go.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            requestQueue = Volley.newRequestQueue(getApplicationContext());
                            requestQueue.start();

                            String url = null;
                            try {
                                url = "http://" + Constants.IP + ":8081/reservationJson?rcode=r000004&pcode=" + 2 + "&hpcode=" + hpcode + "&dcode=" + selectDoctor + "&subject=" + URLEncoder.encode(selectSubject, "UTF-8") + "&date=" + result_select2 + "&time=" + reservation_time + "&res_who=1";
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }
                            Log.v("url", url);


                            doctorlist = new ArrayList<>();

                            JsonObjectRequest
                                    jsonObjectRequest = new JsonObjectRequest
                                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Toast.makeText(getApplicationContext(), "진료 예약을 완료 하였습니다.", Toast.LENGTH_SHORT).show();
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            intent.putExtra("loginpatient", loginpatientVO);
                                            startActivity(intent);

                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // TODO: Handle error

                                            Toast.makeText(getApplicationContext(), "인터넷 연결이 되지 않습니다.", Toast.LENGTH_SHORT).show();

                                        }
                                    });

                            // Access the RequestQueue through your singleton class.
                            requestQueue.add(jsonObjectRequest);

                        }
                    });

                }


            }
        });

    }

}
