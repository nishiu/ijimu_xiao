package com.ijimu.android.xiao.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.game.util.RandomUtils;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Block;

public class BlockManager implements GameEventListener, Constants{
	
	private Block selected;
	private Block pressed;
	
	private Block[][] blocks;
	private GameWorld gameWorld;
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	public BlockManager(){
		initBlocks();
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, EventType.ROUND_START);
	}

	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType()==EventType.ROUND_START){
			resetBlocks();
		}
	}
	
	public void iterate(BlockIterator iterator){
		for(int x=0; x<BLOCK_COLS; x++){
			for(int y=0; y<BLOCK_ROWS; y++){
				iterator.onBlock(getBlock(x, y));
			}
		}
	}
	
	private void initBlocks() {
		blocks = new Block[BLOCK_COLS][];
		for(int x=0; x<blocks.length; x++){
			blocks[x] = new Block[BLOCK_ROWS];
			for(int y=0; y<blocks[x].length; y++){
				Block block = new Block();
				block.setId((long)(x+y*BLOCK_COLS));
				block.setX(x);
				block.setY(y);
				block.setState(Block.STATE_GONE);
				blocks[x][y] = block;
			}
		}
	}
	
	public void exchange(Block src, Block dst){
		int srcX = src.getX();
		int srcY = src.getY();
		src.setX(dst.getX());
		src.setY(dst.getY());
		dst.setX(srcX);
		dst.setY(srcY);
		blocks[src.getX()][src.getY()] = src;
		blocks[dst.getX()][dst.getY()] = dst;
	}
	
	public void resetBlocks(){
		for(int x=0; x<blocks.length; x++){
			for(int y=0; y<blocks[x].length; y++){
				Block block = blocks[x][y];
				block.setState(Block.STATE_ON);
				block.setOffsetX(0);
				block.setOffsetY((y+1)*Constants.SCREEN_HEIGHT+x*BLOCK_HEIGHT*RandomUtils.random(1, 3));
				block.setScore(0);
				resetValue(block);
			}
		}
	}
	
	public void resetValue(Block block){
		int maxColors = getMaxColors();
		block.setValue(RandomUtils.random(0, maxColors));
	}

	public int getMaxColors() {
		if(gameWorld.getLevel()<=1) return 3;
		if(gameWorld.getLevel()<=2) return 4;
		return 5;
	}
	
	public Block getBlock(int x, int y){
		if(x<0||x>=BLOCK_COLS) return null;
		if(y<0||y>=BLOCK_ROWS) return null;
		return blocks[x][y];
	}

//	public void removeBlock(int x, int y){
//		blocks[x][y].setState(Block.STATE_GONE);
//	}
//	
//	public void setBlock(Block block){
//		blocks[block.getX()][block.getY()] = block.getValue();
//	}
	
	public Block[][] getBlocks(){
		return blocks;
	}

	public void setBlocks(Block[][] blocks) {
		this.blocks = blocks;
	}
	
	public boolean isGone(int x ,int y){
		return isGone(getBlock(x, y));
	}
	
	public boolean isGone(Block block){
		if(block == null)return true;
		return block.isGone();
	}

	public Block getSelected() {
		return selected;
	}

	public void setSelected(Block selected) {
		this.selected = selected;
	}

	public Block getPressed() {
		return pressed;
	}

	public void setPressed(Block pressed) {
		this.pressed = pressed;
	}
}
