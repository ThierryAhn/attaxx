package model.algorithm;

import model.AttaxxModel;
import model.Move;

/**
 * Classe modélisant un noeud dans l'arbre 
 * de L'algorithe Alpha-Beta  et Alpha-Beta en version Négamax
 * 
 * @author Rachid ABALINE & Thierry Folabi AHOUNOU
 *
 */
class Node {

	// ATTRIBUTS

	// Le model associé au noeud
	private AttaxxModel model;

	// La valeur Alpha
	private int alpha = PlayerAlgo.PLUS_INFINITY;

	// La valeur Beta
	private int beta  = PlayerAlgo.MINUS_INFINITY;

	// CONSTRUCTORS

	/**
	 * Constructeur 
	 * @param model le model du noeud parent
	 * @param move le coup à jouer 
	 */
	public Node(AttaxxModel model, Move move){
		// on récupère le nouveau modèle après lui avoir appliquer le coup
		this.model = model.simulateMove(move);
	}

	// FONCTIONS

	/**
	 * Retourne le model associé à ce noeud
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
	 * Affecte une nouvelle valeur à Alpha
	 * @param alpha
	 */
	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	/**
	 * Affecte une nouvelle valeur à Beta
	 * @param beta
	 */
	public void setBeta(int beta) {
		this.beta = beta;
	}
}
