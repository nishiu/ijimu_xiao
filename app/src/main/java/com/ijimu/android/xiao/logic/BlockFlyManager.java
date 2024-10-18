package com.ijimu.android.xiao.logic;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Block;

public class BlockFlyManager implements GameEventListener, Constants{
	
	private static final int VY = Constants.BLOCK_HEIGHT/4;
	private static final int VX = Constants.BLOCK_WIDTH/4;
	
	private GameWorld gameWorld;
	private BlockManager blockManager;
	
	public BlockFlyManager() {
		blockManager = BeanFactory.getBean(BlockManager.class);
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, GameEvent.TICK);
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		updateOffset();
	}

	private void updateOffset(){
		for(int x=0;  x<BLOCK_COLS; x++){
			for(int y=0; y<BLOCK_ROWS; y++){
				Block block = blockManager.getBlock(x, y);
				if(block.getOffsetY()!=0){
					int oy = block.getOffsetY()-getV(block.getOffsetY(), VY);
					block.setOffsetY(oy);
				}
				if(block.getOffsetX()!=0){
					int ox = block.getOffsetX()-getV(block.getOffsetX(), VX);
					block.setOffsetX(ox);
				}
			}
		}
	}

	private int getV(int dx, int min){
		if(dx>0) return Math.min(dx, Math.max(dx/4, min));
		return Math.max(dx, Math.min(dx/4, -min)) ;
	}
}
