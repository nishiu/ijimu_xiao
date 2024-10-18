package com.ijimu.android.xiao.effect;

import com.ijimu.android.game.display.DisplayObject;
import com.ijimu.android.game.effect.EffectSupport;
import com.ijimu.android.game.math.Point;
import com.ijimu.android.game.opengl.GLContext;
import com.ijimu.android.game.opengl.GLText;
import com.ijimu.android.xiao.view.TextFont;

public class ScoreFlyEffect extends EffectSupport{
	
	private static final int DEST_X = 220;//97
	private static final int DEST_Y = 580;//173
	
	private GLText glText;
	
	private Point start;
	
	public ScoreFlyEffect() {
		glText = new GLText();
		glText.setTextSize(30);
		glText.setTypeface(TextFont.getTypeface());
		setRemoveOnFinish(true);
	}

	public void setScore(int score){
		glText.setText(score+"");
	}
	
	public void setStartPoint(Point point){
		this.start = point;
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
		glContext.translate(current.getX(), current.getY());
		glText.draw(glContext);
		glContext.popMartix();
	}
	
	private Point getCurrentPoint(){
		double h = start.heading(DEST_X, DEST_Y);
		double d = start.distance(DEST_X, DEST_Y);
		return start.next(h, d*getAdvance());
	}
	
}
