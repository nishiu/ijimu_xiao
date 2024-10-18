package com.ijimu.android.xiao.view.game;

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
import com.ijimu.android.xiao.effect.ScoreFlyEffect;

public class ScoreFlyWall extends DisplayObject implements GameEventListener{
	
	private List<ScoreFlyEffect> effects;
	private GameWorld gameWorld;
	
	public ScoreFlyWall(){
		setX(0);
		setY(106);
		setWidth(Constants.SCREEN_WIDTH);
		setHeight(Constants.SCREEN_HEIGHT);
		effects = new CopyOnWriteArrayList<ScoreFlyEffect>();
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, EventType.SCORE_UP);
	}

	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType()==EventType.SCORE_UP){
			int score = (Integer)event.getData();
			Object source = event.getAttr("source");
			show(score, getFlyStart(source), 500);
		}
	}
	
	private Point getFlyStart(Object source){
		if(source instanceof Point){
			return (Point) source;
		}
		else{
			int x = 0; //Constants.SCREEN_WIDTH/2;
			int y = 0; //Constants.SCREEN_HEIGHT/2-200;
			return new Point(x, y);
		}
	}
	
	private void show(int score, Point startPos, long duration){
		ScoreFlyEffect effect = getEffect();
		effect.setScore(score);
		effect.setStartPoint(startPos);
		effect.setDuration(duration);
		effect.reset();
		addEffect(effect);
	}
	
	private ScoreFlyEffect getEffect(){
		if(effects.size()>0) return effects.remove(0);
		return new ScoreFlyEffect(){
			@Override
			protected void onFinish(DisplayObject displayObject) {
				super.onFinish(displayObject);
				effects.add(this);
			}
		};
	}

}
