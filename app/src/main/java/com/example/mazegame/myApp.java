package com.example.mazegame;

import android.app.Application;
import com.example.mazegame.MyInApp.Item;
import com.google.android.gms.ads.MobileAds;

public class myApp extends Application {

    Item[] items = new Item[] {
            new Item(MyInApp.TYPE.Subscription, ""),
            new Item(MyInApp.TYPE.OneTimeInApp, ""),
    };



    @Override
    public void onCreate() {
        super.onCreate();
        DataManager.initHelper();
        MobileAds.initialize(this);
        MyInApp.initHelper(this, "", items);

    }
}
