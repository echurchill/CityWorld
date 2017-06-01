package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.BlackMagic;
import me.daddychurchill.CityWorld.Support.BadMagic;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.Odds.ColorSet;

public class StructureOnGroundProvider extends Provider {

//	private static RoomProvider contentsKitchen = new HouseKitchens();
//	private static RoomProvider contentsBedroom = new HouseBedrooms();
//	private static RoomProvider contentsDiningRoom = new HouseDiningRooms();
//	private static RoomProvider contentsLivingRoom = new HouseLivingRooms();
	
	public StructureOnGroundProvider() {
		super();

	}
	
	public final static StructureOnGroundProvider loadProvider(CityWorldGenerator generator) {
		// for now
		return new StructureOnGroundProvider();
	}
	
	private final static double oddsOfFurnace = Odds.oddsSomewhatUnlikely;
	private final static double oddsOfCraftingTable = Odds.oddsSomewhatUnlikely;
	
	public void generateShed(CityWorldGenerator generator, RealBlocks chunk, DataContext context, Odds odds, 
			int x, int y, int z, int radius, LootLocation location) {
		generateShed(generator, chunk, context, odds, x, y, z, radius, location, location);
	}
		
	public void generateShed(CityWorldGenerator generator, RealBlocks chunk, DataContext context, Odds odds, 
			int x, int y, int z, int radius, LootLocation location, LootLocation other) {
		int x1 = x - radius;
		int x2 = x + radius + 1;
		int z1 = z - radius;
		int z2 = z + radius + 1;
		int y1 = y;
		int y2 = y + DataContext.FloorHeight - 1;
		int xR = x2 - x1 - 2;
		int zR = z2 - z1 - 2;
		
		Material wallMat = generator.materialProvider.itemsSelectMaterial_ShedWalls.getRandomMaterial(odds, Material.COBBLESTONE);
		Material roofMat = generator.materialProvider.itemsSelectMaterial_ShedRoofs.getRandomMaterial(odds, Material.COBBLESTONE);
		
		chunk.setWalls(x1, x2, y1, y2, z1, z2, wallMat);
		chunk.setBlocks(x1 + 1, x2 - 1, y2, z1 + 1, z2 - 1, roofMat);
		
		switch (odds.getRandomInt(4)) {
		case 0: // north
			chunk.setWoodenDoor(x1 + odds.getRandomInt(xR) + 1, y1, z1, BadMagic.Door.NORTHBYNORTHEAST);
			chunk.setBlock(x1 + odds.getRandomInt(xR) + 1, y1 + 1, z2 - 1, materialGlass);
			placeShedTable(generator, chunk, odds, x1 + odds.getRandomInt(xR) + 1, y1, z2 - 2, BadMagic.General.SOUTH);
			placeShedChest(generator, chunk, odds, x1 - 1, y1, z1 + odds.getRandomInt(zR) + 1, BadMagic.General.WEST, location);
			placeShedChest(generator, chunk, odds, x2, y1, z1 + odds.getRandomInt(zR) + 1, BadMagic.General.EAST, other);
			break;
		case 1: // south
			chunk.setWoodenDoor(x1 + odds.getRandomInt(xR) + 1, y1, z2 - 1, BadMagic.Door.SOUTHBYSOUTHWEST);
			chunk.setBlock(x1 + odds.getRandomInt(xR) + 1, y1 + 1, z1, materialGlass);
			placeShedTable(generator, chunk, odds, x1 + odds.getRandomInt(xR) + 1, y1, z1 + 1, BadMagic.General.NORTH);
			placeShedChest(generator, chunk, odds, x1 - 1, y1, z1 + odds.getRandomInt(zR) + 1, BadMagic.General.WEST, location);
			placeShedChest(generator, chunk, odds, x2, y1, z1 + odds.getRandomInt(zR) + 1, BadMagic.General.EAST, other);
			break;
		case 2: // west
			chunk.setWoodenDoor(x1, y1, z1 + odds.getRandomInt(zR) + 1, BadMagic.Door.WESTBYNORTHWEST);
			chunk.setBlock(x2 - 1, y1 + 1, z1 + odds.getRandomInt(zR) + 1, materialGlass);
			placeShedTable(generator, chunk, odds, x2 - 2, y1, z1 + odds.getRandomInt(zR) + 1, BadMagic.General.EAST);
			placeShedChest(generator, chunk, odds, x1 + odds.getRandomInt(xR) + 1, y1, z1 - 1, BadMagic.General.NORTH, location);
			placeShedChest(generator, chunk, odds, x1 + odds.getRandomInt(xR) + 1, y1, z2, BadMagic.General.SOUTH, other);
			break;
		default: // east
			chunk.setWoodenDoor(x1, y1, z1 + odds.getRandomInt(zR) + 1, BadMagic.Door.EASTBYSOUTHEAST);
			chunk.setBlock(x2 - 1, y1 + 1, z1 + odds.getRandomInt(zR) + 1, materialGlass);
			placeShedTable(generator, chunk, odds, x1 + 1, y1, z1 + odds.getRandomInt(zR) + 1, BadMagic.General.WEST);
			placeShedChest(generator, chunk, odds, x1 + odds.getRandomInt(xR) + 1, y1, z1 - 1, BadMagic.General.NORTH, location);
			placeShedChest(generator, chunk, odds, x1 + odds.getRandomInt(xR) + 1, y1, z2, BadMagic.General.SOUTH, other);
			break;
		}
	}
	
