package model.algorithm;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import model.AttaxxModel;
import model.Move;
import model.MoveEnumerator;

public class MiniMax implements PlayerAlgo{
	
	/**
	 * plus infinity
	 */
	public final static int PLUS_INFINITY=1000; 
	
	/**
	 * minus infinity
	 */
	public final static int MINUS_INFINITY=-1000; 
	
	private MoveEnumerator moveEnum;
	
	private Map<Integer, List<AttaxxModel>> tree;
	
	public MiniMax() {
		moveEnum = new MoveEnumerator();
		tree = new TreeMap<Integer, List<AttaxxModel>>();
	}

	@Override
	public Move getNextMove(AttaxxModel model) {
		return miniMax(model);
	}
	
	private Move miniMax(AttaxxModel model){
		// Meilleur Movement
		Move bestMove=null;
		
		// heuritic
		int heuristic = MINUS_INFINITY;
		
		// List des movements possibles
		Set<Move> listM = moveEnum.getPossibleMoves(model);
		Iterator<Move> i=listM.iterator();
		
		// tant qu'il y a des mouvements possibles
		while(i.hasNext()){
			
			// On recupère le mouvement
			Move  m = i.next();
			
			// on simule de mouvement
			AttaxxModel md = model.simulateMove(m);
			System.out.println(md + " heuritic =" + md.heristic());
			
			// Max des heuristiques
			if (md.heristic() > heuristic){
				heuristic = md.heristic();
				bestMove = m;
			}
		}
//		System.out.println(listM.size());
		
		// retourne de meilleur mouvement
		return bestMove;
		
	}

}
