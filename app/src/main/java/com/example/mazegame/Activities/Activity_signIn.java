package com.example.mazegame.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mazegame.DataManager;
import com.example.mazegame.R;
import com.example.mazegame.User;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.BuildConfig;

public class Activity_signIn extends AppCompatActivity {

    private MaterialButton main_BTN_sign_in;
    private ExtendedFloatingActionButton main_FAB_sign_up;
    private TextInputEditText main_EDT_password;
    private TextInputEditText main_EDT_name;
    private TextView main_txt_error;
    private final DataManager dataManager = DataManager.getInstance();
    private RewardedAd mRewardedAd;
    private User currentUser;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        findsView();
        loadVideoAd();
        main_FAB_sign_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), Activity_SignUp.class);
                startActivity(intent);
                finish();
                showVideoAd();
            }
        });

        main_BTN_sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int exist;
                String name = main_EDT_name.getText().toString();
                String password = main_EDT_password.getText().toString();
                exist = dataManager.findUserInDB(name,password);
                if (exist == 0) {
                    main_txt_error.setVisibility(View.INVISIBLE);
                    currentUser = dataManager.getCurrentUser();
                    if (!currentUser.isPremium()){
                        showVideoAd();
                    }
                    Intent intent = new Intent(Activity_signIn.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                if (exist == 1) {
                    Toast.makeText(Activity_signIn.this, "The email is not registered in the system", Toast.LENGTH_SHORT).show();
                    main_EDT_name.getText().clear();
                    main_EDT_password.getText().clear();
                    main_txt_error.setVisibility(View.VISIBLE);
                }
                if (exist == 2) {
                    Toast.makeText(Activity_signIn.this, "Wrong password please try again", Toast.LENGTH_SHORT).show();
                    main_EDT_name.getText().clear();
                    main_EDT_password.getText().clear();
                    main_txt_error.setVisibility(View.VISIBLE);
                }
            }
        });
    }
    private void showVideoAd() {
        mRewardedAd.show(this, new OnUserEarnedRewardListener() {
            @Override
            public void onUserEarnedReward(@NonNull RewardItem rewardItem) {
                Toast.makeText(Activity_signIn.this, "Congr... +1 Live", Toast.LENGTH_SHORT).show();
                loadVideoAd();
            }
        });
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
    public void findsView(){
        main_FAB_sign_up = findViewById(R.id.main_FAB_sign_up);
        main_BTN_sign_in = findViewById(R.id.main_BTN_sign_in);
        main_EDT_password =findViewById(R.id.main_EDT_password);
        main_EDT_name = findViewById(R.id.main_EDT_name);
        main_txt_error = findViewById(R.id.main_txt_error);
    }
}