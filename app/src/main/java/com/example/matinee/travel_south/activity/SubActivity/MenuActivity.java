package com.example.matinee.travel_south.activity.SubActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TableRow;
import android.widget.Toast;

import com.example.matinee.travel_south.R;
import com.example.matinee.travel_south.activity.LoginActivity;
import com.example.matinee.travel_south.activity.Utill.UserPreference;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    TableRow logout;
    TableRow language;
    ImageView imgLang;
    UserPreference pref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        pref = new UserPreference(getApplicationContext());
        SettingToobar();
        initWidget();
    }

    private void SettingToobar() {
        //Toolbar setting
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        this.setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Menu");
        } else {
            Toast.makeText(getApplicationContext(), "ActionBar not avaliable", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {

            this.finish();
        }

        return super.onOptionsItemSelected(item);
    }

    private void initWidget() {

        logout = (TableRow) findViewById(R.id.logout);
        logout.setOnClickListener(this);
        language = (TableRow) findViewById(R.id.language);
        language.setOnClickListener(this);
        imgLang = (ImageView) findViewById(R.id.imgLang);
        setImagLang(pref.getLang());

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

        } else if (v == language) {

            popupLang();
        }
    }

    private void popupLang() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_lang, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle("Choose Language");

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        TableRow lngThai = (TableRow) dialogView.findViewById(R.id.lngThai);
        TableRow lngEng = (TableRow) dialogView.findViewById(R.id.lngEng);
        TableRow lngChaina = (TableRow) dialogView.findViewById(R.id.lngChaina);

        final AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

        lngThai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImagLang("TH");
                alertDialog.dismiss();
                pref.setLang("TH");
            }
        });
        lngEng.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImagLang("ENG");
                alertDialog.dismiss();
                pref.setLang("ENG");
            }
        });
        lngChaina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setImagLang("CN");
                alertDialog.dismiss();
                pref.setLang("CN");
            }
        });

        Toast.makeText(getApplicationContext(), pref.getLang(), Toast.LENGTH_SHORT).show();

    }

    protected void setImagLang(String lang) {

        if (lang.equals("TH")) {
            imgLang.setImageResource(R.mipmap.thailand);
        } else if (lang.equals("ENG")) {
            imgLang.setImageResource(R.mipmap.eng);
        } else if (lang.equals("CN")) {
            imgLang.setImageResource(R.mipmap.cnlarge);
        }
    }


}
