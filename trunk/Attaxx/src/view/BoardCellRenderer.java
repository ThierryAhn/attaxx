package view;

import java.awt.Component;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

import model.Cell;



@SuppressWarnings("serial")
public class BoardCellRenderer extends DefaultTableCellRenderer {

	private static final ImageIcon[] ICONS = new ImageIcon[] {
		createImageIcon("/data/images/bluePion.png"),
		createImageIcon("/data/images/redPion.png"),
		createImageIcon("/data/images/block.png"),
		createBigImageIcon("/data/images/bluePion.png"),
		createBigImageIcon("/data/images/redPion.png")};


	private DefaultTableCellRenderer delegate;
	
	public BoardCellRenderer() {
		delegate = new DefaultTableCellRenderer();
	}
	
	@Override
	public Component getTableCellRendererComponent(JTable table,
			Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {
		
		Cell cell = (Cell) table.getValueAt(row, column);
		
		if (cell.getPlayer().equals("blue")) {
			if(cell.isSelected())
				delegate.setIcon(ICONS[3]);
			else
				delegate.setIcon(ICONS[0]);
		}
		else if (cell.getPlayer().equals("red")){
			if(cell.isSelected())
				delegate.setIcon(ICONS[4]);
			else
				delegate.setIcon(ICONS[1]);
		}
		else if (cell.isBlock()){
			delegate.setIcon(ICONS[2]);
		}else {
			delegate.setIcon(null);
		}
		
		delegate.setBackground(cell.getColor());
		
		Component result = delegate.getTableCellRendererComponent(table, null,
    			isSelected, hasFocus, row, column);
    	return result;
	}

	private static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = BoardCellRenderer.class.getResource(path);
		if (imgURL == null) {
			System.err.println("Ressource non trouvée : " + path);
			return null;
		}
		ImageIcon icon = new ImageIcon(imgURL);
		Image img = icon.getImage().getScaledInstance(46, 44, 
				java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}

	private static ImageIcon createBigImageIcon(String path) {
		java.net.URL imgURL = BoardCellRenderer.class.getResource(path);
		if (imgURL == null) {
			System.err.println("Ressource non trouvée : " + path);
			return null;
		}
		ImageIcon icon = new ImageIcon(imgURL);
		Image img = icon.getImage().getScaledInstance(48, 46, 
				java.awt.Image.SCALE_SMOOTH);
		return new ImageIcon(img);
	}
}
