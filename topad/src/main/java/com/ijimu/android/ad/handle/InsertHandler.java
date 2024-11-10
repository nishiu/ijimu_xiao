package com.ijimu.android.ad.handle;

import android.app.Activity;

import com.anythink.interstitial.api.ATInterstitialAutoAd;
import com.ijimu.android.ad.TopAdConfig;

public class InsertHandler {

    public void showAd(Activity activity){
        ATInterstitialAutoAd.entryAdScenario(TopAdConfig.INSERT, TopAdConfig.REWARD_LOCAL_TAG);
        if (ATInterstitialAutoAd.isAdReady(TopAdConfig.INSERT)) {
            ATInterstitialAutoAd.show(activity, TopAdConfig.INSERT, null);
        }
    }
}
