package com.ijimu.android.ad;

import android.app.Application;

public class BaseContext {

    public static Application context;

    public static Application getContext() {
        return context;
    }

    public static void setContext(Application context) {
        BaseContext.context = context;
    }
}
