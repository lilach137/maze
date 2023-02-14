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
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.BuildConfig;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Activity_signIn extends AppCompatActivity {

    private MaterialButton main_BTN_sign_in;
    private ExtendedFloatingActionButton main_FAB_sign_up;
    private TextInputEditText main_EDT_password;
    private TextInputEditText main_EDT_name;
    private final DataManager dataManager = DataManager.getInstance();
    private RewardedAd mRewardedAd;

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
                String name = main_EDT_name.getText().toString();
                String password = main_EDT_password.getText().toString();
                login(name,password);
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
    }
    private void login(String name, String password) {
        final boolean[] isExist = {false};
        DatabaseReference myRef = dataManager.getRealTimeDB().getReference("Users");
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.child("name").getValue().equals(name)) {
                        isExist[0] = true;
                        if (dataSnapshot.child("password").getValue().equals(password)) {
                            Intent intent = new Intent(Activity_signIn.this, MainActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("name", dataSnapshot.child("name").getValue().toString());
                            bundle.putString("premium", dataSnapshot.child("premium").getValue().toString());
                            bundle.putInt("lastLevel", Integer.parseInt(dataSnapshot.child("lastLevel").getValue().toString()));
                            intent.putExtra("Bundle", bundle);
                            startActivity(intent);
                            finish();
                        }else{
                            Toast.makeText(Activity_signIn.this, "The password is incorrect, please try again", Toast.LENGTH_SHORT).show();
                        }
                    }
                }

                if (!isExist[0]) {
                    Toast.makeText(Activity_signIn.this, "The name is not registered in the system", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }

        });


    }
    }