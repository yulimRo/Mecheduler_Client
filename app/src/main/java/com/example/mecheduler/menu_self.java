package com.example.mecheduler;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class menu_self extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_self);

        // 액션바 //
        {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
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
                finish();
        }

        return super.onOptionsItemSelected(item);
    }

    public void onClick_head(View v)
    {
        startActivity(new Intent(getApplicationContext(), head.class));
    }

    public void onClick_neck(View v)
    {
        startActivity(new Intent(getApplicationContext(), neck.class));
    }

    public void onClick_shoulder(View v)
    {
        startActivity(new Intent(getApplicationContext(), shoulder.class));
    }

    public void onClick_body(View v)
    {
        startActivity(new Intent(getApplicationContext(), body.class));
    }

    public void onClick_waist(View v)
    {
        startActivity(new Intent(getApplicationContext(), waist.class));
    }

    public void onClick_knee(View v)
    {
        startActivity(new Intent(getApplicationContext(), knee.class));
    }

    public void onClick_wrist(View v)
    {
        startActivity(new Intent(getApplicationContext(), wrist.class));
    }

    public void onClick_more(View v)
    {
        startActivity(new Intent(getApplicationContext(), more.class));
    }
}
