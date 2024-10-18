package com.ijimu.android.xiao.view.card;

import android.graphics.Color;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.Button;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.display.Label;
import com.ijimu.android.game.opengl.GLContext;
import com.ijimu.android.game.touch.ClickListener;
import com.ijimu.android.game.touch.TouchEvent;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.domain.Card;
import com.ijimu.android.xiao.domain.Prop;
import com.ijimu.android.xiao.effect.ReversionEffect;
import com.ijimu.android.xiao.logic.CardManager;

public class CardButton extends DisplayContainer{

	private Button drawButton;
	private DisplayContainer propPane;
	private Label propCount;
	private DisplayObject propImage;
	
	private Card card;
	private CardManager cardManager;
	private ReversionEffect cardReversionAnim;
	
	public CardButton(Card card) {
		this.card = card;
		cardManager = BeanFactory.getBean(CardManager.class);
		setWidth(118);
		setHeight(165);
		setClickable(true);
		initDrawButton();
		initPropPane();
	}
	
	private void initDrawButton(){
		drawButton = new Button();
		drawButton.setWidth(getWidth());
		drawButton.setHeight(getHeight());
		drawButton.setBackgroundImage(R.drawable.card_unknow);
		drawButton.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				card.setReversion(true);
			}
		});
		addChild(drawButton);
	}
	
	private void initPropPane(){
		propPane = new DisplayContainer();
		propPane.setWidth(getWidth());
		propPane.setHeight(getHeight());
		propPane.setBackgroundImage(R.drawable.card);
		addChild(propPane);
		
		propImage = new DisplayObject();
		propImage.setWidth(79);
		propImage.setHeight(114);
		propImage.setX((getWidth() - propImage.getWidth())/2);
		propImage.setY((getHeight() - propImage.getHeight())/2+5);
		propImage.setBackgroundImage(getImage(card));
		propPane.addChild(propImage);
		
		propCount = new Label();
		propCount.setWidth(getWidth());
		propCount.setHeight(30);
		propCount.setTextColor(Color.parseColor("#00B9FF"));
		propCount.setStrokeWidth(4);
		propCount.setStrokeColor(Color.WHITE);
		propCount.setTextSize(22);
		propCount.setX(0);
		propCount.setY(30);
		propCount.setAlign(Label.ALIGN_CENTER);
		propPane.addChild(propCount);
	}
	
	protected void onDraw(GLContext glContext){
		if(card.isReversion()){
			propImage.setBackgroundImage(getImage(card));
			propCount.setText("x"+card.getProp().getCount());
			startReversionAnim();
			//if(cardManager.isDrawed(card)) glContext.scaleBy(1.2f, 1.2f, getWidth()/2, getHeight()/2);
		}else{
			drawButton.setVisible(true);
			propPane.setVisible(false);
			cardReversionAnim = null;
		}
		super.onDraw(glContext);
	}
	
	private void startReversionAnim(){
		if(cardReversionAnim != null)return;
			cardReversionAnim = new ReversionEffect(drawButton, propPane, 300){
			@Override
			public void onFinished() {
				super.onFinished();
				if(cardManager.getDrawed() == null){
					cardManager.draw(card);
				}
			}
		};
	}
	
	private int getImage(Card card){
		if(card == null)return -1;
		int type = card.getProp().getType();
		if(type == Prop.TYPE_BOMB)return R.drawable.prop_bomb;
		if(type == Prop.TYPE_PAINT)return R.drawable.prop_paint;
		if(type == Prop.TYPE_REFRESH)return R.drawable.prop_refresh;
		return -1;
	}
}
