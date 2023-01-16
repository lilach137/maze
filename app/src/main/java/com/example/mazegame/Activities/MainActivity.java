package com.example.mazegame.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.mazegame.Adapter_level;
import com.example.mazegame.DataManager;
import com.example.mazegame.Level;
import com.example.mazegame.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.firebase.BuildConfig;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView main_LST_levels;
    private RewardedAd mRewardedAd;
    private FrameLayout main_LAY_banner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_LAY_banner = findViewById(R.id.main_LAY_banner);
        main_LST_levels = findViewById(R.id.main_LST_levels);

        initAdapter();
        //need to be where we are showing the banner
        //showBanner();

    }

    private void initAdapter(){
        ArrayList<Level> levels = DataManager.generateLevels();
        Adapter_level adapter_level = new Adapter_level(this, levels);
        main_LST_levels.setHasFixedSize(true);
        main_LST_levels.setItemAnimator(new DefaultItemAnimator());
        main_LST_levels.setAdapter(adapter_level);

        adapter_level.setLevelClickListener(new Adapter_level.LevelListener() {
            @Override
            public void click(Level level, int position) {
                Toast.makeText(MainActivity.this,"level "+ level.getNum(), Toast.LENGTH_SHORT).show();
                int nRows = level.getRows();
                int nColumns = level.getColumns();
                Intent intent = new Intent(MainActivity.this, Activity_Maze.class);

                intent.putExtra("rows", nRows);
                intent.putExtra("columns",nColumns);
                startActivity(intent);
            }
        });
    }

    private void showBanner() {
        String UNIT_ID = "ca-app-pub-3940256099942544/6300978111";
        if (BuildConfig.DEBUG) {
            UNIT_ID = "ca-app-pub-3940256099942544/6300978111";
        }

        AdView adView = new AdView(this);
        adView.setAdUnitId(UNIT_ID);
        main_LAY_banner.addView(adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdSize adSize = getAdSize();
        adView.setAdSize(adSize);
        adView.loadAd(adRequest);
    }


    private AdSize getAdSize() {
        // Step 2 - Determine the screen width (less decorations) to use for the ad width.
        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float widthPixels = outMetrics.widthPixels;
        float density = outMetrics.density;

        int adWidth = (int) (widthPixels / density);

        // Step 3 - Get adaptive ad size and return for setting on the ad view.
        return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, adWidth);
    }

    private void loadVideoAd() {
       // action_a.setEnabled(false);
        String UNIT_ID = "ca-app-pub-3940256099942544/5224354917";
        if (BuildConfig.DEBUG) {
            UNIT_ID = "ca-app-pub-3940256099942544/5224354917";
        }

        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, UNIT_ID,
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d("pttt", loadAdError.toString());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        //action_b.setEnabled(true);
                        Log.d("pttt", "Ad was loaded.");
                    }
                });
    }


    private void showVideoAd() {
        mRewardedAd.show(this, new OnUserEarnedRewardListener() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                Toast.makeText(MainActivity.this, "Congr... +1 Live", Toast.LENGTH_SHORT).show();
                loadVideoAd();
            }
        });
    }



}