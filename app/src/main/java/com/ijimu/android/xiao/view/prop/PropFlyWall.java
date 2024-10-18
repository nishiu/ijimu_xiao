package com.ijimu.android.xiao.view.prop;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.game.math.Point;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.domain.Card;
import com.ijimu.android.xiao.domain.Prop;
import com.ijimu.android.xiao.effect.PropFlyEffect;
import com.ijimu.android.xiao.logic.PropManager;

public class PropFlyWall extends DisplayObject implements GameEventListener{
	
	private List<PropFlyEffect> effects;
	private GameWorld gameWorld;
	private PropManager propManager;
	
	public PropFlyWall(){
		setX(0);
		setY(0);
		setWidth(Constants.SCREEN_WIDTH);
		setHeight(Constants.SCREEN_HEIGHT);
		effects = new CopyOnWriteArrayList<PropFlyEffect>();
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, EventType.CARD_DRAW);
		propManager = BeanFactory.getBean(PropManager.class);
	}

	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType()==EventType.CARD_DRAW){
			Card card = (Card)event.getData();
			fly(card, 500);
		}
	}
	
	private void fly(Card card, long duration){
		if(card == null)return;
		PropFlyEffect effect = getEffect();
		effect.setDuration(duration);
		effect.setStartPoint(getStartPoint(card));
		effect.setEndPoint(getEndPoint(card.getProp()));
		effect.setResId(getImage(card.getProp()));
		effect.setProp(card.getProp());
		effect.reset();
		addEffect(effect);
	}
	
	private PropFlyEffect getEffect(){
		if(effects.size()>0) return effects.remove(0);
		return new PropFlyEffect(){
			@Override
			protected void onFinish(DisplayObject displayObject) {
				super.onFinish(displayObject);
				effects.add(this);
				Prop prop = getProp();
				propManager.addCount(prop.getType(), prop.getCount());
			}
		};
	}

	private Point getStartPoint(Card card){
		return new Point(card.getX(),card.getY());
	}
	
	private Point getEndPoint(Prop prop){
		int propType = prop.getType();
		if(propType == Prop.TYPE_BOMB)return new Point(191,25);
		if(propType == Prop.TYPE_PAINT)return new Point(290,25);
		if(propType == Prop.TYPE_REFRESH)return new Point(391,25);
	    return null;
	}
	
	private int getImage(Prop prop){
		int type = prop.getType();
		if(type == Prop.TYPE_BOMB)return R.drawable.prop_bomb;
		if(type == Prop.TYPE_PAINT)return R.drawable.prop_paint;
		if(type == Prop.TYPE_REFRESH)return R.drawable.prop_refresh;
		return -1;
	}
}
