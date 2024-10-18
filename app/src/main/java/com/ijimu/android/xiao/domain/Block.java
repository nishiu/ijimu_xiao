package com.ijimu.android.xiao.domain;

import com.ijimu.android.game.domain.BaseEntity;

public class Block extends BaseEntity implements Cloneable{

	private static final long serialVersionUID = 1L;

	public static final int STATE_ON = 0;
	public static final int STATE_GONE = 1;
	
	private int x;
	private int y;
	
	private int offsetX;
	private int offsetY;

	private int value;
	private int state;
	private int score;
	
	public Block() {
		state = STATE_ON;
	}

	public boolean isMoving(){
		return offsetX!=0||offsetY!=0;
	}
	
	public boolean isGone(){
		return state==STATE_GONE;
	}
	
	public boolean isOn(){
		return state==STATE_ON;
	}
	
	@Override
	protected Block clone()  {
		try {
			return (Block)super.clone();
		} catch (CloneNotSupportedException e) {
			throw new RuntimeException(e);
		}
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(int offsetX) {
		this.offsetX = offsetX;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offsetY) {
		this.offsetY = offsetY;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}
}
