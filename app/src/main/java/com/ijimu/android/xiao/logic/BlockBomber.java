package com.ijimu.android.xiao.logic;

import java.util.LinkedList;
import java.util.List;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Block;
import com.ijimu.android.xiao.domain.Prop;

public class BlockBomber implements GameEventListener, Constants {
	
	private  static final int BOMB_RADIUS = 1;
	
	private GameWorld gameWorld;
	private BlockWiper blockWiper;
	private BlockManager blockManager;
	
	public BlockBomber(){
		gameWorld = BeanFactory.getBean(GameWorld.class);
		blockWiper = BeanFactory.getBean(BlockWiper.class);
		blockManager = BeanFactory.getBean(BlockManager.class);
		gameWorld.addEventListener(this, EventType.BLOCK_CLICK);
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType()==EventType.BLOCK_CLICK){
			if(gameWorld.getUsingProp()!=Prop.TYPE_BOMB) return;
			gameWorld.fireEvent(new GameEvent(EventType.PROP_APPLY,gameWorld.getUsingProp()));
			gameWorld.setUsingProp(Prop.TYPE_NONE);
			Block block = (Block)event.getData();
			bomb(block);
		}
	}
	
	private void bomb(Block center){ 
		List<Block> bombBlocks = new LinkedList<Block>();
		bombBlocks = findAround(bombBlocks, center);
		if(bombBlocks.size()!=0) blockWiper.wipe(bombBlocks);
	}
	
	private List<Block> findAround(List<Block> bombBlocks, Block center){
		return findAround(bombBlocks, center, BOMB_RADIUS);
	}
	
	private List<Block> findAround(List<Block> bombBlocks, Block center, int radius){
		int startX = center.getX() - radius;
		int startY = center.getY() - radius;
		int endX = center.getX() + radius;
		int endY = center.getY() + radius;
		for(int x = startX; x <= endX; x++){
			for(int y = startY; y <= endY; y++){
				Block block = blockManager.getBlock(x, y);
				if(block!=null&&block.isOn()) bombBlocks.add(block);
			}
		}
		return bombBlocks;
	}

}
