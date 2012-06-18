package me.daddychurchill.CityWorld.Support;

import java.util.Random;

import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.Direction.Door;
import me.daddychurchill.CityWorld.Support.Direction.Ladder;
import me.daddychurchill.CityWorld.Support.Direction.Stair;
import me.daddychurchill.CityWorld.Support.Direction.TrapDoor;

import org.bukkit.Material;

public final class HouseFactory {
	
	public final static void generateShack(RealChunk chunk, ContextData context, int baseY) {
//		Random random = chunk.random;
		
		// what are we made of?
		Material matWall = Material.WOOD;
		Material matFloor = Material.WOOD;
		Material matCeiling = Material.WOOD;
		Material matRoof = Material.WOOD;
		
		//chunk.setWalls(2, 13, baseY, baseY + ContextData.FloorHeight, 2, 13, Material.WOOD);
		generateColonial(chunk, context, baseY, matFloor, matWall, matCeiling, matRoof, 1, 5, 5, false);
	}
	
	
	public final static void generateHouse(RealChunk chunk, ContextData context, int baseY, int maxFloors) {
		Random random = chunk.random;
		
		// what are we made of?
		Material matWall = pickWallMaterial(random);
		Material matFloor = pickFloorMaterial(random);
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
		generateColonial(chunk, context, baseY, matFloor, matWall, matCeiling, matRoof, floors, MinSize, MaxSize, true);
	}

	private final static Material materialAir = Material.AIR;
	private final static Material materialGlass = Material.GLASS;
	private final static Material materialFence = Material.FENCE;
	private final static Material materialStair = Material.WOOD_STAIRS;
	private final static Material materialUnderStairs = Material.WOOD;
	
	private static final int MinSize = 4;
	private static final int MaxSize = 6;
	private static final int MissingRoomOdds = 5; // 1/n of the time a room is missing
	
	// the description of a single room
	private final static class Room {
		public enum Style {BED, KITCHEN, DINING, ENTRY, LIVING}; 
		
		public int widthX;
		public int widthZ;
		public boolean missing;
		public boolean walls;
		public Style style; 
		
		public Room(boolean aMissing, int aWidthX, int aWidthZ, boolean aWalls, Style aStyle) {
			super();
			
			missing = aMissing;
			widthX = aWidthX;
			widthZ = aWidthZ;
			walls = aWalls;
			style = aStyle;
		}
		
		// where are we?
		boolean located;
		boolean roomEast;
		boolean roomSouth;
		int x1;
		int x2;
		int z1;
		int z2;
		int y1;
		int y2;
		
		protected void Locate(ContextData context, int floor, int floors, 
				int x, int z, int roomOffsetX, int roomOffsetZ, int baseY) {
			if (!located) {
				located = true;
				roomEast = x != 0;
				roomSouth = z != 0;
				x1 = roomOffsetX - (roomEast ? 0 : widthX);
				x2 = roomOffsetX + (roomEast ? widthX : 0);
				z1 = roomOffsetZ - (roomSouth ? 0 : widthZ);
				z2 = roomOffsetZ + (roomSouth ? widthZ : 0);
				y1 = baseY + floor * ContextData.FloorHeight;
				y2 = y1 + ContextData.FloorHeight - 1;
			}
		}
		
