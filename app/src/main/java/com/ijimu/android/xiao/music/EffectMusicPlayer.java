package com.ijimu.android.xiao.music;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.res.AssetFileDescriptor;
import android.media.AudioManager;
import android.media.SoundPool;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.game.res.ApkResources;
import com.ijimu.android.game.res.ResourceLoader;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Prop;

public class EffectMusicPlayer implements GameEventListener {

	private static final String SOUND_BLOCK_CLICK = "block_click";
	private static final String SOUND_BLOCK_FIRE = "block_fire";
	private static final String SOUND_BUTTON_CLICK = "button_click";
	private static final String SOUND_COMBO = "combo";
	private static final String SOUND_ROUND_WIN = "round_win";
	private static final String SOUND_PROP_BOMB = "prop_bomb";
	private static final String SOUND_PROP_PAINT = "prop_paint";
	private static final String SOUND_PROP_REFRESH = "prop_refresh";
	
	private GameWorld gameWorld;
	private SoundManager soundManager;
	
	private Map<String,Integer> effectMusicIds;
	private SoundPool effectMusicPool;
	private ResourceLoader resourceLoader;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public EffectMusicPlayer(){
		gameWorld = BeanFactory.getBean(GameWorld.class);
		soundManager = BeanFactory.getBean(SoundManager.class);
		resourceLoader = BeanFactory.getBean(ResourceLoader.class);
		effectMusicIds = new HashMap<String, Integer>();
		effectMusicPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 10);
		gameWorld.addEventListener(this, EventType.BLOCK_CLICK);
		gameWorld.addEventListener(this, EventType.BLOCK_WIPE);
		gameWorld.addEventListener(this, EventType.BLOCK_FIRE);
		gameWorld.addEventListener(this, EventType.BUTTON_CLICK);
		gameWorld.addEventListener(this, EventType.ROUND_WIN);
		gameWorld.addEventListener(this, EventType.PROP_APPLY);
		load();
	}

	@Override
	public void onGameEvent(GameEvent event) {
		if(event.getType() == EventType.BLOCK_CLICK){
			play(getEffectMusicId(SOUND_BLOCK_CLICK),false);
		}
		if(event.getType() == EventType.BLOCK_FIRE){
			play(getEffectMusicId(SOUND_BLOCK_FIRE),false);
		}
		if(event.getType() == EventType.BUTTON_CLICK){
			play(getEffectMusicId(SOUND_BUTTON_CLICK), false);
		}
		if(event.getType() == EventType.BLOCK_WIPE){
			int count = (Integer)event.getAttrInt("count");
			if(count > 8)play(getEffectMusicId(SOUND_COMBO), false);
		}
		if(event.getType() == EventType.ROUND_WIN){
			play(getEffectMusicId(SOUND_ROUND_WIN), false);
		}
		if(event.getType() == EventType.PROP_APPLY){
			int propType = (Integer)event.getData();
			switch(propType){
			case Prop.TYPE_BOMB:
				play(getEffectMusicId(SOUND_PROP_BOMB), false);
				break;
			case Prop.TYPE_PAINT:
				play(getEffectMusicId(SOUND_PROP_PAINT), false);
				break;
			case Prop.TYPE_REFRESH:
				play(getEffectMusicId(SOUND_PROP_REFRESH), false);
				break;
			}
		}
	}
	
	private void load(){
		addEffectMusicId(SOUND_BLOCK_CLICK);
		addEffectMusicId(SOUND_BLOCK_FIRE);
		addEffectMusicId(SOUND_BUTTON_CLICK);
		addEffectMusicId(SOUND_COMBO);
		addEffectMusicId(SOUND_ROUND_WIN);
		addEffectMusicId(SOUND_PROP_BOMB);
		addEffectMusicId(SOUND_PROP_PAINT);
		addEffectMusicId(SOUND_PROP_REFRESH);
	}
	
	private void play(Integer soundId,boolean loop){
		if(!soundManager.isMusicEnabled())return;
		if(soundId == null)return;
		effectMusicPool.play(soundId, 1, 1, 1, loop ? -1 : 0, 1);
	}
	
	private Integer getEffectMusicId(String path){
		return effectMusicIds.get(path);
	}
	
	private void addEffectMusicId(String path){
		if(effectMusicIds.containsKey(path))return;
		effectMusicIds.put(path, loadSound(path));
	}
	
	private Integer loadSound(String path) {
        if(path==null) return null;
        path = "music/"+path+".ogg";
        InputStream inputStream = resourceLoader.load(path);
        if(inputStream==null) return null;
        try {
            if(resourceLoader.exists(path)){
                AssetFileDescriptor afd = ApkResources.getAssetFD(path);
                return effectMusicPool.load(afd, 1);
            }
            return null;
        }catch (Exception e) {
            logger.error("load sound error", e);
            return null;
        }
    }
}
