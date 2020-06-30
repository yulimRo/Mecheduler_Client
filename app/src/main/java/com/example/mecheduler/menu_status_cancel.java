package com.example.mecheduler;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

public class menu_status_cancel extends Activity implements View.OnClickListener
{
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.tab_status_cancel);

        findViewById(R.id.btn_no).setOnClickListener(this);
        findViewById(R.id.btn_yes).setOnClickListener(this);
    }

    public void onClick(View v)
    {
       if(v.getId() == R.id.btn_yes)
       {
           intent = new Intent(getApplicationContext(), menu_appointment.class);
           intent.putExtra("yes", "1");
           startActivityForResult(intent, 101);
           finish();
       }
       else
           finish();
    }
}