		protected void DrawWalls(RealChunk chunk, ContextData context, int floor, int floors, 
				int x, int z, int roomOffsetX, int roomOffsetZ, int baseY, 
				Material matWall) {
			
			// find ourselves
			Locate(context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY);
			
			// draw the walls
			if (roomEast) {
				chunk.setBlocks(x2, x2 + 1, y1, 	y2, 	z1, 	z2 + 1, matWall); // east wall
				chunk.setBlocks(x2, x2 + 1, y1 + 1, y2 - 1, z1 + 1, z2    , materialGlass); // eastern window
				
				if (roomSouth) {
					chunk.setBlocks(x1, 	x2 + 1, y1, 	y2, 	z2, z2 + 1, matWall); // south wall
					chunk.setBlocks(x1 + 1, x2    , y1 + 1, y2 - 1, z2, z2 + 1, materialGlass); // southern window

					chunk.setBlocks(x1, x2 + 1, y1, y2,	z1, z1 + 1, matWall); // north wall
					chunk.setBlocks(x1, x1 + 1, y1, y2, z1, z2 + 1, matWall); // west wall
					
				} else {
					chunk.setBlocks(x1, 	x2 + 1, y1, 	y2, 	z1, z1 + 1, matWall); // north wall
					chunk.setBlocks(x1 + 1, x2    , y1 + 1, y2 - 1, z1,	z1 + 1, materialGlass); // northern window
					
					chunk.setBlocks(x1, x2 + 1, y1, y2, z2, z2 + 1, matWall); // south wall
					chunk.setBlocks(x1, x1 + 1, y1, y2, z1, z2 + 1, matWall); // west wall
					
				}
			} else {
				chunk.setBlocks(x1, x1 + 1, y1, 	y2, 	z1, 	z2 + 1, matWall); // west wall
				chunk.setBlocks(x1, x1 + 1, y1 + 1, y2 - 1, z1 + 1, z2    , materialGlass); // western window
				
				if (roomSouth) {
					chunk.setBlocks(x1, 	x2 + 1, y1, 	y2, 	z2, z2 + 1, matWall); // south wall
					chunk.setBlocks(x1 + 1, x2    , y1 + 1, y2 - 1, z2, z2 + 1, materialGlass); // southern window

					chunk.setBlocks(x1, x2 + 1, y1, y2, z1, z1 + 1, matWall); // north wall
					chunk.setBlocks(x2, x2 + 1, y1, y2, z1, z2 + 1, matWall); // east wall
					
				} else {
					chunk.setBlocks(x1, 	x2 + 1, y1, 	y2, 	z1, z1 + 1, matWall); // north wall
					chunk.setBlocks(x1 + 1, x2    , y1 + 1, y2 - 1, z1, z1 + 1, materialGlass); // northern window
					
					chunk.setBlocks(x1, x2 + 1, y1, y2, z2, z2 + 1, matWall); // south wall
					chunk.setBlocks(x2, x2 + 1, y1, y2, z1, z2 + 1, matWall); // east wall
				}
			}
		}
		
		protected void DrawFloor(RealChunk chunk, ContextData context, int floor, int floors, 
				int x, int z, int roomOffsetX, int roomOffsetZ, int baseY, 
				Material matFloor) {
			
			// find ourselves
			Locate(context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY);
			
			// put the rug down
			chunk.setBlocks(x1, x2 + 1, y1 - 1, y1, z1, z2 + 1, matFloor);
		}
		
		protected void DrawCeiling(RealChunk chunk, ContextData context, int floor, int floors, 
				int x, int z, int roomOffsetX, int roomOffsetZ, int baseY, 
				Material matCeiling) {
			
			// find ourselves
			Locate(context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY);
			
			// put the rug down
			chunk.setBlocks(x1, x2 + 1, y2, y2 + 1, z1, z2 + 1, matCeiling);
		}
		
		protected void DrawRoof(RealChunk chunk, ContextData context, int floor, int floors, 
				int x, int z, int roomOffsetX, int roomOffsetZ, int baseY, 
				Material matRoof) {
			
			// find ourselves
			Locate(context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY);
			
			// put roof on top
			//TODO need fancier roofs
			chunk.setBlocks(x1, x2 + 1, y2, y2 + 1, z1, z2 + 1, matRoof);
		}

		protected void DrawRailing(RealChunk chunk) {
			
			// only if we have found ourselves
			if (located) {
				
				// north and south ones
				for (int x = x1; x <= x2; x++) {
					chunk.setEmptyBlock(x, y2 + 1, z1, materialFence);
					chunk.setEmptyBlock(x, y2 + 1, z2, materialFence);
				}
				
				// west and east ones
				for (int z = z1; z <= z2; z++) {
					chunk.setEmptyBlock(x1, y2 + 1, z, materialFence);
					chunk.setEmptyBlock(x2, y2 + 1, z, materialFence);
				}
			}
		}

