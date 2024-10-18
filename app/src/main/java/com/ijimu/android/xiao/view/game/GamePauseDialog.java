package com.ijimu.android.xiao.view.game;

import android.graphics.Color;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.Button;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.game.touch.ClickListener;
import com.ijimu.android.game.touch.TouchEvent;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.effect.DialogEffects;
import com.ijimu.android.xiao.view.BaseButton;

public class GamePauseDialog extends DisplayContainer implements GameEventListener{
	
	private GameWorld gameWorld;
	
	public GamePauseDialog(){
		setWidth(Constants.SCREEN_WIDTH);
		setHeight(Constants.SCREEN_HEIGHT);
		setClickable(true);
		setVisible(false);
		setBackgroundColor(Color.parseColor("#88000000"));
		initContent();
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, EventType.DIALOG_GAME_PAUSE);
	}

	private void initContent() {
		DisplayContainer container = new DisplayContainer();
		container.setWidth(458);
		container.setHeight(644);
		container.setX((Constants.SCREEN_WIDTH-container.getWidth())/2);
		container.setY((Constants.SCREEN_HEIGHT-container.getHeight())/2);
		container.setBackgroundImage(R.drawable.bg_game_pause);
		initBackButton(container);
		initContinueButton(container);
		addChild(container);
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType()==EventType.DIALOG_GAME_PAUSE){
			DialogEffects.show(this);
		}
	}

	private void initBackButton(DisplayContainer parent){
		Button button = new BaseButton();
		button.setWidth(247);
		button.setHeight(91);
		button.setX(110);
		button.setY(136);
		button.setBackgroundImage(R.drawable.button_return);
		button.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				back();
			}
		});
		parent.addChild(button);
	}

	private void back() {
		new GameSaveAction();
		gameWorld.setScene(GameWorld.SCENE_PORTAL);
		DialogEffects.dismiss(GamePauseDialog.this);
	}
	
	private void initContinueButton(DisplayContainer parent){
		Button button = new BaseButton();
		button.setWidth(247);
		button.setHeight(91);
		button.setX(110);
		button.setY(31);
		button.setBackgroundImage(R.drawable.button_continue_2);
		button.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				DialogEffects.dismiss(GamePauseDialog.this);
			}
		});
		parent.addChild(button);
	}
}
