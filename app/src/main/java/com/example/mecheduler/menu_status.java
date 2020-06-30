package com.example.mecheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class menu_status extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;

    private Button btn;
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_status);

        // 액션바 //
        {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        btn = (Button)findViewById(R.id.btn);
        tv = (TextView)findViewById(R.id.tv);

        btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                menu_status_sub n_layout = new menu_status_sub(getApplicationContext());
                LinearLayout con = (LinearLayout)findViewById(R.id.con);

                tv.setVisibility(View.GONE);
                con.setVisibility(View.VISIBLE);

                ImageButton btn_cancel = (ImageButton)n_layout.findViewById(R.id.btn_cancel);
                Button btn_cancel2 = (Button)n_layout.findViewById(R.id.btn_cancel2);

                btn_cancel.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), menu_status_cancel.class));
                    }
                });

                btn_cancel2.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(getApplicationContext(), menu_status_cancel.class));
                    }
                });

                con.addView(n_layout);
            }
        });
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
                startActivityForResult(intent, 101);
                finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
