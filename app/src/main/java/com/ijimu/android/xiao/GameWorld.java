package com.ijimu.android.xiao;

import com.ijimu.android.game.engine.AbstractGameWorld;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.xiao.domain.Prop;

public class GameWorld extends AbstractGameWorld{

	public static final int SCENE_PORTAL = 0;
	public static final int SCENE_GAME = 1;

	private int scene;
	private int score;
	private int level;
	private int usingProp;
	
	public GameWorld(){
		score = 0;
		level = 0;
		scene = SCENE_PORTAL;
		getTimer().setTickInterval(25);
		usingProp = Prop.TYPE_NONE;
	}

	public void startGame(){
		score = 0;
		level = 1;
		scene = GameWorld.SCENE_GAME;
		fireEvent(new GameEvent(EventType.GAME_START));
		startRound(0);
	}

	public void startRound(int deltaLevel){
		usingProp = Prop.TYPE_NONE;
		level += deltaLevel;
		fireEvent(new GameEvent(EventType.ROUND_START));
	}
	
	public void pause(){
		timer.pause();
		fireEvent(new GameEvent(EventType.GAME_PAUSE), false);
	}
	
	public void resume(){
		timer.resume();
		fireEvent(new GameEvent(EventType.GAME_RESUME));
	}
	
	public int getUsingProp() {
		return usingProp;
	}

	public void setUsingProp(int usingProp) {
		this.usingProp = usingProp;
	}

	public int getScene() {
		return scene;
	}

	public void setScene(int scene) {
		this.scene = scene;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}
}
