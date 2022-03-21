import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//starter code for MazeSolver
//CST-201

public class Driver {
	
	// static cached reference to the maze, needed in order to find adjacent neighbors
	private static MazeCell[][] sMaze = null;
	public static int sNumRows = 0;
	public static int sNumCols = 0;
	
	// make sure maze coordinates are in the boundaries of the maze
	private static boolean inBounds(int rowIndx, int colIndx)
	{
		if ((rowIndx >= 0 && rowIndx < sNumRows) &&
		    (colIndx >= 0 && colIndx < sNumCols))
		{
			return true;
		}
		return false;	
	}
	
	// check coordinates to see if valid (within the maze), not a wall and has not been visited yet
	// return the neighbor if valid, otherwize null
	private static MazeCell checkNeighbor(int row, int col)
	{
		MazeCell neighbor = null;
		if (inBounds(row,col)) {                      // check if the cell is inbounds
			if (sMaze[row][col].getRow() != -1) {     // check if the cell is a wall (walls have coordinates (-1,-1))
				if (sMaze[row][col].unVisited()) {    // check if the cell has not been visited yet
					neighbor = sMaze[row][col];       // return the valid neighbor
				}
			}
		}
		return  neighbor;
	}
	
	// find valid adjacent cells that are in the boundary of the maze
	// 0 = wall, 1 = cooridoor, 3 is start, 4 is stop/finish
	private static List<MazeCell> getAdjacentNeighbors(MazeCell cell)
	{
		List<MazeCell> neighbors = new ArrayList<MazeCell>();
		while (cell.getDirection() != 4)   // 4 is complete
		{
			// directions:  0: north, 1: east, 2: south, 3: west, 4: complete
			int r = cell.getRow();
			int c = cell.getCol();
			if (cell.getDirection() == 0) {                   // 0 is north, row - 1
				MazeCell neighbor = checkNeighbor(r-1, c);
				if (neighbor != null) 
					neighbors.add(neighbor);
			}
			else if (cell.getDirection() == 1) {              // 1 is east, col + 1
				MazeCell neighbor = checkNeighbor(r, c+1);
				if (neighbor != null) 
					neighbors.add(neighbor);
			}
			else if (cell.getDirection() == 2) {              // 2 is south, row + 1
				MazeCell neighbor = checkNeighbor(r+1, c);
				if (neighbor != null) 
					neighbors.add(neighbor);
			}
			else if (cell.getDirection() == 3) {              // 3 is west, col - 1
				MazeCell neighbor = checkNeighbor(r, c-1);
				if (neighbor != null) 
					neighbors.add(neighbor);
			}
			// Advance the direction to point to the next neighbor
			cell.advanceDirection();
		}
		
		return neighbors;
	}

	/**
	 * 
	 * @param start
	 * @param end
	 * find a path through the maze
	 * if found, output the path from start to end
	 * if not path exists, output a message stating so
	 * 
	 */
	// implement this method
	// A Depth First Search (DFS) - utilizes Stacks
	// Note: will need access to the original maze (in addition to start and end cell) in order
	//       to determine adjacent neighbors that should be added to the stack.
	//
	// Algorithm for DFS
	// S is a stack
	// S.push(v)
	// while S is not empty
	//     v = S.pop()
	//     if v is not labeled as discovered:
	//         label v as discovered
	//         for all edges from v to w in G.getAdjacentEdges(v) do
	//            s.push(w)
	//  
	public static void depthFirst(MazeCell start, MazeCell end) {
		
		// this queue will hold the path through the maze from start to end
		boolean success = false;
		MyQueue<MazeCell> path = new MyQueue<MazeCell>();
		path.push(start);
		
		// this stack will be used to hold nodes for the DFS
		MyStack<MazeCell> stack = new MyStack<MazeCell>();
		stack.push(start);
		
		while (stack.empty() == false)
		{
			MazeCell cell = stack.pop();
			path.push(cell);           // record keeper for our path

			if (cell.equals(end))
			{
				success=true;          // we found the end node, record it and set success
				break;
			}
			
			if (cell.unVisited())
			{
				List<MazeCell> neighbors = getAdjacentNeighbors(cell);
				if (neighbors.isEmpty()) {
					// No neighbors, must be at the end of a path and didn't find the finish node
					// Reset our queue that is recording the completed path
					path = new MyQueue<MazeCell>();
					path.push(start);
				}
				else {
					for (MazeCell neighbor : neighbors) {
						stack.push(neighbor);
					}
				}
			}
		}
		
		// print out the results
		if (success){
			System.out.println("Success.  Found the following path from start" + start + " to the end" + end);
			System.out.print("The recorded path is: ");
			while(path.empty() == false)
			{
				System.out.print(path.pop() + " ");
			}
			System.out.println("");
		}
		else {
			System.out.println("There was no path found from the start" + start + " to the end" + end);
		}

	}

