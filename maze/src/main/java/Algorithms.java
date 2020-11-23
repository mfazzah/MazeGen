import java.util.*;

public class Algorithms {
	private static final int NUM_DIRECTIONS = 4;
	private static final Random RAND = new Random();

	// generate shuffled list of directions (up, down, left, right) as integers
	private static List<Integer> generateDirections() {
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i < 5; i++) {
			list.add(i);
		}
		Collections.shuffle(list);
		return list;
	}

	// prints grid of maze
	private static void printGrid(int[][] grid) {
		for (int i = 0; i < grid.length; i++) {
			for (int j = 0; j < grid[0].length; j++) {
				System.out.print(grid[i][j] + " ");
			}
			System.out.println();
		}
		System.out.println();
	}

	/* MAZE ALGORITHMS */
	// generate maze using depth-first recursive algorithm
	public static void depthFirstRecursive(int grid[][], int row, int col) {
		List<Integer> directions = generateDirections();
		for (int i = 0; i < NUM_DIRECTIONS; i++) {
			switch (directions.get(i)) {
			case 1: // up
				// check if two rows above is within the grid
				if (row - 2 <= 0)
					continue;

				if (grid[row - 2][col] == 1) {
					grid[row - 1][col] = 0;
					grid[row - 2][col] = 0;
					depthFirstRecursive(grid, row - 2, col);
				}
			case 2: // right
				// check if two cols to the right is within grid
				if (col + 2 >= grid[0].length - 1)
					continue;

				if (grid[row][col + 2] == 1) {
					grid[row][col + 1] = 0;
					grid[row][col + 2] = 0;
					depthFirstRecursive(grid, row, col + 2);

				}
			case 3: // down
				// check if two rows below is within grid
				if (row + 2 >= grid.length - 1)
					continue;

				if (grid[row + 2][col] == 1) {
					grid[row + 1][col] = 0;
					grid[row + 2][col] = 0;
					depthFirstRecursive(grid, row + 2, col);
				}
			case 4: // left
				// check if two cols to the left is within grid
				if (col - 2 <= 0)
					continue;

				if (grid[row][col - 2] == 1) {
					grid[row][col - 1] = 0;
					grid[row][col - 2] = 0;
					depthFirstRecursive(grid, row, col - 2);
				}
			}
		}
	}

	// generate maze using Eller's algorithm
	public static void Eller(int grid[][]) {
		int setGrid[][] = new int[(grid.length - 1) / 2][(grid[0].length - 1) / 2];
		int setCounter = 1;
		for (int i = 0; i < setGrid.length; i++) {
			// create the first row, then join any cells not members of a set to their own
			// unique set
			for (int j = 0; j < setGrid[0].length; j++) {
				if (setGrid[i][j] == 0) {
					setGrid[i][j] = setCounter;
					setCounter++;
				}
				grid[i * 2 + 1][j * 2 + 1] = 0;
			}
			// create right-walls, from left to right
			if (i < setGrid.length - 1) {
				for (int j = 0; j < setGrid[0].length - 1; j++) {
					boolean isJoin = RAND.nextBoolean();
					int k = j + 1;

					// if isJoin is true, the sets will be joined
					while (k < setGrid[0].length && isJoin && setGrid[i][k] != setGrid[i][k - 1]) {
						setGrid[i][k] = setGrid[i][k - 1];
						int x1 = k * 2 + 1;
						int x2 = (k - 1) * 2 + 1;
						grid[2 * i + 1][(x1 + x2) / 2] = 0;

						k++;
						isJoin = RAND.nextBoolean();
					}
					if (k < setGrid[0].length - 1 && setGrid[i][k] == setGrid[i][k - 1]) {
						int x1 = k * 2 + 1;
						int x2 = (k - 1) * 2 + 1;
						grid[2 * i + 1][(x1 + x2) / 2] = 1;
						grid[2 * (i + 1) + 1][(x1 + x2) / 2] = 1;

					}
					j = k - 1;
				}
				// create bottom walls, from left to right
				for (int j = 0; j < setGrid[0].length; j++) {
					boolean isHavingExit = false;
					boolean makeExit = RAND.nextBoolean(); // if true, exit made
					int k = j + 1;

					int y1 = 2 * i + 1;
					int y2 = 2 * (i + 1) + 1;
					boolean isJoin = RAND.nextBoolean();
					while (k < setGrid[0].length && isJoin && setGrid[i][k] == setGrid[i][j]) {
						if (makeExit) {
							isHavingExit = true;
							grid[(y1 + y2) / 2][k * 2 + 1] = 0;
							setGrid[i + 1][k] = setGrid[i][k];
						}
						makeExit = RAND.nextBoolean();
						isJoin = RAND.nextBoolean();
						k++;
					}
					if (!isHavingExit) {
						// when a set is the rightmost set
						if (j == grid[0].length - 1) {
							grid[(y1 + y2) / 2][j * 2 + 1] = 0;
							setGrid[i + 1][j] = setGrid[i][j];
						} else {
							int randomX = (int) ((Math.random() * (k - j)) + j);
							grid[(y1 + y2) / 2][randomX * 2 + 1] = 0;
							setGrid[i + 1][randomX] = setGrid[i][randomX];
						}
					}
					isHavingExit = false;
				}
			}
		}
		for (int j = 0; j < setGrid[0].length; j++) {
			int k = j + 1;
			while (k < setGrid[0].length && setGrid[setGrid.length - 1][k] != setGrid[setGrid.length - 1][k - 1]) {
				setGrid[setGrid.length - 1][k] = setGrid[setGrid.length - 1][k - 1];
				int x1 = k * 2 + 1;
				int x2 = (k - 1) * 2 + 1;
				grid[2 * (setGrid.length - 1) + 1][(x1 + x2) / 2] = 1;
			}
			j = k - 1;
		}
	}

	// generate maze using Kruskal's algorithm
	public static void Kruskal(int grid[][]) {
		Tree setGrid[][] = new Tree[(grid.length - 1) / 2][(grid[0].length - 1) / 2];
		Set<Edge> edges = new HashSet<>();

		// initialize the grid, this is a vector of buckets for each cell
		for (int i = 0; i < setGrid.length; i++) {
			for (int j = 0; j < setGrid[0].length; j++) {
				setGrid[i][j] = new Tree(new Coordinate(1 + (2 * j), 1 + (2 * i)));
			}
		}

		// condition for 3x3 grid
		if (grid.length == 3 && grid[0].length == 3) {
			grid[1][1] = 0;
			return;
		}

		// create a set with all connecting edges
		for (int i = 1; i < grid.length; i += 2) {
			for (int j = 1; j < grid[0].length; j += 2) {
				Coordinate current = new Coordinate(j, i);
				List<Coordinate> neighbors = getNeighbors(current, grid);
				for (Coordinate c : neighbors) {
					Edge edge = new Edge(current, c);
					if (!isContained(edges, edge)) // add edge if it isn't in the set
						edges.add(edge);
				}
			}
		}
		// while set of edges is not empty
		while (!edges.isEmpty()) {
			// randomly get an edge
			Edge edge = getRandElement(edges);
			Coordinate c1 = (Coordinate) edge.getT1();
			Coordinate c2 = (Coordinate) edge.getT2();

			int x1 = (c1.getX() - 1) / 2;
			int x2 = (c2.getX() - 1) / 2;
			int y1 = (c1.getY() - 1) / 2;
			int y2 = (c2.getY() - 1) / 2;
			int intX = (c1.getX() + c2.getX()) / 2;
			int intY = (c1.getY() + c2.getY()) / 2;

			// if cells not in the same bucket, connect and merge
			if (setGrid[y1][x1].getRoot() != setGrid[y2][x2].getRoot()) {
				setGrid[y1][x1].insert(setGrid[y2][x2].getRoot());
				update(setGrid[y1][x1].getRoot(), setGrid[y1][x1].getRoot(), setGrid);
				grid[y1 * 2 + 1][x1 * 2 + 1] = 0;
				grid[y2 * 2 + 1][x2 * 2 + 1] = 0;
				grid[intY][intX] = 0;
			}

			// remove edge from set
			edges.remove(edge);
		}

	}

	// generate maze using Prim's algorithm
	public static void Prim(int grid[][], int startX, int startY) {
		// choose arbitrary vertex from grid and add it to some empty set
		Coordinate start = new Coordinate(startX, startY);
		Set<Coordinate> maze = new TreeSet<>();
		Set<Coordinate> frontier = new TreeSet<>();

		// frontier is the set of cells not in the maze, but adjacent to a cell that is
		// in the maze
		frontier.addAll(getNeighbors(start, grid));
		maze.add(start);

		Coordinate chosen, adjacent, intermediate;

		// while frontier is not empty, choose a frontier cell at random
		while (!frontier.isEmpty()) {
			chosen = getRandElement(frontier);
			adjacent = getAdjacent(maze, chosen);

			// intermediate has chance to be same point as chosen, or a different point that
			// is also potentially adjacent to chosen
			intermediate = new Coordinate((chosen.getX() + adjacent.getX()) / 2, (chosen.getY() + adjacent.getY()) / 2);

			// checks to see which choice is made if chosen coordinate has two adjacents at
			// equal weights
			if (maze.contains(chosen) || maze.contains(intermediate)) {
				if (frontier.contains(chosen))
					frontier.remove(chosen);
				if (frontier.contains(intermediate))
					frontier.remove(intermediate);
				continue;
			}

			// add vertices to mst maze, and remove them from frontier set
			maze.add(chosen);
			maze.add(intermediate);
			frontier.addAll(getNeighbors(chosen, grid));
			frontier.remove(adjacent);
			frontier.remove(chosen);
			frontier.remove(intermediate);

			grid[chosen.getY()][chosen.getX()] = 0;
			grid[intermediate.getY()][intermediate.getX()] = 0;
		}
	}

	/* helper methods */
	// checks if set contains an object
	private static <T> boolean isContained(Set<T> set, T obj) {
		boolean isContained = false;
		for (T t : set) {
			if (t.equals(obj))
				isContained = true;
		}
		return isContained;
	}

	// updates grid with values at current point in the algorithm
	private static void update(Node n, Node root, Tree[][] grid) {
		if (n != null) {
			Coordinate c;

			if (n.getRight() != null) {
				c = n.getRight().getCoord();
				grid[(c.getY() - 1) / 2][(c.getX() - 1) / 2].setRoot(root);
			}

			if (n.getLeft() != null) {
				c = n.getLeft().getCoord();
				grid[(c.getY() - 1) / 2][(c.getX() - 1) / 2].setRoot(root);
			}
			update(n.getRight(), root, grid);
			update(n.getLeft(), root, grid);
		}
	}

	// returns a list of surrounding neighbors of a given coordinate in a grid
	private static List<Coordinate> getNeighbors(Coordinate c, int[][] grid) {
		List<Coordinate> neighbors = new ArrayList<>();
		int x = c.getX();
		int y = c.getY();

		if (grid[0].length > 3) {
			// coordinate has only right col neighbor
			if (x - 2 <= 0) {
				neighbors.add(new Coordinate(x + 2, y));
			} else if (x + 2 >= grid[0].length - 1) { // only left col neighbor
				neighbors.add(new Coordinate(x - 2, y));
			} else { // has two cols neighbors
				neighbors.add(new Coordinate(x + 2, y));
				neighbors.add(new Coordinate(x - 2, y));
			}
		}

		if (grid.length > 3) {
			if (y - 2 <= 0) { // coordinate has only right row neighbor
				neighbors.add(new Coordinate(x, y + 2));
			} else if (y + 2 >= grid.length - 1) { // only left row neighbor
				neighbors.add(new Coordinate(x, y - 2));
			} else { // two row neighbors
				neighbors.add(new Coordinate(x, y + 2));
				neighbors.add(new Coordinate(x, y - 2));
			}
		}
		// randomize neighbors
		Collections.shuffle(neighbors);
		return neighbors;
	}

	// returns random element from a set
	private static <T> T getRandElement(Set s) {
		Random rand = new Random();
		int index = rand.nextInt(s.size());
		Iterator<T> iter = s.iterator();

		for (int i = 0; i < index; i++) {
			iter.next();
		}
		return iter.next();
	}

	// returns coordinate that is adjacent to given coordinate
	private static Coordinate getAdjacent(Set<Coordinate> s, Coordinate c) {
		for (Coordinate coor : s) {
			boolean x = Math.abs(coor.getX() - c.getX()) == 2 && coor.getY() == c.getY();
			boolean y = Math.abs(coor.getY() - c.getY()) == 2 && coor.getX() == c.getX();

			if (x && !y || !x && y) { // coordinate is adjacent in this case
				return coor;
			}
		}
		return null;
	}
}
