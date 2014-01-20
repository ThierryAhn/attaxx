package model.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

import model.AttaxxModel;
import model.Move;
import model.MoveEnumerator;

/**
 * Classe mod�lisant le joueur de l'IA en basant sur l'algorithme SSS*
 * 
 * @author Rachid ABALINE & Thierry Folabi AHOUNOU
 *
 */
public class SSS implements PlayerAlgo {

	// ATTRIBUTS

	/**
	 * Profondeur maximale
	 */
	private int maxDepth;

	private TreeSet<NodeSSS> listG;
	// CONSTRUCTORS

	/**
	 * Constructeur
	 * 
	 * @param maxDepth Profondeur Maximale
	 */
	public SSS(int maxDepth) {
		this.maxDepth = maxDepth;
	}

	@Override
	public Move getNextMove(AttaxxModel model) {
		listG = new TreeSet<NodeSSS>();
		return sssAlgo(model);
	}

	private Move sssAlgo(AttaxxModel model){
		Move bestMove = null;
		NodeSSS root = new NodeSSS(model,PLUS_INFINITY);
		listG.add(root);
		// tant que la racine n'est pas r�solue
		while(!root.isResolved()){
			System.out.println(listG);
			// on extrait le premier noeud de la liste
			NodeSSS n = extractFirst();
			// si le noeud est vivant
			if (!n.isResolved()){
				if(n.depth >= maxDepth){
					n.setResolved();
					n.setValue(n.getModel().heuristic());
					listG.add(n);
				}else{
					MoveEnumerator me = new MoveEnumerator(); 
					// on r�cup�re la liste des mouvement possibles
					TreeSet<Move> listM = me.getPossibleMoves(n.getModel());
					Iterator<Move> i=listM.iterator();
					if (n.getModel().getCurrentPlayer().equals(MAX)){
						// prend tous les fils
						while(i.hasNext()){
							Move m = i.next();
							NodeSSS nodeSon = new NodeSSS(n, m);
							// on ajoute le noeud � la liste
							listG.add(nodeSon);
						}
					}else{ // si c'est min
						Move m = listM.iterator().next();
						NodeSSS nodeSon = new NodeSSS(n, m);
						// on ajoute le premier fils � gauche
						listG.add(nodeSon);
					}
				}
			}else { // n est r�solu
				if(n.getModel().getCurrentPlayer().equals(MIN)){
					// Ins�rer le p�re du noeud comme r�solu
					NodeSSS nFather = n.getFather();
					nFather.setResolved();
					nFather.setValue(n.getValue());
					listG.add(nFather);
					// on supprime tous les noeuds successeurs du p�re de n
					Iterator<NodeSSS> i = listG.iterator();
					List<NodeSSS> liN = new ArrayList<NodeSSS>();
					while(i.hasNext()){
						NodeSSS node = i.next();
						if(node.getFather().equals(nFather))
							liN.add(node);
					}
					listG.removeAll(liN);
					// si le p�re est le noeud racine
					if (nFather.getDepth() == 0){
						root.setResolved();
						root.setMove(n.getMove());
						break;
					}
				}else{ // n est de type Max
					NodeSSS nRightBrother = getRightBrother(n);
					if(nRightBrother != null){
						// on ajoute son fr�re droit comme vivant
						nRightBrother.setValue(n.getValue());
						listG.add(nRightBrother);
						// on supprime le noeud
						listG.remove(n);
					}else{
						// on ajoute son p�re comme r�solu
						NodeSSS nFather = n.getFather();
						nFather.setResolved();
						nFather.setValue(n.getValue());
						listG.add(nFather);
						// on supprime le noeud
						listG.remove(n);
						// si le p�re est le noeud racine
						if (nFather.getDepth() == 0){
							root.setResolved();
							root.setMove(n.getMove());
							break;
						}
					}
				}
			}
		}
		System.err.println(listG);
		System.out.println(root.getMove());
		return root.getMove();
	}

