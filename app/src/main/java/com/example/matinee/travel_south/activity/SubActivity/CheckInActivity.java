package com.example.matinee.travel_south.activity.SubActivity;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.matinee.travel_south.R;
import com.example.matinee.travel_south.activity.Adapter.CheckInAdapter;
import com.example.matinee.travel_south.activity.Adapter.LocationSearchAdapter;
import com.example.matinee.travel_south.activity.Model.FeedEntity;
import com.example.matinee.travel_south.activity.Model.ResultEntity;
import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.List;

public class CheckInActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView lv_checkin;
    private LinearLayout layoutMenu;
    private List<FeedEntity> listFeeds;
    private CheckInAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_in);
        SettingToolbar();
        initWidget();
    }

    private void SettingToolbar() {
        //Toolbar setting
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        this.setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Check-In");
        } else {
            Toast.makeText(getApplicationContext(), "ActionBar not avaliable", Toast.LENGTH_SHORT).show();
        }

    }

    private void initWidget() {

        lv_checkin = (ListView) findViewById(R.id.lv_checkin);
        layoutMenu = (LinearLayout) findViewById(R.id.layoutMenu);
        RelativeLayout btn_gallery = (RelativeLayout) findViewById(R.id.btn_gallery);
        RelativeLayout btn_camera = (RelativeLayout) findViewById(R.id.btn_camera);
        RelativeLayout btn_compress = (RelativeLayout) findViewById(R.id.btn_compress);
        btn_gallery.setOnClickListener(this);
        btn_camera.setOnClickListener(this);
        btn_compress.setOnClickListener(this);

        //Animation Fab
        lv_checkin.setOnTouchListener(new View.OnTouchListener() {

            final int DISTANCE = 3;
            float startY = 0;
            float dist = 0;
            boolean isMenuHide = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();

                if (action == MotionEvent.ACTION_DOWN) {
                    startY = event.getY();
                } else if (action == MotionEvent.ACTION_MOVE) {
                    dist = event.getY() - startY;

                    if ((pxToDp((int) dist) <= -DISTANCE) && !isMenuHide) {
                        isMenuHide = true;
                        hideMenuBar();
                    } else if ((pxToDp((int) dist) > DISTANCE) && isMenuHide) {
                        isMenuHide = false;
                        showMenuBar();
                    }

                    if ((isMenuHide && (pxToDp((int) dist) <= -DISTANCE))
                            || (!isMenuHide && (pxToDp((int) dist) > 0))) {
                        startY = event.getY();
                    }
                } else if (action == MotionEvent.ACTION_UP) {
                    startY = 0;
                }

                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        callService();
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

    private int pxToDp(int px) {

        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        int dp = Math.round(px / (dm.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    private void showMenuBar() {
        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(layoutMenu, View.TRANSLATION_Y, 0);
        animSet.playTogether(anim1);
        animSet.setDuration(300);
        animSet.start();
    }

    private void hideMenuBar() {

        AnimatorSet animSet = new AnimatorSet();
        ObjectAnimator anim1 = ObjectAnimator.ofFloat(layoutMenu, View.TRANSLATION_Y, layoutMenu.getHeight());
        animSet.playTogether(anim1);
        animSet.setDuration(300);
        animSet.start();
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.btn_gallery) {

            Intent i = new Intent(this, PostActivity.class);
            i.putExtra("action", "gallery");
            startActivity(i);

        } else if (v.getId() == R.id.btn_camera) {

            Intent i = new Intent(this, PostActivity.class);
            i.putExtra("action", "camera");
            startActivity(i);

        } else if (v.getId() == R.id.btn_compress) {

        }
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

                String url = "http://www.jaa-ikuzo.com/tvs/getFeeds.php";
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
                    listFeeds = results.getResultsFeed();

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);

                adapter = new CheckInAdapter(getApplicationContext(), listFeeds);
                lv_checkin.setAdapter(adapter);

                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
            }

        }.execute();
    }
}
