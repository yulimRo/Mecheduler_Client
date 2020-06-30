package com.example.mecheduler;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.concurrent.TimeUnit;

public class FindInfoActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private ActionBar actionBar;
    static String IP = "192.168.35.11";

    private EditText findIdName, findIdTel, findPwdId, findPwdTel,verifyId, verifyPwd;
    private Button findIdBtn, findPwdBtn,sendForId,sendForPwd;
    private RequestQueue requestQueue;
    private TextView gotoLoginPage;

    private static final String TAG = "PhoneAuthActivity";
    // [START declare_auth]
    private FirebaseAuth auth;
    // [END declare_auth]
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private String mVerificationId;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks,mCallbacks2;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.find_info);


        gotoLoginPage = (TextView) findViewById(R.id.goToLoginPage);
        findIdName = (EditText) findViewById(R.id.findIDName);
        findIdTel = (EditText) findViewById(R.id.find_id_tel);
        findPwdId = (EditText) findViewById(R.id.findPwdID);
        findPwdTel = (EditText) findViewById(R.id.findPWDTel);
        findIdBtn = (Button) findViewById(R.id.find_id_btn);
        findPwdBtn = (Button) findViewById(R.id.findPwdBtn);
        sendForId = (Button) findViewById(R.id.sendForId);
        sendForPwd = (Button) findViewById(R.id.sendForPwd);
        verifyId = (EditText) findViewById(R.id.verifyId);
        verifyPwd = (EditText) findViewById(R.id.verifyPwd);

        verifyPwd.setVisibility(View.GONE);
        verifyId.setVisibility(View.GONE);


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

        mCallbacks2 = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

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

                signInWithPhoneAuthCredential2(credential);
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


        auth = FirebaseAuth.getInstance();
        auth.setLanguageCode("ko");



        requestQueue = Volley.newRequestQueue(this);
        requestQueue.start();

        StrictMode.enableDefaults();

        gotoLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class);
                startActivity(intent);
            }
        });
        sendForId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyId.setVisibility(View.VISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+82" + findIdTel.getText().subSequence(1, findIdTel.getText().toString().length()),        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        FindInfoActivity.this,               // Activity (for callback binding)
                        mCallbacks);        // OnVerificationStateChangedCallbacks

                Toast.makeText(getApplicationContext(), "인증번호가 발송되었습니다.", Toast.LENGTH_LONG).show();
            }
        });

        findIdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String code = verifyId.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    verifyId.setError("Cannot be empty.");
                    return;
                }

                verifyPhoneNumberWithCode(mVerificationId, code);

            }

        });

        sendForPwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                verifyPwd.setVisibility(View.VISIBLE);

                PhoneAuthProvider.getInstance().verifyPhoneNumber(
                        "+82" + findPwdTel.getText().subSequence(1, findPwdTel.getText().toString().length()),        // Phone number to verify
                        60,                 // Timeout duration
                        TimeUnit.SECONDS,   // Unit of timeout
                        FindInfoActivity.this,               // Activity (for callback binding)
                        mCallbacks2);        // OnVerificationStateChangedCallbacks

                Toast.makeText(getApplicationContext(), "인증번호가 발송되었습니다.", Toast.LENGTH_LONG).show();
            }
        });


        findPwdBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String code = verifyPwd.getText().toString();

                if (TextUtils.isEmpty(code)) {
                    verifyPwd.setError("Cannot be empty.");
                    return;
                }
                verifyPhoneNumberWithCode2(mVerificationId, code);

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
    private void verifyPhoneNumberWithCode2(String verificationId, String code) {
        // [START verify_with_code]
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
        // [END verify_with_code]
        signInWithPhoneAuthCredential2(credential);
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

                            try {
                                String url = "http://"+IP+":8081/findIdJson?pname=" + URLEncoder.encode(findIdName.getText().toString(), "UTF-8") + "&ptel=" + findIdTel.getText().toString();
                                Log.v("url", url);

                                JsonObjectRequest
                                        jsonObjectRequest = new JsonObjectRequest
                                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                PatientVO patientVO = null;
                                                try {
                                                    JSONObject obj = response.getJSONObject("patient");
                                                    String user_id = obj.getString("pid");
                                                    Intent intent = new Intent(getApplicationContext(), FindInfoSuccessActivity.class);
                                                    intent.putExtra("find",user_id);
                                                    intent.putExtra("find_kind","아이디");
                                                    startActivity(intent);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }


                                            }
                                        }, new Response.ErrorListener() {

                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                // TODO: Handle error
                                                error.getMessage();
                                                Toast.makeText(getApplicationContext(), "존재하지 않은 아이디 또는 비밀번호입니다.", Toast.LENGTH_SHORT).show();

                                            }
                                        });

                                // Access the RequestQueue through your singleton class.
                                requestQueue.add(jsonObjectRequest);

                            } catch (Exception e) {

                            }


                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(), "인증번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                            }
                            // [START_EXCLUDE silent]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]

    // [START sign_in_with_phone]
    private void signInWithPhoneAuthCredential2(PhoneAuthCredential credential) {
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            Log.v("hello","sucess");
                            FirebaseUser user = task.getResult().getUser();

                            try {
                                String url = "http://"+IP+":8081/findPwdJson?pid=" + findPwdId.getText().toString() + "&ptel=" + findPwdTel.getText().toString();
                                Log.v("url", url);

                                JsonObjectRequest
                                        jsonObjectRequest = new JsonObjectRequest
                                        (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                                            @Override
                                            public void onResponse(JSONObject response) {
                                                PatientVO patientVO = null;
                                                try {
                                                    JSONObject obj = response.getJSONObject("patient");
                                                    String user_pwd = obj.getString("ppwd");
                                                    Intent intent = new Intent(getApplicationContext(), FindInfoSuccessActivity.class);
                                                    intent.putExtra("find",user_pwd);
                                                    intent.putExtra("find_kind","비밀번호");
                                                    startActivity(intent);
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }


                                            }
                                        }, new Response.ErrorListener() {

                                            @Override
                                            public void onErrorResponse(VolleyError error) {

                                                // TODO: Handle error
                                                error.getMessage();
                                                Toast.makeText(getApplicationContext(), "존재하지 않은 아이디 또는 비밀번호입니다.", Toast.LENGTH_SHORT).show();

                                            }
                                        });

                                // Access the RequestQueue through your singleton class.
                                requestQueue.add(jsonObjectRequest);

                            } catch (Exception e) {

                            }

                        } else {
                            // Sign in failed, display a message and update the UI
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                Toast.makeText(getApplicationContext(), "인증번호가 틀렸습니다.", Toast.LENGTH_SHORT).show();
                            }
                            // [START_EXCLUDE silent]
                        }
                    }
                });
    }
    // [END sign_in_with_phone]


}