		protected void DrawStyle(RealChunk chunk, ContextData context, int floor, int floors, 
				int x, int z, int roomOffsetX, int roomOffsetZ, int baseY) {
			
			// which door or halls do we do?
			boolean doorNorth = false;
			boolean doorSouth = false;
			boolean doorWest = false;
			boolean doorEast = false;
			boolean hallNorth = walls;
			boolean hallSouth = walls;
			boolean hallWest = walls;
			boolean hallEast = walls;
			
			// find ourselves
			Locate(context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY);
			
			// our bits!
			switch (style) {
			case KITCHEN:
				
				// where is the door?
				if (chunk.random.nextBoolean()) {
					doorNorth = !roomSouth;
					doorSouth = roomSouth;
				} else {
					doorWest = !roomEast;
					doorEast = roomEast;
				}
				
//				chunk.setBlock((x2 - x1) / 2 + x1, y1, (z2 - z1) / 2 + z1, Material.CHEST);
				break;
			case DINING:
				
//				chunk.setBlock((x2 - x1) / 2 + x1, y1, (z2 - z1) / 2 + z1, Material.FENCE);
//				chunk.setBlock((x2 - x1) / 2 + x1, y1 + 1, (z2 - z1) / 2 + z1, Material.WOOD_PLATE);
				break;
			case ENTRY:
				
				// where is the door?
				if (floor == 0) {
					if (chunk.random.nextBoolean()) {
						doorNorth = !roomSouth;
						doorSouth = roomSouth;
					} else {
						doorWest = !roomEast;
						doorEast = roomEast;
					}
				}
				
				// below the top floor
				if (floor < floors - 1) {
					if (roomEast) {
						if (roomSouth) {
							chunk.setBlocks(x1 + 1, x2, y2, z1 + 1, z1 + 2, materialAir);

							chunk.setBlock(x1 + 1, y1 + 3, z1 + 1, materialUnderStairs);

							chunk.setStair(x1 + 2, y1 + 3, z1 + 1, materialStair, Stair.WEST);
							chunk.setStair(x1 + 2, y1 + 2, z1 + 1, materialStair, Stair.EASTFLIP);

							chunk.setStair(x1 + 3, y1 + 2, z1 + 1, materialStair, Stair.WEST);
							chunk.setStair(x1 + 3, y1 + 1, z1 + 1, materialStair, Stair.EASTFLIP);
							
							chunk.setStair(x1 + 4, y1 + 1, z1 + 1, materialStair, Stair.WEST);
							chunk.setStair(x1 + 4, y1    , z1 + 1, materialStair, Stair.EASTFLIP);

							chunk.setBlock(x1 + 5, y1    , z1 + 1, materialUnderStairs);
							chunk.setStair(x1 + 5, y1    , z1 + 2, materialStair, Stair.NORTH);
							
//							chunk.setBlocks(x1 + 3, y1, y2 + 50, z1 + 3, Material.FENCE);
//							chunk.setBlock(x1 + 3, y2 + 50, z1 + 3, Material.GOLD_BLOCK);
						} else {
							chunk.setBlocks(x1 + 1, x1 + 2, y2, z1 + 1, z2, materialAir);

							chunk.setBlock(x1 + 1, y1 + 3, z2 - 1, materialUnderStairs);

							chunk.setStair(x1 + 1, y1 + 3, z2 - 2, materialStair, Stair.SOUTH);
							chunk.setStair(x1 + 1, y1 + 2, z2 - 2, materialStair, Stair.NORTHFLIP);

							chunk.setStair(x1 + 1, y1 + 2, z2 - 3, materialStair, Stair.SOUTH);
							chunk.setStair(x1 + 1, y1 + 1, z2 - 3, materialStair, Stair.NORTHFLIP);
							
							chunk.setStair(x1 + 1, y1 + 1, z2 - 4, materialStair, Stair.SOUTH);
							chunk.setStair(x1 + 1, y1    , z2 - 4, materialStair, Stair.NORTHFLIP);

							chunk.setBlock(x1 + 1, y1    , z2 - 5, materialUnderStairs);
							chunk.setStair(x1 + 2, y1    , z2 - 5, materialStair, Stair.WEST);
							
//							chunk.setBlocks(x1 + 3, y1, y2 + 50, z1 + 3, Material.FENCE);
//							chunk.setBlock(x1 + 3, y2 + 50, z1 + 3, Material.LAPIS_BLOCK);
						}
					} else {
						if (roomSouth) {
							chunk.setBlocks(x2 - 1, x2, y2, z1 + 1, z2, materialAir);

							chunk.setBlock(x2 - 1, y1 + 3, z1 + 1, materialUnderStairs);

							chunk.setStair(x2 - 1, y1 + 3, z1 + 2, materialStair, Stair.NORTH);
							chunk.setStair(x2 - 1, y1 + 2, z1 + 2, materialStair, Stair.SOUTHFLIP);

							chunk.setStair(x2 - 1, y1 + 2, z1 + 3, materialStair, Stair.NORTH);
							chunk.setStair(x2 - 1, y1 + 1, z1 + 3, materialStair, Stair.SOUTHFLIP);
							
							chunk.setStair(x2 - 1, y1 + 1, z1 + 4, materialStair, Stair.NORTH);
							chunk.setStair(x2 - 1, y1    , z1 + 4, materialStair, Stair.SOUTHFLIP);

							chunk.setBlock(x2 - 1, y1    , z1 + 5, materialUnderStairs);
							chunk.setStair(x2 - 2, y1    , z1 + 5, materialStair, Stair.EAST);
							
//							chunk.setBlocks(x1 + 3, y1, y2 + 50, z1 + 3, Material.FENCE);
//							chunk.setBlock(x1 + 3, y2 + 50, z1 + 3, Material.DIAMOND_BLOCK);
						} else {
							chunk.setBlocks(x1 + 1, x2, y2, z2 - 1, z2, materialAir);

							chunk.setBlock(x2 - 1, y1 + 3, z2 - 1, materialUnderStairs);

							chunk.setStair(x2 - 2, y1 + 3, z2 - 1, materialStair, Stair.EAST);
							chunk.setStair(x2 - 2, y1 + 2, z2 - 1, materialStair, Stair.WESTFLIP);

							chunk.setStair(x2 - 3, y1 + 2, z2 - 1, materialStair, Stair.EAST);
							chunk.setStair(x2 - 3, y1 + 1, z2 - 1, materialStair, Stair.WESTFLIP);
							
							chunk.setStair(x2 - 4, y1 + 1, z2 - 1, materialStair, Stair.EAST);
							chunk.setStair(x2 - 4, y1    , z2 - 1, materialStair, Stair.WESTFLIP);

							chunk.setBlock(x2 - 5, y1    , z2 - 1, materialUnderStairs);
							chunk.setStair(x2 - 5, y1    , z2 - 2, materialStair, Stair.SOUTH);
							
//							chunk.setBlocks(x1 + 3, y1, y2 + 50, z1 + 3, Material.FENCE);
//							chunk.setBlock(x1 + 3, y2 + 50, z1 + 3, Material.GLOWSTONE);
						}
					}
				}
				
				// above the bottom floor
				if (floor > 0) {
					if (roomEast) {
						if (roomSouth) {
							hallNorth = false;
							
						} else {
							hallWest = false;

						}
					} else {
						if (roomSouth) {
							hallEast = false;
	
						} else {
							hallSouth = false;
						
						}
					}
				}
				
				// the top floor
				if (floor == floors - 1) {
					if (roomEast) {
						if (roomSouth) {
							chunk.setLadder(x1 + 1, y1, y1 + 3, z1 + 1, Ladder.SOUTH);
							chunk.setTrapDoor(x1 + 1, y2, z1 + 1, TrapDoor.SOUTH);
							
						} else {
							chunk.setLadder(x1 + 1, y1, y1 + 3, z2 - 1, Ladder.EAST);
							chunk.setTrapDoor(x1 + 1, y2, z2 - 1, TrapDoor.EAST);

						}
					} else {
						if (roomSouth) {
							chunk.setLadder(x2 - 1, y1, y1 + 3, z1 + 1, Ladder.WEST);
							chunk.setTrapDoor(x2 - 1, y2, z1 + 1, TrapDoor.WEST);
	
						} else {
							chunk.setLadder(x2 - 1, y1, y1 + 3, z2 - 1, Ladder.NORTH);
							chunk.setTrapDoor(x2 - 1, y2, z2 - 1, TrapDoor.NORTH);
						
						}
					}
				}
				
				break;
			case LIVING:
				
//				chunk.setBlock((x2 - x1) / 2 + x1, y1, (z2 - z1) / 2 + z1, Material.DIAMOND_BLOCK);
				break;
			case BED:
//				chunk.setBlock((x2 - x1) / 2 + x1, y1, (z2 - z1) / 2 + z1, Material.WOOL);
				break;
			}
			
			// draw the walls
			if (roomEast) {
				if (roomSouth) {
					if (doorSouth)
						chunk.setWoodenDoor(x1 + 3,	y1, z2, Door.SOUTHBYSOUTHEAST);
					if (doorEast)
						chunk.setWoodenDoor(x2, y1, z1 + 3, Door.EASTBYSOUTHEAST);

					if (hallNorth)
						chunk.setWoodenDoor(x1 + 2,	y1, z1,	Door.NORTHBYNORTHWEST); 
					if (hallWest)
						chunk.setWoodenDoor(x1, y1, z1 + 2, Door.WESTBYNORTHWEST); 
					
				} else {
					if (doorNorth)
						chunk.setWoodenDoor(x1 + 3, y1, z1, Door.NORTHBYNORTHEAST); 
					if (doorEast)
						chunk.setWoodenDoor(x2, y1, z2 - 3, Door.EASTBYNORTHEAST); 

					if (hallSouth)
						chunk.setWoodenDoor(x1 + 2, y1, z2, Door.SOUTHBYSOUTHWEST); 
					if (hallWest)
						chunk.setWoodenDoor(x1, y1, z2 - 2, Door.WESTBYSOUTHWEST); 
					
				}
			} else {
				if (roomSouth) {
					if (doorSouth)
						chunk.setWoodenDoor(x2 - 3, y1, z2, Door.SOUTHBYSOUTHWEST); 
					if (doorWest)
						chunk.setWoodenDoor(x1, y1, z1 + 3, Door.WESTBYSOUTHWEST); 

					if (hallNorth)
						chunk.setWoodenDoor(x2 - 2, y1, z1, Door.NORTHBYNORTHEAST); 
					if (hallEast)
						chunk.setWoodenDoor(x2, y1, z1 + 2, Door.EASTBYNORTHEAST); 
					
				} else {
					if (doorNorth)
						chunk.setWoodenDoor(x2 - 3, y1, z1, Door.NORTHBYNORTHWEST); 
					if (doorWest)
						chunk.setWoodenDoor(x1, y1, z2 - 3, Door.WESTBYNORTHWEST); 

					if (hallSouth)
						chunk.setWoodenDoor(x2 - 2, y1, z2, Door.SOUTHBYSOUTHEAST); 
					if (hallEast)
						chunk.setWoodenDoor(x2, y1, z2 - 2, Door.EASTBYSOUTHEAST); 
				}
			}
		}
	}
	
