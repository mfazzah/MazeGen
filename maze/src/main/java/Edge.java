
public class Edge<T> {
	//variables 
	private T t1;
	private T t2; 
	
	//constructors
	public Edge(T t1, T t2) {
		this.t1 = t1; 
		this.t2 = t2; 
	}
	
	//getters
	public T getT1() {
		return t1; 
	}
	
	public T getT2() {
		return t2; 
	}
	
	//check if two edges are equal
	@Override 
	public boolean equals(Object o) {
		if (this.getClass() != o.getClass()) {
			return false; 
		}
		
		Edge e = (Edge) o;
		return (this.t1.equals(e.t1) && this.t2.equals(e.t2)) ||
			   (this.t1.equals(e.t2) && this.t2.equals(e.t1)); 
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append(t1.toString()); 
		sb.append(" -> "); 
		sb.append(t2.toString()); 
		return sb.toString(); 
	}

}
