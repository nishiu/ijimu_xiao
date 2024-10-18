package com.ijimu.android.xiao.logic;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.UIThread;
import com.ijimu.android.game.domain.PayInfo;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.plugin.PayCallback;
import com.ijimu.android.game.plugin.PayPlugin;
import com.ijimu.android.game.plugin.PluginManager;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;

public class PayManager {

	private class CallackWrapper implements PayCallback{

		private PayCallback callback;
		
		public CallackWrapper(PayCallback payCallback){			
			this.callback = payCallback;
		}
		
		@Override
		public void onSuccess(PayInfo payInfo) {
			gameWorld.fireEvent(new GameEvent(EventType.PAY_SUCCESS, payInfo));
			callback.onSuccess(payInfo);
		}

		@Override
		public void onCancel(PayInfo payInfo) {
			callback.onCancel(payInfo);
		}

		@Override
		public void onError(PayInfo payInfo, Throwable e) {
			GameEvent event = new GameEvent(EventType.PAY_ERROR, payInfo);
			event.setAttr("error", e);
			gameWorld.fireEvent(event);
			callback.onError(payInfo, e);
		}
	}
	
	private PayPlugin payPlugin;
	private GameWorld gameWorld;
		
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	public PayManager(){
		initPlugin();
		gameWorld = BeanFactory.getBean(GameWorld.class);
	}

	private void initPlugin() {
		payPlugin = PluginManager.getPlugin(PayPlugin.class);
		if(payPlugin!=null) payPlugin.init();
		else logger.warn("can not find pay plugin: "+PayPlugin.class.getSimpleName());
	}
	
	public void pay(final PayInfo payInfo, final PayCallback callback){
		if(payPlugin==null){
			callback.onSuccess(payInfo);
			return;
		}
		UIThread.post(new Runnable() {
			@Override
			public void run() {
				pluginPay(payInfo, callback);
			}
		});
	}
	
	private void pluginPay(PayInfo payInfo, PayCallback callback) {
		try {
			payPlugin.pay(payInfo, new CallackWrapper(callback));
		} catch (Exception e) {
			callback.onError(payInfo, e);
		}
	}
}
