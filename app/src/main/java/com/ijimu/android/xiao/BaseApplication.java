package com.ijimu.android.xiao;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.ijimu.android.ad.TopAdConfig;

public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        TopAdConfig.init(this,"h670cfb9683cd7","5de61b76a5fb46f70f7a3d5f593e0201");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
