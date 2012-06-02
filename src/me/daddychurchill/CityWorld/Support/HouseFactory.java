package me.daddychurchill.CityWorld.Support;

import java.util.Random;

import me.daddychurchill.CityWorld.Context.ContextData;

import org.bukkit.Material;

public final class HouseFactory {

	public final static void generateHouse(RealChunk chunk, ContextData context, int baseY, int maxFloors) {
		Random random = chunk.random;
		
		// what are we made of?
		Material matFloor = pickFloorMaterial(random);
		Material matWall = pickWallMaterial(random);
		Material matCeiling = pickCeilingMaterial(random);
		Material matRoof = pickRoofMaterial(random);
		int floors = random.nextInt(maxFloors) + 1;
		
		//TODO add doors
		//TODO add stairs
		//TODO add bed
		//TODO add kitchen
		//TODO add living room
		//TODO add split level house style
		
		// draw the house
		generateColonial(chunk, context, baseY, matFloor, matWall, matCeiling, matRoof, floors);
	}

	protected final static Material materialAir = Material.AIR;
	protected final static Material materialLight = Material.GLOWSTONE;
	protected final static Material materialGlass = Material.THIN_GLASS;
	
	// the description of a single room
	private final static class Room {
		public static final int MinSize = 5;
		public static final int MaxSize = 7;
		public static final int MissingRoomOdds = 12; // 1/n of the time a room is missing
		
		public boolean Missing;
		public int SizeX;
		public int SizeZ;
		
		public Room(Random random) {
			super();
			
			Missing = random.nextInt(MissingRoomOdds) == 0;
			SizeX = random.nextInt(MaxSize - MinSize) + MinSize;
			SizeZ = random.nextInt(MaxSize - MinSize) + MinSize;
		}
	}
	
	public final static void generateColonial(RealChunk chunk, ContextData context, int baseY, 
			Material matFloor, Material matWall, Material matCeiling, Material matRoof) {
		generateColonial(chunk, context, baseY, matFloor, matWall, matCeiling, matRoof, 1);
	}

	public final static void generateColonial(RealChunk chunk, ContextData context, int baseY, 
			Material matFloor, Material matWall, Material matCeiling, Material matRoof, int floors) {
		Random random = chunk.random;
		
		// what are the rooms like?
		boolean missingRoom = false;
		Room[][][] rooms = new Room[floors][2][2];
		for (int f = 0; f < floors; f++) {
			missingRoom = false;
			for (int x = 0; x < 2; x++) {
				for (int z = 0; z < 2; z++) {
					rooms[f][x][z] = new Room(random);
					
					// single floor is a little different
					if (floors == 1) {
						if (rooms[f][x][z].Missing) {
							if (!missingRoom)
								missingRoom = true;
							else
								rooms[f][x][z].Missing = false;
						}
					} else {
						
						// first floor must be complete
						if (f == 0)
							rooms[f][x][z].Missing = false;
						
						// each additional floors must include any missing rooms from below
						else if (rooms[f - 1][x][z].Missing)
							rooms[f][x][z].Missing = true;
						
						// only one new missing room per floor
						else if (rooms[f][x][z].Missing) {
							if (!missingRoom)
								missingRoom = true;
							else
								rooms[f][x][z].Missing = false;
						}
						
						// all rooms must be the same size (or smaller) than the one below it
						if (f > 0) {
							rooms[f][x][z].SizeX = Math.min(rooms[f][x][z].SizeX, rooms[f - 1][x][z].SizeX);
							rooms[f][x][z].SizeZ = Math.min(rooms[f][x][z].SizeZ, rooms[f - 1][x][z].SizeZ);
						}
					}
				}
			}
		}
		
		// where is the center of the house?
		int roomOffsetX = chunk.width / 2 + random.nextInt(2) - 1;
		int roomOffsetZ = chunk.width / 2 + random.nextInt(2) - 1;
		
		// draw the individual rooms
		for (int f = 0; f < floors; f++) {
			for (int x = 0; x < 2; x++) {
				for (int z = 0; z < 2; z++) {
					drawRoom(chunk, context, rooms, f, f == floors - 1, x, z, roomOffsetX, roomOffsetZ, baseY, matFloor, matWall, matRoof);
				}
			}
		}
		
		// extrude roof
		int roofY = baseY + floors * ContextData.FloorHeight - 1;
		for (int y = 0; y < ContextData.FloorHeight - 1; y++) {
			for (int x = 1; x < chunk.width - 1; x++) {
				for (int z = 1; z < chunk.width - 1; z++) {
					int yAt = y + roofY;
					if (chunk.getBlock(x - 1, yAt, z) != materialAir && chunk.getBlock(x + 1, yAt, z) != materialAir &&
						chunk.getBlock(x, yAt, z - 1) != materialAir && chunk.getBlock(x, yAt, z + 1) != materialAir) {
						chunk.setBlock(x, yAt + 1, z, matRoof);
					}
				}
			}
		}
		
		// carve out the attic
//		for (int y = 1; y < Generator.floorHeight - 1; y++) {
//			for (int x = 1; x < chunk.width - 1; x++) {
//				for (int z = 1; z < chunk.width - 1; z++) {
//					int yAt = y + roofY;
//					if (chunk.getBlock(x, yAt + 1, z) != materialAir) {
//						chunk.setBlock(x, yAt, z, materialAir);
//					}
//				}
//			}
//		}
	}
	