	public static void main(String[] args) throws FileNotFoundException {		
			
			//create the Maze from the file
			Scanner fin = new Scanner(new File("Maze.in"));
			//read in the rows and cols
			int rows = fin.nextInt();
			int cols = fin.nextInt();
			
			//create the maze
			int [][] grid = new int[rows][cols];
			
			//read in the data from the file to populate
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					grid[i][j] = fin.nextInt();
				}
			}

			//look at it 
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					if (grid[i][j] == 3)
						System.out.print("S ");	
					else if (grid[i][j] == 4)
						System.out.print("E ");	
					else
						System.out.print(grid[i][j] + " ");
				}
				System.out.println();
			}

			//make a 2-d array of cells
			MazeCell[][] cells = new MazeCell[rows][cols];
			
			//populate with MazeCell obj - default obj for walls

			MazeCell start = new MazeCell(), end = new MazeCell();
			
			//iterate over the grid, make cells and set coordinates
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					//make a new cell
					cells[i][j] = new MazeCell();
					//if it isn't a wall, set the coordinates
					if (grid[i][j] != 0) {
						cells[i][j].setCoordinates(i, j);
						//look for the start and end cells
						if (grid[i][j] == 3)
							start = cells[i][j];
						if (grid[i][j] == 4) 
							end = cells[i][j];
						
					}

				}
			}
			
			//testing
			System.out.println("start:"+start+" end:"+end);
			
			// setup some additional data needed for depthFirst search
			// in order to find adjacent neighbors
			sNumRows = rows;
			sNumCols = cols;
			sMaze = cells;
			
			//solve it!
			depthFirst(start, end);
			
		}
}



/*
 *
 * Provided starter code MazeCell class DO NOT CHANGE THIS CLASS
 *
 * models an open cell in a maze each cell knows its coordinates (row, col),
 * direction keeps track of the next unchecked neighbor\ cell is considered
 * 'visited' once processing moves to a neighboring cell the visited variable is
 * necessary so that a cell is not eligible for visits from the cell just
 * visited
 *
 */

class MazeCell {
	private int row, col;
	// direction to check next
	// 0: north, 1: east, 2: south, 3: west, 4: complete
	private int direction;
	private boolean visited;

	// set row and col with r and c
	public MazeCell(int r, int c) {
		row = r;
		col = c;
		direction = 0;
		visited = false;
	}

	// no-arg constructor
	public MazeCell() {
		row = col = -1;
		direction = 0;
		visited = false;
	}

	// copy constructor
	MazeCell(MazeCell rhs) {
		this.row = rhs.row;
		this.col = rhs.col;
		this.direction = rhs.direction;
		this.visited = rhs.visited;
	}

	public int getDirection() {
		return direction;
	}

	// update direction. if direction is 4, mark cell as visited
	public void advanceDirection() {
		direction++;
		if (direction == 4)
			visited = true;
	}

	// change row and col to r and c
	public void setCoordinates(int r, int c) {
		row = r;
		col = c;
	}

	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MazeCell other = (MazeCell) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}

	// set visited status to true
	public void visit() {
		visited = true;
	}

	// reset visited status
	public void reset() {
		visited = false;
	}

	// return true if this cell is unvisited
	public boolean unVisited() {
		return !visited;
	}

	// may be useful for testing, return string representation of cell
	public String toString() {
		return "(" + row + "," + col + ")";
	}
} // end of MazeCell class
