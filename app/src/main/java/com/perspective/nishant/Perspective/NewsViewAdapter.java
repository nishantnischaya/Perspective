package com.perspective.nishant.Perspective;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nisha on 8/24/2017.
 */

public class NewsViewAdapter extends RecyclerView.Adapter<NewsViewAdapter.ViewHolder> {


    List<NewsItem> newsItems;
    Context context;

    public NewsViewAdapter(List<NewsItem> newsItems, Context context) {
        this.newsItems = newsItems;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_view, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final NewsItem newsItem = newsItems.get(position);

        holder.headline.setText(newsItem.getHeadline());
        holder.news.setText(newsItem.getNews());
        holder.date_time.setText("Published on: " + newsItem.getDate_time());

        Picasso.with(context).load(newsItem.getImageURL()).into(holder.imageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, NewsActivity.class);

                Bundle bundle = new Bundle();
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

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView headline;
        public TextView news;
        public ImageView imageView;
        public CardView cardView;
        public TextView date_time;

        public ViewHolder(View itemView) {
            super(itemView);
            headline = (TextView) itemView.findViewById(R.id.headline);
            news = (TextView) itemView.findViewById(R.id.news);
            imageView = (ImageView) itemView.findViewById(R.id.news_image);
            date_time = (TextView) itemView.findViewById(R.id.date_time);
            cardView = (CardView) itemView.findViewById(R.id.news_card);
        }
    }
}
