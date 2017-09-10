package com.perspective.nishant.Perspective;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import com.perspective.nishant.Perspective.R;
/**
 * A simple {@link Fragment} subclass.
 */
public class LatestFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<NewsItem> newsItems;

    public LatestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_latest, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.latest_news);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        newsItems = new ArrayList<>();

        for (int i=0; i<10; i++){
            NewsItem newsItem = new NewsItem("Will Modi's Policy Change India's Future?" , "India's Prime Minister, Narendra Modi is on a 5 days trip to Hongkong. In his meeting with president Xi Jimping he discussed about the possibility of the two nuclear state");
            newsItems.add(newsItem);
        }


        adapter = new NewsViewAdapter(newsItems, this.getContext());
        recyclerView.setAdapter(adapter);

        return view;
    }

}
