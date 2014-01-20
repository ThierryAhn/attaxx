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

import model.AttaxxModel;
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

	private JRadioButtonMenuItem[] rbMenuItemLevel = new JRadioButtonMenuItem[3];
	private JRadioButtonMenuItem rbMenuItemAlgo;


	private PlayerAlgo algo;
	private AttaxxModel model;


	public MenuBar(Attaxx ataxx){
		model = new AttaxxModel(7, 7, new MiniMax(1));

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
		rbMenuItemLevel[0] = new JRadioButtonMenuItem("Facile");
		rbMenuItemLevel[0].addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				choixLevel = 1;
				//rbMenuItemLevel[0].setSelected(true);
			}

		});
		groupLevel.add(rbMenuItemLevel[0]);

		subMenuItemLevel.add(rbMenuItemLevel[0]);

		rbMenuItemLevel[1] = new JRadioButtonMenuItem("Normal");
		rbMenuItemLevel[1].addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				choixLevel = 2;
				//rbMenuItemLevel[1].setSelected(true);
			}

		});
		groupLevel.add(rbMenuItemLevel[1]);

		subMenuItemLevel.add(rbMenuItemLevel[1]);

		rbMenuItemLevel[2] = new JRadioButtonMenuItem("Difficile");
		rbMenuItemLevel[2].addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				choixLevel = 3;
				//rbMenuItemLevel[2].setSelected(true);
			}

		});
		groupLevel.add(rbMenuItemLevel[2]);

		subMenuItemLevel.add(rbMenuItemLevel[2]);

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
				model = new AttaxxModel(7, 7, algo);

				rbMenuItemLevel[choixLevel].setSelected(true);


			}

		});
		groupAlgo.add(rbMenuItemAlgo);
		subMenuItemAlgo.add(rbMenuItemAlgo);

		rbMenuItemAlgo = new JRadioButtonMenuItem("AlphaBeta");
		rbMenuItemAlgo.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				algo = new AlphaBeta(choixLevel);
				model = new AttaxxModel(7, 7, algo);

				rbMenuItemLevel[choixLevel].setSelected(true);


			}

		});

		groupAlgo.add(rbMenuItemAlgo);
		subMenuItemAlgo.add(rbMenuItemAlgo);

		rbMenuItemAlgo = new JRadioButtonMenuItem("Negamax");
		rbMenuItemAlgo.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				algo = new NegaMax(choixLevel);
				model = new AttaxxModel(7, 7, algo);

				rbMenuItemLevel[choixLevel].setSelected(true);


			}

		});
		groupAlgo.add(rbMenuItemAlgo);
		subMenuItemAlgo.add(rbMenuItemAlgo);

		rbMenuItemAlgo = new JRadioButtonMenuItem("AlphaBetaNegaMax");
		rbMenuItemAlgo.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				algo = new AlphaBetaNegaMax(choixLevel);
				model = new AttaxxModel(7, 7, algo);

				rbMenuItemLevel[choixLevel].setSelected(true);


			}

		});
		groupAlgo.add(rbMenuItemAlgo);
		subMenuItemAlgo.add(rbMenuItemAlgo);

		rbMenuItemAlgo = new JRadioButtonMenuItem("SSS*");
		rbMenuItemAlgo.addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				algo = new SSS(choixLevel);
				model = new AttaxxModel(7, 7, algo);

				rbMenuItemLevel[choixLevel].setSelected(true);


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
						new Attaxx(model).display();
						System.out.println("level : "+choixLevel);	
					}
				});
				ataxx.close();
			}

		});


	}

}
