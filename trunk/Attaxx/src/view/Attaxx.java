package view;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.AttaxxModel;
import model.algorithm.AlphaBetaNegaMax;
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

	private JFrame frame;
	private GameBoard board;

	private AttaxxModel model;
	private PlayerAlgo algo;

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
		algo = new SSS(1);
		model = new AttaxxModel(7, 7, algo);
	}

	/**
	 * Creates the view
	 */
	private void createView() {
		frame = new JFrame("Attaxx");
		board = new GameBoard(model);

	}

	/**
	 * Places the components on the Frame
	 */
	private void placeComponents() {
		frame.add(board);
	}

	/**
	 * Creates the Controllers
	 */
	private void createController() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * Displays the frame
	 */
	public void display() {
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
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

}
