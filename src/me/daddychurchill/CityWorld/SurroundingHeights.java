package me.daddychurchill.CityWorld;

public class SurroundingHeights {
	
	//TODO refactor this to SurroundingLevels and SurroundingHeights & SurroundingDepths
	//TODO optimize the lookup logic to use an array of booleans after taking the SurroundingLots
	
	public int[][] heights;
	
	public SurroundingHeights() {
		super();
		heights = new int[3][3];
	}

	public void decrement() {
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				heights[x][z]--;
			}
		}
	}
	
//	private boolean[][] findNeighbors() {
//		boolean[][] neighbors = new boolean[3][3];
//
//		// copy the natural values
//		for (int x = 0; x < 3; x++) {
//			for (int z = 0; z < 3; z++) {
//				neighbors[x][z] = heights[x][z] > 0;
//			}
//		}
//		
//		// correct the corners
//		neighbors[2][0] = neighbors[2][0] && neighbors[1][0] && neighbors[2][1]; 
//		neighbors[2][2] = neighbors[2][2] && neighbors[1][2] && neighbors[2][1]; 
//		neighbors[0][0] = neighbors[0][0] && neighbors[1][0] && neighbors[0][1]; 
//		neighbors[0][2] = neighbors[0][2] && neighbors[0][1] && neighbors[1][2]; 
//		
//		// send it back up the line
//		return neighbors;
//	}
	
	public boolean toNorthWest() {
		return heights[2][0] > 0 && toNorth() && toWest();
	}

	public boolean toNorth() {
		return heights[2][1] > 0;
	}

	public boolean toNorthEast() {
		return heights[2][2] > 0 && toNorth() && toEast();
	}
	
	public boolean toWest() {
		return heights[1][0] > 0;
	}
	
	public boolean toCenter() {
		return true;
	}
	
	public boolean toEast() {
		return heights[1][2] > 0;
	}

	public boolean toSouthWest() {
		return heights[0][0] > 0 && toSouth() && toWest();
	}

	public boolean toSouth() {
		return heights[0][1] > 0;
	}

	public boolean toSouthEast() {
		return heights[0][2] > 0 && toSouth() && toEast();
	}
}
