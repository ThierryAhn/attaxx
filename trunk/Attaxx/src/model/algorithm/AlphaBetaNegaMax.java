package model.algorithm;

import java.util.List;

import model.AttaxxModel;
import model.Move;
import model.MoveEnumerator;

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
		// Valeur Alpha
		int alpha = MINUS_INFINITY;
		// Valeur Beta
		final int beta  = PLUS_INFINITY;
		// Le meilleur Mouvement
		Move bestMove = null;

		MoveEnumerator me = new MoveEnumerator(); 
		// on récupère la liste des mouvement possible pour le joueur
		List<Move> listM = me.getPossibleMoves(model);
		// tant qu'il y a de mouvements possibles
		for(Move m : listM){
			int newVal;
			Node n = new Node(model, m);
			// on calcule la valeur Alpha-Beta sur les noeuds fils
			newVal = -AlphaBeta_NegaMax(1, n, alpha, beta);
			if(newVal > alpha) {
				alpha = newVal;
				bestMove = m;
			}
			// si alpha est plus grand que beta on élague les prochains noeuds
			if (alpha >= beta)
				break;
		}
		// retourne le meilleur mouvement
		return bestMove;
	}

	private int AlphaBeta_NegaMax(int depth, Node node, int alpha, int beta){
		node.setAlpha(alpha);
		node.setBeta(beta);

		/* si on a atteint la profondeur maximale on retourne l'heuristique 
		   du modèle, ce qui représente une feuille*/
		if(depth >= maxDepth){
			return node.getModel().heuristicNega();
		}

		MoveEnumerator me = new MoveEnumerator(); 
		List<Move> listM = me.getPossibleMoves(node.getModel());
		int val;
		for(Move m : listM){
			Node n = new Node(node.getModel(), m);
			val = -AlphaBeta_NegaMax(depth+1, n, 
					-node.getBeta(),-node.getAlpha());
			// On prend le maximum pour son alpha
			node.setAlpha(Math.max(node.getAlpha(), val));
			/* si Alpha est plus grand ou égal à Beta en élague 
			   les noeuds restants*/
			if (node.getAlpha() >= node.getBeta())
				break;
		}
		return node.getAlpha();
	}
	
	@Override
	public int getMaxDepth() {
		return maxDepth;
	}
}
