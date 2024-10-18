package com.ijimu.android.xiao.effect;

import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.effect.Particle;
import com.ijimu.android.game.effect.ParticleEffect;
import com.ijimu.android.xiao.R;

public class FireworksEffect extends ParticleEffect {
	
	private static final int MAX_PARTICLES = 360;
	
	private long nextFireTime;
	
	public FireworksEffect(){
		updateNextFireTime();
	}

	@Override
	public void update(DisplayObject display) {
		super.update(display);
		if(System.currentTimeMillis() >= nextFireTime){
			randomFire(display.getWidth(), display.getHeight());
		}
	}

	@Override
	protected Particle createParticle(Object param) {
		Particle particle = super.createParticle(param);
		particle.width = 8;
		particle.height = 8;
		particle.color = null;
		particle.image = param;
		return particle;
	}

	public void fire(int x, int y){
		if(getParticles().size()>MAX_PARTICLES) return;
		setCenterX(x);
		setCenterY(y);
		addParticle(30, getDrawableForRandom());
	}
	
	private void updateNextFireTime(){
		nextFireTime = (long) (System.currentTimeMillis() + Math.random()*1000L);
	}
	
	private void randomFire(int w, int h){
		fire((int)(Math.random()*w), (int)(Math.random()*h));
		updateNextFireTime();
	}
	
	private Object getDrawableForRandom(){
		int value = (int) (Math.random() * 4);
		if(value == 0) return R.drawable.fireworks_0;
		else if(value == 1) return R.drawable.fireworks_1;
		else if(value == 2) return R.drawable.fireworks_2;
		else if(value == 3) return R.drawable.fireworks_3;
		else if(value == 4) return R.drawable.fireworks_4;
		return null;
	}
}
