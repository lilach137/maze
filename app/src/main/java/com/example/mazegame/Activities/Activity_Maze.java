package com.example.mazegame.Activities;

import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mazegame.DataManager;
import com.example.mazegame.GameMazeView;
import com.example.mazegame.MySensors;
import com.example.mazegame.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;

import java.util.Timer;
import java.util.TimerTask;


public class Activity_Maze extends AppCompatActivity {

    //timer
    private enum TIMER_STATUS {
        OFF,
        RUNNING,
        PAUSE
    }
    private Timer timer = new Timer();
    private int delay=1000;
    private MySensors mySensors;
    private TIMER_STATUS timerStatus = TIMER_STATUS.OFF;
    private int counter = 0;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maze);

    }

    protected void onStop() {
        super.onStop();
        if (timerStatus == TIMER_STATUS.RUNNING) {
            stopTimer();
            timerStatus = TIMER_STATUS.PAUSE;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(timerStatus == TIMER_STATUS.OFF){
            startTimer();
        } else if(timerStatus == TIMER_STATUS.RUNNING ){
            stopTimer();
        }else{
            startTimer();
        }
    }

    private void startTimer() {
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(()-> {
                    ++counter;
                });

            }
        }, 0, delay);
    }

    private void stopTimer() {
        timer.cancel();
        Toast.makeText(Activity_Maze.this, "saved successfully", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }


}




