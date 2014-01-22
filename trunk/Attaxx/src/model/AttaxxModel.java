package model;

import java.util.ArrayList;
import java.util.List;

import model.algorithm.PlayerAlgo;


public class AttaxxModel implements Cloneable, Comparable<AttaxxModel>{

	private Cell[][] cells;
	private String currentPlayer;
	private int rowNumber, columnNumber;
	private PlayerAlgo algo;
	private boolean initial;


	private boolean selected = false;

	public AttaxxModel(int rowNumber, int columnNumber, PlayerAlgo algo) {
		this.columnNumber = columnNumber;
		this.rowNumber = rowNumber;
		this.algo = algo;
		initCells();
		currentPlayer = Player.RED;
	}

	public AttaxxModel(AttaxxModel oldModel) {
		columnNumber = oldModel.getColumnNumber();
		rowNumber = oldModel.getRowNumber();
		currentPlayer = oldModel.getCurrentPlayer();
		algo = oldModel.getAlgo();

		cells = new Cell[getRowNumber()][getColumnNumber()];

		for(int i = 0; i < getRowNumber();i++){
			for(int j = 0; j < getColumnNumber();j++){
				cells[i][j] = oldModel.getCell(i, j).clone();
			}
		}
	}

	public PlayerAlgo getAlgo() {
		return algo;
	}

	public Cell[][] getCells() {
		return cells;
	}

	public Cell getCell(int row, int col) {
		if ((row < 0 || row >= getRowNumber()) 
				|| (col < 0 || col >= getRowNumber())) {
			throw new IllegalArgumentException();
		}
		return cells[row][col];
	}

	public String getCurrentPlayer(){
		return currentPlayer;
	}

	public void setCurrentPlayer(String player){
		currentPlayer = player;
	}

	public int getRowNumber() {
		return rowNumber;
	}

	public int getColumnNumber() {
		return columnNumber;
	}

	public Cell getNeighbour(Cell c) {
		return getNeighbour(c);
	}

	public Cell getSelected() {
		for(int i = 0; i < getColumnNumber();i++){
			for(int j = 0; j < getRowNumber();j++){
				if(cells[i][j].isSelected())
					return cells[i][j];
			}
		}
		return null;
	}

	public boolean isInitial() {
		return initial;
	}

	public void setInitial(boolean initial) {
		this.initial = initial;
	}

	private void initCells(){
		cells = new Cell[getRowNumber()][getColumnNumber()];
		for(int i = 0; i < getRowNumber();i++){
			for(int j = 0; j < getColumnNumber();j++){
				cells[i][j] = new Cell(i, j, this);
			}
		}
	}

	public void reinit() {
		for(int i = 0; i < getRowNumber();i++){
			for(int j = 0; j < getColumnNumber();j++){
				cells[i][j].setEmpty();
			}
		}
	}

	public void setSelected(Cell cell, boolean bool) {
		if (cell != null){
			if (bool == true){
				if (getSelected() != null){
					getSelected().setSelected(false);
				}
				cells[cell.getRow()][cell.getCol()].setSelected(true);
				selected = true;	
			}else{
				cells[cell.getRow()][cell.getCol()].setSelected(false);
				selected = false;
			}
		}
	}

	public boolean isSelected() {
		return selected;
	}

	public void nextPlayer(){
		if (currentPlayer.equals(Player.RED)){
			currentPlayer = Player.BLUE;
		}else if (currentPlayer.equals(Player.BLUE)){
			currentPlayer = Player.RED;
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof AttaxxModel){
			AttaxxModel m = (AttaxxModel)obj;
			if (currentPlayer != m.getCurrentPlayer()) return false;
			if (getRowNumber() != m.getRowNumber()) return false;
			if (getColumnNumber() != m.getColumnNumber()) return false;
			for( int r = 0; r< getRowNumber(); r++)
				for( int c = 0; c< getColumnNumber(); c++)
					if( cells[r][c] != m.getCell(r, c))
						return false;
			return true;
		}
		return false;
	}

	public AttaxxModel clone(){ 
		return new AttaxxModel(this);
	}

	// a modifier
	public boolean gameOver(){
		Boolean finish = true;
		MoveEnumerator mv = new MoveEnumerator();
		if(mv.getPossibleMoves(this).size() != 0){
			finish = false;
		}
		return finish;
	}

	public boolean isLegal(Move move){
		if(move.getPlayer().equals(currentPlayer)
				&& !move.getTarget().equals(move.getRoot())
				&& !move.getRoot().isEmpty()
				&& move.getTarget().isEmpty()
				&& !move.getTarget().isBlock()){
			if(move.getTarget().isNeighborhood(move.getRoot())){
				return true;
			}else {
				List<Cell> listNeib = move.getRoot().getNeighborhoods();
				for (Cell c : listNeib) {
					if(move.getTarget().isNeighborhood(c)){
						return true;
					}
				}
			}	
		}
		return false;	
	}

