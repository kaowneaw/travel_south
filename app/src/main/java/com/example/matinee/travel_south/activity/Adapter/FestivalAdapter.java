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
import com.example.matinee.travel_south.activity.Model.FestivalEntity;

import java.util.List;

/**
 * Created by KaowNeaw on 2/1/2016.
 */
public class FestivalAdapter extends BaseAdapter {

    private Context context;
    private List<FestivalEntity> listFestival;
    String path = "http://www.jaa-ikuzo.com/tvs/img/festival/";
    AQuery aq;

    public FestivalAdapter(Context context, List<FestivalEntity> listFestival) {
        this.context = context;
        this.listFestival = listFestival;
    }

    @Override
    public int getCount() {
        return listFestival.size();
    }

    @Override
    public Object getItem(int position) {
        return listFestival.get(position);
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
            convertView = inflater.inflate(R.layout.item_festival, null);
            Holder.fes_name = (TextView) convertView.findViewById(R.id.fes_name);
            Holder.feed_img = (ImageView) convertView.findViewById(R.id.img_fes);
            Holder.fes_locate = (TextView) convertView.findViewById(R.id.fes_locate);
            Holder.fes_desc = (TextView) convertView.findViewById(R.id.fes_desc);
            Holder.fes_month = (TextView) convertView.findViewById(R.id.fes_month);
            convertView.setTag(Holder);

        } else {

            Holder = (widgetHolder) convertView.getTag();
        }

        aq = new AQuery(convertView);
        Holder.fes_name.setText(listFestival.get(position).getFestivalName());
        Holder.fes_locate.setText(listFestival.get(position).getFesLocate());
        Holder.fes_desc.setText(listFestival.get(position).getFesDetail());
        Holder.fes_month.setText("เดือน " + listFestival.get(position).getMonthName());
        aq.id(Holder.feed_img).image(path + listFestival.get(position).getImageFesfile());

        return convertView;
    }

    class widgetHolder {

        ImageView feed_img;
        TextView fes_name;
        TextView fes_month;
        TextView fes_locate;
        TextView fes_desc;
    }
}
