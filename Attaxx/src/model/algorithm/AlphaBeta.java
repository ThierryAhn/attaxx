package model.algorithm;

import java.util.List;

import model.AttaxxModel;
import model.Move;
import model.MoveEnumerator;


/**
 * Classe mod�lisant le joueur de l'IA en basant sur l'algorithme Alpha-Beta
 * 
 * @author Rachid ABALINE & Thierry Folabi AHOUNOU
 *
 */
public class AlphaBeta implements PlayerAlgo{
	
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
	public AlphaBeta(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	// FONCTIONS
	
	@Override
	public Move getNextMove(AttaxxModel model){
		// Valeur Alpha
		int alpha = MINUS_INFINITY;
		// Valeur Beta
		final int beta  = PLUS_INFINITY;
		// Le meilleur Mouvement
		Move bestMove = null;

		MoveEnumerator me = new MoveEnumerator(); 
		// on r�cup�re la liste des mouvement possible pour le joueur
		List<Move> listM = me.getPossibleMoves(model);
		// tant qu'il y a de mouvements possibles
		for(Move m : listM){
			int newVal;
			Node n = new Node(model, m);
			// on calcule la valeur Alpha-Beta sur les noeuds fils
			newVal = alphaBeta(1, n, alpha, beta);
			if(newVal > alpha) {
				alpha = newVal;
				bestMove = m;
			}
			// si alpha est plus grand que beta on �lague les prochains noeuds
			if (alpha >= beta)
				break;
		}
		// retourne le meilleur mouvement
		return bestMove;
	}
	
	/**
	 * Fonctions Alpha-Beta
	 * 
	 * @param depth  la profondeur
	 * @param node  le noeud
	 * @param alpha  la valeur alpha
	 * @param beta  la valeur beta
	 * 
	 * @return la valeur Alpha-Beta de l'algorithme
	 */
	private int alphaBeta(int depth, Node node, int alpha, int beta) {
		node.setAlpha(alpha);
		node.setBeta(beta);
		
		/* si on a atteint la profondeur maximale on retourne l'heuristique 
		   du mod�le, ce qui repr�sente une feuille*/
		if(depth >= maxDepth){
			return node.getModel().heuristic();
		}

		MoveEnumerator me = new MoveEnumerator(); 
		List<Move> listM = me.getPossibleMoves(node.getModel());
		int val;
		if(node.getModel().getCurrentPlayer().equals(MAX)){ // Max node
			for(Move m : listM){
				Node n = new Node(node.getModel(), m);
				val = alphaBeta(depth+1, n, node.getAlpha(), node.getBeta());
				// On prend le maximum pour son alpha
				node.setAlpha(Math.max(node.getAlpha(), val));
				if (node.getAlpha() >= node.getBeta())
					break;
			}
			return node.getAlpha();
		}else { // Min node
			for(Move m : listM){
				Node n = new Node(node.getModel(), m);
				val = alphaBeta(depth+1, n, node.getAlpha(), node.getAlpha());
				// On prend le maximum pour son beta
				node.setBeta(Math.min(node.getBeta(), val));
				if (node.getBeta() <= node.getAlpha())
					break;
			}
			return node.getBeta();
		}
	}
	
	@Override
	public int getMaxDepth() {
		return maxDepth;
	}
}
