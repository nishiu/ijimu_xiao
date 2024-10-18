package com.ijimu.android.xiao.view.card;

import java.util.List;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.display.DisplayContainer;
import com.ijimu.android.xiao.Constants;
import com.ijimu.android.xiao.domain.Card;
import com.ijimu.android.xiao.logic.CardManager;

public class CardList extends DisplayContainer{

	private CardManager cardManager;
	
	public CardList() {
		setWidth(Constants.SCREEN_WIDTH);
		setHeight(174);
		cardManager = BeanFactory.getBean(CardManager.class);
	}

	public void init(){
		List<Card> cards = cardManager.getCards();
		if(cards == null)return;
		int horizontalSpace = 2;
		int startX = 2;
		for(int i = 0; i < cards.size(); i++){
			Card card = cards.get(i);
			CardButton button = new CardButton(card);
			button.setX(startX+i*button.getWidth()+i*horizontalSpace);
			button.setY((getHeight() - button.getHeight())/2);
			addChild(button);
			card.setX(button.getGlobalX());
			card.setY(button.getGlobalY());
		}
	}
	
}
