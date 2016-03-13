package com.example.matinee.travel_south.activity.Adapter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.androidquery.callback.AjaxCallback;
import com.androidquery.callback.AjaxStatus;
import com.example.matinee.travel_south.R;
import com.example.matinee.travel_south.activity.Model.FeedEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by KaowNeaw on 2/1/2016.
 */
public class CheckInAdapter extends BaseAdapter {

    private Context context;
    private List<FeedEntity> listFeed;
    String path = "http://www.jaa-ikuzo.com/tvs/img/feeds/";
    AQuery aq;

    public CheckInAdapter(Context context, List<FeedEntity> listFeed) {
        this.context = context;
        this.listFeed = listFeed;
    }

    @Override
    public int getCount() {
        return listFeed.size();
    }

    @Override
    public Object getItem(int position) {
        return listFeed.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final widgetHolder Holder;

        if (convertView == null) {

            Holder = new widgetHolder();
            convertView = inflater.inflate(R.layout.item_checkin, null);
            Holder.content = (TextView) convertView.findViewById(R.id.content);
            Holder.user_name = (TextView) convertView.findViewById(R.id.user_name);
            Holder.feed_img = (ImageView) convertView.findViewById(R.id.feed_img);
            Holder.feed_location = (TextView) convertView.findViewById(R.id.feed_location);
            Holder.img_profile = (ImageView) convertView.findViewById(R.id.img_profile);
            Holder.feed_date = (TextView) convertView.findViewById(R.id.feed_date);
            convertView.setTag(Holder);

        } else {

            Holder = (widgetHolder) convertView.getTag();
        }

        aq = new AQuery(convertView);

        String pattern = "yyyy-MM-dd";
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            Date date = format.parse(listFeed.get(position).getDate_time());
            Holder.feed_date.setText(format.format(date));

        } catch (ParseException e) {
            e.printStackTrace();
        }

        Holder.feed_location.setText(listFeed.get(position).getNameTH());
        Holder.user_name.setText(listFeed.get(position).getMember_name());
        Holder.content.setText(listFeed.get(position).getContent());
        aq.id(Holder.img_profile).image("https://graph.facebook.com/" + listFeed.get(position).getMember_id() + "/picture");
        aq.ajax(path + listFeed.get(position).getImg(), Bitmap.class, 0, new AjaxCallback<Bitmap>() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void callback(String url, Bitmap object, AjaxStatus status) {
                super.callback(url, object, status);
                //You will get Bitmap from object.
                Drawable d = new BitmapDrawable(context.getResources(), object);
                Holder.feed_img.setBackground(d);
            }
        });

        return convertView;
    }

    class widgetHolder {

        ImageView feed_img;
        TextView feed_location;
        TextView feed_date;
        TextView content;
        TextView user_name;
        ImageView img_profile;
    }
}
