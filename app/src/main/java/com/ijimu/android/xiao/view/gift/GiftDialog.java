package com.ijimu.android.xiao.view.gift;

import com.ijimu.android.game.UIThread;
import com.ijimu.android.game.display.Button;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.touch.ClickListener;
import com.ijimu.android.game.touch.TouchEvent;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.domain.Gift;
import com.ijimu.android.xiao.effect.DialogEffects;
import com.ijimu.android.xiao.view.DisplayRoot;

public class GiftDialog extends DisplayContainer{

	private static GiftDialog instance;
	
	public static void show(Gift gift, long delay, GiftListener listener){
		if(instance==null){
			instance = new GiftDialog();
			DisplayRoot.getInstance().addDisplayObject(instance, 1);
		}
		instance.setGiftListener(listener);
		instance.setGift(gift);
		instance.show(delay);
	}
	
	private Gift gift;
	
	private GiftListener giftListener;
	private DisplayObject contentImage;
	
	public GiftDialog() {
		setWidth(Constants.SCREEN_WIDTH);
		setHeight(Constants.SCREEN_HEIGHT);
		setClickable(true);
		setVisible(false);
		initContent();
	}
	
	public void show(long delay){
		UIThread.postDelayed(new Runnable() {
			@Override
			public void run() {
				DialogEffects.show(GiftDialog.this);
			}
		}, delay);
	}
	
	public void setGift(Gift gift){
		this.gift = gift;
		updateContentImage(gift);
	}

	public Gift getGift() {
		return gift;
	}
	
	protected void doBuy(){
		if(gift==null) return; 
		new GiftBuyAction(gift, new GiftListener() {
			
			@Override
			public void onGiftBuySuccess(Gift gift) {
				DialogEffects.dismiss(GiftDialog.this);
				if(giftListener!=null) giftListener.onGiftBuySuccess(gift);
			}

			@Override
			public void onGiftBuyError(Gift gift, Throwable e) {
				//if(giftListener!=null) giftListener.onGiftBuyError(gift, e);
			}

			@Override
			public void onGiftBuyCancel(Gift gift) {
				//DialogEffects.dismiss(GiftDialog.this);
				//if(giftListener!=null) giftListener.onGiftBuyCancel(gift);
			}
		});
	}
	
	private void updateContentImage(Gift gift) {
		if(gift.getType()==Gift.TYPE_REBORN){
			contentImage.setBackgroundImage(R.drawable.gift_reborn);
		}
		else if(gift.getType()==Gift.TYPE_PROPS_5){
			contentImage.setBackgroundImage(R.drawable.gift_props_5);
		}
		else if(gift.getType()==Gift.TYPE_PROPS_2){
			contentImage.setBackgroundImage(R.drawable.gift_props_2);
		}
		else if(gift.getType()==Gift.TYPE_BOMB_5){
			contentImage.setBackgroundImage(R.drawable.gift_bomb_5);
		}
		else if(gift.getType()==Gift.TYPE_PAINT_5){
			contentImage.setBackgroundImage(R.drawable.gift_paint_5);
		}
		else if(gift.getType()==Gift.TYPE_REFRESH_5){
			contentImage.setBackgroundImage(R.drawable.gift_refresh_5);
		}
		else if(gift.getType()==Gift.TYPE_OWED){
			contentImage.setBackgroundImage(R.drawable.gift_owed);
		}
		else{
			contentImage.setBackgroundImage(null);
		}
	}
	
	private void initContent(){
		DisplayContainer container = new DisplayContainer();
		container.setWidth(Constants.SCREEN_WIDTH);
		container.setHeight(Constants.SCREEN_HEIGHT);
		container.setX(0);
		container.setY(0);
		initContentImage(container);
		initBuyButton(container);
		initCloseButton(container);
		addChild(container);
	}
	
	
	private void initCloseButton(DisplayContainer parent){
		DisplayObject button = new Button();
		if(ClientConfig.PAY_UI_SMALL){
			button.setWidth(43);
			button.setHeight(45);
			button.setX(384);
			button.setY(800-208-45);
		}else{
			button.setWidth(43);
			button.setHeight(45);
			button.setX(418);
			button.setY(800-70);
		}
		button.setBackgroundImage(R.drawable.button_cancel);
		button.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				DialogEffects.dismiss(GiftDialog.this);
				if(giftListener!=null) giftListener.onGiftBuyCancel(gift);
			}
		});
		if(ClientConfig.PAY_CANCEL_DISABLED) button.setBackgroundImage(null);
		parent.addChild(button);
	}
	
	private void initBuyButton(DisplayContainer parent){
		DisplayObject button = new Button();
		if(ClientConfig.PAY_UI_SMALL){
			button.setWidth(175);
			button.setHeight(71);
			button.setX(147);
			button.setY(800-585-71);
		}else{
			button.setWidth(214);
			button.setHeight(85);
			button.setX(131);
			button.setY(ClientConfig.PAY_BUTTON_OFFSET_Y);
		}
		button.setBackgroundImage(R.drawable.button_ok);
		button.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				doBuy();
			}
		});
		parent.addChild(button);
	}
	
	private void initContentImage(DisplayContainer parent){
		contentImage = new DisplayObject();
		if(ClientConfig.PAY_UI_SMALL){
			contentImage.setX(23);
			contentImage.setY(800-114-574);
			contentImage.setWidth(429);
			contentImage.setHeight(574);
		}else{
			contentImage.setX(0);
			contentImage.setY(0);
			contentImage.setWidth(Constants.SCREEN_WIDTH);
			contentImage.setHeight(Constants.SCREEN_HEIGHT);
		}
		parent.addChild(contentImage);
	}
	
	public void setGiftListener(GiftListener giftListener) {
		this.giftListener = giftListener;
	}
}
