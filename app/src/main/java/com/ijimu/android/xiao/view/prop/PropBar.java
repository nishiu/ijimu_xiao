package com.ijimu.android.xiao.view.prop;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.Button;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.touch.ClickListener;
import com.ijimu.android.game.touch.TouchEvent;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.domain.Prop;

public class PropBar extends DisplayContainer {
	
	public PropBar(){
		setWidth(Constants.SCREEN_WIDTH);
		setHeight(112);
		setX(0);
		setY(10);
		initShopButton();
		addChild(createButton(184, R.drawable.prop_bomb, Prop.TYPE_BOMB));
		addChild(createButton(287, R.drawable.prop_paint, Prop.TYPE_PAINT));
		addChild(createButton(384, R.drawable.prop_refresh, Prop.TYPE_REFRESH));
	}
	
	private DisplayObject createButton(int x, int bg, final int propType) {
		PropButton button = new PropButton(propType);
		button.setX(x);
		button.setY(0);
		button.setPropImage(bg);
		return button;
	}
	
	private void initShopButton(){
		if(!ClientConfig.MARKET_ENABLED) return;
		final GameWorld gameWorld = BeanFactory.getBean(GameWorld.class);
		DisplayObject button = new Button();
		button.setX(10);
		button.setY(0);
		button.setWidth(153);
		button.setHeight(83);
		button.setBackgroundImage(R.drawable.button_market);
		button.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				gameWorld.fireEvent(new GameEvent(EventType.DIALOG_MARKET));
			}
		});
		addChild(button);
	}
}
