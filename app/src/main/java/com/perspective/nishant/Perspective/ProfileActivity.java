package com.perspective.nishant.Perspective;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.makeramen.roundedimageview.RoundedImageView;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

public class ProfileActivity extends AppCompatActivity {

    private ListView listView;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseUser user;
    private TextView username;
    private String name;
    private TextView SignedInvia;
    private String providerId;
    private Uri photoUrl;
    private RoundedImageView profile_picture;

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthStateListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        toolbar.setTitleTextColor(0xFFFFFFFF);
        setSupportActionBar(toolbar);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            for (UserInfo profile : user.getProviderData()) {
                // Id of the provider (ex: google.com)
                providerId = profile.getProviderId();

                // UID specific to the provider
                String uid = profile.getUid();

                // Name, email address, and profile photo Url
                name = profile.getDisplayName();
                providerId = profile.getEmail();
                photoUrl = profile.getPhotoUrl();

            };

            username = (TextView) findViewById(R.id.username);
            SignedInvia = (TextView) findViewById(R.id.signedInvia);
            profile_picture = (RoundedImageView) findViewById(R.id.Profile_Picture);

            if(name != null) {
                username.setText(name);
                SignedInvia.setText("Signed in via Google");
                Transformation transformation = new RoundedTransformationBuilder()
                        .borderColor(Color.BLACK)
                        .borderWidthDp(3)
                        .cornerRadiusDp(30)
                        .oval(false)
                        .build();

                Picasso.with(this).load(photoUrl).fit().transform(transformation).into(profile_picture);
            }else{
                username.setText(" ");
                SignedInvia.setText("Please Sign in via Google to load Profile info");
                Transformation transformation = new RoundedTransformationBuilder()
                        .borderColor(Color.BLACK)
                        .borderWidthDp(3)
                        .cornerRadiusDp(30)
                        .oval(false)
                        .build();

                Picasso.with(this).load("http://www.iconsfind.com/wp-content/uploads/2015/10/20151012_561bae94ea030.png").fit().transform(transformation).into(profile_picture);
            }

        }



        mAuthStateListener = new FirebaseAuth.AuthStateListener(){

            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser() == null){
                    Intent intent = new Intent(ProfileActivity.this, SplashActivity.class);
                    startActivity(intent);
                }
            }
        };


        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener(){

            @Override
            public void onItemClick(AdapterView<?> listView, View view, int position, long id) {
                if(position == 0){
                    SharedPreferences pref = getSharedPreferences("ActivityPREF", Context.MODE_PRIVATE);
                    SharedPreferences.Editor edt = pref.edit();
                    edt.putBoolean("activity_executed", false);
                    edt.commit();
                    Toast.makeText(ProfileActivity.this, "Logo" +
                            "ut Successful", Toast.LENGTH_LONG).show();
                    FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                    user.delete()
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        //expected
                                    }
                                }
                            });
                    mAuth.signOut();
                }

                if(position == 1){
                    AlertDialog.Builder mBuilder = new AlertDialog.Builder(ProfileActivity.this);
                    mBuilder.setTitle("App Version");
                    mBuilder.setMessage("Perspective [Beta v.1.0]");
                    mBuilder.show();
                }

                if(position == 2){
                    Intent intent = new Intent(Intent.ACTION_SENDTO); // it's not ACTION_SEND
                    intent.setType("text/plain");
                    intent.setData(Uri.parse("mailto:nishantnischaya@gmail.com")); // or just "mailto:" for blank
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK); // this will make such that when user returns to your app, your app is displayed, instead of the email app.
                    startActivity(intent);
                }
            }
        };

        listView = (ListView) findViewById(R.id.listView);
        listView.setOnItemClickListener(itemClickListener);
    }
}
