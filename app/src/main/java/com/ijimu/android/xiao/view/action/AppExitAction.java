package com.ijimu.android.xiao.view.action;

import android.content.DialogInterface;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.UIThread;
import com.ijimu.android.game.dialog.SimpleConfirm;
import com.ijimu.android.game.plugin.DestroyPlugin;
import com.ijimu.android.game.plugin.ExitCallback;
import com.ijimu.android.game.plugin.ExitPlugin;
import com.ijimu.android.game.plugin.PluginManager;
import com.ijimu.android.game.plugin.TrackPlugin;
import com.ijimu.android.game.res.ApkResources;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.MainActivity;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.logic.GameRecorder;

public class AppExitAction extends ActionSupport {

	public AppExitAction(){
		ExitPlugin exitPlugin = PluginManager.getPlugin(ExitPlugin.class);
		if(exitPlugin==null){
			showConfirm();
		}else{
			pluginExit(exitPlugin);
		}
	}

	private void pluginExit(ExitPlugin exitPlugin) {
		exitPlugin.exit(new ExitCallback() {
			
			@Override
			public void onExit() {
				load();
			}
			
			@Override
			public void onCancel() {
			}
		});
	}
	
	private void showConfirm(){
		SimpleConfirm.show(	ApkResources.getString(R.string.confirm_tips_exit), new DialogInterface.OnClickListener() {
//		SimpleConfirm.show(	"您确定要退出游戏吗?", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				load();
			}
		}, null ,ApkResources.getString(R.string.confirm_tips_sure),ApkResources.getString(R.string.confirm_tips_cancel));
	}
	
	private void load(){
//		showProgress("正在退出游戏...");
		showProgress(ApkResources.getString(R.string.progress_tips_exit));
		UIThread.postDelayed(new Runnable() {
			@Override
			public void run() {
				doExit();
			}
		}, 300);
	}
	
	private void doExit() {
		saveGame();
		MainActivity.getInstance().finish();
		for(TrackPlugin tracker: PluginManager.getPlugins(TrackPlugin.class)){
			tracker.onDestroy(MainActivity.getInstance());
		}
		for(DestroyPlugin plugin: PluginManager.getPlugins(DestroyPlugin.class)){
			plugin.onDestroy();
		}
		System.exit(0);
	}
	
	private void saveGame(){
		GameWorld gameWorld = BeanFactory.getBean(GameWorld.class);
		if(gameWorld.getScene()!=GameWorld.SCENE_GAME) return;
		GameRecorder recorder = BeanFactory.getBean(GameRecorder.class);
		recorder.save();
	}
}
