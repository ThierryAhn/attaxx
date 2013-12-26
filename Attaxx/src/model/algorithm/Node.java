package model.algorithm;

import model.AttaxxModel;
import model.Move;
import model.Player;


public class Node {
	
	/**
	 * plus infinity
	 */
	public final static int PLUS_INFINITY=1001; 
	
	/**
	 * minus infinity
	 */
	public final static int MINUS_INFINITY=-1001; 

	private AttaxxModel game;
	
	private int alpha = PLUS_INFINITY;
	private int beta  = MINUS_INFINITY;

	public Node(AttaxxModel game, Move move){
		this.game = game;
		if(game.getCurrentPlayer().equals(Player.BLUE))
		game.playMove(move);
	}
	
	public AttaxxModel getGame() {
		return game;
	}

	public void setGame(AttaxxModel game) {
		this.game = game;
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
