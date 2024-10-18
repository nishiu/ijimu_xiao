package com.ijimu.android.xiao.view.game;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.DeamonThread;
import com.ijimu.android.game.res.ApkResources;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.logic.GameRecorder;
import com.ijimu.android.xiao.view.action.ActionSupport;

public class GameRestoreAction extends ActionSupport{

	private GameRecorder gameRecorder;
	
	public GameRestoreAction(){
		gameRecorder = BeanFactory.getBean(GameRecorder.class);		
		load();
	}

	private void load() {
		showProgress(ApkResources.getString(R.string.progress_tips_load));
//		showProgress("加载游戏记录...");
		DeamonThread.post(new Runnable() {			
			@Override
			public void run() {
				try {
					boolean result = gameRecorder.restore();				
					if(!result)	showNotice(ApkResources.getString(R.string.notice_restore_not));//"没有游戏存档");
				}finally {
					dismissProgress();
				}
			}
		});
	}
}
