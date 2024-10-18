package com.ijimu.android.xiao.view.dialog;

import android.graphics.Color;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.Button;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.game.display.Label;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.game.touch.ClickListener;
import com.ijimu.android.game.touch.TouchEvent;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.effect.DialogEffects;
import com.ijimu.android.xiao.view.BaseButton;

public class HelpDialog extends DisplayContainer implements GameEventListener{
	
	private GameWorld gameWorld;
	
	public HelpDialog(){
		setWidth(Constants.SCREEN_WIDTH);
		setHeight(Constants.SCREEN_HEIGHT);
		setBackgroundColor(Color.parseColor("#88000000"));
		initContent();
		setVisible(false);
		setClickable(true);		
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, EventType.DIALOG_HELP);
	}
	
	private void initContent(){
		DisplayContainer contentPane = new DisplayContainer();
		contentPane.setWidth(420);
		contentPane.setHeight(720);
		contentPane.setX((Constants.SCREEN_WIDTH-contentPane.getWidth())/2);
		contentPane.setY((Constants.SCREEN_HEIGHT-contentPane.getHeight())/2);
		contentPane.setBackgroundImage(R.drawable.bg_help);
		addChild(contentPane);
		initVersion(contentPane);
		initCloseButton(contentPane);
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType()==EventType.DIALOG_HELP){
			DialogEffects.show(this);
		}
	}

	@Override
	protected void onClick(TouchEvent event) {
		super.onClick(event);
		DialogEffects.dismiss(this);
	}
	
	private void initVersion(DisplayContainer contentPane){
		Label label = new Label();
		label.setX(10);
		label.setY(-10);
		label.setWidth(50);
		label.setHeight(30);
		StringBuffer buffer = new StringBuffer();
		buffer.append("v").append(ClientConfig.VERSION);
		buffer.append("_").append(ClientConfig.getFullChannel());
		label.setText(buffer);
		contentPane.addChild(label);
	}
	
	private void initCloseButton(DisplayContainer contentPane){
		Button button = new BaseButton();
		button.setWidth(50);
		button.setHeight(50);
		button.setX(contentPane.getWidth()-button.getWidth()-10);
		button.setY(contentPane.getHeight()-button.getHeight()-20);
		button.setBackgroundImage(R.drawable.button_close);
		button.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				DialogEffects.dismiss(HelpDialog.this);
			}
		});
		contentPane.addChild(button);
	}
}
