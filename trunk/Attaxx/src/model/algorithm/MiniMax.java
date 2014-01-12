package model.algorithm;

import java.util.Iterator;
import java.util.Set;

import model.AttaxxModel;
import model.Move;
import model.MoveEnumerator;

public class MiniMax implements PlayerAlgo{

	/**
	 * plus infinity
	 */
	public final static int PLUS_INFINITY=Integer.MAX_VALUE; 

	/**
	 * minus infinity
	 */
	public final static int MINUS_INFINITY=-Integer.MIN_VALUE; 

	/**
	 * List des mouvement possibles
	 */
	private MoveEnumerator moveEnum;

	private int maxDept = 3;

	public MiniMax() {
		moveEnum = new MoveEnumerator();
	}

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

		// 22 a corriger
		//		System.out.println(listM.size());

		// retourne de meilleur mouvement
		System.out.println(bestMove);
		return bestMove;
	}

	private int miniMax(int dept, AttaxxModel model){
		if (dept >= maxDept){
			return model.heuristic();
		}

		MoveEnumerator mv = new MoveEnumerator();
		Set<Move> listM = mv.getPossibleMoves(model);
		Iterator<Move> i=listM.iterator();

		// si c'est Max
		if (dept % 2 != 0){
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
