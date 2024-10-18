package com.ijimu.android.xiao.view.game;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.effect.NoticeEffect;
import com.ijimu.android.xiao.effect.RoundWinEffect;
import com.ijimu.android.xiao.logic.GameScorer;

public class NoticeWall extends DisplayContainer implements GameEventListener{

	private GameWorld gameWorld;
	private GameScorer gameScorer;
	
	private RoundWinEffect roundWinEffect;
	
	public NoticeWall(){
		setX(0);
		setY(0);
		setWidth(Constants.SCREEN_WIDTH);
		setHeight(Constants.SCREEN_HEIGHT);
		gameScorer = BeanFactory.getBean(GameScorer.class);
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, EventType.SCORE_UP);
		gameWorld.addEventListener(this, EventType.BLOCK_WIPE);
		gameWorld.addEventListener(this, EventType.ROUND_START);
	}

	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType()==EventType.SCORE_UP){
			checkRoundWin();
		}
		if(event.getType()==EventType.BLOCK_WIPE){
			int count = (Integer)event.getAttrInt("count");
			int resId= getComboImage(count);
			if(resId>0) show(resId, 1200);
		}
		if(event.getType() == EventType.ROUND_START){
			if(roundWinEffect==null) return;
			removeEffect(roundWinEffect);
			roundWinEffect = null;
		}
	}

	private void checkRoundWin() {
		if(roundWinEffect!=null) return;
		if(gameScorer.getTargetScore()>gameWorld.getScore()) return;
		roundWinEffect = new RoundWinEffect(R.drawable.label_round_win, 600);
		addEffect(roundWinEffect);
	}
	
	private void show(int resId, long duration){
		addEffect(new NoticeEffect(resId, duration));
	}
	
	private int getComboImage(int count){
//		if(count<=4) return -1;
//		if(count<=7) return R.drawable.combo_0;
//		if(count<=12) return R.drawable.combo_1;
//		if(count<=18) return R.drawable.combo_2;
//		if(count<=25) return R.drawable.combo_3;
//		return R.drawable.combo_4;
		if(count<=2) return -1;
		if(count<=4) return R.drawable.combo_0;
		if(count<=6) return R.drawable.combo_1;
		if(count<=8) return R.drawable.combo_2;
		if(count<=10) return R.drawable.combo_3;
		if(count<=12) return R.drawable.combo_4;
		if(count<=14) return R.drawable.combo_5;
		return R.drawable.combo_6;
	}
	
}
