package com.ijimu.android.xiao;

import static com.anythink.nativead.api.ATNativeImageView.TAG;
import static com.ijimu.android.game.util.SystemUtils.dip2px;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.AndroidLogger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.anythink.banner.api.ATBannerExListener;
import com.anythink.banner.api.ATBannerView;
import com.anythink.core.api.ATAdConst;
import com.anythink.core.api.ATAdInfo;
import com.anythink.core.api.ATDebuggerConfig;
import com.anythink.core.api.ATNetworkConfirmInfo;
import com.anythink.core.api.AdError;
import com.ijimu.android.ad.AdActivity;
import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.ContextHolder;
import com.ijimu.android.game.UIThread;
import com.ijimu.android.game.engine.GameScheduler;
import com.ijimu.android.game.plugin.ActivityResultPlugin;
import com.ijimu.android.game.plugin.PluginManager;
import com.ijimu.android.game.plugin.TrackPlugin;
import com.ijimu.android.xiao.logic.LogicModule;
import com.ijimu.android.xiao.music.MusicModule;
import com.ijimu.android.xiao.view.DisplayRoot;
import com.ijimu.android.xiao.view.action.AppExitAction;

public class MainActivity extends AdActivity {

	private static MainActivity instance;
	
	public static MainActivity getInstance(){
		return instance;
	}

	private GameWorld gameWorld;
	private Collection<TrackPlugin> trackers;
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		AndroidLogger.DEFAULT_TAG = "xiao";
		logger.info("onCreate");
		instance = this;
		ContextHolder.set(this);
		UIThread.init();
		initBeans();
		initContent();
		PluginManager.init();
		LogicModule.init();
		MusicModule.init();
		for(TrackPlugin tracker : trackers) tracker.onCreate(this);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		ActivityResultPlugin plugin = PluginManager.getPlugin(ActivityResultPlugin.class);
		if(plugin!=null) plugin.onActivityResult(requestCode, resultCode, data);
	}

	private void initBeans() {
		gameWorld = BeanFactory.getBean(GameWorld.class);
		BeanFactory.getBean(GameScheduler.class).setGameWorld(gameWorld);
		trackers = PluginManager.getPlugins(TrackPlugin.class);
	}

	private void initContent() {
		View surface = DisplayRoot.getInstance().getSurface();
		ViewParent parent = surface.getParent();
		if(parent!=null) ((ViewGroup)parent).removeView(surface);
		RelativeLayout rootView = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.activity_main,null);
		ViewGroup gamePanel = rootView.findViewById(R.id.game_root);
		gamePanel.addView(surface);
		setContentView(rootView);
//		initBanner(rootView.findViewById(R.id.banner));
		showInsert();
	}
	
	@Override
	protected void onPause() {
		super.onPause();
		logger.info("onPause");
		gameWorld.pause();
		for(TrackPlugin tracker : trackers) tracker.onPause(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		logger.info("onResume");
		gameWorld.resume();
		for(TrackPlugin tracker : trackers) tracker.onResume(this);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		logger.info("onDestroy");
	}

	@Override
	public void onBackPressed() {
		new AppExitAction();
	}

}
