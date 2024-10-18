package com.ijimu.android.xiao.view.game;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import android.graphics.Color;
import android.graphics.Rect;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.game.display.Image;
import com.ijimu.android.game.display.Label;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.logic.GameScorer;
import com.ijimu.android.xiao.view.TextFont;

public class GameScoreBar extends DisplayContainer implements GameEventListener {
	
	private static final int PROGRESS_MIN = 16;
	private static final int PROGRESS_W = 345;

	private class ScoreItem{
		private long time;
		private int score;
		
		public ScoreItem(long time){
			this.time = time;
			this.score = gameWorld.getScore();
		}
	}
	
	private Label currentScoreLabel;
	private Label targetScoreLabel;
	private Image progress;
	
	private List<ScoreItem> updateQueue;
	private GameWorld gameWorld;
	private GameScorer gameScorer;
	
	public GameScoreBar(){
		setX(30);
		setY(30);
		setWidth(355);
		setHeight(45);
		initProgress();
		initCurrentScoreLabel();
		initTargetScoreLabel();
		updateQueue = new CopyOnWriteArrayList<ScoreItem>();
		gameScorer = BeanFactory.getBean(GameScorer.class);
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, EventType.SCORE_UP);
		gameWorld.addEventListener(this, EventType.ROUND_START);
		gameWorld.addEventListener(this, EventType.GAME_RESTORE);
		gameWorld.addEventListener(this, GameEvent.TICK);
	}

	private void initCurrentScoreLabel() {
		currentScoreLabel = new Label();
		currentScoreLabel.setWidth(getWidth());
		currentScoreLabel.setX(250);
		currentScoreLabel.setY(2);
		currentScoreLabel.setHeight(35);
		currentScoreLabel.setTextSize(24);
		currentScoreLabel.setTextColor(Color.parseColor("#FF7FB0"));
		currentScoreLabel.setAlign(Label.ALIGN_RIGHT);
		currentScoreLabel.setTypeface(TextFont.getTypeface());
		addChild(currentScoreLabel);
	}

	private void initTargetScoreLabel(){
		targetScoreLabel = new Label();
		targetScoreLabel.setX(252);
		targetScoreLabel.setY(2);
		targetScoreLabel.setWidth(100);
		targetScoreLabel.setHeight(20);
		targetScoreLabel.setTextSize(24);
		targetScoreLabel.setTypeface(TextFont.getTypeface());
		addChild(targetScoreLabel);
	}
	
	private void initProgress() {
		progress = new Image();
		progress.setX(4);
		progress.setY(9);
		progress.setWidth(PROGRESS_W);
		progress.setHeight(34);
		progress.setSrc(R.drawable.bg_progress);
		addChild(progress);
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType()==EventType.SCORE_UP){
			updateQueue.add(new ScoreItem(System.currentTimeMillis()+500));
		}
		if(event.getType()==EventType.ROUND_START||event.getType()==EventType.GAME_RESTORE){
			targetScoreLabel.setText("/"+gameScorer.getTargetScore());
			updateProgress(gameWorld.getScore());
			updateQueue.clear();
			updateQueue.add(new ScoreItem(System.currentTimeMillis()));
		}
		if(event.getType()==GameEvent.TICK){
			checkUpdate();
		}
	}
	
	private void checkUpdate(){
		if(updateQueue.size()<=0) return;
		ScoreItem item = updateQueue.get(0);
		if(System.currentTimeMillis()<item.time) return;
		updateQueue.remove(0);
		currentScoreLabel.setText(item.score+"");
		updateProgress(item.score);
	}

	private void updateProgress(int score) {
		int w = PROGRESS_W*score/gameScorer.getTargetScore();
		w = Math.max(PROGRESS_MIN, w);
		w = Math.min(w, PROGRESS_W);
		progress.setRect(new Rect(0, 0, w, progress.getHeight()));
		progress.setWidth(w);
	}
}
