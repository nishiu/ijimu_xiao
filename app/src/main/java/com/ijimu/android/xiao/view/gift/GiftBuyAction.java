package com.ijimu.android.xiao.view.gift;

import android.app.Activity;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.UIThread;
import com.ijimu.android.game.domain.PayInfo;
import com.ijimu.android.game.plugin.PayCallback;
import com.ijimu.android.game.res.ApkResources;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.domain.Gift;
import com.ijimu.android.xiao.logic.GiftManager;
import com.ijimu.android.xiao.logic.PayManager;
import com.ijimu.android.xiao.view.action.ActionSupport;

public class GiftBuyAction extends ActionSupport{
	
	private GiftListener callback;
	private GiftManager giftManager;
	private PayManager payManager;
	
	public GiftBuyAction(final Gift gift, GiftListener callback){
		this.callback = callback;
		giftManager = BeanFactory.getBean(GiftManager.class);
		payManager = BeanFactory.getBean(PayManager.class);
		UIThread.post(new Runnable() {
			@Override
			public void run() {
				load(gift);
			}
		});
		
	}
	
	private void load( final Gift gift){
		if(ClientConfig.PAY_LOADING_NOTICE) showProgress(ApkResources.getString(R.string.progress_tips_gift));//"正在领取礼包...");
		PayInfo payInfo = new PayInfo(gift.getMoney(), gift.getType());
		payInfo.setName(GiftNames.getName(gift));
		payManager.pay(payInfo, new PayCallback() {
			@Override
			public void onSuccess(PayInfo payInfo) {
				dismissProgress();
				giftManager.buy(gift);
				if(ClientConfig.PAY_SUCCESS_NOTICE){
					showNotice(ApkResources.getString(R.string.notice_gift_success));
//					showNotice("礼包领取成功");
				}
				if(callback!=null) callback.onGiftBuySuccess(gift);
			}
			
			@Override
			public void onError(PayInfo payInfo, Throwable e) {
				dismissProgress();
				logger.error("buy gift error", e);
				if(ClientConfig.PAY_ERROR_AS_SUCCESS){
					onSuccess(payInfo);
					return;
				}
				if(ClientConfig.PAY_ERROR_NOTICE) {
					showNotice(ApkResources.getString(R.string.notice_gift_error)+e.getMessage());
//					showNotice("礼包领取失败: "+e.getMessage());
				}
				if(callback!=null) callback.onGiftBuyError(gift, e);
			}

			@Override
			public void onCancel(PayInfo payInfo) {
				dismissProgress();
				if(ClientConfig.PAY_ERROR_NOTICE) showNotice(ApkResources.getString(R.string.notice_gift_cancel));//"礼包领取取消");
				if(callback!=null) callback.onGiftBuyCancel(gift);
			}
		});
	}
	
}