	private void placeShedTable(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int x, int y, int z, BadMagic.General direction) {
		if (odds.playOdds(oddsOfFurnace))
			chunk.setFurnace(x, y, z, direction);
		else if (odds.playOdds(oddsOfCraftingTable))
			chunk.setBlock(x, y, z, Material.WORKBENCH);
		else {
			chunk.setBlock(x, y, z, Material.FENCE);
			chunk.setBlock(x, y + 1, z, Material.WOOD_PLATE);
		}
	}
	
	private void placeShedChest(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int x, int y, int z, 
			BadMagic.General direction, LootLocation location) {
		switch (direction) {
		case NORTH:
			chunk.setChest(generator, x + 1, y, z, direction, odds, generator.lootProvider, location);
			break;
		case SOUTH:
			chunk.setChest(generator, x - 1, y, z, direction, odds, generator.lootProvider, location);
			break;
		case WEST:
			chunk.setChest(generator, x, y, z + 1, direction, odds, generator.lootProvider, location);
			break;
		case EAST:
			chunk.setChest(generator, x, y, z - 1, direction, odds, generator.lootProvider, location);
			break;
		}
	}

	private final static Material matWindow = Material.THIN_GLASS;
	private final static Material matPole = Material.FENCE;
	private final static Material matFire = Material.FIRE;
	private final static Material matFireBase = Material.NETHERRACK;
	private final static Material matFireRing = Material.COBBLESTONE_STAIRS;
	private final static Material matLog = Material.LOG;
	
	public void generateCampground(CityWorldGenerator generator, RealBlocks chunk, DataContext context, Odds odds, int baseY) {
		
		// what are we made of?
		DyeColor matColor = odds.getRandomColor();
		boolean matCamo = odds.playOdds(Odds.oddsSomewhatUnlikely);
		
		// direction? 
		if (odds.flipCoin()) {
			
			// north/south tent first
			for (int z = 3; z < 9; z++) {
				chunk.setWool(3, baseY, z, getTentColor(odds, matColor, matCamo));
				chunk.setWool(4, baseY + 1, z, getTentColor(odds, matColor, matCamo));
				chunk.setWool(5, baseY + 2, z, getTentColor(odds, matColor, matCamo));
				chunk.setWool(6, baseY + 3, z, getTentColor(odds, matColor, matCamo));
				chunk.setWool(7, baseY + 2, z, getTentColor(odds, matColor, matCamo));
				chunk.setWool(8, baseY + 1, z, getTentColor(odds, matColor, matCamo));
				chunk.setWool(9, baseY, z, getTentColor(odds, matColor, matCamo));
			}
			
			// back wall
			chunk.setWool(4, baseY, 3, getTentColor(odds, matColor, matCamo));
			chunk.setWool(5, baseY, 3, getTentColor(odds, matColor, matCamo));
			chunk.setWool(5, baseY + 1, 3, getTentColor(odds, matColor, matCamo));
			chunk.setWool(6, baseY, 3, getTentColor(odds, matColor, matCamo));
			chunk.setBlock(6, baseY + 1, 3, matWindow);
			chunk.setWool(6, baseY + 2, 3, getTentColor(odds, matColor, matCamo));
			chunk.setWool(7, baseY + 1, 3, getTentColor(odds, matColor, matCamo));
			chunk.setWool(7, baseY, 3, getTentColor(odds, matColor, matCamo));
			chunk.setWool(8, baseY, 3, getTentColor(odds, matColor, matCamo));
					
			// post
			chunk.setBlocks(6, baseY, baseY + 3, 8, matPole);
			
			// beds
			if (odds.playOdds(Odds.oddsPrettyLikely))
				chunk.setBed(5, baseY, 4, BadMagic.Facing.SOUTH);
			if (odds.playOdds(Odds.oddsPrettyLikely))
				chunk.setBed(7, baseY, 4, BadMagic.Facing.SOUTH);
		} else {
			// north/south tent first
			for (int x = 3; x < 9; x++) {
				chunk.setWool(x, baseY, 3, getTentColor(odds, matColor, matCamo));
				chunk.setWool(x, baseY + 1, 4, getTentColor(odds, matColor, matCamo));
				chunk.setWool(x, baseY + 2, 5, getTentColor(odds, matColor, matCamo));
				chunk.setWool(x, baseY + 3, 6, getTentColor(odds, matColor, matCamo));
				chunk.setWool(x, baseY + 2, 7, getTentColor(odds, matColor, matCamo));
				chunk.setWool(x, baseY + 1, 8, getTentColor(odds, matColor, matCamo));
				chunk.setWool(x, baseY, 9, getTentColor(odds, matColor, matCamo));
			}
			
			// back wall
			chunk.setWool(3, baseY, 4, getTentColor(odds, matColor, matCamo));
			chunk.setWool(3, baseY, 5, getTentColor(odds, matColor, matCamo));
			chunk.setWool(3, baseY + 1, 5, getTentColor(odds, matColor, matCamo));
			chunk.setWool(3, baseY, 6, getTentColor(odds, matColor, matCamo));
			chunk.setBlock(3, baseY + 1, 6, matWindow);
			chunk.setWool(3, baseY + 2, 6, getTentColor(odds, matColor, matCamo));
			chunk.setWool(3, baseY + 1, 7, getTentColor(odds, matColor, matCamo));
			chunk.setWool(3, baseY, 7, getTentColor(odds, matColor, matCamo));
			chunk.setWool(3, baseY, 8, getTentColor(odds, matColor, matCamo));
					
			// post
			chunk.setBlocks(8, baseY, baseY + 3, 6, matPole);
			
			// beds
			if (odds.playOdds(Odds.oddsPrettyLikely))
				chunk.setBed(4, baseY, 5, BadMagic.Facing.EAST);
			if (odds.playOdds(Odds.oddsPrettyLikely))
				chunk.setBed(4, baseY, 7, BadMagic.Facing.EAST);
		}
		
		// now the fire pit
		if (odds.playOdds(Odds.oddsPrettyLikely)) {
			chunk.setStair(11, baseY - 1, 10, matFireRing, BadMagic.Stair.SOUTH);
			chunk.setStair(12, baseY - 1, 11, matFireRing, BadMagic.Stair.WEST);
			chunk.setStair(11, baseY - 1, 12, matFireRing, BadMagic.Stair.NORTH);
			chunk.setStair(10, baseY - 1, 11, matFireRing, BadMagic.Stair.EAST);
			chunk.setStair(10, baseY - 1, 10, matFireRing, BadMagic.Stair.SOUTH);
			chunk.setStair(12, baseY - 1, 10, matFireRing, BadMagic.Stair.WEST);
			chunk.setStair(12, baseY - 1, 12, matFireRing, BadMagic.Stair.NORTH);
			chunk.setStair(10, baseY - 1, 12, matFireRing, BadMagic.Stair.EAST);
	
			// and the fire itself
			chunk.setBlock(11, baseY - 1, 11, matFireBase);
			if (odds.playOdds(Odds.oddsPrettyLikely)) {
				chunk.clearBlocks(9, 14, baseY, 9, 14); // we do this to keep the grass and such away from the fire so it doesn't go firebug on us
				chunk.setBlock(11, baseY, 11, matFire);
			}
		}
		
		// and the logs
		int logType = odds.getRandomWoodType();
		if (odds.playOdds(Odds.oddsPrettyLikely)) {
			BlackMagic.setBlock(chunk, 11, baseY, 8, matLog, logType + logWestEast);
			BlackMagic.setBlock(chunk, 12, baseY, 8, matLog, logType + logWestEast);
		}
		if (odds.playOdds(Odds.oddsPrettyLikely)) {
			BlackMagic.setBlock(chunk, 8, baseY, 11, matLog, logType + logNorthSouth);
			BlackMagic.setBlock(chunk, 8, baseY, 12, matLog, logType + logNorthSouth);
		}
	}
	
