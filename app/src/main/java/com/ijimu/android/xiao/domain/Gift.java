package com.ijimu.android.xiao.domain;

import com.ijimu.android.game.domain.BaseEntity;

public class Gift extends BaseEntity{

	private static final long serialVersionUID = 1L;

	public static final int TYPE_REBORN = 0;
	public static final int TYPE_PROPS_5 = 2;
	public static final int TYPE_PROPS_2 = 4;
	public static final int TYPE_BOMB_5 = 5;
	public static final int TYPE_REFRESH_5 = 6;
	public static final int TYPE_PAINT_5 = 7;
	public static final int TYPE_OWED = 8;
	
	public static final int TYPE_CARD_DRAW = 100;
	
	private int type;
	private int money;
	
	private int bombCount;
	private int paintCount;
	private int refreshCount;


	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getBombCount() {
		return bombCount;
	}

	public void setBombCount(int bombCount) {
		this.bombCount = bombCount;
	}

	public int getPaintCount() {
		return paintCount;
	}

	public void setPaintCount(int paintCount) {
		this.paintCount = paintCount;
	}

	public int getRefreshCount() {
		return refreshCount;
	}

	public void setRefreshCount(int refreshCount) {
		this.refreshCount = refreshCount;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

}
