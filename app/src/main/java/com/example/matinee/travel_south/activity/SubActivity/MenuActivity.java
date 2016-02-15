package com.example.matinee.travel_south.activity.SubActivity;

import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.matinee.travel_south.R;
import com.example.matinee.travel_south.activity.LoginActivity;
import com.example.matinee.travel_south.activity.Utill.UserPreference;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    TableRow logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        SettingToobar();
        initWidget();
    }

    private void SettingToobar() {
        //Toolbar setting
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        this.setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Home");
        } else {
            Toast.makeText(getApplicationContext(), "ActionBar not avaliable", Toast.LENGTH_SHORT).show();
        }

    }

    private void initWidget() {

        logout = (TableRow) findViewById(R.id.logout);
        logout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == logout) {

            FacebookSdk.sdkInitialize(this);
            LoginManager.getInstance().logOut();
            UserPreference pref = new UserPreference(this);
            pref.clearPreference();
            Intent i = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(i);
        }
    }
}
