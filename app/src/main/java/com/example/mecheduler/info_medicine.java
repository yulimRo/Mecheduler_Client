package com.example.mecheduler;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mecheduler.DTO.Constants;
import com.example.mecheduler.DTO.LikeHospital;
import com.example.mecheduler.DTO.PatientVO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.net.URL;

import static android.widget.Toast.LENGTH_SHORT;

public class info_medicine extends AppCompatActivity implements OnMapReadyCallback {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private TextView tv1,tv_toolbar,tv_location,tv_tel,tv_mapimg,tv_dg_id_name,tv_facInfo;
    private GoogleMap mMap;
    private String lat,lon,name;

    RequestQueue requestQueue;
    private ImageButton btn_empty, btn_fill;


    private PatientVO loginpatient;
    private String hpid, dutyAddr, dutyName = null, dutyTel1 = null;

    private URL url;
    private int count = 0, result = 0;
    String find_hpid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_medicine);

//        tv1 = (TextView)findViewById(R.id.hpInfoText); //병원안내
        tv_toolbar = (TextView)findViewById(R.id.toolbar_title); //제목텍스트
        tv_location = (TextView)findViewById(R.id.hpLocation); // 병원위치
        tv_tel = (TextView)findViewById(R.id.hpTel1Text); //전화번호
        tv_mapimg = (TextView)findViewById(R.id.hpMapimgText); //간이약도
        tv_facInfo = (TextView)findViewById(R.id.facilityInfo); //시설정보

        btn_fill = (ImageButton) findViewById(R.id.btn_fill);
        btn_empty = (ImageButton) findViewById(R.id.btn_empty);



        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        StrictMode.enableDefaults();


        boolean initem = false, inDutyAddr = false, inDutyDiv = false, inDutyDivNam = false, inDutyEmcls = false;
        boolean inDutyEmclsNam = false, inDutyEryn = false, inDutyEtc = false, inDutyInf = false, inDutyMapimg=false;
        boolean inDutyName = false, inDutyTel1 = false,inDutyTel3=false,inHpid=false,inPostCdn1=false,inPostCdn2=false;
        boolean inWgs84Lon =false, inWgs84Lat =false;

        String addr = null,  dutyDiv = null, dutyDivNam = null, dutyEmcls = null, dutyEmclsNam=null, dutyEryn=null, dutyEtc=null;
        String dutyInf = null, dutyMapimg = null,dutyTel3 = null, postCdn1 = null, postCdn2 = null;



        Intent intent = getIntent(); /*데이터 수신*/

        find_hpid = intent.getExtras().getString("find_medi"); /*String형*/

        loginpatient = (PatientVO) getIntent().getParcelableExtra("loginpatient");

        String url3 = "http://" + Constants.IP + ":8081/getListLikePhJson?pcode="+ loginpatient.getPcode();
        Log.v("url3",url3+"");
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        JsonObjectRequest
                jsonObjectRequest2 = new JsonObjectRequest
                (Request.Method.GET, url3, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        JSONArray obj = null;
                        try {
                            obj = response.getJSONArray("hplist");

                            Log.v("long", String.valueOf(obj.length()));

                            if (obj.length() > 0) {
                                count++;
                                btn_empty.setBackgroundResource(R.drawable.fill);

                            }

                        } catch (JSONException e) {

                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        // Access the RequestQueue through your singleton class.
        requestQueue.add(jsonObjectRequest2);


        requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        btn_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (count > 0) {
                    Gson gson = new GsonBuilder().create();
                    JSONObject jsonObject = null;
                    LikeHospital likeHospital = new LikeHospital();
                    likeHospital.setPcode(loginpatient.getPcode());
                    likeHospital.setHpid(hpid);
                    likeHospital.setHpname(dutyName);
                    likeHospital.setHpaddress(dutyAddr);
                    likeHospital.setHptel(dutyTel1);
                    try {
                        jsonObject = new JSONObject(gson.toJson(likeHospital));
                        Log.i("App", "onClick: " + jsonObject);

                        String url = "http://" + Constants.IP + ":8081/deletelikephJson?hpid="+hpid+"&pcode="+loginpatient.getPcode();
                        JsonObjectRequest
                                jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {

                                        Toast.makeText(getApplicationContext(), "즐겨찾기에서 삭제되었습니다..", LENGTH_SHORT).show();
                                        count--;
                                        btn_empty.setBackgroundResource(R.drawable.empty);

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
                } else {

                    Gson gson = new GsonBuilder().create();
                    JSONObject jsonObject = null;
                    LikeHospital likeHospital = new LikeHospital();
                    likeHospital.setPcode(loginpatient.getPcode());
                    likeHospital.setHpid(hpid);
                    likeHospital.setHpname(dutyName);
                    likeHospital.setHpaddress(dutyAddr);
                    likeHospital.setHptel(dutyTel1);
                    try {
                        jsonObject = new JSONObject(gson.toJson(likeHospital));
                        Log.i("App", "onClick: " + jsonObject);

                        String url = "http://" + Constants.IP + ":8081/addlikephJosn";
                        Log.v("url",url+"");
                        JsonObjectRequest
                                jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {

                                        Toast.makeText(getApplicationContext(), "즐겨찾기에 추가 되었습니다..", LENGTH_SHORT).show();
                                        count++;
                                        btn_empty.setBackgroundResource(R.drawable.fill);

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
            }
        });

        //액션바//
        {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        try{
            URL url = new URL("http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyBassInfoInqire?serviceKey=1PR4zbwEU588IOn%2Bpvt9JkwKc5heAwcp3PCTTagAoyoarqWY8puMYz97T0xo6X50PmCFV2RfbzwU3mjpanL48A%3D%3D&HPID=C1202264"); //검색 URL부분
            Log.v("mediurl",url.toString());
            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT){
                switch(parserEvent){
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if(parser.getName().equals("dutyAddr")){ //주
                            inDutyAddr = true;
                        }
                        if(parser.getName().equals("dutyDiv")){ //병원분류
                            inDutyDiv = true;
                        }
                        if(parser.getName().equals("dutyDivNam")){ //병원분류
                            inDutyDivNam = true;
                        }
                        if(parser.getName().equals("dutyEryn")){ //응급의료기관코드
                            inDutyEryn = true;
                        }
                        if(parser.getName().equals("dutyEmclsNam")){ //응급의료기관코드명
                            inDutyEmclsNam = true;
                        }
                        if(parser.getName().equals("dutyEryn")){ //응급실운영여부
                            inDutyEryn = true;
                        }
                        if(parser.getName().equals("dutyEtc")){ //비고
                            inDutyEtc = true;
                        }
                        if(parser.getName().equals("dutyInf")){ //기관설명상세
                            inDutyInf = true;
                        }
                        if(parser.getName().equals("dutyMapimg")){ //간이약도
                            inDutyMapimg = true;
                        }
                        if(parser.getName().equals("dutyName")){ //기관명
                            inDutyName = true;
                        }
                        if(parser.getName().equals("dutyTel1")){ //mapy 대표전화1
                            inDutyTel1 = true;
                        }
                        if(parser.getName().equals("dutyTel3")){ //mapy응급실 전화
                            inDutyTel3 = true;
                        }
                        if(parser.getName().equals("hpid")){ //기관ID
                            inHpid = true;
                        }
                        if(parser.getName().equals("postCdn1")){ //우편번호1
                            inDutyEmclsNam = true;
                        }
                        if(parser.getName().equals("postCdn2")){ //우편번호2
                            inDutyEmclsNam = true;
                        }
                        if(parser.getName().equals("wgs84Lon")){ //병원경도
                            inWgs84Lon = true;
                        }
                        if(parser.getName().equals("wgs84Lat")){ //병원위도
                            inWgs84Lat = true;
                        }


                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if(inDutyAddr){  //주소
                            dutyAddr = parser.getText();
                            inDutyAddr = false;
                        }
                        if(inDutyDiv){
                            dutyDiv = parser.getText();
                            inDutyDiv = false;
                        }
                        if(inDutyDivNam){ //isMapx이 true일 때 태그의 내용을 저장.
                            dutyDivNam = parser.getText();
                            inDutyDivNam = false;
                        }
                        if(inDutyEmcls){ //isMapy이 true일 때 태그의 내용을 저장.
                            dutyEmcls = parser.getText();
                            inDutyEmcls = false;
                        }
                        if(inDutyEmclsNam){ //isMapy이 true일 때 태그의 내용을 저장.
                            dutyEmclsNam = parser.getText();
                            inDutyEmclsNam = false;
                        }
                        if(inDutyEryn){ //isMapy이 true일 때 태그의 내용을 저장.
                            dutyEryn = parser.getText();
                            inDutyEryn = false;
                        }
                        if(inDutyEtc){ //isMapy이 true일 때 태그의 내용을 저장.
                            dutyEtc = parser.getText();
                            inDutyEtc = false;
                        }
                        if(inDutyInf){ //isMapy이 true일 때 태그의 내용을 저장.
                            dutyInf = parser.getText();
                            inDutyInf = false;
                        }
                        if(inDutyMapimg){ //isMapy이 true일 때 태그의 내용을 저장.
                            dutyMapimg = parser.getText();
                            inDutyMapimg = false;
                        }
                        if(inDutyName){ //isMapy이 true일 때 태그의 내용을 저장.
                            dutyName = parser.getText();
                            inDutyName = false;
                        }
                        if(inDutyTel1){ //isMapy이 true일 때 태그의 내용을 저장.
                            dutyTel1 = parser.getText();
                            inDutyTel1 = false;
                        }
                        if(inDutyTel3){ //isMapy이 true일 때 태그의 내용을 저장.
                            dutyTel3 = parser.getText();
                            inDutyTel3 = false;
                        }
                        if(inHpid){ //isMapy이 true일 때 태그의 내용을 저장.
                            hpid = parser.getText();
                            inHpid = false;
                        }
                        if(inPostCdn1){ //isMapy이 true일 때 태그의 내용을 저장.
                            postCdn1 = parser.getText();
                            inPostCdn1 = false;
                        }
                        if(inPostCdn2){ //isMapy이 true일 때 태그의 내용을 저장.
                            postCdn2 = parser.getText();
                            inPostCdn2 = false;
                        }
                        if(inWgs84Lon){ //isMapy이 true일 때 태그의 내용을 저장.
                            lon = parser.getText();
                            inWgs84Lon= false;
                        }
                        if(inWgs84Lat){ //isMapy이 true일 때 태그의 내용을 저장.
                            lat = parser.getText();
                            inWgs84Lat = false;
                        }


                        break;
                    case XmlPullParser.END_TAG:
                        if(parser.getName().equals("item")){
                            if(dutyAddr != null)
                                tv_location.setText(dutyAddr);
                            if(dutyMapimg != null)
                                tv_mapimg.setText(dutyMapimg);
                            if(dutyTel1 != null)
                                tv_tel.setText("대표번호: "+ dutyTel1);
                            if(dutyName != null)
                                tv_toolbar.setText(dutyName);

                            initem = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }
        } catch(Exception e){
//            tv1.setText("에러가..났습니다...");
        }





    }



    //액션바 버튼 클릭했을 때
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch(id)
        {
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }



    public void onClick_question(View v) {
        Uri uri = Uri.parse("tel:"+dutyTel1);

        startActivity(new Intent(Intent.ACTION_DIAL,uri));
    }
    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;

        LatLng sydney = new LatLng(Double.parseDouble(lat), Double.parseDouble(lon));
        mMap.addMarker(new MarkerOptions().position(sydney).title(tv_toolbar.getText().toString()));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, (float) 14.5));


    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
