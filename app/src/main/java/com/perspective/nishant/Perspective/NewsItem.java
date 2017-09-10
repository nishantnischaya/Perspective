package com.perspective.nishant.Perspective;


import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by nisha on 8/24/2017.
 */

public class NewsItem {

    private String headline;
    private String imageURL;
    private String news;
    private String editor;
    private String date_time;
    private HashMap<String, String> stakeholders;

    public NewsItem(){

    }

    public NewsItem(String headline, String imageURL, String news, String editor, String date_time, HashMap<String, String> stakeholders) {
        this.headline = headline;
        this.imageURL = imageURL;
        this.editor = editor;
        this.news = news;
        this.date_time = date_time;
        this.stakeholders = stakeholders;
    }

    public HashMap<String, String> getstakeholders() {
        return stakeholders;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getHeadline() {
        return headline;
    }

    public String getNews() {
        return news;
    }

    public String getEditor() {
        return editor;
    }

    public String getDate_time() {
        return date_time;
    }
}
