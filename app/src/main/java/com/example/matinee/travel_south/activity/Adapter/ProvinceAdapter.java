package com.example.matinee.travel_south.activity.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.matinee.travel_south.R;
import com.example.matinee.travel_south.activity.Model.ProvinceEntity;

import java.util.List;

/**
 * Created by KaowNeaw on 1/11/2016.
 */
public class ProvinceAdapter extends BaseAdapter {

    Context context;
    List<ProvinceEntity> listProvince;


    public ProvinceAdapter(Context context, List<ProvinceEntity> listProvince) {
        this.context = context;
        this.listProvince = listProvince;
    }

    @Override
    public int getCount() {
        return listProvince.size();
    }

    @Override
    public Object getItem(int position) {
        return listProvince.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listProvince.get(position).getProvince_id();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final widgetHolder Holder;

        if (convertView == null) {

            Holder = new widgetHolder();
            convertView = inflater.inflate(R.layout.item_lv_province, null);
            Holder.province_name = (TextView) convertView.findViewById(R.id.province_name);
            convertView.setTag(Holder);

        } else {

            Holder = (widgetHolder) convertView.getTag();
        }

        Holder.province_name.setText(listProvince.get(position).getProvince_name());

        return convertView;
    }

    class widgetHolder {

        TextView province_name;
    }
}
