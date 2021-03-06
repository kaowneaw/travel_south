package com.example.matinee.travel_south.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.matinee.travel_south.R;
import com.example.matinee.travel_south.activity.Adapter.CheckInAdapter;
import com.example.matinee.travel_south.activity.Model.ResultEntity;
import com.example.matinee.travel_south.activity.Utill.UserPreference;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.gson.Gson;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;


public class LoginActivity extends AppCompatActivity {

    // Login page
    // Creating Facebook CallbackManager Value
    public static CallbackManager callbackmanager;
    private LoginActivity THIS = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UserPreference pref = new UserPreference(this);
        if (!pref.getUserID().equals("NULL")) {
            startActivity(new Intent(this, MainActivity.class));
            this.finish();

        } else {

            FacebookSdk.sdkInitialize(getApplicationContext());
            setContentView(R.layout.activity_login);
            SettingToobar();
            // Initialize layout button
            ImageButton fbbutton = (ImageButton) findViewById(R.id.login_fb_btn);

            fbbutton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Call private method
                    onFblogin();
                }
            });
            getKeyHash();
        }

    }


    private void SettingToobar() {
        //Toolbar setting
        Toolbar toolbar = (Toolbar) findViewById(R.id.app_bar);
        this.setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {

            getSupportActionBar().setTitle("Travel South");
        } else {
            Toast.makeText(getApplicationContext(), "ActionBar not avaliable", Toast.LENGTH_SHORT).show();
        }
    }

    // Private method to handle Facebook login and callback
    private void onFblogin() {
        callbackmanager = CallbackManager.Factory.create();

        // Set permissions
        LoginManager.getInstance().logInWithReadPermissions(this, Arrays.asList("email", "user_photos", "public_profile"));

        LoginManager.getInstance().registerCallback(callbackmanager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {

                        GraphRequest request = GraphRequest.newMeRequest(
                                loginResult.getAccessToken(),
                                new GraphRequest.GraphJSONObjectCallback() {
                                    @Override
                                    public void onCompleted(JSONObject object, GraphResponse response) {
                                        // Application code
                                        Log.v("LoginActivity", response.toString());
                                        try {
                                            String id = object.getString("id");
                                            String name = object.getString("name");
                                            String email = object.getString("email");
                                            UserPreference pref = new UserPreference(getApplicationContext(), id, name, email,"TH");//Facebook not have username and password
                                            if (pref.commit()) {
                                                callService(id, name, email);
                                                Intent toMain = new Intent(getApplicationContext(), MainActivity.class);
                                                toMain.putExtra("name", name);
                                                toMain.putExtra("email", email);
                                                startActivity(toMain);
                                                THIS.finish();
                                            }


                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });

                        Bundle parameters = new Bundle();
                        parameters.putString("fields", "id,name,email,gender, birthday");
                        request.setParameters(parameters);
                        request.executeAsync();

                    }

                    @Override
                    public void onCancel() {
                        Log.d("Cancel", "On cancel");
                    }

                    @Override
                    public void onError(FacebookException error) {
                        Log.d("Error", error.toString());
                        Toast.makeText(getApplicationContext(), "ไม่สามารถเข้าสู่ระบบได้", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        callbackmanager.onActivityResult(requestCode, resultCode, data);
    }

    private void getKeyHash() {

        PackageInfo info;
        try {
            info = getPackageManager().getPackageInfo("com.example.matinee.travel_south", PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                //String something = new String(Base64.encodeBytes(md.digest()));
                Log.e("hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("name not found", e1.toString());
        } catch (NoSuchAlgorithmException e) {
            Log.e("no such an algorithm", e.toString());
        } catch (Exception e) {
            Log.e("exception", e.toString());
        }
    }


    private void callService(final String memberId, final String memberName, final String memberEmail) {

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

                String url = "http://www.jaa-ikuzo.com/tvs/increseMember.php";
                OkHttpClient client = new OkHttpClient();

                RequestBody formBody = new FormEncodingBuilder()
                        .add("memberId", memberId)
                        .add("memberName", memberName)
                        .add("memberEmail", memberEmail)
                        .build();

                Log.v("memberName =>", memberName);
                Log.v("memberEmail =>", memberEmail);

                Request request = new Request.Builder()
                        .post(formBody)
                        .url(url)
                        .build();
                try {

                    Gson gson = new Gson();
                    Response response = client.newCall(request).execute();
                    String reponse = response.body().string();
                    Log.v("=>", reponse);
                    ResultEntity results = gson.fromJson(reponse, ResultEntity.class);


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
            }

        }.execute();
    }


}
