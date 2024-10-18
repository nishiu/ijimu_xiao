package com.ijimu.android.xiao.view.action;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.app.Dialog;

import com.ijimu.android.game.UIThread;
import com.ijimu.android.game.dialog.SimpleAlert;
import com.ijimu.android.game.dialog.SimpleNotice;
import com.ijimu.android.game.dialog.SimpleProgress;

public class ActionSupport {
	
	private Dialog progress;
	private boolean progressShowing;
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	protected void showProgress(final CharSequence msg){
		progressShowing = true;
		UIThread.post(new Runnable() {
			@Override
			public void run() {
				doShowProgress(msg);
			}
		});
	}
	
	private synchronized void doShowProgress(final CharSequence msg) {
		if(!progressShowing) return;
		progress = SimpleProgress.show(msg);
	}
	
	protected synchronized void dismissProgress(){
		progressShowing = false;
		if(progress==null) return;
		progress.dismiss();
		progress = null;
	}
	
	protected void showNotice(final CharSequence msg){
		UIThread.post(new Runnable() {
			
			@Override
			public void run() {
				SimpleNotice.show(msg);
			}
		});
	}
	
	protected void showAlert(final CharSequence msg){
		UIThread.post(new Runnable() {
			
			@Override
			public void run() {
				SimpleAlert.show(msg);
			}
		});
	}

}