	private int logWestEast = 0x4;
	private int logNorthSouth = 0x8;
	
	private DyeColor getTentColor(Odds odds, DyeColor baseColor, boolean camoMode) {
		if (camoMode) {
			if (baseColor == DyeColor.PINK)
				return odds.getRandomColor(ColorSet.PINK);
			else 
				return odds.getRandomColor(ColorSet.GREEN);
		} else
			return baseColor;
	}
	
	private enum HouseRoofStyle {FLAT, NORTHSOUTH, WESTEAST, ANGLED};
	
	public int generateRuralShack(CityWorldGenerator generator, RealBlocks chunk, DataContext context, Odds odds, int baseY, int roomWidth) {
		
		// what are we made of?
		Material matWall = generator.materialProvider.itemsSelectMaterial_ShackWalls.getRandomMaterial(odds, Material.COBBLESTONE);
		Material matFloor = generator.materialProvider.itemsSelectMaterial_ShackWalls.getRandomMaterial(odds, Material.COBBLESTONE);
		Material matRoof = generator.materialProvider.itemsSelectMaterial_ShackRoofs.getRandomMaterial(odds, Material.COBBLESTONE);
		Material matCeiling = matRoof;
		HouseRoofStyle styleRoof = HouseRoofStyle.FLAT;
		int floors = 1;
		
		//chunk.setWalls(2, 13, baseY, baseY + ContextData.FloorHeight, 2, 13, Material.WOOD);
		generateColonial(generator, chunk, context, odds, baseY, matFloor, matWall, matCeiling, matRoof, floors, roomWidth, roomWidth, styleRoof, false);
		return floors;
	}
	
	private Material matTrapDoor = Material.TRAP_DOOR;
	
	public int generateHouse(CityWorldGenerator generator, RealBlocks chunk, DataContext context, Odds odds, int baseY, int maxFloors, int maxRoomWidth) {
		
		// what are we made of?
		Material matWall = generator.materialProvider.itemsSelectMaterial_HouseWalls.getRandomMaterial(odds, Material.COBBLESTONE);
		Material matFloor = generator.materialProvider.itemsSelectMaterial_HouseFloors.getRandomMaterial(odds, Material.COBBLESTONE);
		Material matCeiling = generator.materialProvider.itemsSelectMaterial_HouseCeilings.getRandomMaterial(odds, Material.COBBLESTONE);
		Material matRoof = generator.materialProvider.itemsSelectMaterial_HouseRoofs.getRandomMaterial(odds, Material.COBBLESTONE);
		HouseRoofStyle styleRoof = pickRoofStyle(odds);
		int floors = odds.getRandomInt(maxFloors) + 1;
		
		//TODO add bed
		//TODO add kitchen
		//TODO add living room
		//TODO add split level house style
		
		// draw the house
		generateColonial(generator, chunk, context, odds, baseY, matFloor, matWall, matCeiling, matRoof, floors, MinSize, maxRoomWidth, styleRoof, true);
		return floors;
	}

	public int generateHouse(CityWorldGenerator generator, RealBlocks chunk, DataContext context, Odds odds, int baseY, int maxFloors) {
		return generateHouse(generator, chunk, context, odds, baseY, maxFloors, MaxSize);
	}

