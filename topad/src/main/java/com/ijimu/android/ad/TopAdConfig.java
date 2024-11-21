package com.ijimu.android.ad;

import static com.anythink.network.bigo.BigoATConst.DEBUGGER_CONFIG.BigoAds_NETWORK;
import static com.anythink.network.chartboost.ChartboostATConst.DEBUGGER_CONFIG.Chartboost_NETWORK;
import static com.anythink.network.fyber.FyberATConst.DEBUGGER_CONFIG.Fyber_NETWORK;
import static com.anythink.network.inmobi.InmobiATConst.DEBUGGER_CONFIG.Inmobi_NETWORK;
import static com.anythink.network.ironsource.IronsourceATConst.DEBUGGER_CONFIG.Ironsource_NETWORK;
import static com.anythink.network.mintegral.MintegralATConst.DEBUGGER_CONFIG.Mintegral_NETWORK;
import static com.anythink.network.pangle.PangleATConst.DEBUGGER_CONFIG.Pangle_NETWORK;
import static com.anythink.network.startapp.StartAppATConst.DEBUGGER_CONFIG.StartApp_NETWORK;
import static com.anythink.network.unityads.UnityAdsATConst.DEBUGGER_CONFIG.UnityAds_NETWORK;
import static com.anythink.network.vungle.VungleATConst.DEBUGGER_CONFIG.Vungle_NETWORK;

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
import com.anythink.core.api.ATPrivacyConfig;
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
//        if(VersionUtil.isDebug()){
            ATSDK.setNetworkLogDebug(true);
            ATSDK.integrationChecking(app);
            Context context = app;
            String GAID = ATDeviceUtils.getGaid();
            ATSDK.setDebuggerConfig(context,GAID,new  ATDebuggerConfig.Builder(Chartboost_NETWORK).build());
            ATSDK.setDebuggerConfig(context, GAID, new ATDebuggerConfig.Builder(Mintegral_NETWORK).build());
            ATSDK.setDebuggerConfig(context, GAID, new ATDebuggerConfig.Builder(UnityAds_NETWORK).build());
            ATSDK.setDebuggerConfig(context, GAID, new ATDebuggerConfig.Builder(StartApp_NETWORK).build());
            ATSDK.setDebuggerConfig(context, GAID, new ATDebuggerConfig.Builder(Fyber_NETWORK).build());
            ATSDK.setDebuggerConfig(context, GAID, new ATDebuggerConfig.Builder(Pangle_NETWORK).build());
            ATSDK.setDebuggerConfig(context, GAID, new ATDebuggerConfig.Builder(Vungle_NETWORK).build());
            ATSDK.setDebuggerConfig(context, GAID, new ATDebuggerConfig.Builder(Ironsource_NETWORK).build());
            ATSDK.setDebuggerConfig(context, GAID, new ATDebuggerConfig.Builder(BigoAds_NETWORK).build());
            ATSDK.setDebuggerConfig(context, GAID, new ATDebuggerConfig.Builder(Inmobi_NETWORK).build());
//        }
    }

    public static void setPrivacyConfig(Context context,boolean enabled){
        ATSDK.setGDPRUploadDataLevel(context,enabled ? ATSDK.PERSONALIZED : ATSDK.NONPERSONALIZED);
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
