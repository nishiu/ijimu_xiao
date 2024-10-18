package com.ijimu.android.xiao.logic;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.game.util.RandomUtils;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Block;

public class BlockBlinker implements GameEventListener{
	
	private static final int MAX_BLINKS = 1;
	private static final int BLINKING_TIME = 600;

	private Map<Block, Long> blinks; //block->startTime
	
	private GameWorld gameWorld;
	private BlockManager blockManager;
	
	public BlockBlinker(){
		blinks = new ConcurrentHashMap<Block, Long>();
		blockManager = BeanFactory.getBean(BlockManager.class);
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, GameEvent.TICK);
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType()==GameEvent.TICK){
			long ticks = gameWorld.getTimer().getTicks();
			if(ticks%6!=0) return;
			updateState();
			selectBlinks();
		}
	}

	private void updateState(){
		long time = System.currentTimeMillis();
		for(Block block : blinks.keySet()){
			long startTime = blinks.get(block);
			if(time-startTime>BLINKING_TIME) blinks.remove(block);
		}
	}
	
	private void selectBlinks(){
		if(blinks.size()>=MAX_BLINKS) return;		
		int x = RandomUtils.random(Constants.BLOCK_COLS);
		int y = RandomUtils.random(Constants.BLOCK_ROWS);
		Block block = blockManager.getBlock(x, y);
		if(blockManager.isGone(block)) return;
		blinks.put(block, System.currentTimeMillis());
	}
	
	public boolean isBlinking(Block block){
		return blinks.containsKey(block);
	}	
	
}
