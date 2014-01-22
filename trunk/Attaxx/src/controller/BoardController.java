package controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JOptionPane;
import javax.swing.JTable;

import model.BoardModel;
import model.Cell;
import model.Move;
import model.Player;
import view.GameBoard;


public class BoardController extends MouseAdapter {

	GameBoard gb;

	public BoardController(GameBoard gb) {
		this.gb = gb;
	}

	public void mousePressed(MouseEvent e) {
		JTable table = (JTable) e.getSource();
		BoardModel model = (BoardModel) table.getModel();
		if (model.getValueAt(table.rowAtPoint(e.getPoint()),
				table.columnAtPoint(e.getPoint())) instanceof Cell) {
			Cell cell =
					(Cell) table.getValueAt(table.rowAtPoint(e.getPoint()),
							table.columnAtPoint(e.getPoint()));
			if (e.getButton() == MouseEvent.BUTTON1 
					&& e.getClickCount() == 1
					&& model.getBoard().getCurrentPlayer().equals(Player.RED)) {
				// Si le jeu n'est pas finis
				if (!model.getBoard().gameOver()) {
					// si rien n'est pre-selectionné
					if (!model.getBoard().isSelected()) {
						// si c'est une case rouge
						if (cell.getPlayer().equals(Player.RED)) {
							// le selected de la cellule devient true du board aussi
							model.getBoard().setSelected(cell, true);
							colorNeiborhood(cell, new Color(250,240,230));
						} else {
							// bip systeme : aucune cellule selectionnee
							java.awt.Toolkit.getDefaultToolkit().beep();
						}
					} else { // si une case est avant-selectionné
						// si la cellule avant-selectionné n'est pas la même que la cellule selectonnée
						if (!cell.equals(model.getBoard().getSelected())) {
							// si la deuxieme selectionnée est rouge ou bleu ou noir
							if (!cell.isEmpty()) {
								// annuller la premiere selection et afficher un message
								colorNeiborhood(model.getBoard().getSelected(), Color.WHITE);
								model.getBoard().setSelected(model.getBoard().getSelected(), false);
								// bip systeme : cellule non vide
								java.awt.Toolkit.getDefaultToolkit().beep();
							} else {// sinon si elle est vide on joue le mouvement
								colorNeiborhood(model.getBoard().getSelected(), Color.WHITE);
								Move m = new Move(model.getBoard().getCurrentPlayer(), model.getBoard().getSelected(), cell);
								if (model.getBoard().isLegal(m)) {
									model.getBoard().playMove(m);
									model.getBoard().setInitial(false);
									if (model.getBoard().gameOver()) {
										endOfGame(model);
									}
								} else {
									// bip systeme : mouvement impossible
									java.awt.Toolkit.getDefaultToolkit().beep();
								}
							}
						}
					}
				} else {
					endOfGame(model);
				}
			}
			table.repaint();
		}
	}

	public void mouseReleased(MouseEvent e) {
		JTable table = (JTable) e.getSource();
		BoardModel model = (BoardModel) table.getModel();
		if (model.getBoard().getCurrentPlayer().equals(Player.BLUE)) {
			if (!model.getBoard().gameOver()) {
				long begin = System.currentTimeMillis();
				System.out.println("tour de l'ordi");
				model.getBoard().playMove(model.getBoard().getAlgo().getNextMove(model.getBoard()));
				
				table.repaint();
				System.out.println("mon tour");
				long end = System.currentTimeMillis();
				System.out.println((end-begin));
				if (model.getBoard().gameOver()) {
					endOfGame(model);
				}
			} else {
				endOfGame(model);
			}
			table.repaint();
		}
	}

	private void endOfGame(BoardModel model) {
		int retour = JOptionPane.showConfirmDialog(null, 
				"Le jeux est finis\n Voulez vous rejouer ?",
				"Félicitation !",
				JOptionPane.YES_NO_OPTION);
		if (retour == JOptionPane.NO_OPTION) {
			System.exit(0);
		} else {
			gb.reinit();
		}
	}

	private void colorNeiborhood(Cell c, Color color){
		Set<Cell> listNeib = new HashSet<Cell>(c.getNeighborhoods());
		for(Cell cell : c.getNeighborhoods()){
			listNeib.addAll(cell.getNeighborhoods());
		}
		for (Cell cell : listNeib){
			if(!cell.isBlock() && cell.isEmpty())
				cell.setColor(color);
		}
	}
}
