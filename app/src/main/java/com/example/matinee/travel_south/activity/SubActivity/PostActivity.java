package com.example.matinee.travel_south.activity.SubActivity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.matinee.travel_south.R;
import com.example.matinee.travel_south.activity.Model.LocationEntity;
import com.example.matinee.travel_south.activity.Model.ResultEntity;
import com.example.matinee.travel_south.activity.Utill.RealPathUtil;
import com.google.gson.Gson;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class PostActivity extends AppCompatActivity implements View.OnClickListener {

    private String action;
    private final int GALLERY = 1;
    private final int CAMERA = 2;
    private final int SEARCH_LOCATION = 3;
    private ImageView img;
    private EditText content;
    private LocationEntity location;
    private TextView locationName;
    private Uri srcImage;
    String realPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        action = getIntent().getStringExtra("action");
        SettingToolbar();
        initWidget();
        if (action.equals("gallery")) {
            openGallery();
        } else {
            openCamera();
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
        } else if (id == R.id.post) {

            if (this.location != null && realPath != null) {
                String content = this.content.getText().toString();
                File imgFile = new File(realPath);
                Toast.makeText(getApplicationContext(), realPath, Toast.LENGTH_LONG).show();
                Toast.makeText(getApplicationContext(), imgFile.toString(), Toast.LENGTH_LONG).show();
                Log.v("imgFile", imgFile.toString());
                callService(content, imgFile);//posting
            } else {
                Toast.makeText(getApplicationContext(), "Plese Select Location", Toast.LENGTH_SHORT).show();
            }
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.post, menu);
        return true;
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

        img = (ImageView) findViewById(R.id.img);
        content = (EditText) findViewById(R.id.content);
        TableRow checkinLocation = (TableRow) findViewById(R.id.checkinLocation);
        locationName = (TextView) findViewById(R.id.locationName);
        checkinLocation.setOnClickListener(this);
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), GALLERY);
    }

    private void openCamera() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cameraIntent, CAMERA);
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == GALLERY) {

            if (resultCode == RESULT_OK) {
                if (data != null) {
//                    try {
//                        Bitmap image = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
//                        img.setImageBitmap(image);
//                        // SDK < API11
//                        if (Build.VERSION.SDK_INT < 11)
//                            realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());
//
//                            // SDK >= 11 && SDK < 19
//                        else if (Build.VERSION.SDK_INT < 19)
//                            realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());
//
//                            // SDK > 19 (Android 4.4)
//                        else
//                            realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
//
//
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inSampleSize = 4;
                    Uri selectedImageUri = data.getData();
                    realPath = getPath(selectedImageUri);
                    Bitmap bitmap = BitmapFactory.decodeFile(realPath);
                    int height = bitmap.getHeight(), width = bitmap.getWidth();

                    if (height > 1280 && width > 960) {
                        Bitmap imgbitmap = BitmapFactory.decodeFile(realPath, options);
                        Drawable d = new BitmapDrawable(getResources(), imgbitmap);
                        img.setBackground(d);

                        System.out.println("Need to resize");

                    } else {

                        Drawable d = new BitmapDrawable(getResources(), bitmap);
                        img.setBackground(d);
                        System.out.println("WORKS");
                    }

                }
            } else if (resultCode == RESULT_CANCELED) {

                Toast.makeText(getApplicationContext(), "Cancelled", Toast.LENGTH_SHORT).show();
            }

        } else if (requestCode == CAMERA) {

            if (resultCode == RESULT_OK) {

                Bitmap image = (Bitmap) data.getExtras().get("data");
                img.setImageBitmap(image);
            }

        } else if (requestCode == SEARCH_LOCATION) {

            if (resultCode == RESULT_OK) {

                location = data.getParcelableExtra("locationObj");
                locationName.setText(location.getNameTH());
            }
        }
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.checkinLocation) {
            //select loaction
            startActivityForResult(new Intent(this, SelectLocationActivity.class), SEARCH_LOCATION);
        }
    }

    private void callService(final String mycontent, final File src) {

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

                String url = "http://www.jaa-ikuzo.com/tvs/postFeeds.php";
                OkHttpClient client = new OkHttpClient();
                client.setConnectTimeout(60, TimeUnit.SECONDS); // connect timeout
                client.setReadTimeout(60, TimeUnit.SECONDS);    // socket tim

                final MediaType MEDIA_TYPE_PNG = MediaType.parse("image/png");

                RequestBody requestBody = new MultipartBuilder()
                        .type(MultipartBuilder.FORM)
                        .addFormDataPart("content", mycontent)
                        .addFormDataPart("locationId", location.getLocation_id() + "")
                        .addFormDataPart("memberId", "1")//memberid
                        .addFormDataPart("myfile", src.getName(), RequestBody.create(MEDIA_TYPE_PNG, reduceSizeFile(src)))
                        .build();

                Request request = new Request.Builder()
                        .post(requestBody)
                        .url(url)
                        .build();

                try {

                    Gson gson = new Gson();
                    Response response = client.newCall(request).execute();
                    String reponse = response.body().string();
                    Log.v("result=>", reponse);
                    ResultEntity results = gson.fromJson(reponse, ResultEntity.class);
                    if (results.isStatus()) {
                        finish();
                    }

                } catch (IOException e) {

                    e.printStackTrace();
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

    public File reduceSizeFile(File file) {
        try {

            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 4;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 70;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);

            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);

            return file;

        } catch (Exception e) {
            return null;
        }
    }

    /**
     * helper to retrieve the path of an image URI
     */
    public String getPath(Uri uri) {
        // just some safety built in
        if (uri == null) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        if (cursor != null) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        }
        // this is our fallback here
        return uri.getPath();
    }


}
