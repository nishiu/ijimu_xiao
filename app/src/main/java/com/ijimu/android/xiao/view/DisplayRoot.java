package com.ijimu.android.xiao.view;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.UIThread;
import com.ijimu.android.game.display.DisplayPort;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.domain.Gift;
import com.ijimu.android.xiao.view.dialog.HelpDialog;
import com.ijimu.android.xiao.view.dialog.MarketDialog;
import com.ijimu.android.xiao.view.game.GamePauseDialog;
import com.ijimu.android.xiao.view.game.GameScene;
import com.ijimu.android.xiao.view.gift.GiftShowAction;
import com.ijimu.android.xiao.view.portal.PortalScene;
import com.ijimu.android.xiao.view.portal.SplashScene;

public class DisplayRoot extends DisplayPort implements GameEventListener{

	private static DisplayRoot instance;
	
	public static DisplayRoot getInstance(){
		if(instance==null) instance = new DisplayRoot();
		return instance;
	}
	
	public DisplayRoot() {
		setWidth(Constants.SCREEN_WIDTH);
		setHeight(Constants.SCREEN_HEIGHT);
		
		addDisplayObject(new PortalScene(), 0);
		addDisplayObject(new GameScene(), 0);
		addDisplayObject(new SplashScene(), 2);

		addDisplayObject(new DebugLabel(), 0);

		addDisplayObject(new HelpDialog(), 1);
		addDisplayObject(new GamePauseDialog(), 1);
		addDisplayObject(new MarketDialog(), 1);
		
		GameWorld gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, GameEvent.TICK);
		loadTextures();
		
		showInitGift();
	}

	private void showInitGift() {
		if(!ClientConfig.INIT_GIFT_ENABLED) return;
		UIThread.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				new GiftShowAction(Gift.TYPE_PROPS_5, 0, null);
			}
		}, 1000);
	}

	private void loadTextures(){
		post(new Runnable() {
			@Override
			public void run() {
				//getTextureFactory().get(R.drawable.bg_scene);
				//getTextureFactory().get(R.drawable.bg_help);
				
				getTextureFactory().get(R.drawable.block_00);
				getTextureFactory().get(R.drawable.block_10);
				getTextureFactory().get(R.drawable.block_20);
				getTextureFactory().get(R.drawable.block_30);
				getTextureFactory().get(R.drawable.block_40);
				
				getTextureFactory().get(R.drawable.star_0);
				getTextureFactory().get(R.drawable.star_0);
				getTextureFactory().get(R.drawable.star_0);
				getTextureFactory().get(R.drawable.star_0);
				getTextureFactory().get(R.drawable.star_0);
			}
		});
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		repaint();
	}

}
