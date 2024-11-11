package com.ijimu.android.xiao.plugin;

import com.ijimu.android.ad.handle.InsertHandler;
import com.ijimu.android.xiao.MainActivity;

public class InsertAction {

    private InsertHandler insertHandler;

    public InsertAction() {
        insertHandler = new InsertHandler();
    }

    public void showInsert(){
        insertHandler.showAd(MainActivity.getInstance());
    }
}
