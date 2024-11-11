package com.ijimu.android.xiao.view.game;

import com.ijimu.android.ad.VersionUtil;
import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.UIThread;
import com.ijimu.android.game.dialog.SimpleNotice;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.plugin.AppraisePlugin;
import com.ijimu.android.xiao.plugin.InsertAction;

public class RoundStartHandler implements GameEventListener{
	
	private GameWorld gameWorld;
	private AppraisePlugin appraisePlugin;
	
	public RoundStartHandler() {
		gameWorld = BeanFactory.getBean(GameWorld.class);
		appraisePlugin = BeanFactory.getBean(AppraisePlugin.class);
		gameWorld.addEventListener(this, EventType.ROUND_START);
	}

	@Override
	public void onGameEvent(GameEvent event) {		
		if(gameWorld.getLevel() == 1) return;
		if(gameWorld.getLevel() == ClientConfig.APPRAISE_LEVEL){
			UIThread.post(() -> {
				if(VersionUtil.isDebug()){
					SimpleNotice.show("评价系统展示");
				}
				appraisePlugin.showGPAppraise(null);
			});
		}
		if(gameWorld.getLevel() % ClientConfig.INSERT_AD_LEVEL == 0){
			UIThread.post(() -> {
				if(VersionUtil.isDebug()){
					SimpleNotice.show("插屏广告展示");
				}
				new InsertAction().showInsert();
			});
		}
	}
}
