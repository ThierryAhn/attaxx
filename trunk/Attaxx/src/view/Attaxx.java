package view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

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

	private final static int MINIMAX = 0;
	private final static int ALPHABETA = 1;
	private final static int NEGAMAX = 2;
	private final static int ALPHABETANEGAMAX = 3;
	private final static int SSS = 4;
	
	private int choixLevel;
	private JFrame frame;
	private GameBoard board;
	private PlayerAlgo algo;
	private int algor;
	private AttaxxModel model;

	private MenuBar menuBar;

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
		createModel(1,MINIMAX);
		createView();
		placeComponents();
		createController();
	}
	
	public Attaxx(int choixlevel, int algo) {
		createModel(choixlevel, algo);
		createView();
		placeComponents();
		createController();
	}



	/**
	 * Creates a Model
	 */
	private void createModel(int choixlevel, int algorithme) {
		this.choixLevel = choixlevel;
		switch (algorithme) {
		case MINIMAX:
			algor = MINIMAX;
			this.algo = new MiniMax(choixlevel);
			model = new AttaxxModel(7, 7, algo);
			break;
		case ALPHABETA:
			algor = ALPHABETA;
			this.algo = new AlphaBeta(choixlevel);
			model = new AttaxxModel(7, 7, algo);
			break;
		case NEGAMAX:
			algor = NEGAMAX;
			this.algo = new NegaMax(choixlevel);
			model = new AttaxxModel(7, 7, algo);
			break;
		case ALPHABETANEGAMAX:
			algor = ALPHABETANEGAMAX;
			this.algo = new AlphaBetaNegaMax(choixlevel);
			model = new AttaxxModel(7, 7, algo);
			break;
		case SSS:
			algor = SSS;
			this.algo = new SSS(choixlevel);
			model = new AttaxxModel(7, 7, algo);
			break;
		default:
			break;
		}
		
	}

	/**
	 * Creates the view
	 */
	private void createView() {
		ImageIcon img = new ImageIcon(
				getClass().getResource("/data/images/bluePion.png"));

		frame = new JFrame("Attaxx");
		frame.setIconImage(img.getImage());

		board = new GameBoard(model);
		menuBar = new MenuBar(this);
		
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
//		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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

	public int getChoixLevel() {
		return choixLevel;
	}

	public void setChoixLevel(int choixLevel) {
		this.choixLevel = choixLevel;
	}

	public int getAlgor() {
		return algor;
	}

	public void setAlgor(int algor) {
		this.algor = algor;
	}

}
