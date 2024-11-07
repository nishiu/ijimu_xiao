package com.ijimu.android.xiao;

import android.app.Application;

import com.ijimu.android.ad.TopAdConfig;

public class BaseApplication extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        TopAdConfig.init(this,"h670cfb9683cd7","5de61b76a5fb46f70f7a3d5f593e0201");
        //h670cfb9683cd7 appid
        //n670cfbdb697ab reward
        //n670cfbdae53e7 insert
        //n670cfbda6c587 banner
    }
}
