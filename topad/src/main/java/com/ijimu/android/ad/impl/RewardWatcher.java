package com.ijimu.android.ad.impl;

import android.util.Log;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.rewardvideo.api.ATRewardVideoAutoEventListener;
import com.ijimu.android.ad.TopAdConfig;

public class RewardWatcher extends ATRewardVideoAutoEventListener {

    public interface VideoRewardCompleteListener{
        void reward(boolean success);
    }
    private VideoRewardCompleteListener completeListener;

    public RewardWatcher(VideoRewardCompleteListener completeListener){
        this.completeListener = completeListener;
    }

    @Override
    public void onRewardedVideoAdPlayStart(ATAdInfo atAdInfo) {

    }

    @Override
    public void onRewardedVideoAdPlayEnd(ATAdInfo atAdInfo) {

    }

    @Override
    public void onRewardedVideoAdPlayFailed(AdError adError, ATAdInfo atAdInfo) {

    }

    @Override
    public void onRewardedVideoAdClosed(ATAdInfo atAdInfo) {

    }

    @Override
    public void onRewardedVideoAdPlayClicked(ATAdInfo atAdInfo) {

    }

    @Override
    public void onReward(ATAdInfo atAdInfo) {
        Log.i(TopAdConfig.TAG,"onReward : "+atAdInfo.toString());
        if(completeListener != null){
            completeListener.reward(true);
        }
    }


}
