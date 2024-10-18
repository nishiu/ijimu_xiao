package com.ijimu.android.xiao.view.prop;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.xiao.domain.Gift;
import com.ijimu.android.xiao.domain.Prop;
import com.ijimu.android.xiao.logic.PropManager;
import com.ijimu.android.xiao.view.gift.GiftShowAction;

public class PropUseAction {
	
	private PropManager propManager;
	
	public PropUseAction(int type){
		propManager = BeanFactory.getBean(PropManager.class);		
		if(propManager.getCount(type)<=0){
			if(type==Prop.TYPE_BOMB) new GiftShowAction(Gift.TYPE_BOMB_5, 0, null);
			if(type==Prop.TYPE_PAINT) new GiftShowAction(Gift.TYPE_PAINT_5, 0, null);
			if(type==Prop.TYPE_REFRESH) new GiftShowAction(Gift.TYPE_REFRESH_5, 0, null);
		}else{
			propManager.use(type);
		}
	}
}
