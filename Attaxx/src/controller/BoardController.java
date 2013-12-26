package controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JTable;

import model.BoardModel;
import model.Cell;
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
						}else{// si la deuxieme est voisine
							if(model.getBoard().getSelected().isNeighborhood(cell)){
								// changer la couleur de la deuxime par celle de la 1ere
								cell.setPlayer(model.getBoard().getSelected().getPlayer());
								// changer la couleur des voisins
								List<Cell> listNeib = cell.getNeighborhoods();
								for (Cell c : listNeib) {
									if(!c.isEmpty() && !c.isBlock()){
										c.setPlayer(cell.getPlayer());
									}
								}
								// annuller la selection de la premiere
								model.getBoard().setSelected(model.getBoard().getSelected(), false);
								table.repaint();
								model.getBoard().nextPlayer();
								try {
									Thread.sleep(3000);
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
								model.getBoard().playMove(model.getBoard().getAlphaBeta().getNextMove(model.getBoard()));
								table.repaint();
							}else{// si la deuxieme n'est pas voisine
								List<Cell> listNeib = model.getBoard().getSelected().getNeighborhoods();
								boolean saut = false;
								for (Cell c : listNeib) {
									if(cell.isNeighborhood(c) && !c.equals(cell)){
										saut=true;
									}
								}
								if (saut){
									// changer la couleur de la deuxime par celle de la 1ere
									cell.setPlayer(model.getBoard().getSelected().getPlayer());
									// la premiere devient blanche
									model.getBoard().getSelected().setEmpty();
									// changer la couleur des voisins
									for (Cell c : listNeib) {
										if(!c.isEmpty() && !c.isBlock()){
											c.setPlayer(cell.getPlayer());
										}
									}
								}else{
									System.out.println("impossible de faire cette action");
								}
								// annuller la selection de la premiere
								model.getBoard().setSelected(model.getBoard().getSelected(), false);
								table.repaint();
								model.getBoard().nextPlayer();
								try {
									Thread.sleep(3000);
								} catch (InterruptedException e1) {
									e1.printStackTrace();
								}
								model.getBoard().playMove(model.getBoard().getAlphaBeta().getNextMove(model.getBoard()));
								table.repaint();
							}		
						}
					}
				}
			}
		}
	}
}
