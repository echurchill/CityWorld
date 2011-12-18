package me.daddychurchill.CityWorld.Support;

public class SurroundingFloors {
	
	//TODO refactor this to SurroundingLevels and SurroundingHeights & SurroundingDepths
	//TODO optimize the lookup logic to use an array of booleans after taking the SurroundingLots
	
	public int[][] floors;
	
	public SurroundingFloors() {
		super();
		floors = new int[3][3];
	}

	public void decrement() {
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				floors[x][z]--;
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
	
	public int getNeighborCount() {
		int result = 0;
		if (floors[0][1] > 0)
			result++;
		if (floors[1][0] > 0)
			result++;
		if (floors[2][1] > 0)
			result++;
		if (floors[1][2] > 0)
			result++;
		return result;
	}
	
	public boolean isRoundable() {
		if (toSouth()) {
			if (toWest()) {
				return !toNorth() && !toEast() && floorsSame(floorsToSouth(), floorsToWest());
			} else if (toEast()) {
				return !toNorth() && floorsSame(floorsToSouth(), floorsToEast());
			}
		} else if (toNorth()) {
			if (toWest()) {
				return !toEast() && floorsSame(floorsToNorth(), floorsToWest());
			} else if (toEast()) {
				return floorsSame(floorsToNorth(), floorsToEast());
			}
		}
		return false;
	}
	
	private boolean floorsSame(int other1, int other2) {
		return floors[1][1] == other1 && other1 == other2;
	}
	
	private int floorsToWest() {
		return floors[0][1];
	}
	
	private int floorsToEast() {
		return floors[2][1];
	}
	
	private int floorsToNorth() {
		return floors[1][0];
	}
	
	private int floorsToSouth() {
		return floors[1][2];
	}
	
	public boolean toNorthEast() {
		return floors[2][0] > 0 && toEast() && toNorth();
	}

	public boolean toEast() {
		return floors[2][1] > 0;
	}

	public boolean toSouthEast() {
		return floors[2][2] > 0 && toEast() && toSouth();
	}
	
	public boolean toNorth() {
		return floors[1][0] > 0;
	}
	
	public boolean toCenter() {
		return true;
	}
	
	public boolean toSouth() {
		return floors[1][2] > 0;
	}

	public boolean toNorthWest() {
		return floors[0][0] > 0 && toWest() && toNorth();
	}

	public boolean toWest() {
		return floors[0][1] > 0;
	}

	public boolean toSouthWest() {
		return floors[0][2] > 0 && toWest() && toSouth();
	}
}
