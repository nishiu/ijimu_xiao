package com.ijimu.android.xiao.view.portal;

import com.ijimu.android.game.DeamonThread;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.R;

public class SplashScene extends DisplayContainer implements Runnable{
		
	public SplashScene() {		
		setX(0);
		setY(0);
		setWidth(Constants.SCREEN_WIDTH);
		setHeight(Constants.SCREEN_HEIGHT);
		setBackgroundImage(R.drawable.splash);
		setClickable(true);
		if(ClientConfig.SPLASH_TIME>0){
			DeamonThread.post(this, ClientConfig.SPLASH_TIME+1500);
		}else{
			setVisible(false);
		}
	}
	
	@Override
	public void run() {
		setVisible(false);
	}
}
