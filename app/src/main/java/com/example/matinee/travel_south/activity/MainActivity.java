package com.example.matinee.travel_south.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.matinee.travel_south.R;
import com.example.matinee.travel_south.activity.SubActivity.CheckInActivity;
import com.example.matinee.travel_south.activity.SubActivity.MenuActivity;
import com.example.matinee.travel_south.activity.SubActivity.ProvinceActivity;
import com.example.matinee.travel_south.activity.SubActivity.RecommendActivity;
import com.example.matinee.travel_south.activity.SubActivity.SearchMenuLocationActivity;
import com.example.matinee.travel_south.activity.SubActivity.SelectLocationActivity;
import com.example.matinee.travel_south.activity.Utill.UserPreference;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageButton btHome_travel, btHome_rec, btHome_checkin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SettingToobar();
        initWidget();
        UserPreference pref = new UserPreference(this);
        Toast.makeText(getApplicationContext(), pref.getName(), Toast.LENGTH_SHORT).show();
    }


    private void initWidget() {

        btHome_travel = (ImageButton) findViewById(R.id.btHome_travel);
        btHome_rec = (ImageButton) findViewById(R.id.btHome_rec);
        btHome_checkin = (ImageButton) findViewById(R.id.btHome_checkin);
        btHome_travel.setOnClickListener(this);
        btHome_rec.setOnClickListener(this);
        btHome_checkin.setOnClickListener(this);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.search_location) {

            startActivity(new Intent(this, SearchMenuLocationActivity.class));

        } else if (id == R.id.menu) {
            startActivity(new Intent(this, MenuActivity.class));
        }

        return true;
    }

    @Override
    public void onClick(View v) {
        if (v == btHome_travel) {
            startActivity(new Intent(this, ProvinceActivity.class));
        } else if (v == btHome_rec) {
            startActivity(new Intent(this, RecommendActivity.class));
        } else if (v == btHome_checkin) {
            startActivity(new Intent(this, CheckInActivity.class));
        }
    }
}
