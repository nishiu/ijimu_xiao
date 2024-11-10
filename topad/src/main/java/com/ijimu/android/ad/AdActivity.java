package com.ijimu.android.ad;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anythink.banner.api.ATBannerView;
import com.anythink.core.api.ATAdConst;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATNativeAdCustomRender;
import com.anythink.core.api.ATNativeAdInfo;
import com.anythink.core.api.ATShowConfig;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitialAutoAd;
import com.anythink.interstitial.api.ATInterstitialAutoLoadListener;
import com.anythink.rewardvideo.api.ATRewardVideoAutoAd;
import com.anythink.rewardvideo.api.ATRewardVideoAutoLoadListener;
import com.ijimu.android.ad.impl.BannerListenerImpl;
import com.ijimu.android.ad.view.MediationNativeAdUtil;

import java.util.HashMap;
import java.util.Map;

public abstract class AdActivity extends AppCompatActivity {

    private ATBannerView atBannerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void showBanner(ViewGroup container){
        if(container == null)return;
        container.removeAllViews();
        atBannerView = new ATBannerView(this);
        atBannerView.setPlacementId(TopAdConfig.BANNER);
        int padding = dip2px(12);
        Map<String, Object> localMap = new HashMap<>();
        localMap.put(ATAdConst.KEY.AD_WIDTH, getResources().getDisplayMetrics().widthPixels - 2 * padding);
        localMap.put(ATAdConst.KEY.AD_HEIGHT, dip2px(60));
        atBannerView.setLocalExtra(localMap);
        atBannerView.setBannerAdListener(new BannerListenerImpl());
        atBannerView.setNativeAdCustomRender(new NativeAdCustomRender(this));
        container.addView(atBannerView);
        atBannerView.loadAd();
    }

    protected void initInsert(){
        ATInterstitialAutoAd.init(this, new String[]{TopAdConfig.INSERT}, new ATInterstitialAutoLoadListener() {
            @Override
            public void onInterstitialAutoLoaded(String s) {

            }

            @Override
            public void onInterstitialAutoLoadFail(String s, AdError adError) {
                logInfo("onInterstitialAutoLoadFail : "+adError.getFullErrorInfo());
            }
        });
    }

    protected void initReward(){
        ATRewardVideoAutoAd.init(this, new String[]{TopAdConfig.REWARD}, new ATRewardVideoAutoLoadListener() {
            @Override
            public void onRewardVideoAutoLoaded(String s) {
            }

            @Override
            public void onRewardVideoAutoLoadFail(String s, AdError adError) {
                logInfo("onRewardVideoAutoLoadFail : "+adError.getFullErrorInfo());
            }
        });
    }

    public static class NativeAdCustomRender implements ATNativeAdCustomRender {
        private Context context;
        public NativeAdCustomRender(Context context) {
            this.context = context.getApplicationContext();
        }

        @Override
        public View getMediationViewFromNativeAd(ATNativeAdInfo mixNativeAd, ATAdInfo atAdInfo) {
            return MediationNativeAdUtil.getViewFromNativeAd(context, mixNativeAd, atAdInfo, false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(atBannerView != null){
            atBannerView.destroy();
            atBannerView = null;
        }
        ATRewardVideoAutoAd.removePlacementId(TopAdConfig.REWARD);
        ATInterstitialAutoAd.removePlacementId(TopAdConfig.INSERT);
    }

    public int dip2px(int dipValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    public void logInfo(String msg){
        if(VersionUtil.isDebug()){
            Log.i(TopAdConfig.TAG,msg);
        }
    }
}
