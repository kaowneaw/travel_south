package com.example.matinee.travel_south.activity.Utill;

import android.content.Context;
import android.content.SharedPreferences;

public class UserPreference {
    public static final String PREFERENCE_KEY = "pref_tw";
    public static final String USER_ID_KEY = "user_id";
    public static final String USERNAME_KEY = "username";
    public static final String PASSWORD_KEY = "password";
    public static final String NAME_KEY = "name";
    public static final String EMAIL_KEY = "email";
    public static final String LANG_KEY = "lang";

    private SharedPreferences sh_pref;
    private SharedPreferences.Editor sh_edit;

    public UserPreference(Context context) {

        sh_pref = context.getSharedPreferences(PREFERENCE_KEY, Context.MODE_PRIVATE);
        sh_edit = sh_pref.edit();
    }

    public UserPreference(Context context, String userid, String name, String email, String lang) {
        this(context);
        registerUserPreference(userid, name, email, lang);
    }

    public void registerUserPreference(String userid, String name, String email, String lang) {
        sh_edit.putString(USER_ID_KEY, userid);
        sh_edit.putString(NAME_KEY, name);
        sh_edit.putString(EMAIL_KEY, email);
        sh_edit.putString(LANG_KEY, lang);

    }

    public boolean commit() {
        return sh_edit.commit();
    }

    public boolean clearPreference() {
        sh_edit.clear();
        return sh_edit.commit();
    }

    public String getUserID() {
        return sh_pref.getString(USER_ID_KEY, "NULL");
    }

    public String getName() {
        return sh_pref.getString(NAME_KEY, "NULL");
    }

    public String getEmail() {
        return sh_pref.getString(EMAIL_KEY, "NULL");
    }

    public String getLang() {

        return sh_pref.getString(LANG_KEY, "NULL");
    }

    public void setLang(String lang) {

        sh_edit.putString(LANG_KEY, lang);
        sh_edit.commit();
    }


}
