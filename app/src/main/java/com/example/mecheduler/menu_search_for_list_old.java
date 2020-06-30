package com.example.mecheduler;

import android.content.Intent;
import android.net.TrafficStats;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.mecheduler.DTO.PatientVO;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class menu_search_for_list_old extends AppCompatActivity {

    private Spinner spinnerOption, spinnerAddr1, spinnerAddr2;
    private EditText searchEdit;
    private ImageButton searchBtn;
    private ListView searchViewHp, searchViewHp2;
    private CustomAdapter customListadapter;
    private RadioGroup radioGroup;
    private RadioButton hopstionRdo, medicineRdo;

    private String location_option1, location_option2,choice_search_option; // 시/도 스피너에서 선택된 값,시/군/구 스피너에서 선택된 값,검색 옵션 스피너(이름,진료과목)
    private ArrayAdapter<String> adapter_spinner_location2;
    private static final int THREAD_ID = 10000;
    private int totalCount;

    private URL url;
    XmlPullParserFactory parserCreator;
    XmlPullParser parser;
    int parserEvent;
    DocumentBuilderFactory dbFactoty;
    DocumentBuilder dBuilder;
    Document doc;
    NodeList list;
    Element element;

    boolean initem = false, inDutyAddr = false, inDutyDiv = false, inDutyDivNam = false, inDutyEmcls = false;
    boolean inDutyEmclsNam = false, inDutyEryn = false, inDutyEtc = false, inDutyInf = false, inDutyMapimg = false;
    boolean inDutyName = false, inDutyTel1 = false, inDutyTel3 = false, inHpid = false, inPostCdn1 = false, inPostCdn2 = false;
    boolean inWgs84Lon = false, inWgs84Lat = false, inDgidIdName = false;

    String addr = null, dutyAddr = null, dutyDiv = null, dutyDivNam = null, dutyEmcls = null, dutyEmclsNam = null, dutyEryn = null, dutyEtc = null;
    String dutyInf = null, dutyMapimg = null, dutyName = null, dutyTel1 = null, dutyTel3 = null, hpid = null, postCdn1 = null, postCdn2 = null;
    private String wgs84Lon,wgs84Lat,dgidIdName;


    private Toolbar toolbar;
    private ActionBar actionBar;

    private DrawerLayout drawerLayout;
    private View drawerView;
    private PatientVO loginpatientVO; //로그인 회원

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_hospital_list);
        StrictMode.enableDefaults();
        TrafficStats.setThreadStatsTag(THREAD_ID);

        loginpatientVO = (PatientVO) getIntent().getParcelableExtra("loginpatient");  // 로그인 회원

        //액션바//
        {
            toolbar = (Toolbar) findViewById(R.id.toolbar_hospital_list);
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
//            searchBtn = (ImageButton) findViewById(R.id.main_search_Btn);

        }


        //작업 스레드들
        final SearchThread thread = new SearchThread();
        final SearchThread2 thread2 = new SearchThread2();
        final SearchThread3 thread3 = new SearchThread3();
        final SearchThread4 thread4 = new SearchThread4();


        final String[] search_kinds = {"검색옵션", "이름", "진료과목"};
        final String[] search_location1 = {"시/도", "서울특별시", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시", "울산광역시", "세종특별자치시", "강원도", "경기도", "경상남도", "경상북도", "전라남도", "제주특별자치도", "충청남도", "충청북도"};
        final List<String> search_location2 = new ArrayList<String>();

        searchViewHp = (ListView) findViewById(R.id.listView);  // 검색 리스트
        searchViewHp2 = (ListView) findViewById(R.id.listView2);  // 검색 리스트
        searchBtn = (ImageButton) findViewById(R.id.searchHpBtn); //검색 버튼
        searchEdit = (EditText) findViewById(R.id.edit_search_hp_info); //검색창
        spinnerOption = (Spinner) findViewById(R.id.search_hp_option); // 이름, 진료 과목 옵션 스피너
        spinnerAddr1 = (Spinner) findViewById(R.id.search_hp_location1); // 시/도 스피너
        spinnerAddr2 = (Spinner) findViewById(R.id.search_hp_location2); // 시/군/구 스피너
        customListadapter = new CustomAdapter();  //검색 리스트
        hopstionRdo = (RadioButton) findViewById(R.id.hospitalRdo);
        medicineRdo = (RadioButton) findViewById(R.id.medicineRdo);
        radioGroup = (RadioGroup) findViewById(R.id.rdoGroupd);
//        butBack = (Button) findViewById(R.id.btn_back);

        ArrayAdapter<String> adapter_spinner_option = new ArrayAdapter(this, android.R.layout.simple_spinner_item, search_kinds);
        spinnerOption.setAdapter(adapter_spinner_option);
        ArrayAdapter<String> adapter_spinner_location1 = new ArrayAdapter(this, android.R.layout.simple_spinner_item, search_location1);
        spinnerAddr1.setAdapter(adapter_spinner_location1);

        searchViewHp.setVisibility(View.VISIBLE);
        searchViewHp2.setVisibility(View.GONE);


//        butBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(getApplicationContext(), MainActivity.class));
//            }
//        });

        hopstionRdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customListadapter = new CustomAdapter();
                searchViewHp.setAdapter(customListadapter);
                searchViewHp.setVisibility(View.VISIBLE);
                searchViewHp2.setVisibility(View.GONE);
            }
        });

        medicineRdo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                customListadapter = new CustomAdapter();
                searchViewHp2.setAdapter(customListadapter);
                searchViewHp2.setVisibility(View.VISIBLE);
                searchViewHp.setVisibility(View.GONE);
            }
        });

        spinnerAddr1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override   // position 으로 몇번째 것이 선택됬는지 값을 넘겨준다
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location_option1 = search_location1[position];
                try {
                    search_location2.clear();

                    // city.xml에서 시/구/군 값 불러오기
                    InputStream is = getBaseContext().getResources().getAssets().open("city.xls");
                    Workbook wb = Workbook.getWorkbook(is);
                    if (wb != null) {
                        Sheet sheet = wb.getSheet(0);   // 시트 불러오기
                        if (sheet != null) {
                            int colTotal = sheet.getColumns();    // 전체 컬럼
                            int rowIndexStart = 1;                  // row 인덱스 시작
                            int rowTotal = sheet.getColumn(colTotal - 1).length;

                            for (int row = rowIndexStart; row < rowTotal; row++) {
                                // 2행 값이 시/도 스피너의 값과 같을 때
                                if (sheet.getCell(2, row).getContents().equals(location_option1)) {
                                    for (int col = 0; col < colTotal; col++) {
                                        if (col == 4) {
                                            if (sheet.getCell(6, row).getContents().equals("") && !sheet.getCell(4, row).getContents().equals("")) {
                                                if (sheet.getCell(4, row).getContents().equals("권선구") || sheet.getCell(4, row).getContents().equals("장안구") || sheet.getCell(4, row).getContents().equals("팔달구") || sheet.getCell(4, row).getContents().equals("영통구")) {
                                                    String contents = sheet.getCell(col, row).getContents();
                                                    search_location2.add("수원시" + contents);
                                                } else if (sheet.getCell(4, row).getContents().equals("수정구") || sheet.getCell(4, row).getContents().equals("분당구") || sheet.getCell(4, row).getContents().equals("중원구")) {
                                                    String contents = sheet.getCell(col, row).getContents();
                                                    search_location2.add("성남시" + contents);
                                                } else if (sheet.getCell(4, row).getContents().equals("처인구") || sheet.getCell(4, row).getContents().equals("기흥구") || sheet.getCell(4, row).getContents().equals("수지구")) {
                                                    String contents = sheet.getCell(col, row).getContents();
                                                    search_location2.add("용인시" + contents);
                                                } else if (sheet.getCell(4, row).getContents().equals("상록구") || sheet.getCell(4, row).getContents().equals("단원구") || sheet.getCell(4, row).getContents().equals("중원구")) {
                                                    String contents = sheet.getCell(col, row).getContents();
                                                    search_location2.add("안산시" + contents);
                                                } else if (sheet.getCell(4, row).getContents().equals("만원구") || sheet.getCell(4, row).getContents().equals("동안구")) {
                                                    String contents = sheet.getCell(col, row).getContents();
                                                    search_location2.add("안양시" + contents);
                                                } else if (sheet.getCell(4, row).getContents().equals("덕양구") || sheet.getCell(4, row).getContents().equals("일산동구") || sheet.getCell(4, row).getContents().equals("일산서구")) {
                                                    String contents = sheet.getCell(col, row).getContents();
                                                    search_location2.add("고양시" + contents);
                                                } else if (sheet.getCell(4, row).getContents().equals("수원시") || sheet.getCell(4, row).getContents().equals("안산시") || sheet.getCell(4, row).getContents().equals("용인시") || sheet.getCell(4, row).getContents().equals("고양시") || sheet.getCell(4, row).getContents().equals("안양시") || sheet.getCell(4, row).getContents().equals("성남시")) {
                                                } else {
                                                    String contents = sheet.getCell(col, row).getContents();
                                                    search_location2.add(contents);
                                                }
                                            }
                                        }
                                    }
                                }

                            }
                        }

                    }
                    adapter_spinner_location2 = new ArrayAdapter(menu_search_for_list_old.this, android.R.layout.simple_list_item_1, search_location2.toArray(new String[search_location2.size()]));
                    spinnerAddr2.setAdapter(adapter_spinner_location2);

                } catch (IOException e) {
                    e.printStackTrace();
                } catch (BiffException e) {
                    e.printStackTrace();
                }

            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                searchEdit.setText("아무것도 선택되지 않았습니다");
            }
        }); // spinnerAddr1.selectListener 종료


        //검색옵션 선택시
        spinnerOption.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override   // position 으로 몇번째 것이 선택됬는지 값을 넘겨준다
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choice_search_option = search_kinds[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                searchEdit.setText("아무것도 선택되지 않았습니다");
            }
        });

        //검색옵션 선택시
        spinnerAddr2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override   // position 으로 몇번째 것이 선택됬는지 값을 넘겨준다
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                location_option2 = search_location2.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                searchEdit.setText("아무것도 선택되지 않았습니다");
            }
        });


        searchBtn.setOnClickListener(new View.OnClickListener() {


            List<String> titleList = new ArrayList<String>();
            List<String> contentList = new ArrayList<String>();
            List<String> search_object_list = new ArrayList<String>();
            List<String> hpMapimgList = new ArrayList<String>();
            List<String> hpTelList = new ArrayList<String>();
            List<String> hpidList = new ArrayList<String>();
            int ishospital = 0;
            int ismedecine = 0;

            @Override
            public void onClick(View v) {


                try {


                    switch (radioGroup.getCheckedRadioButtonId()) {
                        case R.id.hospitalRdo:
                            ishospital = 1;
                            ismedecine = 0;
                            break;
                        case R.id.medicineRdo:
                            ismedecine = 1;
                            ishospital = 0;
                            break;
                    }
                    if (ishospital == 1) {

                        search_object_list.clear();
                        titleList.clear();
                        contentList.clear();
                        hpMapimgList.clear();
                        hpTelList.clear();
                        hpidList.clear();

                        customListadapter = new CustomAdapter();

                        URL url = null;
                        if (choice_search_option.equals("이름") && searchEdit.getText().length() == 0) {
                            Toast.makeText(menu_search_for_list_old.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                        } else if (choice_search_option.equals("이름")) {
                            if (location_option1.equals("시/도") && searchEdit.getText().length() != 0) {
                                url = new URL("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=1PR4zbwEU588IOn%2Bpvt9JkwKc5heAwcp3PCTTagAoyoarqWY8puMYz97T0xo6X50PmCFV2RfbzwU3mjpanL48A%3D%3D&QN=" + URLEncoder.encode(searchEdit.getText().toString(), "UTF-8")); //검색 URL부분
                            } else
                                url = new URL("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=1PR4zbwEU588IOn%2Bpvt9JkwKc5heAwcp3PCTTagAoyoarqWY8puMYz97T0xo6X50PmCFV2RfbzwU3mjpanL48A%3D%3D&Q0=" + URLEncoder.encode(location_option1, "UTF-8") + "&Q1=" + URLEncoder.encode(location_option2, "UTF-8") + "&QN=" + URLEncoder.encode(searchEdit.getText().toString(), "UTF-8"));
                        } else if (choice_search_option.equals("검색옵션") && location_option1.equals("시/도")) {
                            Toast.makeText(menu_search_for_list_old.this, "검색 옵션을 선택해주세요", Toast.LENGTH_SHORT).show();
                        } else if (choice_search_option.equals("검색옵션") && !location_option1.equals("시/도")) {
                            url = new URL("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=1PR4zbwEU588IOn%2Bpvt9JkwKc5heAwcp3PCTTagAoyoarqWY8puMYz97T0xo6X50PmCFV2RfbzwU3mjpanL48A%3D%3D&Q0=" + URLEncoder.encode(location_option1, "UTF-8") + "&Q1=" + URLEncoder.encode(location_option2, "UTF-8"));
                        } else if (choice_search_option.equals("진료과목") && !searchEdit.getText().equals("")) {
                            if (location_option1.equals("시/도"))
                                url = new URL("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=1PR4zbwEU588IOn%2Bpvt9JkwKc5heAwcp3PCTTagAoyoarqWY8puMYz97T0xo6X50PmCFV2RfbzwU3mjpanL48A%3D%3D"); //검색 URL부분
                            else
                                url = new URL("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlMdcncListInfoInqire?serviceKey=1PR4zbwEU588IOn%2Bpvt9JkwKc5heAwcp3PCTTagAoyoarqWY8puMYz97T0xo6X50PmCFV2RfbzwU3mjpanL48A%3D%3D&Q0=" + URLEncoder.encode(location_option1, "UTF-8") + "&Q1=" + URLEncoder.encode(location_option2, "UTF-8"));
                        }

                        parserCreator = XmlPullParserFactory.newInstance();
                        parser = parserCreator.newPullParser();

                        dbFactoty = DocumentBuilderFactory.newInstance();
                        dBuilder = dbFactoty.newDocumentBuilder();
                        doc = dBuilder.parse(String.valueOf(url));
                        doc.getChildNodes().item(0).getLastChild();
                        doc.getDocumentElement().normalize();
                        list = doc.getDocumentElement().getElementsByTagName("body");
                        for (int i = 0; i < list.getLength(); i++) {
                            element = (Element) list.item(i);
                            // log 태그의 day취득
                        }
                        totalCount = Integer.parseInt(element.getLastChild().getTextContent());
                        url = new URL(url + "&numOfRows=" + totalCount);
                        System.out.println(url);

                        parser.setInput(url.openStream(), null);

                        parserEvent = parser.getEventType();
                        System.out.println("파싱시작합니다.");


                        if (choice_search_option.equals("이름") || choice_search_option.equals("검색옵션")) {
                            thread.run(contentList, titleList, hpMapimgList, hpTelList, hpidList);
                            setData(contentList, titleList, hpMapimgList, hpTelList, hpidList);
                            searchViewHp.setAdapter(customListadapter);
                        }

                        if (choice_search_option.equals("진료과목")) {
                            thread2.run(search_object_list);


                            for (int i = 0; i < totalCount; i++) {

                                url = new URL("http://apis.data.go.kr/B552657/HsptlAsembySearchService/getHsptlBassInfoInqire?serviceKey=1PR4zbwEU588IOn%2Bpvt9JkwKc5heAwcp3PCTTagAoyoarqWY8puMYz97T0xo6X50PmCFV2RfbzwU3mjpanL48A%3D%3D&HPID=" + search_object_list.get(i)); //검색 URL부분

                                parserCreator = XmlPullParserFactory.newInstance();
                                parser = parserCreator.newPullParser();

                                parser.setInput(url.openStream(), null);

                                parserEvent = parser.getEventType();

                                thread3.run(contentList, titleList, hpMapimgList, hpTelList, hpidList);
                                setData(contentList, titleList, hpMapimgList, hpTelList, hpidList);
                                searchViewHp.setAdapter(customListadapter);
                            }
                        }
                    } else {

                        search_object_list.clear();
                        titleList.clear();
                        contentList.clear();
                        hpMapimgList.clear();
                        hpTelList.clear();
                        hpidList.clear();

                        customListadapter = new CustomAdapter();

                        URL url = null;
                        if (choice_search_option.equals("이름") && searchEdit.getText().length() == 0) {
                            Toast.makeText(menu_search_for_list_old.this, "이름을 입력하세요", Toast.LENGTH_SHORT).show();
                        } else if (choice_search_option.equals("이름")) {
                            if (location_option1.equals("시/도") && searchEdit.getText().length() != 0) {
                                url = new URL("http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire?serviceKey=1PR4zbwEU588IOn%2Bpvt9JkwKc5heAwcp3PCTTagAoyoarqWY8puMYz97T0xo6X50PmCFV2RfbzwU3mjpanL48A%3D%3D&QN=" + URLEncoder.encode(searchEdit.getText().toString(), "UTF-8")); //검색 URL부분
                            } else
                                url = new URL("http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire?serviceKey=1PR4zbwEU588IOn%2Bpvt9JkwKc5heAwcp3PCTTagAoyoarqWY8puMYz97T0xo6X50PmCFV2RfbzwU3mjpanL48A%3D%3D&Q0=" + URLEncoder.encode(location_option1, "UTF-8") + "&Q1=" + URLEncoder.encode(location_option2, "UTF-8") + "&QN=" + URLEncoder.encode(searchEdit.getText().toString(), "UTF-8"));
                        } else if (choice_search_option.equals("검색옵션") && location_option1.equals("시/도")) {
                            Toast.makeText(menu_search_for_list_old.this, "검색 옵션을 선택해주세요", Toast.LENGTH_SHORT).show();
                        } else if (choice_search_option.equals("검색옵션") && !location_option1.equals("시/도")) {
                            url = new URL("http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire?serviceKey=1PR4zbwEU588IOn%2Bpvt9JkwKc5heAwcp3PCTTagAoyoarqWY8puMYz97T0xo6X50PmCFV2RfbzwU3mjpanL48A%3D%3D&Q0=" + URLEncoder.encode(location_option1, "UTF-8") + "&Q1=" + URLEncoder.encode(location_option2, "UTF-8"));
                        } else if (choice_search_option.equals("진료과목") && !searchEdit.getText().equals("")) {
                            if (location_option1.equals("시/도"))
                                url = new URL("http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire?serviceKey=1PR4zbwEU588IOn%2Bpvt9JkwKc5heAwcp3PCTTagAoyoarqWY8puMYz97T0xo6X50PmCFV2RfbzwU3mjpanL48A%3D%3D"); //검색 URL부분
                            else
                                url = new URL("http://apis.data.go.kr/B552657/ErmctInsttInfoInqireService/getParmacyListInfoInqire?serviceKey=1PR4zbwEU588IOn%2Bpvt9JkwKc5heAwcp3PCTTagAoyoarqWY8puMYz97T0xo6X50PmCFV2RfbzwU3mjpanL48A%3D%3D&Q0=" + URLEncoder.encode(location_option1, "UTF-8") + "&Q1=" + URLEncoder.encode(location_option2, "UTF-8"));
                        }

                        parserCreator = XmlPullParserFactory.newInstance();
                        parser = parserCreator.newPullParser();

                        dbFactoty = DocumentBuilderFactory.newInstance();
                        dBuilder = dbFactoty.newDocumentBuilder();
                        doc = dBuilder.parse(String.valueOf(url));
                        doc.getChildNodes().item(0).getLastChild();
                        doc.getDocumentElement().normalize();
                        list = doc.getDocumentElement().getElementsByTagName("body");
                        for (int i = 0; i < list.getLength(); i++) {
                            element = (Element) list.item(i);
                            // log 태그의 day취득
                        }
                        totalCount = Integer.parseInt(element.getLastChild().getTextContent());
                        url = new URL(url + "&numOfRows=" + totalCount);
                        System.out.println(url);

                        parser.setInput(url.openStream(), null);

                        parserEvent = parser.getEventType();
                        System.out.println("파싱시작합니다.");


                        if (choice_search_option.equals("이름") || choice_search_option.equals("검색옵션")) {
                            thread4.run(contentList, titleList, hpMapimgList, hpTelList, hpidList);

                            setData(contentList, titleList, hpMapimgList, hpTelList, hpidList);
                            searchViewHp2.setAdapter(customListadapter);
                        }


                    }
                } catch (Exception e) {
//                    tv1.setText("에러가..났습니다...");
                }


                searchViewHp.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(getApplicationContext(), info_hospital.class);
                        intent.putExtra("find_hpid", hpidList.get(position));
                        intent.putExtra("loginpatient", loginpatientVO);
                        intent.putExtra("seac","2");
                        startActivity(intent);


                    }
                });

                searchViewHp2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                        Intent intent = new Intent(getApplicationContext(), info_medicine.class);
                        intent.putExtra("find_medi", hpidList.get(position));
                        intent.putExtra("loginpatient", loginpatientVO);
                        startActivity(intent);


                    }
                });
            }

        });


    }

    //검색리스트의 어뎁터에 지금까지 검색된 결과 저장
    private void setData(List<String> contentList, List<String> titleList, List<String> hpMapimgList, List<String> hpTelList, List<String> hpidList) {


        for (int i = 0; i < contentList.size(); i++) {
            CustomDTO dto = new CustomDTO();
            dto.setHpName(titleList.get(i));
            dto.setHpAddr(contentList.get(i));
            System.out.println(i + ":" + hpMapimgList.get(i));
            dto.setHpMapImg(hpMapimgList.get(i));
            dto.setHpTel1(hpTelList.get(i));
            dto.setHpid(hpidList.get(i));

            customListadapter.addItem(dto);
        }

    }

    private class SearchThread extends Thread {
        private static final String TAG = "ExampleThread";

        public SearchThread() { // 초기화 작업
        }

        public void run(List<String> contentList, List<String> titleList, List<String> hpMapimgList, List<String> hpTelList, List<String> hpidList) {

            try {
                dutyMapimg = " ";
                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                            if (parser.getName().equals("dutyAddr")) { //주
                                inDutyAddr = true;
                            }
                            if (parser.getName().equals("dutyName")) { //기관명
                                inDutyName = true;
                            }
                            if (parser.getName().equals("dutyTel1")) { //전화
                                inDutyTel1 = true;
                            }
                            if (parser.getName().equals("dutyMapimg")) { //간이약도
                                inDutyMapimg = true;
                            }
                            if (parser.getName().equals("hpid")) {
                                inHpid = true;
                            }
                            break;

                        case XmlPullParser.TEXT://parser가 내용에 접근했을때

                            if (inDutyAddr) {  //주소
                                dutyAddr = parser.getText();
                                inDutyAddr = false;
                            }
                            if (inDutyName) { //isMapy이 true일 때 태그의 내용을 저장.
                                dutyName = parser.getText();
                                inDutyName = false;
                            }
                            if (inHpid) { //isMapy이 true일 때 태그의 내용을 저장.
                                hpid = parser.getText();
                                inHpid = false;
                            }

                            if (inDutyTel1) {  //주소
                                dutyTel1 = parser.getText();
                                inDutyTel1 = false;
                            }
                            if (inDutyMapimg) {
                                dutyMapimg = parser.getText();
                                //isMapy이 true일 때 태그의 내용을 저장.
                                inDutyMapimg = false;
                            }

                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("item")) {
                                if (dutyAddr != null)
                                    titleList.add(dutyAddr);
                                if (dutyName != null)
                                    contentList.add(dutyName);
                                if (dutyTel1 != null)
                                    hpTelList.add(dutyTel1);
                                if (hpid != null)
                                    hpidList.add(hpid);
                                if (dutyMapimg != null)
                                    hpMapimgList.add(dutyMapimg);
                                initem = false;
                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (Exception e) {

            }

        }
    }

    private class SearchThread4 extends Thread {
        private static final String TAG = "ExampleThread";

        public SearchThread4() { // 초기화 작업
        }

        public void run(List<String> contentList, List<String> titleList, List<String> hpMapimgList, List<String> hpTelList, List<String> hpidList) {

            try {
                dutyMapimg = " ";
                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행
                            if (parser.getName().equals("dutyAddr")) { //주
                                inDutyAddr = true;
                            }
                            if (parser.getName().equals("dutyName")) { //기관명
                                inDutyName = true;
                            }
                            if (parser.getName().equals("dutyTel1")) { //전화
                                inDutyTel1 = true;
                            }
                            if (parser.getName().equals("dutyMapimg")) { //간이약도
                                inDutyMapimg = true;
                            }
                            if (parser.getName().equals("hpid")) {
                                inHpid = true;
                            }
                            break;

                        case XmlPullParser.TEXT://parser가 내용에 접근했을때

                            if (inDutyAddr) {  //주소
                                dutyAddr = parser.getText();
                                inDutyAddr = false;
                            }
                            if (inDutyName) { //isMapy이 true일 때 태그의 내용을 저장.
                                dutyName = parser.getText();
                                inDutyName = false;
                            }
                            if (inHpid) { //isMapy이 true일 때 태그의 내용을 저장.
                                hpid = parser.getText();
                                inHpid = false;
                            }

                            if (inDutyTel1) {  //주소
                                dutyTel1 = parser.getText();
                                inDutyTel1 = false;
                            }
                            if (inDutyMapimg) {
                                dutyMapimg = parser.getText();
                                //isMapy이 true일 때 태그의 내용을 저장.
                                inDutyMapimg = false;
                            }

                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("item")) {
                                if (dutyAddr != null)
                                    titleList.add(dutyAddr);
                                if (dutyName != null)
                                    contentList.add(dutyName);
                                if (dutyTel1 != null)
                                    hpTelList.add(dutyTel1);
                                if (hpid != null)
                                    hpidList.add(hpid);
                                if (dutyMapimg != null)
                                    hpMapimgList.add(dutyMapimg);
                                initem = false;
                            }
                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (Exception e) {

            }

        }
    }

    private class SearchThread2 extends Thread {
        private static final String TAG = "ExampleThread";

        public SearchThread2() { // 초기화 작업
        }

        public void run(List<String> search_object_list) {

            try {
                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행

                            if (parser.getName().equals("dutyAddr")) { //기관ID
                                inDutyAddr = true;
                            }
                            if (parser.getName().equals("dutyName")) { //기관ID
                                inDutyName = true;
                            }

                            if (parser.getName().equals("hpid")) { //기관ID
                                inHpid = true;
                            }


                            break;

                        case XmlPullParser.TEXT://parser가 내용에 접근했을때

                            if (inDutyAddr) { //isMapy이 true일 때 태그의 내용을 저장.
                                dutyAddr = parser.getText();
                                inDutyAddr = false;
                            }
                            if (inHpid) { //isMapy이 true일 때 태그의 내용을 저장.
                                hpid = parser.getText();
                                inHpid = false;
                            }
                            if (inDutyName) { //isMapy이 true일 때 태그의 내용을 저장.
                                dutyName = parser.getText();
                                inDutyName = false;
                            }

                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("item")) {
                                System.out.println(dutyName);
                                search_object_list.add(hpid);

                                initem = false;
                            }


                            break;
                    }
                    parserEvent = parser.next();
                }
            } catch (Exception e) {

            }

        }
    }

    private class SearchThread3 extends Thread {
        private static final String TAG = "ExampleThread";

        public SearchThread3() { // 초기화 작업
        }

        public void run(List<String> contentList, List<String> titleList, List<String> hpMapimgList, List<String> hpTelList, List<String> hpidList) {

            try {
                dutyMapimg = " ";
                while (parserEvent != XmlPullParser.END_DOCUMENT) {
                    switch (parserEvent) {
                        case XmlPullParser.START_TAG://parser가 시작 태그를 만나면 실행

                            if (parser.getName().equals("dutyAddr")) { //기관ID
                                inDutyAddr = true;
                            }
                            if (parser.getName().equals("hpid")) { //기관ID
                                inHpid = true;
                            }
                            if (parser.getName().equals("dutyName")) { //기관ID
                                inDutyName = true;
                            }
                            if (parser.getName().equals("dgidIdName")) { //기관ID
                                inDgidIdName = true;
                            }
                            if (parser.getName().equals("dutyTel1")) { //기관ID
                                inDutyTel1 = true;
                            }
                            if (parser.getName().equals("dutyMapimg")) { //기관ID
                                inDutyMapimg = true;
                            }

                            break;

                        case XmlPullParser.TEXT://parser가 내용에 접근했을때

                            if (inDutyAddr) { //isMapy이 true일 때 태그의 내용을 저장.
                                dutyAddr = parser.getText();
                                inDutyAddr = false;
                            }
                            if (inHpid) { //isMapy이 true일 때 태그의 내용을 저장.
                                hpid = parser.getText();
                                inHpid = false;
                            }
                            if (inDutyName) { //isMapy이 true일 때 태그의 내용을 저장.
                                dutyName = parser.getText();
                                inDutyName = false;
                            }
                            if (inDgidIdName) { //isMapy이 true일 때 태그의 내용을 저장.
                                dgidIdName = parser.getText();
                                inDgidIdName = false;
                            }
                            if (inDutyMapimg) { //isMapy이 true일 때 태그의 내용을 저장.
                                dutyMapimg = parser.getText();
                                inDutyMapimg = false;
                            }
                            if (inDutyTel1) { //isMapy이 true일 때 태그의 내용을 저장.
                                dutyTel1 = parser.getText();
                                inDutyTel1 = false;
                            }
                            break;
                        case XmlPullParser.END_TAG:
                            if (parser.getName().equals("item")) {
                                if (dgidIdName != null && dgidIdName.matches(".*" + searchEdit.getText().toString() + ".*")) {
                                    if (dutyAddr != null)
                                        titleList.add(dutyAddr);
                                    if (dutyName != null)
                                        contentList.add(dutyName);
                                    if (hpid != null)
                                        hpidList.add(hpid);
                                    if (dutyMapimg != null)
                                        hpMapimgList.add(dutyMapimg);
                                    if (dutyMapimg == null)
                                        hpMapimgList.add("");
                                    if (dutyTel1 != null)
                                        hpTelList.add(dutyTel1);
                                    initem = false;
                                } else {
                                }

                            }
                            break;

                    }
                    parserEvent = parser.next();
                }
            } catch (Exception e) {

            }

        }
    }

    //액션버튼을 클릭했을 때
    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();

        switch(id)
        {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("loginpatient",loginpatientVO);
                startActivityForResult(intent, 101);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }


}