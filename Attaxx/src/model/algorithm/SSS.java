package model.algorithm;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

import model.AttaxxModel;
import model.Move;
import model.MoveEnumerator;

/**
 * Classe modélisant le joueur de l'IA en basant sur l'algorithme SSS*
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
		// tant que la racine n'est pas résolue
		while(!root.isResolved()){
			System.out.println(listG);
			// on extrait le premier noeud de la liste
			NodeSSS n = extractFirst();
			System.err.println(n);
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// si le noeud est vivant
			if (!n.isResolved()){
				if(n.depth >= maxDepth){
					n.setResolved();
					n.setValue(n.getModel().heuristic());
					listG.add(n);
				}else{
					MoveEnumerator me = new MoveEnumerator(); 
					// on récupère la liste des mouvement possibles
					Set<Move> listM = me.getPossibleMoves(n.getModel());
					Iterator<Move> i=listM.iterator();
					if (n.getModel().getCurrentPlayer().equals(MAX)){
						// prend tous les fils
						while(i.hasNext()){
							Move m = i.next();
							NodeSSS nodeSon = new NodeSSS(n, m);
							// on ajoute le noeud à la liste
							listG.add(nodeSon);
						}
					}else{ // si c'est min
						Move m = listM.iterator().next();
						NodeSSS nodeSon = new NodeSSS(n, m);
						// on ajoute le premier fils à gauche
						listG.add(nodeSon);
					}
				}
			}else { // n est résolu
				if(n.getModel().getCurrentPlayer().equals(MIN)){
					// Insérer le père du noeud comme résolu
					NodeSSS nFather = n.getFather();
					nFather.setResolved();
					nFather.setValue(n.getValue());
					listG.add(nFather);
					// on supprime tous les noeuds successeurs du père de n
					Iterator<NodeSSS> i = listG.iterator();
					while(i.hasNext()){
						NodeSSS node = i.next();
						if(node.getFather().equals(nFather))
							listG.remove(node);
					}
					// si le père est le noeud racine
					if (nFather.equals(root)){
						root.setResolved();
						break;
					}
				}else{ // n est de type Max
					NodeSSS nRightBrother = getRightBrother(n);
					// si le noeud à un frère à droite
					if(nRightBrother != null){
						// on ajoute son frère droit comme vivant
						nRightBrother.setValue(n.getValue());
						listG.add(nRightBrother);
						// on supprime le noeud
						listG.remove(n);
					}else{ // si il n'a pas de frère à droite
						// on ajoute son père comme résolu
						NodeSSS nFather = n.getFather();
						nFather.setResolved();
						nFather.setValue(n.getValue());
						listG.add(nFather);
						// on supprime le noeud
						listG.remove(n);
						// si le père est le noeud racine
						if (nFather.equals(root)){
							root.setResolved();
							break;
						}
					}
				}
			}
		}
		bestMove = listG.first().getMove();
		return bestMove;
	}

	/**
	 * Extraire le premier noeud de la liste
	 * @return un noeud
	 */
	private NodeSSS extractFirst(){
		NodeSSS first = listG.first();
		listG.remove(first);
		return first;
	}

	/**
	 * Retourne le premier frère à droit du noeud
	 *  
	 * @param n
	 * @return
	 */
	private NodeSSS getRightBrother(NodeSSS n){
		
		NodeSSS no = null;
		MoveEnumerator me = new MoveEnumerator(); 
		// on récupère la liste des mouvement possible pour le joueur
		Set<Move> listM = me.getPossibleMoves(n.getFather().getModel());
		Iterator<Move> i=listM.iterator();
		
		// tant qu'il y a de mouvements possibles
		int j = 0;
		Move m;
		while(i.hasNext()){
			System.out.print(j++ +" - ");
			m = i.next();
			if (m.equals(n.getMove()) && i.hasNext()){
				NodeSSS node = new NodeSSS(n.getFather(), i.next());
				no = node;
				break;
			}
		}
		
		return no;
		
		/*System.err.println("DEBUT");
		MoveEnumerator me = new MoveEnumerator(); 
		// on récupère la liste des mouvement possible pour le joueur
		Set<Move> listM = me.getPossibleMoves(n.getFather().getModel());
		Iterator<Move> i=listM.iterator();
		
		// tant qu'il y a de mouvements possibles
		Move m;
		while(i.hasNext()){
			m = i.next();
			if (m.equals(n.getMove())){
				System.err.println("move " +m);
				if(i.hasNext()){
					System.err.println("True");
					try {
						Thread.sleep(2000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					NodeSSS node = new NodeSSS(n.getFather(), i.next());
					return node;
				}
			}
		}
		System.err.println("End");
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}

	/**
	 * Représente le noeud de l'arbre de l'algorithme SSS*
	 * 
	 * @author Rachid ABALINE & Thierry Folabi AHOUNOU
	 *
	 */
	class NodeSSS implements Comparable<NodeSSS>{
		// ATTRIBUTS

		// Etat du noeud
		private boolean resolved = false;
		// le père du noeud
		private NodeSSS father;
		// Le model associé au noeud
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
		 * @param model Le model associé au noeud
		 */
		public NodeSSS(AttaxxModel model, int value) {
			this.model = model;
			this.value = value;
		}

		/**
		 * Constructeur
		 * 
		 * @param model Le model associé au noeud
		 * @param move le mouvement
		 * @param father le père du noeud
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
		 * Retourne le père du noeud
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
		 * Retourne le mouvement qui nous a amené à ce model
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
					comp=-1;
				else
					comp=1;
			}
			return comp;
		}

		// METHODS

		/**
		 * Met le noeud en etat résolu
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
