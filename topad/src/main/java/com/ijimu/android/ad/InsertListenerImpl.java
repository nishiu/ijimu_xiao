package com.ijimu.android.ad;

import static com.ijimu.android.ad.TopAdConfig.TAG;

import android.content.Context;
import android.util.Log;

import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATNetworkConfirmInfo;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitialExListener;

public class InsertListenerImpl implements ATInterstitialExListener {

    public interface LoadListener{
        void load(boolean success);
    }

    private LoadListener loadListener;

    public InsertListenerImpl(LoadListener loadListener){
        this.loadListener = loadListener;
    }

    @Override
    public void onDeeplinkCallback(ATAdInfo adInfo, boolean isSuccess) {
        logInfo( "onDeeplinkCallback:" + adInfo.toString() + "--status:" + isSuccess);
    }

    @Override
    public void onDownloadConfirm(Context context, ATAdInfo adInfo, ATNetworkConfirmInfo networkConfirmInfo) {
        logInfo( "onDownloadConfirm: adInfo=" + adInfo.toString());
    }

    @Override
    public void onInterstitialAdLoaded() {
        logInfo( "onInterstitialAdLoaded");
        if(loadListener != null)loadListener.load(true);
    }

    @Override
    public void onInterstitialAdLoadFail(AdError adError) {
        logInfo( "onInterstitialAdLoadFail:\n" + adError.getFullErrorInfo());
    }

    @Override
    public void onInterstitialAdClicked(ATAdInfo entity) {
        logInfo( "onInterstitialAdClicked:\n" + entity.toString());
    }

    @Override
    public void onInterstitialAdShow(ATAdInfo entity) {
        logInfo( "onInterstitialAdShow:\n" + entity.toString());
    }

    @Override
    public void onInterstitialAdClose(ATAdInfo entity) {
        logInfo("onInterstitialAdClose:\n" + entity.toString());
    }

    @Override
    public void onInterstitialAdVideoStart(ATAdInfo entity) {
        logInfo("onInterstitialAdVideoStart:\n" + entity.toString());
    }

    @Override
    public void onInterstitialAdVideoEnd(ATAdInfo entity) {
        logInfo( "onInterstitialAdVideoEnd:\n" + entity.toString());
    }

    @Override
    public void onInterstitialAdVideoError(AdError adError) {
        logInfo("onInterstitialAdVideoError:\n" + adError.getFullErrorInfo());
    }

    private void logInfo(String info){
        if(VersionUtil.isDebug()){
            Log.i(TAG,info);
        }
    }
}
