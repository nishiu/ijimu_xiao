package com.ijimu.android.xiao.view.game;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.DeamonThread;
import com.ijimu.android.game.res.ApkResources;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.logic.GameRecorder;
import com.ijimu.android.xiao.view.action.ActionSupport;

public class GameSaveAction extends ActionSupport{
	
	public GameSaveAction(){
		this(false);
	}
	
	public GameSaveAction(boolean showProgress){
		doSave(showProgress);
	}
	
	private void doSave(boolean showProgress){
		if(showProgress) showProgress(ApkResources.getString(R.string.progress_tips_save));//"保存游戏进度...");
		DeamonThread.post(new Runnable() {
			@Override
			public void run() {
				try {
					GameRecorder recorder = BeanFactory.getBean(GameRecorder.class);
					recorder.save();
				}finally{
					dismissProgress();
				}				
			}
		});
	}
}
