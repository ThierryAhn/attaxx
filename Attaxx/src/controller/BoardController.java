package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JTable;

import model.BoardModel;
import model.Cell;
import model.Move;
import model.MoveEnumerator;
import model.Player;


public class BoardController extends MouseAdapter {

	public void mouseClicked(MouseEvent e) {
		JTable table = (JTable) e.getSource();
		BoardModel model = (BoardModel) table.getModel();
		if (model.getValueAt(table.rowAtPoint(e.getPoint()),
				table.columnAtPoint(e.getPoint())) instanceof Cell) {
			Cell cell =
					(Cell) table.getValueAt(table.rowAtPoint(e.getPoint()),
							table.columnAtPoint(e.getPoint()));
			if (e.getButton() == MouseEvent.BUTTON1 
					&& e.getClickCount() == 1
					&& model.getBoard().getCurrentPlayer().equals(Player.RED)){
				// si rien n'est avant-selectionné
				if(!model.getBoard().isSelected()){
					// si c'est une case rouge
					if(cell.getPlayer().equals(Player.RED)){
						// le selected de la cellule devient true du board aussi
						model.getBoard().setSelected(cell, true);
					}
				}else { // si une case est avant-selectionné
					// si la cellule avant-selectionné n'est pas la même que la cellule selectonnée
					if(!cell.equals(model.getBoard().getSelected())){
						// si la deuxieme selectionnée est rouge ou bleu ou noir
						if(!cell.isEmpty()){
							// annuller la premiere selection et afficher un message
							model.getBoard().setSelected(model.getBoard().getSelected(), false);
							System.out.println("impossible de faire cette action");
						}else{// sinon si elle est vide on joue le mouvement

							Move m = new Move(model.getBoard().getCurrentPlayer(), model.getBoard().getSelected(), cell);
							if (model.getBoard().isLegal(m)){
								model.getBoard().playMove(m);
								System.out.println("Red just finished playing");
								//							try {
								//								Thread.sleep(1000);
								//							} catch (InterruptedException e1) {
								//								e1.printStackTrace();
								//							}

								model.getBoard().playMove(model.getBoard().getAlgo().getNextMove(model.getBoard()));
							}
						}
					}
				}
			}
			table.repaint();
		}
	}
}
