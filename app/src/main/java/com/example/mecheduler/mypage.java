package com.example.mecheduler;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.example.mecheduler.DTO.Constants;
import com.example.mecheduler.DTO.LikeHospital;
import com.example.mecheduler.DTO.MedicineVO;
import com.example.mecheduler.DTO.PatientVO;
import com.example.mecheduler.DTO.ReservationVO;
import com.example.mecheduler.DTO.Reservation_PatientVO;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class mypage extends AppCompatActivity implements RecyclerViewAdapter.OnListItemSelectedInterface, RecyclerViewAdapter2.OnListItemSelectedInterface, RecyclerViewAdapter3.OnListItemSelectedInterface, RecyclerViewAdapter_hp.OnListItemSelectedInterface {

    private Toolbar toolbar;
    private ActionBar actionBar;

    private TabHost tabHost;
    private TabHost.TabSpec ts1, ts2, ts3, ts4;

    Intent intent;

    private View view;

    private Button btn_status, btn_cancel;
    private ImageButton imgBtn_guide1, imgBtn_guide2;
    private ImageButton btn_head, btn_eye, btn_nose, btn_mouth, ear, neck, heart;

    static int i = 0, color = 0, count = 0;

    private ArrayList<String> mGroupList = null;
    private ArrayList<ArrayList<String>> mChildList = null;
    private ArrayList<String> mChildListContent = null;

    private RequestQueue requestQueue;
    private RequestQueue requestQueue1;
    private RequestQueue requestQueue3;
    private RequestQueue requestQueue7;
    private RequestQueue requestQueue10;

    private RecyclerView mRecyclerView;
    private RecyclerView mRecyclerView2;
    private RecyclerView mRecyclerView3;
    private RecyclerView mRecyclerView4;
    private RecyclerView mRecyclerView5;
    private RecyclerView mRecyclerView10;
    private RecyclerView mRecyclerView7;
    private RecyclerViewAdapter mAdapter;
    private RecyclerViewAdapter mAdapter7;
    private RecyclerViewAdapter2 mAdapter2;
    private RecyclerViewAdapter_medi mAdapter_m;
    private RecyclerViewAdapter_hp mAdapter_hp;
    private RecyclerViewAdapter_ph mAdapter_hp2;
    private RecyclerViewAdapter3 mAdapter3;
    private View dialogView, dialogView2;
    private AlertDialog.Builder dlg;

    private PatientVO patientVO;
    private PatientVO patientVO1;
    private ReservationVO reservationVO;
    private List<ReservationVO> reservationVOList = new ArrayList<>();
    private List<Reservation_PatientVO> patientVOList = new ArrayList<>();
    private List<Reservation_PatientVO> patientVOList1 = new ArrayList<>();
    private MedicineVO medicineVO;
    private List<MedicineVO> medicineVOS;

    private LinearLayoutManager layoutManager;
    private LinearLayoutManager layoutManager1;
    private LinearLayoutManager layoutManager2;
    private LinearLayoutManager layoutManager3;
    private LinearLayoutManager layoutManager5;
    private LinearLayoutManager layoutManager7;
    private LinearLayoutManager layoutManager10;

    private List<String> doctor;
    private List<String> hospital;
    private List<String> subject;
    private List<String> time;
    private List<String> date;
    private List<String> rcode;
    private List<String> pcode;
    private List<Integer> res_who;
    private List<String> res_state;
    private List<String> doctor1;
    private List<String> hospital1;
    private List<String> subject1;
    private List<String> time1;
    private List<String> date1;
    private List<String> rcode1;
    private List<String> pcode1;
    private List<Integer> res_who1;
    private List<String> res_state1;
    private List<LikeHospital> likeHospitalList, likeHospitalList2;
    private LikeHospital likeHospital;
    private TextView tb_name;
    private TextView tb_num;
    private TextView tb_oneday;
    private Button tb_medinfo;
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
    private View child1;


    private TextView med_hpname, med_pname, med_oneday, med_forday, med_eattime, med_sectime, med_date;
    private TextView confrimhp, confrimDoctor, confirmSubject, confirmDate, confrimTime, confirmwho, confirmState, res_canclebtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mypage_xml);

        // 액션바 //
        {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        patientVO = getIntent().getParcelableExtra("loginpatient");
        count = getIntent().getExtras().getInt("count");
        Log.v("count ", String.valueOf(count));


        // 탭 //
        {
            //탭1
            {
                tabHost = (TabHost) findViewById(R.id.tabhost);
                tabHost.setup();

                // 진료 현황 //
                ts1 = tabHost.newTabSpec("tab1").setIndicator("진료 현황");

//                if (count == 0) {
//                    findViewById(R.id.tab_status1).setVisibility(View.VISIBLE);
//                    findViewById(R.id.tab_status2).setVisibility(View.INVISIBLE);
//                    findViewById(R.id.tab_status3).setVisibility(View.INVISIBLE);
//                } else {
                    findViewById(R.id.tab_status1).setVisibility(View.INVISIBLE);
                    findViewById(R.id.tab_status2).setVisibility(View.VISIBLE);
                    findViewById(R.id.tab_status3).setVisibility(View.INVISIBLE);
//                }

                ts1.setContent(R.id.tab1);
                tabHost.addTab(ts1);

                view = (View) findViewById(R.id.tab_status1);
                btn_status = (Button) view.findViewById(R.id.btn_status);

                view = (View) findViewById(R.id.tab_status2);

                btn_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getApplicationContext(), appointment_search_old.class);
                        intent.putExtra("loginpatient", patientVO);
                        startActivity(intent);
                    }
                });

                requestQueue7 = Volley.newRequestQueue(this);
                requestQueue7.start();
                String url = "http://" + Constants.IP + ":8081/myreservationJson?pcode=" + patientVO.getPcode();
                Log.v("res", url);

                mRecyclerView7 = (RecyclerView) findViewById(R.id.my_recycler_view);


                JsonObjectRequest
                        jsonObjectRequest7 = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    patientVOList1 = new ArrayList<>();
                                    // Setup Manager
                                    layoutManager7 = new LinearLayoutManager(mypage.this);
                                    mRecyclerView7.setLayoutManager(layoutManager7);
                                    // If you can determine that the height of each item is fixed, setting this option can improve performance.
                                    mRecyclerView7.setHasFixedSize(true);

                                    rcode1 = new ArrayList<>();
                                    doctor1 = new ArrayList<>();
                                    hospital1 = new ArrayList<>();
                                    subject1 = new ArrayList<>();
                                    time1 = new ArrayList<>();
                                    date1 = new ArrayList<>();
                                    pcode1 = new ArrayList<>();
                                    res_who1 = new ArrayList<Integer>();
                                    res_state1 = new ArrayList<>();

                                    JSONArray obj = response.getJSONArray("reservations");
                                    Log.v("lg", String.valueOf(response.length()));
                                    for (int i = 0; i < obj.length(); i++) {
                                        JSONObject jObject = obj.getJSONObject(i);
                                        rcode1.add(jObject.getString("rcode"));
                                        doctor1.add(jObject.getString("dname"));
                                        hospital1.add(jObject.getString("hpname"));
                                        pcode1.add(jObject.getString("pcode"));
                                        date1.add(jObject.getString("date"));
                                        time1.add(jObject.getString("time"));
                                        subject1.add(jObject.getString("subject"));
                                        res_who1.add(jObject.getInt("res_who"));
                                        res_state1.add(jObject.getString("res_state"));

                                    }


                                    for (int i = 0; i < hospital1.size(); i++) {
                                        Reservation_PatientVO dto = new Reservation_PatientVO();
                                        dto.setHpname(hospital1.get(i));
                                        dto.setDname(doctor1.get(i));
                                        dto.setDate(date1.get(i));
                                        dto.setTime(time1.get(i));
                                        dto.setSubject(subject1.get(i));
                                        dto.setRcode(rcode1.get(i));
                                        dto.setPcode(Integer.parseInt(pcode1.get(i)));
                                        dto.setRes_who(res_who1.get(i));
                                        dto.setRes_state(res_state1.get(i));

                                        patientVOList1.add(i, dto);


                                    }

                                    // Setting up the adapter
                                    mAdapter7 = new RecyclerViewAdapter(mypage.this, (ArrayList<Reservation_PatientVO>) patientVOList1, mypage.this);
                                    mRecyclerView7.setAdapter(mAdapter7);


                                } catch (JSONException e) {
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
                requestQueue7.add(jsonObjectRequest7);


            }

            //탭2
            {
                ts2 = tabHost.newTabSpec("tab2").setIndicator("복용 안내");
                ts2.setContent(R.id.tab2);
                tabHost.addTab(ts2);

                view = (View) findViewById(R.id.tab_guide);
                requestQueue1 = Volley.newRequestQueue(this);
                requestQueue1.start();
                String url = "http://" + Constants.IP + ":8081/myreservationJson?pcode=" + patientVO.getPcode();
                Log.v("res", url);

                mRecyclerView2 = (RecyclerView) findViewById(R.id.mypostview3);


                JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest
                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                try {
                                    // Setup Manager
                                    layoutManager2 = new LinearLayoutManager(mypage.this);
                                    mRecyclerView2.setLayoutManager(layoutManager2);
                                    // If you can determine that the height of each item is fixed, setting this option can improve performance.
                                    mRecyclerView2.setHasFixedSize(true);

                                    rcode = new ArrayList<>();
                                    doctor = new ArrayList<>();
                                    hospital = new ArrayList<>();
                                    subject = new ArrayList<>();
                                    time = new ArrayList<>();
                                    date = new ArrayList<>();
                                    pcode = new ArrayList<>();
                                    res_who = new ArrayList<Integer>();
                                    res_state = new ArrayList<>();

                                    JSONArray obj = response.getJSONArray("reservations");
                                    Log.v("lgobj", String.valueOf(response.length()));
                                    for (int i = 0; i < obj.length(); i++) {
                                        if(obj.getJSONObject(i).getString("res_state").equals("진료완료")){
                                            JSONObject jObject = obj.getJSONObject(i);


                                            rcode.add(jObject.getString("rcode"));
                                            doctor.add(jObject.getString("dname"));
                                            hospital.add(jObject.getString("hpname"));
                                            pcode.add(jObject.getString("pcode"));
                                            date.add(jObject.getString("date"));
                                            time.add(jObject.getString("time"));
                                            subject.add(jObject.getString("subject"));
                                            res_who.add(jObject.getInt("res_who"));
                                            res_state.add(jObject.getString("res_state"));
                                        }
                                    }


                                    for (int i = 0; i < hospital.size(); i++) {
                                        Reservation_PatientVO dto = new Reservation_PatientVO();
                                        dto.setHpname(hospital.get(i));
                                        dto.setDname(doctor.get(i));
                                        dto.setDate(date.get(i));
                                        dto.setTime(time.get(i));
                                        dto.setSubject(subject.get(i));
                                        dto.setRcode(rcode.get(i));
                                        dto.setPcode(Integer.parseInt(pcode.get(i)));
                                        dto.setRes_who(res_who.get(i));
                                        dto.setRes_state(res_state.get(i));

                                        patientVOList.add(i, dto);
                                    }

                                    mAdapter2 = new RecyclerViewAdapter2(mypage.this, (ArrayList<Reservation_PatientVO>) patientVOList, new RecyclerViewAdapter2.OnListItemSelectedInterface() {
                                        @Override
                                        public void onItemSelected(View v, final int position) {
                                            final RecyclerViewAdapter2.ViewHolder viewHolder = (RecyclerViewAdapter2.ViewHolder) mRecyclerView2.findViewHolderForAdapterPosition(position);
                                            dialogView = (View) View.inflate(mypage.this, R.layout.tab_guide_detail, null);
                                            requestQueue3 = Volley.newRequestQueue(mypage.this);
                                            requestQueue3.start();


                                            dlg = new AlertDialog.Builder(mypage.this);
                                            dlg.setPositiveButton("확인", null);
                                            String url = "http://" + Constants.IP + ":8081/readMedJson?rcode=" + rcode.get(position);
                                            Log.v("url", url);


                                            JsonObjectRequest
                                                    jsonObjectRequest3 = new JsonObjectRequest
                                                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                                        @Override
                                                        public void onResponse(JSONObject response) {

                                                            try{
                                                                medhpname = new ArrayList<>();
                                                                medrcode = new ArrayList<>();
                                                                medpname = new ArrayList<>();
                                                                medname = new ArrayList<>();
                                                                mednum = new ArrayList<>();
                                                                medeattime = new ArrayList<>();
                                                                medsectime = new ArrayList<>();
                                                                meddanger = new ArrayList<>();
                                                                medinfo1 = new ArrayList<>();
                                                                medinfo2 = new ArrayList<>();
                                                                meddate = new ArrayList<>();
                                                                medforday = new ArrayList<>();
                                                                medoneday = new ArrayList<>();
                                                                medicineVOS = new ArrayList<>();

                                                                JSONArray obj = response.getJSONArray("medicine");
                                                                Log.v("medicinelist", String.valueOf(obj.length()));
                                                                for (int i = 0; i < obj.length(); i++) {

                                                                    JSONObject jObject = obj.getJSONObject(i);
                                                                    medrcode.add(jObject.getString("rcode"));
                                                                    medpname.add(jObject.getString("pname"));
                                                                    medhpname.add(jObject.getString("hpname"));
                                                                    medname.add(jObject.getString("mname"));
                                                                    medinfo1.add(jObject.getString("info1"));
                                                                    medinfo2.add(jObject.getString("info2"));
                                                                    medsectime.add(jObject.getString("sectime"));
                                                                    medeattime.add(jObject.getString("eattime"));
                                                                    meddanger.add(jObject.getString("danger"));
                                                                    meddate.add(jObject.getString("replyDate"));
                                                                    mednum.add(jObject.getInt("num"));
                                                                    medforday.add(jObject.getInt("forday"));
                                                                    medoneday.add(jObject.getInt("oneday"));

                                                                    medicineVO = new MedicineVO(jObject.getString("rcode"),jObject.getString("hpname"),jObject.getString("pname"),jObject.getString("mname"),jObject.getInt("num")
                                                                            ,jObject.getInt("forday"),jObject.getInt("oneday"),jObject.getString("eattime"),jObject.getString("sectime"),jObject.getString("info1"),jObject.getString("info2")
                                                                            ,jObject.getString("danger"),jObject.getString("replyDate"));

                                                                    medicineVOS.add(medicineVO);

                                                                }


                                                                med_hpname = (TextView) dialogView.findViewById(R.id.medhp);
                                                                med_pname = (TextView) dialogView.findViewById(R.id.medpname);
                                                                med_oneday = (TextView) dialogView.findViewById(R.id.oneday);
                                                                med_forday = (TextView) dialogView.findViewById(R.id.forday);
                                                                med_date = (TextView) dialogView.findViewById(R.id.meddate);
                                                                med_hpname.setText(medhpname.get(position));
                                                                med_pname.setText(medpname.get(position));
                                                                med_forday.setText(medforday.get(position).toString());
                                                                med_oneday.setText(medoneday.get(position).toString());
                                                                med_date.setText(meddate.get(position).subSequence(0, 11));



                                                                TableLayout tb = dialogView.findViewById(R.id.tl);

                                                                child1 = LayoutInflater.from(mypage.this).inflate(R.layout.tablelayout, null);


                                                                tb_name = child1.findViewById(R.id.medname);
                                                                tb_num = child1.findViewById(R.id.mednum);
                                                                tb_oneday = child1.findViewById(R.id.medoneday);


                                                                tb_name.setText("약이름");
                                                                tb_num.setText("투여량");
                                                                tb_oneday.setText("투여횟수");

                                                                tb.addView(child1);
                                                                for ( i = 0; i < medoneday.size(); i++) {
                                                                    child1 = LayoutInflater.from(mypage.this).inflate(R.layout.tablelayout, null);


                                                                    tb_name = child1.findViewById(R.id.medname);
                                                                    tb_num = child1.findViewById(R.id.mednum);
                                                                    tb_oneday = child1.findViewById(R.id.medoneday);

                                                                    tb_name.setText(medname.get(i));
                                                                    tb_num.setText(mednum.get(i).toString());
                                                                    tb_oneday.setText(medoneday.get(i).toString());

                                                                    tb.addView(child1);

                                                                }
                                                                mRecyclerView10 = (RecyclerView) dialogView.findViewById(R.id.mypostview_medi);
                                                                layoutManager10 = new LinearLayoutManager(mypage.this);
                                                                mRecyclerView10.setLayoutManager(layoutManager10);
                                                                // If you can determine that the height of each item is fixed, setting this option can improve performance.
                                                                mRecyclerView10.setHasFixedSize(true);

                                                                mAdapter_m = new RecyclerViewAdapter_medi(mypage.this, (ArrayList<MedicineVO>) medicineVOS);
                                                                mRecyclerView10.setAdapter(mAdapter_m);

                                                            }catch (Exception e){

                                                            }

                                                        }
                                                    }, new Response.ErrorListener() {

                                                        @Override
                                                        public void onErrorResponse(VolleyError
                                                                                            error) {
                                                            // TODO: Handle error

                                                            Toast.makeText(getApplicationContext(), "인터넷 연결이 되지 않습니다.", Toast.LENGTH_SHORT).show();

                                                        }
                                                    });
                                            requestQueue3.add(jsonObjectRequest3);

                                            dlg.setView(dialogView);
                                            dlg.show();

                                        }
                                    });
                                    mRecyclerView2.setAdapter(mAdapter2);

                                } catch (Exception e) {

                                }

                            }

                        }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // TODO: Handle error

                                Toast.makeText(getApplicationContext(), "인터넷 연결이 되지 않습니다.", Toast.LENGTH_SHORT).show();


                            }
                        });
                requestQueue1.add(jsonObjectRequest2);


            }

            //탭3
            {
                ts3 = tabHost.newTabSpec("tab3").

                        setIndicator("관심 병원");
                ts3.setContent(R.id.tab3);
                tabHost.addTab(ts3);

                view = (View)

                        findViewById(R.id.tab_my);
// Setup Manager
                mRecyclerView4 = (RecyclerView) view.findViewById(R.id.mypostview1);
                heart = view.findViewById(R.id.heart);
                layoutManager1 = new

                        LinearLayoutManager(mypage.this);
                mRecyclerView4.setLayoutManager(layoutManager1);
                // If you can determine that the height of each item is fixed, setting this option can improve performance.
                mRecyclerView4.setHasFixedSize(true);


                String url3 = "http://" + Constants.IP + ":8081/getListLikeHpJson?pcode=" + patientVO.getPcode();
                requestQueue = Volley.newRequestQueue(this);
                requestQueue.start();

                likeHospitalList = new ArrayList<>();
                JsonObjectRequest
                        jsonObjectRequest2 = new JsonObjectRequest
                        (Request.Method.GET, url3, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                JSONArray obj = null;
                                try {
                                    obj = response.getJSONArray("hplist");
                                    JSONObject jObject = obj.getJSONObject(i);
                                    likeHospital = new LikeHospital();
                                    likeHospital.setHpid(jObject.getString("hpid"));
                                    likeHospital.setHptel(jObject.getString("hptel"));
                                    likeHospital.setHpaddress(jObject.getString("hpaddress"));
                                    likeHospital.setHpname(jObject.getString("hpname"));
                                    likeHospital.setPcode(jObject.getInt("pcode"));

                                    likeHospitalList.add(i, likeHospital);

                                } catch (JSONException e) {

                                }

// Setting up the adapter
                                mAdapter_hp = new RecyclerViewAdapter_hp(mypage.this, (ArrayList<LikeHospital>) likeHospitalList, new RecyclerViewAdapter_hp.OnListItemSelectedInterface() {
                                    @Override
                                    public void onItemSelected(View v, final int position) {

                                        final RecyclerViewAdapter_hp.ViewHolder viewHolder = (RecyclerViewAdapter_hp.ViewHolder) mRecyclerView4.findViewHolderForAdapterPosition(position);

                                        Intent intent = new Intent(getApplicationContext(), info_hospital.class);
                                        intent.putExtra("find_hpid", "A1100001");
                                        intent.putExtra("loginpatient", patientVO);
                                        startActivity(intent);
                                        assert viewHolder != null;
                                        viewHolder.heart.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {


                                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                                                builder.setTitle("즐겨찾기 삭제").setMessage("즐겨찾기 삭제하시겠습니까?");

                                                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Gson gson = new GsonBuilder().create();
                                                        JSONObject jsonObject = null;
                                                        LikeHospital likeHospital = new LikeHospital();
                                                        likeHospital.setPcode(patientVO.getPcode());
                                                        likeHospital.setHpid(likeHospitalList.get(position).getHpid());
                                                        likeHospital.setHpname(likeHospitalList.get(position).getHpname());
                                                        likeHospital.setHpaddress(likeHospitalList.get(position).getHpaddress());
                                                        likeHospital.setHptel(likeHospitalList.get(position).getHptel());
                                                        try {
                                                            jsonObject = new JSONObject(gson.toJson(likeHospital));
                                                            Log.i("App", "onClick: " + jsonObject);

                                                            String url = "http://" + Constants.IP + ":8081/deletelikehpJson?hpid=" + likeHospitalList.get(position).getHpid() + "&pcode=" + patientVO.getPcode();
                                                            JsonObjectRequest
                                                                    jsonObjectRequest = new JsonObjectRequest
                                                                    (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                                                                        @Override
                                                                        public void onResponse(JSONObject response) {

                                                                            Intent intent = new Intent(mypage.this, mypage.class);
                                                                            intent.putExtra("loginpatient", patientVO);
                                                                            intent.putExtra("showpage", "2");
                                                                            likeHospitalList.clear();
                                                                            startActivity(intent);

                                                                        }
                                                                    }, new Response.ErrorListener() {

                                                                        @Override
                                                                        public void onErrorResponse(VolleyError error) {
                                                                            // TODO: Handle error
                                                                            Toast.makeText(getApplicationContext(), "즐겨찾기가 추가되지 않았습니다.", Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    });

                                                            // Access the RequestQueue through your singleton class.
                                                            requestQueue.add(jsonObjectRequest);
                                                        } catch (Exception e) {
                                                            e.getStackTrace();
                                                        }

                                                    }
                                                });

                                                builder.setNegativeButton("아니요", null);
                                                AlertDialog alertDialog = builder.create();

                                                alertDialog.show();

                                            }
                                        });


                                    }
                                });
                                mRecyclerView4.setAdapter(mAdapter_hp);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                // Access the RequestQueue through your singleton class.
                requestQueue.add(jsonObjectRequest2);


                mRecyclerView5 = (RecyclerView) view.findViewById(R.id.mypostview2);
                heart = view.findViewById(R.id.heart);
                layoutManager5 = new

                        LinearLayoutManager(mypage.this);
                mRecyclerView5.setLayoutManager(layoutManager5);
                // If you can determine that the height of each item is fixed, setting this option can improve performance.
                mRecyclerView5.setHasFixedSize(true);


                String url5 = "http://" + Constants.IP + ":8081/getListLikePhJson?pcode=" + patientVO.getPcode();
                requestQueue = Volley.newRequestQueue(this);
                requestQueue.start();

                likeHospitalList2 = new ArrayList<>();
                JsonObjectRequest
                        jsonObjectRequest3 = new JsonObjectRequest
                        (Request.Method.GET, url5, null, new Response.Listener<JSONObject>() {

                            @Override
                            public void onResponse(JSONObject response) {

                                JSONArray obj = null;
                                try {
                                    obj = response.getJSONArray("hplist");
                                    JSONObject jObject = obj.getJSONObject(i);
                                    likeHospital = new LikeHospital();
                                    likeHospital.setHpid(jObject.getString("hpid"));
                                    likeHospital.setHptel(jObject.getString("hptel"));
                                    likeHospital.setHpaddress(jObject.getString("hpaddress"));
                                    likeHospital.setHpname(jObject.getString("hpname"));
                                    likeHospital.setPcode(jObject.getInt("pcode"));

                                    likeHospitalList2.add(i, likeHospital);

                                } catch (JSONException e) {

                                }

// Setting up the adapter
                                mAdapter_hp2 = new RecyclerViewAdapter_ph(mypage.this, (ArrayList<LikeHospital>) likeHospitalList2, new RecyclerViewAdapter_ph.OnListItemSelectedInterface() {
                                    @Override
                                    public void onItemSelected(View v, final int position) {

                                        final RecyclerViewAdapter_ph.ViewHolder viewHolder = (RecyclerViewAdapter_ph.ViewHolder) mRecyclerView5.findViewHolderForAdapterPosition(position);

                                        Intent intent = new Intent(getApplicationContext(), info_medicine.class);
                                        intent.putExtra("find_medi", likeHospitalList2.get(position).getHpid());
                                        intent.putExtra("loginpatient", patientVO);
                                        startActivity(intent);
                                        assert viewHolder != null;
                                        viewHolder.heart.setOnClickListener(new View.OnClickListener() {

                                            @Override
                                            public void onClick(View v) {


                                                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

                                                builder.setTitle("즐겨찾기 삭제").setMessage("즐겨찾기 삭제하시겠습니까?");

                                                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        Gson gson = new GsonBuilder().create();
                                                        JSONObject jsonObject = null;
                                                        LikeHospital likeHospital = new LikeHospital();
                                                        likeHospital.setPcode(patientVO.getPcode());
                                                        likeHospital.setHpid(likeHospitalList2.get(position).getHpid());
                                                        likeHospital.setHpname(likeHospitalList2.get(position).getHpname());
                                                        likeHospital.setHpaddress(likeHospitalList2.get(position).getHpaddress());
                                                        likeHospital.setHptel(likeHospitalList2.get(position).getHptel());
                                                        try {
                                                            jsonObject = new JSONObject(gson.toJson(likeHospital));
                                                            Log.i("App", "onClick: " + jsonObject);

                                                            String url = "http://" + Constants.IP + ":8081/deletelikephJson?hpid=" + likeHospitalList2.get(position).getHpid() + "&pcode=" + patientVO.getPcode();
                                                            JsonObjectRequest
                                                                    jsonObjectRequest = new JsonObjectRequest
                                                                    (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                                                                        @Override
                                                                        public void onResponse(JSONObject response) {
                                                                            Intent intent = new Intent(mypage.this, mypage.class);
                                                                            intent.putExtra("loginpatient", patientVO);
                                                                            intent.putExtra("showpage", "2");
                                                                            likeHospitalList.clear();
                                                                            startActivity(intent);

                                                                        }
                                                                    }, new Response.ErrorListener() {

                                                                        @Override
                                                                        public void onErrorResponse(VolleyError error) {
                                                                            // TODO: Handle error
                                                                            Toast.makeText(getApplicationContext(), "즐겨찾기가 추가되지 않았습니다.", Toast.LENGTH_SHORT).show();

                                                                        }
                                                                    });

                                                            // Access the RequestQueue through your singleton class.
                                                            requestQueue.add(jsonObjectRequest);
                                                        } catch (Exception e) {
                                                            e.getStackTrace();
                                                        }

                                                    }
                                                });

                                                builder.setNegativeButton("아니요", null);
                                                AlertDialog alertDialog = builder.create();

                                                alertDialog.show();

                                            }
                                        });


                                    }
                                });
                                mRecyclerView5.setAdapter(mAdapter_hp2);

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {

                            }
                        });

                // Access the RequestQueue through your singleton class.
                requestQueue.add(jsonObjectRequest3);


            }

            //탭4
            {
                ts4 = tabHost.newTabSpec("tab4").setIndicator("자가 진단");
                ts4.setContent(R.id.tab4);
                tabHost.addTab(ts4);
            }
            if (getIntent().getExtras().get("showpage") == null) {

                tabHost.setCurrentTab(0);

            } else if (getIntent().getExtras().get("showpage").equals("3")) {
                tabHost.setCurrentTab(3);
            } else if (getIntent().getExtras().get("showpage").equals("2")) {

                tabHost.setCurrentTab(2);
            } else if (getIntent().getExtras().get("showpage").equals("1")) {

                tabHost.setCurrentTab(1);
            }
        }

    }

    //액션버튼을 클릭했을 때
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("loginpatient", patientVO);
                startActivityForResult(intent, 101);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }


    //탭1 예약 클릭
    public void onClick_reservation(View v) {
        findViewById(R.id.tab_status1).setVisibility(View.GONE);
        findViewById(R.id.tab_status2).setVisibility(View.VISIBLE);
        findViewById(R.id.tab_status3).setVisibility(View.VISIBLE);
    }

    //탭3 병원 클릭
    public void onClick_hospital(View v) {
        startActivity(new Intent(getApplicationContext(), info_hospital.class));
    }

    //탭3 약국 클릭
    public void onClick_pharmacy(View v) {
        startActivity(new Intent(getApplicationContext(), info_pharmacy.class));
    }

    @Override
    public void onItemSelected(View v, int position) {
        final RecyclerViewAdapter.ViewHolder viewHolder = (RecyclerViewAdapter.ViewHolder) mRecyclerView7.findViewHolderForAdapterPosition(position);
        dialogView = (View) View.inflate(this, R.layout.dialog2, null);


        confrimhp = (TextView) dialogView.findViewById(R.id.confirmhp);
        confirmDate = (TextView) dialogView.findViewById(R.id.confirmDate);
        confrimTime = (TextView) dialogView.findViewById(R.id.confirmTime);
        confirmSubject = (TextView) dialogView.findViewById(R.id.confirmSubject);
        confrimDoctor = (TextView) dialogView.findViewById(R.id.confirmDoctor);
        confirmwho = (TextView) dialogView.findViewById(R.id.sub_who);
        confirmState = (TextView) dialogView.findViewById(R.id.confirmStateTv);


        confirmSubject.setText(viewHolder.subject.getText().toString());
        confirmDate.setText(viewHolder.date.getText().toString());
        confrimTime.setText(viewHolder.time.getText().toString());
        confrimDoctor.setText(viewHolder.doctor.getText().toString());
        confrimhp.setText(viewHolder.hospital.getText().toString());
        confirmwho.setText(viewHolder.res_who.getText().toString());


        if (viewHolder.res_state.getText().toString().equals("예약확인")) {
            color = getResources().getColor(R.color.sky_color);
            confirmState.setTextColor(color);
            confirmState.setText(viewHolder.res_state.getText().toString());
        } else if (viewHolder.res_state.getText().toString().equals("예약취소")) {
            color = getResources().getColor(R.color.red_color);
            confirmState.setTextColor(color);
            confirmState.setText(viewHolder.res_state.getText().toString());
        } else if (viewHolder.res_state.getText().toString().equals("진료완료")) {
            color = getResources().getColor(R.color.gray_color);
            confirmState.setTextColor(color);
            confirmState.setText(viewHolder.res_state.getText().toString());
        } else if (viewHolder.res_state.getText().toString().equals("예약접수")) {
            color = getResources().getColor(R.color.green_color);
            confirmState.setTextColor(color);
            confirmState.setText(viewHolder.res_state.getText().toString());
        }


        dlg = new AlertDialog.Builder(this);


        dlg.setPositiveButton("확인", null);

        dlg.setView(dialogView);
        dlg.show();


        viewHolder.res_cancletv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Log.v("res_state", viewHolder.res_state.getText().toString());
                if (viewHolder.res_state.getText().toString().equals("진료완료")) {
                    Toast.makeText(getApplicationContext(), "완료된 진료입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (viewHolder.res_state.getText().toString().equals("예약취소")) {
                    Toast.makeText(getApplicationContext(), "취소된 진료예약입니다.", Toast.LENGTH_SHORT).show();
                    return;
                }
                dialogView2 = (View) View.inflate(mypage.this, R.layout.dialog3, null);

                confrimhp = (TextView) dialogView2.findViewById(R.id.confirmhp);
                confirmDate = (TextView) dialogView2.findViewById(R.id.confirmDate);
                confrimTime = (TextView) dialogView2.findViewById(R.id.confirmTime);
                confirmSubject = (TextView) dialogView2.findViewById(R.id.confirmSubject);
                confrimDoctor = (TextView) dialogView2.findViewById(R.id.confirmDoctor);
                confirmwho = (TextView) dialogView2.findViewById(R.id.sub_who);
                confirmState = (TextView) dialogView2.findViewById(R.id.confirmStateTv);

                confirmSubject.setText(viewHolder.subject.getText().toString());
                confirmDate.setText(viewHolder.date.getText().toString());
                confrimTime.setText(viewHolder.time.getText().toString());
                confrimDoctor.setText(viewHolder.doctor.getText().toString());
                confrimhp.setText(viewHolder.hospital.getText().toString());
                confirmwho.setText(viewHolder.res_who.getText().toString());

                res_canclebtn = (Button) dialogView2.findViewById(R.id.rescanclebtn);


                if (viewHolder.res_state.getText().toString().equals("예약확인")) {
                    color = getResources().getColor(R.color.sky_color);
                    confirmState.setTextColor(color);
                    confirmState.setText(viewHolder.res_state.getText().toString());
                } else if (viewHolder.res_state.getText().toString().equals("예약취소")) {
                    color = getResources().getColor(R.color.red_color);
                    confirmState.setTextColor(color);
                    confirmState.setText(viewHolder.res_state.getText().toString());
                } else if (viewHolder.res_state.getText().toString().equals("진료완료")) {
                    color = getResources().getColor(R.color.gray_color);
                    confirmState.setTextColor(color);
                    confirmState.setText(viewHolder.res_state.getText().toString());
                } else if (viewHolder.res_state.getText().toString().equals("예약접수")) {
                    color = getResources().getColor(R.color.green_color);
                    confirmState.setTextColor(color);
                    confirmState.setText(viewHolder.res_state.getText().toString());
                }


                res_canclebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        requestQueue = Volley.newRequestQueue(mypage.this);
                        requestQueue.start();

                        String url = "http://" + Constants.IP + ":8081/deleteresJson?rcode=" + viewHolder.rcode.getText().toString();

                        JsonObjectRequest
                                jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {

                                        Intent intent = new Intent(getApplicationContext(), mypage.class);
                                        intent.putExtra("loginpatient", patientVO);
                                        Toast.makeText(getApplicationContext(), "예약이 취소되었습니다.", Toast.LENGTH_SHORT).show();

                                        startActivity(intent);
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // TODO: Handle error
                                        error.getStackTrace();
                                        Toast.makeText(getApplicationContext(), "인터넷 연결이 되지 않습니다.", Toast.LENGTH_SHORT).show();

                                    }
                                });

                        // Access the RequestQueue through your singleton class.
                        requestQueue.add(jsonObjectRequest);
                    }
                });
                dlg = new AlertDialog.Builder(mypage.this);

                dlg.setPositiveButton("확인", null);
                dlg.setView(dialogView2);
                dlg.show();


            }
        });
    }


    public void onClick_head(View v) {
        startActivity(new Intent(getApplicationContext(), head.class));
    }

    public void onClick_neck(View v) {
        startActivity(new Intent(getApplicationContext(), neck.class));
    }

    public void onClick_shoulder(View v) {
        startActivity(new Intent(getApplicationContext(), shoulder.class));
    }

    public void onClick_body(View v) {
        startActivity(new Intent(getApplicationContext(), body.class));
    }

    public void onClick_waist(View v) {
        startActivity(new Intent(getApplicationContext(), waist.class));
    }

    public void onClick_knee(View v) {
        startActivity(new Intent(getApplicationContext(), knee.class));
    }

    public void onClick_wrist(View v) {
        startActivity(new Intent(getApplicationContext(), wrist.class));
    }

    public void onClick_more(View v) {
        startActivity(new Intent(getApplicationContext(), more.class));
    }


}


