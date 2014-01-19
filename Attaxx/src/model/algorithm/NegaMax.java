package model.algorithm;

import java.util.Iterator;
import java.util.Set;

import model.AttaxxModel;
import model.Move;
import model.MoveEnumerator;

/**
 * Classe modélisant le joueur de l'IA en basant sur l'algorithme NegaMax
 * 
 * @author Rachid ABALINE & Thierry Folabi AHOUNOU
 *
 */
public class NegaMax implements PlayerAlgo {

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
	public NegaMax(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	// FONCTIONS

	@Override
	public Move getNextMove(AttaxxModel model) {
		// Meilleur Movement
		Move bestMove=null;

		int val = MINUS_INFINITY;

		//List des mouvement possibles
		MoveEnumerator moveEnum = new MoveEnumerator();
		// List des movements possibles
		Set<Move> listM = moveEnum.getPossibleMoves(model);
		Iterator<Move> i=listM.iterator();
		// tant qu'il y a des mouvements possibles
		while(i.hasNext()){
			// On recupère le mouvement
			Move  m = i.next();
			// on simule de mouvement
			AttaxxModel md = model.simulateMove(m);
			int newVal = -1*negaMax(1, md);
			if (newVal > val){
				val  = newVal;
				bestMove = m;
			}
		}
		// retourne le meilleur mouvement
		return bestMove;
	}

	private int negaMax(int dept, AttaxxModel model){
		if (dept >= maxDepth){
			return model.heuristic();
		}

		MoveEnumerator mv = new MoveEnumerator();
		Set<Move> listM = mv.getPossibleMoves(model);
		Iterator<Move> i=listM.iterator();

		int val = MINUS_INFINITY;
		while (i.hasNext()) {
			Move  m = i.next();
			// on simule de mouvement
			AttaxxModel md = model.simulateMove(m);
			val = Math.max(val,-1*negaMax(dept + 1,md));
		}
		return val;
	}
}
