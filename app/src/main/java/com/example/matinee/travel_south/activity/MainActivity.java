package com.example.matinee.travel_south.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.example.matinee.travel_south.R;

public class MainActivity extends AppCompatActivity {
    Button login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        login = (Button)findViewById(R.id.BTLoginwithfacebook); //ใช้ ล็อกอินคือ บอกว่าถ้าพูดถึงล็อกอินก็จะเป็นบรรทัดนี้ทั้งหมด
        login.setOnClickListener(new View.OnClickListener() { //เซ็ทให้มันทำงาน
            @Override
            public void onClick(View v) { // intent คือการเป็นตัวกลางในส่งค่า
                Intent datatomain = new Intent(getApplicationContext(),MainMenuActivity.class);
                startActivity(datatomain);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
