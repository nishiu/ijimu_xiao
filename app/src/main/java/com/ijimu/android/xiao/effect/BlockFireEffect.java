package com.ijimu.android.xiao.effect;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.effect.Particle;
import com.ijimu.android.game.effect.ParticleEffect;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.event.GameEventListener;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.domain.Block;
import com.ijimu.android.xiao.view.game.BlockWall;

public class BlockFireEffect extends ParticleEffect implements GameEventListener {
	
	private static final int MAX_PARTICLES = 80;

	private GameWorld gameWorld;
	
	public BlockFireEffect() {
		gameWorld = BeanFactory.getBean(GameWorld.class);
		gameWorld.addEventListener(this, EventType.BLOCK_FIRE);
	}

	@Override
	public void onGameEvent(GameEvent event) {
		if (event.getType() == EventType.BLOCK_FIRE) {
			if(getParticles().size()>MAX_PARTICLES) return;
			fire((Block)event.getData());
		}
	}
	
	@Override
	protected Particle createParticle(Object param) {
		Particle particle = super.createParticle(param);
		particle.width = 30;
		particle.height = 30;
		particle.color = null;
		particle.visibleRadius = 200;
		particle.vx = 15-(int)(30*Math.random());
		particle.vy = 15-(int)(30*Math.random());
		particle.image = getImage((Block)param);
		return particle;
	}

	private void fire(Block block) {
		setCenterX(block.getX() * BlockWall.BLOCK_WIDTH);
		setCenterY(block.getY() * BlockWall.BLOCK_HEIGHT);
		addParticle(10, block);
	}
	
	private Object getImage(Block block) {
		if (block.getValue() == 0)
			return R.drawable.star_0;
		if (block.getValue() == 1)
			return R.drawable.star_1;
		if (block.getValue() == 2)
			return R.drawable.star_2;
		if (block.getValue() == 3)
			return R.drawable.star_3;
		if (block.getValue() == 4)
			return R.drawable.star_4;
		return null;
	}
}