package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
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

/**
 * The view of the game Attaxx
 * 
 * @author Rachid ABALINE & Thierry Folabi AHOUNOU
 * 
 */
public class Attaxx {

	private int choixLevel;
	private JFrame frame;
	private GameBoard board;
	private PlayerAlgo algo;
	private AttaxxModel model;
	//private PlayerAlgo algor;

	private JMenuBar menuBar;
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

	final String algos[] = {"MiniMax",
			"AlphaBeta",
			"NegaMax",
			"AlphaBetaNegaMax",
	"SSS"};
	final String level[] = {"Facile",
			"Moyen",
	"Difficile"};

	/**
	 * Constructor
	 */
	public Attaxx() {
		createModel();
		createView();
		placeComponents();
		createController();
	}

	/**
	 * Creates a Model
	 */
	private void createModel() {
		choixLevel = 1;
		algo = new MiniMax(choixLevel);
		model = new AttaxxModel(7, 7, algo);
	}

	/**
	 * Creates the view
	 */
	private void createView() {
		ImageIcon img = new ImageIcon(getClass().getResource("/data/images/bluePion.png"));

		frame = new JFrame("Attaxx");
		frame.setIconImage(img.getImage());

		board = new GameBoard(model);
		menuBar = new JMenuBar();
		// menu parametres
		menuParam = new JMenu("Paramètres");
		menuParam.setIcon(new ImageIcon(getClass().getResource("/data/images/param.png")));

		rbMenuItemLevel = new JRadioButtonMenuItem[level.length];
		rbMenuItemAlgo = new JRadioButtonMenuItem[algos.length];

		// sous menu level
		subMenuItemLevel = new JMenu("Level");
		groupLevel = new ButtonGroup();
		for(int i = 0;i<level.length;i++){
			rbMenuItemLevel[i] = new JRadioButtonMenuItem(level[i]);
			groupLevel.add(rbMenuItemLevel[i]);
			subMenuItemLevel.add(rbMenuItemLevel[i]);
		}
		subMenuItemLevel.setIcon(new ImageIcon(getClass().getResource("/data/images/level.png")));
		menuParam.add(subMenuItemLevel);
		// sous menu algo
		subMenuItemAlgo = new JMenu("Algorithme");
		groupAlgo = new ButtonGroup();
		for(int i = 0;i<algos.length;i++){
			rbMenuItemAlgo[i] = new JRadioButtonMenuItem(algos[i]);
			groupAlgo.add(rbMenuItemAlgo[i]);
			subMenuItemAlgo.add(rbMenuItemAlgo[i]);
		}
		subMenuItemAlgo.setIcon(new ImageIcon(getClass().getResource("/data/images/logo.png")));
		menuParam.add(subMenuItemAlgo);
		
		
		// menu aide
		menuHelp = new JMenu("Aide");
		menuHelp.setIcon(new ImageIcon(getClass().getResource("/data/images/aide.png")));
		menuRegle = new JMenuItem("Règle du jeu");
		menuHelp.add(menuRegle);

		menuApropos = new JMenuItem("A propos !");
		menuHelp.add(menuApropos);

		// menu nouveau jeu
		menuNewGame = new JMenu("Nouveau jeu");
		menuNewGame.setIcon(new ImageIcon(getClass().getResource("/data/images/new.png")));

		menuBar.add(menuParam);
		menuBar.add(menuHelp);
		menuBar.add(Box.createHorizontalGlue()); 
		menuBar.add(menuNewGame);
		
		addBricks = new JMenuItem("Gérer Blocs");
		menuParam.add(addBricks);
		addBricks.setIcon(new ImageIcon(getClass().getResource("/data/images/blockLogo.png")));
		rbMenuItemLevel[choixLevel-1].setSelected(true);
		rbMenuItemAlgo[0].setSelected(true);
	}

	/**
	 * Places the components on the Frame
	 */
	private void placeComponents() {
		frame.add(board);
		frame.setJMenuBar(menuBar);
	}

	/**
	 * Creates the Controllers
	 */
	private void createController() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		rbMenuItemLevel[0].addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				choixLevel = 1;
				rbMenuItemLevel[0].setSelected(true);
			}
		});

		rbMenuItemLevel[1].addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				choixLevel = 2;
				rbMenuItemLevel[1].setSelected(true);
			}
		});

		rbMenuItemLevel[2].addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				choixLevel = 3;
				rbMenuItemLevel[2].setSelected(true);
			}
		});

		rbMenuItemAlgo[0].addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				algo = new MiniMax(choixLevel);
				model.setAlgo(algo);
				rbMenuItemAlgo[0].setSelected(true);
			}
		});

		rbMenuItemAlgo[1].addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				algo = new AlphaBeta(choixLevel);
				model.setAlgo(algo);
				rbMenuItemAlgo[1].setSelected(true);
			}

		});

		rbMenuItemAlgo[2].addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				algo = new NegaMax(choixLevel);
				model.setAlgo(algo);
				rbMenuItemAlgo[2].setSelected(true);
			}

		});

		rbMenuItemAlgo[3].addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				algo = new AlphaBetaNegaMax(choixLevel);
				model.setAlgo(algo);
				rbMenuItemAlgo[3].setSelected(true);
			}

		});

		rbMenuItemAlgo[4].addItemListener(new ItemListener(){
			@Override
			public void itemStateChanged(ItemEvent arg0) {
				algo = new SSS(choixLevel);
				model.setAlgo(algo);
				rbMenuItemAlgo[4].setSelected(true);
			}
		});

		// règle du jeu
		menuRegle.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new Rule(frame);
			}
		});

		menuApropos.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				new About(frame);
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
				board.reinit();
				board.getTable().repaint(3);
			}

		});
		
		addBricks.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				board.getModel().setTakeenBrick(true);
			}
		});
	}

	/**
	 * Displays the frame
	 */
	public void display() {
		frame.setResizable(false);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);	
	}

	/**
	 * Close the application
	 */
	public void close() {
		frame.setVisible(false);
		frame.dispose();
	}

	/**
	 * Main method
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				new Attaxx().display();
			}
		});
	}

	public JFrame getFrame() {
		return frame;
	}

	public GameBoard getBoard() {
		return board;
	}

	public void setBoard(GameBoard board) {
		this.board = board;
	}

}
