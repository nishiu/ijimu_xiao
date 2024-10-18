package com.ijimu.android.xiao.logic;

import com.ijimu.android.game.BeanFactory;

public class LogicModule {
	public static void init(){
		BeanFactory.getBean(BlockMerger.class);
		BeanFactory.getBean(BlockWiper.class);
		BeanFactory.getBean(BlockBomber.class);
		BeanFactory.getBean(BlockPainter.class);
		BeanFactory.getBean(BlockRefresh.class);
		BeanFactory.getBean(BlockCleaner.class);
		BeanFactory.getBean(BlockFlyManager.class);
		BeanFactory.getBean(PayManager.class);
		BeanFactory.getBean(GameScorer.class);
	}
}
