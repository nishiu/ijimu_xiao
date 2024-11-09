package com.ijimu.android.ad;

import android.content.Context;
import android.content.pm.ApplicationInfo;

public class VersionUtil {

    public static boolean isDebug(Context context){
        boolean value = context.getApplicationInfo()!=null&&(
                context.getApplicationInfo().flags& ApplicationInfo.FLAG_DEBUGGABLE)!=0;
        return value;
    }

    public static boolean isDebug(){
        return isDebug(BaseContext.getContext());
    }
}
