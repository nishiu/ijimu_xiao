package com.ijimu.android.xiao.logic;

import java.util.LinkedList;
import java.util.List;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.game.util.RandomUtils;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Block;
import com.ijimu.android.xiao.domain.Prop;

public class BlockRefresh implements GameEventListener, Constants{
	
	private GameWorld gameWorld;
	private BlockManager blockManager;
	
	public BlockRefresh(){
		gameWorld = BeanFactory.getBean(GameWorld.class);
		blockManager = BeanFactory.getBean(BlockManager.class);
		gameWorld.addEventListener(this, EventType.PROP_USE);
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType()==EventType.PROP_USE){
			if(gameWorld.getUsingProp()!=Prop.TYPE_REFRESH) return;
			gameWorld.fireEvent(new GameEvent(EventType.PROP_APPLY,gameWorld.getUsingProp()));
			gameWorld.setUsingProp(Prop.TYPE_NONE);
			refresh();
		}
	}
	
//	private void refresh(){
//		blockManager.iterate(new BlockIterator() {
//			@Override
//			public void onBlock(Block block) {
//				if(block.isGone()) return;
//				blockManager.resetValue(block);
//			}
//		});
//	}
	
	private void refresh(){
		List<Block> leftBlocks = new LinkedList<Block>();
		for(int x = 0; x < BLOCK_COLS; x++){
			for(int y = 0; y < BLOCK_ROWS; y++){
				if(blockManager.getBlock(x, y).getState() == Block.STATE_ON){
					leftBlocks.add(blockManager.getBlock(x, y));
				}
			}
		}
		randomLeftBlocks(leftBlocks);
	}
	
	private void randomLeftBlocks(List<Block> leftBlocks){
		if(leftBlocks.size() == 0)return;
		Block src = leftBlocks.get(0);
		Block dst = randomBlock(new LinkedList<Block>(leftBlocks));
		setBlockDest(src, dst);
		blockManager.exchange(src, dst);
		leftBlocks.remove(src);
		randomLeftBlocks(leftBlocks);
	}
	
	private Block randomBlock(List<Block> leftBlocks){
		Block block =leftBlocks.get((RandomUtils.random(0, leftBlocks.size())));
		leftBlocks.remove(block);
		return block;
	}
	
	private void setBlockDest(Block src, Block dst){
		if(src == null || dst == null)return;
		int dx = (src.getX() - dst.getX())*BLOCK_WIDTH;
		int dy = (src.getY() - dst.getY())*BLOCK_HEIGHT;
//		src.setDestX(dx);
//		src.setDestY(dy);
//		dst.setDestX(-dx);
//		dst.setDestY(-dy);
		src.setOffsetX(dx);
		src.setOffsetY(dy);
		dst.setOffsetX(-dx);
		dst.setOffsetY(-dy);
	}
}
