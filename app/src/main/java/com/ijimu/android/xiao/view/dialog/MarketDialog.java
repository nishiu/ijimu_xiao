package com.ijimu.android.xiao.view.dialog;

import java.util.Arrays;
import java.util.List;

import android.graphics.Color;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.Button;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.game.touch.ClickListener;
import com.ijimu.android.game.touch.TouchEvent;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.domain.Gift;
import com.ijimu.android.xiao.effect.DialogEffects;
import com.ijimu.android.xiao.logic.GiftManager;
import com.ijimu.android.xiao.view.BaseButton;
import com.ijimu.android.xiao.view.gift.GiftShowAction;

public class MarketDialog extends DisplayContainer implements GameEventListener{
	
	private static final List<Integer> MARKET_ITEMS = Arrays.asList(
			Gift.TYPE_REFRESH_5,Gift.TYPE_PAINT_5,Gift.TYPE_BOMB_5,Gift.TYPE_PROPS_2,Gift.TYPE_PROPS_5);

	private GiftManager giftManager;
	private GameWorld gameWorld;
	
	public MarketDialog(){
		setWidth(Constants.SCREEN_WIDTH);
		setHeight(Constants.SCREEN_HEIGHT);
		setClickable(true);
		setVisible(false);
		setBackgroundColor(Color.parseColor("#88000000"));
		giftManager = BeanFactory.getBean(GiftManager.class);
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, EventType.DIALOG_MARKET);
		initContent();
	}

	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType()==EventType.DIALOG_MARKET){
			DialogEffects.show(this);
		}
	}
	
	private void initContent() {
		initBg();
		initMarketItems();
		initCloseButton();
	}

	private void initBg(){
		DisplayContainer bg = new DisplayContainer();
		bg.setWidth(458);
		bg.setHeight(718);
		bg.setX((Constants.SCREEN_WIDTH-bg.getWidth())/2);
		bg.setY((Constants.SCREEN_HEIGHT-bg.getHeight())/2);
		bg.setBackgroundImage(R.drawable.bg_market);
		addChild(bg);
	}
	
	private void initMarketItems() {
		for(int i = 0; i < MARKET_ITEMS.size(); i++){
			Gift gift = giftManager.getByType(MARKET_ITEMS.get(i));
			DisplayObject item = createMarketItem(gift);
			item.setX(5);
			item.setY(80+i*110);
			addChild(item);
		}
	}
	
	private DisplayObject createMarketItem(final Gift gift){
		DisplayContainer container = new DisplayContainer();
		container.setWidth(463);
		container.setHeight(106);
		initBuyButton(gift, container);
		return container;
	}

	private void initBuyButton(final Gift gift, DisplayContainer container) {
		DisplayObject button = new Button();
		button.setWidth(146);
		button.setHeight(55);
		button.setX(container.getWidth()-button.getWidth()-15);
		button.setY(50);
		button.setBackgroundImage(R.drawable.button_buy);
		button.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				doBuy(gift);
			}
		});
		container.addChild(button);
	}
	
	private void doBuy(Gift gift){
		new GiftShowAction(gift.getType(), 0, null);
	}
	
	private void initCloseButton(){
		Button button = new BaseButton();
		button.setWidth(50);
		button.setHeight(50);
		button.setX(getWidth()-button.getWidth()-10);
		button.setY(getHeight()-button.getHeight()-55);
		button.setBackgroundImage(R.drawable.button_close);
		button.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				DialogEffects.dismiss(MarketDialog.this);
			}
		});
		addChild(button);
	}
	
}
