package com.ijimu.android.xiao.view.game;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Gift;
import com.ijimu.android.xiao.view.gift.GiftShowAction;

public class RoundStartHandler implements GameEventListener{
	
	private GameWorld gameWorld;
	
	public RoundStartHandler() {
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, EventType.ROUND_START);
	}

	@Override
	public void onGameEvent(GameEvent event) {		
		if(gameWorld.getLevel() != 1) return;
		if(!ClientConfig.START_GIFT_ENABLED) return;
		new GiftShowAction(Gift.TYPE_PROPS_5, 1000L, null);
	}
}
