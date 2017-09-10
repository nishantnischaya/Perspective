package com.perspective.nishant.Perspective;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsActivity extends AppCompatActivity {

    private ArrayList<String> stakeholders;
    private ArrayList<String> all_news;
    private TextView stakeholder_preview;
    private TextView editor;
    private TextView date_time;
    private int counter = 0;
    private int totalnews;
    private TextView full_news;
    private TextView fullnews_Title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        full_news = (TextView) findViewById(R.id.full_news);
        ImageView news_image = (ImageView) findViewById(R.id.news_imageView);
        stakeholder_preview = (TextView) findViewById(R.id.stakeholder_preview);
        fullnews_Title = (TextView) findViewById(R.id.fullnews_title);
        editor = (TextView) findViewById(R.id.news_editor);
        date_time = (TextView) findViewById(R.id.news_date_time);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        String headline = bundle.getString("Headline");
        String news = bundle.getString("News");
        String imageURL = bundle.getString("ImageURL");
        stakeholders = bundle.getStringArrayList("Stakeholders");
        all_news = bundle.getStringArrayList("All_news");
        String editor_article = bundle.getString("Editor");
        String date_time_article = bundle.getString("Date_Time");

        totalnews = all_news.size();

        Toolbar toolbar = (Toolbar) findViewById(R.id.news_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(headline);

        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle(headline);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.rgb(255,255,255));

        fullnews_Title.setText(headline);
        editor.setText("Written By: " + editor_article);
        date_time.setText("Published on: " + date_time_article);

        full_news.setText(all_news.get(counter%totalnews));
        full_news.startAnimation(AnimationUtils.loadAnimation(NewsActivity.this, android.R.anim.fade_in));
        stakeholder_preview.setText(stakeholders.get(counter%totalnews));
        Toast.makeText(this, "From the Perspective of " + stakeholders.get(counter%totalnews), Toast.LENGTH_SHORT).show();
        Picasso.with(this).load(imageURL).into(news_image);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = stakeholderDialog(stakeholders);
                dialog.show();
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_news, menu);
        return true;
    }


    public void OnClickChangePerspective(View view){
        counter++;
        full_news.setText(all_news.get(counter%totalnews));
        full_news.startAnimation(AnimationUtils.loadAnimation(NewsActivity.this, android.R.anim.fade_in));
        stakeholder_preview.setText(stakeholders.get(counter%totalnews));
        Toast.makeText(this, "From the Perspective of " + stakeholders.get(counter%totalnews), Toast.LENGTH_SHORT).show();
    }

    public Dialog stakeholderDialog(ArrayList<String> Stakeholders){
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(this);
        String[] choices = Stakeholders.toArray(new String[Stakeholders.size()]);
        final int choice = counter;
        counter = (choice + 1)%totalnews;

        mBuilder.setTitle("Select Stakeholder").setSingleChoiceItems(choices, (choice + 1)%totalnews, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                counter = which;
            }
        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                full_news.setText(all_news.get(counter%totalnews));
                full_news.startAnimation(AnimationUtils.loadAnimation(NewsActivity.this, android.R.anim.fade_in));
                stakeholder_preview.setText(stakeholders.get(counter%totalnews));
                Toast.makeText(NewsActivity.this, "From the Perspective of " + stakeholders.get(counter%totalnews), Toast.LENGTH_SHORT).show();
            }
        }).setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                counter = choice;
            }
        });

        return mBuilder.create();
    }
}