	public void playMove(Move m){
		if(!isLegal(m)){	
			return;		// never called, since isLegal only returns true
		}
		// si la cible selectionnée est rouge ou bleu ou noir
		if(!m.getTarget().isEmpty()){
			System.out.println("impossible de faire cette action");
		}else{
			// si la deuxieme est voisine
			if(m.getRoot().isNeighborhood(m.getTarget())){
				// changer la couleur de la deuxime par celle de la 1ere
				m.getTarget().setPlayer(m.getRoot().getPlayer());

				List<Cell> listNeib = m.getTarget().getNeighborhoods();
				// changer la couleur des voisins
				for (Cell c : listNeib) {
					if(!c.isEmpty() && !c.isBlock()){
						c.setPlayer(m.getRoot().getPlayer());
					}
				}
				// annuller la selection de la premiere
				setSelected(getSelected(), false);
				nextPlayer();
			}else{// si la deuxieme n'est pas voisine
				boolean saut = false;
				List<Cell> listNeib = m.getRoot().getNeighborhoods();
				for (Cell c : listNeib) {
					if(m.getTarget().isNeighborhood(c) && !c.equals(m.getTarget())){
						saut=true;
					}
				}
				if (saut){
					// changer la couleur de la deuxime par celle de la 1ere
					m.getTarget().setPlayer(m.getRoot().getPlayer());
					// la premiere devient blanche
					m.getRoot().setEmpty();
					// changer la couleur des voisins
					listNeib = m.getTarget().getNeighborhoods();
					for (Cell c : listNeib) {
						if(!c.isEmpty() && !c.isBlock()){
							c.setPlayer(m.getTarget().getPlayer());
						}
					}
					// annuler la selection de la premiere
					setSelected(getSelected(), false);
					nextPlayer();
				}else{
					// annuler la selection de la premiere
					setSelected(getSelected(), false);
					System.out.println("impossible de faire cette action");
				}
			}
		}
	}

	public List<Cell> getCells(String player){
		List<Cell> list = new ArrayList<Cell>();
		for(int i = 0; i < getRowNumber();i++){
			for(int j = 0; j < getColumnNumber();j++){
				if(cells[i][j].getPlayer().equals(player))
					list.add(cells[i][j]);
			}
		}
		return list;
	}

	public void setAlgo(PlayerAlgo algo) {
		this.algo = algo;
	}

	public int heuristic(){
		return getCells(PlayerAlgo.MAX).size() - getCells(PlayerAlgo.MIN).size();
	}


	public int heuristicNega(){
		if (getCurrentPlayer().equals(PlayerAlgo.MAX))
			return getCells(PlayerAlgo.MAX).size() - getCells(PlayerAlgo.MIN).size();
		return getCells(PlayerAlgo.MIN).size() - getCells(PlayerAlgo.MAX).size();
	}

	public AttaxxModel simulateMove(Move m) {
		AttaxxModel md = clone();
		// si la deuxieme est voisine
		if(m.getRoot().isNeighborhood(m.getTarget())){
			// changer la couleur de la deuxime par celle de la 1ere
			md.getCell(m.getTarget().getRow(), m.getTarget().getCol())
			.setPlayer(m.getRoot().getPlayer());

			List<Cell> listNeib = md.getCell(m.getTarget().getRow(), 
					m.getTarget().getCol()).getNeighborhoods();
			// changer la couleur des voisins
			for (Cell c : listNeib) {
				if(!c.isEmpty() && !c.isBlock()){
					md.getCell(c.getRow(),c.getCol())
					.setPlayer(m.getRoot().getPlayer());
				}
			}
			md.nextPlayer();
		}else{// si la deuxieme n'est pas voisine
			boolean saut = false;
			Cell root = md.getCell(m.getRoot().getRow(),
					m.getRoot().getCol());
			List<Cell> listNeib = root.getNeighborhoods();
			Cell target = null;
			for (Cell c : listNeib) {
				target = md.getCell(m.getTarget().getRow(),m.getTarget().getCol());
				if(target.isNeighborhood(c) && !c.equals(target)){
					saut=true;
				}
			}
			if (saut){
				// changer la couleur de la deuxime par celle de la 1ere
				target.setPlayer(root.getPlayer());
				// la premiere devient blanche
				root.setEmpty();
				// changer la couleur des voisins
				listNeib = target.getNeighborhoods();
				//
				Cell target2;
				for (Cell c : listNeib) {
					target2 = md.getCell(c.getRow(), c.getCol());
					if(!target2.isEmpty() && !target2.isBlock()){
						target2.setPlayer(target.getPlayer());
					}
				}
				md.nextPlayer();
			}
		}
		return md;
	}

	@Override
	public int compareTo(AttaxxModel o) {
		return this.equals(o) ? 0 : 1;
	}

	@Override
	public String toString() {
		String str = "player:"+ (getCurrentPlayer().equals(PlayerAlgo.MAX) ? "Max" : "Min")+"\n";
		for(int i = 0; i < getRowNumber();i++){
			for(int j = 0; j < getColumnNumber();j++){
				str += "|"+ (getCell(i, j).getPlayer()=="" ? " " : 
					getCell(i, j).getPlayer().charAt(0));
			}
			str += "|\n";
		}
		return str + "h="+ heuristic() + "\n";
	}
}
