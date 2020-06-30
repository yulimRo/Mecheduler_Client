package com.example.mecheduler;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mecheduler.DTO.Constants;
import com.example.mecheduler.DTO.PatientVO;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

public class LogInActivity extends AppCompatActivity {

    EditText et1, et2;
    Button btn_login;
    ImageButton btn_check, btn_uncheck;

    SharedPreferences sf;
    SharedPreferences.Editor editor;

    RequestQueue requestQueue;

    private String id = "i", pw = "u";
    int check;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log_in);




        Log.d("tocken", FirebaseInstanceId.getInstance().getToken().toString());

        sf = getSharedPreferences("sFile", MODE_PRIVATE);


        editor = sf.edit();

        requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        if (!sf.getString("id", "").equals("") && !sf.getString("pw", "").equals("")){
            String url = "http://"+Constants.IP+":8081/loginJson?pid=" + sf.getString("id","") + "&ppwd=" + sf.getString("pw","");
            Log.v("url",url);
            JsonObjectRequest
                    jsonObjectRequest = new JsonObjectRequest
                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                        @Override
                        public void onResponse(JSONObject response) {
                            PatientVO patientVO = null;
                            try {
                                JSONObject obj = response.getJSONObject("patient");
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                patientVO = new PatientVO(obj.getInt("pcode"), obj.getString("pname"), obj.getString("pid"), obj.getString("ppwd"), obj.getString("pbirth"), obj.getString("ptel"), obj.getString("pgender"));
                                intent.putExtra("loginpatient", patientVO);
                                editor.apply();
                                startActivity(intent);
                            } catch (JSONException e) {
                                e.getStackTrace();
                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.getStackTrace();
                        }
                    });

            // Access the RequestQueue through your singleton class.
            requestQueue.add(jsonObjectRequest);
        }

        //로그인
        {
            et1 = (EditText) findViewById(R.id.et_id);
            et2 = (EditText) findViewById(R.id.et_pw);


            btn_login = (Button) findViewById(R.id.btn_log_in);

            btn_login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://"+Constants.IP+":8081/loginJson?pid=" + et1.getText().toString() + "&ppwd=" + et2.getText().toString();

                    JsonObjectRequest
                            jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    PatientVO patientVO = null;
                                    try {
                                        JSONObject obj = response.getJSONObject("patient");
                                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                        patientVO = new PatientVO(obj.getInt("pcode"),obj.getString("pname"), obj.getString("pid"), obj.getString("ppwd"), obj.getString("pbirth"), obj.getString("ptel"), obj.getString("pgender"));
                                        intent.putExtra("loginpatient", patientVO);
                                        editor.putString("id",id);
                                        editor.putString("pw",pw);
                                        editor.commit();
                                        startActivity(intent);
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(getApplicationContext(), "존재하지 않은 아이디 또는 비밀번호입니다.", Toast.LENGTH_SHORT).show();
                                    }


                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error
                                    error.getMessage();
                                    Toast.makeText(getApplicationContext(), "인터넷 연결이 되지 않습니다.", Toast.LENGTH_SHORT).show();

                                }
                            });

                    // Access the RequestQueue through your singleton class.
                    requestQueue.add(jsonObjectRequest);
                }

//                    if (et1.getText().toString().equals(id) && et2.getText().toString().equals(pw) && check == 1) {
//                        editor.putString("id", et1.getText().toString());
//                        editor.putString("pw", et2.getText().toString());
//                        editor.commit();
//
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//
//                    }
//                    else if(et1.getText().toString().equals(id) && et2.getText().toString().equals(pw) && check != 1)
//                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
//                    else
//                        Toast.makeText(getApplicationContext(), "아이디 혹은 비밀번호가 틀렸습니다", Toast.LENGTH_SHORT).show();
//                }
            });
        }

        //로그인 저장
        {
            btn_check = (ImageButton) findViewById(R.id.btn_check);
            btn_uncheck = (ImageButton) findViewById(R.id.btn_uncheck);

            btn_uncheck.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_check.setVisibility(View.VISIBLE);
                    btn_uncheck.setVisibility(View.GONE);

                    id = et1.getText().toString();
                    pw = et2.getText().toString();
                    check = 1;
                }
            });

            btn_check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    btn_check.setVisibility(View.GONE);
                    btn_uncheck.setVisibility(View.VISIBLE);
                    editor.clear();

                }
            });
        }
    }

    public void onClick_find_id(View v) {
        startActivity(new Intent(getApplicationContext(), FindInfoActivity.class));
    }


    public void onClick_sign_up(View v) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
    }
}


