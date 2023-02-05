package com.example.mazegame.Activities;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mazegame.R;
import com.example.mazegame.VideoAd;
import com.google.android.material.button.MaterialButton;

public class Activity_NewVideo extends AppCompatActivity {

    public static final String TAG = "PTTT_Activity_VideoNew";

    private MaterialButton main_BTN_sign_in;


    VideoAd coinVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        findViews();
        main_BTN_sign_in.setOnClickListener(view -> start());

        initAds();
    }

    private void start() {
        if (coinVideo.isLoaded()) {
            main_BTN_sign_in.setEnabled(false);
            coinVideo.show();
        } else {
            initAds();
            // move to next level
        }
    }

    VideoAd.CallBack callBack = new VideoAd.CallBack() {
        @Override
        public void unitLoaded() {
            main_BTN_sign_in.setEnabled(true);
        }

        @Override
        public void earned() {

        }

        @Override
        public void canceled() {

        }

        @Override
        public void failed() {

        }
    };

    private void initAds() {
        main_BTN_sign_in.setEnabled(false);
        String UNIT_ID = "ca-app-pub-3940256099942544/5224354917";
        coinVideo = new VideoAd(this, UNIT_ID, callBack);
    }



    private void findViews() {
        main_BTN_sign_in = findViewById(R.id.main_BTN_sign_in);
    }

}