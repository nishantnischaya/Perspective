package com.perspective.nishant.Perspective;

import android.app.Application;

import com.firebase.client.Firebase;

/**
 * Created by nisha on 8/26/2017.
 */

public class Perspective extends Application {

    @Override
    public void onCreate(){
        super.onCreate();

        Firebase.setAndroidContext(this);
    }
}
