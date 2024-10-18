package com.ijimu.android.xiao;

public interface EventType {
	public static final int GAME_START = 100;
	public static final int GAME_PAUSE = 101;
	public static final int GAME_RESUME = 102;
	public static final int GAME_OVER = 103;
	public static final int GAME_RESTORE = 104;
	
	public static final int BLOCK_CLICK = 200;
	public static final int BLOCK_MERGE = 201;
	public static final int BLOCK_WIPE = 202;
	public static final int BLOCK_FIRE = 203;
	public static final int BLOCK_CLEAN = 204;
	
	public static final int PROP_USE = 300;
	public static final int PROP_APPLY = 301;
	public static final int GIFT_BUY = 302;
	
	public static final int DIALOG_HELP = 400;
	public static final int DIALOG_GAME_PAUSE = 401;
	public static final int DIALOG_PAINT = 402;
	public static final int DIALOG_MARKET = 403;
	
	public static final int CARD_DRAW = 600;
	public static final int CARD_BUY = 601;
	
	public static final int PAY_START = 700;
	public static final int PAY_SUCCESS = 701;
	public static final int PAY_ERROR = 702;
	
	public static final int ROUND_START = 800;
	public static final int ROUND_FINISH = 801;
	public static final int ROUND_WIN = 802;
	
	public static final int SCENE_CHANGE = 900;
	public static final int LEVEL_UP = 901;
	public static final int SCORE_UP = 902;
	public static final int SOUND_CHANGE = 903;
	public static final int BUTTON_CLICK = 904;

}
