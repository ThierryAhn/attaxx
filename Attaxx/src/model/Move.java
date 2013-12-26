package model;


public class Move {

	private String player;
	private Cell root;
	private Cell target;
	private boolean isPass;
	
	public Move(String player, Cell root, Cell target){
		this.player = player;
		this.root = root;
		this.target = target;
		isPass = false;
	}

	private Move(String player){
		this.player = player;
		isPass = true;
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

	public boolean isPass() {
		return isPass;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Move){
			Move m = (Move)obj;
			return m.getRoot().equals(root) 
					&& m.getTarget().equals(target)
					&& m.getPlayer().equals(player)
					&& m.isPass() == isPass;
		}
		return false;
	}
}
