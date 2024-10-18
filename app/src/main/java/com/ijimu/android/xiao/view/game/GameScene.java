package com.ijimu.android.xiao.view.game;

import android.graphics.Color;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.display.DisplayPort;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.view.prop.PropBar;
import com.ijimu.android.xiao.view.prop.PropFlyWall;
import com.ijimu.android.xiao.view.prop.PropPaintDialog;

public class GameScene extends DisplayContainer {
	
	private GameWorld gameWorld;
	
	public GameScene() {
		setX(0);
		setY(0);
		setWidth(Constants.SCREEN_WIDTH);
		setHeight(Constants.SCREEN_HEIGHT);		
		setBackgroundImage(R.drawable.bg_scene);
		initMask();
		addChild(new BlockWall());
		addChild(new PropBar());
		addChild(new GameStatePane());
		addChild(new PropPaintDialog());
		addChild(new NoticeWall());
		addChild(new ScoreFlyWall());
		
		new RoundStartHandler();
		new RoundWinHandler();
		new RoundLoseHandler();
		gameWorld = BeanFactory.getBean(GameWorld.class);
	}

	private void initMask(){
		DisplayObject mask = new DisplayObject();
		mask.setWidth(Constants.SCREEN_WIDTH);
		mask.setHeight(Constants.SCREEN_HEIGHT);
		mask.setBackgroundColor(Color.parseColor("#88000000"));
		addChild(mask);
	}
	
	@Override
	public void setPort(DisplayPort port) {
		super.setPort(port);
		port.addDisplayObject(new PropFlyWall(), 2);
	}

	@Override
	public boolean isVisible() {
		return gameWorld.getScene()==GameWorld.SCENE_GAME;
	}
}
