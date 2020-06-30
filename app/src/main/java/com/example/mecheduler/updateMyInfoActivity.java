package com.example.mecheduler;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mecheduler.DTO.PatientVO;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseException;
import com.google.firebase.FirebaseTooManyRequestsException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

import static android.widget.Toast.LENGTH_SHORT;

public class updateMyInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    static String IP = "192.168.35.11";

    private int checkpoint = 0;
    private Button btn_1, btn_2, btn_3;

    private int result =1;
    private RequestQueue requestQueue;
    private TextView deletetv;
    private EditText id, pwd, pwd_confirm, birth, tel, name,verify;
    private Spinner updategender;
    private Button gotoLogin, updateBtn,sendSMS;
    private View rootview;
    private String text;
    PatientVO loginpatientVO;

    private static final String TAG = "PhoneAuthActivity";


    // [START declare_auth]
    private FirebaseAuth auth;
    // [END declare_auth]
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_info_page);

        // 액션바 //
        {
            toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            actionBar = getSupportActionBar();
            actionBar.setDisplayShowTitleEnabled(false);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }


        loginpatientVO = (PatientVO) getIntent().getParcelableExtra("loginpatient");

        id = (EditText) findViewById(R.id.userId);
        pwd = (EditText) findViewById(R.id.userpwd);
        pwd_confirm = (EditText) findViewById(R.id.userpwd_confirm);
        tel = (EditText) findViewById(R.id.user_tel);
        birth = (EditText) findViewById(R.id.user_birth);
        name = (EditText) findViewById(R.id.updatename);
        updategender = (Spinner) findViewById(R.id.updategender);
        verify = (EditText) findViewById(R.id.verify);
        deletetv = (TextView) findViewById(R.id.deleteuser);
        updateBtn = (Button) findViewById(R.id.updateuser);
        sendSMS = (Button) findViewById(R.id.sendNum);



        auth = FirebaseAuth.getInstance();
        auth.setLanguageCode("ko");

        id.setText(loginpatientVO.getPid());
        pwd.setText(loginpatientVO.getPpwd());
        birth.setText(loginpatientVO.getPbirth());
        tel.setText(loginpatientVO.getPtel());
        name.setText(loginpatientVO.getPname());
        verify.setVisibility(View.GONE);

        final String[] gender = {"성별", "남", "여"};

        ArrayAdapter<String> adapter_spinner_gender = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender);
        updategender.setAdapter(adapter_spinner_gender);

        updategender.setSelection(2);

        // [START phone_auth_callbacks]
        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

            @Override
            public void onVerificationCompleted(PhoneAuthCredential credential) {
                // This callback will be invoked in two situations:
                // 1 - Instant verification. In some cases the phone number can be instantly
                //     verified without needing to send or enter a verification code.
                // 2 - Auto-retrieval. On some devices Google Play services can automatically
                //     detect the incoming verification SMS and perform verification without
                //     user action.
                Log.d(TAG, "onVerificationCompleted:" + credential);
                // [START_EXCLUDE silent]
                // [END_EXCLUDE]

                signInWithPhoneAuthCredential(credential);
            }


            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                    // [END_EXCLUDE]
                }

            }

            @Override
            public void onCodeSent(@NonNull String verificationId,
                                   @NonNull PhoneAuthProvider.ForceResendingToken token) {
                // The SMS verification code has been sent to the provided phone number, we
                // now need to ask the user to enter the code and then construct a credential
                // by combining the code with a verification ID.
                Log.d(TAG, "onCodeSent:" + verificationId);
                // Save verification ID and resending token so we can use them later
                mVerificationId = verificationId;
                mResendToken = token;
            }
        };
        // [END phone_auth_callbacks]


        tel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                verify.setVisibility(View.GONE);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                result=0;
                verify.setVisibility(View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        sendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                result =1;
                verify.setVisibility(View.VISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+82" + tel.getText().subSequence(1, tel.getText().toString().length()),        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        updateMyInfoActivity.this,               // Activity (for callback binding)
                        mCallbacks);        // OnVerificationStateChangedCallbacks

                Toast.makeText(getApplicationContext(), "인증번호가 발송되었습니다.", Toast.LENGTH_LONG).show();
            }
        });


        requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();
        updateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                text = updategender.getSelectedItem().toString();

                if (id.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "아이디를 입력하세요.", Toast.LENGTH_LONG).show();
                else if (pwd.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_LONG).show();
                else if (pwd_confirm.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력하세요", Toast.LENGTH_LONG).show();
                else if (name.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "이름을 입력하세요.", Toast.LENGTH_LONG).show();
                else if (text.equals("성별"))
                    Toast.makeText(getApplicationContext(), "성별을 선택하세요.", Toast.LENGTH_LONG).show();
                else if (birth.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "생일을 입력하세요", Toast.LENGTH_LONG).show();
                else if (tel.getText().toString().equals(""))
                    Toast.makeText(getApplicationContext(), "핸드폰 번호를 입력하세요", Toast.LENGTH_LONG).show();
                else {
                        if (pwd.getText().toString().equals(pwd_confirm.getText().toString())) {
                            if (birth.getText().toString().contains("-")) {
                                if(result != 0){
                                    String code = verify.getText().toString();
                                    if (TextUtils.isEmpty(code)) {
                                        verify.setError("Cannot be empty.");
                                        return;
                                    }

                                    verifyPhoneNumberWithCode(mVerificationId, code);
                                }
                                else
                                    Toast.makeText(getApplicationContext(), "핸드폰 인증을 해주세요", Toast.LENGTH_LONG).show();

                            } else
                                Toast.makeText(getApplicationContext(), "생일 양식을 맞춰주세요(yyyy-mm-dd)", Toast.LENGTH_LONG).show();
                        } else
                            Toast.makeText(getApplicationContext(), "비밀번호가 일치하지 않습니다", Toast.LENGTH_LONG).show();

                }
            }
        });

        requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.start();
        deletetv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(updateMyInfoActivity.this);

                builder.setTitle("회원 탈퇴하시겠습니까?").setMessage("회원 탈퇴시 모든 정보가 사라집니다.");

                builder.setNegativeButton("아니요",null);

                builder.setPositiveButton("네", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String url = "http://"+IP+":8081/deleteJson?pcode=" + loginpatientVO.getPcode();
                        Log.v("url", url);

                        JsonObjectRequest
                                jsonObjectRequest = new JsonObjectRequest
                                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                    @Override
                                    public void onResponse(JSONObject response) {

                                        Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                                        PatientVO userDTO = null;
                                        intent.putExtra("loginuser", userDTO);
                                        startActivity(intent);

                                    }
                                }, new Response.ErrorListener() {

                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        // TODO: Handle error
                                        error.getMessage();
                                    }
                                });

                        // Access the RequestQueue through your singleton class.
                        requestQueue.add(jsonObjectRequest);
                    }
                });



                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });



    }

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


    private void verifyPhoneNumberWithCode(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential(credential);
    }

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");

                            FirebaseUser user = task.getResult().getUser();

                            String url = null;
                            try {

                                text = updategender.getSelectedItem().toString();
                                url = "http://"+IP+":8081/updateJson?&pcode=" + loginpatientVO.getPcode() + "&ppwd=" + pwd.getText().toString() + "&pname=" + URLEncoder.encode(name.getText().toString(), "UTF-8") + "&ptel=" + tel.getText().toString() + "&pbirth=" + birth.getText().toString() + "&pgender=" + URLEncoder.encode(text, "UTF-8");
                            } catch (UnsupportedEncodingException e) {
                                e.printStackTrace();
                            }

                            JsonObjectRequest
                                    jsonObjectRequest = new JsonObjectRequest
                                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Toast.makeText(getApplicationContext(), "회원정보 변경을 하였습니다..", Toast.LENGTH_LONG).show();
                                            loginpatientVO.setPpwd(pwd.getText().toString());
                                            loginpatientVO.setPbirth(birth.getText().toString());
                                            loginpatientVO.setPtel(tel.getText().toString());
                                            loginpatientVO.setPname(name.getText().toString());
                                            loginpatientVO.setPgender(text);
                                            Intent intent  = new Intent(getApplicationContext(),updateMyInfoActivity.class);
                                            intent.putExtra("loginpatient", loginpatientVO);
                                            startActivity(intent);


                                        }
                                    }, new Response.ErrorListener() {

                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            // TODO: Handle error

                                        }
                                    });
                            // Access the RequestQueue through your singleton class.
                            requestQueue.add(jsonObjectRequest);


                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(), "인증번호가 틀렸습니다.", Toast.LENGTH_LONG).show();
                            }
                            // [START_EXCLUDE silent]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]
}
