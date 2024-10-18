package com.ijimu.android.xiao.view.game;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.Button;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.display.Label;
import com.ijimu.android.game.opengl.GLContext;
import com.ijimu.android.game.touch.ClickListener;
import com.ijimu.android.game.touch.TouchEvent;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.logic.GameScorer;
import com.ijimu.android.xiao.view.TextFont;

public class GameStatePane extends DisplayContainer {
	
	private Label hightScoreLabel;
	private Label roundLabel;

	private GameWorld gameWorld;
	private GameScorer gameScorer;

	public GameStatePane(){
		setWidth(480);
		setHeight(113);
		setY(Constants.SCREEN_HEIGHT - getHeight());
		setBackgroundImage(R.drawable.bg_state_bar);
		initRound();
		initHighScore();
		initPauseButton();
		addChild(new GameScoreBar());
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameScorer = BeanFactory.getBean(GameScorer.class);
	}
	
	@Override
	protected void onDraw(GLContext glContext){
		roundLabel.setText(gameWorld.getLevel()+"");		
		hightScoreLabel.setText(gameScorer.getHighScore()+"");
		super.onDraw(glContext);
	}
	
	private void initHighScore(){
		hightScoreLabel = new Label();
		hightScoreLabel.setWidth(140);
		hightScoreLabel.setHeight(20);
		hightScoreLabel.setX(140);
		hightScoreLabel.setY(73);
		hightScoreLabel.setTextSize(24);
		hightScoreLabel.setTypeface(TextFont.getTypeface());
		addChild(hightScoreLabel);
	}
	
	private void initRound(){
		roundLabel = new Label();
		roundLabel.setWidth(100);
		roundLabel.setHeight(20);
		roundLabel.setX(308);
		roundLabel.setY(73);
		roundLabel.setTextSize(24);
		roundLabel.setTypeface(TextFont.getTypeface());
		addChild(roundLabel);
	}
		
	private void initPauseButton(){
		DisplayObject button = new Button();
		button.setX(396);
		button.setY(41);
		button.setWidth(57);
		button.setHeight(57);
		button.setBackgroundImage(R.drawable.button_pause);
		button.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				new GamePauseAction();
			}
		});
		addChild(button);
	}
}
