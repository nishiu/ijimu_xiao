package com.ijimu.android.xiao.music;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.util.LocalStorage;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;

public class SoundManager {

	private static final String KEY_MUSIC_SETTING = "music";
	
	private Boolean enabled;
	
	private LocalStorage localStorage;
	private GameWorld gameWorld;
	
	public SoundManager(){
		gameWorld = BeanFactory.getBean(GameWorld.class);
		localStorage = BeanFactory.getBean(LocalStorage.class);
	}
	
	public void setMusicEnabled(boolean isEnabled){
		this.enabled = isEnabled;
		localStorage.put(KEY_MUSIC_SETTING, isEnabled);
		gameWorld.fireEvent(new GameEvent(EventType.SOUND_CHANGE));
	}
	
	public boolean isMusicEnabled(){		
		if(enabled==null){
			enabled = localStorage.get(KEY_MUSIC_SETTING, true);
		}
		return enabled;
	}
}
