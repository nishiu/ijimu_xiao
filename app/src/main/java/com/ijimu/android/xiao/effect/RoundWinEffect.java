package com.ijimu.android.xiao.effect;

import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.effect.EffectSupport;
import com.ijimu.android.game.opengl.GLContext;
import com.ijimu.android.game.opengl.GLImage;
import com.ijimu.android.game.opengl.Texture;

public class RoundWinEffect extends EffectSupport {
	
	private static final int DY = 270;
	
	private GLImage glImage;
	private float scale;
	
	public RoundWinEffect(int resId, long duration){
		this.glImage = new GLImage();
		glImage.setImage(resId);
		glImage.setAutoSize(true);
		setDuration(duration);
		setRemoveOnFinish(false);
		scale = 1f;
	}
	
	@Override
	protected void onUpdate(DisplayObject displayObject) {
		super.onUpdate(displayObject);
		fadeInNotice(displayObject);
	}
	
	private void fadeInNotice(DisplayObject displayObject){
		scale = 1f-0.4f*getAdvance();
		drawImage(displayObject, 0, DY);
	}
	
	private void drawImage(DisplayObject displayObject, float dx, float dy) {
		GLContext glContext = displayObject.getGLContext();
		glContext.pushMartix();
		Texture texture = glImage.getTexture(glContext);
		dx = dx+(displayObject.getWidth()-texture.getWidth())/2;
		dy = dy+(displayObject.getHeight()-texture.getHeight())/2;
		glContext.translate(dx, dy);
		glContext.scaleBy(scale, scale,texture.getWidth()/2,texture.getHeight()/2);
		glImage.draw(glContext);
		glContext.popMartix();
	}
}
