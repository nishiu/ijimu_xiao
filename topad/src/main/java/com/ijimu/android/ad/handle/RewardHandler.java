package com.ijimu.android.ad.handle;

import android.app.Activity;

import com.anythink.rewardvideo.api.ATRewardVideoAutoAd;
import com.ijimu.android.ad.impl.RewardWatcher;
import com.ijimu.android.ad.TopAdConfig;

public class RewardHandler {

    public boolean isAdReady(){
        return ATRewardVideoAutoAd.isAdReady(TopAdConfig.REWARD);
    }
    public void showAd(Activity activity,RewardWatcher.VideoRewardCompleteListener completeListener){
        ATRewardVideoAutoAd.entryAdScenario(TopAdConfig.REWARD,TopAdConfig.REWARD_LOCAL_TAG);
        if(ATRewardVideoAutoAd.isAdReady(TopAdConfig.REWARD)){
            ATRewardVideoAutoAd.show(activity,TopAdConfig.REWARD,new RewardWatcher(completeListener));
        }
    }
}
