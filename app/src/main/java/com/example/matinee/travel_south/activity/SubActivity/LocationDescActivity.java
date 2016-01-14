package com.example.matinee.travel_south.activity.SubActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.example.matinee.travel_south.R;
import com.example.matinee.travel_south.activity.Adapter.LocationAdapter;
import com.example.matinee.travel_south.activity.Model.LocationEntity;
import com.example.matinee.travel_south.activity.Model.ResultEntity;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class LocationDescActivity extends AppCompatActivity {

    private int locationId;
    private ImageView img_location_desc;
    private TextView address_desc, tel_desc;
    private AQuery aq;
    private LocationEntity location;
    private final String PATH = "http://www.jaa-ikuzo.com/tvs/img/location/";
    private LinearLayout contrainerJourney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_desc);
        locationId = getIntent().getIntExtra("locationId", 0);
        aq = new AQuery(this);
        SettingToobar();
        initialWidget();
        callService();
    }

    private void initialWidget() {
        img_location_desc = (ImageView) findViewById(R.id.img_location_desc);
        address_desc = (TextView) findViewById(R.id.address_desc);
        tel_desc = (TextView) findViewById(R.id.tel_desc);
        contrainerJourney = (LinearLayout) findViewById(R.id.contrainerJourney);
    }

    private void SettingToobar() {
        //Toolbar setting
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        this.setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Location");
        } else {
            Toast.makeText(getApplicationContext(), "ActionBar not avaliable", Toast.LENGTH_SHORT).show();
        }
    }

    private void callService() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                String url = "http://www.jaa-ikuzo.com/tvs/getLocationDesc.php?location_id=" + locationId;
                OkHttpClient client = new OkHttpClient();

                Request request = new Request.Builder()
                        .url(url)
                        .build();

                try {

                    Gson gson = new Gson();
                    Response response = client.newCall(request).execute();
                    String reponse = response.body().string();
                    Log.v("===>", reponse + "<<<");
                    ResultEntity results = gson.fromJson(reponse, ResultEntity.class);
                    location = results.getResultsLocation().get(0);

                } catch (IOException e) {

                    Log.v("==error==>", e.getMessage());
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                address_desc.setText(location.getAddressTH());
                tel_desc.setText(location.getTel());
                AQuery aq = new AQuery(getApplicationContext());
                aq.id(img_location_desc).image(PATH + location.getImageLocationFile(), true, true, 0, 0,
                        new BitmapAjaxCallback() {
                            @Override
                            public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                                Drawable d = new BitmapDrawable(getResources(), bm);
                                iv.setBackgroundDrawable(d);
                            }
                        }.header("User-Agent", "android"));

                TableRow row = new TableRow(getApplicationContext());


            }

        }.execute();

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

}
