package model.algorithm;

import java.util.Iterator;
import java.util.TreeSet;

import model.AttaxxModel;
import model.Move;
import model.MoveEnumerator;

/**
 * Classe mod�lisant le joueur de l'IA en basant 
 * sur l'algorithme Alpha-Beta en version N�gamax
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
		// on r�cup�re la liste des mouvement possible pour le joueur
		TreeSet<Move> listM = me.getPossibleMoves(model);
		Iterator<Move> i=listM.iterator();
		// tant qu'il y a de mouvements possibles
		while(i.hasNext()){
			Move m =  i.next();
			int newVal;
			Node n = new Node(model, m);
			// on calcule la valeur Alpha-Beta sur les noeuds fils
			newVal = -1*AlphaBeta_NegaMax(1, n, alpha, beta);
			if(newVal > alpha) {
				alpha = newVal;
				bestMove = m;
			}
			// si alpha est plus grand que beta on �lague les prochains noeuds
			if (alpha >= beta)
				break;
		}
		// retourne le meilleur mouvement
		System.out.println(bestMove);
		return bestMove;
	}

	private int AlphaBeta_NegaMax(int depth, Node node, int alpha, int beta){
		node.setAlpha(alpha);
		node.setBeta(beta);

		/* si on a atteint la profondeur maximale on retourne l'heuristique 
		   du mod�le, ce qui repr�sente une feuille*/
		if(depth >= maxDepth){
			return node.getModel().heuristic();
		}

		MoveEnumerator me = new MoveEnumerator(); 
		TreeSet<Move> listM = me.getPossibleMoves(node.getModel());
		Iterator<Move> i=listM.iterator();
		int val;
		while(i.hasNext()){
			Node n = new Node(node.getModel(), i.next());
			val = -1*AlphaBeta_NegaMax(depth+1, n, 
					node.getAlpha(), node.getBeta());
			// On prend le maximum pour son alpha
			node.setAlpha(Math.max(node.getAlpha(), val));
			/* si Alpha est plus grand ou �gal � Beta en �lague 
			   les noeuds restants*/
			if (node.getAlpha() >= node.getBeta())
				break;
		}
		return node.getAlpha();
	}
}
