package com.ijimu.android.ad.impl;


import static com.ijimu.android.ad.TopAdConfig.TAG;

import android.content.Context;
import android.util.Log;

import com.anythink.banner.api.ATBannerExListener;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATNetworkConfirmInfo;
import com.anythink.core.api.AdError;
import com.ijimu.android.ad.VersionUtil;

public class BannerListenerImpl implements ATBannerExListener {

    @Override
    public void onDeeplinkCallback(boolean isRefresh, ATAdInfo adInfo, boolean isSuccess) {
        logInfo( "onDeeplinkCallback:" + adInfo.toString() + "--status:" + isSuccess);
    }

    @Override
    public void onDownloadConfirm(Context context, ATAdInfo adInfo, ATNetworkConfirmInfo networkConfirmInfo) {
        logInfo( "onDownloadConfirm:" + adInfo.toString() + " networkConfirmInfo:" + networkConfirmInfo);
    }

    @Override
    public void onBannerLoaded() {
        logInfo( "onBannerLoaded");
    }

    @Override
    public void onBannerFailed(AdError adError) {
        logInfo( "onBannerFailed: " + adError.getFullErrorInfo());
    }

    @Override
    public void onBannerClicked(ATAdInfo entity) {
        logInfo( "onBannerClicked:" + entity.toString());
    }

    @Override
    public void onBannerShow(ATAdInfo entity) {
        logInfo( "onBannerShow:" + entity.toString());
    }

    @Override
    public void onBannerClose(ATAdInfo entity) {
        logInfo( "onBannerClose:" + entity.toString());
    }

    @Override
    public void onBannerAutoRefreshed(ATAdInfo entity) {
        logInfo( "onBannerAutoRefreshed:" + entity.toString());
    }

    @Override
    public void onBannerAutoRefreshFail(AdError adError) {
        logInfo("onBannerAutoRefreshFail: " + adError.getFullErrorInfo());
    }

    private void logInfo(String info){
        if(VersionUtil.isDebug()){
            Log.i(TAG,info);
        }
    }
}
