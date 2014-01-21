package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
								model.getBoard().setSelected(model.getBoard().getSelected(), false);
								// bip systeme : cellule non vide
								java.awt.Toolkit.getDefaultToolkit().beep();
							} else {// sinon si elle est vide on joue le mouvement
	
								Move m = new Move(model.getBoard().getCurrentPlayer(), model.getBoard().getSelected(), cell);
								if (model.getBoard().isLegal(m)) {
									model.getBoard().playMove(m);
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
	
	public void endOfGame(BoardModel model) {
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
}
