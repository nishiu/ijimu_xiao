package com.ijimu.android.xiao.view.portal;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.opengl.GLContext;
import com.ijimu.android.game.touch.TouchEvent;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.music.SoundManager;
import com.ijimu.android.xiao.view.BaseButton;

public class SoundButton extends BaseButton {

	private SoundManager soundManager;
	
	public SoundButton(){
		setWidth(60);
		setHeight(60);
		setX(100);
		setY(800-777);
		soundManager = BeanFactory.getBean(SoundManager.class);
	}

	@Override
	protected void onDraw(GLContext glContext) {
		if(soundManager.isMusicEnabled()){
			setBackgroundImage(R.drawable.button_sound_on);
		}else{
			setBackgroundImage(R.drawable.button_sound_off);
		}
		super.onDraw(glContext);
	}

	@Override
	protected void onClick(TouchEvent event) {
		soundManager.setMusicEnabled(!soundManager.isMusicEnabled());
		super.onClick(event);
	}
	
	
}
