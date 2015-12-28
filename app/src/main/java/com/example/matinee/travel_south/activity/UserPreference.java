package com.example.matinee.travel_south.activity;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {
    public static final String PREFERENCE_KEY = "pref_tw";
    public static final String USER_ID_KEY = "user_id";
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";


    private SharedPreferences sh_pref;
    private SharedPreferences.Editor sh_edit;

    public UserPreference(Context context) {

        sh_pref = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        sh_edit = sh_pref.edit();
    }

    public UserPreference(Context context, String userid) {
        this(context);
        registerUserPreference(userid);
    }

    public void registerUserPreference(String userid) {
        sh_edit.putString(USER_ID_KEY, userid);


    }

    public boolean commit() {
        return sh_edit.commit();
    }

    public boolean clearPreference() {
        sh_edit.clear();
        return sh_edit.commit();
    }

    public String getUserID() {
        return sh_pref.getString(USER_ID_KEY, "null");
    }


}
