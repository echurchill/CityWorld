package me.daddychurchill.CityWorld.Support;

public final class SurroundingFloors extends Surroundings {

	// TODO refactor this to SurroundingLevels and SurroundingHeights &
	// SurroundingDepths
	// TODO optimize the lookup logic to use an array of booleans after taking the
	// SurroundingLots

	public final int[][] floors;
	private final boolean[][] neighbors;

	public SurroundingFloors() {
		super();
		floors = new int[3][3];
		neighbors = new boolean[3][3];
	}

	public void decrement() {
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				floors[x][z]--;
			}
		}
		update();
	}

	public void update() {

		// copy the natural values
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				neighbors[x][z] = floors[x][z] > 0;
			}
		}

		// correct the corners
		neighbors[2][0] = neighbors[2][0] && neighbors[1][0] && neighbors[2][1];
		neighbors[2][2] = neighbors[2][2] && neighbors[1][2] && neighbors[2][1];
		neighbors[0][0] = neighbors[0][0] && neighbors[1][0] && neighbors[0][1];
		neighbors[0][2] = neighbors[0][2] && neighbors[0][1] && neighbors[1][2];
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

	@Override
	public boolean toNorthEast() {
		return neighbors[2][0] && toEast() && toNorth();
	}

	@Override
	public boolean toEast() {
		return neighbors[2][1];
	}

	@Override
	public boolean toSouthEast() {
		return neighbors[2][2] && toEast() && toSouth();
	}

	@Override
	public boolean toNorth() {
		return neighbors[1][0];
	}

	@Override
	public boolean toCenter() {
		return true;
	}

	@Override
	public boolean toSouth() {
		return neighbors[1][2];
	}

	@Override
	public boolean toNorthWest() {
		return neighbors[0][0] && toWest() && toNorth();
	}

	@Override
	public boolean toWest() {
		return neighbors[0][1];
	}

	@Override
	public boolean toSouthWest() {
		return neighbors[0][2] && toWest() && toSouth();
	}
}
