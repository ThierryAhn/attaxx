package model;

import java.util.List;

import model.algorithm.AlphaBeta;


public class AttaxxModel {

	private Cell[][] cells;
	private String currentPlayer;
	private int rowNumber, columnNumber;
	private AlphaBeta alphaBeta;
	private boolean selected = false;

	public AttaxxModel(int rowNumber, int columnNumber, AlphaBeta alphaBeta) {
		this.columnNumber = columnNumber;
		this.rowNumber = rowNumber;
		this.alphaBeta = alphaBeta;
		initCells();
		currentPlayer = Player.RED;
	}

	//	public AttaxxModel(AttaxxModel oldModel) {
	//		columnNumber = oldModel.getColumnNumber();
	//		rowNumber = oldModel.getRowNumber();
	//		currentPlayer = oldModel.getCurrentPlayer();
	//		alphaBeta = oldModel.getAlphaBeta();
	//		cells = new Cell[getRowNumber()][getColumnNumber()];
	//		for(int i = 0; i < getRowNumber();i++){
	//			for(int j = 0; j < getColumnNumber();j++){
	//				cells[i][j] = oldModel.getCell(i, j);
	//			}
	//		}
	//	}

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

	public AlphaBeta getAlphaBeta() {
		return alphaBeta;
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

	private void initCells(){
		cells = new Cell[getRowNumber()][getColumnNumber()];
		for(int i = 0; i < getRowNumber();i++){
			for(int j = 0; j < getColumnNumber();j++){
				cells[i][j] = new Cell(i, j, this);
			}
		}
	}

	public void reinit() {
		initCells();
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
			System.out.println("-----------Blue turn");
			currentPlayer = Player.BLUE;
		}else if (currentPlayer.equals(Player.BLUE)){
			System.out.println("-----------Red turn");
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
			if (!getAlphaBeta().equals(m.getAlphaBeta())) return false;
			for( int r = 0; r< getRowNumber(); r++)
				for( int c = 0; c< getColumnNumber(); c++)
					if( cells[r][c] != m.getCell(r, c))
						return false;
			return true;
		}
		return false;
	}

	public Object clone(){ 
		AttaxxModel m = new AttaxxModel(rowNumber, columnNumber, alphaBeta);
		m.setCurrentPlayer(currentPlayer);
		m.selected = selected;
		for(int i = 0; i < getRowNumber();i++){
			for(int j = 0; j < getColumnNumber();j++){
				m.cells[i][j] = cells[i][j];
			}
		}
		return (Object) this;
	}

	// a modifier
	public boolean gameOver(){
		for(int i = 0; i < getRowNumber();i++){
			for(int j = 0; j < getColumnNumber();j++){
				if(getCell(i, j).isEmpty()) return false;
			}
		}
		return true;
	}

	boolean isLegal(Move move){
		if(move.getPlayer().equals(currentPlayer)
				&& !move.getTarget().equals(move.getRoot())
				&& !move.getRoot().isEmpty()
				&& move.getTarget().isEmpty()){
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
		if(!isLegal(m)) return;	

		List<Cell> listNeib = m.getRoot().getNeighborhoods();

		System.out.println("The move the play is : " + m.getRoot() + " to " + m.getTarget());

		if(m.getTarget().isNeighborhood(m.getRoot())){
			System.out.println("duplicate Move");
			getCell(m.getTarget().getRow(),m.getTarget().getCol()).setPlayer(m.getRoot().getPlayer());
			System.out.println("duplicate Move Played");
			// changer la couleur des voisins
			for (Cell c : listNeib) {
				if(!c.isEmpty() && !c.isBlock() && !c.getPlayer().equals(Player.BLUE)){
					c.setPlayer(m.getPlayer());
					System.out.println(c+" Changed to blue");
				}
			}
			System.out.println("->Neibors changing finished");
		}
		else {
			System.out.println("Jump Move");
			boolean saut = false;
			for (Cell c : listNeib) {
				if(m.getTarget().isNeighborhood(c) && !c.equals(m.getTarget())){
					saut=true;
				}
			}
			if (saut){
				// changer la couleur de la deuxime par celle de la 1ere
				getCell(m.getTarget().getRow(),m.getTarget().getCol()).setPlayer(m.getPlayer());
				// la premiere devient blanche
				getCell(m.getRoot().getRow(),m.getRoot().getCol()).setEmpty();
				System.out.println("Jump Move Played");
				// changer la couleur des voisins
				for (Cell c : listNeib) {
					if(!c.isEmpty() && !c.isBlock() && !c.getPlayer().equals(Player.BLUE)){
						c.setPlayer(m.getPlayer());
						System.out.println(c+" Changed to blue");
					}
				}
				System.out.println("->Neibors changing finished");
			}else{
				System.out.println("impossible de faire cette action");
			}
		}
		nextPlayer();
	}
}
