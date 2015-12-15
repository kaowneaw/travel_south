package com.example.matinee.travel_south.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.matinee.travel_south.model.AttractionModel;
import com.example.matinee.travel_south.R;

import java.util.List;

/**
 * Created by matinee on 12/13/2015.
 */
public class AttractionAdapter extends RecyclerView.Adapter<AttractionAdapter.ViewHolder> {

    private List<AttractionModel> attractions;
    private Context mContext;

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mName;

        public ViewHolder(View view) {
            super(view);

            mName = (TextView) view.findViewById(R.id.TVrecommend01);

        }
    }

    public AttractionAdapter(Context context, List<AttractionModel> dataset) {
        attractions = dataset;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.attraction_recycelview, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        AttractionModel player = attractions.get(position);

        viewHolder.mName.setText(player.getName());

    }

    @Override
    public int getItemCount() {
        return attractions.size();
    }
}
