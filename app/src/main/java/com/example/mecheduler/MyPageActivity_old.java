package com.example.mecheduler;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mecheduler.DTO.MedicineVO;
import com.example.mecheduler.DTO.PatientVO;
import com.example.mecheduler.DTO.ReservationVO;
import com.example.mecheduler.DTO.Reservation_PatientVO;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class MyPageActivity_old extends AppCompatActivity implements RecyclerViewAdapter.OnListItemSelectedInterface, RecyclerViewAdapter2.OnListItemSelectedInterface, RecyclerViewAdapter3.OnListItemSelectedInterface {


    private Toolbar toolbar;
    private ActionBar actionBar;

    private TabHost tabHost;
    private TabHost.TabSpec ts1, ts2, ts3, ts4;

    private View view;

    private Button btn_status, btn_cancel;
    private ImageButton imgBtn_guide1, imgBtn_guide2;

    private RequestQueue requestQueue;
    private RequestQueue requestQueue1;
    private RequestQueue requestQueue3;

    static int i = 0, color = 0;

    private ArrayList<String> mGroupList = null;
    private ArrayList<ArrayList<String>> mChildList = null;
    private ArrayList<String> mChildListContent = null;
    private PatientVO patientVO;
    private ReservationVO reservationVO;
    private List<ReservationVO> reservationVOList = new ArrayList<>();
    private List<Reservation_PatientVO> patientVOList = new ArrayList<>();
    private List<MedicineVO> medicineVOS = new ArrayList<>();


    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private RecyclerView mRecyclerView3;
    private RecyclerViewAdapter mAdapter;
    private RecyclerViewAdapter2 mAdapter2;
    private RecyclerViewAdapter3 mAdapter3;
    private View dialogView, dialogView2;
    private AlertDialog.Builder dlg;
    private List<String> doctor;
    private List<String> hospital;
    private List<String> subject;
    private List<String> time;
    private List<String> date;
    private List<String> rcode;
    private List<String> pcode;
    private List<Integer> res_who;
    private List<String> res_state;
    private View child1;
    private TextView tb_name;
    private TextView tb_num ;
    private TextView tb_oneday ;
    private Button tb_medinfo ;
    private List<String> medhpname;
    private List<String> medpname;
    private List<Integer> medoneday;
    private List<Integer> medforday;
    private List<Integer> mednum;
    private List<String> medrcode;
    private List<String> medname;
    private List<String> medinfo1;
    private List<String> medinfo2;
    private List<String> meddanger;
    private List<String> medeattime;
    private List<String> medsectime;
    private List<String> meddate;
    private List<String> likehpcode;
    private List<String> likehpnamelist;
    private List<String> likeaddresslist;


    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManager2;
    private LinearLayoutManager layoutManager3;
    private URL url_u;
    XmlPullParserFactory parserCreator;
    XmlPullParser parser;
    int parserEvent;
    DocumentBuilderFactory dbFactoty;
    DocumentBuilder dBuilder;
    Document doc;
    NodeList list;
    Element element;
    private int count =0;
//
//    private SearchThread3 thread3;
//    boolean initem = false, inDutyAddr = false, inDutyDiv = false, inDutyDivNam = false, inDutyEmcls = false;
//    boolean inDutyEmclsNam = false, inDutyEryn = false, inDutyEtc = false, inDutyInf = false, inDutyMapimg = false;
//    boolean inDutyName = false, inDutyTel1 = false, inDutyTel3 = false, inHpid = false, inPostCdn1 = false, inPostCdn2 = false;
//    boolean inWgs84Lon = false, inWgs84Lat = false, inDgidIdName = false;
//
//    String addr = null, dutyAddr = null, dutyDiv = null, dutyDivNam = null, dutyEmcls = null, dutyEmclsNam = null, dutyEryn = null, dutyEtc = null;
//    String dutyInf = null, dutyMapimg = null, dutyName = null, dutyTel1 = null, dutyTel3 = null, hpid = null, postCdn1 = null, postCdn2 = null;
//    String wgs84Lon = null, wgs84Lat = null, dgidIdName = null;
//
//
//    private TextView confrimhp, confrimDoctor, confirmSubject, confirmDate, confrimTime, confirmwho, confirmState, res_canclebtn;
//    private TextView med_hpname, med_pname, med_oneday, med_forday, med_eattime, med_sectime, med_date;
//
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage);
        Intent intent = getIntent();
