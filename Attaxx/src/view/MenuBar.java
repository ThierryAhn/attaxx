package view;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
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
	private JMenu menuNewGame;
	private JMenu menuHelp;
	
	private JMenu subMenuItemLevel;
	private JMenu subMenuItemAlgo;
	
	
	
	
	private ButtonGroup groupLevel;
	private ButtonGroup groupAlgo;
	
	
	public MenuBar(Attaxx ataxx){
		createMenu();
		placeComponents();
		createController(ataxx);
	}
	
	/**
	 * Crée les menus
	 */
	private void createMenu(){
		
		
		// menu parametres
		menuParam = new JMenu("Paramètres");
		menuParam.setIcon(new ImageIcon(getClass().getResource("/data/images/param.png")));
		
		// sous menu level
		subMenuItemLevel = new JMenu("Level");
		
		groupLevel = new ButtonGroup();
		JRadioButtonMenuItem rbMenuItem = new JRadioButtonMenuItem("Facile");
		groupLevel.add(rbMenuItem);
		
		subMenuItemLevel.add(rbMenuItem);
		
		rbMenuItem = new JRadioButtonMenuItem("Normal");
		rbMenuItem.setSelected(true);
		groupLevel.add(rbMenuItem);
		
		subMenuItemLevel.add(rbMenuItem);
		
		rbMenuItem = new JRadioButtonMenuItem("Difficile");
		groupLevel.add(rbMenuItem);
		
		subMenuItemLevel.add(rbMenuItem);
		
		subMenuItemLevel.setIcon(new ImageIcon(getClass().getResource("/data/images/level.png")));
		menuParam.add(subMenuItemLevel);
		
		// sous menu algorithme
		subMenuItemAlgo = new JMenu("Algorithme");
		
		groupAlgo = new ButtonGroup();
		rbMenuItem = new JRadioButtonMenuItem("Minimax");
		rbMenuItem.setSelected(true);
		groupAlgo.add(rbMenuItem);
		subMenuItemAlgo.add(rbMenuItem);
		
		rbMenuItem = new JRadioButtonMenuItem("AlphaBeta");
		groupAlgo.add(rbMenuItem);
		subMenuItemAlgo.add(rbMenuItem);
		
		rbMenuItem = new JRadioButtonMenuItem("Negamax");
		groupAlgo.add(rbMenuItem);
		subMenuItemAlgo.add(rbMenuItem);
		
		rbMenuItem = new JRadioButtonMenuItem("AlphaBetaNegaMax");
		groupAlgo.add(rbMenuItem);
		subMenuItemAlgo.add(rbMenuItem);
		
		rbMenuItem = new JRadioButtonMenuItem("SSS");
		groupAlgo.add(rbMenuItem);
		subMenuItemAlgo.add(rbMenuItem);
		
		
		subMenuItemAlgo.setIcon(new ImageIcon(getClass().getResource("/data/images/logo.png")));
		menuParam.add(subMenuItemAlgo);
		
		
		
		
		// menu aide
		menuHelp = new JMenu("Aide");
		menuHelp.setIcon(new ImageIcon(getClass().getResource("/data/images/aide.png")));
		JMenuItem menuRegle = new JMenuItem("Comment jouer ?");
		menuHelp.add(menuRegle);
		
		JMenuItem menuApropos = new JMenuItem("A propos !");
		menuHelp.add(menuApropos);
		
		
		// menu nouveau jeu
		menuNewGame = new JMenu("Nouveau jeu");
		menuNewGame.setIcon(new ImageIcon(getClass().getResource("/data/images/new.png")));
		
		
	}
	
	private void placeComponents() {
		this.add(menuParam);
		this.add(menuHelp);
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
