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
import com.example.mazegame.User;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.BuildConfig;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private FirebaseAnalytics mFirebaseAnalytics;
    private RecyclerView main_LST_levels;

    private FrameLayout main_LAY_banner;
    private boolean premium;
    private int lastLevel = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_LAY_banner = findViewById(R.id.main_LAY_banner);
        main_LST_levels = findViewById(R.id.main_LST_levels);

        if (DataManager.getInstance().getCurrentUser() == null) {
            Bundle bundle = getIntent().getBundleExtra("Bundle");
            String name = bundle.getString("name");
            premium = Boolean.parseBoolean(bundle.getString("premium"));
            lastLevel = bundle.getInt("lastLevel");
            DataManager.getInstance().setCurrentUser(new User(name, premium , lastLevel));
        } else{
            premium = DataManager.getInstance().getCurrentUser().isPremium();
            lastLevel = DataManager.getInstance().getCurrentUser().getLastLevel();
        }

        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
        mFirebaseAnalytics.setUserId("test0");


        if (!premium) {
            showBanner();
        }
        initAdapter(premium ,lastLevel);

    }

    private void initAdapter(boolean premium , int lastLevel){
        ArrayList<Level> levels = DataManager.generateLevels();
        Adapter_level adapter_level = new Adapter_level(this, levels, premium , lastLevel);

        main_LST_levels.setHasFixedSize(true);
        main_LST_levels.setItemAnimator(new DefaultItemAnimator());
        main_LST_levels.setAdapter(adapter_level);



        adapter_level.setLevelClickListener(new Adapter_level.LevelListener() {
            @Override
            public void click(Level level, int position,boolean premium) {
                Toast.makeText(MainActivity.this,"level "+ level.getNum(), Toast.LENGTH_SHORT).show();
                int nRows = level.getRows();
                int nColumns = level.getColumns();
                int nLeve = level.getNum();
                Intent intent = new Intent(MainActivity.this, Activity_Maze.class);
                intent.putExtra("rows", nRows);
                intent.putExtra("columns",nColumns);
                intent.putExtra("level",nLeve);
                startActivity(intent);

            }
        });
    }

    private void showBanner() {
        String UNIT_ID = "ca-app-pub-3940256099942544/6300978111";
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








}