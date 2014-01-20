package view;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

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

import model.algorithm.AlphaBeta;
import model.algorithm.AlphaBetaNegaMax;
import model.algorithm.MiniMax;
import model.algorithm.NegaMax;
import model.algorithm.PlayerAlgo;
import model.algorithm.SSS;

public class MenuBar extends JMenuBar{
	
	private int choixLevel = 1;
	
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
	
	private JRadioButtonMenuItem rbMenuItemLevel;
	private JRadioButtonMenuItem rbMenuItemAlgo;
	
	
	private PlayerAlgo algo;
	
	
	public MenuBar(Attaxx ataxx){
		createMenu();
		placeComponents();
		createController(ataxx);
	}
	
	/**
	 * Cr�e les menus
	 */
	private void createMenu(){
		
		// menu parametres
		menuParam = new JMenu("Param�tres");
		menuParam.setIcon(new ImageIcon(getClass().getResource("/data/images/param.png")));
		
		// sous menu level
		subMenuItemLevel = new JMenu("Level");
		
		groupLevel = new ButtonGroup();
		rbMenuItemLevel = new JRadioButtonMenuItem("Facile");
		rbMenuItemLevel.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				choixLevel = 1;
				rbMenuItemLevel.setSelected(true);
			}
			
		});
		groupLevel.add(rbMenuItemLevel);
		
		subMenuItemLevel.add(rbMenuItemLevel);
		
		rbMenuItemLevel = new JRadioButtonMenuItem("Normal");
		rbMenuItemLevel.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				choixLevel = 2;
				rbMenuItemLevel.setSelected(true);
			}
			
		});
		groupLevel.add(rbMenuItemLevel);
		
		subMenuItemLevel.add(rbMenuItemLevel);
		
		rbMenuItemLevel = new JRadioButtonMenuItem("Difficile");
		rbMenuItemLevel.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				choixLevel = 3;
				rbMenuItemLevel.setSelected(true);
			}
			
		});
		groupLevel.add(rbMenuItemLevel);
		
		subMenuItemLevel.add(rbMenuItemLevel);
		
		subMenuItemLevel.setIcon(new ImageIcon(getClass().getResource("/data/images/level.png")));
		menuParam.add(subMenuItemLevel);
		
		// sous menu algorithme
		subMenuItemAlgo = new JMenu("Algorithme");
		
		groupAlgo = new ButtonGroup();
		rbMenuItemAlgo = new JRadioButtonMenuItem("Minimax");
		rbMenuItemAlgo.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				algo = new MiniMax(choixLevel);
				rbMenuItemAlgo.setSelected(true);
			}
			
		});
		groupAlgo.add(rbMenuItemAlgo);
		subMenuItemAlgo.add(rbMenuItemAlgo);
		
		rbMenuItemAlgo = new JRadioButtonMenuItem("AlphaBeta");
		rbMenuItemAlgo.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				algo = new AlphaBeta(choixLevel);
				rbMenuItemAlgo.setSelected(true);
			}
			
		});
		
		groupAlgo.add(rbMenuItemAlgo);
		subMenuItemAlgo.add(rbMenuItemAlgo);
		
		rbMenuItemAlgo = new JRadioButtonMenuItem("Negamax");
		rbMenuItemAlgo.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				algo = new NegaMax(choixLevel);
				rbMenuItemAlgo.setSelected(true);
			}
			
		});
		groupAlgo.add(rbMenuItemAlgo);
		subMenuItemAlgo.add(rbMenuItemAlgo);
		
		rbMenuItemAlgo = new JRadioButtonMenuItem("AlphaBetaNegaMax");
		rbMenuItemAlgo.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				algo = new AlphaBetaNegaMax(choixLevel);
				rbMenuItemAlgo.setSelected(true);
			}
			
		});
		groupAlgo.add(rbMenuItemAlgo);
		subMenuItemAlgo.add(rbMenuItemAlgo);
		
		rbMenuItemAlgo = new JRadioButtonMenuItem("SSS*");
		rbMenuItemAlgo.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				algo = new SSS(choixLevel);
				rbMenuItemAlgo.setSelected(true);
			}
			
		});
		groupAlgo.add(rbMenuItemAlgo);
		subMenuItemAlgo.add(rbMenuItemAlgo);
		
		
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
		// new game
		menuNewGame.addMenuListener(new MenuListener(){
			@Override
			public void menuCanceled(MenuEvent arg0) {
			}

			@Override
			public void menuDeselected(MenuEvent arg0) {
			}

			@Override
			public void menuSelected(MenuEvent arg0) {
				
				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						System.out.println("level : "+choixLevel);
						new Attaxx(algo).display();
						
					}
				});
				ataxx.close();
			}
			
		});
		
		
	}
	
}
