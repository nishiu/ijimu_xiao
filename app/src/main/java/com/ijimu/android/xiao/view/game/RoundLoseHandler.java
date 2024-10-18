package com.ijimu.android.xiao.view.game;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Gift;
import com.ijimu.android.xiao.view.gift.GiftListener;
import com.ijimu.android.xiao.view.gift.GiftShowAction;

public class RoundLoseHandler implements GameEventListener, GiftListener{
	
	private GameWorld gameWorld;
	
	public RoundLoseHandler() {
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, EventType.GAME_OVER);
	}

	@Override
	public void onGameEvent(GameEvent event) {		
		new GiftShowAction(Gift.TYPE_REBORN, 0, this);
	}

	@Override
	public void onGiftBuySuccess(Gift gift) {
		gameWorld.startRound(0);
	}
	
	@Override
	public void onGiftBuyCancel(Gift gift) {	
		gameWorld.setScene(GameWorld.SCENE_PORTAL);
	}
	
	@Override
	public void onGiftBuyError(Gift gift, Throwable e) {
		gameWorld.setScene(GameWorld.SCENE_PORTAL);
	}
}
