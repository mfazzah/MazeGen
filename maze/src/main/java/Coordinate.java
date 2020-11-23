
public class Coordinate implements Comparable {

	// variables
	private int x;
	private int y;

	public Coordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	@Override
	public int compareTo(Object o) {
		if (this.getClass() != o.getClass()) {
			return -1;
		}

		Coordinate otherCoordinate = (Coordinate) o;

		int compValue = Integer.compare(getX(), otherCoordinate.getX());
		if (compValue != 0) {
			return compValue;
		}
		return Integer.compare(getY(), otherCoordinate.getY());
	}

	@Override
	public boolean equals(Object o) {
		if (o == null) {
			return false;
		} else if (this.getClass() != o.getClass()) {
			return false;
		}

		Coordinate otherCoordinate = (Coordinate) o;
		return this.x == otherCoordinate.x && this.y == otherCoordinate.y;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append("(" + this.x + ", " + this.y + ")");
		return sb.toString(); 
	}
}
