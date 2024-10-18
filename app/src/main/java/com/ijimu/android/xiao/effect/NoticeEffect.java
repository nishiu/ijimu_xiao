package com.ijimu.android.xiao.effect;

import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.effect.EffectSupport;
import com.ijimu.android.game.opengl.GLContext;
import com.ijimu.android.game.opengl.GLImage;
import com.ijimu.android.game.opengl.Texture;

public class NoticeEffect extends EffectSupport {
	
	private GLImage glImage;
	private float scale;
	private float alpha;
	
	public NoticeEffect(int resId, long duration){
		this.glImage = new GLImage();
		glImage.setImage(resId);
		setDuration(duration);
		setRemoveOnFinish(true);
		scale = 1f;
		alpha = 1f;
	}

	@Override
	protected void onUpdate(DisplayObject displayObject) {
		super.onUpdate(displayObject);
		if(getPhase() == 1) fadeInNotice(displayObject);
		if(getPhase() == 2) residenNotice(displayObject);
		if(getPhase() == 3) fadeOutNotice(displayObject);
	}
	
	private void fadeInNotice(DisplayObject displayObject){
		scale = 0.6f+0.4f*(getAdvance()/0.06f);
		alpha = 1;
		drawImage(displayObject);
	}
	
	private void residenNotice(DisplayObject displayObject){		
		scale = 1;
		alpha = 1;
		drawImage(displayObject);
	}

	private void fadeOutNotice(DisplayObject displayObject){
		scale = 1f-0.4f*(getAdvance()-0.7f)/0.3f;
		alpha = 1-(getAdvance()-0.7f)/0.3f;
		drawImage(displayObject);
	}
	
	private int getPhase(){
		float advance = getAdvance();
		if(advance<= 0.06) return 1;
		if(advance <= 0.7 ) return 2;
		return 3;
	}
	
	private void drawImage(DisplayObject displayObject) {
		GLContext glContext = displayObject.getGLContext();
		glContext.pushMartix();
		Texture texture = glImage.getTexture(glContext);
		float dx = (displayObject.getWidth()-texture.getWidth())/2;
		float dy = (displayObject.getHeight()-texture.getHeight())/2;
		glContext.translate(dx, dy);
		glContext.scaleBy(scale, scale, texture.getWidth()/2, texture.getHeight()/2);
		glImage.setAlpha(alpha);
		glImage.setWidth(texture.getWidth());
		glImage.setHeight(texture.getHeight());
		glImage.draw(glContext);
		glContext.popMartix();
	}
	
}
