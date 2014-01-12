package model.algorithm;

import java.util.Iterator;
import java.util.Set;

import model.AttaxxModel;
import model.Move;
import model.MoveEnumerator;


public class AlphaBeta implements PlayerAlgo{

	private int maxDepth = 3;

	
	private int minimax_AB(int depth, Node node, int alpha, int beta ) {
		node.setAlpha(alpha);
		node.setBeta(beta);
		if( depth >= maxDepth )
		{
			return node.getModel().heuristic();
		}

		int val;
		if( depth % 2 == 0 ) // max node
		{
			MoveEnumerator me = new MoveEnumerator(); 
			Set<Move> listM = me.getPossibleMoves(node.getModel());
			Iterator<Move> i=listM.iterator();
			while(i.hasNext()){
				Node ni = new Node(node.getModel(), i.next());
				val = minimax_AB( depth+1, ni, node.getAlpha(), node.getBeta());
				node.setAlpha(Math.max(node.getAlpha(), val));
				if (node.getAlpha() >= node.getBeta())
					break;
			}
			return node.getAlpha();
		}
		else                   // min Node
		{
			MoveEnumerator me = new MoveEnumerator(); 
			Set<Move> listM = me.getPossibleMoves(node.getModel());
			Iterator<Move> i=listM.iterator();
			while(i.hasNext()){
				Node ni = new Node(node.getModel(), i.next());
				val = minimax_AB( depth+1, ni, node.getAlpha(), node.getAlpha() );
				node.setBeta(Math.min(node.getBeta(), val));
				if (node.getBeta() <= node.getAlpha())
					break;
			}
			return node.getBeta();
		}
	}
	
	public Move getNextMove(AttaxxModel model){
		int alpha = Node.MINUS_INFINITY;
		final int beta  = Node.PLUS_INFINITY;
		Move bestMove = null;
		
		MoveEnumerator me = new MoveEnumerator(); 
		Set<Move> listM = me.getPossibleMoves(model);
		Iterator<Move> i=listM.iterator();
		while(i.hasNext()){
			
			Move m =  i.next();
			Node ni;
			int val;
			try{
				ni = new Node(model, m);
				val = minimax_AB( 1, ni, alpha, beta );
			}catch(Exception ime){
				ime.printStackTrace();
				continue;
			}
			if( val > alpha)
			{
				alpha = val;
				bestMove = m;
			}
			if (alpha >= beta)
				break;
		}
		return bestMove;
	}
}
