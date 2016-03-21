//---------------------------------------------------------------80 columns---|


public class Grid {
	
	private int numRows, numCols;
	private Object grid[][];

	public Grid(int numRows, int numCols) {
		
		this.numRows = numRows;
		this.numCols = numCols;
		grid = new Object[numRows][numCols];
	}

	public Object elementAt(Location loc) {
		if (!inBounds(loc)) {
			throw new IndexOutOfBoundsException();
		}

		return grid[loc.getRow()][loc.getCol()];
	}

	public boolean inBounds(Location loc) {
		return (loc.getRow() >= 0 && loc.getRow() < numRows
				&& loc.getCol() >= 0 && loc.getCol() < numCols);
	}

	public int numCols() {
		return numCols;
	}

	public int numRows() {
		return numRows;
	}

	public Object randomElement() {
		int row = (int) (Math.random() * numRows);
		int col = (int) (Math.random() * numCols);
		return grid[row][col];
	}

	public void setElementAt(Location loc, Object obj) {
		if (!inBounds(loc)) {
			throw new IndexOutOfBoundsException();
		}

		grid[loc.getRow()][loc.getCol()] = obj;
	}
}