//
//        //patientVO = intent.getParcelableExtra("loginpatient");
          i = intent.getExtras().getInt("count");
//        i = 2;
//
//
        // 액션바 //
        {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
//
//
        // 탭 //
        {
            //탭1
            {
//                tabHost = (TabHost) findViewById(R.id.tabhost);
//                tabHost.setup();
//
//                // 진료 현황 //
//                ts1 = tabHost.newTabSpec("tab1").setIndicator("진료 현황");
//
//                if (i == 0) {
//                    findViewById(R.id.tab_status1).setVisibility(View.VISIBLE);
//                    findViewById(R.id.tab_status2).setVisibility(View.INVISIBLE);
//                    findViewById(R.id.tab_status3).setVisibility(View.INVISIBLE);
//
//
//                } else {
//
//                    findViewById(R.id.tab_status1).setVisibility(View.INVISIBLE);
//                    findViewById(R.id.tab_status2).setVisibility(View.VISIBLE);
//                    findViewById(R.id.tab_status3).setVisibility(View.INVISIBLE);
//                }
//
//                ts1.setContent(R.id.tab1);
//                tabHost.addTab(ts1);
//
//
//                view = (View) findViewById(R.id.tab_status2);
//
//
//                requestQueue = Volley.newRequestQueue(this);
//                requestQueue.start();
//                String url = "http://192.168.35.251:8081/myreservationJson?pcode=2"; //patientVO.getPcode();
//                Log.v("res", url);
//
//                mRecyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
//
//
//                JsonObjectRequest
//                        jsonObjectRequest = new JsonObjectRequest
//                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                            @Override
//                            public void onResponse(JSONObject response) {
//
//                                try {
//
//                                    // Setup Manager
//                                    layoutManager = new LinearLayoutManager(MyPageActivity_old.this);
//                                    mRecyclerView.setLayoutManager(layoutManager);
//                                    // If you can determine that the height of each item is fixed, setting this option can improve performance.
//                                    mRecyclerView.setHasFixedSize(true);
//
//                                    rcode = new ArrayList<>();
//                                    doctor = new ArrayList<>();
//                                    hospital = new ArrayList<>();
//                                    subject = new ArrayList<>();
//                                    time = new ArrayList<>();
//                                    date = new ArrayList<>();
//                                    pcode = new ArrayList<>();
//                                    res_who = new ArrayList<Integer>();
//                                    res_state = new ArrayList<>();
//
//                                    JSONArray obj = response.getJSONArray("reservations");
//                                    Log.v("lg", String.valueOf(response.length()));
//                                    for (int i = 0; i < obj.length(); i++) {
//                                        JSONObject jObject = obj.getJSONObject(i);
//                                        rcode.add(jObject.getString("rcode"));
//                                        doctor.add(jObject.getString("dname"));
//                                        hospital.add(jObject.getString("hpname"));
//                                        pcode.add(jObject.getString("pcode"));
//                                        date.add(jObject.getString("date"));
//                                        time.add(jObject.getString("time"));
//                                        subject.add(jObject.getString("subject"));
//                                        res_who.add(jObject.getInt("res_who"));
//                                        res_state.add(jObject.getString("res_state"));
//
//                                    }
//
//
//                                    for (int i = 0; i < hospital.size(); i++) {
//                                        Reservation_PatientVO dto = new Reservation_PatientVO();
//                                        dto.setHpname(hospital.get(i));
//                                        dto.setDname(doctor.get(i));
//                                        dto.setDate(date.get(i));
//                                        dto.setTime(time.get(i));
//                                        dto.setSubject(subject.get(i));
//                                        dto.setRcode(rcode.get(i));
//                                        dto.setPcode(Integer.parseInt(pcode.get(i)));
//                                        dto.setRes_who(res_who.get(i));
//                                        dto.setRes_state(res_state.get(i));
//
//                                        patientVOList.add(i, dto);
//
//
//                                    }
//
//                                    // Setting up the adapter
//                                    mAdapter = new RecyclerViewAdapter(MyPageActivity_old.this, (ArrayList<Reservation_PatientVO>) patientVOList, MyPageActivity_old.this);
//                                    mRecyclerView.setAdapter(mAdapter);
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }, new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // TODO: Handle error
//
//                                Toast.makeText(getApplicationContext(), "인터넷 연결이 되지 않습니다.", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//
//
//                // Access the RequestQueue through your singleton class.
//                requestQueue.add(jsonObjectRequest);
//            }
//
//            //탭2
//            {
//                ts2 = tabHost.newTabSpec("tab2").setIndicator("복용 안내");
//                ts2.setContent(R.id.tab2);
//                tabHost.addTab(ts2);
//
//                view = (View) findViewById(R.id.tab_guide);
//                requestQueue1 = Volley.newRequestQueue(this);
//                requestQueue1.start();
//                String url = "http://192.168.35.251:8081/myreservationJson?pcode=2"; //patientVO.getPcode();
//                Log.v("res", url);
//
//                mRecyclerView2 = (RecyclerView) findViewById(R.id.my_recycler_view2);
//
//                JsonObjectRequest
//                        jsonObjectRequest2 = new JsonObjectRequest
//                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                            @Override
//                            public void onResponse(JSONObject response) {
//
//                                try {
//
//                                    patientVOList.clear();
//                                    // Setup Manager
//                                    layoutManager2 = new LinearLayoutManager(MyPageActivity_old.this);
//                                    mRecyclerView2.setLayoutManager(layoutManager2);
//                                    // If you can determine that the height of each item is fixed, setting this option can improve performance.
//                                    mRecyclerView2.setHasFixedSize(true);
//
//                                    rcode = new ArrayList<>();
//                                    doctor = new ArrayList<>();
//                                    hospital = new ArrayList<>();
//                                    subject = new ArrayList<>();
//                                    time = new ArrayList<>();
//                                    date = new ArrayList<>();
//                                    pcode = new ArrayList<>();
//                                    res_who = new ArrayList<Integer>();
//                                    res_state = new ArrayList<>();
//
//                                    JSONArray obj = response.getJSONArray("reservations");
//                                    Log.v("lg", String.valueOf(response.length()));
//                                    for (int i = 0; i < obj.length(); i++) {
//                                        JSONObject jObject = obj.getJSONObject(i);
//                                        rcode.add(jObject.getString("rcode"));
//                                        doctor.add(jObject.getString("dname"));
//                                        hospital.add(jObject.getString("hpname"));
//                                        pcode.add(jObject.getString("pcode"));
//                                        date.add(jObject.getString("date"));
//                                        time.add(jObject.getString("time"));
//                                        subject.add(jObject.getString("subject"));
//                                        res_who.add(jObject.getInt("res_who"));
//                                        res_state.add(jObject.getString("res_state"));
//
//                                    }
//
//
//                                    for (int i = 0; i < hospital.size(); i++) {
//                                        Reservation_PatientVO dto = new Reservation_PatientVO();
//                                        dto.setHpname(hospital.get(i));
//                                        dto.setDname(doctor.get(i));
//                                        dto.setDate(date.get(i));
//                                        dto.setTime(time.get(i));
//                                        dto.setSubject(subject.get(i));
//                                        dto.setRcode(rcode.get(i));
//                                        dto.setPcode(Integer.parseInt(pcode.get(i)));
//                                        dto.setRes_who(res_who.get(i));
//                                        dto.setRes_state(res_state.get(i));
//
//                                        patientVOList.add(i, dto);
//                                    }
//
//
//                                    Toast.makeText(getApplicationContext(), String.valueOf(patientVOList.size()), Toast.LENGTH_SHORT).show();
//                                    // Setting up the adapter
//                                    mAdapter2 = new RecyclerViewAdapter2(MyPageActivity_old.this, (ArrayList<Reservation_PatientVO>) patientVOList, new RecyclerViewAdapter2.OnListItemSelectedInterface() {
//                                        @Override
//                                        public void onItemSelected(View v, final int position) {
//                                            final RecyclerViewAdapter.ViewHolder viewHolder = (RecyclerViewAdapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
//                                            dialogView = (View) View.inflate(MyPageActivity_old.this, R.layout.tab_guide_detail, null);
//                                            requestQueue3 = Volley.newRequestQueue(MyPageActivity_old.this);
//                                            requestQueue3.start();
//
//
//                                            dlg = new AlertDialog.Builder(MyPageActivity_old.this);
//                                            dlg.setPositiveButton("확인", null);
//                                            String url = "http://192.168.35.251:8081/readMedJson?rcode=" + rcode.get(position);
//                                            Log.v("url", url);
//                                            JsonObjectRequest
//                                                    jsonObjectRequest3 = new JsonObjectRequest
//                                                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                                                        @Override
//                                                        public void onResponse(JSONObject response) {
//                                                            try {
//
//                                                                medhpname = new ArrayList<>();
//                                                                medrcode = new ArrayList<>();
//                                                                medpname = new ArrayList<>();
//                                                                medname = new ArrayList<>();
//                                                                mednum = new ArrayList<>();
//                                                                medeattime = new ArrayList<>();
//                                                                medsectime = new ArrayList<>();
//                                                                meddanger = new ArrayList<>();
//                                                                medinfo1 = new ArrayList<>();
//                                                                medinfo2 = new ArrayList<>();
//                                                                meddate = new ArrayList<>();
//                                                                medforday = new ArrayList<>();
//                                                                medoneday = new ArrayList<>();
//
//                                                                JSONArray obj = response.getJSONArray("medicine");
//                                                                Log.v("medicinelist", String.valueOf(obj.length()));
//                                                                for (int i = 0; i < obj.length(); i++) {
//                                                                    JSONObject jObject = obj.getJSONObject(i);
//                                                                    medrcode.add(jObject.getString("rcode"));
//                                                                    medpname.add(jObject.getString("pname"));
//                                                                    medhpname.add(jObject.getString("hpname"));
//                                                                    medname.add(jObject.getString("mname"));
//                                                                    medinfo1.add(jObject.getString("info1"));
//                                                                    medinfo2.add(jObject.getString("info2"));
//                                                                    medsectime.add(jObject.getString("sectime"));
//                                                                    medeattime.add(jObject.getString("eattime"));
//                                                                    meddanger.add(jObject.getString("danger"));
//                                                                    meddate.add(jObject.getString("replyDate"));
//                                                                    mednum.add(jObject.getInt("num"));
//                                                                    medforday.add(jObject.getInt("forday"));
//                                                                    medoneday.add(jObject.getInt("oneday"));
//                                                                }
//
////                                                                for (int i = 0; i < obj.length(); i++) {
////                                                                    MedicineVO medicineVO = new MedicineVO();
////                                                                    medicineVO.setRcode(medrcode.get(i));
////                                                                    medicineVO.setPname(medpname.get(i));
////                                                                    medicineVO.setHpname(medhpname.get(i));
////                                                                    medicineVO.setMname(medname.get(i));
////                                                                    medicineVO.setInfo1(medinfo1.get(i));
////                                                                    medicineVO.setInfo2(medinfo2.get(i));
////                                                                    medicineVO.setDanger(meddanger.get(i));
////                                                                    medicineVO.setReplyDate(meddate.get(i));
////                                                                    medicineVO.setNum(mednum.get(i));
////                                                                    medicineVO.setForday(medforday.get(i));
////                                                                    medicineVO.setOneday(medoneday.get(i));
////
////                                                                    medicineVOS.add(i,medicineVO);
////                                                                }
////
////// Setting up the adapter
////                                                                mAdapter3 = new RecyclerViewAdapter(MyPageActivity.this, (ArrayList<Reservation_PatientVO>) patientVOList, MyPageActivity.this);
////                                                                mRecyclerView.setAdapter(mAdapter);
//
//                                                                med_hpname = (TextView) dialogView.findViewById(R.id.medhp);
//                                                                med_pname = (TextView) dialogView.findViewById(R.id.medpname);
//                                                                med_oneday = (TextView) dialogView.findViewById(R.id.oneday);
//                                                                med_forday = (TextView) dialogView.findViewById(R.id.forday);
//                                                                med_date = (TextView) dialogView.findViewById(R.id.meddate);
//                                                                med_hpname.setText(medhpname.get(position));
//                                                                med_pname.setText(medpname.get(position));
//                                                                med_forday.setText(medforday.get(position).toString());
//                                                                med_oneday.setText(medoneday.get(position).toString());
//                                                                med_date.setText(meddate.get(position).subSequence(0, 11));
//
//
//
//                                                                TableLayout tb = dialogView.findViewById(R.id.tl);
//
//                                                                child1 = LayoutInflater.from(MyPageActivity_old.this).inflate(R.layout.tablelayout, null);
//
//
//                                                                tb_name = child1.findViewById(R.id.medname);
//                                                                tb_num = child1.findViewById(R.id.mednum);
//                                                                tb_oneday = child1.findViewById(R.id.medoneday);
//                                                                tb_medinfo = child1.findViewById(R.id.medinfo);
//
//
//                                                                tb_name.setText("약이름");
//                                                                tb_num.setText("투여량");
//                                                                tb_oneday.setText("투여횟수");
//                                                                tb.addView(child1);
//                                                                for ( i = 0; i < medoneday.size(); i++) {
//                                                                    child1 = LayoutInflater.from(MyPageActivity_old.this).inflate(R.layout.tablelayout, null);
//
//
//                                                                    tb_name = child1.findViewById(R.id.medname);
//                                                                    tb_num = child1.findViewById(R.id.mednum);
//                                                                    tb_oneday = child1.findViewById(R.id.medoneday);
//                                                                    tb_medinfo = child1.findViewById(R.id.medinfo);
//
//                                                                    tb_name.setText(medname.get(i));
//                                                                    tb_num.setText(mednum.get(i).toString());
//                                                                    tb_oneday.setText(medoneday.get(i).toString());
//                                                                    tb_medinfo.setVisibility(View.VISIBLE);
//                                                                    tb_medinfo.setText("약정보"+(i+1));
//
//                                                                    tb.addView(child1);
//
////                                                                    tb_medinfo.setOnClickListener(new View.OnClickListener() {
////                                                                        @Override
////                                                                        public void onClick(View v) {
////                                                                            LinearLayout ll = dialogView.findViewById(R.id.medll);
////                                                                            ll.setVisibility(View.VISIBLE);
////                                                                            int num = Integer.parseInt(tb_medinfo.getText().toString().substring(3).toString());
////                                                                            TextView tv = dialogView.findViewById(R.id.infoname);
////                                                                            tv.setText(medname.get(num-1));
////
////
////                                                                        }
////                                                                    });
//                                                                }
//
//
//
//                                                            } catch (JSONException e) {
//                                                                e.printStackTrace();
//                                                                Toast.makeText(getApplicationContext(), "존재하지 않은 아이디 또는 비밀번호입니다.", Toast.LENGTH_SHORT).show();
//                                                            }
//
//
//                                                        }
//                                                    }, new Response.ErrorListener() {
//                                                        @Override
//                                                        public void onErrorResponse(VolleyError error) {
//                                                            // TODO: Handle error
//                                                            error.getMessage();
//                                                            Toast.makeText(getApplicationContext(), "인터넷 연결이 되지 않습니다.", Toast.LENGTH_SHORT).show();
//
//                                                        }
//                                                    });
//
//                                            // Access the RequestQueue through your singleton class.
//                                            requestQueue3.add(jsonObjectRequest3);
//
//                                            dlg.setView(dialogView);
//                                            dlg.show();
//
//                                        }
//                                    });
//                                    mRecyclerView2.setAdapter(mAdapter2);
//
//
//                                } catch (JSONException e) {
//                                    e.printStackTrace();
//                                }
//
//                            }
//                        }, new Response.ErrorListener() {
//
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                // TODO: Handle error
//
//                                Toast.makeText(getApplicationContext(), "인터넷 연결이 되지 않습니다.", Toast.LENGTH_SHORT).show();
//
//                            }
//                        });
//
//                // Access the RequestQueue through your singleton class.
//                requestQueue1.add(jsonObjectRequest2);
//
//            }
//            //탭3
//            {
//
//                ts3 = tabHost.newTabSpec("tab3").setIndicator("관심 병원");
//                ts3.setContent(R.id.tab3);
//                tabHost.addTab(ts3);
//
//                view = (View) findViewById(R.id.tab_my);
//
//
//            }
//
//            //탭4
//            {
//                ts4 = tabHost.newTabSpec("tab4").setIndicator("자가 진단");
//                ts4.setContent(R.id.tab4);
//                tabHost.addTab(ts4);
//            }
//
//            tabHost.setCurrentTab(0);
        }
//
    }
