package com.perspective.nishant.Perspective;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.perspective.nishant.Perspective.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpinionFragment extends Fragment {

    String[] choices = {"YES", "NO"}; //TO BE TAKEN FROM DATABASE
    int[] choices_data = {70, 30}; //TO BE TAKEN FROM DATABASE

    public OpinionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opinion, container, false);
        setupLatestPieChart(view);
        return view;
    }

    private void setupLatestPieChart(View view) {

        PieChart chart = (PieChart) view.findViewById(R.id.pieChart_Latest);

        List<PieEntry> pieEntries = new ArrayList<>();

        for (int i=0; i<2; i++){
             pieEntries.add(new PieEntry(choices_data[i], choices[i]));
        }

        int[] colors = {Color.GREEN ,Color.RED};
        PieDataSet dataSet = new PieDataSet(pieEntries, "CURRENT RESULTS");
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        chart.setData(data);
        Description description = new Description();
        description.setTextColor(0xffffffff);
        chart.setDescription(description);
        chart.animateY(1000);
        chart.invalidate();
    }

    public void onClickStartNews(View view){
        Intent intent = new Intent(this.getContext(), NewsActivity.class);
        startActivity(intent);
    }

}
