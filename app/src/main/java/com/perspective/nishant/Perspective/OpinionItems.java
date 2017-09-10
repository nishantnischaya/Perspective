package com.perspective.nishant.Perspective;

import java.util.HashMap;

/**
 * Created by nisha on 9/3/2017.
 */

public class OpinionItems {

    int yes;
    int no;
    String question;
    HashMap<String, Boolean> usersVoted;
    String article;

    public OpinionItems(){

    }

    public String getArticle() {
        return article;
    }

    public HashMap<String, Boolean> getUsersVoted() {
        return usersVoted;
    }

    public int getYes() {
        return yes;
    }

    public int getNo() {
        return no;
    }

    public String getQuestion() {
        return question;
    }
}
