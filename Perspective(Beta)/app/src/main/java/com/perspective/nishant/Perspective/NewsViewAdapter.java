package com.perspective.nishant.Perspective;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

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
    public void onBindViewHolder(ViewHolder holder, int position) {
        NewsItem newsItem = newsItems.get(position);

        holder.headline.setText(newsItem.getHeadline());
        holder.news.setText(newsItem.getNews());

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, NewsActivity.class);
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
        public CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            headline = (TextView) itemView.findViewById(R.id.headline);
            news = (TextView) itemView.findViewById(R.id.news);

            cardView = (CardView) itemView.findViewById(R.id.news_card);
        }
    }
}
