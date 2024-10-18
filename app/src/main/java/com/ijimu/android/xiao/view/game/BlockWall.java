package com.ijimu.android.xiao.view.game;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.opengl.GLContext;
import com.ijimu.android.game.opengl.GLImage;
import com.ijimu.android.game.touch.TouchEvent;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.domain.Block;
import com.ijimu.android.xiao.effect.BlockFireEffect;
import com.ijimu.android.xiao.logic.BlockBlinker;
import com.ijimu.android.xiao.logic.BlockIterator;
import com.ijimu.android.xiao.logic.BlockManager;

public class BlockWall extends DisplayContainer implements Constants{
	
	private static final int STATE_NORMAL = 0;
	private static final int STATE_PRESSED = 1;
	private static final int STATE_SELECTED = 2;
	
	private static final int OFFSET_X = 0;
	private static final int OFFSET_Y = 126;
	
	private static final float PRESSED_SCALE = 1.2f;
	private static final float SELECTED_SCALE = 1.4f;
	
	private GameWorld gameWorld;
	private BlockManager blockManager;
	private BlockBlinker blockBlinker;
	
	private GLImage glImage;

	public BlockWall(){
		setX(OFFSET_X);
		setY(OFFSET_Y);
		setWidth(BLOCK_COLS*BLOCK_WIDTH);
		setHeight(BLOCK_ROWS*BLOCK_HEIGHT);
		initGLImage();
		initFireEffect();
		gameWorld = BeanFactory.getBean(GameWorld.class);
		blockManager = BeanFactory.getBean(BlockManager.class);
		blockBlinker = BeanFactory.getBean(BlockBlinker.class);
	}

	private void initFireEffect(){
		DisplayObject display = new DisplayObject();
		display.setX(0);
		display.setY(0);
		display.setWidth(getWidth());
		display.setHeight(getHeight());
		display.addEffect(new BlockFireEffect());
		addChild(display);
	}
	
	private void initGLImage() {
		glImage = new GLImage();
		glImage.setWidth(BLOCK_WIDTH);
		glImage.setHeight(BLOCK_HEIGHT);
	}
	
	@Override
	public void onDraw(GLContext glContext) {
		drawBlocks(glContext);
		super.onDraw(glContext);
	}

	private void drawBlocks(final GLContext glContext){
		blockManager.iterate(new BlockIterator() {
			@Override
			public void onBlock(Block block) {
				paint(glContext, block, STATE_NORMAL);
			}
		});
		paint(glContext, blockManager.getPressed(), STATE_PRESSED);
		paint(glContext, blockManager.getSelected(), STATE_SELECTED);
	}
	
	@Override
	protected boolean onTouch(TouchEvent event) {
		super.onTouch(event);
		int x = (int)event.getX()/BLOCK_WIDTH;
		int y = (int)event.getY()/BLOCK_HEIGHT;
		Block block = blockManager.getBlock(x, y);
		if(event.getAction()==TouchEvent.ACTION_DOWN){
			if(block==null) return false;
			if(block.isGone()) return false;
			blockManager.setPressed(block);
		}
		if(event.getAction()==TouchEvent.ACTION_UP){
			checkClick(block);
			blockManager.setPressed(null);
		}
		return true;
	}

	private void checkClick(Block block) {
		if(block==null) return;
		if(!block.equals(blockManager.getPressed())) return;
		gameWorld.fireEvent(new GameEvent(EventType.BLOCK_CLICK, block));
	}

	private void paint(GLContext glContext, Block block, int state){
		if(block==null) return;
		if(block.isGone()) return;
		glContext.pushMartix();
		int dx = BLOCK_WIDTH*block.getX()+block.getOffsetX();
		int dy = BLOCK_HEIGHT*block.getY()+block.getOffsetY();
		glContext.translate(dx, dy);
		if(state==STATE_PRESSED){
			glContext.scaleBy(PRESSED_SCALE, PRESSED_SCALE, BLOCK_WIDTH/2, BLOCK_HEIGHT/2);
		}
		if(state==STATE_SELECTED){
			glContext.scaleBy(SELECTED_SCALE, SELECTED_SCALE, BLOCK_WIDTH/2, BLOCK_HEIGHT/2);
		}
		glImage.setImage(getImage(block));
		glImage.draw(glContext);
		glContext.popMartix();
	}
	
	private Object getImage(Block block){
		if(blockBlinker.isBlinking(block)){
			if(block.getValue()==0) return R.drawable.block_01;
			if(block.getValue()==1) return R.drawable.block_11;
			if(block.getValue()==2) return R.drawable.block_21;
			if(block.getValue()==3) return R.drawable.block_31;
			if(block.getValue()==4) return R.drawable.block_41;
		}else{
			if(block.getValue()==0) return R.drawable.block_00;
			if(block.getValue()==1) return R.drawable.block_10;
			if(block.getValue()==2) return R.drawable.block_20;
			if(block.getValue()==3) return R.drawable.block_30;
			if(block.getValue()==4) return R.drawable.block_40;
		}
		return null;
	}
}
