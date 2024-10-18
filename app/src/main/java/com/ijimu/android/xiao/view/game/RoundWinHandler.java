package com.ijimu.android.xiao.view.game;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Gift;
import com.ijimu.android.xiao.view.card.CardDialog;
import com.ijimu.android.xiao.view.card.CardListener;
import com.ijimu.android.xiao.view.gift.GiftListener;
import com.ijimu.android.xiao.view.gift.GiftShowAction;

public class RoundWinHandler implements GameEventListener, GiftListener, CardListener{
	
	private GameWorld gameWorld;
	
	public RoundWinHandler() {
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, EventType.ROUND_WIN);
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		if(ClientConfig.CARD_DRAW_ENABLED){
			CardDialog.show(200, this);
		}
		else if(ClientConfig.ROUND_GIFT_ENABLED){
			new GiftShowAction(Gift.TYPE_PROPS_2, 200, this);
		}
		else{
			nextRound();
		}
	}

	@Override
	public void onCardBuy() {
		nextRound();
	}

	@Override
	public void onCardCancel() {
		nextRound();
	}
	
	@Override
	public void onGiftBuySuccess(Gift gift) {
		nextRound();
	}
	
	@Override
	public void onGiftBuyError(Gift gift, Throwable e) {
		nextRound();
	}
	
	@Override
	public void onGiftBuyCancel(Gift gift) {
		nextRound();
	}
	
	private void nextRound(){
		gameWorld.startRound(1);
	}
}
