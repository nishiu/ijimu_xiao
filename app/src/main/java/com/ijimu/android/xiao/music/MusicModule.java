package com.ijimu.android.xiao.music;

import com.ijimu.android.game.BeanFactory;

public class MusicModule {

	public static void init(){
		BeanFactory.getBean(SoundManager.class);
		BeanFactory.getBean(BgMusicPlayer.class);
		BeanFactory.getBean(EffectMusicPlayer.class);
	}
}
