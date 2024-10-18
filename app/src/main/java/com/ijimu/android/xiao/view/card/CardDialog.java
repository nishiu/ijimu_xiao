package com.ijimu.android.xiao.view.card;

import android.graphics.Color;
import android.text.Html;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.UIThread;
import com.ijimu.android.game.display.Button;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.display.Label;
import com.ijimu.android.game.opengl.GLContext;
import com.ijimu.android.game.res.ApkResources;
import com.ijimu.android.game.touch.ClickListener;
import com.ijimu.android.game.touch.TouchEvent;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.effect.DialogEffects;
import com.ijimu.android.xiao.logic.CardManager;
import com.ijimu.android.xiao.view.DisplayRoot;

public class CardDialog extends DisplayContainer{

	private static CardDialog instance;
	
	public static void show(long delay, CardListener listener){
		if(instance==null){
			instance = new CardDialog();
			DisplayRoot.getInstance().addDisplayObject(instance, 1);
		}
		instance.setCardListener(listener);
		instance.show(delay);
	}
	
	private CardManager cardManager;

	private CardListener cardListener;
	private DisplayObject drawAlertMessage;
	private Label takeAllAlertMessage;
	private Button takeAllButton;
	
	public CardDialog() {
		setWidth(Constants.SCREEN_WIDTH);
		setHeight(Constants.SCREEN_HEIGHT);
		setClickable(true);
		setVisible(false);
		setBackgroundColor(Color.parseColor("#88000000"));
		initContent();
		cardManager = BeanFactory.getBean(CardManager.class);		
	}
	
	private void show(long delay) {
		cardManager.reset();
		UIThread.postDelayed(new Runnable() {
			@Override
			public void run() {
				DialogEffects.show(CardDialog.this);
			}
		}, delay);
	}
	
	@Override
	protected void onDraw(GLContext glContext){
		if(cardManager.getDrawed()!=null){
			takeAllButton.setVisible(true);
			drawAlertMessage.setVisible(false);
			takeAllAlertMessage.setVisible(true);
		}else{
			takeAllButton.setVisible(false);
			drawAlertMessage.setVisible(true);
			takeAllAlertMessage.setVisible(false);
		}
		super.onDraw(glContext);
	}
	
	private void initContent(){
		DisplayContainer container = new DisplayContainer();
		container.setWidth(Constants.SCREEN_WIDTH);
		container.setHeight(Constants.SCREEN_HEIGHT);
		container.setX((getWidth() - container.getWidth())/2);
		container.setY((getHeight() - container.getHeight())/2);
		initLight(container);
		initCardList(container);
		initCloseButton(container);
		initTakeAllButton(container);
		initDrawMessage(container);
		initTakeAllMessage(container);
		initTitle(container);
		addChild(container);
	}
	
	private void initLight(DisplayContainer parent){
		DisplayObject light = new Button();
		light.setWidth(480);
		light.setHeight(505);
		light.setX((parent.getWidth() - light.getWidth())/2);
		light.setY(parent.getHeight()- 125 -light.getHeight()/2);
		light.setBackgroundImage(R.drawable.bg_light);
		parent.addChild(light);
	}
	
	private void initCloseButton(DisplayContainer parent){
		Button button = new Button();
		button.setWidth(45);
		button.setHeight(45);
		button.setX(parent.getWidth() - 50);
		button.setY(parent.getHeight() - 75);
		button.setBackgroundImage(R.drawable.button_close);
		button.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				dismiss(0);
				if(cardListener!=null) cardListener.onCardCancel();
			}
		});
		parent.addChild(button);
	}
	
	private void initTakeAllButton(DisplayContainer parent){
		takeAllButton = new Button();
		takeAllButton.setWidth(247);
		takeAllButton.setHeight(91);
		takeAllButton.setX((parent.getWidth() - takeAllButton.getWidth())/2);
		takeAllButton.setY(136);
		takeAllButton.setBackgroundImage(R.drawable.button_take_all);
		takeAllButton.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				if(cardManager.getDrawed()==null) return;
				new CardBuyAction(new CardBuyAction.Callback(){
					@Override
					public void onCardBuySuccess() {
						dismiss(600);
						if(cardListener!=null) cardListener.onCardBuy();
					}

					@Override
					public void onCardBuyFailure(Throwable e) {
					}
				});
			}
		});
		parent.addChild(takeAllButton);
	}
	
	private void dismiss(long delay){
		UIThread.postDelayed(new Runnable() {
			@Override
			public void run() {
				DialogEffects.dismiss(CardDialog.this);
			}
		}, delay);		
	}
	
	private void initDrawMessage(DisplayContainer parent){
		drawAlertMessage = new DisplayObject();
		drawAlertMessage.setWidth(420);
		drawAlertMessage.setHeight(30);
		drawAlertMessage.setY(270);
		drawAlertMessage.setX((parent.getWidth()-drawAlertMessage.getWidth())/2);
		drawAlertMessage.setBackgroundImage(R.drawable.label_card_take);
		parent.addChild(drawAlertMessage);
	}
	
	private void initTakeAllMessage(DisplayContainer parent){
		takeAllAlertMessage = new Label();
		takeAllAlertMessage.setTextSize(14);
		takeAllAlertMessage.setWidth(426);
		takeAllAlertMessage.setHeight(100);
		takeAllAlertMessage.setY(13);
		takeAllAlertMessage.setX(426-20);
		takeAllAlertMessage.setAlign(Label.ALIGN_RIGHT);
		takeAllAlertMessage.setSingleLine(false);
		takeAllAlertMessage.setBlod(true);
		takeAllAlertMessage.setStrokeWidth(0);
		takeAllAlertMessage.setMaxWidth(380);
		takeAllAlertMessage.setTextColor(Color.parseColor("#653819"));
		String str = ApkResources.getString(R.string.pay_notice, ApkResources.getString(R.string.notice_buy_get_all), ClientConfig.CARD_DRAW_ALL_MONEY+"");
		takeAllAlertMessage.setText(Html.fromHtml(str));
		parent.addChild(takeAllAlertMessage);
	}
	
	private void initTitle(DisplayContainer parent){
		DisplayObject title = new DisplayObject();
		title.setWidth(430);
		title.setHeight(137);
		title.setX((parent.getWidth()-title.getWidth())/2);
		title.setY(parent.getHeight()-title.getHeight()-75);
		title.setBackgroundImage(R.drawable.label_got);
		parent.addChild(title);
	}
	
	private void initCardList(DisplayContainer parent){
		CardList cardList = new CardList();
		cardList.setX((parent.getWidth() - cardList.getWidth())/2);
		cardList.setY((parent.getHeight() - cardList.getHeight())/2);
		parent.addChild(cardList);
		cardList.init();
	}

	public void setCardListener(CardListener cardListener) {
		this.cardListener = cardListener;
	}

}
