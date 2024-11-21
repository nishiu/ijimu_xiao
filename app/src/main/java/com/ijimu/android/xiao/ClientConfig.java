package com.ijimu.android.xiao;

import com.ijimu.android.game.util.LocalConfig;

public class ClientConfig {

	public static final int INSERT_AD_LEVEL = LocalConfig.get("insert_ad_level",2);//插屏每2关展示一次,注意和评价系统隔2个大小的关卡间隔
	public static final int APPRAISE_LEVEL = LocalConfig.get("appraise_level",3);//第三关展示评价系统,注意不用和插屏广告间隔太近


	public static final boolean MARKET_ENABLED = LocalConfig.get("market_enabled", true);
	public static final boolean GIFT_AUTO_BUY = LocalConfig.get("gift_auto_buy", false);
	public static final boolean START_GIFT_ENABLED = LocalConfig.get("start_gift_enabled", false);
	public static final boolean ROUND_GIFT_ENABLED = LocalConfig.get("round_gift_enabled", true);
	public static final boolean OWED_GIFT_ENABLED = LocalConfig.get("owed_gift_enabled", false);
	public static final boolean INIT_GIFT_ENABLED = LocalConfig.get("init_gift_enabled", false);
	
	public static final boolean CARD_DRAW_ENABLED = LocalConfig.get("card_draw_enabled", true);
	public static final int CARD_DRAW_ALL_MONEY = Integer.parseInt(LocalConfig.get("card_draw_all_money", "6"));
	
	public static final boolean PAY_UI_SMALL = LocalConfig.get("pay_ui_small", false);
	public static final boolean PAY_LOADING_NOTICE = LocalConfig.get("pay_loading_notice", false);
	public static final boolean PAY_ERROR_NOTICE = LocalConfig.get("pay_error_notice", false);
	public static final boolean PAY_SUCCESS_NOTICE = LocalConfig.get("pay_success_notice", false);
	public static final boolean PAY_CANCEL_DISABLED = LocalConfig.get("pay_cancel_disabled", false);
	public static final boolean PAY_ERROR_AS_SUCCESS = LocalConfig.get("pay_error_as_success", false);
	public static final int PAY_BUTTON_OFFSET_Y = LocalConfig.get("pay_button_offset_y", 90);

	public static final String VERSION = LocalConfig.get("version");
	public static final String CHANNEL = LocalConfig.get("channel");
	public static final String CHANNEL_SUB = LocalConfig.get("channel_sub");
	public static final long SPLASH_TIME = LocalConfig.get("splash_time", -1);
	
	public static final int LOGO_WIDTH = LocalConfig.get("logo_width", 227);
	public static final int LOGO_HEIGHT = LocalConfig.get("logo_height", 185);
	
	public static String getFullChannel(){
		if(CHANNEL==null) return "dev";
		if(CHANNEL_SUB==null) return CHANNEL;
		return CHANNEL+"_"+CHANNEL_SUB;
	}
}
