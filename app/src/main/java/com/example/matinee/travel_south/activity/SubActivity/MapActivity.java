package com.example.matinee.travel_south.activity.SubActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.StrictMode;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ActionMenuView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.androidquery.AQuery;
import com.example.matinee.travel_south.R;
import com.example.matinee.travel_south.activity.Model.LocationEntity;
import com.example.matinee.travel_south.activity.Model.ResultEntity;
import com.example.matinee.travel_south.activity.TRAVELSOUTH;
import com.example.matinee.travel_south.activity.Utill.GPSTracker;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MapActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener, GoogleMap.OnMarkerClickListener {


    private GoogleApiClient mGoogleApiClient;
    private MapView mMapView;
    private GoogleMap googleMap;
    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;
    private Button maps_list, maps_nearBy;
    private ImageButton type_locationBT;
    private RadioButton[] radioArr;
    private ResultEntity listLocation;
    private double current_lattitude, current_longitude, radius = 5.0;
    private int locationType = 0;
    private GPSTracker gps;
    LocationEntity locationIntent;
    ArrayList<Marker> listMarker = new ArrayList<>();
    int markerIndex =0;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        MultiDex.install(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        SettingToobar();
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Bundle data = getIntent().getExtras();
        locationIntent = data.getParcelable("locationObj");
        initWidget(savedInstanceState);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    private void SettingToobar() {
        //Toolbar setting
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        this.setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setTitle("Map");
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

    private void initWidget(Bundle savedInstanceState) {

        maps_list = (Button) findViewById(R.id.maps_list);
        maps_nearBy = (Button) findViewById(R.id.maps_nearBy);
        type_locationBT = (ImageButton) findViewById(R.id.type_locationBT);
        mMapView = (MapView) findViewById(R.id.maps);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {

            MapsInitializer.initialize(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();

        googleMap.setOnMarkerClickListener(this);
        maps_list.setOnClickListener(this);
        maps_nearBy.setOnClickListener(this);
        type_locationBT.setOnClickListener(this);


    }

    @Override
    public void onStart() {
        super.onStart();

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();

        if (locationIntent != null) {
            LatLng loc = new LatLng(locationIntent.getLatitude(), locationIntent.getLongtitude());
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 14.0f));
            callService(this.radius, locationIntent.getLatitude(), locationIntent.getLongtitude());
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }


    private boolean checkPlayServices() {

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {

            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {

                GooglePlayServicesUtil.getErrorDialog(resultCode, this, PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {

                Toast.makeText(this, "This device is not supported.", Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;
    }


    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }


    @Override
    public void onConnected(Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }


    @Override
    public void onClick(View v) {
        if (v == maps_list) {
            //Intent to list
            Intent i = new Intent(this,LocationActivity.class);
            i.putExtra("province_id",locationIntent.getProvince_id());
            startActivity(i);
        } else if (v == maps_nearBy) {
            popupNearBy("Distance");
        } else if (v == type_locationBT) {
            popupLocationType("Display on Map");
        } else if (v instanceof RadioButton) {

            setSingleRadio(v.getId()); //for update Radio Group

            if (v.getId() == R.id.rd_all) {
                locationType = 0;// 0 is all
            } else if (v.getId() == R.id.rd_natural) {
                locationType = 10;// 10 is natural
            } else if (v.getId() == R.id.rd_culture) {
                locationType = 20;// 20 is culture
            } else if (v.getId() == R.id.rd_hotel) {
                locationType = 30;// 30 is hotel
            } else if (v.getId() == R.id.rd_5km) {
                radius = 5.0;
            } else if (v.getId() == R.id.rd_10km) {
                radius = 10.0;
            } else if (v.getId() == R.id.rd_25km) {
                radius = 25.0;
            } else if (v.getId() == R.id.rd_50km) {
                radius = 50.0;
            }
        }
    }

    private boolean callGPSTracker() {

        this.gps = new GPSTracker(this);
        int status;
        boolean isLocation = gps.canGetLocation();
        if (isLocation) {
            status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);

            if (status == ConnectionResult.SUCCESS) {

                current_lattitude = gps.getLatitude();
                current_longitude = gps.getLongitude();
                //initial marker on map created
                callService(this.radius, this.current_lattitude, this.current_longitude);
                LatLng loc = new LatLng(current_lattitude, current_longitude);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 14.0f));

            } else {
                Toast.makeText(getApplicationContext(), "Can't Access GPS", Toast.LENGTH_LONG).show();
            }

        }

        return isLocation;
    }

    private void popupNearBy(String title) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_nearby, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(title);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                if (current_lattitude != 0 && current_longitude != 0)
                callService(radius, locationIntent.getLatitude(), locationIntent.getLongtitude());
            }
        });
        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        radioArr = new RadioButton[4];
        radioArr[0] = (RadioButton) dialogView.findViewById(R.id.rd_5km);
        radioArr[1] = (RadioButton) dialogView.findViewById(R.id.rd_10km);
        radioArr[2] = (RadioButton) dialogView.findViewById(R.id.rd_25km);
        radioArr[3] = (RadioButton) dialogView.findViewById(R.id.rd_50km);
        radioArr[0].setOnClickListener(this);
        radioArr[0].setOnClickListener(this);
        radioArr[1].setOnClickListener(this);
        radioArr[2].setOnClickListener(this);
        radioArr[3].setOnClickListener(this);
        if (radius == 5.0) {
            setSingleRadio(radioArr[0].getId());
        } else if (radius == 10.0) {
            setSingleRadio(radioArr[1].getId());
        } else if (radius == 25.0) {
            setSingleRadio(radioArr[2].getId());
        } else if (radius == 50.0) {
            setSingleRadio(radioArr[3].getId());
        }
        AlertDialog alertDialog = dialogBuilder.create();
        alertDialog.show();

    }

    private void popupLocationType(String title) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_locationtype, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(title);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                callService(radius, locationIntent.getLatitude(), locationIntent.getLongtitude());
            }
        });

        dialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });


        radioArr = new RadioButton[4];
        radioArr[0] = (RadioButton) dialogView.findViewById(R.id.rd_all);
        radioArr[1] = (RadioButton) dialogView.findViewById(R.id.rd_natural);
        radioArr[2] = (RadioButton) dialogView.findViewById(R.id.rd_culture);
        radioArr[3] = (RadioButton) dialogView.findViewById(R.id.rd_hotel);
        radioArr[0].setOnClickListener(this);
        radioArr[0].setOnClickListener(this);
        radioArr[1].setOnClickListener(this);
        radioArr[2].setOnClickListener(this);
        radioArr[3].setOnClickListener(this);
        if (locationType == 0) {
            setSingleRadio(radioArr[0].getId());
        } else if (locationType == 10) {
            setSingleRadio(radioArr[1].getId());
        } else if (locationType == 20) {
            setSingleRadio(radioArr[2].getId());
        } else if (locationType == 30) {
            setSingleRadio(radioArr[3].getId());
        }
        AlertDialog alertDialog = dialogBuilder.create();

        alertDialog.show();

    }


    private void setSingleRadio(int id) {

        for (RadioButton radio : radioArr) {

            if (radio.getId() == id) {
                radio.setChecked(true);
            } else {

                radio.setChecked(false);
            }
        }
    }


    private void callService(final double radians, final double lat, final double lng) {

        googleMap.clear();
        final ProgressDialog dialog = new ProgressDialog(this);

        new AsyncTask<Void, Void, Void>() {


            protected void onPreExecute() {
                dialog.setMessage("Loading...");
                dialog.show();
            }

            @Override
            protected Void doInBackground(Void... voids) {
                String url = "http://www.jaa-ikuzo.com/tvs/getLocationNearby.php";
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormEncodingBuilder()
                        .add("radians", String.valueOf(radians))
                        .add("type", String.valueOf(locationType))
                        .add("lat", String.valueOf(lat))
                        .add("lng", String.valueOf(lng)).build();

                Request request = new Request.Builder()
                        .url(url)
                        .post(formBody)
                        .build();

                try {
                    Gson gson = new Gson();
                    Response response = client.newCall(request).execute();
                    String reponse = response.body().string();
                    Log.v("Map =>", reponse);
                    listLocation = gson.fromJson(reponse, ResultEntity.class);

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.v("error=>", e.getMessage());
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }

                for (int i = 0; i < listLocation.getResultsLocation().size(); i++) {
                    setMarker(listLocation.getResultsLocation().get(i), i);
                }

                // Setting a custom info window adapter for the google map
                googleMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {

                    final String PATH = "http://www.jaa-ikuzo.com/tvs/img/location/";

                    // Use default InfoWindow frame
                    @Override
                    public View getInfoWindow(Marker arg0) {
                        return null;
                    }

                    // Defines the contents of the InfoWindow
                    @Override
                    public View getInfoContents(Marker arg0) {

                        // Getting view from the layout file info_window_layout
                        View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);

                        int index = Integer.parseInt(arg0.getTitle());

                        TextView tv = (TextView) v.findViewById(R.id.name_info_window);
                        ImageView img = (ImageView) v.findViewById(R.id.img_info_window);
                        // Setting the latitude
                        LocationEntity obj = listLocation.getResultsLocation().get(index);
                        tv.setText(obj.getNameTH());
                        Bitmap imgBitmap = getBitmapFromURL(PATH + obj.getImageLocationFile());
                        if (imgBitmap != null) {
                            img.setImageBitmap(imgBitmap);
                        } else {
                            LinearLayout.LayoutParams layoutImgview = new LinearLayout.LayoutParams(80, 80);
                            img.setLayoutParams(layoutImgview);
                            img.setLayoutParams(layoutImgview);
                            img.setImageResource(R.drawable.img_default);
                        }

                        Log.v("=>", PATH + obj.getImageLocationFile());
                        return v;
                    }

                });
                listMarker.get(markerIndex).showInfoWindow();

            }
        }.execute();
    }

    private void setMarker(LocationEntity data, int index) {
        Marker marker = null;
        if (data.getType_id() == 10) {
            marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(data.getLatitude(), data.getLongtitude())).title(String.valueOf(index)).icon(BitmapDescriptorFactory.fromResource(R.drawable.loacte_natural_select)));
        } else if (data.getType_id() == 20) {
            marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(data.getLatitude(), data.getLongtitude())).title(String.valueOf(index)).icon(BitmapDescriptorFactory.fromResource(R.drawable.loacte_culture_select)));
        } else if (data.getType_id() == 30) {
            marker = googleMap.addMarker(new MarkerOptions().position(new LatLng(data.getLatitude(), data.getLongtitude())).title(String.valueOf(index)).icon(BitmapDescriptorFactory.fromResource(R.drawable.loacte_hotel_select)));
        }
        if(marker != null) listMarker.add(marker);
        if( locationIntent.getLatitude() == data.getLatitude() && locationIntent.getLongtitude() == data.getLongtitude()){
            if(marker != null)
                markerIndex = index;
        }
    }




    public static Bitmap getBitmapFromURL(String urlParams) {
        Bitmap image = null;
        try {

            URL url = new URL(urlParams);
            image = BitmapFactory.decodeStream(url.openConnection().getInputStream());

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image;
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(this, "" + marker.getTitle(), Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
