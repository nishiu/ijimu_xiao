package com.ijimu.android.xiao.logic;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.util.LocalStorage;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Gift;
import com.ijimu.android.xiao.domain.Prop;

public class GiftManager {

	private static final String GIFT_OWED_BUY = "gift.owed_buy";

	private Map<Object, Gift> gifts;
	private boolean isOwedGiftBuy;
	
	private GameWorld gameWorld;
	private PropManager propManager;
	private LocalStorage localStorage;
	
	public GiftManager(){
		gameWorld = BeanFactory.getBean(GameWorld.class);
		propManager = BeanFactory.getBean(PropManager.class);
		localStorage = BeanFactory.getBean(LocalStorage.class);
		isOwedGiftBuy = localStorage.get(GIFT_OWED_BUY, false);
		initGifts();
	}
	
	private void initGifts(){
		gifts = new HashMap<Object, Gift>();
	    createGift(Gift.TYPE_REBORN, 6, 2, 2, 2);
	    createGift(Gift.TYPE_PROPS_5, 10, 5, 5, 5);
	    createGift(Gift.TYPE_PROPS_2, 6, 2, 2, 2);
	    createGift(Gift.TYPE_BOMB_5, 6, 5, 0, 0);
	    createGift(Gift.TYPE_PAINT_5, 6, 0, 5, 0);
	    createGift(Gift.TYPE_REFRESH_5, 6, 0, 0, 5);
	    createGift(Gift.TYPE_OWED, 1, 2, 0, 0);
	}
	
	private Gift createGift(int giftType, int money, int bombCount, int paintCount, int refreshCount){
		Gift gift = new Gift();
		gift.setType(giftType);
		gift.setMoney(money);
		gift.setBombCount(bombCount);
		gift.setPaintCount(paintCount);
		gift.setRefreshCount(refreshCount);
		gifts.put(gift.getType(), gift);
		return gift;
	}
	
	public List<Gift> getGifts(){
		return new LinkedList<Gift>(gifts.values());
	}
	
	public boolean isOwedGiftBuy() {
		return isOwedGiftBuy;
	}
	
	public void buy(Gift gift){
		if(gift.getType()==Gift.TYPE_OWED){
			isOwedGiftBuy = true;
			localStorage.put(GIFT_OWED_BUY, true);
		}
		propManager.addCount(Prop.TYPE_BOMB, gift.getBombCount());
		propManager.addCount(Prop.TYPE_PAINT, gift.getPaintCount());
		propManager.addCount(Prop.TYPE_REFRESH, gift.getRefreshCount());
		gameWorld.fireEvent(new GameEvent(EventType.GIFT_BUY, gift));
	}
	
	public Gift getByType(int type){
		return gifts.get(type);
	}
}