	private final static int getRoomWidth(Random random, int minRoomWidth, int maxRoomWidth) {
		return random.nextInt(maxRoomWidth - minRoomWidth + 1) + minRoomWidth;
	}
	
	private final static void generateColonial(RealChunk chunk, ContextData context, int baseY, 
			Material matFloor, Material matWall, Material matCeiling, Material matRoof, 
			int floors, int minRoomWidth, int maxRoomWidth, boolean allowMissingRooms) {
		Random random = chunk.random;
		
		// what are the rooms like?
		Room[][][] rooms = new Room[floors][2][2];
		for (int f = 0; f < floors; f++) {
			boolean missingRoom = false;
			for (int x = 0; x < 2; x++) {
				for (int z = 0; z < 2; z++) {
					
					// missing rooms?
					boolean thisRoomMissing = false;
					if (allowMissingRooms && floors > 1) {
						thisRoomMissing = random.nextInt(MissingRoomOdds) == 0;
					}
					
					// what does the room "look" like?
					int thisRoomWidthZ = getRoomWidth(random, minRoomWidth, maxRoomWidth);
					int thisRoomWidthX = getRoomWidth(random, minRoomWidth, maxRoomWidth);
					boolean thisRoomHasWalls = true;
					Room.Style thisRoomStyle = Room.Style.BED;
					
					// create the room
					rooms[f][x][z] = new Room(thisRoomMissing,
											  thisRoomWidthZ, thisRoomWidthX,
											  thisRoomHasWalls, thisRoomStyle);
					
					// single floor is a little different
					if (floors == 1) {
						if (rooms[f][x][z].missing) {
							if (!missingRoom)
								missingRoom = true;
							else
								rooms[f][x][z].missing = false;
						}
					} else {
						
						// first floor must be complete
						if (f == 0)
							rooms[f][x][z].missing = false;
						
						// each additional floors must include any missing rooms from below
						else if (rooms[f - 1][x][z].missing)
							rooms[f][x][z].missing = true;
						
						// only one new missing room per floor
						else if (rooms[f][x][z].missing) {
							if (!missingRoom)
								missingRoom = true;
							else
								rooms[f][x][z].missing = false;
						}
						
						// all rooms must be the same size (or smaller) than the one below it
						if (f > 0) {
							rooms[f][x][z].widthX = Math.min(rooms[f][x][z].widthX, rooms[f - 1][x][z].widthX);
							rooms[f][x][z].widthZ = Math.min(rooms[f][x][z].widthZ, rooms[f - 1][x][z].widthZ);
						}
					}
				}
			}
		}
		
		// find a non-missing room on the first floor
		int roomX = random.nextInt(2);
		int roomZ = random.nextInt(2);
		while (rooms[0][roomX][roomZ].missing) {
			roomX = random.nextInt(2);
			roomZ = random.nextInt(2);
		}
		
		// pick the entry room
		for (int f = 0; f < floors; f++) {
				
			// set the style and make sure there is room for stairs
			rooms[f][roomX][roomZ].missing = false;
			rooms[f][roomX][roomZ].style = Room.Style.ENTRY;
			rooms[f][roomX][roomZ].widthX = maxRoomWidth;
			rooms[f][roomX][roomZ].widthZ = maxRoomWidth;
		}
		
		// now the kitchen
		roomZ = roomZ == 0 ? 1 : 0;
		if (rooms[0][roomX][roomZ].missing) {
			roomX = roomX == 0 ? 1 : 0;
			roomZ = roomZ == 0 ? 1 : 0;
		}
		rooms[0][roomX][roomZ].style = Room.Style.KITCHEN;

		// is this a single story house?
		if (floors == 1) {

			// next find the dining room
			roomX = roomX == 0 ? 1 : 0;
			if (!rooms[0][roomX][roomZ].missing) {
				rooms[0][roomX][roomZ].style = Room.Style.DINING;
			}
			
			// put the bed in the last spot
			roomZ = roomZ == 0 ? 1 : 0;
			rooms[0][roomX][roomZ].missing = false;
			rooms[0][roomX][roomZ].style = Room.Style.BED;
		
		// got more floors!
		} else {
			
			// next find the dining room
			roomX = roomX == 0 ? 1 : 0;
			if (!rooms[0][roomX][roomZ].missing) {
				rooms[0][roomX][roomZ].style = Room.Style.DINING;
				
				// put the living room in the last spot if available
				roomZ = roomZ == 0 ? 1 : 0;
				if (!rooms[0][roomX][roomZ].missing) {
					rooms[0][roomX][roomZ].style = Room.Style.LIVING;
				}
			
			// only one room left, dining room please!
			} else {
				roomZ = roomZ == 0 ? 1 : 0;
				if (!rooms[0][roomX][roomZ].missing) {
					rooms[0][roomX][roomZ].style = Room.Style.DINING;
				}
			}
		}
		
		// where is the center of the house?
		int roomOffsetX = chunk.width / 2 + random.nextInt(2) - 1;
		int roomOffsetZ = chunk.width / 2 + random.nextInt(2) - 1;
		
		// draw the individual rooms
		for (int f = 0; f < floors; f++) {
			
			// just in case we come across an entry way
			int entryX = -1;
			int entryZ = -1;
			
			// do the rooms
			for (int x = 0; x < 2; x++) {
				for (int z = 0; z < 2; z++) {
					
					// do entry ways later
					if (rooms[f][x][z].style == Room.Style.ENTRY) {
						entryX = x;
						entryZ = z;
					} else
						drawRoom(chunk, context, rooms, f, floors, x, z, roomOffsetX, roomOffsetZ, baseY, matFloor, matWall, matCeiling, matRoof);
				}
			}
			
			// found an entry
			if (entryX != -1) {
				drawRoom(chunk, context, rooms, f, floors, entryX, entryZ, roomOffsetX, roomOffsetZ, baseY, matFloor, matWall, matCeiling, matRoof);
			}
		}
		
//		// flat roof?
//		if (random.nextDouble() < 0.95) {
			
			//TODO simple blocks
			//TODO flat roofs
			//TODO NS stair roof
			//TODO EW stair root
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
			for (int y = 1; y < ContextData.FloorHeight - 1; y++) {
				for (int x = 1; x < chunk.width - 1; x++) {
					for (int z = 1; z < chunk.width - 1; z++) {
						int yAt = y + roofY;
						if (chunk.getBlock(x, yAt + 1, z) != materialAir) {
							chunk.setBlock(x, yAt, z, materialAir);
						}
					}
				}
			}

//			// extrude roof
//			int roofY = baseY + floors * ContextData.FloorHeight - 1;
//			for (int y = 0; y < ContextData.FloorHeight - 1; y++) {
//				for (int x = 1; x < chunk.width - 1; x++) {
//					for (int z = 1; z < chunk.width - 1; z++) {
//						int yAt = y + roofY;
//						if (chunk.getBlock(x, yAt, z - 1) != materialAir && chunk.getBlock(x, yAt, z + 1) != materialAir) {
//							chunk.setBlock(x, yAt + 1, z, matRoof);
//						}
//					}
//				}
//			}
//			
//			// carve out the attic
//			for (int y = 1; y < ContextData.FloorHeight - 1; y++) {
//				for (int x = 1; x < chunk.width - 1; x++) {
//					for (int z = 1; z < chunk.width - 1; z++) {
//						int yAt = y + roofY;
//						if (chunk.getBlock(x, yAt + 1, z) != materialAir) {
//							chunk.setBlock(x, yAt, z, materialAir);
//						}
//					}
//				}
//			}

//		}
	}
	
