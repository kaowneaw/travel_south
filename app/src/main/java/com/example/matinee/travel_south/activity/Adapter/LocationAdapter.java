package com.example.matinee.travel_south.activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidquery.AQuery;
import com.example.matinee.travel_south.R;
import com.example.matinee.travel_south.activity.Model.LocationEntity;
import com.example.matinee.travel_south.activity.Model.ProvinceEntity;

import java.util.List;

/**
 * Created by KaowNeaw on 1/11/2016.
 */
public class LocationAdapter extends BaseAdapter {

    Context context;
    List<LocationEntity> listLocation;
    AQuery aq;
    final String PATH = "http://www.jaa-ikuzo.com/tvs/img/location/";

    public LocationAdapter(Context context, List<LocationEntity> listLocation) {
        this.context = context;
        this.listLocation = listLocation;

    }

    @Override
    public int getCount() {
        return listLocation.size();
    }

    @Override
    public Object getItem(int position) {
        return listLocation.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listLocation.get(position).getLocation_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final widgetHolder Holder;

        if (convertView == null) {

            Holder = new widgetHolder();
            convertView = inflater.inflate(R.layout.item_lv_location, null);
            Holder.province_name = (TextView) convertView.findViewById(R.id.location_name);
            Holder.location_desc = (TextView) convertView.findViewById(R.id.location_desc);
            Holder.img_location = (ImageView) convertView.findViewById(R.id.img_location);
            convertView.setTag(Holder);

        } else {

            Holder = (widgetHolder) convertView.getTag();
        }

        aq = new AQuery(convertView);
        Holder.province_name.setText(listLocation.get(position).getNameTH());
        Holder.location_desc.setText(listLocation.get(position).getAddressTH());

        aq.id(Holder.img_location).progress(R.id.progress).image(PATH + listLocation.get(position).getImageLocationFile());
        return convertView;
    }

    class widgetHolder {
        ImageView img_location;
        TextView province_name;
        TextView location_desc;
    }
}
