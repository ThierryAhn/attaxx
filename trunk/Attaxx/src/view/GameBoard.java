package view;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;

import javax.swing.JPanel;
import javax.swing.JTable;

import model.AttaxxModel;
import model.BoardModel;
import model.BoardTableModel;
import model.Cell;
import model.Player;
import controller.BoardController;




@SuppressWarnings("serial")
public class GameBoard extends JPanel {

	private static final int DIMENSIONVALUE = 50;

	private BoardTableModel model;
	private JTable table;
	private AttaxxModel boardModel;

	public GameBoard(AttaxxModel boardModel) {
		this.boardModel = boardModel;
		createModel();
		createTable();
		placeTable();
		initTable();
		createController();
	}

	public AttaxxModel getBoard() {
		return boardModel;
	}

	private void createModel() {
		model = new BoardModel(boardModel);
	}

	public JTable getTable() {
		return table;
	}

	private void createTable() {
		table = new JTable(boardModel.getRowNumber(), boardModel.getColumnNumber());
		BoardCellRenderer bcr = new BoardCellRenderer();
		table.setDefaultRenderer(Cell.class, bcr);
		table.setModel(model);
		table.setRowSelectionAllowed(false);
		for (int i = 0; i < table.getRowCount(); i++) {
			for (int j = 0; j < table.getColumnCount(); j++) {
				table.setValueAt(boardModel.getCell(i, j), i, j);
			}
		}

		table.setRowHeight(DIMENSIONVALUE);
		for (int i = 0; i < model.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(DIMENSIONVALUE);
		}
	}

	private void placeTable() {
		this.add(table, BorderLayout.CENTER);
	}



	private void initTable() {
		boardModel.getCell(0, 0).setPlayer(Player.RED);
		boardModel.getCell(6, 6).setPlayer(Player.RED);
		boardModel.getCell(0, 6).setPlayer(Player.BLUE);
		boardModel.getCell(6, 0).setPlayer(Player.BLUE);
//		boardModel.getCell(3, 3).setBlock();
	}

	public void reinit() {
		boardModel.reinit();
		createTable();
		placeTable();
		initTable();
	}

	private void createController() {
		MouseAdapter bc = new BoardController();
		table.addMouseListener(bc);
		table.addMouseMotionListener(bc);
	}
}
