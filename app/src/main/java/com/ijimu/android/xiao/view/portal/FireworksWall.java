package com.ijimu.android.xiao.view.portal;

import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.effect.FireworksEffect;

public class FireworksWall extends DisplayObject {
	
	private FireworksEffect effect;
	
	public FireworksWall() {
		setX(0);
		setY(0);
		setWidth(Constants.SCREEN_WIDTH);
		setHeight(Constants.SCREEN_HEIGHT);
		effect = new FireworksEffect();
		addEffect(effect);
	}
	
//	@Override
//	protected boolean onTouch(TouchEvent event) {
//		boolean result = super.onTouch(event);
//		if(result) return true;
//		if(event.getAction()==TouchEvent.ACTION_MOVE
//			||event.getAction()==TouchEvent.ACTION_DOWN){
//			effect.fire((int)event.getX(), (int)event.getY());
//		}
//		return true;
//	}
}
