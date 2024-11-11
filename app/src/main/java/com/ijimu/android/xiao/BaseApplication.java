package com.ijimu.android.xiao;

import static com.ijimu.android.xiao.Constants.PRIVACY_AGREE;

import android.app.Application;
import android.content.Context;

import androidx.multidex.MultiDex;

import com.ijimu.android.ad.TopAdConfig;
import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.ContextHolder;
import com.ijimu.android.game.UIThread;
import com.ijimu.android.game.util.LocalStorage;

public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        UIThread.init();
        TopAdConfig.init(this,"h670cfb9683cd7","5de61b76a5fb46f70f7a3d5f593e0201");
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
