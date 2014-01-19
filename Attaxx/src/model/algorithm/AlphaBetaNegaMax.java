package model.algorithm;

import model.AttaxxModel;
import model.Move;

/**
 * Classe modélisant le joueur de l'IA en basant 
 * sur l'algorithme Alpha-Beta en version Négamax
 * 
 * @author Rachid ABALINE & Thierry Folabi AHOUNOU
 *
 */
public class AlphaBetaNegaMax implements PlayerAlgo {

	// ATTRIBUTS

	/**
	 * Profondeur maximale
	 */
	private int maxDepth;

	// CONSTRUCTORS

	/**
	 * Constructeur
	 * 
	 * @param maxDepth Profondeur Maximale
	 */
	public AlphaBetaNegaMax(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	@Override
	public Move getNextMove(AttaxxModel model) {
		// TODO Auto-generated method stub
		return null;
	}

	private int AlphaBetaNegaMax(){
		return maxDepth;
		
	}
}
