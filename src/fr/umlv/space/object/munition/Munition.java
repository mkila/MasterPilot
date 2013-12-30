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
	 * @param munitionBomb, munition of BOMB
	 * @param munitionMega, munition of MEGA
	 */
	public Munition(int munitionBomb, int munitionMega) {
		this.munitionBomb = munitionBomb;
		this.munitionMega = munitionMega;
	}
		
	/**
	 * Get the total ammo of a bomb
	 * @param bonus, the type of Bonus
	 * @return the total ammo of a bomb
	 */
	public int getMunition(Service.TYPEBONUS bonus) {
		if(bonus == Service.TYPEBONUS.BOMB)
			return munitionBomb;
		if(bonus == Service.TYPEBONUS.MEGA)
			return munitionMega;
		return 0;
	}
	
	/**
	 * Decrease the total ammo of a bomb when use
	 * @param bonus, the type of Bonus
	 */
	public void setMunition(Service.TYPEBONUS bonus) {
		if(bonus == Service.TYPEBONUS.BOMB)
			munitionBomb--;
		if(bonus == Service.TYPEBONUS.MEGA)
			munitionMega--;
	}

	/**
	 * Set the total ammo of Bomb with the bonus
	 * @param munitionBomb, munition BOMB
	 **/
	public void setMunitionBomb(int munitionBomb) {
		this.munitionBomb = munitionBomb;
	}

	/**
	 * Set the total ammo of Mega with the bonus
	 * @param munitionMega munition MEGA
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
