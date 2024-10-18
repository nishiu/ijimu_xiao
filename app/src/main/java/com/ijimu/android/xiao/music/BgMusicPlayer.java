package com.ijimu.android.xiao.music;

import java.io.InputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.DeamonThread;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.game.res.ApkResources;
import com.ijimu.android.game.res.ResourceLoader;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;

public class BgMusicPlayer implements GameEventListener{
	
	private static final int PLAY_DELAY = 2000;

	private static final String BG_MUSIC = "bg_music";

	private GameWorld gameWorld;
	private SoundManager soundManager;
	
	private MediaPlayer mediaPlayer;
	private ResourceLoader resourceLoader;

	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public BgMusicPlayer(){
		gameWorld = BeanFactory.getBean(GameWorld.class);
		soundManager = BeanFactory.getBean(SoundManager.class);
		resourceLoader = BeanFactory.getBean(ResourceLoader.class);
		gameWorld.addEventListener(BgMusicPlayer.this, EventType.SOUND_CHANGE);
		gameWorld.addEventListener(BgMusicPlayer.this, EventType.GAME_PAUSE);
		gameWorld.addEventListener(BgMusicPlayer.this, EventType.GAME_RESUME);
		mediaPlayer = new MediaPlayer();
		start();
	}

	private void start() {
		DeamonThread.post(new Runnable() {
			@Override
			public void run() {
				loadMusic(BG_MUSIC);
				if(soundManager.isMusicEnabled()) resume();				
			}
		}, PLAY_DELAY);		
	}

	@Override
	public void onGameEvent(GameEvent event) {
		if(soundManager.isMusicEnabled()&&!gameWorld.getTimer().isPaused()){
			resume();
		}else{
			pause();
		}
	}
	
	private void resume(){
		try {
			if(!mediaPlayer.isPlaying())mediaPlayer.start();
		} catch (Exception e) {
			logger.error("resume music error", e);
		}
	}
	
	private void pause(){
		try {
			if(mediaPlayer.isPlaying()) mediaPlayer.pause();
		} catch (Exception e) {
			logger.error("pause music error", e);
		}
	}

//	private void stop(){
//		try {
//			mediaPlayer.stop();
//		} catch (IllegalStateException e) {
//			logger.error("stop music error", e);
//		}
//	}
	
	private void loadMusic(String path){
		path = "music/"+path+".ogg";
		InputStream inputStream = resourceLoader.load(path);
		if(inputStream==null){
			return;
		}
		logger.debug("load sound "+path);
		try {
			mediaPlayer.reset();
			mediaPlayer.setLooping(true);
			if(resourceLoader.exists(path)){
				AssetFileDescriptor afd = ApkResources.getAssetFD(path);
				mediaPlayer.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(), afd.getLength());
			}
			mediaPlayer.prepare();
		}catch (Exception e) {
			//throw new RuntimeException(e);
			logger.error("load sound error", e);
		}
	}
}
