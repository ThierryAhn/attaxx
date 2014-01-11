//package model.algorithm;
//
//import model.AttaxxModel;
//import model.Move;
//import model.MoveEnumerator;
//import model.Player;
//
//
//public class AlphaBeta {
//
//	int maxDepth = 3;
//
//	int movesConsidered;
//
//	int leafNodes;
//
//	int roots;
//
//	int totalBranchings;
//
//	int totalBoardPositions;
//	
//	private int minimax_AB(int depth, Node node, int alpha, int beta ) {
//		node.setAlpha(alpha);
//		node.setBeta(beta);
//		movesConsidered++;
//		if( depth >= maxDepth )
//		{
//			leafNodes++;
//			return heuristic(node.getGame());
//		}
//
//		int val;
//		if( depth % 2 == 0 ) // max node
//		{
//			MoveEnumerator me = new MoveEnumerator(node.getGame()); 
//			roots++;
//			while(me.hasMoreElements()){
//				System.out.println("while1");
//				totalBranchings++;
//				Node ni = new Node(node.getGame(), me.nextMove());
//				val = minimax_AB( depth+1, ni, node.getAlpha(), node.getBeta());
//				node.setAlpha(Math.max(node.getAlpha(), val));
//				if (node.getAlpha() >= node.getBeta())
//					break;
//			}
//			return node.getAlpha();
//		}
//		else                   // min Node
//		{
//			MoveEnumerator me = new MoveEnumerator(node.getGame()); 
//			roots++;
//			while(me.hasMoreElements()){
//				System.out.println("while2");
//				totalBranchings++;
//				Node ni = new Node(node.getGame(), me.nextMove());
//				val = minimax_AB( depth+1, ni, node.getAlpha(), node.getAlpha() );
//				node.setBeta(Math.min(node.getBeta(), val));
//				if (node.getBeta() <= node.getAlpha())
//					break;
//			}
//			return node.getBeta();
//		}
//	}
//	
//	public Move getNextMove(AttaxxModel model){
//		movesConsidered = leafNodes = totalBranchings = roots = 0;
//		MoveEnumerator me = new MoveEnumerator(model);
//		int alpha = Node.MINUS_INFINITY;
//		final int beta  = Node.PLUS_INFINITY;
//		Move bestMove = null;
//		while( me.hasMoreElements())
//		{
//			roots++;
//			Move m =  me.nextMove();
//			totalBranchings++;
//			Node ni;
//			int val;
//			try{
//				ni = new Node(model, m);
//				val = minimax_AB( 1, ni, alpha, beta );
//			}catch(Exception ime){
//				ime.printStackTrace();
//				continue;
//			}
//			if( val > alpha)
//			{
//				alpha = val;
//				bestMove = m;
//			}
//			if (alpha >= beta)
//				break;
//		}
//		return bestMove;
//	}
//	
//	public int heuristic (AttaxxModel game){
//		int blue = 0;
//		int red = 0;
//		for( int r = 0; r< game.getRowNumber(); r++){
//			for( int c = 0; c< game.getColumnNumber(); c++) {
//				if(!game.getCell(r, c).isEmpty() && !game.getCell(r, c).isBlock()){
//					if(game.getCell(r, c).getPlayer().equals(Player.BLUE)){
//						blue++;
//					}
//					if(game.getCell(r, c).getPlayer().equals(Player.RED)){
//						red++;
//					}
//				}
//			}	
//		}
//		return blue - red;
//	}
//
//}
