package model.algorithm;

import model.AttaxxModel;
import model.Move;
import model.Player;


public class Node {
	
	/**
	 * plus infinity
	 */
	public final static int PLUS_INFINITY=Integer.MAX_VALUE; 
	
	/**
	 * minus infinity
	 */
	public final static int MINUS_INFINITY=Integer.MIN_VALUE; 

	private AttaxxModel model;
	
	private int alpha = PLUS_INFINITY;
	private int beta  = MINUS_INFINITY;

	public Node(AttaxxModel model, Move move){
		this.model = model.simulateMove(move);
	}
	
	public AttaxxModel getModel() {
		return model;
	}

	public void setGame(AttaxxModel model) {
		this.model = model;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}

	public int getBeta() {
		return beta;
	}

	public void setBeta(int beta) {
		this.beta = beta;
	}

}
