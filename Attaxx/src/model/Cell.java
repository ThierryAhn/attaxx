package model;

import java.awt.Color;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;


public class Cell implements Comparable<Cell>, Cloneable {
	
	private int row, column;
	private AttaxxModel boardModel;
	private boolean selected;
	private boolean isEmpty = true;
	private boolean isBlock = false;
	private String player = "";
	private PropertyChangeSupport support;
	private Color color = Color.WHITE;
	
	public boolean isEmpty() {
		return isEmpty;
	}

	public void setEmpty() {
		isEmpty = true;
		isBlock = false;
		player = "";
	}

	public boolean isBlock() {
		return isBlock;
	}

	public void setBlock() {
		isBlock = true;
		isEmpty = false;
		player = "";
	}

	public String getPlayer() {
		return player;
	}

	public void setPlayer(String player) {
		this.player = player;
		isBlock = false;
		isEmpty = false;
	}



	public Cell(int row, int column, AttaxxModel boardModel) {
		if (boardModel == null || row < 0 || row >= boardModel.getRowNumber()
				|| column < 0 || column >= boardModel.getColumnNumber() 
				|| boardModel.getCell(row, column) != null) {
			throw new IllegalArgumentException();
		}
		this.row = row;
		this.column = column;
		this.boardModel = boardModel;
		selected = false;
		support = new PropertyChangeSupport(this);
	}
	
	public int getCol() {
		return column;
	}

	public int getRow() {
		return row;
	}

	public AttaxxModel getBoard() {
		return boardModel;
	}
	
	public void setSelected(boolean bool) {
		selected = bool;
	}

	public boolean isSelected() {
		return selected;
	}

	public List<Cell> getNeighborhoods() {
		List<Cell> listNeighborhoors = new ArrayList<Cell>();
		for (int i = -1; i < 2; i++){
			for (int j = -1; j < 2; j++){
				int r = row + i;
				int c = column + j;
				if (r >= 0 && r < boardModel.getRowNumber() 
						&& c >= 0 && c < boardModel.getColumnNumber()){
					listNeighborhoors.add(boardModel.getCell(r, c));
				}
			}
		}
		
		return listNeighborhoors;
	}

	public boolean isNeighborhood(Cell cell) {
		return getNeighborhoods().contains(cell);
	}

	public PropertyChangeListener[] getPropertyChangeListeners(String prop) {
		if (support == null) {
			return new PropertyChangeListener[0];
		}
		return support.getPropertyChangeListeners(prop);
	}

	public void addPropertyChangeListener(String prop,
			PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		support.addPropertyChangeListener(prop, listener);
	}

	public void removePropertyChangeListener(String prop,
			PropertyChangeListener listener) {
		if (listener == null) {
			return;
		}
		support.removePropertyChangeListener(prop, listener);
	}

	protected void firePropertyChange(String propName,
			Object oldVal, Object newVal) {
		support.firePropertyChange(propName, oldVal, newVal);
	}
	
	@Override
	public String toString() {
		return "Cell("+row+","+column+")";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + column;
		result = prime * result + row;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cell other = (Cell) obj;
		if (column != other.column)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	@Override
	public int compareTo(Cell c) {
		return (this.equals(c)) ? 0 : 1;
	}
	
	@Override
	public Cell clone(){
		Cell c = null;
		try {
			c = (Cell)super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return c;
	}

	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
}
