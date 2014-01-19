package model.algorithm;

import java.util.Iterator;
import java.util.Set;

import model.AttaxxModel;
import model.Move;
import model.MoveEnumerator;

/**
 * Classe modélisant le joueur de l'IA en basant sur l'algorithme Minimax
 * 
 * @author Rachid ABALINE & Thierry Folabi AHOUNOU
 *
 */
public class MiniMax implements PlayerAlgo{
	
	// ATTRIBUTS
	
	//List des mouvement possibles
	private MoveEnumerator moveEnum;

	private int maxDept;

	// CONSTRUCTORS
	
	public MiniMax(int maxDept) {
		moveEnum = new MoveEnumerator();
		this.maxDept = maxDept;
	}

	// FONCTIONS
	
	@Override
	public Move getNextMove(AttaxxModel model) {
		// Meilleur Movement
		Move bestMove=null;

		// heuritic
		int val = PLUS_INFINITY;

		// List des movements possibles
		Set<Move> listM = moveEnum.getPossibleMoves(model);
		Iterator<Move> i=listM.iterator();
		// tant qu'il y a des mouvements possibles
		while(i.hasNext()){
			// On recupère le mouvement
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

		System.out.println(listM.size());

		// retourne de meilleur mouvement
		System.out.println(bestMove);
		return bestMove;
	}

	/**
	 * 
	 * @param dept
	 * @param model
	 * @return
	 */
	private int miniMax(int dept, AttaxxModel model){
		if (dept >= maxDept){
			return model.heuristic();
		}

		MoveEnumerator mv = new MoveEnumerator();
		Set<Move> listM = mv.getPossibleMoves(model);
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
