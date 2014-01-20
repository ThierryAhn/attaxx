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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((player == null) ? 0 : player.hashCode());
		result = prime * result + ((root == null) ? 0 : root.hashCode());
		result = prime * result + ((target == null) ? 0 : target.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Move other = (Move) obj;
		if (player == null) {
			if (other.player != null)
				return false;
		} else if (!player.equals(other.player))
			return false;
		if (root == null) {
			if (other.root != null)
				return false;
		} else if (!root.equals(other.root))
			return false;
		if (target == null) {
			if (other.target != null)
				return false;
		} else if (!target.equals(other.target))
			return false;
		return true;
	}

	public Cell getTarget() {
		return target;
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
