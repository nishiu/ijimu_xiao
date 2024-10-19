package com.ijimu.android.xiao.view.portal;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.plugin.AboutPlugin;
import com.ijimu.android.game.plugin.MoreGamePlugin;
import com.ijimu.android.game.plugin.PluginManager;
import com.ijimu.android.game.touch.ClickListener;
import com.ijimu.android.game.touch.TouchEvent;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.view.BaseButton;
import com.ijimu.android.xiao.view.game.GameRestoreAction;
import com.ijimu.android.xiao.view.game.GameStartAction;
import com.ijimu.android.xiao.view.gift.GiftOwedButton;

public class PortalScene extends DisplayContainer{
	
	private GameWorld gameWorld;
	private MoreGamePlugin moreGamePlugin;
	
	public PortalScene() {
		gameWorld = BeanFactory.getBean(GameWorld.class);
		moreGamePlugin = PluginManager.getPlugin(MoreGamePlugin.class);

		setX(0);
		setY(0);
		setWidth(Constants.SCREEN_WIDTH);
		setHeight(Constants.SCREEN_HEIGHT);
		setBackgroundImage(R.drawable.bg_scene);

		addChild(new FireworksWall());
		addChild(new SoundButton());
		addChild(new GiftOwedButton());
		initLogo();
		initHelpButton();
		initAboutButton();
		initMainMenu();		
	}
	
	@Override
	public boolean isVisible() {
		return gameWorld.getScene()==GameWorld.SCENE_PORTAL;
	}
	
	private void initLogo(){
		DisplayObject logo = new DisplayObject();
		logo.setWidth(ClientConfig.LOGO_WIDTH);
		logo.setHeight(ClientConfig.LOGO_HEIGHT);
		logo.setX((Constants.SCREEN_WIDTH-logo.getWidth())/2);
		logo.setY(Constants.SCREEN_HEIGHT-logo.getHeight()-24);
		logo.setBackgroundImage(R.drawable.logo);
		addChild(logo);
	}
	
	private void initMainMenu() {
		DisplayContainer pane = new DisplayContainer();
		pane.setWidth(225);
		pane.setHeight(400);
		pane.setX((Constants.SCREEN_WIDTH-pane.getWidth())/2);
		pane.setY(95);
		createMoreGameButton(pane);
		createMarketButton(pane);
		createContinueButton(pane);
		createNewGameButton(pane);
		addChild(pane);
	}

	private void createMoreGameButton(DisplayContainer pane){
		if(moreGamePlugin==null) return;
		createMenu(pane, R.drawable.button_more_game, new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				moreGamePlugin.onMoreGameAction();
			}
		});
	}
	
	private void createNewGameButton(DisplayContainer pane) {
		createMenu(pane, R.drawable.button_start, new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				new GameStartAction();
			}
		});
	}

	private void createContinueButton(DisplayContainer pane) {
		createMenu(pane, R.drawable.button_continue, new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				new GameRestoreAction();
			}
		});
	}

	private void createMarketButton(DisplayContainer pane) {
		if(!ClientConfig.MARKET_ENABLED) return;
		createMenu(pane, R.drawable.button_shop, new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				gameWorld.fireEvent(new GameEvent(EventType.DIALOG_MARKET));
			}
		});
	}

	private void createMenu(DisplayContainer pane, int bg, ClickListener clickListener) {
		DisplayObject menu = new BaseButton();
		menu.setWidth(193);//224
		menu.setHeight(71);
		menu.setX(0);
		menu.setY(pane.getChildCount()*(85));
		menu.setBackgroundImage(bg);
		if(clickListener!=null) menu.addClickListener(clickListener);
		pane.addChild(menu);
	}
	
	private void initHelpButton(){
		DisplayObject button = new BaseButton();
		button.setWidth(60);
		button.setHeight(60);
		button.setX(19);
		button.setY(800-777);
		button.setBackgroundImage(R.drawable.button_help);
		button.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				gameWorld.fireEvent(new GameEvent(EventType.DIALOG_HELP));
			}
		});
		addChild(button);
	}
	
	private void initAboutButton(){
		final AboutPlugin aboutPlugin = PluginManager.getPlugin(AboutPlugin.class);
		if(aboutPlugin==null) return;
		DisplayObject button = new BaseButton();
		button.setWidth(60);
		button.setHeight(60);
		button.setX(180);
		button.setY(800-777);
		button.setBackgroundImage(R.drawable.button_about);
		button.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				aboutPlugin.onAbountAction();
			}
		});
		addChild(button);
	}
}
