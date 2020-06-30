package com.example.mecheduler;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.mecheduler.DTO.ListVO;
import com.example.mecheduler.DTO.PatientVO;

import java.util.List;

public class menu_search_list extends Activity {

    static String name, lat, lng, code, code_name, zip, addr, tel, page, doc_all, doc_normal, doc_Intern, doc_resi, doc_pro,sendmsg;
    static TextView tv, tv2, tv3,tv4;
    private LinearLayout ll;
    private ImageView iv,iv2;
    private ListVO list2;
    private PatientVO patientVO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.menu_list);



        patientVO = (PatientVO) getIntent().getParcelableExtra("loginpatient");

        //크기 조절
        {
            WindowManager.LayoutParams params = this.getWindow().getAttributes();
            params.x = 0;
            params.y = 700;
            params.width = WindowManager.LayoutParams.MATCH_PARENT;
            params.height = WindowManager.LayoutParams.WRAP_CONTENT;

            this.getWindow().setAttributes(params);
        }

        Intent intent = getIntent();

        tv = (TextView) findViewById(R.id.text1);
        tv2 = (TextView) findViewById(R.id.text2);
        tv3 = (TextView) findViewById(R.id.text3);
        tv4 = (TextView) findViewById(R.id.info);
        iv = (ImageView) findViewById(R.id.iv);
        iv2 = (ImageButton) findViewById(R.id.telhp);

        if (intent.getStringExtra("name").contains("약국")) {
            iv.setBackgroundResource(R.drawable.phicon);

            int color = getResources().getColor(R.color.ph_color);
            tv4.setTextColor(color);
            tv4.setText("약국 정보 보러가기 ");
            menu_search_list.name = intent.getStringExtra("name");
            menu_search_list.code = intent.getStringExtra("code");
            menu_search_list.code_name = intent.getStringExtra("code_name");
            menu_search_list.zip = intent.getStringExtra("zip");
            menu_search_list.addr = intent.getStringExtra("addr");
            menu_search_list.tel = intent.getStringExtra("tel");
            menu_search_list.lat = intent.getStringExtra("lat");
            menu_search_list.lng = intent.getStringExtra("lng");
        } else {
            int color = getResources().getColor(R.color.colorAccent);
            tv4.setTextColor(color);
            iv.setBackgroundResource(R.drawable.hospital);
            menu_search_list.name = intent.getStringExtra("name");
            menu_search_list.code = intent.getStringExtra("code");
            menu_search_list.code_name = intent.getStringExtra("code_name");
            menu_search_list.zip = intent.getStringExtra("zip");
            menu_search_list.addr = intent.getStringExtra("addr");
            menu_search_list.tel = intent.getStringExtra("tel");
            menu_search_list.page = intent.getStringExtra("page");
            menu_search_list.doc_all = intent.getStringExtra("doc_all");
            menu_search_list.doc_normal = intent.getStringExtra("doc_normal");
            menu_search_list.doc_Intern = intent.getStringExtra("doc_intern");
            menu_search_list.doc_resi = intent.getStringExtra("doc_resi");
            menu_search_list.doc_pro = intent.getStringExtra("doc_pro");
            menu_search_list.lat = intent.getStringExtra("lat");
            menu_search_list.lng = intent.getStringExtra("lng");
            sendmsg = "A2100004";

        }

        tv.setText(name);
        tv2.setText(addr);
        tv3.setText(tel);

        //인텐트 전송
        ll = (LinearLayout) findViewById(R.id.hospital);
        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tv.getText().toString().contains("약국")) {
                    Intent intent = new Intent(getApplicationContext(), info_medicine.class);
                    intent.putExtra("name", name);
                    intent.putExtra("code", code);
                    intent.putExtra("code_name", code_name);
                    intent.putExtra("zip", zip);
                    intent.putExtra("addr", addr);
                    intent.putExtra("tel", tel);
                    intent.putExtra("lat", lat);
                    intent.putExtra("lng", lng);
                    intent.putExtra("loginpatient", patientVO);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(getApplicationContext(), info_hospital.class);
//                    intent.putExtra("name", name);
//                    intent.putExtra("code", code);
//                    intent.putExtra("code_name", code_name);
//                    intent.putExtra("zip", zip);
//                    intent.putExtra("addr", addr);
//                    intent.putExtra("tel", tel);
                    intent.putExtra("page", page);
//                    intent.putExtra("doc_all", doc_all);
//                    intent.putExtra("doc_normal", doc_normal);
//                    intent.putExtra("doc_intern", doc_Intern);
//                    intent.putExtra("doc_resi", doc_resi);
//                    intent.putExtra("doc_pro", doc_pro);
//                    intent.putExtra("lat", lat);
//                    intent.putExtra("lng", lng);
//                    intent.putExtra("list2", list2);
                    sendmsg = "A2100004";
                    intent.putExtra("hp", sendmsg);
                    intent.putExtra("seac","1");
                    intent.putExtra("loginpatient", patientVO);
                    startActivity(intent);
                }
            }
        });

        iv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:"+tel);
                Intent intent = new Intent(Intent.ACTION_DIAL, uri);
                startActivity(intent);
            }
        });

    }
}
