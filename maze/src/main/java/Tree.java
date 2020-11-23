
public class Tree {
	//variables 
	private Node root; 
	
	//constructors
	public Tree(Coordinate c) {
		this.root = new Node(null, null, null, c); 
	}
	
	public Tree() {
		this(null); 
	}
	
	//getters and setters
	public Node getRoot() {
		return root; 
	}
	
	public void setRoot(Node n) {
		this.root = n; 
	}
	
	//finds height of tree 
	public int height(Node n) {
		if (n == null) return 0; 
		if (n.getLeft() == null && n.getRight() == null) {
			return 1; //only root node exists
		} else if (n.getLeft() == null) {
			return 1 + height(n.getRight()); 
		} else if (n.getRight() == null) {
			return 1 + height(n.getLeft()); 
		} else {
			return 1 + height(n.getRight()) + height(n.getLeft()); 
		}
	}
	
	//insert nodes into tree 
	public void insert(Node n, Node root) {
		//checks if left and right child nodes are null, and inserts there if able
		if (root.getLeft() == null) { 
			root.setLeft(n);
		} else if (root.getRight() == null) {
			root.setRight(n);
		} else { //if both children are not null, insert in child with smaller height
			if (height(n.getLeft()) <= height(n.getRight())) {
				insert(n, root.getLeft()); 
			} else {
				insert(n, root.getRight()); 
			}
		}
	}
	
	public void insert(Node n) {
		insert(n, this.root); 
	}
}
