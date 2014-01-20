package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.TreeSet;

public class MoveEnumerator {

	public TreeSet<Move> getPossibleMoves(AttaxxModel model){
		List<Move> possibleMoves = new ArrayList<Move>();
		String player = model.getCurrentPlayer();

		for(Cell root : model.getCells(player)){
			List<Cell> listNeibRoot = root.getNeighborhoods();
			for(Cell target : listNeibRoot){
				if(target.isEmpty()){
					Move m = new Move(player, root, target);
					if (!possibleMoves.contains(m))
					possibleMoves.add(m);
				}
				List<Cell> listNeibTarget = target.getNeighborhoods();
				for(Cell target2 : listNeibTarget){
					if(target2.isEmpty()){
						Move m = new Move(player, root, target2);
						if (!possibleMoves.contains(m)){
							possibleMoves.add(m);
						}
					}
				}

			}
		}

		return new TreeSet<>(possibleMoves);
	}

}
