package view;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MenuBar extends JMenuBar{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JMenu menuParam;
	
	private JMenu menuItemLevel;
	private JMenu menuItemAlgo;
	
	
	private JMenu menuNewGame;
	
	private ButtonGroup groupLevel;
	private ButtonGroup groupAlgo;
	
	
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
		
		menuItemLevel = new JMenu("Level");
		menuItemAlgo = new JMenu("Algorithme");
		
		
		groupLevel = new ButtonGroup();
		JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("Facile");
		groupLevel.add(rbMenuItem);
		
		menuItemLevel.add(rbMenuItem);
		
		rbMenuItem = new JRadioButtonMenuItem("Normal");
		rbMenuItem.setSelected(true);
		groupLevel.add(rbMenuItem);
		
		menuItemLevel.add(rbMenuItem);
		
		rbMenuItem = new JRadioButtonMenuItem("Difficile");
		groupLevel.add(rbMenuItem);
		
		menuItemLevel.add(rbMenuItem);
		
		menuParam.add(menuItemLevel);
		
		
		groupAlgo = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("Minimax");
		rbMenuItem.setSelected(true);
		groupAlgo.add(rbMenuItem);
		menuItemAlgo.add(rbMenuItem);
		
		rbMenuItem = new JRadioButtonMenuItem("AlphaBeta");
		groupAlgo.add(rbMenuItem);
		menuItemAlgo.add(rbMenuItem);
		
		rbMenuItem = new JRadioButtonMenuItem("AlphaBetaNegaMax");
		groupAlgo.add(rbMenuItem);
		menuItemAlgo.add(rbMenuItem);
		
		rbMenuItem = new JRadioButtonMenuItem("SSS");
		groupAlgo.add(rbMenuItem);
		menuItemAlgo.add(rbMenuItem);
		
		menuParam.add(menuItemAlgo);
		
		
		
		this.add(menuParam);
		this.add(Box.createHorizontalGlue()); 
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
