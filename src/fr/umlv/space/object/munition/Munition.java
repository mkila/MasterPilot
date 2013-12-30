package fr.umlv.space.object.munition;

import fr.umlv.space.service.Service;

public class Munition {
	
	/**
	 * The class which allow you to get
	 * some bomb.
	 * Bomb is your savior !!!!!
	 **/
	private int munitionBomb;
	private int munitionMega;
	
	
	/**
	 * The constructor initialize the total bomb of the hero 
	 * at the beginning of the level
	 * @param int munitionBomb, int munitionMega
	 **/
	public Munition(int munitionBomb, int munitionMega) {
		this.munitionBomb = munitionBomb;
		this.munitionMega = munitionMega;
	}
	
	/**
	 * Get the total ammo of a bomb
	 * @param Service.TYPEBONUS bonus
	 * @return the total ammo of a bomb
	 **/
	public int getMunition(Service.TYPEBONUS bonus) {
		if(bonus == Service.TYPEBONUS.BOMB)
			return munitionBomb;
		if(bonus == Service.TYPEBONUS.MEGA)
			return munitionMega;
		return 0;
	}
	
	/**
	 * Decrease the total ammo of a bomb when use
	 * @param Service.TYPEBONUS bonus
	 **/
	public void setMunition(Service.TYPEBONUS bonus) {
		if(bonus == Service.TYPEBONUS.BOMB)
			munitionBomb--;
		if(bonus == Service.TYPEBONUS.MEGA)
			munitionMega--;
	}

	/**
	 * Set the total ammo of Bomb with the bonus
	 * @param int munitionBomb
	 **/
	public void setMunitionBomb(int munitionBomb) {
		this.munitionBomb = munitionBomb;
	}

	/**
	 * Set the total ammo of Mega with the bonus
	 * @param int munitionMega
	 **/
	public void setMunitionMega(int munitionMega) {
		this.munitionMega = munitionMega;
	}

	/**
	 * Get the total ammo of Bomb
	 * @return the total ammo of Bomb
	 **/
	public int getMunitionBomb() {
		return munitionBomb;
	}

	/**
	 * Get the total ammo of Mega
	 * @return the total ammo of Mega
	 **/
	public int getMunitionMega() {
		return munitionMega;
	}
}
