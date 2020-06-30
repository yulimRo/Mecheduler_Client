package com.example.mecheduler;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
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
import com.example.mecheduler.DTO.LikeList;
import com.example.mecheduler.DTO.ListVO;
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

public class info_hospital extends AppCompatActivity implements OnMapReadyCallback {

    private Toolbar toolbar;
    private ActionBar actionBar;
    private TextView tv1, tv_toolbar, tv_location, tv_tel, tv_mapimg, tv_dg_id_name, tv_facInfo, tv_title_fac_info, tv_title_near_medi, near_medi, hpMapimg_title, tv;
    private GoogleMap mMap;
    private String wgs84Lon = null, wgs84Lat = null;
    private String reservation_hpcode, reservation_hpname;
    private PatientVO loginpatient;
    private ImageButton btn_empty, btn_fill;
    private int i = 0;
    private String[] near;
    private String b = "";
    private URL url;
    private String seac;
    private int count = 0, result = 0;
    RequestQueue requestQueue;
    private String hpid, dutyAddr, dutyName = null, dutyTel1 = null;

    private ListVO list2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_hospital);

//        tv1 = (TextView)findViewById(R.id.hpInfoText); //병원안내
        tv_toolbar = (TextView) findViewById(R.id.toolbar_title); //제목텍스트
        tv_location = (TextView) findViewById(R.id.hpLocation); // 병원위치
        tv_tel = (TextView) findViewById(R.id.hpTel1Text); //전화번호
        tv_mapimg = (TextView) findViewById(R.id.hpMapimgText); //간이약도
        tv_dg_id_name = (TextView) findViewById(R.id.dgidIdNameText); //진료과목
        tv_facInfo = (TextView) findViewById(R.id.facilityInfo); //시설정보
        tv_title_fac_info = (TextView) findViewById(R.id.tv_title_fac_info); //시설정보타이틀
        tv_title_near_medi = (TextView) findViewById(R.id.tv_title_near_medi); //근처약국타이틀
        near_medi = (TextView) findViewById(R.id.nearMedi); //근처 약국
        hpMapimg_title = (TextView) findViewById(R.id.hpMapimg_title); //근처 약국
        tv = (TextView) findViewById(R.id.hp_resbtn); //근처 약국

        btn_fill = (ImageButton) findViewById(R.id.btn_fill);
        btn_empty = (ImageButton) findViewById(R.id.btn_empty);


        StrictMode.enableDefaults();


        boolean initem = false, inDutyAddr = false, inDutyDiv = false, inDutyDivNam = false, inDutyEmcls = false;
        boolean inDutyEmclsNam = false, inDutyEryn = false, inDutyEtc = false, inDutyInf = false, inDutyMapimg = false;
        boolean inDutyName = false, inDutyTel1 = false, inDutyTel3 = false, inHpid = false, inPostCdn1 = false, inPostCdn2 = false;
        boolean inWgs84Lon = false, inWgs84Lat = false;

        String addr = null, dutyDiv = null, dutyDivNam = null, dutyEmcls = null, dutyEmclsNam = null, dutyEryn = null, dutyEtc = null;
        String dutyInf = null, dutyMapimg = null, dutyTel3 = null, postCdn1 = null, postCdn2 = null;


        boolean inHvec = false, inHvoc = false, inHvcc = false, inHvncc = false, inHvccc = false, inHvicc = false, inHvgc = false, inDutyHayn = false;
        boolean inDutyHano = false, inMKioskTy25 = false, inMKioskTy1 = false, inMKioskTy2 = false, inMKioskTy3 = false, inMKioskTy4 = false, inMKioskTy5 = false, inMKioskTy6 = false;
        boolean inMKioskTy7 = false, inMKioskTy8 = false, inMKioskTy9 = false, inMKioskTy10 = false, inMKioskTy11 = false, inO001 = false, inO002 = false, inO003 = false;
        boolean inO004 = false, inO005 = false, inO006 = false, inO007 = false, inO008 = false, inO009 = false, inO010 = false, inO011 = false;
        boolean inO012 = false, inO013 = false, inO014 = false, inO015 = false, inO016 = false, inO017 = false, inO018 = false, inO019 = false;
        boolean inO020 = false, inO021 = false, inO022 = false, inO023 = false, inO024 = false, inO025 = false, inO026 = false, inO027 = false;
        boolean inO028 = false, inO029 = false, inO030 = false, inO031 = false, inO032 = false, inO033 = false, inO034 = false, inO035 = false;
        boolean inO036 = false, inO037 = false, inO038 = false, inDgidIdName = false, inHpbdn = false, inHpccuyn = false, inHpcuyn = false, inHperyn = false;
        boolean inHpgryn = false, inHpicuyn = false, inHpnicuyn = false, inHpopyn = false;


        String hvec = null, hvoc = null, hvcc = null, hvncc = null, hvccc = null, hvicc = null, hvgc = null, dutyHayn = null;
        String dutyHano = null, MKioskTy25 = null, MKioskTy1 = null, MKioskTy2 = null, MKioskTy3 = null, MKioskTy4 = null, MKioskTy5 = null, MKioskTy6 = null;
        String MKioskTy7 = null, MKioskTy8 = null, MKioskTy9 = null, MKioskTy10 = null, MKioskTy11 = null, o001 = null, o002 = null, o003 = null;
        String o004 = null, o005 = null, o006 = null, o007 = null, o008 = null, o009 = null, o010 = null, o011 = null;
        String o012 = null, o013 = null, o014 = null, o015 = null, o016 = null, o017 = null, o018 = null, o019 = null;
        String o020 = null, o021 = null, o022 = null, o023 = null, o024 = null, o025 = null, o026 = null, o027 = null;
        String o028 = null, o029 = null, o030 = null, o031 = null, o032 = null, o033 = null, o034 = null, o035 = null;
        String o036 = null, o037 = null, o038 = null, dgidIdName = null, hpbdn = null, hpccuyn = null, hpcuyn = null, hperyn = null;
        String hpgryn = null, hpicuyn = null, hpnicuyn = null, hpopyn = null;

        Intent intent = getIntent(); /*데이터 수신*/

        String find_hpid = intent.getExtras().getString("find_hpid"); /*String형*/
        seac = intent.getExtras().getString("seac"); /*String형*/
        loginpatient = (PatientVO) getIntent().getParcelableExtra("loginpatient");



        //액션바//
        {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        String url3 = "http://" + Constants.IP + ":8081/getListLikeHpJson?pcode="+ loginpatient.getPcode();
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

                        String url = "http://" + Constants.IP + ":8081/deletelikehpJson?hpid="+hpid+"&pcode="+loginpatient.getPcode();
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

                        String url = "http://" + Constants.IP + ":8081/addlikehospitalJosn";
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


        try {
//            if(getIntent().getExtras().getString("seac").equals("1")){
//                url = new URL("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlBassInfoInqire?serviceKey=1PR4zbwEU588IOn%2Bpvt9JkwKc5heAwcp3PCTTagAoyoarqWY8puMYz97T0xo6X50PmCFV2RfbzwU3mjpanL48A%3D%3D&HPID=A2100004"); //검색 URL부분
//                near_medi.setText("평화약국평촌열린약국 / 평촌성신약국 / 동인당약국 / 이레약국 / 밝은약국 / 평촌광장약국 / 새동안약국 / 아민약국 / 오얏봉약국");
//                Log.v("url", url.toString());
//            }
//            else{
            url = new URL("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlBassInfoInqire?serviceKey=1PR4zbwEU588IOn%2Bpvt9JkwKc5heAwcp3PCTTagAoyoarqWY8puMYz97T0xo6X50PmCFV2RfbzwU3mjpanL48A%3D%3D&HPID="+find_hpid); //검색 URL부분
            near_medi.setText("경희제일약국 / 약원한약국 / 경희온누리약국 / 성원약국 / 대학약국 / 경희동물약국 / 경희정문약국 / 새경희약국 / 경희중앙약국 / 경희메티칼약국 ");
            Log.v("url", url.toString());
//            }

            XmlPullParserFactory parserCreator = XmlPullParserFactory.newInstance();
            XmlPullParser parser = parserCreator.newPullParser();

            parser.setInput(url.openStream(), null);

            int parserEvent = parser.getEventType();
            System.out.println("파싱시작합니다.");

            while (parserEvent != XmlPullParser.END_DOCUMENT) {
                switch (parserEvent) {
                    case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                        if (parser.getName().equals("dutyAddr")) { //주
                            inDutyAddr = true;
                        }
                        if (parser.getName().equals("dutyDiv")) { //병원분류
                            inDutyDiv = true;
                        }
                        if (parser.getName().equals("dutyDivNam")) { //병원분류
                            inDutyDivNam = true;
                        }
                        if (parser.getName().equals("dutyEryn")) { //응급의료기관코드
                            inDutyEryn = true;
                        }
                        if (parser.getName().equals("dutyEmclsNam")) { //응급의료기관코드명
                            inDutyEmclsNam = true;
                        }
                        if (parser.getName().equals("dutyEryn")) { //응급실운영여부
                            inDutyEryn = true;
                        }
                        if (parser.getName().equals("dutyEtc")) { //비고
                            inDutyEtc = true;
                        }
                        if (parser.getName().equals("dutyInf")) { //기관설명상세
                            inDutyInf = true;
                        }
                        if (parser.getName().equals("dutyMapimg")) { //간이약도
                            inDutyMapimg = true;
                        }
                        if (parser.getName().equals("dutyName")) { //기관명
                            inDutyName = true;
                        }
                        if (parser.getName().equals("dutyTel1")) { //mapy 대표전화1
                            inDutyTel1 = true;
                        }
                        if (parser.getName().equals("dutyTel3")) { //mapy응급실 전화
                            inDutyTel3 = true;
                        }
                        if (parser.getName().equals("hpid")) { //기관ID
                            inHpid = true;
                        }
                        if (parser.getName().equals("postCdn1")) { //우편번호1
                            inDutyEmclsNam = true;
                        }
                        if (parser.getName().equals("postCdn2")) { //우편번호2
                            inDutyEmclsNam = true;
                        }
                        if (parser.getName().equals("wgs84Lon")) { //병원경도
                            inWgs84Lon = true;
                        }
                        if (parser.getName().equals("wgs84Lat")) { //병원위도
                            inWgs84Lat = true;
                        }
                        if (parser.getName().equals("hvec")) { //응급실
                            inHvec = true;
                        }
                        if (parser.getName().equals("hvoc")) { //수술실
                            inHvoc = true;
                        }
                        if (parser.getName().equals("hvcc")) { //신경중환자
                            inHvcc = true;
                        }
                        if (parser.getName().equals("hvncc")) { //신생중환자
                            inHvncc = true;
                        }
                        if (parser.getName().equals("hvccc")) { //흉부중환자
                            inHvccc = true;
                        }
                        if (parser.getName().equals("hvicc")) { //일반중환자
                            inHvicc = true;
                        }
                        if (parser.getName().equals("hvgc")) { //입원실
                            inHvgc = true;
                        }
                        if (parser.getName().equals("dutyHayn")) { //입원실가용여부
                            inDutyHayn = true;
                        }
                        if (parser.getName().equals("dutyHano")) { //병상수
                            inDutyHano = true;
                        }
                        if (parser.getName().equals("MKioskTy1")) { //응급실 가능/불가
                            inMKioskTy25 = true;
                        }
                        if (parser.getName().equals("MKioskTy1")) { //뇌출혈수술 가능/불가
                            inMKioskTy1 = true;
                        }
                        if (parser.getName().equals("MKioskTy2")) { //뇌경색의재관류 가능/불가
                            inMKioskTy2 = true;
                        }
                        if (parser.getName().equals("MKioskTy3")) { //심근경색의재관류 가능/불가
                            inMKioskTy3 = true;
                        }
                        if (parser.getName().equals("MKioskTy4")) { //복부손상의수술 가능/불가
                            inMKioskTy4 = true;
                        }
                        if (parser.getName().equals("MKioskTy5")) { //사지접합의수술 가능/불가
                            inMKioskTy5 = true;
                        }
                        if (parser.getName().equals("MKioskTy6")) { //응급내시경 가능/불가
                            inMKioskTy6 = true;
                        }
                        if (parser.getName().equals("MKioskTy7")) { //응급투석 가능/불가
                            inMKioskTy7 = true;
                        }
                        if (parser.getName().equals("MKioskTy8")) { //조산산모 가능/불가
                            inMKioskTy8 = true;
                        }
                        if (parser.getName().equals("MKioskTy9")) { //정신질환자 가능/불가
                            inMKioskTy9 = true;
                        }
                        if (parser.getName().equals("MKioskTy10")) { //신생아 가능/불가
                            inMKioskTy10 = true;
                        }
                        if (parser.getName().equals("MKioskTy11")) { //중증화상 가능/불가
                            inMKioskTy11 = true;
                        }
                        if (parser.getName().equals("o001")) { //응급실 일반병상 있음/없음
                            inO001 = true;
                        }
                        if (parser.getName().equals("o002")) { //응급실 소아 병상 있음/없음
                            inO002 = true;
                        }
                        if (parser.getName().equals("o003")) { //응급실 음압 격리 병상 있음/없음
                            inO003 = true;
                        }
                        if (parser.getName().equals("o004")) { //응급실 일반 격리 병상 있음/없음
                            inO004 = true;
                        }
                        if (parser.getName().equals("o005")) { //응급전용 중환자실 있음/없음
                            inO005 = true;
                        }
                        if (parser.getName().equals("o006")) { //내과중환자실 있음/없음
                            inO006 = true;
                        }
                        if (parser.getName().equals("o007")) { //외과중환자실 있음/없음
                            inO007 = true;
                        }
                        if (parser.getName().equals("o008")) { //신생아중환자실 있음/없음
                            inO008 = true;
                        }
                        if (parser.getName().equals("o009")) { //소아 중환자실 있음/없음
                            inO009 = true;
                        }
                        if (parser.getName().equals("o010")) { //소아응급전용 중환자실 병상 있음/없음
                            inO010 = true;
                        }
                        if (parser.getName().equals("o011")) { //신경과중환자실 있음/없음
                            inO011 = true;
                        }
                        if (parser.getName().equals("o012")) { //신경외과 중환자실 있음/없음
                            inO012 = true;
                        }
                        if (parser.getName().equals("o013")) { //화상중환자실 있음/없음
                            inO013 = true;
                        }
                        if (parser.getName().equals("o014")) { //외상중환자실 있음/없음
                            inO014 = true;
                        }
                        if (parser.getName().equals("o015")) { //심장내과 중환자실 있음/없음
                            inO015 = true;
                        }
                        if (parser.getName().equals("o016")) { //흉부외과 중환자실 있음/없음
                            inO016 = true;
                        }
                        if (parser.getName().equals("o017")) { //일반 중환자실 있음/없음
                            inO017 = true;
                        }
                        if (parser.getName().equals("o018")) { //중환자실 내 음압 격리 병상 있음/없음
                            inO018 = true;
                        }
                        if (parser.getName().equals("o019")) { //응급전용 입원실 있음/없음
                            inO019 = true;
                        }
                        if (parser.getName().equals("o020")) { //소아응급전용 입원 병상 입원실 있음/없음
                            inO020 = true;
                        }
                        if (parser.getName().equals("o021")) { //외상전용 입원실 있음/없음
                            inO021 = true;
                        }
                        if (parser.getName().equals("o022")) { //수술실 있음/없음
                            inO022 = true;
                        }
                        if (parser.getName().equals("o023")) { //외상전용 수술실 있음/없음
                            inO023 = true;
                        }
                        if (parser.getName().equals("o024")) { //정신과 폐쇄 병상 있음/없음
                            inO024 = true;
                        }
                        if (parser.getName().equals("o025")) { //음압 격리 병상 있음/없음
                            inO025 = true;
                        }
                        if (parser.getName().equals("o026")) { //분만실 있음/없음
                            inO026 = true;
                        }
                        if (parser.getName().equals("o027")) { //CT 있음/없음
                            inO027 = true;
                        }
                        if (parser.getName().equals("o028")) { //MRI 있음/없음
                            inO028 = true;
                        }
                        if (parser.getName().equals("o029")) { //혈관 찰영기 있음/없음
                            inO029 = true;
                        }
                        if (parser.getName().equals("o030")) { //인공호흡기 있음/없음
                            inO030 = true;
                        }
                        if (parser.getName().equals("o031")) { //인공호흡기(소아) 있음/없음
                            inO031 = true;
                        }
                        if (parser.getName().equals("o032")) { //인큐베이터 있음/없음
                            inO032 = true;
                        }
                        if (parser.getName().equals("o033")) { //CRRT 있음/없음
                            inO033 = true;
                        }
                        if (parser.getName().equals("o034")) { //ECMO 있음/없음
                            inO034 = true;
                        }
                        if (parser.getName().equals("o035")) { //치료적 저체온 요법 있음/없음
                            inO035 = true;
                        }
                        if (parser.getName().equals("o036")) { //화상전용 처치실 있음/없음
                            inO036 = true;
                        }
                        if (parser.getName().equals("o037")) { //고압산소치료기 있음/없음
                            inO037 = true;
                        }
                        if (parser.getName().equals("o038")) { //일반입원실 있음/없음
                            inO038 = true;
                        }
                        if (parser.getName().equals("dgidIdName")) { //진료과목
                            inDgidIdName = true;
                        }
                        if (parser.getName().equals("hpbdn")) { //병상수
                            inHpbdn = true;
                        }
                        if (parser.getName().equals("hpccuyn")) { //흉부중환자실 수
                            inHpccuyn = true;
                        }
                        if (parser.getName().equals("hpcuyn")) { //신경중환자실 수
                            inHpcuyn = true;
                        }
                        if (parser.getName().equals("hperyn")) { //응급실 수
                            inHperyn = true;
                        }
                        if (parser.getName().equals("hpgryn")) { //입원실 수
                            inHpgryn = true;
                        }
                        if (parser.getName().equals("hpicuyn")) { //일반중환자실 수
                            inHpicuyn = true;
                        }
                        if (parser.getName().equals("hpnicuyn")) { //신생아중환자실 수
                            inHpnicuyn = true;
                        }
                        if (parser.getName().equals("hpopyn")) { //수술실 수
                            inHpopyn = true;
                        }

                        break;

                    case XmlPullParser.TEXT://parser가 내용에 접근했을때
                        if (inDutyAddr) {  //주소
                            dutyAddr = parser.getText();
                            inDutyAddr = false;
                        }
                        if (inDutyDiv) {
                            dutyDiv = parser.getText();
                            inDutyDiv = false;
                        }
                        if (inDutyDivNam) { //isMapx이 true일 때 태그의 내용을 저장.
                            dutyDivNam = parser.getText();
                            inDutyDivNam = false;
                        }
                        if (inDutyEmcls) { //isMapy이 true일 때 태그의 내용을 저장.
                            dutyEmcls = parser.getText();
                            inDutyEmcls = false;
                        }
                        if (inDutyEmclsNam) { //isMapy이 true일 때 태그의 내용을 저장.
                            dutyEmclsNam = parser.getText();
                            inDutyEmclsNam = false;
                        }
                        if (inDutyEryn) { //isMapy이 true일 때 태그의 내용을 저장.
                            dutyEryn = parser.getText();
                            inDutyEryn = false;
                        }
                        if (inDutyEtc) { //isMapy이 true일 때 태그의 내용을 저장.
                            dutyEtc = parser.getText();
                            inDutyEtc = false;
                        }
                        if (inDutyInf) { //isMapy이 true일 때 태그의 내용을 저장.
                            dutyInf = parser.getText();
                            inDutyInf = false;
                        }
                        if (inDutyMapimg) { //isMapy이 true일 때 태그의 내용을 저장.
                            dutyMapimg = parser.getText();
                            inDutyMapimg = false;
                        }
                        if (inDutyName) { //isMapy이 true일 때 태그의 내용을 저장.
                            dutyName = parser.getText();
                            inDutyName = false;
                        }
                        if (inDutyTel1) { //isMapy이 true일 때 태그의 내용을 저장.
                            dutyTel1 = parser.getText();
                            inDutyTel1 = false;
                        }
                        if (inDutyTel3) { //isMapy이 true일 때 태그의 내용을 저장.
                            dutyTel3 = parser.getText();
                            inDutyTel3 = false;
                        }
                        if (inHpid) { //isMapy이 true일 때 태그의 내용을 저장.
                            hpid = parser.getText();
                            inHpid = false;
                        }
                        if (inPostCdn1) { //isMapy이 true일 때 태그의 내용을 저장.
                            postCdn1 = parser.getText();
                            inPostCdn1 = false;
                        }
                        if (inPostCdn2) { //isMapy이 true일 때 태그의 내용을 저장.
                            postCdn2 = parser.getText();
                            inPostCdn2 = false;
                        }
                        if (inWgs84Lon) { //isMapy이 true일 때 태그의 내용을 저장.
                            wgs84Lon = parser.getText();
                            inWgs84Lon = false;
                        }
                        if (inWgs84Lat) { //isMapy이 true일 때 태그의 내용을 저장.
                            wgs84Lat = parser.getText();
                            inWgs84Lat = false;
                        }
                        if (inHvec) {
                            hvec = parser.getText();
                            inHvec = false;
                        }
                        if (inHvoc) {
                            hvoc = parser.getText();
                            inHvoc = false;
                        }
                        if (inHvcc) {
                            hvcc = parser.getText();
                            inHvcc = false;
                        }
                        if (inHvncc) {
                            hvncc = parser.getText();
                            inHvncc = false;
                        }
                        if (inHvccc) {
                            hvccc = parser.getText();
                            inHvccc = false;
                        }
                        if (inHvicc) {
                            hvicc = parser.getText();
                            inHvicc = false;
                        }
                        if (inHvgc) {
                            hvgc = parser.getText();
                            inHvgc = false;
                        }
                        if (inDutyHayn) {
                            dutyHayn = parser.getText();
                            inDutyHayn = false;
                        }
                        if (inDutyHano) {
                            dutyHano = parser.getText();
                            inDutyHano = false;
                        }
                        if (inMKioskTy25) {
                            MKioskTy25 = parser.getText();
                            inMKioskTy25 = false;
                        }
                        if (inMKioskTy1) {
                            MKioskTy1 = parser.getText();
                            inMKioskTy1 = false;
                        }
                        if (inMKioskTy2) {
                            MKioskTy2 = parser.getText();
                            inMKioskTy2 = false;
                        }
                        if (inMKioskTy3) {
                            MKioskTy3 = parser.getText();
                            inMKioskTy3 = false;
                        }
                        if (inMKioskTy4) {
                            MKioskTy4 = parser.getText();
                            inMKioskTy4 = false;
                        }
                        if (inMKioskTy5) {
                            MKioskTy5 = parser.getText();
                            inMKioskTy5 = false;
                        }
                        if (inMKioskTy6) {
                            MKioskTy6 = parser.getText();
                            inMKioskTy6 = false;
                        }
                        if (inMKioskTy7) {
                            MKioskTy7 = parser.getText();
                            inMKioskTy7 = false;
                        }
                        if (inMKioskTy8) {
                            MKioskTy8 = parser.getText();
                            inMKioskTy8 = false;
                        }
                        if (inMKioskTy9) {
                            MKioskTy9 = parser.getText();
                            inMKioskTy9 = false;
                        }
                        if (inMKioskTy10) {
                            MKioskTy10 = parser.getText();
                            inMKioskTy10 = false;
                        }
                        if (inMKioskTy11) {
                            MKioskTy11 = parser.getText();
                            inMKioskTy11 = false;
                        }
                        if (inO001) {
                            o001 = parser.getText();
                            inO001 = false;
                        }
                        if (inO002) {
                            o002 = parser.getText();
                            inO002 = false;
                        }
                        if (inO003) {
                            o003 = parser.getText();
                            inO003 = false;
                        }
                        if (inO004) {
                            o004 = parser.getText();
                            inO004 = false;
                        }
                        if (inO005) {
                            o005 = parser.getText();
                            inO005 = false;
                        }
                        if (inO006) {
                            o006 = parser.getText();
                            inO006 = false;
                        }
                        if (inO007) {
                            o007 = parser.getText();
                            inO007 = false;
                        }
                        if (inO008) {
                            o008 = parser.getText();
                            inO008 = false;
                        }
                        if (inO009) {
                            o009 = parser.getText();
                            inO009 = false;
                        }
                        if (inO010) {
                            o010 = parser.getText();
                            inO010 = false;
                        }
                        if (inO011) {
                            o011 = parser.getText();
                            inO011 = false;
                        }
                        if (inO012) {
                            o012 = parser.getText();
                            inO012 = false;
                        }
                        if (inO013) {
                            o013 = parser.getText();
                            inO013 = false;
                        }
                        if (inO014) {
                            o014 = parser.getText();
                            inO014 = false;
                        }
                        if (inO015) {
                            o015 = parser.getText();
                            inO015 = false;
                        }
                        if (inO016) {
                            o016 = parser.getText();
                            inO016 = false;
                        }
                        if (inO017) {
                            o017 = parser.getText();
                            inO017 = false;
                        }
                        if (inO018) {
                            o018 = parser.getText();
                            inO018 = false;
                        }
                        if (inO019) {
                            o019 = parser.getText();
                            inO019 = false;
                        }
                        if (inO020) {
                            o020 = parser.getText();
                            inO020 = false;
                        }
                        if (inO021) {
                            o021 = parser.getText();
                            inO021 = false;
                        }
                        if (inO022) {
                            o022 = parser.getText();
                            inO022 = false;
                        }
                        if (inO023) {
                            o023 = parser.getText();
                            inO023 = false;
                        }
                        if (inO024) {
                            o024 = parser.getText();
                            inO024 = false;
                        }
                        if (inO025) {
                            o025 = parser.getText();
                            inO025 = false;
                        }
                        if (inO026) {
                            o026 = parser.getText();
                            inO026 = false;
                        }
                        if (inO027) {
                            o027 = parser.getText();
                            inO027 = false;
                        }
                        if (inO028) {
                            o028 = parser.getText();
                            inO028 = false;
                        }
                        if (inO029) {
                            o029 = parser.getText();
                            inO029 = false;
                        }
                        if (inO030) {
                            o030 = parser.getText();
                            inO030 = false;
                        }
                        if (inO031) {
                            o031 = parser.getText();
                            inO031 = false;
                        }
                        if (inO032) {
                            o032 = parser.getText();
                            inO032 = false;
                        }
                        if (inO033) {
                            o033 = parser.getText();
                            inO033 = false;
                        }
                        if (inO034) {
                            o034 = parser.getText();
                            inO034 = false;
                        }
                        if (inO035) {
                            o035 = parser.getText();
                            inO035 = false;
                        }
                        if (inO036) {
                            o036 = parser.getText();
                            inO036 = false;
                        }
                        if (inO037) {
                            o037 = parser.getText();
                            inO037 = false;
                        }
                        if (inO038) {
                            o038 = parser.getText();
                            inO038 = false;
                        }
                        if (inDgidIdName) {
                            dgidIdName = parser.getText();
                            inDgidIdName = false;
                        }
                        if (inHpbdn) {
                            hpbdn = parser.getText();
                            inHpbdn = false;
                        }
                        if (inHpccuyn) {
                            hpccuyn = parser.getText();
                            inHpccuyn = false;
                        }
                        if (inHpcuyn) {
                            hpcuyn = parser.getText();
                            inHpcuyn = false;
                        }
                        if (inHperyn) {
                            hperyn = parser.getText();
                            inHperyn = false;
                        }
                        if (inHpgryn) {
                            hpgryn = parser.getText();
                            inHpgryn = false;
                        }
                        if (inHpicuyn) {
                            hpicuyn = parser.getText();
                            inHpicuyn = false;
                        }
                        if (inHpnicuyn) {
                            hpnicuyn = parser.getText();
                            inHpnicuyn = false;
                        }
                        if (inHpopyn) {
                            hpopyn = parser.getText();
                            inHpopyn = false;
                        }

                        break;
                    case XmlPullParser.END_TAG:
                        if (parser.getName().equals("item")) {
                            if (dutyAddr != null)
                                tv_location.setText(dutyAddr);
//                            if(dutyDiv != null)
//                                status1.setText(status1.getText() +"\n 병원 분류: "+ dutyDiv);
//                            if(dutyDivNam != null)
//                                status1.setText(status1.getText() +"\n 병원 분류명: "+ dutyDivNam);
//                            if(dutyEmcls != null)
//                                status1.setText(status1.getText() +"\n 응급의료기관코드: "+ dutyEmcls);
//                            if(dutyEmclsNam != null)
//                                status1.setText(status1.getText() +"\n 응급의료기과코드명: "+ dutyEmclsNam);
//                            if(dutyEryn != null)
//                                status1.setText(status1.getText() +"\n 응급실 운영 여부: "+ dutyEryn);
//                            if(dutyEtc != null)
//                                status1.setText(status1.getText() +"\n 비고: "+ dutyEtc);
//                            if(dutyInf != null)
//                                status1.setText(status1.getText() +"\n 기관 설명 상세: "+ dutyInf);
                            if (dutyMapimg != null) {
                                hpMapimg_title.setVisibility(View.VISIBLE);
                                tv_mapimg.setVisibility(View.VISIBLE);
                                tv_mapimg.setText(dutyMapimg);
                            }
//                            if(dutyName != null)
//                                status1.setText(status1.getText() +"\n 기관명: "+ dutyName);
                            if (dutyTel1 != null)
                                tv_tel.setText("대표번호: " + dutyTel1);
                            if (dutyTel3 != null)
                                tv_tel.setText(tv_tel.getText() + "\n응급실번호: " + dutyTel3);
                            if (hpid != null)
                                reservation_hpcode = hpid;

                            if (o001 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n응급실 일반병상 수 :" + o001);
                            if (o002 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n응급실 소아 병상 수 :" + o002);
                            if (o003 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n응급실 음압 격리 병상 수 :" + o003);
                            if (o004 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n응급실 일반 격리 병상 수 :" + o004);
                            if (o005 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n응급전용 중환자실 수 :" + o005);
                            if (o006 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n내과중환자실 수 :" + o006);
                            if (o007 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n외과중환자실 수 :" + o007);
                            if (o008 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n신생아중환자실 수 :" + o008);
                            if (o009 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n소아중환자실 수 :" + o009);
                            if (o010 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n소아응급전용 중환자실 병상 수 :" + o010);
                            if (o011 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n신경과중환자실 수 :" + o011);
                            if (o012 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n신경외과중환자실 수 :" + o012);
                            if (o013 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n화상중환자실 수 :" + o013);
                            if (o014 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n외상중환자실 수 :" + o014);
                            if (o015 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n심장내과 중환자실 수 :" + o015);
                            if (o016 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n흉부외과 중환자실 수 :" + o016);
                            if (o017 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n일반 중환자실 수 :" + o017);
                            if (o018 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n중환자실 내 응압 격리 병상 수 :" + o018);
                            if (o019 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n응급전용 입원실 수 :" + o019);
                            if (o020 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n소아응급전용 입원 병상 수 :" + o020);
                            if (o021 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n외상전용 입원실 수 :" + o021);
                            if (o022 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n수술실 수 :" + o022);
                            if (o023 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n외상전용 수술실 수 :" + o023);
                            if (o024 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n정신과 폐쇄 병상 수 :" + o024);
                            if (o025 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n음압 격리 병상 수 :" + o025);
                            if (o026 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n분만실 : O");
                            if (o027 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\nCT : O");
                            if (o028 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\nMRI : O");
                            if (o029 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n혈관촬영기 : O");
                            if (o030 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n인공호흡기 : O");
                            if (o031 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n인공호흡기(소아) : O");
                            if (o032 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n인큐베이터 : O");
                            if (o033 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\nCRRT : O");
                            if (o034 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\nECMO : O");
                            if (o035 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n치료적 저체온요법 : O");
                            if (o036 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n화상전용 처치실 : O");
                            if (o037 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n고압산소 치료기 : O");
                            if (o038 != null)
                                tv_facInfo.setText(tv_facInfo.getText() + "\n일반 입원실 : O");
                            if (dgidIdName != null) {
                                tv_dg_id_name.setText(dgidIdName);
                            }

                            if (dutyName != null) {
                                tv_toolbar.setText(dutyName);
                                reservation_hpname = dutyName;
                            }
                            initem = false;
                        }
                        break;
                }
                parserEvent = parser.next();
            }

            if (tv_facInfo == null)
                tv_title_fac_info.setVisibility(View.GONE);
            if (near_medi == null)
                tv_title_near_medi.setVisibility(View.GONE);


            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                    .findFragmentById(R.id.map);
            mapFragment.getMapAsync(this);


            String url2 = "http://" + Constants.IP + ":8081/isjoinJson?hpcode=" + hpid;
            Log.v("url", url2.toString());
            requestQueue = Volley.newRequestQueue(this);
            requestQueue.start();

            JsonObjectRequest
                    jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url2, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            try {

                                if (response.getString("num").equals("0")) {
                                    tv.setText("예약준비중");
                                    ImageButton img = findViewById(R.id.btn_reservation);
                                    img.setBackgroundResource(R.drawable.res_no);
                                    img.setEnabled(false);

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
            requestQueue.add(jsonObjectRequest);



        } catch (
                Exception e) {
//            tv1.setText("에러가..났습니다...");
        }


    }


    //액션바 버튼 클릭했을 때
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick_reservation(View v) {


        Intent intent = new Intent(getApplicationContext(), reservation_page.class);

        intent.putExtra("hpcode", reservation_hpcode);
        intent.putExtra("hpname", reservation_hpname);
        intent.putExtra("loginpatient", loginpatient);
        startActivity(intent);

    }

    public void onClick_question(View v) {
        String page = getIntent().getStringExtra("page");
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(page));
        startActivity(intent);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;

        LatLng sydney = new LatLng(Double.parseDouble(wgs84Lat), Double.parseDouble(wgs84Lon));
        mMap.addMarker(new MarkerOptions().position(sydney).title(reservation_hpname));
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, (float) 14.5));


    }


}
