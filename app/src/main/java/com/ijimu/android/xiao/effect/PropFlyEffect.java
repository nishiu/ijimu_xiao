package com.ijimu.android.xiao.effect;

import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.effect.EffectSupport;
import com.ijimu.android.game.math.Point;
import com.ijimu.android.game.opengl.GLContext;
import com.ijimu.android.game.opengl.GLImage;
import com.ijimu.android.xiao.domain.Prop;

public class PropFlyEffect extends EffectSupport{
	
	private GLImage glImage;
	
	private Prop prop;
	private Point start;
	private Point end;
	
	public PropFlyEffect() {
		glImage = new GLImage();
		glImage.setAutoSize(true);
		setRemoveOnFinish(true);
	}
	
	public void setStartPoint(Point point){
		this.start = point;
	}
	
	public void setEndPoint(Point point){
		this.end = point;
	}
	
	public void setResId(int resId){
		glImage.setImage(resId);
	}
	
	@Override
	protected void onUpdate(DisplayObject displayObject) {
		super.onUpdate(displayObject);
		draw(displayObject);
	}
	
	private void draw(DisplayObject displayObject) {
		GLContext glContext = displayObject.getGLContext();
		glContext.pushMartix();
		Point current = getCurrentPoint();
		glContext.translate(current.getX(),  current.getY());
		glImage.draw(glContext);
		glContext.popMartix();
	}
	
	private Point getCurrentPoint(){
		double h = start.heading(end.getX(), end.getY());
		double d = start.distance(end.getX(), end.getY());
		return start.next(h, d*getAdvance());
	}

	public Prop getProp() {
		return prop;
	}

	public void setProp(Prop prop) {
		this.prop = prop;
	}
	
}
