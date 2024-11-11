package com.ijimu.android.xiao.plugin;

import static com.ijimu.android.xiao.Constants.PRIVACY_AGREE;

import com.ijimu.android.ad.TopAdConfig;
import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.ContextHolder;
import com.ijimu.android.game.UIThread;
import com.ijimu.android.game.dialog.SimpleNotice;
import com.ijimu.android.game.util.LocalStorage;

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
                SimpleNotice.show("sooner show");
            }
        });
    }
}
