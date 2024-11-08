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
import com.anythink.core.api.ATNetworkConfirmInfo;
import com.anythink.core.api.AdError;
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

public class MainActivity extends Activity{

	private static MainActivity instance;
	
	public static MainActivity getInstance(){
		return instance;
	}

	private GameWorld gameWorld;
	private Collection<TrackPlugin> trackers;


	private ATBannerView mBannerView;
	
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
		UIThread.postDelayed(new Runnable() {
			@Override
			public void run() {

				initBanner(rootView);
			}
		},3000);
	}

	private void initBanner(View root){
		FrameLayout adContainer = root.findViewById(R.id.banner);
		ATBannerView mBannerView = new ATBannerView(this);
		mBannerView.setPlacementId("n670cfbda6c587");

		mBannerView.setBannerAdListener(new ATBannerExListener() {

			@Override
			public void onDeeplinkCallback(boolean isRefresh, ATAdInfo adInfo, boolean isSuccess) {
				Log.i(TAG, "onDeeplinkCallback:" + adInfo.toString() + "--status:" + isSuccess);
			}

			@Override
			public void onDownloadConfirm(Context context, ATAdInfo adInfo, ATNetworkConfirmInfo networkConfirmInfo) {
				Log.i(TAG, "onDownloadConfirm:" + adInfo.toString() + " networkConfirmInfo:" + networkConfirmInfo);
			}

			@Override
			public void onBannerLoaded() {
				Log.i(TAG, "onBannerLoaded");
			}

			@Override
			public void onBannerFailed(AdError adError) {
				Log.i(TAG, "onBannerFailed: " + adError.getFullErrorInfo());
			}

			@Override
			public void onBannerClicked(ATAdInfo entity) {
				Log.i(TAG, "onBannerClicked:" + entity.toString());
			}

			@Override
			public void onBannerShow(ATAdInfo entity) {
				Log.i(TAG, "onBannerShow:" + entity.toString());
			}

			@Override
			public void onBannerClose(ATAdInfo entity) {
				Log.i(TAG, "onBannerClose:" + entity.toString());
			}

			@Override
			public void onBannerAutoRefreshed(ATAdInfo entity) {
				Log.i(TAG, "onBannerAutoRefreshed:" + entity.toString());
			}

			@Override
			public void onBannerAutoRefreshFail(AdError adError) {
				Log.i(TAG, "onBannerAutoRefreshFail: " + adError.getFullErrorInfo());
			}
		});

//		mBannerView.setAdSourceStatusListener(new ATAdSourceStatusListenerImpl());
//		mBannerView.setAdMultipleLoadedListener(new AdMultipleLoadedListener());
		mBannerView.setVisibility(View.VISIBLE);
		adContainer.addView(mBannerView,new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, adContainer.getLayoutParams().height));

		int padding = dip2px(12);
		Map<String, Object> localMap = new HashMap<>();
		localMap.put(ATAdConst.KEY.AD_WIDTH, getResources().getDisplayMetrics().widthPixels - 2 * padding);
		localMap.put(ATAdConst.KEY.AD_HEIGHT, dip2px(60));
		mBannerView.setLocalExtra(localMap);
		mBannerView.loadAd();
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
