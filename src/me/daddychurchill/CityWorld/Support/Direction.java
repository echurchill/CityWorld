package me.daddychurchill.CityWorld.Support;

public class Direction {
	public enum Cardinal {
		NORTH, SOUTH, WEST, EAST
	};

	public enum Ordinal {
		NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST
	};

	public enum Stair {
		NORTH, SOUTH, EAST, WEST;
		
		public byte getData() {
			return (byte) ordinal();
		}
	};

	public enum Door {
		WESTBYSOUTHWEST, NORTHBYNORTHWEST, EASTBYNORTHEAST, SOUTHBYSOUTHEAST,
		SOUTHBYSOUTHWEST, WESTBYNORTHWEST, NORTHBYNORTHEAST, EASTBYSOUTHEAST;
		
		public byte getData() {
			return (byte) ordinal();
		}
	};

	public enum TrapDoor {
		EAST, WEST, NORTH, SOUTH;
		
		public byte getData() {
			return (byte) ordinal();
		}
	};
	
	public enum Ladder {
		EAST(2), WEST(3), NORTH(4), SOUTH(5);
		
		private byte data;
		private Ladder(int d) {
			data = (byte) d;
		}
		public byte getData() {
			return data;
		}
	};
	
	public enum Vine {
		EAST(1), SOUTH(2), WEST(4), NORTH(8);
		
		private byte data;
		private Vine(int d) {
			data = (byte) d;
		}
		public byte getData() {
			return data;
		}
	};
	
	//TODO wool (0 to 15 colors)
	//TODO slab (0 to 6 material type)
	//TODO double slab (0 to 6 material type)
	//TODO stone (0 to 2 material type)
	//TODO silver fish (0 to 2 material type)
	//TODO wood (0 to 2 material type)
	
	//TODO chests (direction/orientation and inventory)
	//TODO furnaces (direction/orientation and inventory)
	//TODO dispensers (direction/orientation and inventory)
	//TODO jukebox (inventory)
	
	//TODO fence gates (direction/orientation)
	//TODO torch (direction/orientation)
	//TODO redstone torch (direction/orientation)
	//TODO rail (direction/orientation)
	//TODO powered rail (direction/orientation)
	//TODO levers (direction/orientation)
	//TODO buttons (direction/orientation)
	//TODO sign posts (direction/orientation)
	//TODO wall signs (direction/orientation)
	//TODO bed (direction/orientation)
	
	//TODO leaves (0 to 3 material type and decay info)
	//TODO saplings (0 to 2 material type)
	//TODO grass (0 to 2 material type)
	//TODO cactus (up to 3 high)
	//TODO sugar cane (up to 3 high)
	//TODO farm land (0 to 8, where 8 is maximum wetness)
	//TODO crop (0 to 7, where 7 is highest)
	//TODO pumpkin stem (0 to 7, where 7 will create a pumpkin)
	//TODO pumpkin (direction)
	//TODO melon stem (0 to 7, where 7 will create a melon)
	//TODO melon (direction)
	
	//TODO huge brown mushroom (??)
	//TODO huge red mushroom (??)
}
