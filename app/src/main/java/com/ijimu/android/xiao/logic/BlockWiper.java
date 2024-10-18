package com.ijimu.android.xiao.logic;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Block;
import com.ijimu.android.xiao.domain.Prop;

public class BlockWiper implements GameEventListener{
	
	private static final int FIRE_INTERVAL = 2;
	
	private List<Block> wippingQueue;
	
	private GameWorld gameWorld;
	private BlockManager blockManager;
	
	public BlockWiper(){
		wippingQueue = new CopyOnWriteArrayList<Block>();
		blockManager = BeanFactory.getBean(BlockManager.class);
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, EventType.BLOCK_CLICK);
		gameWorld.addEventListener(this, GameEvent.TICK);
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType()==EventType.BLOCK_CLICK){
			if(gameWorld.getUsingProp()!=Prop.TYPE_NONE) return;
			Block block = (Block)event.getData();
			checkWipe(block);
		}
		if(event.getType()==GameEvent.TICK){
			if(wippingQueue.size()<=0)return;
			long ticks = gameWorld.getTimer().getTicks();
			if(ticks%FIRE_INTERVAL!=0) return;
			Block block = wippingQueue.remove(0);
			block.setState(Block.STATE_GONE);
			block.setOffsetX(0);
			block.setOffsetY(0);
			gameWorld.fireEvent(new GameEvent(EventType.BLOCK_FIRE, block));
		}
	}
	
	public void wipe(List<Block> blocks){
		wippingQueue.addAll(blocks);
		fireWipeEvent(blocks);
	}
	
	private void checkWipe(Block center) {
		if(center==null) return;
		if(wippingQueue.contains(center)) return;
		List<Block> blocks = findLinked(center);
		if(blocks.size()==0) return;
		blocks.remove(center);
		blocks.add(0, center);
		sortWipeOrder(blocks);
		wippingQueue.addAll(blocks);
		fireWipeEvent(blocks);
	}

	private void fireWipeEvent(List<Block> blocks) {
		GameEvent event = new GameEvent(EventType.BLOCK_WIPE, blocks);
		event.setAttr("count", blocks.size());
		gameWorld.fireEvent(event);
	}
	
	private void sortWipeOrder(List<Block>blocks){
		
	}
	
	private List<Block> findLinked(Block center){
		List<Block> result = new LinkedList<Block>();
		findAround(result, center);
		return result;
	}
	
	private void findAround(List<Block>blocks, Block center){
		int x = center.getX();
		int y = center.getY();			
		if(!blocks.contains(getBlock(x-1, y))) checkValue(blocks, center, getBlock(x-1, y));
		if(!blocks.contains(getBlock(x+1, y))) checkValue(blocks, center, getBlock(x+1, y));
		if(!blocks.contains(getBlock(x, y-1))) checkValue(blocks, center, getBlock(x, y-1));
		if(!blocks.contains(getBlock(x, y+1))) checkValue(blocks, center, getBlock(x, y+1));
	}
	
	private void checkValue(List<Block> blocks, Block src, Block dst){
		if(dst == null || dst.isGone()) return;
		if(wippingQueue.contains(dst)) return;
		if(dst.getValue() == src.getValue()){			
			blocks.add(dst);
			findAround(blocks, dst);
		}
	}
	
	private Block getBlock(int x, int y){
		return blockManager.getBlock(x, y);
	}
}
