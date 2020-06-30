package com.example.mecheduler;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import com.example.mecheduler.DTO.ListVO;
import com.example.mecheduler.DTO.PatientVO;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.opencsv.CSVReader;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class appointment_search_old extends FragmentActivity implements OnMapReadyCallback {

    //GoogleMap 객체
    private GoogleMap googleMap;
    private MapFragment mapFragment;
    private LocationManager locationManager;
    private RelativeLayout boxMap;

    //나의 위도 경도 고도
    double mLatitude;  //위도
    double mLongitude; //경도

    private Marker marker;
    private MarkerOptions makerOptions;

    private RelativeLayout search;
    private TextView editresult;
    private Button btn_back;
    private ImageButton ib;

    Intent intent;

    private String select;
    private List<String> lists;
    private ListView listView;
    private EditText editSearch;
    private SearchAdapter adapter;
    private ArrayList<String> arraylist;

    private List<String[]> list;
    private ListVO list2,list1;
    private Button btn_my;
    private PatientVO loginpatientVO;

    static String name, lat, lng, code, code_name, zip, addr, tel, page, doc_all, doc_normal, doc_Intern, doc_resi, doc_pro;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.appointment_search);

        btn_my = (Button)findViewById(R.id.btn_my);

        btn_my.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(mLatitude, mLongitude), (float) 16));
                editSearch.setText("");
                editresult.setText(" '현재 위치 ' 검색결과 입니다.");
                editresult.setTextColor(getResources().getColor(R.color.gray_color));

            }
        });



        loginpatientVO = (PatientVO) getIntent().getParcelableExtra("loginpatient");

        editSearch = (EditText) findViewById(R.id.editSearch);
        editresult = (TextView) findViewById(R.id.searchresult);

        String name = getIntent().getExtras().getString("name");
        String ment = " ' "+name+" ' 검색결과 입니다.";
        editresult.setText(ment);

        //맵
        {
            btn_back = (Button) findViewById(R.id.btn_back);
            btn_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            boxMap = (RelativeLayout) findViewById(R.id.boxMap);


            //LocationManager
            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            //GPS가 켜져있는지 체크
            if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                //GPS 설정화면으로 이동
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                intent.addCategory(Intent.CATEGORY_DEFAULT);
                startActivity(intent);
                finish();
            }

            //마시멜로 이상이면 권한 요청하기
            if (Build.VERSION.SDK_INT >= 23) {
                //권한이 없는 경우
                if (ContextCompat.checkSelfPermission(appointment_search_old.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                        ContextCompat.checkSelfPermission(appointment_search_old.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(appointment_search_old.this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                }
                //권한이 있는 경우
                else {
                    requestMyLocation();
                }
            }
            //마시멜로 아래
            else {
                requestMyLocation();
            }
        }
    }

    //권한 요청후 응답 콜백
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        //ACCESS_COARSE_LOCATION 권한
        if (requestCode == 1) {
            //권한받음
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                requestMyLocation();
            }
            //권한못받음
            else {
                Toast.makeText(this, "권한없음", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }

    //나의 위치 요청
    public void requestMyLocation() {
        if (ContextCompat.checkSelfPermission(appointment_search_old.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(appointment_search_old.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //요청
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 100, 10, locationListener);
    }

    //위치정보 구하기 리스너
    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            if (ContextCompat.checkSelfPermission(appointment_search_old.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(appointment_search_old.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            //나의 위치를 한번만 가져오기 위해
            locationManager.removeUpdates(locationListener);

            //위도 경도
            mLatitude = location.getLatitude();   //위도
            mLongitude = location.getLongitude(); //경도



            //맵생성
            SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
            //콜백클래스 설정
            mapFragment.getMapAsync(appointment_search_old.this);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            Log.d("gps", "onStatusChanged");
        }

        @Override
        public void onProviderEnabled(String provider) {
        }

        @Override
        public void onProviderDisabled(String provider) {
        }
    };

    //구글맵 생성 콜백
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        intent = getIntent();
        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        this.googleMap = googleMap;

        //지도타입 - 일반
        this.googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        LatLng position;
        //나의 위치 설정
        if(TextUtils.isEmpty(intent.getStringExtra("lat")))
            position = new LatLng(mLatitude, mLongitude);
        else
        {
            if(intent.getStringExtra("name").contains("약국"))
                spinner.setSelection(2);
            else
                spinner.setSelection(1);

            position = new LatLng(Double.parseDouble(intent.getStringExtra("lat")), Double.parseDouble(intent.getStringExtra("lng")));
        }

        //화면중앙의 위치와 카메라 줌비율
        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 16));

        editresult.setText(" ' "+getIntent().getExtras().getString("name")+" ' 검색결과 입니다.");

        //지도 보여주기
        boxMap.setVisibility(View.VISIBLE);


        try {
            InputStreamReader is = new InputStreamReader(getResources().openRawResource(R.raw.hospital), "euc-kr");

            BufferedReader reader = new BufferedReader(is);
            CSVReader read = new CSVReader(reader);
            list = read.readAll();

            InputStreamReader is2 = new InputStreamReader(getResources().openRawResource(R.raw.ph), "euc-kr");
            BufferedReader reader2 = new BufferedReader(is2);
            CSVReader read2 = new CSVReader(reader2);
            list2 = new ListVO(read2.readAll()) ;

        } catch (Exception e) {
            e.printStackTrace();
        }

        listView = (ListView) findViewById(R.id.listView);
        ib = (ImageButton) findViewById(R.id.searchbtn);
        lists = new ArrayList<String>();

        //툴바 설정
        {
            googleMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
                @Override
                public void onMapClick(LatLng latLng) {
                    search = (RelativeLayout) findViewById(R.id.search);

                    if (search.getVisibility() == View.VISIBLE)
                        search.setVisibility(View.GONE);
                    else
                        search.setVisibility(View.VISIBLE);
                }
            });
        }

        //마커 눌렀을 때
        {
            googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
                @Override
                public boolean onMarkerClick(Marker marker) {
                    if(marker.getTitle().contains("약국"))
                    {
                        for (int n = 1; n < 22942; n++)
                        {
                            if (marker.getPosition().latitude == Double.parseDouble(list2.getItem().get(n)[14]) && marker.getPosition().longitude == Double.parseDouble(list2.getItem().get(n)[13]))
                            {
                                appointment_search_old.name = list2.getItem().get(n)[1];
                                appointment_search_old.code = list2.getItem().get(n)[2];
                                appointment_search_old.code_name = list2.getItem().get(n)[3];
                                appointment_search_old.zip = list2.getItem().get(n)[9];
                                appointment_search_old.addr = list2.getItem().get(n)[10];
                                appointment_search_old.tel = list2.getItem().get(n)[11];
                                appointment_search_old.lat = list2.getItem().get(n)[14];
                                appointment_search_old.lng = list2.getItem().get(n)[13];
                                break;
                            }
                        }

                        intent = new Intent(getApplicationContext(), menu_search_list.class);
                        intent.putExtra("name", name);
                        intent.putExtra("code", code);
                        intent.putExtra("code_name", code_name);
                        intent.putExtra("zip", zip);
                        intent.putExtra("addr", addr);
                        intent.putExtra("tel", tel);
                        intent.putExtra("lat", lat);
                        intent.putExtra("lng", lng);
                        intent.putExtra("loginpatient", loginpatientVO);
                        startActivity(intent);
                    }
                    else
                    {
                        for (int n = 1; n < 72748; n++)
                        {
                            if (marker.getPosition().latitude == Double.parseDouble(list.get(n)[20]) && marker.getPosition().longitude == Double.parseDouble(list.get(n)[19]))
                            {
                                appointment_search_old.name = list.get(n)[1];
                                appointment_search_old.code = list.get(n)[2];
                                appointment_search_old.code_name = list.get(n)[3];
                                appointment_search_old.zip = list.get(n)[9];
                                appointment_search_old.addr = list.get(n)[10];
                                appointment_search_old.tel = list.get(n)[11];
                                appointment_search_old.page = list.get(n)[12];
                                appointment_search_old.doc_all = list.get(n)[14];
                                appointment_search_old.doc_normal = list.get(n)[15];
                                appointment_search_old.doc_Intern = list.get(n)[16];
                                appointment_search_old.doc_resi = list.get(n)[17];
                                appointment_search_old.doc_pro = list.get(n)[18];
                                appointment_search_old.lat = list.get(n)[20];
                                appointment_search_old.lng = list.get(n)[19];

                                break;
                            }
                        }

                        intent = new Intent(getApplicationContext(), menu_search_list.class);
                        intent.putExtra("name", name);
                        intent.putExtra("code", code);
                        intent.putExtra("code_name", code_name);
                        intent.putExtra("zip", zip);
                        intent.putExtra("addr", addr);
                        intent.putExtra("tel", tel);
                        intent.putExtra("page", page);
                        intent.putExtra("doc_all", doc_all);
                        intent.putExtra("doc_normal", doc_normal);
                        intent.putExtra("doc_intern", doc_Intern);
                        intent.putExtra("doc_resi", doc_resi);
                        intent.putExtra("doc_pro", doc_pro);
                        intent.putExtra("lat", lat);
                        intent.putExtra("lng", lng);
                        intent.putExtra("list2", list2);
                        intent.putExtra("loginpatient", loginpatientVO);
                        startActivity(intent);
                    }

                    return false;
                }
            });
        }


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                select = adapterView.getItemAtPosition(i).toString();


                switch (select) {
                    case "병원":
                        //카메라 이동할 때
                    {
                        googleMap.clear();
                        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                            @Override
                            public void onCameraIdle() {
                                // Cleaning all the markers.
                                googleMap.clear();
                                LatLng point = googleMap.getCameraPosition().target;

                                try {
                                    InputStreamReader is = new InputStreamReader(getResources().openRawResource(R.raw.hospital), "euc-kr");
                                    BufferedReader reader = new BufferedReader(is);
                                    CSVReader read = new CSVReader(reader);
                                    list = read.readAll();

                                    for (int n = 1; n < 72747; n++) {
                                        if ((point.latitude - 0.03 < Double.parseDouble(list.get(n)[20])) && (Double.parseDouble(list.get(n)[20]) < point.latitude + 0.03) &&
                                                (point.longitude - 0.03 < Double.parseDouble(list.get(n)[19])) && (Double.parseDouble(list.get(n)[19]) < point.longitude + 0.03)) {
                                            makerOptions = new MarkerOptions();
                                            makerOptions
                                                    .position(new LatLng(Double.parseDouble(list.get(n)[20]), Double.parseDouble(list.get(n)[19])))
                                                    .title(list.get(n)[1]);
                                            if(Double.compare(Math.round(Double.parseDouble(list.get(n)[20])*100)/100.0,Math.round(point.latitude*100)/100.0 ) == 0 &&Double.compare(Math.round(Double.parseDouble(list.get(n)[19])*100)/100.0,Math.round(point.longitude*100)/100.0 ) == 0){
                                                googleMap.addMarker(makerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.maker1))));
                                            }
                                            else{
                                                googleMap.addMarker(makerOptions);
                                            }
                                        }
                                    }

                                    editresult.setText(" ' "+editSearch.getText().toString()+" ' 검색결과 입니다.");

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    }

                    //검색
                    {
                        for (int n = 1; n < 72748; n++)
                            lists.add(list.get(n)[1]);

                        arraylist = new ArrayList<String>();
                        arraylist.addAll(lists);

                        adapter = new SearchAdapter(lists, getApplicationContext());
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
                                editSearch.setText(lists.get(position));
                                listView.setVisibility(View.GONE);
                            }
                        });

                        lists.clear();
                    }

                    //클릭
                    {
                        ib.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (int n = 1; n < 72748; n++)
                                {
                                    if (editSearch.getText().toString().equals(list.get(n)[1]))
                                    {
                                        appointment_search_old.lat = list.get(n)[20];
                                        appointment_search_old.lng = list.get(n)[19];
                                        break;
                                    }
                                }

                                listView.setVisibility(View.GONE);
                                LatLng positions = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positions, 16));
                            }
                        });
                    }
                    break;

                    case "약국":
                        //카메라 이동할 때
                    {
                        googleMap.clear();
                        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                            @Override
                            public void onCameraIdle() {
                                // Cleaning all the markers.
                                googleMap.clear();
                                LatLng point = googleMap.getCameraPosition().target;

                                try {
                                    InputStreamReader is2 = new InputStreamReader(getResources().openRawResource(R.raw.ph), "euc-kr");
                                    BufferedReader reader2 = new BufferedReader(is2);
                                    CSVReader read2 = new CSVReader(reader2);
                                    list2 = new ListVO(read2.readAll());

                                    for (int n2 = 1; n2 < 22942; n2++) {
                                        if ((point.latitude - 0.03 < Double.parseDouble(list2.getItem().get(n2)[14])) && (Double.parseDouble(list2.getItem().get(n2)[14]) < point.latitude + 0.03) &&
                                                (point.longitude - 0.03 < Double.parseDouble(list2.getItem().get(n2)[13])) && (Double.parseDouble(list2.getItem().get(n2)[13]) < point.longitude + 0.03)) {

                                            makerOptions = new MarkerOptions();
                                            makerOptions
                                                    .position(new LatLng(Double.parseDouble(list2.getItem().get(n2)[14]), Double.parseDouble(list2.getItem().get(n2)[13])))
                                                    .title(list2.getItem().get(n2)[1]);
                                            if(Double.compare(Math.round(Double.parseDouble(list2.getItem().get(n2)[14])*10000)/10000.0,Math.round(point.latitude*10000)/10000.0 ) == 0 &&Double.compare(Math.round(Double.parseDouble(list2.getItem().get(n2)[13])*10000)/10000.0,Math.round(point.longitude*10000)/10000.0 ) == 0){
                                                googleMap.addMarker(makerOptions.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.maker1))));
                                            }
                                            else{
                                                googleMap.addMarker(makerOptions);
                                            }
                                        }
                                    }


                                    editresult.setText(" ' "+editSearch.getText().toString()+" ' 검색결과 입니다.");
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                            }
                        });
                    }

                    //검색
                    {
                        for (int n = 1; n < 22942; n++)
                            lists.add(list2.getItem().get(n)[1]);

                        arraylist = new ArrayList<String>();
                        arraylist.addAll(lists);

                        adapter = new SearchAdapter(lists, getApplicationContext());
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
                                editSearch.setText(lists.get(position));
                                listView.setVisibility(View.GONE);
                            }
                        });

                        lists.clear();
                    }

                    //클릭
                    {
                        ib.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                for (int n = 1; n < 22942; n++)
                                {
                                    if (editSearch.getText().toString().equals(list2.getItem().get(n)[1]))
                                    {
                                        appointment_search_old.lat = list2.getItem().get(n)[14];
                                        appointment_search_old.lng = list2.getItem().get(n)[13];
                                        break;
                                    }
                                }

                                listView.setVisibility(View.GONE);

                                editresult.setText(" ' "+editSearch.getText().toString()+" ' 검색결과 입니다.");
                                editresult.setTextColor(getResources().getColor(R.color.green_color));
                                LatLng positions = new LatLng(Double.parseDouble(lat), Double.parseDouble(lng));
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(positions, 16));
                            }
                        });
                    }
                    break;

                    default:
                        googleMap.clear();
                        googleMap.setOnCameraIdleListener(new GoogleMap.OnCameraIdleListener() {
                            @Override
                            public void onCameraIdle() {
                                // Cleaning all the markers.
                                googleMap.clear();
                            }
                        });
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    // 검색을 수행하는 메소드
    public void search(String charText) {

        // 문자 입력시마다 리스트를 지우고 새로 뿌려준다.
        lists.clear();

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
                    lists.add(arraylist.get(i));
                }
            }
        }
        // 리스트 데이터가 변경되었으므로 아답터를 갱신하여 검색된 데이터를 화면에 보여준다.
        adapter.notifyDataSetChanged();
    }

    //액션바 버튼 클릭했을 때
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent(appointment_search_old.this, MainActivity.class);
                intent.putExtra("loginpatient",loginpatientVO);
                startActivity(intent);
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}