	/**
	 * Extraire le premier noeud de la liste
	 * @return un noeud
	 */
	private NodeSSS extractFirst(){
		NodeSSS first = listG.pollFirst();
		return first;
	}

	/**
	 * Retourne le premier fr�re � droit du noeud
	 *  
	 * @param n
	 * @return
	 */
	private NodeSSS getRightBrother(NodeSSS n){
		MoveEnumerator me = new MoveEnumerator(); 
		// on r�cup�re la liste des mouvement possible pour le joueur
		TreeSet<Move> listM = me.getPossibleMoves(n.getFather().getModel());
		List<Move> list = new ArrayList<Move>(listM);
		NodeSSS node = null;

		if(list.contains(n.getMove())){
			int index = list.indexOf(node);
			if(index < list.size() && index > 0){
				Move m = list.get(list.indexOf(node)+1);
				node = new NodeSSS(n.getFather(),m);
			}
		}
		return node;
	}

	/**
	 * Repr�sente le noeud de l'arbre de l'algorithme SSS*
	 * 
	 * @author Rachid ABALINE & Thierry Folabi AHOUNOU
	 *
	 */
	class NodeSSS implements Comparable<NodeSSS>{
		// ATTRIBUTS

		// Etat du noeud
		private boolean resolved = false;
		// le p�re du noeud
		private NodeSSS father;
		// Le model associ� au noeud
		private AttaxxModel model;
		// la valeur du noeud
		private int value;
		//le mouvement
		private Move move;
		// profondeur
		private int depth;

		// CONSTRUCTORS

		/**
		 * Constructeur
		 * 
		 * @param model Le model associ� au noeud
		 */
		public NodeSSS(AttaxxModel model, int value) {
			this.model = model;
			this.value = value;
		}

		/**
		 * Constructeur
		 * 
		 * @param model Le model associ� au noeud
		 * @param move le mouvement
		 * @param father le p�re du noeud
		 */
		public NodeSSS(NodeSSS father, Move move) {
			this.model = father.getModel().simulateMove(move);
			this.father = father;
			this.move = move;
			this.value = father.getValue();
			this.depth = father.getDepth() + 1;
		}

		// FONCTIONS

		/**
		 * Etat du noeud
		 * 
		 * @return 	true s'il est resolu
		 * 			false sinon
		 */
		public boolean isResolved() {
			return resolved;
		}

		/**
		 * Retourne le p�re du noeud
		 *
		 * @return
		 */
		public NodeSSS getFather() {
			return father;
		}

		/**
		 * retourne le modele du noeud
		 * @return
		 */
		public AttaxxModel getModel() {
			return model;
		}

		/**
		 * Retourne la valeur du noeud
		 * @return
		 */
		public int getValue() {
			return value;
		}

		/**
		 * Retourne le mouvement qui nous a amen� � ce model
		 * @return
		 */
		public Move getMove() {
			return move;
		}

		/**
		 * Retourne la profondeur du noeud
		 * @return
		 */
		public int getDepth() {
			return depth;
		}

		@Override
		public int compareTo(NodeSSS o) {
			int comp = getModel().compareTo(o.getModel());
			if (comp != 0){
				if (this.value < o.getValue()) 
					comp=1;
				else
					comp=-1;
			}
			return comp;
		}

		// METHODS

		/**
		 * Met le noeud en etat r�solu
		 * @param resolved
		 */
		public void setResolved() {
			this.resolved = true;
		}

		/**
		 * Affecte une nouvelle valeur au noeud
		 * @param value
		 */
		public void setValue(int value) {
			if (value < this.value)
				this.value = value;
		}

		public void setMove(Move move) {
			this.move = move;
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof NodeSSS){
				NodeSSS node = (NodeSSS)obj;
				return 	getModel().equals(node.getModel());
			}
			return false;
		}

		@Override
		public String toString() {
			return "("+super.toString().split("@")[1] + "," 
					+ (isResolved() ? "r" : "v") + "," + value + ")" 
					+ "Depth=" + getDepth() ;
		}
	}

}
