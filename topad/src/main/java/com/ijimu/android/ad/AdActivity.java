package com.ijimu.android.ad;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.anythink.banner.api.ATBannerView;
import com.anythink.core.api.ATAdConst;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATNativeAdCustomRender;
import com.anythink.core.api.ATNativeAdInfo;
import com.anythink.core.api.AdError;
import com.anythink.interstitial.api.ATInterstitial;
import com.anythink.interstitial.api.ATInterstitialAutoAd;
import com.anythink.interstitial.api.ATInterstitialAutoLoadListener;

import java.util.HashMap;
import java.util.Map;

public abstract class AdActivity extends AppCompatActivity {

    private ATBannerView atBannerView;
    private ATInterstitialAutoAd atInterstitialAutoAd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void initBanner(ViewGroup container){
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

    protected void showInsert(){
        ATInterstitialAutoAd.init(this, new String[]{TopAdConfig.INSERT}, new ATInterstitialAutoLoadListener() {
            @Override
            public void onInterstitialAutoLoaded(String s) {
                ATInterstitialAutoAd.entryAdScenario(TopAdConfig.INSERT, "ijimu_insert");
                if (ATInterstitialAutoAd.isAdReady(TopAdConfig.INSERT)) {

                }
            }

            @Override
            public void onInterstitialAutoLoadFail(String s, AdError adError) {

            }
        });
    }

    protected void showReward(){

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
    }

    public int dip2px(int dipValue) {
        float scale = getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
