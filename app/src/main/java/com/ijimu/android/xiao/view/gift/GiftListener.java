package com.ijimu.android.xiao.view.gift;

import com.ijimu.android.xiao.domain.Gift;


public interface GiftListener{
	public void onGiftBuySuccess(Gift gift);
	public void onGiftBuyError(Gift gift, Throwable e);
	public void onGiftBuyCancel(Gift gift);
}