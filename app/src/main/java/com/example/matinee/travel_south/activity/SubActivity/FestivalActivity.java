package com.example.matinee.travel_south.activity.SubActivity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.matinee.travel_south.R;
import com.example.matinee.travel_south.activity.Adapter.FestivalAdapter;
import com.example.matinee.travel_south.activity.Model.FestivalEntity;
import com.example.matinee.travel_south.activity.Model.ResultEntity;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FestivalActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {


    private List<FestivalEntity> listFest;
    private ListView lv_festival;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_festival);
        SettingToolbar();
        initWidget();
    }

    private void initWidget() {

        lv_festival = (ListView) findViewById(R.id.lv_festival);
        spinner = (Spinner) findViewById(R.id.spinner);
        List<String> categories = new ArrayList();
        categories.add("ทั้งหมด");
        categories.add("มกราคม");
        categories.add("กุมภาพันธ์");
        categories.add("มีนาคม");
        categories.add("เมษายน");
        categories.add("พฤษภาคม");
        categories.add("มิถุนายน");
        categories.add("กรกฎาคม");
        categories.add("มิถุนายน");
        categories.add("สิงหาคม");
        categories.add("กันยายน");
        categories.add("ตุลาคม");
        categories.add("พฤศจิกายน");
        categories.add("ธันวาคม");

        ArrayAdapter dataAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, categories);
        spinner.setAdapter(dataAdapter);
        spinner.setOnItemSelectedListener(this);
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


    private void SettingToolbar() {
        //Toolbar setting
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        this.setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Festival");
        } else {
            Toast.makeText(getApplicationContext(), "ActionBar not avaliable", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        callService();
    }

    private void callService() {

        final ProgressDialog dialog = new ProgressDialog(this);
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                dialog.setMessage("Loading...");
                dialog.show();
            }

            @Override
            protected Void doInBackground(Void... voids) {

                String url = "http://www.jaa-ikuzo.com/tvs/getFestival.php";
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try {

                    Gson gson = new Gson();
                    Response response = client.newCall(request).execute();
                    String reponse = response.body().string();
                    Log.v("=>", reponse);
                    ResultEntity results = gson.fromJson(reponse, ResultEntity.class);
                    listFest = results.getResultsFestival();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

                FestivalAdapter adapter = new FestivalAdapter(getApplicationContext(), listFest);
                lv_festival.setAdapter(adapter);


            }
        }.execute();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Toast.makeText(getApplicationContext(), position + "", Toast.LENGTH_SHORT).show();
        if (listFest != null) {
            List<FestivalEntity> data = filterDataMonth(position);
            FestivalAdapter adapter = new FestivalAdapter(getApplicationContext(), data);
            lv_festival.setAdapter(adapter);
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private List<FestivalEntity> filterDataMonth(int monthId) {
        List<FestivalEntity> data = new ArrayList<>();
        if (monthId != 0) {
            for (FestivalEntity item : listFest) {
                if (item.getMonth_id() == monthId) {
                    data.add(item);
                }
            }
        } else {
            return listFest;
        }
        return data;
    }
}
