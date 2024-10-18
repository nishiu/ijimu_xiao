package com.ijimu.android.xiao.view.prop;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.game.touch.ClickListener;
import com.ijimu.android.game.touch.TouchEvent;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.effect.DialogEffects;
import com.ijimu.android.xiao.logic.BlockManager;
import com.ijimu.android.xiao.view.BaseButton;

public class PropPaintDialog extends DisplayContainer implements GameEventListener{

	private GameWorld gameWorld;
	private BlockManager blockManager;
	
	private DisplayContainer blockPaintContainer;
	
	public PropPaintDialog() {
		setWidth(Constants.SCREEN_WIDTH);
		setHeight(Constants.SCREEN_HEIGHT);
		setClickable(true);
		setVisible(false);
		blockManager = BeanFactory.getBean(BlockManager.class);
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, EventType.DIALOG_PAINT);
		initContent();
	}

	@Override
	public void onGameEvent(GameEvent event) {
		relayout();
		DialogEffects.show(this);
	}
	
	private void initContent() {
		blockPaintContainer = new DisplayContainer();
		blockPaintContainer.setWidth(350);
		blockPaintContainer.setHeight(80);
		blockPaintContainer.setX((Constants.SCREEN_WIDTH - blockPaintContainer.getWidth())/2);
		blockPaintContainer.setY((Constants.SCREEN_HEIGHT - blockPaintContainer.getHeight())/2);
		blockPaintContainer.setBackgroundImage(R.drawable.bg_paint_dialog);
		initBlockPaints();
		addChild(blockPaintContainer);
	}
	
	private void relayout(){
		int maxColor = blockManager.getMaxColors();
		int horizontalSpace = (blockPaintContainer.getWidth() - maxColor*Constants.BLOCK_WIDTH)/maxColor;
		int startX = (blockPaintContainer.getWidth() - maxColor*Constants.BLOCK_WIDTH - (maxColor-1)*horizontalSpace)/2;
		for(int i = 0; i < 5; i++){
			DisplayObject button = blockPaintContainer.getChildren().get(i);
			if(i >= maxColor){
				button.setVisible(false);
			}else{
				button.setVisible(true);
			}
			int x = i*button.getWidth()+i*horizontalSpace;
			button.setX(x+startX);
			button.setY((blockPaintContainer.getHeight() - button.getHeight())/2);
		}
	}
	
	private void initBlockPaints(){
		for(int i = 0; i < 5; i++){
			DisplayObject button = createBlockPainter(i);
			blockPaintContainer.addChild(button);
		}
		relayout();
	}
	
	private DisplayObject createBlockPainter(final int value){
		DisplayObject button = new BaseButton();
		button.setWidth(Constants.BLOCK_WIDTH);
		button.setHeight(Constants.BLOCK_HEIGHT);
		button.setBackgroundImage(getImage(value));
		button.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				GameEvent ev = new GameEvent(EventType.PROP_APPLY,gameWorld.getUsingProp());
				ev.setAttr("value", value);
				gameWorld.fireEvent(ev);
				DialogEffects.dismiss(PropPaintDialog.this);
			}
		});
		return button;
	}
	
	private Object getImage(int value){
		if(value==0) return R.drawable.block_00;
		if(value==1) return R.drawable.block_10;
		if(value==2) return R.drawable.block_20;
		if(value==3) return R.drawable.block_30;
		if(value==4) return R.drawable.block_40;
		return null;
	}
}
