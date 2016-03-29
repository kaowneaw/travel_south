package com.example.matinee.travel_south.activity.SubActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
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
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxStatus;
import com.androidquery.callback.BitmapAjaxCallback;
import com.example.matinee.travel_south.R;
import com.example.matinee.travel_south.activity.Adapter.LocationAdapter;
import com.example.matinee.travel_south.activity.Model.ImageLocation;
import com.example.matinee.travel_south.activity.Model.Journey;
import com.example.matinee.travel_south.activity.Model.LocationEntity;
import com.example.matinee.travel_south.activity.Model.ResultEntity;
import com.example.matinee.travel_south.activity.Utill.UserPreference;
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

public class LocationDescActivity extends AppCompatActivity implements View.OnClickListener {

    private ImageView img_location_desc;
    private TextView address_desc, tel_desc, locationDesc;
    private LocationEntity location, locationIntent;
    private final String PATH = "http://www.jaa-ikuzo.com/tvs/img/location/";
    private LinearLayout contrainerJourney;
    private ViewFlipper fliper;
    private float lastX;
    private AQuery aq = new AQuery(this);
    private UserPreference pref;
    TableRow telClick;
    TextView address_text, tel_text, desc_text, travel_text, web_desc, web_text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location_desc);
        pref = new UserPreference(getApplicationContext());
        Bundle data = getIntent().getExtras();
        locationIntent = data.getParcelable("locationObj");
        SettingToobar();
        initialWidget();
        callService();
    }

    private void initialWidget() {

        fliper = (ViewFlipper) findViewById(R.id.flipper);
//      img_location_desc = (ImageView) findViewById(R.id.img_location_desc);
        address_desc = (TextView) findViewById(R.id.address_desc);
        tel_desc = (TextView) findViewById(R.id.tel_desc);
        contrainerJourney = (LinearLayout) findViewById(R.id.contrainerJourney);
        locationDesc = (TextView) findViewById(R.id.locationDesc);
        telClick = (TableRow) findViewById(R.id.telClick);
        telClick.setOnClickListener(this);
        address_text = (TextView) findViewById(R.id.address_text);
        tel_text = (TextView) findViewById(R.id.tel_text);
        desc_text = (TextView) findViewById(R.id.desc_text);
        travel_text = (TextView) findViewById(R.id.travel_text);
        web_desc = (TextView) findViewById(R.id.web_desc);
        web_text = (TextView) findViewById(R.id.web_text);
    }

    private void SettingToobar() {
        //Toolbar setting
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        this.setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            if (pref.getLang().equals("TH")) {
                getSupportActionBar().setTitle(locationIntent.getNameTH());
            } else if (pref.getLang().equals("ENG")) {
                getSupportActionBar().setTitle(locationIntent.getNameEng());
            } else {
                getSupportActionBar().setTitle(locationIntent.getNameChi());
            }
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
                for (ImageLocation data : location.getListImage()) {
                    //  This will create dynamic image view and add them to ViewFlipper
                    setFlipperImage(data);
                }
                setStaticTextLang(pref.getLang());
                if (pref.getLang().equals("TH")) {
                    address_desc.setText(location.getAddressTH());
                    locationDesc.setText(location.getAttDetails_Th());
                    web_desc.setText(location.getWebsite());

                } else if (pref.getLang().equals("ENG")) {
                    address_desc.setText(location.getAddressEng());
                    locationDesc.setText(location.getAttDetails_Eng());
                    web_desc.setText(location.getWebsite());

                } else {
                    address_desc.setText(location.getAddressChi());
                    locationDesc.setText(location.getAttDetails_Chi());
                    web_desc.setText(location.getWebsite());
                }

                tel_desc.setText(location.getTel());

                for (Journey jorney : location.getListJorney()) {

                    TableRow row = new TableRow(getApplicationContext());
                    row.setPadding(10, 10, 10, 10);
                    TableRow.LayoutParams layout_gravity = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
                    layout_gravity.weight = 1.0f;
                    layout_gravity.gravity = Gravity.TOP;

                    TextView tv = new TextView(getApplicationContext());
                    tv.setPadding(0, 0, 0, 0);
                    tv.setLayoutParams(layout_gravity);
                    int count = 1;
                    if (pref.getLang().equals("TH")) {
                        if (!jorney.getCarTh().equals("")) {

                            tv.setText(count + ". " + jorney.getCarTh());
                            count++;
                        }
                        if (!jorney.getBusTh().equals("")) {
                            tv.append("\n\n" + count + ". " + jorney.getBusTh());
                            count++;
                        }
                        if (!jorney.getTrainTh().equals("")) {
                            tv.append("\n\n" + count + ". " + jorney.getTrainTh());
                            count++;
                        }
                        if (!jorney.getAirplaneTh().equals("")) {
                            tv.append("\n\n" + count + ". " + jorney.getAirplaneTh());
                            count++;
                        }

                    } else {
                        if (!jorney.getCarEng().equals("")) {
                            tv.setText(count + ". " + jorney.getCarEng());
                            count++;
                        }
                        if (!jorney.getBusEng().equals("")) {
                            tv.append("\n\n" + count + ". " + jorney.getBusEng());
                            count++;
                        }
                        if (!jorney.getTrainEng().equals("")) {
                            tv.append("\n\n" + count + ". " + jorney.getTrainEng());
                            count++;
                        }
                        if (!jorney.getAirplaneEng().equals("")) {
                            tv.append("\n\n" + count + ". " + jorney.getAirplaneEng());
                            count++;
                        }
                    }

                    tv.setGravity(Gravity.CENTER_VERTICAL);
                    tv.setTextSize(14);
                    tv.setTextColor(Color.BLACK);
                    row.setGravity(Gravity.CENTER_VERTICAL);
//                  row.addView(img);
                    row.addView(tv);

                    contrainerJourney.addView(row);
                }

            }

        }.execute();

    }

    private void setStaticTextLang(String lang) {

        if (lang.equals("ENG")) {
            address_text.setText("Address");
            tel_text.setText("Tel");
            desc_text.setText("Description");
            travel_text.setText("Travel By");
            web_text.setText("Website");
        } else if (lang.equals("TH")) {
            address_text.setText("ที่อยู่");
            tel_text.setText("โทร");
            desc_text.setText("รายละเอียด");
            travel_text.setText("การเดินทาง");
            web_text.setText("เว็ปไซต์");
        } else {
            address_text.setText("Address");
            tel_text.setText("Tel");
            desc_text.setText("Description");
            travel_text.setText("Travel By");
            web_text.setText("Website");
        }

        if (location.getWebsite().equals("")) {
            web_text.setVisibility(View.GONE);
            web_desc.setVisibility(View.GONE);
        } else {
            web_text.setVisibility(View.VISIBLE);
            web_desc.setVisibility(View.VISIBLE);
        }
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


    private void setFlipperImage(ImageLocation res) {

        ImageView image = new ImageView(getApplicationContext());

        aq.id(image).image(PATH + res.getImageLocationFile(), true, true, 0, 0,
                new BitmapAjaxCallback() {
                    @Override
                    public void callback(String url, ImageView iv, Bitmap bm, AjaxStatus status) {
                        Drawable d = new BitmapDrawable(getResources(), bm);
                        iv.setBackgroundDrawable(d);
                    }
                }.header("User-Agent", "android"));

        fliper.addView(image);
    }

    @Override
    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            // when user first touches the screen to swap
            case MotionEvent.ACTION_DOWN: {
                lastX = touchevent.getX();
                break;
            }
            case MotionEvent.ACTION_UP: {
                float currentX = touchevent.getX();

                // if left to right swipe on screen
                if (lastX < currentX) {
                    // If no more View/Child to flip
                    if (fliper.getDisplayedChild() == 0)
                        break;

                    fliper.setInAnimation(this, R.anim.in_from_left);
                    fliper.setOutAnimation(this, R.anim.out_to_right);
                    // Show the next Screen
                    fliper.showNext();
                }

                // if right to left swipe on screen
                if (lastX > currentX) {
                    if (fliper.getDisplayedChild() == 1)
                        break;
                    fliper.setInAnimation(this, R.anim.in_from_right);
                    fliper.setOutAnimation(this, R.anim.out_to_left);
                    // Show The Previous Screen
                    fliper.showPrevious();
                }
                break;
            }
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        String phone = location.getTel();
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phone, null));
        startActivity(intent);
    }
}
