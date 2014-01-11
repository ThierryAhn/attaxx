package model;


public class Move implements Comparable<Move> {

	private String player;
	private Cell root;
	private Cell target;
	
	public Move(String player, Cell root, Cell target){
		this.player = player;
		this.root = root;
		this.target = target;
	}

	private Move(String player){
		this.player = player;
	}

	static Move getNewPassMove(String player){
		return new Move(player);
	}
	
	public String getPlayer() {
		return player;
	}

	public Cell getRoot() {
		return root;
	}

	public Cell getTarget() {
		return target;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Move){
			Move m = (Move)obj;
			return m.getRoot().equals(root) 
					&& m.getTarget().equals(target)
					&& m.getPlayer().equals(player);
			
		}
		return false;
	}
	
	@Override
	public String toString() {
		return player + " : "+ root +" to " + target;
	}

	@Override
	public int compareTo(Move m) {
		int comp = m.getRoot().compareTo(getRoot());
		if (comp == 0) {
			comp = m.getTarget().compareTo(getTarget());
			if (comp == 0) {
				comp = m.getPlayer().compareTo(getPlayer());
			}
		}
		return comp;
	}
}
