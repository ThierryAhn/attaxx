package model;

import java.util.List;
import java.util.TreeSet;

public class MoveEnumerator {

	public TreeSet<Move> getPossibleMoves(AttaxxModel model){
		TreeSet<Move> possibleMoves = new TreeSet<Move>();
		String player = model.getCurrentPlayer();

		for(Cell root : model.getCells(player)){
			List<Cell> listNeibRoot = root.getNeighborhoods();
			for(Cell target : listNeibRoot){
				if(target.isEmpty())
					possibleMoves.add(new Move(player, root, target));
				List<Cell> listNeibTarget = target.getNeighborhoods();
				for(Cell target2 : listNeibTarget){
					if(target2.isEmpty())
						possibleMoves.add(new Move(player, root, target2));
				}

			}
		}

		return possibleMoves;
	}

}