	private final static void drawRoom(RealChunk chunk, ContextData context, Room[][][] rooms, int floor, int floors, int x, int z, 
			int roomOffsetX, int roomOffsetZ, int baseY, 
			Material matFloor, Material matWall, Material matCeiling, Material matRoof) {

		// which room?
		Room room = rooms[floor][x][z];
		
		// missing?
		if (room.missing) {
			
			// is there a floor below?
			if (floor > 0 && !rooms[floor - 1][x][z].missing)
				rooms[floor - 1][x][z].DrawRailing(chunk);
		} else {

			// draw bottom bits
			if (floor == 0) {
				room.DrawFloor(chunk, context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY, matFloor);
			}
	
			// draw outside bits
			room.DrawWalls(chunk, context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY, matWall);
	
			// top floor's top
			if (floor == floors - 1) {
				room.DrawRoof(chunk, context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY, matRoof);
			
			} else {
				room.DrawCeiling(chunk, context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY, matCeiling);
			}
			
			// now the inner bits
			room.DrawStyle(chunk, context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY);
		} 
	}
	
	private final static Material pickWallMaterial(Random random) {
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
			return Material.NETHER_BRICK;
		case 7:
			return Material.BRICK;
		case 8:
			return Material.CLAY;
		default:
			return Material.WOOD;
		}
	}

	private final static Material pickCeilingMaterial(Random random) {
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

	private final static Material pickFloorMaterial(Random random) {
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

	private final static Material pickRoofMaterial(Random random) {
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
