package com.ijimu.android.xiao.logic;

import java.util.LinkedList;
import java.util.List;

import com.ijimu.android.game.BeanFactory;
import com.ijimu.android.game.DeamonThread;
import com.ijimu.android.game.event.GameEvent;
import com.ijimu.android.game.util.RandomUtils;
import com.ijimu.android.xiao.EventType;
import com.ijimu.android.xiao.GameWorld;
import com.ijimu.android.xiao.domain.Card;

public class CardManager {

	private static final int CARD_MAX = 4;
	
	private Card drawed;
	private List<Card> cards;
	
	private GameWorld gameWorld;
	
	public CardManager() {
		gameWorld = BeanFactory.getBean(GameWorld.class);
		initCards();
	}
	
	private void initCards(){
		cards = new LinkedList<Card>();
		for(int i = 0; i < CARD_MAX;i++){
			cards.add(new Card());
		}
	}
	
	public List<Card> getCards(){
		return cards;
	}
	
	public void reset(){
		drawed = null;
		for(Card card :  cards){
			card.getProp().setType(RandomUtils.random(1, 4));
			card.getProp().setCount(1);
			card.setReversion(false);
		}
	}
	
	public void draw(Card card){
		if(card == null)return;
		drawed = card;
		resetCount(card);
		fetchProp(card);
		reversionAll();
	}

	private void fetchProp(Card card) {
		gameWorld.fireEvent(new GameEvent(EventType.CARD_DRAW, card));
	}
	
	private void resetCount(Card drawed) {
		drawed.getProp().setCount(1);
		for(Card card :  cards){
			if(card!=drawed) 	card.getProp().setCount(RandomUtils.random(1, CARD_MAX+1));
		}
	}

	private void reversionAll(){
//		for(Card card : getCards()){
//			if(card != drawed)card.setReversion(true);
//		}
		int delay = 300;
		for(int i = 0; i < cards.size();i++){
			final Card card = cards.get(i);
			if(card != drawed){
				DeamonThread.post(new Runnable() {
					@Override
					public void run() {
						card.setReversion(true);
					}
				}, delay);
				delay+=300;
			}
		}
	}
	
	public void drawAll(){
		for(Card card : cards){
			if(card!=drawed)fetchProp(card);
		}
		gameWorld.fireEvent(new GameEvent(EventType.CARD_BUY));
	}

	public boolean isDrawed(Card card){
		return card!=null&&card==drawed;
	}
	
	public Card getDrawed() {
		return drawed;
	}
}
