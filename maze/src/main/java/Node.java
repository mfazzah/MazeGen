
public class Node {
	// variables
	private Node left; // left, parent, right, in order of traversal
	private Node parent;
	private Node right;
	private Coordinate coord;

	// constructors
	public Node(Node left, Node parent, Node right, Coordinate coord) {
		this.left = left;
		this.parent = parent;
		this.right = right;
		this.coord = coord;
	}

	public Node(Coordinate coord) {
		this(null, null, null, coord);
	}

	public Node() {
		this(null, null, null, null);
	}

	//getters and setters
	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Coordinate getCoord() {
		return coord;
	}
	
	public String toString() {
		return coord.toString(); 
	}
	
	
}
