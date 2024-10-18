package com.ijimu.android.xiao.logic;

import java.util.Collection;
import java.util.LinkedList;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Block;

public class BlockMerger implements GameEventListener, Constants {
	
	private GameWorld gameWorld;
	private BlockManager blockManager;
	
	public BlockMerger(){
		gameWorld = BeanFactory.getBean(GameWorld.class);
		blockManager = BeanFactory.getBean(BlockManager.class);
		gameWorld.addEventListener(this, EventType.BLOCK_FIRE);
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType()==EventType.BLOCK_FIRE){
			mergeH();
			mergeV();
		}
	}
	
	//v
	private void mergeV() {
		for(int x = 0;  x < BLOCK_COLS; x++){
			mergeV(x);
		}
	}
	
	private void mergeV(int x){
		for(int y = BLOCK_ROWS-2; y >=0; y--){
			if(blockManager.getBlock(x, y).isGone()){
				for(int k = y; k <BLOCK_ROWS-1; k++){
					Block down = blockManager.getBlock(x, k);
					Block up = blockManager.getBlock(x, k+1);
					blockManager.exchange(down, up);
					up.setOffsetY(up.getOffsetY()+Constants.BLOCK_HEIGHT);
				}
			}
		}
	}
	
	//h
	private void mergeH(){
		Collection<Integer> emptyCols = new LinkedList<Integer>();
		for(int x = BLOCK_COLS-1; x >=0; x--){			
			if(isColsEmpty(x)) emptyCols.add(x);
		}
		for(int x : emptyCols){
			exchangeCols(x);
		}
	}
	
	private boolean isColsEmpty(int x){
		for(int y = 0; y < BLOCK_ROWS; y++){
			if(blockManager.getBlock(x, y).isOn()) return false;
		}
		return true;
	}
	
	private void exchangeCols(int colNum){
		for(int y = BLOCK_ROWS-1; y >= 0; y--){
			for(int x = colNum; x < BLOCK_COLS - 1; x++){
				Block left = blockManager.getBlock(x, y);
				Block right = blockManager.getBlock(x+1, y);
				blockManager.exchange(left, right);
				right.setOffsetX(right.getOffsetX()+Constants.BLOCK_WIDTH);
			}
		}
	}
	

}
