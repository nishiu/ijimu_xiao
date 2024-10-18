package com.ijimu.android.xiao.view.gift;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.Button;
import com.ijimu.android.game.touch.ClickListener;
import com.ijimu.android.game.touch.TouchEvent;
import com.ijimu.android.xiao.ClientConfig;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.R;
import com.ijimu.android.xiao.domain.Gift;
import com.ijimu.android.xiao.logic.GiftManager;

public class GiftOwedButton extends Button{
	
	private GiftManager giftManager;
	
	public GiftOwedButton() {
		giftManager = BeanFactory.getBean(GiftManager.class);
		
		setWidth(113);
		setHeight(117);
		setX(Constants.SCREEN_WIDTH-123);
		setY(10);
		setBackgroundImage(R.drawable.button_owed_gift);
		addClickListener(new ClickListener() {
			@Override
			public void onClick(TouchEvent event) {
				new GiftShowAction(Gift.TYPE_OWED, 0, null);
			}
		});
	}

	@Override
	public boolean isVisible() {
		return ClientConfig.OWED_GIFT_ENABLED&&!giftManager.isOwedGiftBuy();
	}
}
