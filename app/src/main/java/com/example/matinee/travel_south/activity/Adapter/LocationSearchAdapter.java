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
import com.example.matinee.travel_south.activity.Model.LocationEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by KaowNeaw on 1/11/2016.
 */
public class LocationSearchAdapter extends BaseAdapter {

    Context context;
    List<LocationEntity> listLocation; // full data
    private List<LocationEntity> listLocationFilter;
    AQuery aq;
    final String PATH = "http://www.jaa-ikuzo.com/tvs/img/location/";

    public LocationSearchAdapter(Context context, List<LocationEntity> listLocation) {
        this.context = context;
        this.listLocationFilter = listLocation;
        this.listLocation = new ArrayList<>();
        this.listLocation.addAll(listLocationFilter);
    }

    @Override
    public int getCount() {
        return listLocationFilter.size();
    }

    @Override
    public Object getItem(int position) {
        return listLocationFilter.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listLocationFilter.get(position).getLocation_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final widgetHolder Holder;

        if (convertView == null) {

            Holder = new widgetHolder();
            convertView = inflater.inflate(R.layout.item_lv_location_search, null);
            Holder.province_name = (TextView) convertView.findViewById(R.id.location_name);
            Holder.img_location = (ImageView) convertView.findViewById(R.id.img_location);
            convertView.setTag(Holder);

        } else {

            Holder = (widgetHolder) convertView.getTag();
        }

        aq = new AQuery(convertView);
        Holder.province_name.setText(listLocationFilter.get(position).getNameTH());
        aq.id(Holder.img_location).progress(R.id.progress).image(PATH + listLocationFilter.get(position).getImageLocationFile());
        return convertView;
    }

    class widgetHolder {
        ImageView img_location;
        TextView province_name;
    }

    // Filter Class
    public void filter(String charText) {

        listLocationFilter.clear();
        if (charText.length() == 0) {
            listLocationFilter.addAll(listLocation);
        } else {
            for (LocationEntity location : listLocation) {
                if (location.getNameTH().contains(charText)) {
                    listLocationFilter.add(location);
                }
            }
        }
        notifyDataSetChanged();
    }
}
