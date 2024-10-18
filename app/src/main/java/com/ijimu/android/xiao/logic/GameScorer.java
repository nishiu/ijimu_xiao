package com.ijimu.android.xiao.logic;

import java.util.List;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.game.math.Point;
import com.ijimu.android.game.util.LocalStorage;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Block;

public class GameScorer implements GameEventListener{
	
	private static final String HIGH_SCORE = "high_score";
	
	private GameWorld gameWorld;
	private LocalStorage localStorage;
	
	private int highScore;
	
	public GameScorer() {
		gameWorld = BeanFactory.getBean(GameWorld.class);
		localStorage = BeanFactory.getBean(LocalStorage.class);
		gameWorld.addEventListener(this, EventType.BLOCK_WIPE);
		gameWorld.addEventListener(this, EventType.BLOCK_CLEAN);
		gameWorld.addEventListener(this, EventType.ROUND_FINISH);
		gameWorld.addEventListener(this, EventType.BLOCK_FIRE);
		highScore = localStorage.get(HIGH_SCORE, 0);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType()==EventType.BLOCK_CLEAN){
			List<Block> blocks = (List<Block>)event.getData();
			cleanScore(blocks);
		}
		if(event.getType()==EventType.ROUND_FINISH){
			checkFinish();
		}
		if(event.getType()==EventType.BLOCK_FIRE){
			Block block = (Block)event.getData();
			wipeScore(block);
		}
		if(event.getType()==EventType.BLOCK_WIPE){
			List<Block> blocks = (List<Block>)event.getData();
			markWipeScore(blocks);
		}
	}
	
	private void cleanScore(List<Block> blocks) {
		int score = getCleanScore(blocks.size());
		if(score<=0) return;
		updateScore(score);
		fireScoreUp(score, null);
	}

	private void markWipeScore(List<Block> blocks) {
		int score = getWipeScore(blocks.size());
		if(score<=0) return;
		if(blocks.size()<=0) return;
		for(int i=0; i<blocks.size(); i++){
			if(i%2==0) continue;
			int s = Math.min(score, i*20);
			score -= s;
			Block block = blocks.get(i);
			block.setScore(s);
		}
		if(score<=0) return;
		for(int i=blocks.size()-1; i>=0; i--){
			Block block = blocks.get(i);
			if(block.getScore()>0) block.setScore(score+block.getScore());
		}
	}
	
	private void wipeScore(Block block){
		if(block.getScore()<=0) return;
		updateScore(block.getScore());
		int x = block.getX()*Constants.BLOCK_WIDTH;
		int y = block.getY()*Constants.BLOCK_HEIGHT;
		fireScoreUp(block.getScore(), new Point(x, y));
	}
	
	private void checkFinish(){
		if(gameWorld.getScore()>=getTargetScore()){
			gameWorld.fireEvent(new GameEvent(EventType.ROUND_WIN));
		}else{
			gameWorld.fireEvent(new GameEvent(EventType.GAME_OVER));
		}
		if(gameWorld.getScore() > highScore){
			localStorage.put(HIGH_SCORE, gameWorld.getScore());
			highScore = gameWorld.getScore();
		}
	}
	
	private void updateScore(int delta){
		if(delta==0) return;
		int score = gameWorld.getScore()+delta;
		gameWorld.setScore(score);		
	}
	
	private void fireScoreUp(int delta, Object source){
		GameEvent event = new GameEvent(EventType.SCORE_UP, delta);
		event.setAttr("source", source);
		gameWorld.fireEvent(event);
	}
	
	private int getCleanScore(int count){
//		剩9个：奖励100分 
//		剩8个：奖励240分
//		剩7个：奖励420分
//		剩6个：奖励540分
//		剩5个：奖励800分
//		剩4个，奖励1200分
//		剩3个：奖励1440分
//		剩2个：奖励1920分
//		剩1个：奖励2520分
//		剩0个：奖励3000分
		if(count==0) return 3000;
		if(count==1) return 2520;
		if(count==2) return 1920;
		if(count==3) return 1440;
		if(count==4) return 1200;
		if(count==5) return 800;
		if(count==6) return 540;
		if(count==7) return 420;
		if(count==8) return 240;
		if(count==9) return 100;
		return 0;
	}
	
	private int getWipeScore(int count){
//		公式：20+（N-1）*10+5
//	      N大于等于3
//	      2连消直接定义成20。
		if(count<=2) return 20;
		return 20+(count-1)*10+5;
	}
	
	public int getTargetScore(){
//		第一关1000，第二关2500、第三关4500定死
//		  从第四关开始，按如下公式
//		  4500+（n-3）*2000+（n-3）*40
//		  N大于等于4。
		
		int level = gameWorld.getLevel();
		if(level<=1) return 1000;
		if(level==2) return 2500;
		if(level==3) return 4500;
		return 4500+(level-3)*2000+(level-3)*40;
	}
	
	public int getHighScore(){
		return Math.max(highScore, gameWorld.getScore());
	}
}
