package com.ijimu.android.xiao.effect;

import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.effect.ScaleEffect;

public class ReversionEffect {

	private long duration;
	private DisplayObject back;
	private DisplayObject front;
	
	public ReversionEffect(DisplayObject back, DisplayObject front, long duration) {
		this.duration = duration;
		this.back = back;
		this.front = front;
		this.front.setVisible(false);
		backReversion();
	}
	
	private void backReversion(){
		back.setVisible(true);
		new ScaleEffect(back ,1f, 1f, 0f,1f,duration/2){
			@Override
			protected void onFinish(DisplayObject displayObject) {
				super.onFinish(displayObject);
				back.setVisible(false);
				frontReversion();
			}
		};
	}
	
	private void frontReversion(){
		front.setVisible(true);
		new ScaleEffect(front,0f, 1f, 1f,1f,duration/2){
			@Override
			protected void onFinish(DisplayObject displayObject) {
				super.onFinish(displayObject);
				onFinished();
			}
		};
	}
	
	public void onFinished(){
	}
}
