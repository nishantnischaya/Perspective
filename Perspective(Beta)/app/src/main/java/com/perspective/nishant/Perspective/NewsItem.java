package com.perspective.nishant.Perspective;

/**
 * Created by nisha on 8/24/2017.
 */

public class NewsItem {

    private String headline;
    private String news;

    public NewsItem(String headline, String news) {
        this.headline = headline;
        this.news = news;
    }

    public String getHeadline() {
        return headline;
    }

    public String getNews() {
        return news;
    }
}
