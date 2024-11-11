package com.ijimu.android.xiao.view.portal;

import android.graphics.Color;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.display.Label;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.plugin.AboutPlugin;
import com.ijimu.android.game.plugin.PluginManager;
import com.ijimu.android.game.touch.ClickListener;
import com.ijimu.android.game.touch.TouchEvent;
import com.ijimu.android.game.util.LocalStorage;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.MainActivity;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.view.BaseButton;
import com.ijimu.android.xiao.plugin.PrivacyPolicyPlugin;
import com.ijimu.android.xiao.view.game.GameRestoreAction;
import com.ijimu.android.xiao.view.game.GameStartAction;
import com.ijimu.android.xiao.view.gift.GiftOwedButton;

public class PortalScene extends DisplayContainer{
	
	private GameWorld gameWorld;
	private LocalStorage localStorage;
	private PrivacyPolicyPlugin privacyPolicyPlugin;
	
	public PortalScene() {
		gameWorld = BeanFactory.getBean(GameWorld.class);
		localStorage = BeanFactory.getBean(LocalStorage.class);
		privacyPolicyPlugin = new PrivacyPolicyPlugin();
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
		pane.setX((Constants.SCREEN_WIDTH-pane.getWidth())/2+20);
		pane.setY(95);
//		createPrivacyPolicyLabel(pane);
		createCheckButton(pane);
		createMarketButton(pane);
		createContinueButton(pane);
		createNewGameButton(pane);
		addChild(pane);
	}

	private void createCheckButton(DisplayContainer pane){
		DisplayContainer subContainer = new DisplayContainer();
		subContainer.setWidth(200);
		subContainer.setHeight(65);
		subContainer.setX(0);
		subContainer.setY(0);

		DisplayObject checkBox = new BaseButton();
		checkBox.setWidth(64);//224
		checkBox.setHeight(64);
		checkBox.setX(32);
		checkBox.setScaleX(0.3f);
		checkBox.setScaleY(0.3f);
		checkBox.setY(3);
		checkBox.setBackgroundImage(privacyPolicyPlugin.isPrivacyAgree() ? R.drawable.ic_checked : R.drawable.ic_uncheck);
		checkBox.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				boolean check = privacyPolicyPlugin.isPrivacyAgree();
				check = !check;
				privacyPolicyPlugin.updatePrivacyEnabled(check);
				checkBox.setBackgroundImage(check ? R.drawable.ic_checked : R.drawable.ic_uncheck);
			}
		});

		Label label = new Label();
		label.setWidth(100);//224
		label.setHeight(65);
		label.setX(60);
		label.setY(0);
		label.setAlign(Label.ALIGN_LEFT);
		label.setText( MainActivity.getInstance().getString(R.string.privacy_policy_label));
		label.setTextColor(Color.parseColor("#ffffff"));
		label.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				privacyPolicyPlugin.showPrivacy();
			}
		});

		subContainer.addChild(checkBox);
		subContainer.addChild(label);
		pane.addChild(subContainer);
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
		menu.setY((pane.getChildCount()-1)*85 + 40);
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
