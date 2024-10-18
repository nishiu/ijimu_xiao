package com.ijimu.android.xiao.view.game;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;

public class GamePauseAction {
	
	public GamePauseAction() {
		GameWorld gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.fireEvent(new GameEvent(EventType.DIALOG_GAME_PAUSE));
	}

}
