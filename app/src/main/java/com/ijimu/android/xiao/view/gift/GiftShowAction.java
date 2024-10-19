package com.ijimu.android.xiao.view.gift;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.domain.Gift;
import com.ijimu.android.xiao.logic.GiftManager;
import com.ijimu.android.xiao.view.action.ActionSupport;

public class GiftShowAction extends ActionSupport{

	public GiftShowAction(int type, long delay, final GiftListener listener){
		GiftManager giftManager = BeanFactory.getBean(GiftManager.class);
		Gift gift = giftManager.getByType(type);
//		new GiftBuyAction(gift, listener);
		if(ClientConfig.GIFT_AUTO_BUY){
			new GiftBuyAction(gift, listener);
		}else{
			GiftDialog.show(gift, delay, listener);
		}
	}
}