	private void generateColonial(CityWorldGenerator generator, RealBlocks chunk, DataContext context, 
			Odds odds, int baseY,
			Material matFloor, Material matWall, Material matCeiling, Material matRoof, 
			int floors, int minRoomWidth, int maxRoomWidth, HouseRoofStyle styleRoof, boolean allowMissingRooms) {
		
		// what are the rooms like?
		Room[][][] rooms = new Room[floors][2][2];
		for (int f = 0; f < floors; f++) {
			boolean missingRoom = false;
			for (int x = 0; x < 2; x++) {
				for (int z = 0; z < 2; z++) {
					
					// missing rooms?
					boolean thisRoomMissing = false;
					if (allowMissingRooms && floors > 1) {
						thisRoomMissing = odds.getRandomInt(MissingRoomOdds) == 0;
					}
					
					// what does the room "look" like?
					int thisRoomWidthZ = getRoomWidth(odds, minRoomWidth, maxRoomWidth);
					int thisRoomWidthX = getRoomWidth(odds, minRoomWidth, maxRoomWidth);
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
		int roomX = odds.getRandomInt(2);
		int roomZ = odds.getRandomInt(2);
		while (rooms[0][roomX][roomZ].missing) {
			roomX = odds.getRandomInt(2);
			roomZ = odds.getRandomInt(2);
		}
		
		// pick the entry room
		for (int f = 0; f < floors; f++) {
				
			// set the style and make sure there is room for stairs
			rooms[f][roomX][roomZ].missing = false;
			rooms[f][roomX][roomZ].style = Room.Style.ENTRY;
			rooms[f][roomX][roomZ].widthX = maxRoomWidth;
			rooms[f][roomX][roomZ].widthZ = maxRoomWidth;
			
			// and on the second floor
			if (f == 1) {
				
				// if one of the side rooms is missing, make it not missing and make the opposite one is
				if (rooms[f][roomX][flip(roomZ)].missing) {
					rooms[f][roomX][flip(roomZ)].missing = false;
					rooms[f][flip(roomX)][flip(roomZ)].missing = true;
				} else if (rooms[f][flip(roomX)][roomZ].missing) {
					rooms[f][flip(roomX)][roomZ].missing = false;
					rooms[f][flip(roomX)][flip(roomZ)].missing = true;
				}
			}
		}
		
		// now the kitchen
		roomZ = flip(roomZ);
		if (rooms[0][roomX][roomZ].missing) {
			roomX = flip(roomX);
			roomZ = flip(roomZ);
		}
		rooms[0][roomX][roomZ].style = Room.Style.KITCHEN;

		// is this a single story house?
		if (floors == 1) {

			// next find the dining room
			roomX = flip(roomX);
			if (!rooms[0][roomX][roomZ].missing) {
				rooms[0][roomX][roomZ].style = Room.Style.DINING;
			}
			
			// put the bed in the last spot
			roomZ = flip(roomZ);
			rooms[0][roomX][roomZ].missing = false;
			rooms[0][roomX][roomZ].style = Room.Style.BED;
		
		// got more floors!
		} else {
			
			// next find the dining room
			roomX = flip(roomX);
			if (!rooms[0][roomX][roomZ].missing) {
				rooms[0][roomX][roomZ].style = Room.Style.DINING;
				
				// put the living room in the last spot if available
				roomZ = flip(roomZ);
				if (!rooms[0][roomX][roomZ].missing) {
					rooms[0][roomX][roomZ].style = Room.Style.LIVING;
				}
			
			// only one room left, dining room please!
			} else {
				roomZ = flip(roomZ);
				if (!rooms[0][roomX][roomZ].missing) {
					rooms[0][roomX][roomZ].style = Room.Style.DINING;
				}
			}
		}
		
		// where is the center of the house?
		int roomOffsetX = chunk.width / 2 + odds.getRandomInt(2) - 1;
		int roomOffsetZ = chunk.width / 2 + odds.getRandomInt(2) - 1;
		
		// draw the individual rooms
		for (int f = 0; f < floors; f++) {
			
			// just in case we come across an entry way
			int entryX = -1;
			int entryZ = -1;
			
			// floor material?
			Material thisFloor = matFloor;
			if (f > 0)
				thisFloor = matCeiling;
			
			// do the rooms
			for (int x = 0; x < 2; x++) {
				for (int z = 0; z < 2; z++) {
					
					// do entry ways later
					if (rooms[f][x][z].style == Room.Style.ENTRY) {
						entryX = x;
						entryZ = z;
					} else
						drawRoom(generator, chunk, context, odds, rooms, f, floors, x, z, roomOffsetX, roomOffsetZ, baseY, thisFloor, matWall, matCeiling, matRoof);
				}
			}
			
			// found an entry
			if (entryX != -1) {
				drawRoom(generator, chunk, context, odds, rooms, f, floors, entryX, entryZ, roomOffsetX, roomOffsetZ, baseY, thisFloor, matWall, matCeiling, matRoof);
			}
		}
		
		// figure out roofs
		int roofBottom = baseY + floors * DataContext.FloorHeight - 1;
		int roofHeight = DataContext.FloorHeight + 1;
		boolean makeAttic = true;
		
		switch (styleRoof) {
		case ANGLED:
		default:
			
			// place the roof!
			for (int y = 0; y < roofHeight; y++) {
				for (int x = 1; x < chunk.width - 1; x++) {
					for (int z = 1; z < chunk.width - 1; z++) {
						int yAt = y + roofBottom;
						if (y == 0) {
							if (chunk.isOfTypes(x, yAt, z, matRoof, matTrapDoor) && 
								chunk.isOfTypes(x - 1, yAt, z, matRoof, matTrapDoor) &&
								chunk.isOfTypes(x + 1, yAt, z, matRoof, matTrapDoor) &&
								chunk.isOfTypes(x, yAt, z - 1, matRoof, matTrapDoor) &&
								chunk.isOfTypes(x, yAt, z + 1, matRoof, matTrapDoor))
								chunk.setBlock(x, yAt + 1, z, matRoof);
						} else {
							if (chunk.isType(x, yAt, z, matRoof) && 
								chunk.isType(x - 1, yAt, z, matRoof) &&
								chunk.isType(x + 1, yAt, z, matRoof) &&
								chunk.isType(x, yAt, z - 1, matRoof) &&
								chunk.isType(x, yAt, z + 1, matRoof))
								chunk.setBlock(x, yAt + 1, z, matRoof);
						}
//						if (chunk.isOfTypes(x, yAt, z, matRoof, matTrapDoor) && 
//							!chunk.isEmpty(x - 1, yAt, z) && !chunk.isEmpty(x + 1, yAt, z) &&
//							!chunk.isEmpty(x, yAt, z - 1) && !chunk.isEmpty(x, yAt, z + 1))
//							chunk.setBlock(x, yAt + 1, z, matRoof);
					}
				}
			}
			break;
		case NORTHSOUTH:
			
			// place the roof!
			for (int y = 0; y < roofHeight; y++) {
				for (int x = 1; x < chunk.width - 1; x++) {
					for (int z = 1; z < chunk.width - 1; z++) {
						int yAt = y + roofBottom;
						if (y == 0) {
							if (chunk.isOfTypes(x, yAt, z, matRoof, matTrapDoor) && 
								chunk.isOfTypes(x - 1, yAt, z, matRoof, matTrapDoor) &&
								chunk.isOfTypes(x + 1, yAt, z, matRoof, matTrapDoor))
								chunk.setBlock(x, yAt + 1, z, matRoof);
						} else {
							if (chunk.isType(x, yAt, z, matRoof) && 
								chunk.isType(x - 1, yAt, z, matRoof) &&
								chunk.isType(x + 1, yAt, z, matRoof))
								chunk.setBlock(x, yAt + 1, z, matRoof);
						}
//						if (chunk.isOfTypes(x, yAt, z, matRoof, matTrapDoor) && 
//							!chunk.isEmpty(x - 1, yAt, z) && !chunk.isEmpty(x + 1, yAt, z))
//							chunk.setBlock(x, yAt + 1, z, matRoof);
					}
				}
			}
			break;
		case WESTEAST:

			// place the roof!
			for (int y = 0; y < roofHeight; y++) {
				for (int x = 1; x < chunk.width - 1; x++) {
					for (int z = 1; z < chunk.width - 1; z++) {
						int yAt = y + roofBottom;
						if (y == 0) {
							if (chunk.isOfTypes(x, yAt, z, matRoof, matTrapDoor) && 
								chunk.isOfTypes(x, yAt, z - 1, matRoof, matTrapDoor) &&
								chunk.isOfTypes(x, yAt, z + 1, matRoof, matTrapDoor))
								chunk.setBlock(x, yAt + 1, z, matRoof);
						} else {
							if (chunk.isType(x, yAt, z, matRoof) && 
								chunk.isType(x, yAt, z - 1, matRoof) &&
								chunk.isType(x, yAt, z + 1, matRoof))
								chunk.setBlock(x, yAt + 1, z, matRoof);
						}
//						if (chunk.isOfTypes(x, yAt, z, matRoof, matTrapDoor) && 
//							!chunk.isEmpty(x, yAt, z - 1) && !chunk.isEmpty(x, yAt, z + 1))
//							chunk.setBlock(x, yAt + 1, z, matRoof);
					}
				}
			}
			break;
		case FLAT:

			// place the roof!
			for (int y = 0; y < 1; y++) {
				for (int x = 1; x < chunk.width - 1; x++) {
					for (int z = 1; z < chunk.width - 1; z++) {
						int yAt = y + roofBottom;
						if (chunk.isOfTypes(x, yAt, z, matRoof, matTrapDoor) && 
							(chunk.isEmpty(x - 1, yAt, z) || 
							 chunk.isEmpty(x + 1, yAt, z) ||
							 chunk.isEmpty(x, yAt, z - 1) || 
							 chunk.isEmpty(x, yAt, z + 1)))
							chunk.setBlock(x, yAt + 1, z, matRoof);
					}
				}
			}
			makeAttic = false;
			break;
		}
		
		
		if (makeAttic) {
			
			// fill the potential attic space with something silly
			for (int y = 1; y < roofHeight - 1; y++) {
				for (int x = 1; x < chunk.width - 1; x++) {
					for (int z = 1; z < chunk.width - 1; z++) {
						int yAt = y + roofBottom;
						if (/*!chunk.isEmpty(x, yAt + 1, z)) */
							chunk.isType(x, yAt, z, matRoof))
							chunk.setBlock(x, yAt, z, Material.BEDROCK); // mark potential attic space
					}
				}
			}
			
			// but don't over do it and go too far
			for (int y = 1; y < roofHeight - 1; y++) {
				for (int x = 1; x < chunk.width - 1; x++) {
					for (int z = 1; z < chunk.width - 1; z++) {
						int yAt = y + roofBottom;
						if (chunk.isType(x, yAt, z, Material.BEDROCK)) { // where we think the attic might be
							if (chunk.isEmpty(x - 1, yAt, z) || chunk.isEmpty(x + 1, yAt, z) || 
								chunk.isEmpty(x, yAt, z - 1) || chunk.isEmpty(x, yAt, z + 1))
								chunk.setBlock(x, yAt, z, matRoof);
						}
					}
				}
			}
			
			// finally remove the silliness from the attic
			for (int y = 1; y < roofHeight - 1; y++) {
				for (int x = 1; x < chunk.width - 1; x++) {
					for (int z = 1; z < chunk.width - 1; z++) {
						int yAt = y + roofBottom;
						if (chunk.isType(x, yAt, z, Material.BEDROCK))
							chunk.clearBlock(x, yAt, z);
					}
				}
			}
		}
	}
	
	private int flip(int i) {
		return i == 0 ? 1 : 0;
	}
	
	private void drawRoom(CityWorldGenerator generator, RealBlocks chunk, DataContext context, 
			Odds odds, Room[][][] rooms, int floor, int floors, int x, int z, 
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
			room.DrawStyle(generator, chunk, context, odds, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY);
		} 
	}
	
	private int getRoomWidth(Odds odds, int minRoomWidth, int maxRoomWidth) {
		return odds.getRandomInt(maxRoomWidth - minRoomWidth + 1) + minRoomWidth;
	}
	
	private HouseRoofStyle pickRoofStyle(Odds odds) {
		switch (odds.getRandomInt(4)) {
		default:
		case 0:
			return HouseRoofStyle.ANGLED;
		case 1:
			return HouseRoofStyle.NORTHSOUTH;
		case 2:
			return HouseRoofStyle.WESTEAST;
		case 3:
			return HouseRoofStyle.FLAT;
		}
	}
	
	private final static Material materialAir = Material.AIR;
	private final static Material materialGlass = Material.GLASS;
	private final static Material materialFence = Material.FENCE;
	private final static Material materialStair = Material.WOOD_STAIRS;
	private final static Material materialUnderStairs = Material.WOOD;
	
	private final static int MinSize = 4;
	private final static int MaxSize = 6;
	private final static int MissingRoomOdds = 5; // 1/n of the time a room is missing
	
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
		
		protected void Locate(DataContext context, int floor, int floors, 
				int x, int z, int roomOffsetX, int roomOffsetZ, int baseY) {
			if (!located) {
				located = true;
				roomEast = x != 0;
				roomSouth = z != 0;
				x1 = roomOffsetX - (roomEast ? 0 : widthX);
				x2 = roomOffsetX + (roomEast ? widthX : 0);
				z1 = roomOffsetZ - (roomSouth ? 0 : widthZ);
				z2 = roomOffsetZ + (roomSouth ? widthZ : 0);
				y1 = baseY + floor * DataContext.FloorHeight;
				y2 = y1 + DataContext.FloorHeight - 1;
			}
		}
		
		protected void DrawWalls(RealBlocks chunk, DataContext context, int floor, int floors, 
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
		
		protected void DrawFloor(RealBlocks chunk, DataContext context, int floor, int floors, 
				int x, int z, int roomOffsetX, int roomOffsetZ, int baseY, 
				Material matFloor) {
			
			// find ourselves
			Locate(context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY);
			
			// put the rug down
			chunk.setBlocks(x1, x2 + 1, y1 - 1, y1, z1, z2 + 1, matFloor);
		}
		
		protected void DrawCeiling(RealBlocks chunk, DataContext context, int floor, int floors, 
				int x, int z, int roomOffsetX, int roomOffsetZ, int baseY, 
				Material matCeiling) {
			
			// find ourselves
			Locate(context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY);
			
			// put the rug down
			chunk.setBlocks(x1, x2 + 1, y2, y2 + 1, z1, z2 + 1, matCeiling);
		}
		
		protected void DrawRoof(RealBlocks chunk, DataContext context, int floor, int floors, 
				int x, int z, int roomOffsetX, int roomOffsetZ, int baseY, 
				Material matRoof) {
			
			// find ourselves
			Locate(context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY);
			
			// put roof on top
			//TODO need fancier roofs
			chunk.setBlocks(x1, x2 + 1, y2, y2 + 1, z1, z2 + 1, matRoof);
		}

		protected void DrawRailing(RealBlocks chunk) {
			
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

		protected void DrawStyle(CityWorldGenerator generator, RealBlocks chunk, DataContext context, 
				Odds odds, int floor, int floors, 
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
				if (odds.flipCoin()) {
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
					if (odds.flipCoin()) {
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

							chunk.setStair(x1 + 2, y1 + 3, z1 + 1, materialStair, BadMagic.Stair.WEST);
							chunk.setStair(x1 + 2, y1 + 2, z1 + 1, materialStair, BadMagic.Stair.EASTFLIP);

							chunk.setStair(x1 + 3, y1 + 2, z1 + 1, materialStair, BadMagic.Stair.WEST);
							chunk.setStair(x1 + 3, y1 + 1, z1 + 1, materialStair, BadMagic.Stair.EASTFLIP);
							
							chunk.setStair(x1 + 4, y1 + 1, z1 + 1, materialStair, BadMagic.Stair.WEST);
							chunk.setStair(x1 + 4, y1    , z1 + 1, materialStair, BadMagic.Stair.EASTFLIP);

							chunk.setBlock(x1 + 5, y1    , z1 + 1, materialUnderStairs);
							chunk.setStair(x1 + 5, y1    , z1 + 2, materialStair, BadMagic.Stair.NORTH);
							
//							chunk.setBlocks(x1 + 3, y1, y2 + 50, z1 + 3, Material.FENCE);
//							chunk.setBlock(x1 + 3, y2 + 50, z1 + 3, Material.GOLD_BLOCK);
						} else {
							chunk.setBlocks(x1 + 1, x1 + 2, y2, z1 + 1, z2, materialAir);

							chunk.setBlock(x1 + 1, y1 + 3, z2 - 1, materialUnderStairs);

							chunk.setStair(x1 + 1, y1 + 3, z2 - 2, materialStair, BadMagic.Stair.SOUTH);
							chunk.setStair(x1 + 1, y1 + 2, z2 - 2, materialStair, BadMagic.Stair.NORTHFLIP);

							chunk.setStair(x1 + 1, y1 + 2, z2 - 3, materialStair, BadMagic.Stair.SOUTH);
							chunk.setStair(x1 + 1, y1 + 1, z2 - 3, materialStair, BadMagic.Stair.NORTHFLIP);
							
							chunk.setStair(x1 + 1, y1 + 1, z2 - 4, materialStair, BadMagic.Stair.SOUTH);
							chunk.setStair(x1 + 1, y1    , z2 - 4, materialStair, BadMagic.Stair.NORTHFLIP);

							chunk.setBlock(x1 + 1, y1    , z2 - 5, materialUnderStairs);
							chunk.setStair(x1 + 2, y1    , z2 - 5, materialStair, BadMagic.Stair.WEST);
							
//							chunk.setBlocks(x1 + 3, y1, y2 + 50, z1 + 3, Material.FENCE);
//							chunk.setBlock(x1 + 3, y2 + 50, z1 + 3, Material.LAPIS_BLOCK);
						}
					} else {
						if (roomSouth) {
							chunk.setBlocks(x2 - 1, x2, y2, z1 + 1, z2, materialAir);

							chunk.setBlock(x2 - 1, y1 + 3, z1 + 1, materialUnderStairs);

							chunk.setStair(x2 - 1, y1 + 3, z1 + 2, materialStair, BadMagic.Stair.NORTH);
							chunk.setStair(x2 - 1, y1 + 2, z1 + 2, materialStair, BadMagic.Stair.SOUTHFLIP);

							chunk.setStair(x2 - 1, y1 + 2, z1 + 3, materialStair, BadMagic.Stair.NORTH);
							chunk.setStair(x2 - 1, y1 + 1, z1 + 3, materialStair, BadMagic.Stair.SOUTHFLIP);
							
							chunk.setStair(x2 - 1, y1 + 1, z1 + 4, materialStair, BadMagic.Stair.NORTH);
							chunk.setStair(x2 - 1, y1    , z1 + 4, materialStair, BadMagic.Stair.SOUTHFLIP);

							chunk.setBlock(x2 - 1, y1    , z1 + 5, materialUnderStairs);
							chunk.setStair(x2 - 2, y1    , z1 + 5, materialStair, BadMagic.Stair.EAST);
							
//							chunk.setBlocks(x1 + 3, y1, y2 + 50, z1 + 3, Material.FENCE);
//							chunk.setBlock(x1 + 3, y2 + 50, z1 + 3, Material.DIAMOND_BLOCK);
						} else {
							chunk.setBlocks(x1 + 1, x2, y2, z2 - 1, z2, materialAir);

							chunk.setBlock(x2 - 1, y1 + 3, z2 - 1, materialUnderStairs);

							chunk.setStair(x2 - 2, y1 + 3, z2 - 1, materialStair, BadMagic.Stair.EAST);
							chunk.setStair(x2 - 2, y1 + 2, z2 - 1, materialStair, BadMagic.Stair.WESTFLIP);

							chunk.setStair(x2 - 3, y1 + 2, z2 - 1, materialStair, BadMagic.Stair.EAST);
							chunk.setStair(x2 - 3, y1 + 1, z2 - 1, materialStair, BadMagic.Stair.WESTFLIP);
							
							chunk.setStair(x2 - 4, y1 + 1, z2 - 1, materialStair, BadMagic.Stair.EAST);
							chunk.setStair(x2 - 4, y1    , z2 - 1, materialStair, BadMagic.Stair.WESTFLIP);

							chunk.setBlock(x2 - 5, y1    , z2 - 1, materialUnderStairs);
							chunk.setStair(x2 - 5, y1    , z2 - 2, materialStair, BadMagic.Stair.SOUTH);
							
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
							chunk.setLadder(x1 + 1, y1, y1 + 3, z1 + 1, BlockFace.NORTH);
							chunk.setTrapDoor(x1 + 1, y2, z1 + 1, BadMagic.TrapDoor.SOUTH);
							
						} else {
							chunk.setLadder(x1 + 1, y1, y1 + 3, z2 - 1, BlockFace.WEST);
							chunk.setTrapDoor(x1 + 1, y2, z2 - 1, BadMagic.TrapDoor.EAST);

						}
					} else {
						if (roomSouth) {
							chunk.setLadder(x2 - 1, y1, y1 + 3, z1 + 1, BlockFace.EAST);
							chunk.setTrapDoor(x2 - 1, y2, z1 + 1, BadMagic.TrapDoor.WEST);
	
						} else {
							chunk.setLadder(x2 - 1, y1, y1 + 3, z2 - 1, BlockFace.SOUTH);
							chunk.setTrapDoor(x2 - 1, y2, z2 - 1, BadMagic.TrapDoor.NORTH);
						
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
						chunk.setWoodenDoor(x1 + 3,	y1, z2, BadMagic.Door.SOUTHBYSOUTHEAST);
					if (doorEast)
						chunk.setWoodenDoor(x2, y1, z1 + 3, BadMagic.Door.EASTBYSOUTHEAST);

					if (hallNorth)
						chunk.setWoodenDoor(x1 + 2,	y1, z1,	BadMagic.Door.NORTHBYNORTHWEST); 
					if (hallWest)
						chunk.setWoodenDoor(x1, y1, z1 + 2, BadMagic.Door.WESTBYNORTHWEST); 
					
				} else {
					if (doorNorth)
						chunk.setWoodenDoor(x1 + 3, y1, z1, BadMagic.Door.NORTHBYNORTHEAST); 
					if (doorEast)
						chunk.setWoodenDoor(x2, y1, z2 - 3, BadMagic.Door.EASTBYNORTHEAST); 

					if (hallSouth)
						chunk.setWoodenDoor(x1 + 2, y1, z2, BadMagic.Door.SOUTHBYSOUTHWEST); 
					if (hallWest)
						chunk.setWoodenDoor(x1, y1, z2 - 2, BadMagic.Door.WESTBYSOUTHWEST); 
					
				}
			} else {
				if (roomSouth) {
					if (doorSouth)
						chunk.setWoodenDoor(x2 - 3, y1, z2, BadMagic.Door.SOUTHBYSOUTHWEST); 
					if (doorWest)
						chunk.setWoodenDoor(x1, y1, z1 + 3, BadMagic.Door.WESTBYSOUTHWEST); 

					if (hallNorth)
						chunk.setWoodenDoor(x2 - 2, y1, z1, BadMagic.Door.NORTHBYNORTHEAST); 
					if (hallEast)
						chunk.setWoodenDoor(x2, y1, z1 + 2, BadMagic.Door.EASTBYNORTHEAST); 
					
				} else {
					if (doorNorth)
						chunk.setWoodenDoor(x2 - 3, y1, z1, BadMagic.Door.NORTHBYNORTHWEST); 
					if (doorWest)
						chunk.setWoodenDoor(x1, y1, z2 - 3, BadMagic.Door.WESTBYNORTHWEST); 

					if (hallSouth)
						chunk.setWoodenDoor(x2 - 2, y1, z2, BadMagic.Door.SOUTHBYSOUTHEAST); 
					if (hallEast)
						chunk.setWoodenDoor(x2, y1, z2 - 2, BadMagic.Door.EASTBYSOUTHEAST); 
				}
			}
		}
	}
	
	public void drawWaterTower(CityWorldGenerator generator, RealBlocks chunk, int x, int y, int z, Odds odds) {
		int y1 = y;
		int y2 = y1 + 7;
		int y3 = y2 + 6;

		Material legMat = generator.materialProvider.itemsSelectMaterial_WaterTowers.getRandomMaterial(odds, Material.CLAY);
		Material topMat = generator.materialProvider.itemsSelectMaterial_WaterTowers.getRandomMaterial(odds, Material.STAINED_CLAY);
		
		DyeColor platformColor = odds.getRandomDarkColor();
		DyeColor tankColor = odds.getRandomLightColor();
		DyeColor topColor = odds.getRandomColor();
		
		if (legMat == Material.STAINED_CLAY) {
			DyeColor legColor = odds.getRandomColor();
			chunk.setBlocksTypeAndColor(x, y1, y3, z + 1, legMat, legColor);
			chunk.setBlocksTypeAndColor(x + 1, y1, y3, z, legMat, legColor);
			
			chunk.setBlocksTypeAndColor(x + 6, y1, y3, z, legMat, legColor);
			chunk.setBlocksTypeAndColor(x + 7, y1, y3, z + 1, legMat, legColor);
			
			chunk.setBlocksTypeAndColor(x, y1, y3, z + 6, legMat, legColor);
			chunk.setBlocksTypeAndColor(x + 1, y1, y3, z + 7, legMat, legColor);

			chunk.setBlocksTypeAndColor(x + 7, y1, y3, z + 6, legMat, legColor);
			chunk.setBlocksTypeAndColor(x + 6, y1, y3, z + 7, legMat, legColor);
			
		} else {
			chunk.setBlocks(x, y1, y3, z + 1, legMat);
			chunk.setBlocks(x + 1, y1, y3, z, legMat);
			
			chunk.setBlocks(x + 6, y1, y3, z, legMat);
			chunk.setBlocks(x + 7, y1, y3, z + 1, legMat);
			
			chunk.setBlocks(x, y1, y3, z + 6, legMat);
			chunk.setBlocks(x + 1, y1, y3, z + 7, legMat);

			chunk.setBlocks(x + 7, y1, y3, z + 6, legMat);
			chunk.setBlocks(x + 6, y1, y3, z + 7, legMat);
		}
		
		if (topMat == Material.STAINED_CLAY) {
			chunk.setCircle(x + 4, x + 4, 3, y3 - 1, topMat, topColor, true);
			chunk.setCircle(x + 4, x + 4, 5, y3, topMat, platformColor, true);
			chunk.setCircle(x + 4, x + 4, 4, y3 + 1, y3 + 5, topMat, tankColor, false);
			chunk.setCircle(x + 4, x + 4, 4, y3 + 5, topMat, tankColor, true);
			chunk.setCircle(x + 4, x + 4, 3, y3 + 6, topMat, topColor, true);
		} else {
			chunk.setCircle(x + 4, x + 4, 3, y3 - 1, topMat, true);
			chunk.setCircle(x + 4, x + 4, 5, y3, topMat, true);
			chunk.setCircle(x + 4, x + 4, 4, y3 + 1, y3 + 5, topMat, false);
			chunk.setCircle(x + 4, x + 4, 4, y3 + 5, topMat, true);
			chunk.setCircle(x + 4, x + 4, 3, y3 + 6, topMat, true);
		}

		if (generator.settings.includeAbovegroundFluids) {
			chunk.setCircle(x + 4, x + 4, 3, y3 + 1, y3 + 2 + odds.getRandomInt(3), generator.oreProvider.fluidFluidMaterial, true);
		}
	}
}
