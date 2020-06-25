package com.example.popeyes.preference;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

import org.json.JSONObject;

public class UserPreference {

    private static final String TAG = "UserPreference";
    private static final String MAIL = "email";
    private static final String STUDENT_ID = "studentid";
    private static final String PASSWORD = "password";
    private static final String UPDATED_AT = "updated_at";
    private static final String CREATED_AT = "created_at";
    private static final String IS_LOGGED = "is_logged";

    private static UserPreference instance;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    private UserPreference(Context context) {
        preferences = context.getSharedPreferences(TAG, Context.MODE_PRIVATE);
    }

    public static UserPreference getInstance(Context context) {
        if (instance == null)
            instance = new UserPreference(context.getApplicationContext());

        return instance;
    }

    public static UserPreference getInstance() {
        if (instance != null)
            return instance;

        throw new IllegalArgumentException("Should use getInstance(Context) at least once before using this method.");
    }

    public void clear() {
        doEdit();
        editor.clear();
        doApply();
    }

    @SuppressLint("CommitPrefEdits")
    private void doEdit() {
        editor = preferences.edit();
    }

    private void doApply() {
        if (editor != null) {
            editor.apply();
            editor = null;
        }
    }

    public void putUser(JSONObject object) {
        doEdit();
        editor.putInt("id", object.optInt("id"));
        editor.putString("name", object.optString("name"));
        editor.putString(MAIL, object.optString(MAIL));
        editor.putString(STUDENT_ID, object.optString(STUDENT_ID));
        editor.putString(PASSWORD, object.optString(PASSWORD));
        editor.putString(UPDATED_AT, object.optString(UPDATED_AT));
        editor.putString(CREATED_AT, object.optString(CREATED_AT));
        editor.putBoolean(IS_LOGGED, true);
        doApply();
    }

    public void logOut() {
        editor.putBoolean(IS_LOGGED, false);
    }

    public String getUserName() {
        return preferences.getString("name", "");
    }

    public String getStudentId() {
        return preferences.getString(STUDENT_ID, "");
    }

    public int getUserId() {
        return preferences.getInt("id", 0);
    }

    public String getEmail() {
        return preferences.getString(MAIL, "");
    }

    public boolean isLoggedIn() {
        return preferences.getBoolean(IS_LOGGED, false);
    }
}

