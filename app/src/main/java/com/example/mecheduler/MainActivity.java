package com.example.mecheduler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.TrafficStats;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mecheduler.DTO.Constants;
import com.example.mecheduler.DTO.ListVO;
import com.example.mecheduler.DTO.PatientVO;
import com.example.mecheduler.DTO.ReservationVO;
import com.google.firebase.iid.FirebaseInstanceId;
import com.opencsv.CSVReader;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import javax.crypto.Mac;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private DrawerLayout drawerLayout;
    private View drawerView;
    private TextView tv_today, tv_hello_user, tv_reservation_count;

    static List<String> list;
    private ListView listView;
    private EditText editSearch;
    private SearchAdapter adapter;
    private ArrayList<String> arraylist;
    private ViewPager viewPager;
    private PatientVO loginpatientVO;
    private ReservationVO reservationVO;
    private List<ReservationVO> reservationList;
    private int count=0;
    private String year,month,day,weekDay;
    static List<String[]> list1,list2;
    private Button mylocationbtn;

    private static final int THREAD_ID = 10000;

    static String name, lat, lng;

    RequestQueue requestQueue,requestQueue2;
    private String today_res="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.v("device", FirebaseInstanceId.getInstance().getToken());

        TrafficStats.setThreadStatsTag(THREAD_ID);

        //작업 스레드들
        final SearchThread thread = new SearchThread();

        loginpatientVO = (PatientVO) getIntent().getParcelableExtra("loginpatient");





        //액션바//
        {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

            drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawerView = (View) findViewById(R.id.drawerView);
            TextView name= drawerLayout.findViewById(R.id.drawer_name);
            name.setText(loginpatientVO.getPname()+"님");
            drawerLayout.setDrawerListener(listener);
        }

        //시간
        {
            Date currentTime = Calendar.getInstance().getTime();
            SimpleDateFormat weekdayFormat = new SimpleDateFormat("EE", Locale.getDefault());
            SimpleDateFormat dayFormat = new SimpleDateFormat("dd", Locale.getDefault());
            SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
            SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

            weekDay = weekdayFormat.format(currentTime);
            year = yearFormat.format(currentTime);
            month = monthFormat.format(currentTime);
            day = dayFormat.format(currentTime);

            tv_today = (TextView) findViewById(R.id.todayDateText);

            tv_today.setText(year + " 년 " + month + " 월 " + day + " 일 ");

            tv_hello_user = (TextView) findViewById(R.id.helloUserText);

            if (loginpatientVO != null) {
                tv_hello_user.setText("안녕하세요    " + loginpatientVO.getPname()+"님");
            } else
                tv_hello_user.setText("로그인 해주세요");
        }
        // 진료 예약 수 출력
        {
            reservationList = new ArrayList<>();

            requestQueue = Volley.newRequestQueue(this);
            requestQueue.start();
            today_res = "0";
            String date_s = year+"년"+month+"월"+day+"일";
            String url = null;
            try {
                url = "http://"+ Constants.IP+":8081/todaymyresJson?pcode=" + loginpatientVO.getPcode()+"&date="+ URLEncoder.encode(date_s,"utf-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            //String url = "http://172.30.1.48:8081/todaymyresJson?pcode=2" +"&date="+year+"-"+month+"-"+day;
            Log.v("res",url);
            JsonObjectRequest
                    jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray obj = response.getJSONArray("todayres");
                                today_res = String.valueOf(obj.length());
                                Log.v("obj", today_res);

                            } catch (JSONException e) {
                                today_res = "0";
                                Log.v("today_res",today_res);
                            }


                        }
                    }, new Response.ErrorListener() {

                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // TODO: Handle error

                            today_res = "0";
                            Log.v("today_res",today_res);

                        }
                    });


            requestQueue2 = Volley.newRequestQueue(this);
            requestQueue2.start();
            tv_reservation_count = (TextView)findViewById(R.id.reservation_count);
            url = "http://"+Constants.IP+":8081/myreservationJson?pcode=" + loginpatientVO.getPcode();
            Log.v("res",url);
            JsonObjectRequest jsonObjectRequest2 = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                JSONArray obj = response.getJSONArray("reservations");
                                tv_reservation_count.setText("  오늘 예약 : "+ today_res+  "건      총 예약 : "+obj.length()+" 건  ");
                                count = obj.length();
                                for (int i = 0; i < obj.length(); i++) {
                                    reservationVO = new ReservationVO();

                                    JSONObject jObject = obj.getJSONObject(i);
                                    reservationVO.setRcode(jObject.getString("rcode"));
                                    reservationVO.setDcode(jObject.getString("dname"));
                                    reservationVO.setHpcode(jObject.getString("hpname"));
                                    reservationVO.setPcode(jObject.getInt("pcode"));
                                    reservationVO.setDate(jObject.getString("date"));
                                    reservationVO.setTime(jObject.getString("time"));
                                    reservationVO.setSubject(jObject.getString("subject"));
                                    reservationVO.setRes_who(jObject.getInt("res_who"));

                                    reservationList.add(reservationVO);
                                }
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
            requestQueue.add(jsonObjectRequest);
            // Access the RequestQueue through your singleton class.
            requestQueue2.add(jsonObjectRequest2);


        }

        //검색//
        {


            editSearch = (EditText) findViewById(R.id.editSearch);
            editSearch.setText("");
            listView = (ListView) findViewById(R.id.listView);
            listView.setVisibility(View.GONE);
            list = new ArrayList<String>();

            new Thread(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.KITKAT)
                @Override
                public void run() {

                    try {
                        InputStreamReader is = new InputStreamReader(getResources().openRawResource(R.raw.hospital), "euc-kr");
                        BufferedReader reader = new BufferedReader(is);
                        CSVReader read = new CSVReader(reader);
                        list1 = read.readAll();

                        InputStreamReader is2 = new InputStreamReader(getResources().openRawResource(R.raw.ph), "euc-kr");
                        BufferedReader reader2 = new BufferedReader(is2);
                        CSVReader read2 = new CSVReader(reader2);
                        list2 = read2.readAll();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                    Objects.requireNonNull(MainActivity.this).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            for (int n = 1; n < 72748; n++)
                                list.add(list1.get(n)[1]);

                            for (int n = 1; n < 22942; n++)
                                list.add(list2.get(n)[1]);

                            arraylist = new ArrayList<String>();
                            arraylist.addAll(list);

                            adapter = new SearchAdapter(list, MainActivity.this);

                            listView.setAdapter(adapter);

                            editSearch.addTextChangedListener(new TextWatcher() {
                                @Override
                                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                                }

                                @Override
                                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                                }

                                @Override
                                public void afterTextChanged(Editable editable) {
                                    listView.setVisibility(View.VISIBLE);
                                    String text = editSearch.getText().toString();
                                    search(text);
                                }
                            });

                            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                    editSearch.setText(list.get(position));
                                    onClick_search_img();
                                }
                            });

                            list.clear();
                        }
                    });
                }
            }).start();



        }

        //뷰페이저
        {
            ArrayList<Integer> listImage = new ArrayList<>();
            listImage.add(R.drawable.warnig);
            listImage.add(R.drawable.warnig2);
            listImage.add(R.drawable.warnig3);

            viewPager = findViewById(R.id.viewPager);
            FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager());
            // ViewPager와  FragmentAdapter 연결
            viewPager.setAdapter(fragmentAdapter);

            viewPager.setClipToPadding(false);
            int dpValue = 16;
            float d = getResources().getDisplayMetrics().density;
            int margin = (int) (dpValue * d);
            viewPager.setPadding(margin, 0, margin, 0);
            viewPager.setPageMargin(margin / 2);

            // FragmentAdapter에 Fragment 추가, Image 개수만큼 추가
            for (int i = 0; i < listImage.size(); i++) {
                viewPager imageFragment = new viewPager();
                Bundle bundle = new Bundle();
                bundle.putInt("imgRes", listImage.get(i));
                imageFragment.setArguments(bundle);
                fragmentAdapter.addItem(imageFragment);
            }
            fragmentAdapter.notifyDataSetChanged();
        }
    }

    //서랍
    DrawerLayout.DrawerListener listener = new DrawerLayout.DrawerListener() {
        @Override
        public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {
        }

        @Override
        public void onDrawerOpened(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerClosed(@NonNull View drawerView) {
        }

        @Override
        public void onDrawerStateChanged(int newState) {
        }
    };

    //툴바 버튼 추가
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_menu, menu);

        return true;
    }

    //툴바 버튼을 클릭했을 때
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(drawerView);
                break;
            case R.id.action_my:
                Intent intent = new Intent(getApplicationContext(), mypage.class);
                intent.putExtra("loginpatient", loginpatientVO);
                intent.putExtra("count", count);
                startActivityForResult(intent, 101);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        list.clear();

        // 문자 입력이 없을때는 모든 데이터를 보여준다.
        if (charText.length() == 0) {

        }
        // 문자 입력을 할때..
        else {
            // 리스트의 모든 데이터를 검색한다.
            for (int i = 0; i < arraylist.size(); i++) {
                // arraylist의 모든 데이터에 입력받은 단어(charText)가 포함되어 있으면 true를 반환한다.
                if (arraylist.get(i).toLowerCase().contains(charText)) {
                    // 검색된 데이터를 리스트에 추가한다.
                    list.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    public void onClick_search(View v) {
        Intent intent = new Intent(getApplicationContext(), menu_search_for_list_old.class);
        intent.putExtra("loginpatient", loginpatientVO);
        Log.v("main_patient",loginpatientVO.getPname());
        startActivity(new Intent(intent));
    }

    public void onClick_search_img() {
        if (editSearch.getText().toString().contains("약국"))
        {
            for (int n = 1; n < 22942; n++)
            {
                if (editSearch.getText().toString().equals(list2.get(n)[1]))
                {
                    name = list2.get(n)[1];
                    lat = list2.get(n)[14];
                    lng = list2.get(n)[13];
                    break;
                }
            }
        }
        else
        {
            for (int n = 1; n < 72748; n++)
            {
                if (editSearch.getText().toString().equals(list1.get(n)[1]))
                {
                    name = list1.get(n)[1];
                    lat = list1.get(n)[20];
                    lng = list1.get(n)[19];
                    break;
                }
            }
        }

        listView.setVisibility(View.GONE);
        Intent intent = new Intent(getApplicationContext(), appointment_search_old.class);
        intent.putExtra("name", name);
        intent.putExtra("lat", lat);
        intent.putExtra("lng", lng);
        intent.putExtra("loginpatient", loginpatientVO);
        startActivity(intent);
    }

    public void onClick_appointment(View v) {
        Intent intent = new Intent(getApplicationContext(), menu_search_for_list_old.class);
        intent.putExtra("loginpatient", loginpatientVO);
        Log.v("main_patient",loginpatientVO.getPname());
        startActivity(new Intent(intent));
    }

    public void onClick_status(View v) {
        Intent intent = new Intent(getApplicationContext(), mypage.class);
        intent.putExtra("loginpatient", loginpatientVO);
        Log.v("main_patient",loginpatientVO.getPname());
        startActivity(intent);
    }

//    public void onClick_prescription(View v) {
//        startActivity(new Intent(getApplicationContext(), menu_prescription.class));
//    }

    public void onClick_prescription(View v) {
        Intent intent = new Intent(getApplicationContext(), updateMyInfoActivity.class);
        intent.putExtra("loginpatient", loginpatientVO);
        startActivity(intent);
    }

    public void onClick_self(View v) {
        startActivity(new Intent(getApplicationContext(), menu_self.class));
    }

    public void onClick_log_out(View v) {
        startActivity(new Intent(getApplicationContext(), LogInActivity.class));

        SharedPreferences sf = getSharedPreferences("sFile", MODE_PRIVATE);
        SharedPreferences.Editor editor = sf.edit();
        editor.clear();
        editor.commit();
    }

    private class SearchThread extends Thread {
        private static final String TAG = "ExampleThread";

        public SearchThread() {};

        public void run(){
            try {
                InputStreamReader is = new InputStreamReader(getResources().openRawResource(R.raw.hospital), "euc-kr");
                BufferedReader reader = new BufferedReader(is);
                CSVReader read = new CSVReader(reader);
                list1 = read.readAll();

                InputStreamReader is2 = new InputStreamReader(getResources().openRawResource(R.raw.ph), "euc-kr");
                BufferedReader reader2 = new BufferedReader(is2);
                CSVReader read2 = new CSVReader(reader2);
                list2 = read2.readAll();
            } catch (Exception e) {
                e.printStackTrace();
            }


        }


    }
}

//뷰페이저
class FragmentAdapter extends FragmentPagerAdapter {

    // ViewPager에 들어갈 Fragment들을 담을 리스트
    private ArrayList<Fragment> fragments = new ArrayList<>();

    // 필수 생성자
    FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    // List에 Fragment를 담을 함수
    void addItem(Fragment fragment) {
        fragments.add(fragment);
    }
}

