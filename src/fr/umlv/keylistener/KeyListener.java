package fr.umlv.keylistener;

import java.util.ArrayList;

import fr.umlv.zen3.ApplicationContext;
import fr.umlv.zen3.KeyboardEvent;
import fr.umlv.zen3.KeyboardKey;

public class KeyListener {

	private ArrayList<KeyboardEvent> list;
	public enum State {UNKNOW,UP,DOWN,LEFT,RIGHT};
	private State state;

	public KeyListener(){
		list = new ArrayList<>();
		state = State.UNKNOW;
	}

	public void movement(ApplicationContext context) {
		KeyboardEvent ke = null;
		try {
			ke = context.pollKeyboard();
		} catch(NullPointerException o) {
		}
		if(ke == null){
			return;
		}

		if(ke.getKey() == KeyboardKey.LEFT){
			list.add(ke);
			state = State.LEFT;
			//x--
			return;
		}

		if(ke.getKey() == KeyboardKey.RIGHT){
			list.add(ke);
			state = State.RIGHT;
			//x++
			return;
		}
		
		if(ke.getKey() == KeyboardKey.UP){
			list.add(ke);
			state = State.UP;
			//y++
			return;
		}
		
		if(ke.getKey() == KeyboardKey.DOWN){
			list.add(ke);
			state = State.DOWN;
			//y--
			return;
		}
	}
	
	/**
	 * Set the state of the movement 
	 * 
	 */
	public void setState(State state) {
		this.state = state;
	}
	
	/**
	 * Get the list of KeyboardEvent
	 * @return the list
	 */
	public ArrayList<KeyboardEvent> getList() {
		return list;
	}
	
	/**
	 * Get the state of the movement 
	 * @return the state 
	 */
	public State getState() {
		return state;
	}

}