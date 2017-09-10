package com.perspective.nishant.Perspective;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.perspective.nishant.Perspective.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HeadlineFragment extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<NewsItem> newsItems;

    private DatabaseReference mdatabase;
    private ProgressDialog progressDialog;

    public HeadlineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if(savedInstanceState == null) {
            progressDialog = new ProgressDialog(this.getContext());
            progressDialog.setMessage("Loading Data...");
            progressDialog.show();
        }

        final View view = inflater.inflate(R.layout.fragment_headline, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.headline_news);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        mdatabase = FirebaseDatabase.getInstance().getReference("Headline News");
        newsItems = new ArrayList<NewsItem>();

        mdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                newsItems.clear();

                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    NewsItem newsItem = snapshot.getValue(NewsItem.class);
                    newsItems.add(newsItem);
                    adapter = new NewsViewAdapter(newsItems, view.getContext());
                    recyclerView.setAdapter(adapter);
                    progressDialog.hide();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(getContext(), "Connection Failed! Check Internet Connection", Toast.LENGTH_LONG).show();
            }
        });

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean("progress_flag", true);
    }

}
