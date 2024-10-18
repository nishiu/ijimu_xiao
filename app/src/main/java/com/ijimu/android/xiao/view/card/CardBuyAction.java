package com.ijimu.android.xiao.view.card;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.domain.PayInfo;
import com.ijimu.android.game.plugin.PayCallback;
import com.ijimu.android.game.res.ApkResources;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.domain.Gift;
import com.ijimu.android.xiao.logic.CardManager;
import com.ijimu.android.xiao.logic.PayManager;
import com.ijimu.android.xiao.view.action.ActionSupport;
import com.ijimu.android.xiao.view.gift.GiftNames;

public class CardBuyAction extends ActionSupport{
	
	public static interface Callback{
		public void onCardBuySuccess();
		public void onCardBuyFailure(Throwable e);
	}
	
	private Callback callback;
	private CardManager cardManager;
	private PayManager payManager;

	public CardBuyAction(Callback callback) {
		this.callback = callback;
		cardManager = BeanFactory.getBean(CardManager.class);
		payManager = BeanFactory.getBean(PayManager.class);
		buy();
	}

	private void buy(){
		//showProgress("正在领取全部道具...");
		PayInfo payInfo = new PayInfo(ClientConfig.CARD_DRAW_ALL_MONEY, Gift.TYPE_CARD_DRAW);
		payInfo.setName(GiftNames.getName(Gift.TYPE_CARD_DRAW));
		payManager.pay(payInfo, new PayCallback() {
			
			@Override
			public void onSuccess(PayInfo payInfo) {
				dismissProgress();
				cardManager.drawAll();
				if(ClientConfig.PAY_SUCCESS_NOTICE) showNotice(ApkResources.getString(R.string.notice_buy_success));//"道具领取成功");
				if(callback!=null) callback.onCardBuySuccess();
			}
			
			@Override
			public void onError(PayInfo payInfo, Throwable e) {
				dismissProgress();
				logger.error("buy cards error", e);
				if(ClientConfig.PAY_ERROR_AS_SUCCESS){
					onSuccess(payInfo);
					return;
				}
				if(ClientConfig.PAY_ERROR_NOTICE) {
					showNotice(ApkResources.getString(R.string.notice_buy_error)+e.getMessage());
//					showNotice("道具领取失败: "+e.getMessage());
				}
				if(callback!=null) callback.onCardBuyFailure(e);
			}

			@Override
			public void onCancel(PayInfo payInfo) {
				
			}
		});
	}
}
