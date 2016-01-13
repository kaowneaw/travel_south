package com.example.matinee.travel_south.activity.Fragment;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.matinee.travel_south.R;
import com.example.matinee.travel_south.activity.Utill.GPSTracker;
import com.example.matinee.travel_south.activity.Model.LocationEntity;
import com.example.matinee.travel_south.activity.Model.ResultEntity;
import com.example.matinee.travel_south.activity.TRAVELSOUTH;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MapsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MapsFragment extends Fragment implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener,GoogleMap.OnMarkerClickListener {
    // TODO: Rename parameter arguments, choose names that match

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

    public MapsFragment() {
        // Required empty public constructor
    }


    public static MapsFragment newInstance() {

        return new MapsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (checkPlayServices()) {
            // Building the GoogleApi client
            buildGoogleApiClient();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_maps, container, false);

        initWidget(rootView, savedInstanceState);

        return rootView;
    }

    private void initWidget(View rootView, Bundle savedInstanceState) {

        maps_list = (Button) rootView.findViewById(R.id.maps_list);
        maps_nearBy = (Button) rootView.findViewById(R.id.maps_nearBy);
        type_locationBT = (ImageButton) rootView.findViewById(R.id.type_locationBT);
        mMapView = (MapView) rootView.findViewById(R.id.maps);
        mMapView.onCreate(savedInstanceState);
        mMapView.onResume();
        try {

            MapsInitializer.initialize(getActivity());

        } catch (Exception e) {
            e.printStackTrace();
        }

        googleMap = mMapView.getMap();
        googleMap.setMyLocationEnabled(true);
        googleMap.setOnMarkerClickListener(this);
        maps_list.setOnClickListener(this);
        maps_nearBy.setOnClickListener(this);
        type_locationBT.setOnClickListener(this);

        if (!callGPSTracker()) {
            this.gps.showSettingsAlert(); //show Dialog open GPS
        }


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
        callGPSTracker();
    }


    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
        Toast.makeText(getContext(), "pause", Toast.LENGTH_SHORT).show();
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

        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());

        if (resultCode != ConnectionResult.SUCCESS) {

            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {

                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(), PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {

                Toast.makeText(getActivity(), "This device is not supported.", Toast.LENGTH_LONG).show();
            }
            return false;
        }
        return true;
    }


    protected synchronized void buildGoogleApiClient() {

        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
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
        } else if (v == maps_nearBy) {

            popupNearBy("Distance");
        } else if (v == type_locationBT) {

            popupLocationType("Display on Map");
        } else if (v instanceof RadioButton) {

            setSingleRadio(v.getId()); //for update Radio Group

            if (v.getId() == R.id.rd_all) {
                locationType = 0;// 0 is all
            } else if (v.getId() == R.id.rd_natural) {
                locationType = 1;// 1 is natural
            } else if (v.getId() == R.id.rd_culture) {
                locationType = 2;// 2 is culture
            } else if (v.getId() == R.id.rd_hotel) {
                locationType = 3;// 3 is hotel
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

        this.gps = new GPSTracker(getContext());
        int status;
        boolean isLocation = gps.canGetLocation();
        if (isLocation) {
            status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(getActivity());

            if (status == ConnectionResult.SUCCESS) {

                current_lattitude = gps.getLatitude();
                current_longitude = gps.getLongitude();
                //initial marker on map created
                callService(this.radius, this.current_lattitude, this.current_longitude);
                LatLng loc = new LatLng(current_lattitude, current_longitude);
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loc, 14.0f));

            } else {
                Toast.makeText(getContext(), "Can't Access GPS", Toast.LENGTH_LONG).show();
            }

        }

        return isLocation;
    }

    private void popupNearBy(String title) {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_nearby, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(title);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (current_lattitude != 0 && current_longitude != 0)
                    callService(radius, current_lattitude, current_longitude);
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

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = getActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_locationtype, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setTitle(title);
        dialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (current_lattitude != 0 && current_longitude != 0)
                    callService(radius, current_lattitude, current_longitude);
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
        } else if (locationType == 1) {
            setSingleRadio(radioArr[1].getId());
        } else if (locationType == 2) {
            setSingleRadio(radioArr[2].getId());
        } else if (locationType == 3) {
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

        new AsyncTask<Void, Void, Void>() {

            @Override
            protected Void doInBackground(Void... voids) {
                String url = "http://" + TRAVELSOUTH.ipconfig + "/tvs/getlocation.php";
                OkHttpClient client = new OkHttpClient();
                RequestBody formBody = new FormEncodingBuilder()
                        .add("radians", String.valueOf(radians))
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
                    Log.v("=>", reponse);
                    listLocation = gson.fromJson(reponse, ResultEntity.class);

                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                }

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                for (LocationEntity location : listLocation.getResultsLocation()) {
                    setMarker(location);
                }
            }
        }.execute();
    }

    private void setMarker(LocationEntity data) {

        if (data.getType_id() == 1) {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(data.getLat(), data.getLng())).title(data.getLocationNameTH()).icon(BitmapDescriptorFactory.fromResource(R.drawable.loacte_culture_select)));
        } else if (data.getType_id() == 2) {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(data.getLat(), data.getLng())).title(data.getLocationNameTH()).icon(BitmapDescriptorFactory.fromResource(R.drawable.loacte_hotel_select)));
        } else if (data.getType_id() == 3) {
            googleMap.addMarker(new MarkerOptions().position(new LatLng(data.getLat(), data.getLng())).title(data.getLocationNameTH()).icon(BitmapDescriptorFactory.fromResource(R.drawable.loacte_natural_select)));
        }


    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Toast.makeText(getContext(),""+marker.getTitle(),Toast.LENGTH_SHORT).show();
        return false;
    }
}
