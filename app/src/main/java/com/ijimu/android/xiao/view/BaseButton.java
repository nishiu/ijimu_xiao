package com.ijimu.android.xiao.view;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.Button;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.touch.TouchEvent;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;

public class BaseButton extends Button {
	
	private GameWorld gameWorld;
	
	public BaseButton(){
		gameWorld = BeanFactory.getBean(GameWorld.class);		
	}

	@Override
	protected void onClick(TouchEvent event) {
		super.onClick(event);
		gameWorld.fireEvent(new GameEvent(EventType.BUTTON_CLICK));
	}
}
