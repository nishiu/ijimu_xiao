package com.ijimu.android.xiao.plugin;

import static com.ijimu.android.xiao.Constants.PRIVACY_AGREE;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.ijimu.android.ad.TopAdConfig;
import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.ContextHolder;
import com.ijimu.android.game.UIThread;
import com.ijimu.android.game.dialog.SimpleNotice;
import com.ijimu.android.game.util.LocalStorage;
import com.ijimu.android.xiao.MainActivity;

public class PrivacyPolicyPlugin {

    private LocalStorage localStorage;

    public PrivacyPolicyPlugin() {
        localStorage = BeanFactory.getBean(LocalStorage.class);
    }

    public boolean isPrivacyAgree(){
        return localStorage.get(PRIVACY_AGREE,true);
    }

    public void updatePrivacyEnabled(boolean enabled){
        localStorage.put(PRIVACY_AGREE,enabled);
        TopAdConfig.setPrivacyConfig(ContextHolder.get(),enabled);
    }

    public void showPrivacy(){
        UIThread.post(new Runnable() {
            @Override
            public void run() {
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri content_url = Uri.parse("https://sites.google.com/view/pop-pig-privacy-policy");
                intent.setData(content_url);
                MainActivity.getInstance().startActivity(intent);
            }
        });
    }
}
