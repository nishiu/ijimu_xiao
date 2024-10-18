package com.ijimu.android.xiao.domain;

import com.ijimu.android.game.domain.BaseEntity;

public class Prop extends BaseEntity {

	private static final long serialVersionUID = 1L;

	public static final int TYPE_NONE = 0;
	public static final int TYPE_BOMB = 1;
	public static final int TYPE_PAINT = 2;
	public static final int TYPE_REFRESH = 3;
	
	private int type;
	private int count;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
