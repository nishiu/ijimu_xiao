package com.ijimu.android.xiao.view.gift;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.dialog.SimpleConfirm;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.MainActivity;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.domain.Gift;
import com.ijimu.android.xiao.logic.GiftManager;
import com.ijimu.android.xiao.view.action.ActionSupport;

public class GiftShowAction extends ActionSupport{

	public GiftShowAction(int type, long delay, final GiftListener listener){
		GiftManager giftManager = BeanFactory.getBean(GiftManager.class);
		Gift gift = giftManager.getByType(type);
//		new GiftBuyAction(gift, listener);
//		if(ClientConfig.GIFT_AUTO_BUY){
//			new GiftBuyAction(gift, listener);
//		}else{
//			GiftDialog.show(gift, delay, listener);
//		}
		SimpleConfirm.show(MainActivity.getInstance().getString(R.string.gift_video_ad_watch_alert), new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialogInterface, int i) {
				new GiftBuyAction(gift,listener);
			}
		},null,MainActivity.getInstance().getString(R.string.gift_video_ad_watch_conform_title),MainActivity.getInstance().getString(R.string.gift_video_ad_watch_cancel_title));
	}
}
