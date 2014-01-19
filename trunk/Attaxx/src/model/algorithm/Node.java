package model.algorithm;

import model.AttaxxModel;
import model.Move;

/**
 * Classe mod�lisant un noeud dans l'arbre 
 * de L'algorithe Alpha-Beta  et Alpha-Beta en version N�gamax
 * 
 * @author Rachid ABALINE & Thierry Folabi AHOUNOU
 *
 */
class Node {

	// ATTRIBUTS

	// Le model associ� au noeud
	private AttaxxModel model;

	// La valeur Alpha
	private int alpha = PlayerAlgo.PLUS_INFINITY;

	// La valeur Beta
	private int beta  = PlayerAlgo.MINUS_INFINITY;

	// CONSTRUCTORS

	/**
	 * Constructeur 
	 * @param model le model du noeud parent
	 * @param move le coup � jouer 
	 */
	public Node(AttaxxModel model, Move move){
		// on r�cup�re le nouveau mod�le apr�s lui avoir appliquer le coup
		this.model = model.simulateMove(move);
	}

	// FONCTIONS

	/**
	 * Retourne le model associ� � ce noeud
	 * @return
	 */
	public AttaxxModel getModel() {
		return model;
	}

	/**
	 * Retourne la valeur Alpha du noeud
	 * @return
	 */
	public int getAlpha() {
		return alpha;
	}

	/**
	 * Retourne la valeur Beta du noeud
	 * @return
	 */
	public int getBeta() {
		return beta;
	}

	// METHODS

	/**
	 * Affecte une nouvelle valeur � Alpha
	 * @param alpha
	 */
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	/**
	 * Affecte une nouvelle valeur � Beta
	 * @param beta
	 */
	public void setBeta(int beta) {
		this.beta = beta;
	}
}
