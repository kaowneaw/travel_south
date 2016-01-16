package com.example.matinee.travel_south.activity.SubActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
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
import android.view.Gravity;
import android.view.Menu;
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
import com.example.matinee.travel_south.activity.Model.Journey;
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

    private ImageView img_location_desc;
    private TextView address_desc, tel_desc;
    private LocationEntity location, locationIntent;
    private final String PATH = "http://www.jaa-ikuzo.com/tvs/img/location/";
    private LinearLayout contrainerJourney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_desc);
        Bundle data = getIntent().getExtras();
        locationIntent = data.getParcelable("locationObj");
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
            getSupportActionBar().setTitle(locationIntent.getNameTH());
        } else {
            Toast.makeText(getApplicationContext(), "ActionBar not avaliable", Toast.LENGTH_SHORT).show();
        }
    }

    private void callService() {

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                String url = "http://www.jaa-ikuzo.com/tvs/getLocationDesc.php?location_id=" + locationIntent.getLocation_id();
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


                for (Journey jorney : location.getListJorney()) {

                    TableRow row = new TableRow(getApplicationContext());
                    row.setPadding(20, 20, 20, 20);
                    ImageView img = new ImageView(getApplicationContext());
                    TableRow.LayoutParams layoutImgview = new TableRow.LayoutParams(80, 80);
                    img.setLayoutParams(layoutImgview);
                    img.setLayoutParams(layoutImgview);
                    img.setBackgroundResource(R.drawable.car);

                    TableRow.LayoutParams layout_gravity = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.MATCH_PARENT);
                    layout_gravity.weight = 1.0f;
                    layout_gravity.gravity = Gravity.TOP;

                    TextView tv = new TextView(getApplicationContext());
                    tv.setPadding(50, 0, 0, 0);
                    tv.setLayoutParams(layout_gravity);
                    tv.setText(jorney.getJourneyDesc());
                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    tv.setTextSize(16);
                    tv.setTextColor(Color.BLACK);

                    row.setGravity(Gravity.CENTER_VERTICAL);
                    row.addView(img);
                    row.addView(tv);

                    contrainerJourney.addView(row);
                }

            }

        }.execute();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_location_desc, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if (id == android.R.id.home) {

            this.finish();

        } else if (id == R.id.map_desc) {

            Intent i = new Intent(this, MapActivity.class);
            i.putExtra("locationObj", this.location);
            startActivity(i);
        }

        return super.onOptionsItemSelected(item);
    }

}
