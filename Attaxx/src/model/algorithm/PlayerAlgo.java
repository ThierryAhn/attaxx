package model.algorithm;

import model.AttaxxModel;
import model.Move;
import model.Player;

/**
 * 
 * @author Rachid ABALINE & Thierry Folabi AHOUNOU
 *
 */
public interface PlayerAlgo {

	/**
	 * La valeur de plus l'infini
	 */
	public final static int PLUS_INFINITY=Integer.MAX_VALUE; 
	
	/**
	 * La valeur de moins l'infini
	 */
	public final static int MINUS_INFINITY=Integer.MIN_VALUE; 
	
	/**
	 * Le joueur MAX
	 */
	public final static String MAX = Player.RED;
	
	/**
	 * Le joueur MIN
	 */
	public final static String MIN = Player.BLUE;
	
	
	/**
	 * Calcule le meilleur mouvement en appliquant l'algorithme
	 * 
	 * @param model le model courrant
	 * @return le meilleur mouvement
	 */
	public Move getNextMove(AttaxxModel model);
}
