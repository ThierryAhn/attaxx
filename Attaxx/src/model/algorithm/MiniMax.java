package model.algorithm;

import java.util.List;

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
		// meilleur score
		int bestValue = PLUS_INFINITY;
		//List des mouvement possibles
		MoveEnumerator moveEnum = new MoveEnumerator();
		// List des movements possibles
		List<Move> listM = moveEnum.getPossibleMoves(model);
		System.out.println(listM);
		// tant qu'il y a des mouvements possibles
		for(Move m : listM){
			// On recupère le mouvement et on le simule
			AttaxxModel md = model.simulateMove(m);
			int newVal = miniMax(1, md);
			// Max des heuristiques
			if (newVal < bestValue){
				bestValue  = newVal;
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
		int val;
		MoveEnumerator mv = new MoveEnumerator();
		List<Move> listM = mv.getPossibleMoves(model);
		// si c'est Max
		if (model.getCurrentPlayer().equals(MAX)){
			val = MINUS_INFINITY;
			for(Move m : listM){
				// on simule de mouvement
				AttaxxModel md = model.simulateMove(m);
				val = Math.max(val, miniMax(dept + 1,md));
			}
		}else{
			// si c'est Min
			val = PLUS_INFINITY;
			for(Move m : listM){
				// on simule de mouvement
				AttaxxModel md = model.simulateMove(m);
				val = Math.min(val, miniMax(dept + 1,md));
			}
		}
		return val;
	}
}
