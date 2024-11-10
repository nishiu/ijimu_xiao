package com.ijimu.android.ad;

import static com.anythink.network.chartboost.ChartboostATConst.DEBUGGER_CONFIG.Chartboost_NETWORK;
import static com.anythink.network.inmobi.InmobiATConst.DEBUGGER_CONFIG.Inmobi_NETWORK;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.webkit.WebView;

import com.anythink.core.api.ATAdConst;
import com.anythink.core.api.ATDebuggerConfig;
import com.anythink.core.api.ATDeviceUtils;
import com.anythink.core.api.ATInitConfig;
import com.anythink.core.api.ATNetworkConfig;
import com.anythink.core.api.ATSDK;

import java.util.ArrayList;
import java.util.List;

public class TopAdConfig {

    public static String TAG = "ijimu_topon";

    public static final String BANNER = "n670cfbda6c587";
    public static final String REWARD_LOCAL_TAG = "ijimu_reward";
    public static final String REWARD = "n670cfbdb697ab";
    public static final String INSERT_LOCAL_TAG = "ijimu_insert";
    public static final String INSERT = "n670cfbdae53e7";

    public static void init(Application app,String appId,String appKey){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            String processName = getProcessName(app);
            if (!app.getPackageName().equals(processName)) {
                WebView.setDataDirectorySuffix(processName);
            }
        }

        if (!isMainProcess(app)) {
            return;
        }
        BaseContext.setContext(app);
//        ATSDK.deniedUploadDeviceInfo(
//                DeviceDataInfo.DEVICE_SCREEN_SIZE
//                , DeviceDataInfo.ANDROID_ID
//                , DeviceDataInfo.APP_PACKAGE_NAME
//                , DeviceDataInfo.APP_VERSION_CODE
//                , DeviceDataInfo.APP_VERSION_NAME
//                , DeviceDataInfo.BRAND
//                , DeviceDataInfo.GAID
//                , DeviceDataInfo.LANGUAGE
//                , DeviceDataInfo.MCC
//                , DeviceDataInfo.MNC
//                , DeviceDataInfo.MODEL
//                , DeviceDataInfo.ORIENTATION
//                , DeviceDataInfo.OS_VERSION_CODE
//                , DeviceDataInfo.OS_VERSION_NAME
//                , DeviceDataInfo.TIMEZONE
//                , DeviceDataInfo.USER_AGENT
//                , DeviceDataInfo.NETWORK_TYPE
//                , ChinaDeviceDataInfo.IMEI
//                , ChinaDeviceDataInfo.MAC
//                , ChinaDeviceDataInfo.OAID
//                , DeviceDataInfo.INSTALLER
//
//        );


        ATSDK.setPersonalizedAdStatus(ATAdConst.PRIVACY.PERSIONALIZED_ALLOW_STATUS);
        ATNetworkConfig atNetworkConfig = getAtNetworkConfig();
        ATSDK.init(app, appId, appKey, atNetworkConfig);
        if(VersionUtil.isDebug()){
            ATSDK.setNetworkLogDebug(true);
            ATSDK.integrationChecking(app);
            ATSDK.setDebuggerConfig(app,ATDeviceUtils.getGaid(),new  ATDebuggerConfig.Builder(Chartboost_NETWORK).build());
//            ATSDK.setDebuggerConfig(app, ATDeviceUtils.getGaid(), new ATDebuggerConfig.Builder(Inmobi_NETWORK).build());
        }

    }


    private static ATNetworkConfig getAtNetworkConfig() {
        List<ATInitConfig> atInitConfigs = new ArrayList<>();

//        ATInitConfig pangleATInitConfig = new PangleATInitConfig("8025677");
//        ATInitConfig mintegralATInitConfig = new MintegralATInitConfig("100947", "ef13ef712aeb0f6eb3d698c4c08add96");
//        ATInitConfig facebookATInitConfig = new FacebookATInitConfig();
//        ATInitConfig vungleAtInitConfig = new VungleATInitConfig("5ad59a853d927044ac75263a");
//        ATInitConfig adColonyATInitConfig = new AdColonyATInitConfig("app251236acbb494d48a8", "vz6ddfc996216e4c2b99", null);
//        ATInitConfig myTargetATInitConfig = new MyTargetATInitConfig();
//
//        atInitConfigs.add(pangleATInitConfig);
//        atInitConfigs.add(mintegralATInitConfig);
//        atInitConfigs.add(facebookATInitConfig);
//        atInitConfigs.add(vungleAtInitConfig);
//        atInitConfigs.add(adColonyATInitConfig);
//        atInitConfigs.add(myTargetATInitConfig);

        ATNetworkConfig.Builder builder = new ATNetworkConfig.Builder();
        builder.withInitConfigList(atInitConfigs);
        return builder.build();
    }

    public static boolean isMainProcess(Context context) {
        try {
            if (null != context) {
                return context.getPackageName().equals(getProcessName(context));
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public static String getProcessName(Context cxt) {
        int pid = android.os.Process.myPid();
        ActivityManager am = (ActivityManager) cxt.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningAppProcessInfo> runningApps = am.getRunningAppProcesses();
        if (runningApps == null) {
            return null;
        }
        for (ActivityManager.RunningAppProcessInfo procInfo : runningApps) {
            if (procInfo.pid == pid) {
                return procInfo.processName;
            }
        }
        return null;
    }
}
