package model;

import javax.swing.table.AbstractTableModel;

@SuppressWarnings("serial")
public class BoardModel extends AbstractTableModel implements BoardTableModel {

	private AttaxxModel boardModel;
	

	/**
	 * Constructor
	 * @param boardModel
	 */
	public BoardModel(AttaxxModel boardModel) {
		this.boardModel = boardModel;
		fireTableDataChanged();
	}
	
	public AttaxxModel getBoard() {
		return boardModel;
	}
	
	@Override
	public int getRowCount() {
		return boardModel.getRowNumber();
	}

	@Override
	public int getColumnCount() {
		return boardModel.getColumnNumber();
	}
	
	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		if ((rowIndex < 0 || rowIndex >= getRowCount()) 
				|| (columnIndex < 0 || columnIndex >= getColumnCount())) {
			throw new IllegalArgumentException();
		}
		return boardModel.getCell(rowIndex, columnIndex);
	}
	
	@Override
	public Class<?> getColumnClass(int columnIndex) {
    	if (columnIndex < 0 || columnIndex >= getColumnCount()) {
    		throw new IllegalArgumentException();
    	}
    	return Cell.class;
	}
	
	public void reinit() {
		boardModel.reinit();
		fireTableDataChanged();
	}

}
