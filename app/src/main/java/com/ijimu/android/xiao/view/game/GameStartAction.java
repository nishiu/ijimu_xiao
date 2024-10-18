package com.ijimu.android.xiao.view.game;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.xiao.GameWorld;

public class GameStartAction {
	
	public GameStartAction(){
		GameWorld gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.startGame();
	}
}
