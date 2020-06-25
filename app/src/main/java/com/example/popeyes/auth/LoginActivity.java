package com.example.popeyes.auth;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.popeyes.R;
import com.example.popeyes.preference.UserPreference;
import com.example.popeyes.routes.Routes;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";

    private ProgressDialog mProgressDialog;
    private EditText mUsernameEditText, mPasswordEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        findViews();
        mProgressDialog = new ProgressDialog(LoginActivity.this);
    }

    private void findViews() {
        mUsernameEditText = findViewById(R.id.mUsernameEditText);
        mPasswordEditText = findViewById(R.id.mPasswordEditText);

        findViewById(R.id.loginButton).setOnClickListener(this);
        findViewById(R.id.registerButton).setOnClickListener(this);
    }

    private void loginClicked() {
        mProgressDialog.setMessage("Loading");
        mProgressDialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("email", mUsernameEditText.getText().toString());
        params.put("password", mPasswordEditText.getText().toString());

        AndroidNetworking.post("Routes.LOGIN")
                .addBodyParameter(params)
                .setTag(TAG)
                .setPriority(Priority.MEDIUM)
                .addHeaders("accept", "application/json")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mProgressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, getString(R.string.successfully_registered), Toast.LENGTH_LONG).show();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivity(new Intent(getApplicationContext(),RegisterActivity.class));
                            }
                        }, 1200);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.w(TAG, anError.getErrorBody());
                        Log.w(TAG,anError.getErrorCode()+"");
                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.loginButton:
                loginClicked();
                break;
            case R.id.registerButton:
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
                break;
        }
    }
}
