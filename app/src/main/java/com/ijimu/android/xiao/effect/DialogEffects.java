package com.ijimu.android.xiao.effect;

import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.effect.ScaleEffect;
import com.ijimu.android.game.effect.ShakeOutEffect;

public class DialogEffects {
	
	public static void show(DisplayObject displayObject){
		displayObject.setVisible(true);
		//new MoveEffect(displayObject, new Point(Constants.SCREEN_WIDTH, 0), 160);
		new ShakeOutEffect(displayObject, 400);
	}
	
	public static void dismiss(DisplayObject displayObject){
		new ScaleEffect(displayObject, 1, 0.1f, 160){
			@Override
			protected void onFinish(DisplayObject displayObject) {
				super.onFinish(displayObject);
				displayObject.setVisible(false);
			}
		};
	}
}
