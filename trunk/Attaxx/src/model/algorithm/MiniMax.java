package model.algorithm;

import java.util.Iterator;
import java.util.TreeSet;

import model.AttaxxModel;
import model.Move;
import model.MoveEnumerator;

/**
 * Classe mod�lisant le joueur de l'IA en basant sur l'algorithme Minimax
 * 
 * @author Rachid ABALINE & Thierry Folabi AHOUNOU
 *
 */
public class MiniMax implements PlayerAlgo{
	
	// ATTRIBUTS
	
	private int maxDepth;

	// CONSTRUCTORS
	
	public MiniMax(int maxDept) {
		
		this.maxDepth = maxDept;
	}

	// FONCTIONS
	
	@Override
	public Move getNextMove(AttaxxModel model) {
		// Meilleur Movement
		Move bestMove=null;

		int val = PLUS_INFINITY;
		
		//List des mouvement possibles
		MoveEnumerator moveEnum = new MoveEnumerator();
		// List des movements possibles
		TreeSet<Move> listM = moveEnum.getPossibleMoves(model);
		
		Iterator<Move> i=listM.iterator();
		// tant qu'il y a des mouvements possibles
		while(i.hasNext()){
			// On recup�re le mouvement
			Move  m = i.next();
			// on simule de mouvement
			AttaxxModel md = model.simulateMove(m);
			int newVal = miniMax(1, md);
			// Max des heuristiques
			if (newVal < val){
				val  = newVal;
				bestMove = m;
			}
		}
		// retourne de meilleur mouvement
		return bestMove;
	}

	/**
	 * 
	 * @param dept
	 * @param model
	 * @return
	 */
	private int miniMax(int dept, AttaxxModel model){
		if (dept >= maxDepth){
			return model.heuristic();
		}

		MoveEnumerator mv = new MoveEnumerator();
		TreeSet<Move> listM = mv.getPossibleMoves(model);
		Iterator<Move> i=listM.iterator();

		// si c'est Max
		if (model.getCurrentPlayer().equals(MAX)){
			int val = MINUS_INFINITY;
			while (i.hasNext()) {
				Move  m = i.next();
				// on simule de mouvement
				AttaxxModel md = model.simulateMove(m);
				val = Math.max(val, miniMax(dept + 1,md));
			}
			return val;
		}
		// si c'est Min
		int val = PLUS_INFINITY;
		while (i.hasNext()) {
			Move  m = i.next();
			// on simule de mouvement
			AttaxxModel md = model.simulateMove(m);
			val = Math.min(val, miniMax(dept + 1,md));
		}
		return val;
	}
}
