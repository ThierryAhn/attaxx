package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.SwingUtilities;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;

public class MenuBar extends JMenuBar{

	private static final long serialVersionUID = 1L;

	private final static int MINIMAX = 0;
	private final static int ALPHABETA = 1;
	private final static int NEGAMAX = 2;
	private final static int ALPHABETANEGAMAX = 3;
	private final static int SSS = 4;

	private JMenu menuParam;
	private JMenu menuNewGame;
	private JMenu menuHelp;

	private JMenu subMenuItemLevel;
	private JMenu subMenuItemAlgo;

	private JMenuItem menuRegle;
	private JMenuItem menuApropos;

	private ButtonGroup groupLevel;
	private ButtonGroup groupAlgo;
	private JMenuItem addBricks;

	private JRadioButtonMenuItem[] rbMenuItemLevel;
	private JRadioButtonMenuItem[] rbMenuItemAlgo;

	private int newAlgo;
	private int algo;
	private int choixLevel;
	private Attaxx ataxx;

	final String algos[] = {"MiniMax",
			"AlphaBeta",
			"NegaMax",
			"AlphaBetaNegaMax",
	"SSS"};
	final String level[] = {"Facile",
			"Moyen",
	"Difficile"};

	public MenuBar(Attaxx ataxx){
		this.choixLevel = ataxx.getChoixLevel();
		this.algo = ataxx.getAlgor();
		this.ataxx = ataxx;
		createMenu();
		placeComponents();
		createController(ataxx);
		updateItems();
	}

	/**
	 * Crée les menus
	 */
	private void createMenu(){
		subMenuItemLevel = new JMenu("Level");
		groupLevel = new ButtonGroup();
		menuParam = new JMenu("Paramètres");
		rbMenuItemLevel = new JRadioButtonMenuItem[level.length];
		rbMenuItemAlgo = new JRadioButtonMenuItem[algos.length];
		for(int i = 0;i<level.length;i++){
			rbMenuItemLevel[i] = new JRadioButtonMenuItem(level[i]);
			groupLevel.add(rbMenuItemLevel[i]);
			subMenuItemLevel.add(rbMenuItemLevel[i]);
		}
		subMenuItemLevel.setIcon(new ImageIcon(
				getClass().getResource("/data/images/level.png")));
		menuParam.add(subMenuItemLevel);
		// sous menu algo
		subMenuItemAlgo = new JMenu("Algorithme");
		groupAlgo = new ButtonGroup();
		for(int i = 0;i<algos.length;i++){
			rbMenuItemAlgo[i] = new JRadioButtonMenuItem(algos[i]);
			groupAlgo.add(rbMenuItemAlgo[i]);
			subMenuItemAlgo.add(rbMenuItemAlgo[i]);
		}
		subMenuItemAlgo.setIcon(
				new ImageIcon(getClass().getResource("/data/images/logo.png")));
		menuParam.add(subMenuItemAlgo);


		// menu aide
		menuHelp = new JMenu("Aide");
		menuHelp.setIcon(
				new ImageIcon(getClass().getResource("/data/images/aide.png")));
		menuRegle = new JMenuItem("Règle du jeu");
		menuHelp.add(menuRegle);

		menuApropos = new JMenuItem("A propos !");
		menuHelp.add(menuApropos);

		// menu nouveau jeu
		menuNewGame = new JMenu("Nouveau jeu");
		menuNewGame.setIcon(
				new ImageIcon(getClass().getResource("/data/images/new.png")));

		this.add(menuParam);
		this.add(menuHelp);
		this.add(Box.createHorizontalGlue()); 
		this.add(menuNewGame);

		addBricks = new JMenuItem("Gérer Blocs");
		menuParam.add(addBricks);
		addBricks.setIcon(
				new ImageIcon(
						getClass().getResource("/data/images/blockLogo.png")));
		rbMenuItemLevel[choixLevel-1].setSelected(true);
		rbMenuItemAlgo[algo].setSelected(true);
	}

	private void placeComponents() {
		this.add(menuParam);
		this.add(menuHelp);
		this.add(Box.createHorizontalGlue()); 
		this.add(menuNewGame);	
	}

	private void createController(final Attaxx ataxx){


		rbMenuItemLevel[0].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				rbMenuItemLevel[0].setSelected(true);
				newGame(1,algo);
			}
		});

		rbMenuItemLevel[1].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				rbMenuItemLevel[1].setSelected(true);
				newGame(2,algo);
			}
		});

		rbMenuItemLevel[2].addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				rbMenuItemLevel[2].setSelected(true);
				newGame(3,algo);
			}
		});


		rbMenuItemAlgo[MINIMAX].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				rbMenuItemAlgo[MINIMAX].setSelected(true);
				newGame(choixLevel, MINIMAX);
			}
		});

		rbMenuItemAlgo[ALPHABETA].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				rbMenuItemAlgo[ALPHABETA].setSelected(true);
				newGame(choixLevel, ALPHABETA);
			}

		});


		rbMenuItemAlgo[NEGAMAX].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				rbMenuItemAlgo[NEGAMAX].setSelected(true);
				newGame(choixLevel, NEGAMAX);
			}

		});


		rbMenuItemAlgo[ALPHABETANEGAMAX].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				rbMenuItemAlgo[ALPHABETANEGAMAX].setSelected(true);
				newGame(choixLevel, ALPHABETANEGAMAX);
			}

		});

		rbMenuItemAlgo[SSS].addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				rbMenuItemAlgo[SSS].setSelected(true);
				newGame(choixLevel, SSS);
			}

		});


		// règle du jeu
		menuRegle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new Rule(ataxx.getFrame());
			}
		});

		menuApropos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new About(ataxx.getFrame());
			}
		});


		// new game
		menuNewGame.addMenuListener(new MenuListener(){
			@Override
			public void menuCanceled(MenuEvent arg0) {}

			@Override
			public void menuDeselected(MenuEvent arg0) {}

			@Override
			public void menuSelected(MenuEvent arg0) {
				int retour = JOptionPane.showConfirmDialog(null, 
						"Voulez-vous vraiment quitter partie ?",
						"Quitter",
						JOptionPane.YES_NO_OPTION);
				if (retour == JOptionPane.NO_OPTION) {
					//					System.exit(0);
				} else {
					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							new Attaxx(choixLevel, newAlgo).display();
						}
					});
					ataxx.close();
				}
			}

		});

		addBricks.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				ataxx.getBoard().getModel().setTakeenBrick(true);
			}
		});
	}

	private void updateItems() {
		rbMenuItemLevel[choixLevel-1].setSelected(true);
		rbMenuItemAlgo[algo].setSelected(true);
	}

	private void newGame(final int choixLevel, final int algo) {

		int retour = JOptionPane.showConfirmDialog(null, 
				"Ces paramètres ne s'appliqueront pas au jeu en cours," +
						" voulez-vous commencer une nouvelle partie ?",
						"Paramètres du jeu modifiés",
						JOptionPane.YES_NO_OPTION);
		
		if (retour == JOptionPane.YES_OPTION) {
			SwingUtilities.invokeLater(new Runnable() {
				public void run() {
					new Attaxx(choixLevel, algo).display();
				}
			});

			ataxx.close();
		}
	}
}
