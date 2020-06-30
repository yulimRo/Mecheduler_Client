package com.example.mecheduler;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

public class info_pharmacy extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;

    private Button btn_info_pharmacy, btn_info_time;

    private Menu menu;

    static String name, lat, lng, code, code_name, zip, addr, tel;

    private ImageButton btn_empty, btn_fill;

    private List<String[]> lists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_pharmacy);

        //액션바//
        {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btn_fill = (ImageButton) findViewById(R.id.btn_fill);
        btn_empty = (ImageButton) findViewById(R.id.btn_empty);
        btn_info_pharmacy = (Button)findViewById(R.id.btn_info_pharmacy);
        TextView tv = (TextView)findViewById(R.id.toolbar_title);
        TextView tv2 = (TextView)findViewById(R.id.head);
        TextView tv3 = (TextView)findViewById(R.id.body);

        Intent intent = getIntent();

        this.name = intent.getStringExtra("name");
        this.code = intent.getStringExtra("code");
        this.code_name = intent.getStringExtra("code_name");
        this.zip = intent.getStringExtra("zip");
        this.addr = intent.getStringExtra("addr");
        this.tel = intent.getStringExtra("tel");
        this.lat = intent.getStringExtra("lat");
        this.lng = intent.getStringExtra("lng");

        tv.setText(name);
        tv2.setText("");
        tv3.setText("");

        btn_empty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_empty.setVisibility(View.GONE);
                btn_fill.setVisibility(View.VISIBLE);
            }
        });

        btn_fill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btn_empty.setVisibility(View.VISIBLE);
                btn_fill.setVisibility(View.GONE);
            }
        });

        tv2.setText("약국 위치 안내");
        tv3.setText("우편 번호: " + zip +"\n주소: " + addr + "\n전화번호: " + tel);
    }

    //액션바 버튼 클릭
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

    public void onClick_question(View v)
    {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + tel));
        //Intent intent = new Intent(Intent.ACTION_CALL,Uri.parse("tel:12345"));
        startActivity(intent);

    }
}
