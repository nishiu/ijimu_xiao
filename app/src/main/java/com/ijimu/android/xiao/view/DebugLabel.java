package com.ijimu.android.xiao.view;

import android.os.Debug;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.Label;
import com.ijimu.android.game.opengl.GLContext;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.GameWorld;

public class DebugLabel extends Label {

	private static final int UPDATE_INTERVAL = 1000;
	
	private long lastUpdateTime = 0;
	private long lastUpdateTicks = 0;

	private GameWorld gameWorld;
	
	public DebugLabel(){
		lastUpdateTime = 0;
		lastUpdateTicks = 0;
		setX(10);
		setY(0);
		setWidth(200);
		setHeight(30);
		gameWorld = BeanFactory.getBean(GameWorld.class);
		setVisible("dev".equals(ClientConfig.CHANNEL));
	}
	
	@Override
	public void update(GLContext glContext) {
		checkUpdate();
		super.update(glContext);
	}

	private void checkUpdate() {
		long time = gameWorld.getTimer().getTime();
		if(time-lastUpdateTime<UPDATE_INTERVAL) return;
		long ticks = gameWorld.getTimer().getTicks();
		StringBuffer buffer = new StringBuffer();
		int fps = (int)(1000f/(time-lastUpdateTime)*(ticks-lastUpdateTicks));
		buffer.append("FPS: ").append(fps).append("  ");
		buffer.append("MEM: ").append(getMemoryInfo().getTotalPss()/1024);
		setText(buffer.toString());
		lastUpdateTicks = ticks;
		lastUpdateTime = time;
	}
	
	private Debug.MemoryInfo getMemoryInfo(){
		Debug.MemoryInfo memoryInfo = new Debug.MemoryInfo();
	    Debug.getMemoryInfo(memoryInfo);
		return memoryInfo;
	}
}
