package com.example.mecheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.example.mecheduler.R;

public class tab_guide extends Activity implements View.OnClickListener
{
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tab_guide_detail);
    }

    public void onClick(View v)
    {

    }
}
