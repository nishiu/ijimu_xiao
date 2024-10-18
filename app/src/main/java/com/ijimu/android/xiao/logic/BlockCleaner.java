package com.ijimu.android.xiao.logic;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Block;

public class BlockCleaner implements Constants, GameEventListener{

	private static final int CHECK_DELAY = 1200;

	private boolean isCleaning;
	private long nextCheckTime;
	
	private GameWorld gameWorld;
	private BlockManager blockManager;
	private BlockWiper blockWiper;
	
	public BlockCleaner(){
		nextCheckTime = -1;
		isCleaning = false;
		blockManager = BeanFactory.getBean(BlockManager.class);
		blockWiper = BeanFactory.getBean(BlockWiper.class);
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, EventType.BLOCK_FIRE);
		gameWorld.addEventListener(this, EventType.PROP_USE);
		gameWorld.addEventListener(this, EventType.PROP_APPLY);
		gameWorld.addEventListener(this, EventType.ROUND_START);
		gameWorld.addEventListener(this, GameEvent.TICK);
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType()==EventType.ROUND_START){
			isCleaning = false;
			nextCheckTime = -1;
		}
		if(event.getType()==EventType.BLOCK_FIRE
			||event.getType()==EventType.PROP_USE
			||event.getType()==EventType.PROP_APPLY){
			markNextCheck();
		}
		else if(event.getType()==GameEvent.TICK){
			if(nextCheckTime<=0) return;
			if(System.currentTimeMillis()<nextCheckTime) return;
			nextCheckTime = -1;
			check();
		}
	}

	private void markNextCheck() {
		nextCheckTime = System.currentTimeMillis()+CHECK_DELAY;
	}
	
	private void check(){
		List<Block> blocks = getExistsBlocks();
		if(isCleaning){			
			checkFinish(blocks);
		}
		else{
			checkStart(blocks);
		}
	}

	private void checkStart(List<Block> blocks) {		
		if(hasLink(blocks)) return;
		isCleaning = true;
		Collections.reverse(blocks);
		blockWiper.wipe(blocks);
		gameWorld.fireEvent(new GameEvent(EventType.BLOCK_CLEAN, blocks));
		checkFinish(blocks);
	}

	private void checkFinish(List<Block> blocks) {
		if(blocks.size()>0) return;
		isCleaning = false;
		gameWorld.fireEvent(new GameEvent(EventType.ROUND_FINISH));
	}
	
	private List<Block> getExistsBlocks(){
		List<Block> blocks = new LinkedList<Block>();
		for(int x = 0; x < BLOCK_COLS;x++){
			for(int y = 0; y < BLOCK_ROWS;y++){
				Block block = blockManager.getBlock(x, y);
				if(block.getState()==Block.STATE_ON) blocks.add(block);
			}
		}
		return blocks;
	}
	
	private boolean hasLink(Collection<Block> blocks){
		for(Block block : blocks){
			if(hasLink(block)) return true;
		}
		return false;
	}
	
	public boolean hasLink(Block src){
		if(src==null||src.isGone()) return false;
		int x = src.getX();
		int y = src.getY();
		return isSameColor(src, blockManager.getBlock(x-1, y))
			||isSameColor(src, blockManager.getBlock(x+1, y))
			||isSameColor(src, blockManager.getBlock(x, y-1))
			||isSameColor(src, blockManager.getBlock(x, y+1));
	}
	
	private boolean isSameColor(Block src, Block dst){
		if(dst==null) return false;
		if(dst.isGone())return false;
		return src.getValue() == dst.getValue();
	}
}
