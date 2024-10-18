package com.ijimu.android.xiao.view;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import android.content.res.AssetManager;
import android.graphics.Typeface;

import com.ijimu.android.game.ContextHolder;

public class TextFont {
	
	protected static Logger logger = LoggerFactory.getLogger(TextFont.class);
	
	private static Typeface typeface;

	public static Typeface getTypeface() {
		if(typeface==null){
			try {
				AssetManager asset = ContextHolder.get().getResources().getAssets();
				typeface = Typeface.createFromAsset(asset, "font.ttf");
			} catch (Exception e) {
				logger.error("create typeface error", e);
				typeface = Typeface.DEFAULT;
			}
		}
		return typeface;
	}	
}
