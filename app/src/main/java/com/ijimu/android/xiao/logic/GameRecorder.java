package com.ijimu.android.xiao.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.DeamonThread;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.game.util.LocalStorage;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Block;
import com.ijimu.android.xiao.domain.Prop;
import com.ijimu.android.xiao.domain.Record;

public class GameRecorder implements GameEventListener{
	
	private static final String STORE_KEY = "game_record";
	
	private GameWorld gameWorld;
	private BlockManager blockManager;
	private LocalStorage localStorage;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public GameRecorder(){
		gameWorld = BeanFactory.getBean(GameWorld.class);
		blockManager = BeanFactory.getBean(BlockManager.class);
		localStorage = BeanFactory.getBean(LocalStorage.class);
		gameWorld.addEventListener(this, EventType.GAME_PAUSE);
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		if(gameWorld.getScene()!=GameWorld.SCENE_GAME) return;
		DeamonThread.post(new Runnable() {			
			@Override
			public void run() {
				logger.info("save game record in deamon");
				save();
			}
		});
	}

	public synchronized void save(){
		Record record = new Record();
		record.setBlocks(blockManager.getBlocks());
		record.setLevel(gameWorld.getLevel());
		record.setScore(gameWorld.getScore());
		localStorage.put(STORE_KEY, record);
	}
	
	public synchronized boolean restore(){
		Record record = localStorage.get(STORE_KEY, Record.class);
		if(record==null) return false;
		gameWorld.setScore(record.getScore());
		gameWorld.setLevel(record.getLevel());
		gameWorld.setUsingProp(Prop.TYPE_NONE);
		blockManager.setBlocks(record.getBlocks());
		
		gameWorld.setScene(GameWorld.SCENE_GAME);
		gameWorld.fireEvent(new GameEvent(EventType.GAME_RESTORE));
		if(!hasBlockOn()) gameWorld.startRound(1);		
		return true;
	}
	
	private boolean hasBlockOn(){
		for(int x = 0; x < Constants.BLOCK_COLS;x++){
			for(int y = 0; y < Constants.BLOCK_ROWS;y++){
				Block block = blockManager.getBlock(x, y);
				if(block.getState()==Block.STATE_ON) return true;
			}
		}
		return false;
	}
}
