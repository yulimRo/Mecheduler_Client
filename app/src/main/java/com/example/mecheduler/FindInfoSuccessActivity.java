package com.example.mecheduler;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class FindInfoSuccessActivity extends AppCompatActivity {

    private TextView gotoLoginPage, find_kind, find_value;
    private RequestQueue requestQueue;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_success_page);

        gotoLoginPage = (TextView) findViewById(R.id.goLogin);

        find_kind = (TextView)findViewById(R.id.find_kinds);
        find_value = (TextView)findViewById(R.id.find_value);


        requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        Intent intent = getIntent();
        String find_things = intent.getExtras().getString("find");
        String find_kinds = intent.getExtras().getString("find_kind");
        find_value.setText(find_things);
        find_kind.setText(find_kinds);

        StrictMode.enableDefaults();



        gotoLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
            }
        });


    }

}