	public final static void drawRoom(RealChunk chunk, ContextData context, Room[][][] rooms, int floor, boolean topFloor, int x, int z, 
			int roomOffsetX, int roomOffsetZ, int baseY, 
			Material matFloor, Material matWall, Material matRoof) {

		//TODO I think this function suffers from the North = West problem... It sure does!
		// which room?
		Room room = rooms[floor][x][z];
		boolean northRoom = x != 0;
		boolean eastRoom = z != 0;
		
		// is there really something here?
		if (!room.Missing) {
			int x1 = roomOffsetX - (northRoom ? 0 : room.SizeX);
			int x2 = roomOffsetX + (northRoom ? room.SizeX : 0);
			int z1 = roomOffsetZ - (eastRoom ? 0 : room.SizeZ);
			int z2 = roomOffsetZ + (eastRoom ? room.SizeZ : 0);
			int y1 = baseY + floor * ContextData.FloorHeight;
			int y2 = y1 + ContextData.FloorHeight - 1;

			// draw the walls
			chunk.setBlocks(x1, x1 + 1, y1, y2, z1, z2 + 1, matWall);
			chunk.setBlocks(x2, x2 + 1, y1, y2, z1, z2 + 1, matWall);
			chunk.setBlocks(x1, x2 + 1, y1, y2, z1, z1 + 1, matWall);
			chunk.setBlocks(x1, x2 + 1, y1, y2, z2, z2 + 1, matWall);
			
			// add rug and roof
			chunk.setBlocks(x1, x2 + 1, y1 - 1, y1, z1, z2 + 1, matFloor);
			if (!topFloor) {
				chunk.setBlocks(x1, x2 + 1, y2, y2 + 1, z1, z2 + 1, matRoof);
				chunk.setBlocks(x1 + 1, x2 + 1 - 1, y2, y2 + 1, z1 + 1, z2 + 1 - 1, matRoof);
			} else
				chunk.setBlocks(x1 - 1, x2 + 2, y2, y2 + 1, z1 - 1, z2 + 2, matRoof);
			
			if (northRoom) {
				if (eastRoom) {
					chunk.setBlocks(x1 + 2, y1, 	y2 - 1, z1, 	materialAir); // west
					chunk.setBlocks(x1, 	y1, 	y2 - 1, z1 + 2, materialAir); // south
					chunk.setBlocks(x1 + 2, x2 - 1, y1 + 1, y2 - 1, z2, 	z2 + 1, materialGlass); // east
					chunk.setBlocks(x2, 	x2 + 1, y1 + 1, y2 - 1, z1 + 2, z2 - 1, materialGlass); // north
				} else {
					chunk.setBlocks(x1 + 2, y1, 	y2 - 1, z2, 	materialAir); // east
					chunk.setBlocks(x1, 	y1, 	y2 - 1, z2 - 2, materialAir); // south
					chunk.setBlocks(x1 + 2, x2 - 1, y1 + 1, y2 - 1, z1, 	z1 + 1, materialGlass); // west
					chunk.setBlocks(x2, 	x2 + 1,	y1 + 1, y2 - 1, z1 + 2, z2 - 1, materialGlass); // north
				}
			} else {
				if (eastRoom) {
					chunk.setBlocks(x2 - 2, y1, 	y2 - 1, z1, 	materialAir); // west
					chunk.setBlocks(x2, 	y1, 	y2 - 1, z1 + 2, materialAir); // north
					chunk.setBlocks(x1 + 2, x2 - 1, y1 + 1, y2 - 1, z2, 	z2 + 1, materialGlass); // east
					chunk.setBlocks(x1, 	x1 + 1, y1 + 1, y2 - 1, z1 + 2, z2 - 1, materialGlass); // south
				} else {
					chunk.setBlocks(x2 - 2, y1, 	y2 - 1, z2, 	materialAir); // east
					chunk.setBlocks(x2, 	y1, 	y2 - 1, z2 - 2, materialAir); // north
					chunk.setBlocks(x1 + 2, x2 - 1, y1 + 1, y2 - 1, z1, 	z1 + 1, materialGlass); // west
					chunk.setBlocks(x1, 	x1 + 1, y1 + 1, y2 - 1, z1 + 2, z2 - 1, materialGlass); // south
				}
			}
		}
	}
	
	public final static Material pickWallMaterial(Random random) {
		switch (random.nextInt(9)) {
		case 1:
			return Material.COBBLESTONE;
		case 2:
			return Material.MOSSY_COBBLESTONE;
		case 3:
			return Material.STONE;
		case 4:
			return Material.SMOOTH_BRICK;
		case 5:
			return Material.SANDSTONE;
		case 6:
			return Material.SAND;
		case 7:
			return Material.BRICK;
		case 8:
			return Material.CLAY;
		default:
			return Material.WOOD;
		}
	}

	public final static Material pickCeilingMaterial(Random random) {
		switch (random.nextInt(5)) {
		case 1:
			return Material.COBBLESTONE;
		case 2:
			return Material.STONE;
		case 3:
			return Material.SMOOTH_BRICK;
		case 4:
			return Material.SANDSTONE;
		default:
			return Material.WOOD;
		}
	}

	public final static Material pickFloorMaterial(Random random) {
		switch (random.nextInt(4)) {
		case 1:
			return Material.COBBLESTONE;
		case 2:
			return Material.STONE;
		case 3:
			return Material.WOOL;
		default:
			return Material.WOOD;
		}
	}

	public final static Material pickRoofMaterial(Random random) {
		switch (random.nextInt(6)) {
		case 1:
			return Material.COBBLESTONE;
		case 2:
			return Material.MOSSY_COBBLESTONE;
		case 3:
			return Material.STONE;
		case 4:
			return Material.SMOOTH_BRICK;
		case 5:
			return Material.SANDSTONE;
		default:
			return Material.WOOD;
		}
	}
}
