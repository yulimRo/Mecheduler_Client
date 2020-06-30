package com.example.mecheduler;

import android.content.Intent;
import android.graphics.Color;
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

import java.util.concurrent.TimeUnit;

public class SignUpActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;

    private EditText joinID, joinPWD, joinBith, joinTel, joinName, pwdConfirm, verify;
    private Button joinBtn, IdConfirm, sendSMS;
    private TextView gotoLoginPage;
    private int checkpoint = 0;
    Button btn_sing_up;
    RequestQueue requestQueue;
    private int result =0;
    private String text;
    private static final String TAG = "PhoneAuthActivity";


    // [START declare_auth]
    private FirebaseAuth auth;
    // [END declare_auth]
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    String toast = "";


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sign_up);


        verify = (EditText) findViewById(R.id.verify);
        joinID = (EditText) findViewById(R.id.joinID);
        joinPWD = (EditText) findViewById(R.id.joinPwd);
        pwdConfirm = (EditText) findViewById(R.id.joinPwdConfirm);
        joinBith = (EditText) findViewById(R.id.joinBirth);
        joinTel = (EditText) findViewById(R.id.joinTel);
        joinName = (EditText) findViewById(R.id.joinName);
        IdConfirm = (Button) findViewById(R.id.idconfirm);
        sendSMS = (Button) findViewById(R.id.sendNum);
        gotoLoginPage = (TextView) findViewById(R.id.goToLoginPage);
        btn_sing_up = (Button) findViewById(R.id.btn_sign_up);


        verify.setVisibility(View.GONE);
        requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();


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
                verify.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "인증번호가 발송되었습니다.", Toast.LENGTH_LONG).show();

                signInWithPhoneAuthCredential(credential);
            }


            @Override
            public void onVerificationFailed(FirebaseException e) {
                // This callback is invoked in an invalid request for verification is made,
                // for instance if the the phone number format is not valid.
                Log.w(TAG, "onVerificationFailed", e);

                if (e instanceof FirebaseAuthInvalidCredentialsException) {
                    Toast.makeText(getApplicationContext(), "휴대폰 정보가 옳지 않습니다.", Toast.LENGTH_LONG).show();
                    // Invalid request
                } else if (e instanceof FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    // [START_EXCLUDE]
                    Snackbar.make(findViewById(android.R.id.content), "Quota exceeded.",
                            Snackbar.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "휴대폰 정보가 옳지 않습니다.", Toast.LENGTH_LONG).show();
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
                verify.setVisibility(View.VISIBLE);
                Toast.makeText(getApplicationContext(), "인증번호가 발송되었습니다.", Toast.LENGTH_LONG).show();

            }
        };
        // [END phone_auth_callbacks]


        auth = FirebaseAuth.getInstance();
        auth.setLanguageCode("ko");


        //회원가입
        {

            requestQueue = Volley.newRequestQueue(this);
            requestQueue.start();

            final String[] gender = {"성별", "남", "여"};

            final Spinner spinner = (Spinner) findViewById(R.id.joinGender);
            ArrayAdapter<String> adapter_spinner_gender = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender);
            spinner.setAdapter(adapter_spinner_gender);


            gotoLoginPage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                    startActivity(intent);
                }
            });
            IdConfirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = "http://192.168.35.251:8081/checkIdJson?pid=" + joinID.getText().toString();
                    Log.v("url", url);
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                            (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                @Override
                                public void onResponse(JSONObject response) {
                                    try {
                                        if (response.getJSONObject("patient") != null) {
                                            Toast.makeText(getApplicationContext(), "중복된 아이디가 존재합니다.", Toast.LENGTH_LONG).show();
                                            joinID.setTextColor(Color.BLACK);
                                            if (checkpoint == 1) {
                                                checkpoint--;
                                                Log.v("check", String.valueOf(checkpoint));
                                            }

                                        }
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "사용할 수 있는 아이디 입니다.", Toast.LENGTH_LONG).show();
                                        joinID.setTextColor(Color.BLUE);
                                        if (checkpoint == 0)
                                            checkpoint++;
                                        else
                                            Log.v("check", String.valueOf(checkpoint));
                                    }

                                }
                            }, new Response.ErrorListener() {

                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    // TODO: Handle error
                                    Toast.makeText(getApplicationContext(), "인터넷 연결이 되지않았습니다.", Toast.LENGTH_LONG).show();

                                }
                            });

                    // Access the RequestQueue through your singleton class.
                    requestQueue.add(jsonObjectRequest);
                }
            });

            joinID.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    if (checkpoint == 1) {
                        joinID.setTextColor(Color.BLACK);
                        checkpoint--;
                        Log.v("check", String.valueOf(checkpoint));
                    }
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            requestQueue = Volley.newRequestQueue(this);
            requestQueue.start();

            sendSMS.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(joinTel.getText().toString().equals("")){
                        Toast.makeText(getApplicationContext(),"핸드폰 번호를 입력해주세요",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    else{
                        result =1;
                        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                                "+82" + joinTel.getText().subSequence(1, joinTel.getText().toString().length()),        // Phone number to verify
                                60,                 // Timeout duration
                                TimeUnit.SECONDS,   // Unit of timeout
                                SignUpActivity.this,               // Activity (for callback binding)
                                mCallbacks);        // OnVerificationStateChangedCallbacks
                    }




                }
            });

            btn_sing_up.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    text = spinner.getSelectedItem().toString();

                    if (joinID.getText().toString().equals(""))
                        Toast.makeText(getApplicationContext(), "아이디를 입력하세요.", Toast.LENGTH_LONG).show();
                    else if (joinPWD.getText().toString().equals(""))
                        Toast.makeText(getApplicationContext(), "비밀번호를 입력하세요", Toast.LENGTH_LONG).show();
                    else if (pwdConfirm.getText().toString().equals(""))
                        Toast.makeText(getApplicationContext(), "비밀번호 확인을 입력하세요", Toast.LENGTH_LONG).show();
                    else if (joinName.getText().toString().equals(""))
                        Toast.makeText(getApplicationContext(), "이름을 입력하세요.", Toast.LENGTH_LONG).show();
                    else if (text.equals("성별"))
                        Toast.makeText(getApplicationContext(), "성별을 선택하세요.", Toast.LENGTH_LONG).show();
                    else if (joinBith.getText().toString().equals(""))
                        Toast.makeText(getApplicationContext(), "생일을 입력하세요", Toast.LENGTH_LONG).show();
                    else if (joinTel.getText().toString().equals(""))
                        Toast.makeText(getApplicationContext(), "핸드폰 번호를 입력하세요", Toast.LENGTH_LONG).show();
                    else {
                        if (checkpoint != 0) {
                            if (joinPWD.getText().toString().equals(pwdConfirm.getText().toString())) {
                                if (joinBith.getText().toString().contains("-")) {
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
                        } else
                            Toast.makeText(getApplicationContext(), "아이디 중복 확인을 해주세요", Toast.LENGTH_LONG).show();

                    }
                }
            });
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case android.R.id.home:
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivityForResult(intent, 101);
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

                            PatientVO patientVO = new PatientVO(joinName.getText().toString(), joinID.getText().toString(), joinPWD.getText().toString(), joinBith.getText().toString(), joinTel.getText().toString(), text);
                            String url = "http://192.168.35.251:8081/signup?pid=" + patientVO.getPid() + "&ppwd=" + patientVO.getPpwd() + "&pname=" + patientVO.getPname() + "&ptel=" + patientVO.getPtel() + "&pbirth=" + patientVO.getPbirth() + "&pgender=" + patientVO.getPgender();
                            JsonObjectRequest
                                    jsonObjectRequest = new JsonObjectRequest
                                    (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                        @Override
                                        public void onResponse(JSONObject response) {
                                            Toast.makeText(getApplicationContext(), "회원가입을 축하합니다.", Toast.LENGTH_LONG).show();
                                            startActivity(new Intent(getApplicationContext(), LogInActivity.class));

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
