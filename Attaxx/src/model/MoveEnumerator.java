package model;

import java.util.Enumeration;
import java.util.NoSuchElementException;

@SuppressWarnings("rawtypes")
public class MoveEnumerator implements Enumeration {

	private AttaxxModel model;

	private Move lastMove = null;

	private Move nextMove = null;

	private boolean infoDirty = true;

	private int mt_row= 0, mt_col =0;

	private int h = -2, v =-2;

	private boolean adjacentConsidered = false;
	
	public MoveEnumerator(AttaxxModel model){
		this.model = model;
	}
	
	@Override
	public boolean hasMoreElements() {
		try{
			calcNextMove(false);
		}catch ( NoSuchElementException nsee ){
			return false;
		}
		return nextMove != null;
	}

	@Override
	public Object nextElement() {
		return (Object)nextMove();
	}

	private void calcNextMove(boolean makeDirty) throws NoSuchElementException {

		if( !infoDirty ){
			infoDirty = makeDirty;
			return;
		}
		infoDirty = makeDirty;

		lastMove = nextMove;

		if( lastMove != null && lastMove.isPass() )
		{
			nextMove = null;
			return;
		}

		int height = model.getRowNumber();

		int width = model.getColumnNumber();

		for( ; mt_row < height; mt_row++)
		{
			for( ; mt_col < width; mt_col++, adjacentConsidered = false)
			{
				if(!model.getCell(mt_row,mt_col).isEmpty())
					continue;

				/* skip adjacent move if already considered */
				if( adjacentConsidered == false ){
					adjacentConsidered = true;
					// find adjacent move
					for( int h = -1; h <= 1; h++)
						for( int v = -1; v <= 1; v++){
							int col = mt_col + h;
							int row = mt_row + v;
							if( col < 0 || col >= width ||
									row < 0 || row >= height)
								continue;
							if( model.getCell(row,col).getPlayer().equals(Player.BLUE)){
								nextMove =  new Move(model.getCurrentPlayer(),
										model.getCell(row, col),
										model.getCell(mt_row,mt_col));
								return;
							}
						}
				}

//				if(model.isLegal(nextMove))
//					continue;
				
				// find next jump move
				for( ; h <= 2; h++ ) {
					for( ; v <= 2; v++ ){
						if( Math.max(Math.abs(h),Math.abs(v)) < 2 )
							continue;

						int col = mt_col + h;
						int row = mt_row + v;
						if( col < 0 || col >= width ||
								row < 0 || row >= height)
							continue;
						if( model.getCell(col, row ).getPlayer().equals(Player.BLUE)){
							nextMove =  new Move(model.getCurrentPlayer(),
									model.getCell(row, col),
									model.getCell(mt_col, mt_row));
							return;
						}
					}
					v = -2;
				}
			}
			mt_col =0;
			adjacentConsidered = false;
		}

		if( lastMove == null )
			nextMove = Move.getNewPassMove(model.getCurrentPlayer());
		else
			nextMove = null;
	}

	public Move nextMove() throws NoSuchElementException{
		calcNextMove(true);
		return nextMove;
	}
	

}
