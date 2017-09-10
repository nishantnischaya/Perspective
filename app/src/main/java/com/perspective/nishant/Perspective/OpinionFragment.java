package com.perspective.nishant.Perspective;



import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.ViewSwitcher;


import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.perspective.nishant.Perspective.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class OpinionFragment extends Fragment{

    private TextView poll_ques;
    private OpinionItems opinionItems;
    private View view;
    private ProgressDialog progressDialog;
    private DatabaseReference mdatabase;
    private ViewSwitcher viewSwitcher;
    private Button yesButton;
    private Button noButton;
    private Button relatedStory;
    private int total;

    public OpinionFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_opinion, container, false);
        progressDialog = new ProgressDialog(view.getContext());
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        poll_ques = (TextView) view.findViewById(R.id.poll_ques);
        viewSwitcher = (ViewSwitcher) view.findViewById(R.id.view_switcher);
        yesButton = (Button) view.findViewById(R.id.yesButton);
        noButton = (Button) view.findViewById(R.id.noButton);
        relatedStory = (Button) view.findViewById(R.id.related_story);

        yesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                yesButtonPressed();
            }
        });

        noButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                noButtonPressed();
            }
        });

        relatedStory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickStartRelatedNews();
            }
        });

        mdatabase = FirebaseDatabase.getInstance().getReference("Opinion");

            mdatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        if(snapshot.getValue(OpinionItems.class) != null) {
                            opinionItems = snapshot.getValue(OpinionItems.class);
                            poll_ques.setText(opinionItems.getQuestion());
                            invalidate();
                            total = (opinionItems.getYes() + opinionItems.getNo())/100;
                            setupLatestPieChart(view, opinionItems.getYes()/total, 100 - (opinionItems.getYes()/total));
                            HashMap<String, Boolean> map = opinionItems.getUsersVoted();
                            FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
                            if(map.containsKey(currentFirebaseUser.getUid())){
                                Animation in = AnimationUtils.loadAnimation(view.getContext(), android.R.anim.slide_in_left);
                                viewSwitcher.setInAnimation(in);
                                viewSwitcher.showNext();
                            }
                        }
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

        progressDialog.hide();

        return view;
    }

    private void noButtonPressed() {
        Animation in = AnimationUtils.loadAnimation(view.getContext(), android.R.anim.slide_in_left);
        viewSwitcher.setInAnimation(in);
        viewSwitcher.showNext();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        HashMap<String, Boolean> map = new HashMap<>();
        map.putAll(opinionItems.getUsersVoted());
        map.put(currentFirebaseUser.getUid(), true);
        mdatabase.child("sdagdgdfgdfg").child("usersVoted").setValue(map);
        mdatabase.child("sdagdgdfgdfg").child("no").setValue(opinionItems.getNo() + 1);
    }

    private void yesButtonPressed() {
        Animation in = AnimationUtils.loadAnimation(view.getContext(), android.R.anim.slide_in_left);
        viewSwitcher.setInAnimation(in);
        viewSwitcher.showNext();
        FirebaseUser currentFirebaseUser = FirebaseAuth.getInstance().getCurrentUser() ;
        HashMap<String, Boolean> map = new HashMap<>();
        map.putAll(opinionItems.getUsersVoted());
        map.put(currentFirebaseUser.getUid(), true);
        mdatabase.child("sdagdgdfgdfg").child("usersVoted").setValue(map);
        mdatabase.child("sdagdgdfgdfg").child("yes").setValue(opinionItems.getYes() + 1);
    }


    private void setupLatestPieChart(View view, int yes, int no) {

        PieChart chart = (PieChart) view.findViewById(R.id.pieChart_Opinion);

        List<PieEntry> pieEntries = new ArrayList<>();

        pieEntries.add(new PieEntry(yes, "YES"));
        pieEntries.add(new PieEntry(no, "NO"));

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

        return;
    }


    public void onClickStartRelatedNews(){
        DatabaseReference newsDatabase = FirebaseDatabase.getInstance().getReference("Headline News");
        final Bundle bundle = new Bundle();

        newsDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.getKey().equals(opinionItems.getArticle())){
                            NewsItem newsItem = snapshot.getValue(NewsItem.class);
                            Intent intent = new Intent(view.getContext(), NewsActivity.class);
                            bundle.putString("Headline", newsItem.getHeadline());
                            bundle.putString("News", newsItem.getNews());
                            bundle.putString("ImageURL", newsItem.getImageURL());
                            bundle.putString("Date_Time", newsItem.getDate_time());
                            bundle.putString("Editor", newsItem.getEditor());
                            ArrayList<String> stakeholders = new ArrayList<String>(newsItem.getstakeholders().keySet());
                            ArrayList<String> all_news = new ArrayList<String>();

                            for(String news: stakeholders){
                                all_news.add(newsItem.getstakeholders().get(news));
                            }

                            bundle.putStringArrayList("Stakeholders", stakeholders);
                            bundle.putStringArrayList("All_news", all_news);

                            intent.putExtras(bundle);
                            startActivity(intent);
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void invalidate() {
        poll_ques.post(new Runnable() {
            @Override
            public void run() {
                poll_ques.invalidate();
            }
        });
    }

}
