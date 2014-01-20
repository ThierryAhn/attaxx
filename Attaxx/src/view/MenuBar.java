package view;

import javax.swing.Box;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.SwingUtilities;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MenuBar extends JMenuBar{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenu menuParam;
	
	private JMenu menuNewGame;
	
	
	public MenuBar(Attaxx ataxx){
		createMenu();
		createController(ataxx);
	}
	
	/**
	 * Crée les menus
	 */
	private void createMenu(){
		menuParam = new JMenu("Paramètres");
		menuNewGame = new JMenu("Nouveau jeu");
		
		
		this.add(Box.createHorizontalGlue()); 
		this.add(menuParam);
		this.add(menuNewGame);
	}
	
	private void createController(final Attaxx ataxx){
		menuNewGame.addMenuListener(new MenuListener(){
			@Override
			public void menuCanceled(MenuEvent arg0) {
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
			}

			@Override
			public void menuSelected(MenuEvent arg0) {
				ataxx.close();
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						new Attaxx().display();
					}
				});
			}
			
		});
	}
	
}
