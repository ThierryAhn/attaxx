package view;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import model.AttaxxModel;
import model.algorithm.MiniMax;
import model.algorithm.PlayerAlgo;

/**
 * The view of the game Attaxx
 * 
 * @author Rachid ABALINE & Thierry Folabi AHOUNOU
 * 
 */
public class Attaxx {

	private JFrame frame;
	private GameBoard board;
	private MenuBar menuBar;

	private AttaxxModel model;
	//private PlayerAlgo algo;

	/**
	 * Constructor
	 */
	public Attaxx(PlayerAlgo algo) {
		createModel(algo);
		createView();
		placeComponents();
		createController();
	}

	/**
	 * Creates a Model
	 */
	private void createModel(PlayerAlgo algo) {
		//algo = new MiniMax(3);
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
				new Attaxx(new MiniMax(1)).display();
			}
		});
	}

}
