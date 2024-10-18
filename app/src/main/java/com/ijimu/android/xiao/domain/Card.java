package com.ijimu.android.xiao.domain;

import com.ijimu.android.game.domain.BaseEntity;

public class Card extends BaseEntity{

	private static final long serialVersionUID = 1L;
	
	private Prop prop;
	private int x;
	private int y;
	private boolean isReversion;
	
	public Card() {
		prop = new Prop();
	}

	public Prop getProp() {
		return prop;
	}

	public void setProp(Prop prop) {
		this.prop = prop;
	}
	
	public void setX(int x){
		this.x = x;
	}
	
	public void setY(int y){
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}
	
	public boolean isReversion() {
		return isReversion;
	}

	public void setReversion(boolean isReversion) {
		this.isReversion = isReversion;
	}

}
