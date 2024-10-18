package com.ijimu.android.xiao.view.gift;

import com.ijimu.android.game.res.ApkResources;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.domain.Gift;

public class GiftNames {
	
	public static String getName(int type){
		if(type==Gift.TYPE_REBORN) return ApkResources.getString(R.string.gift_name_reborn);//"复活礼包";
		if(type==Gift.TYPE_PROPS_5) return ApkResources.getString(R.string.gift_name_props5);//"豪华礼包";
		if(type==Gift.TYPE_PROPS_2) return ApkResources.getString(R.string.gift_name_props2);//"成长礼包";	
		if(type==Gift.TYPE_BOMB_5) return ApkResources.getString(R.string.gift_name_bomb5);//"炸弹礼包";	
		if(type==Gift.TYPE_PAINT_5) return ApkResources.getString(R.string.gift_name_paint5);//"刷子礼包";	
		if(type==Gift.TYPE_REFRESH_5) return ApkResources.getString(R.string.gift_name_refresh5);//"旋转礼包";	
		if(type==Gift.TYPE_CARD_DRAW) return ApkResources.getString(R.string.gift_name_card_draw);//"翻牌礼包";	
		return ApkResources.getString(R.string.gift_name_owed);//"道具礼包";		
	}
	
	public static String getName(Gift gift){
		if(gift==null) return ApkResources.getString(R.string.gift_name);//"礼包";
		return getName(gift.getType());
	}
}
