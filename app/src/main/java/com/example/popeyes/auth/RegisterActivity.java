package com.example.popeyes.auth;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.popeyes.R;
import com.example.popeyes.preference.UserPreference;
import com.example.popeyes.routes.Routes;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String TAG = "RegisterActivity";

    private ProgressDialog mProgressDialog;
    private EditText userName, email, phone, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        findViews();

        mProgressDialog = new ProgressDialog(this);
    }

    private void findViews() {
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        userName = findViewById(R.id.userName);
        password = findViewById(R.id.password);

        findViewById(R.id.registerButton).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.registerButton) {
            UserRegister();
        }
    }

    private void UserRegister() {
        mProgressDialog.setMessage("Loading");
        mProgressDialog.show();

        Map<String, String> params = new HashMap<>();
        params.put("email", email.getText().toString());
        params.put("studentid", phone.getText().toString());
        params.put("name", userName.getText().toString());
        params.put("password", password.getText().toString());

        AndroidNetworking.post("Routes.REGISTER")
                .addBodyParameter(params)
                .setTag(TAG)
                .addHeaders("accept", "application/json")
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mProgressDialog.dismiss();
                        Toast.makeText(RegisterActivity.this, getString(R.string.successfully_registered), Toast.LENGTH_LONG).show();
                        UserPreference.getInstance().putUser(response);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                finish();
                            }
                        }, 1200);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.w(TAG, anError.toString());
                    }
                });
    }
}


