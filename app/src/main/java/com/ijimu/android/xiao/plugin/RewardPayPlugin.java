package com.ijimu.android.xiao.plugin;

import android.app.Activity;
import android.widget.Toast;

import com.ijimu.android.ad.handle.RewardHandler;
import com.ijimu.android.game.ContextHolder;
import com.ijimu.android.game.domain.PayInfo;
import com.ijimu.android.game.plugin.PayCallback;
import com.ijimu.android.game.plugin.PayPlugin;
import com.ijimu.android.xiao.MainActivity;
import com.ijimu.android.xiao.R;

public class RewardPayPlugin implements PayPlugin {

    private Activity activity;
    private RewardHandler rewardHandler;
    @Override
    public void init() {
        activity = MainActivity.getInstance();
        rewardHandler = new RewardHandler();
    }

    @Override
    public void pay(PayInfo payInfo, PayCallback callback) {
        if(rewardHandler.isAdReady()){
            rewardHandler.showAd(activity, success -> {
                if(success){
                    callback.onSuccess(payInfo);
                    Toast.makeText(ContextHolder.get(),activity.getString(R.string.notice_gift_success),Toast.LENGTH_LONG).show();
                }else{
                    callback.onCancel(payInfo);
                    Toast.makeText(ContextHolder.get(),activity.getString(R.string.notice_gift_cancel),Toast.LENGTH_LONG).show();
                }
            });
        }else{
            Toast.makeText(ContextHolder.get(),activity.getString(R.string.no_ads_alert),Toast.LENGTH_LONG).show();
            callback.onError(payInfo,null);
        }

//        SimpleConfirm.show("test", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                callback.onSuccess(payInfo);
//                Toast.makeText(ContextHolder.get(),"reward success",Toast.LENGTH_LONG).show();
//            }
//        }, new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialogInterface, int i) {
//                callback.onCancel(payInfo);
//                Toast.makeText(ContextHolder.get(),"cancel",Toast.LENGTH_LONG).show();
//            }
//        },"reward","not reward");
    }
}
