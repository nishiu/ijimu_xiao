package com.ijimu.android.xiao.view.prop;

import android.graphics.Color;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.Button;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.game.display.Label;
import com.ijimu.android.game.opengl.GLContext;
import com.ijimu.android.game.touch.ClickListener;
import com.ijimu.android.game.touch.TouchEvent;
import com.ijimu.android.xiao.logic.PropManager;
import com.ijimu.android.xiao.view.BaseButton;

public class PropButton extends DisplayContainer {
	
	private int type;
	private Label label;
	private Button button;
	private PropManager propManager;
	
	public PropButton(int type){
		this.type = type;
		setWidth(78);
		setHeight(110);
		initButton();
		initLabel();
		propManager = BeanFactory.getBean(PropManager.class);
	}
	
	@Override
	public void update(GLContext glContext) {
		label.setText(propManager.getCount(type)+"");
		super.update(glContext);
	}
	
	public void setPropImage(int bg){
		button.setBackgroundImage(bg);
	}
	
	private void initButton(){
		button = new BaseButton();
		button.setWidth(78);
		button.setHeight(107);
		button.addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				new PropUseAction(type);
			}
		});
		addChild(button);
	}
	
	private void initLabel(){
		label = new Label();
		label.setX(0);
		label.setTextSize(24);
		label.setTextColor(Color.parseColor("#00B6FF"));
		label.setStrokeColor(Color.WHITE);
		label.setStrokeWidth(4);
		label.setWidth(78);
		label.setHeight(29);
		label.setAlign(Label.ALIGN_CENTER);
		addChild(label);
	}
}
