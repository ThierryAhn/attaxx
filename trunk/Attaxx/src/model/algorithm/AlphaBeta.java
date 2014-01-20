package model.algorithm;

import java.util.Iterator;
import java.util.TreeSet;

import model.AttaxxModel;
import model.Move;
import model.MoveEnumerator;


/**
 * Classe modélisant le joueur de l'IA en basant sur l'algorithme Alpha-Beta
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
		// on récupère la liste des mouvement possible pour le joueur
		TreeSet<Move> listM = me.getPossibleMoves(model);
		Iterator<Move> i=listM.iterator();
		// tant qu'il y a de mouvements possibles
		while(i.hasNext()){
			Move m =  i.next();
			int newVal;
			Node n = new Node(model, m);
			// on calcule la valeur Alpha-Beta sur les noeuds fils
			newVal = alphaBeta(1, n, alpha, beta);
			if(newVal < beta) {
				alpha = newVal;
				bestMove = m;
			}
			// si alpha est plus grand que beta on élague les prochains noeuds
			if (alpha >= beta)
				break;
		}
		// retourne le meilleur mouvement
		System.out.println(bestMove);
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
		   du modèle, ce qui représente une feuille*/
		if(depth >= maxDepth){
			return node.getModel().heuristic();
		}

		MoveEnumerator me = new MoveEnumerator(); 
		TreeSet<Move> listM = me.getPossibleMoves(node.getModel());
		Iterator<Move> i=listM.iterator();
		int val;
		if(node.getModel().getCurrentPlayer().equals(MAX)){ // Max node
			while(i.hasNext()){
				Node n = new Node(node.getModel(), i.next());
				val = alphaBeta(depth+1, n, node.getAlpha(), node.getBeta());
				// On prend le maximum pour son alpha
				node.setAlpha(Math.max(node.getAlpha(), val));
				if (node.getAlpha() >= node.getBeta())
					break;
			}
			return node.getAlpha();
		}else { // Min node
			while(i.hasNext()){
				Node n = new Node(node.getModel(), i.next());
				val = alphaBeta(depth+1, n, node.getAlpha(), node.getAlpha());
				// On prend le maximum pour son beta
				node.setBeta(Math.min(node.getBeta(), val));
				if (node.getBeta() <= node.getAlpha())
					break;
			}
			return node.getBeta();
		}
	}
}
