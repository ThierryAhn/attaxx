package view;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MenuBar extends JMenuBar{
	
	
	private JMenu menuParam;
	
	private JMenu menuNewGame;
	
	
	public MenuBar(){
		createMenu();
	}
	
	/**
	 * Cr�e les menus
	 */
	private void createMenu(){
		menuParam = new JMenu("Param�tres");
		menuNewGame = new JMenu("Nouveau jeu");
		
		
		this.add(Box.createHorizontalGlue()); 
		this.add(menuParam);
		this.add(menuNewGame);
	}
	
	private void createController(){
		
	}
	
}
