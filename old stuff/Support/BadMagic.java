package me.daddychurchill.CityWorld.Support;

public class BadMagic {
	public enum Facing { // clockwise starting with South
		SOUTH(0), // 0 - increasing Z
		WEST(1),  // 1 - decreasing X
		NORTH(2), // 2 - decreasing Z 
		EAST(3);  // 3 - increasing X
		
		private byte data;
		private Facing(int d) {
			data = (byte) d;
		}
		public byte getData() {
			return data;
		}
	};

// Retired 12/28/15
//	public enum Ordinal { // clockwise starting with SouthWest (see above)
//		SOUTHWEST, // 0.5
//		NORTHWEST, // 1.5
//		NORTHEAST, // 2.5
//		SOUTHEAST  // 3.5
//	};

	public enum StairWell {
		NONE, CENTER, NORTHWEST, NORTHEAST, SOUTHWEST, SOUTHEAST, NORTH, SOUTH, WEST, EAST
	};

	public enum Stair {
		EAST, WEST, SOUTH, NORTH, EASTFLIP, WESTFLIP, SOUTHFLIP, NORTHFLIP;
		
		public byte getData() {
			return (byte) ordinal();
		}
	};

	public enum Door {
		WESTBYNORTHWEST, NORTHBYNORTHEAST, EASTBYSOUTHEAST, SOUTHBYSOUTHWEST,
		NORTHBYNORTHWEST, EASTBYNORTHEAST, SOUTHBYSOUTHEAST, WESTBYSOUTHWEST;
		
		public byte getData() {
			return (byte) ordinal();
		}
	};

	public enum TrapDoor {
		SOUTH(0), NORTH(1), EAST(2), WEST(3), // these are the same as Facing's values
		SOUTH_UP(4 + 0), NORTH_UP(4 + 1), EAST_UP(4 + 2), WEST_UP(4 + 3),
		TOP_SOUTH(8 + 0), TOP_NORTH(8 + 1), TOP_EAST(8 + 2), TOP_WEST(8 + 3),
		TOP_SOUTH_DOWN(8 + 4 + 0), TOP_NORTH_DOWN(8 + 4 + 1), TOP_EAST_DOWN(8 + 4 + 2), TOP_WEST_DOWN(8 + 4 + 3);
		
		private byte data;
		private TrapDoor(int d) {
			data = (byte) d;
		}
		public byte getData() {
			return data;
		}
	};
	
	public enum StoneSlab {
		STONE(0), SANDSTONE(1), 
		WOODSTONE(2), COBBLESTONE(3), 
		BRICK(4), STONEBRICK(5),
		
		STONEFLIP(8 + 0), SANDSTONEFLIP(8 + 1), 
		WOODSTONEFLIP(8 + 2), COBBLESTONEFLIP(8 + 3), 
		BRICKFLIP(8 + 4), STONEBRICKFLIP(8 + 5);
			
		private byte data;
		private StoneSlab(int d) {
			data = (byte) d;
		}
		public byte getData() {
			return data;
		}
	};
	
// Retired 12/28/15
//	public enum Wood {
//		OAK(0), SPRUCE(1), 
//		BIRCH(2), JUNGLE(3),
//		ACACIA(4), DARK_OAK(5);
//		
//		private byte data;
//		private Wood(int d) {
//			data = (byte) d;
//		}
//		public byte getData() {
//			return data;
//		}
//	};
	
// Retired 12/28/15
//	public enum WoodSlab {
//		OAK(0), SPRUCE(1), 
//		BIRCH(2), JUNGLE(3),
//		ACACIA(4), DARK_OAK(5),
//		
//		OAKFLIP(8 + 0), SPRUCEFLIP(8 + 1), 
//		BIRCHFLIP(8 + 2), JUNGLEFLIP(8 + 3),
//		ACACIAFLIP(8 + 4), DARK_OAKFLIP(8 + 5);
//			
//		private byte data;
//		private WoodSlab(int d) {
//			data = (byte) d;
//		}
//		public byte getData() {
//			return data;
//		}
//	};
	
	public enum Torch {
		EAST(1), WEST(2), SOUTH(3), NORTH(4), FLOOR(5);
		
		private byte data;
		private Torch(int d) {
			data = (byte) d;
		}
		public byte getData() {
			return data;
		}
	};
	
	public enum Vine {
		SOUTH(1), WEST(2), NORTH(4), EAST(8);
		
		private byte data;
		private Vine(int d) {
			data = (byte) d;
		}
		public byte getData() {
			return data;
		}
	};
	
	public enum General {
		NORTH(2), SOUTH(3), WEST(4), EAST(5);
		
		private byte data;
		private General(int d) {
			data = (byte) d;
		}
		public byte getData() {
			return data;
		}
	}

/* Ideas at one point but now we should be using MaterialData or something similar
 * wool (0 to 15 colors)
 * double slab (0 to 6 material type)
 * stone (0 to 2 material type)
 * silver fish (0 to 2 material type)
 * wood (0 to 2 material type)
	
 * chests (direction/orientation and inventory)
 * furnaces (direction/orientation and inventory)
 * dispensers (direction/orientation and inventory)
 * jukebox (inventory)
	
 * fence gates (direction/orientation)
 * torch (direction/orientation)
 * redstone torch (direction/orientation)
 * rail (direction/orientation)
 * powered rail (direction/orientation)
 * levers (direction/orientation)
 * buttons (direction/orientation)
 * sign posts (direction/orientation)
 * wall signs (direction/orientation)
 * bed (direction/orientation)
	
 * leaves (0 to 3 material type and decay info)
 * saplings (0 to 2 material type)
 * grass (0 to 2 material type)
 * cactus (up to 3 high)
 * sugar cane (up to 3 high)
 * farm land (0 to 8, where 8 is maximum wetness)
 * crop (0 to 7, where 7 is highest)
 * pumpkin stem (0 to 7, where 7 will create a pumpkin)
 * pumpkin (direction)
 * melon stem (0 to 7, where 7 will create a melon)
 * melon (direction)
	
 * huge brown mushroom (??)
 * huge red mushroom (??)
*/
}
