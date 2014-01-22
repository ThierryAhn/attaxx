package view;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;

import javax.swing.JLabel;
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
	
	
	private JLabel scoreLabel;
	
	
	
	public GameBoard(AttaxxModel boardModel) {
		this.boardModel = boardModel;
		createModel();
		createView();
		createTable();
		placeComponents();
		initTable();
		createController();
	}

	public AttaxxModel getBoard() {
		return boardModel;
	}
	
	
	private void createView(){
		scoreLabel = new JLabel("     0 : 0     ");
		Font font = new Font("Arial",Font.BOLD,20);
		scoreLabel.setFont(font);
	}
	
	private void createModel() {
		model = new BoardModel(boardModel);
	}

	public JTable getTable() {
		return table;
	}

	public AttaxxModel getModel() {
		return boardModel;
	}

	public void setModel(AttaxxModel model) {
		this.boardModel = model;
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

	private void placeComponents() {
		
		JPanel panelScore = new JPanel();
		
		setLayout(new BorderLayout());
		
		this.add(table, BorderLayout.CENTER);
		
		panelScore.add(new JLabel(""), BorderLayout.WEST);
		
		JPanel panelTemp = new JPanel(new BorderLayout());
		
		JLabel labelTemp = new JLabel();
		labelTemp.setIcon(BoardCellRenderer.createBigImageIcon("/data/images/redPion.png"));
		panelTemp.add(labelTemp, BorderLayout.WEST);
		panelTemp.add(scoreLabel);
		labelTemp = new JLabel();
		labelTemp.setIcon(BoardCellRenderer.createBigImageIcon("/data/images/bluePion.png"));
		panelTemp.add(labelTemp, BorderLayout.EAST);
		
		panelScore.add(panelTemp, BorderLayout.CENTER);
		
		panelScore.add(new JLabel(""), BorderLayout.EAST);
		
		this.add(panelScore, BorderLayout.SOUTH);
	}



	private void initTable() {
		boardModel.getCell(0, 0).setPlayer(Player.RED);
		boardModel.getCell(6, 6).setPlayer(Player.RED);
		boardModel.getCell(0, 6).setPlayer(Player.BLUE);
		boardModel.getCell(6, 0).setPlayer(Player.BLUE);
		boardModel.setInitial(true);
//		boardModel.getCell(1, 1).setBlock();
//		boardModel.getCell(1, 5).setBlock();
//		boardModel.getCell(3, 3).setBlock();
//		boardModel.getCell(5, 1).setBlock();
//		boardModel.getCell(5, 5).setBlock();
	}

	public void reinit() {
		boardModel.reinit();
		createTable();
		placeComponents();
		initTable();
	}

	private void createController() {
		MouseAdapter bc = new BoardController(this);
		table.addMouseListener(bc);
		table.addMouseMotionListener(bc);
	}

	public JLabel getScoreLabel() {
		return scoreLabel;
	}
	
}