//
//    //액션버튼을 클릭했을 때
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        switch (id) {
//            case android.R.id.home:
//                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//                intent.putExtra("loginpatient", patientVO);
//                startActivityForResult(intent, 101);
//                finish();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
//
//    //탭1 예약 클릭
//    public void onClick_reservation(View v) {
//        findViewById(R.id.tab_status1).setVisibility(View.GONE);
//        findViewById(R.id.tab_status2).setVisibility(View.VISIBLE);
//        findViewById(R.id.tab_status3).setVisibility(View.VISIBLE);
//    }
//
//    //탭3 병원 클릭
//    public void onClick_hospital(View v) {
//        Intent intent = new Intent(getApplicationContext(), info_hospital.class);
//        startActivity(intent);
//    }
//
//    //탭3 약국 클릭
//    public void onClick_pharmacy(View v) {
//        Intent intent = new Intent(getApplicationContext(), info_pharmacy.class);
//        startActivity(intent);
//    }
//
//
//    @Override
//    public void onItemSelected(View v, int position) {
//        final RecyclerViewAdapter.ViewHolder viewHolder = (RecyclerViewAdapter.ViewHolder) mRecyclerView.findViewHolderForAdapterPosition(position);
//        dialogView = (View) View.inflate(this, R.layout.dialog2, null);
//
//
//        confrimhp = (TextView) dialogView.findViewById(R.id.confirmhp);
//        confirmDate = (TextView) dialogView.findViewById(R.id.confirmDate);
//        confrimTime = (TextView) dialogView.findViewById(R.id.confirmTime);
//        confirmSubject = (TextView) dialogView.findViewById(R.id.confirmSubject);
//        confrimDoctor = (TextView) dialogView.findViewById(R.id.confirmDoctor);
//        confirmwho = (TextView) dialogView.findViewById(R.id.sub_who);
//        confirmState = (TextView) dialogView.findViewById(R.id.confirmStateTv);
//
//
//        confirmSubject.setText(viewHolder.subject.getText().toString());
//        confirmDate.setText(viewHolder.date.getText().toString());
//        confrimTime.setText(viewHolder.time.getText().toString());
//        confrimDoctor.setText(viewHolder.doctor.getText().toString());
//        confrimhp.setText(viewHolder.hospital.getText().toString());
//        confirmwho.setText(viewHolder.res_who.getText().toString());
//
//
//        if (viewHolder.res_state.getText().toString().equals("예약확인")) {
//            color = getResources().getColor(R.color.sky_color);
//            confirmState.setTextColor(color);
//            confirmState.setText(viewHolder.res_state.getText().toString());
//        } else if (viewHolder.res_state.getText().toString().equals("예약취소")) {
//            color = getResources().getColor(R.color.red_color);
//            confirmState.setTextColor(color);
//            confirmState.setText(viewHolder.res_state.getText().toString());
//        } else if (viewHolder.res_state.getText().toString().equals("진료완료")) {
//            color = getResources().getColor(R.color.gray_color);
//            confirmState.setTextColor(color);
//            confirmState.setText(viewHolder.res_state.getText().toString());
//        } else if (viewHolder.res_state.getText().toString().equals("예약접수")) {
//            color = getResources().getColor(R.color.green_color);
//            confirmState.setTextColor(color);
//            confirmState.setText(viewHolder.res_state.getText().toString());
//        }
//
//
//        dlg = new AlertDialog.Builder(this);
//
//
//        dlg.setPositiveButton("확인", null);
//
//        dlg.setView(dialogView);
//        dlg.show();
//
//
//        viewHolder.res_cancletv.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogView2 = (View) View.inflate(MyPageActivity_old.this, R.layout.dialog3, null);
//
//                confrimhp = (TextView) dialogView2.findViewById(R.id.confirmhp);
//                confirmDate = (TextView) dialogView2.findViewById(R.id.confirmDate);
//                confrimTime = (TextView) dialogView2.findViewById(R.id.confirmTime);
//                confirmSubject = (TextView) dialogView2.findViewById(R.id.confirmSubject);
//                confrimDoctor = (TextView) dialogView2.findViewById(R.id.confirmDoctor);
//                confirmwho = (TextView) dialogView2.findViewById(R.id.sub_who);
//                confirmState = (TextView) dialogView2.findViewById(R.id.confirmStateTv);
//
//                confirmSubject.setText(viewHolder.subject.getText().toString());
//                confirmDate.setText(viewHolder.date.getText().toString());
//                confrimTime.setText(viewHolder.time.getText().toString());
//                confrimDoctor.setText(viewHolder.doctor.getText().toString());
//                confrimhp.setText(viewHolder.hospital.getText().toString());
//                confirmwho.setText(viewHolder.res_who.getText().toString());
//
//                res_canclebtn = (Button) dialogView2.findViewById(R.id.rescanclebtn);
//
//
//                if (viewHolder.res_state.getText().toString().equals("예약확인")) {
//                    color = getResources().getColor(R.color.sky_color);
//                    confirmState.setTextColor(color);
//                    confirmState.setText(viewHolder.res_state.getText().toString());
//                } else if (viewHolder.res_state.getText().toString().equals("예약취소")) {
//                    color = getResources().getColor(R.color.red_color);
//                    confirmState.setTextColor(color);
//                    confirmState.setText(viewHolder.res_state.getText().toString());
//                } else if (viewHolder.res_state.getText().toString().equals("진료완료")) {
//                    color = getResources().getColor(R.color.gray_color);
//                    confirmState.setTextColor(color);
//                    confirmState.setText(viewHolder.res_state.getText().toString());
//                } else if (viewHolder.res_state.getText().toString().equals("예약접수")) {
//                    color = getResources().getColor(R.color.green_color);
//                    confirmState.setTextColor(color);
//                    confirmState.setText(viewHolder.res_state.getText().toString());
//                }
//
//
//                res_canclebtn.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        requestQueue = Volley.newRequestQueue(MyPageActivity_old.this);
//                        requestQueue.start();
//
//                        String url = "http://192.168.35.251:8081/deleteresJson?rcode=" + viewHolder.rcode.getText().toString();
//
//                        JsonObjectRequest
//                                jsonObjectRequest = new JsonObjectRequest
//                                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
//
//                                    @Override
//                                    public void onResponse(JSONObject response) {
//
//                                        Intent intent = new Intent(getApplicationContext(), MyPageActivity_old.class);
//                                        intent.putExtra("loginpatient", patientVO);
//                                        Toast.makeText(getApplicationContext(), "예약이 취소되었습니다.", Toast.LENGTH_SHORT).show();
//
//                                        startActivity(intent);
//                                    }
//                                }, new Response.ErrorListener() {
//                                    @Override
//                                    public void onErrorResponse(VolleyError error) {
//                                        // TODO: Handle error
//                                        error.getStackTrace();
//                                        Toast.makeText(getApplicationContext(), "인터넷 연결이 되지 않습니다.", Toast.LENGTH_SHORT).show();
//
//                                    }
//                                });
//
//                        // Access the RequestQueue through your singleton class.
//                        requestQueue.add(jsonObjectRequest);
//                    }
//                });
//                dlg = new AlertDialog.Builder(MyPageActivity_old.this);
//
//                dlg.setView(dialogView2);
//                dlg.show();
//
//
//            }
//        });
//    }
//
//
//    private class SearchThread3 extends Thread {
//        private static final String TAG = "ExampleThread";
//
//        public SearchThread3() { // 초기화 작업
//        }
//
//        public void run(List<String> hpnamelist, List<String> hpaddresslist) {
//
//            try {
//                dutyMapimg = " ";
//                while (parserEvent != XmlPullParser.END_DOCUMENT) {
//                    switch (parserEvent) {
//                        case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
//
//                            if (parser.getName().equals("dutyAddr")) { //기관ID
//                                inDutyAddr = true;
//                            }
//                            if (parser.getName().equals("dutyName")) { //기관ID
//                                inDutyName = true;
//                            }
//
//                            break;
//
//                        case XmlPullParser.TEXT://parser가 내용에 접근했을때
//
//                            if (inDutyAddr) { //isMapy이 true일 때 태그의 내용을 저장.
//                                dutyAddr = parser.getText();
//                                inDutyAddr = false;
//                            }
//                            if (inDutyName) { //isMapy이 true일 때 태그의 내용을 저장.
//                                dutyName = parser.getText();
//                                inDutyName = false;
//                            }
//                            if (inDgidIdName) { //isMapy이 true일 때 태그의 내용을 저장.
//                                dgidIdName = parser.getText();
//                                inDgidIdName = false;
//                            }
//                            break;
//                        case XmlPullParser.END_TAG:
//                            if (parser.getName().equals("item")) {
//                                    if (dutyAddr != null)
//                                        hpaddresslist.add(dutyAddr);
//                                    if (dutyName != null)
//                                        hpnamelist.add(dutyName);
//                            }
//                            break;
//
//                    }
//                    parserEvent = parser.next();
//                }
//            } catch (Exception e) {
//
//            }
//
//        }
    }

    @Override
    public void onItemSelected(View v, int position) {

    }
}