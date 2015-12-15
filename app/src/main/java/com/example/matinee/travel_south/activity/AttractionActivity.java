package com.example.matinee.travel_south.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.matinee.travel_south.adapter.AttractionAdapter;
import com.example.matinee.travel_south.model.AttractionModel;
import com.example.matinee.travel_south.R;

import java.util.ArrayList;
import java.util.List;

public class AttractionActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attraction);

        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);

        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new AttractionAdapter(this, initAttraction());
        mRecyclerView.setAdapter(mAdapter);

//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
    }
    private List<AttractionModel> initAttraction() {

        AttractionModel attrac1 = new AttractionModel("แหลมพรมเทพ1");
        AttractionModel attrac2 = new AttractionModel("แหลมพรมเทพ2");
        AttractionModel attrac3 = new AttractionModel("แหลมพรมเทพ3");
        AttractionModel attrac4 = new AttractionModel("แหลมพรมเทพ4");
        AttractionModel attrac5 = new AttractionModel("แหลมพรมเทพ5");

        List<AttractionModel> dataset = new ArrayList<AttractionModel>();

        dataset.add(attrac1);
        dataset.add(attrac2);
        dataset.add(attrac3);
        dataset.add(attrac4);
        dataset.add(attrac5);

        return dataset;
    }

}
