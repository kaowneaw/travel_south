package com.example.matinee.travel_south.activity.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.example.matinee.travel_south.R;
import com.example.matinee.travel_south.activity.Model.FeedEntity;

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
            Holder.feed_img = (ImageView) convertView.findViewById(R.id.feed_img);
            Holder.feed_location = (TextView) convertView.findViewById(R.id.feed_location);
            convertView.setTag(Holder);

        } else {

            Holder = (widgetHolder) convertView.getTag();
        }

        aq = new AQuery(convertView);
        Holder.feed_location.setText(listFeed.get(position).getNameTH());
        Holder.content.setText(listFeed.get(position).getContent());
        aq.id(Holder.feed_img).image(path + listFeed.get(position).getImg());

        return convertView;
    }

    class widgetHolder {

        ImageView feed_img;
        TextView feed_location;
        TextView content;
    }
}