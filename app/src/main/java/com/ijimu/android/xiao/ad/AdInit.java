package com.ijimu.android.xiao.ad;

import com.ijimu.android.game.plugin.InitPlugin;
import com.ijimu.android.game.plugin.PluginManager;

public class AdInit implements InitPlugin {
    @Override
    public void onInit() {
        RewardPay rewardPay = new RewardPay();
    }
}
