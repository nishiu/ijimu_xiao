package com.ijimu.android.xiao.logic;

import java.util.HashMap;
import java.util.Map;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.res.ApkResources;
import com.ijimu.android.game.util.LocalStorage;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.domain.Prop;

public class PropManager implements Constants{
	
	private GameWorld gameWorld;
	private LocalStorage localStorage;
	private Map<Object, Integer> counts;
	
	public PropManager(){
		gameWorld = BeanFactory.getBean(GameWorld.class);
		localStorage = BeanFactory.getBean(LocalStorage.class);
		initCounts();
	}
	private void initCounts() {
		counts = new HashMap<Object, Integer>();
		loadCount(Prop.TYPE_BOMB, 5);
		loadCount(Prop.TYPE_REFRESH, 5);
		loadCount(Prop.TYPE_PAINT, 5);
	}
	
	private void loadCount(int type, int defaultCount){
		counts.put(type, localStorage.get("prop_"+type, defaultCount));
	}
	
	public void use(int type){		
		if(gameWorld.getUsingProp()!=Prop.TYPE_NONE) return;
		if(getCount(type)<=0) return;
		updateCount(type, -1);
		gameWorld.setUsingProp(type);
		gameWorld.fireEvent(new GameEvent(EventType.PROP_USE, type));
	}

	public void addCount(int type, int delta){
		updateCount(type, delta);
	}
	
	public void updateCount(int type, int delta) {
		int count = getCount(type);
		count += delta;
		count = Math.max(0, count);
		counts.put(type, count);
		localStorage.put("prop_"+type, count);
	}
	
	public void cancel(){
		gameWorld.setUsingProp(Prop.TYPE_NONE);
	}
	
	public int getCount(int type){
		Integer count =  counts.get(type);
		if(count==null) return 0;
		return count;
	}
	
	public String getName(int type){
		if(type==Prop.TYPE_BOMB) return ApkResources.getString(R.string.prop_name_bomb);//"炸弹";
		if(type==Prop.TYPE_PAINT) return ApkResources.getString(R.string.prop_name_paint);//"刷子";
		if(type==Prop.TYPE_REFRESH) return ApkResources.getString(R.string.prop_name_refresh);//"刷新";
		return "?";
	}
}
