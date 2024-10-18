package com.ijimu.android.xiao.logic;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Block;
import com.ijimu.android.xiao.domain.Prop;

public class BlockPainter implements GameEventListener, Constants {

	private GameWorld gameWorld;
	private BlockManager blockManager;
	
	public BlockPainter(){
		blockManager = BeanFactory.getBean(BlockManager.class);
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, EventType.BLOCK_CLICK);
		gameWorld.addEventListener(this, EventType.PROP_APPLY);
	}
	
	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType()==EventType.BLOCK_CLICK){
			if(gameWorld.getUsingProp() != Prop.TYPE_PAINT)return;
			Block block = (Block)event.getData();
			blockManager.setSelected(block);
			gameWorld.fireEvent(new GameEvent(EventType.DIALOG_PAINT));
		}
		else if(event.getType() == EventType.PROP_APPLY){
			if(gameWorld.getUsingProp() != Prop.TYPE_PAINT)return;
			gameWorld.setUsingProp(Prop.TYPE_NONE);
			int value = event.getAttrInt("value");
			paint(blockManager.getSelected(), value);
			blockManager.setSelected(null);
		}
	}

	private void paint(Block block, int value) {
		if(block != null) block.setValue(value);
	}
	
}
