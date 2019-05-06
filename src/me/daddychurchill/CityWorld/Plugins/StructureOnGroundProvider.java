package me.daddychurchill.CityWorld.Plugins;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Bisected.Half;
import org.bukkit.block.data.type.Stairs;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plugins.LootProvider.LootLocation;
import me.daddychurchill.CityWorld.Support.Colors;
import me.daddychurchill.CityWorld.Support.Colors.ColorSet;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.Trees;

public class StructureOnGroundProvider extends Provider {

//	private static RoomProvider contentsKitchen = new HouseKitchens();
//	private static RoomProvider contentsBedroom = new HouseBedrooms();
//	private static RoomProvider contentsDiningRoom = new HouseDiningRooms();
//	private static RoomProvider contentsLivingRoom = new HouseLivingRooms();

	private StructureOnGroundProvider() {
		super();

	}

	public static StructureOnGroundProvider loadProvider(CityWorldGenerator generator) {
		// for now
		return new StructureOnGroundProvider();
	}

	private final static double oddsOfFurnace = Odds.oddsSomewhatUnlikely;
	private final static double oddsOfCraftingTable = Odds.oddsSomewhatUnlikely;

	public void generateShed(CityWorldGenerator generator, RealBlocks chunk, DataContext context, Odds odds, int x,
			int y, int z, int radius, LootLocation location) {
		generateShed(generator, chunk, context, odds, x, y, z, radius, location, location);
	}

	public void generateShed(CityWorldGenerator generator, RealBlocks chunk, DataContext context, Odds odds, int x,
			int y, int z, int radius, LootLocation location, LootLocation other) {
		int x1 = x - radius;
		int x2 = x + radius + 1;
		int z1 = z - radius;
		int z2 = z + radius + 1;
		int y1 = y;
		int y2 = y + DataContext.FloorHeight - 1;
		int xR = x2 - x1 - 2;
		int zR = z2 - z1 - 2;

		Material wallMat = generator.materialProvider.itemsSelectMaterial_ShedWalls.getRandomMaterial(odds,
				Material.COBBLESTONE);
		Material roofMat = generator.materialProvider.itemsSelectMaterial_ShedRoofs.getRandomMaterial(odds,
				Material.COBBLESTONE);

		chunk.setWalls(x1, x2, y1, y2, z1, z2, wallMat);
		chunk.setBlocks(x1 + 1, x2 - 1, y2, z1 + 1, z2 - 1, roofMat);

		switch (odds.getRandomInt(4)) {
		case 0: // north
			chunk.setDoor(x1 + odds.getRandomInt(xR) + 1, y1, z1, Material.BIRCH_DOOR, BlockFace.NORTH_NORTH_EAST);
			chunk.setBlock(x1 + odds.getRandomInt(xR) + 1, y1 + 1, z2 - 1, materialGlass);
			placeShedTable(generator, chunk, odds, x1 + odds.getRandomInt(xR) + 1, y1, z2 - 2, BlockFace.SOUTH);
			placeShedChest(generator, chunk, odds, x1 - 1, y1, z1 + odds.getRandomInt(zR) + 1, BlockFace.WEST,
					location);
			placeShedChest(generator, chunk, odds, x2, y1, z1 + odds.getRandomInt(zR) + 1, BlockFace.EAST, other);
			break;
		case 1: // south
			chunk.setDoor(x1 + odds.getRandomInt(xR) + 1, y1, z2 - 1, Material.BIRCH_DOOR, BlockFace.SOUTH_SOUTH_WEST);
			chunk.setBlock(x1 + odds.getRandomInt(xR) + 1, y1 + 1, z1, materialGlass);
			placeShedTable(generator, chunk, odds, x1 + odds.getRandomInt(xR) + 1, y1, z1 + 1, BlockFace.NORTH);
			placeShedChest(generator, chunk, odds, x1 - 1, y1, z1 + odds.getRandomInt(zR) + 1, BlockFace.WEST,
					location);
			placeShedChest(generator, chunk, odds, x2, y1, z1 + odds.getRandomInt(zR) + 1, BlockFace.EAST, other);
			break;
		case 2: // west
			chunk.setDoor(x1, y1, z1 + odds.getRandomInt(zR) + 1, Material.BIRCH_DOOR, BlockFace.WEST_NORTH_WEST);
			chunk.setBlock(x2 - 1, y1 + 1, z1 + odds.getRandomInt(zR) + 1, materialGlass);
			placeShedTable(generator, chunk, odds, x2 - 2, y1, z1 + odds.getRandomInt(zR) + 1, BlockFace.EAST);
			placeShedChest(generator, chunk, odds, x1 + odds.getRandomInt(xR) + 1, y1, z1 - 1, BlockFace.NORTH,
					location);
			placeShedChest(generator, chunk, odds, x1 + odds.getRandomInt(xR) + 1, y1, z2, BlockFace.SOUTH, other);
			break;
		default: // east
			chunk.setDoor(x1, y1, z1 + odds.getRandomInt(zR) + 1, Material.BIRCH_DOOR, BlockFace.EAST_SOUTH_EAST);
			chunk.setBlock(x2 - 1, y1 + 1, z1 + odds.getRandomInt(zR) + 1, materialGlass);
			placeShedTable(generator, chunk, odds, x1 + 1, y1, z1 + odds.getRandomInt(zR) + 1, BlockFace.WEST);
			placeShedChest(generator, chunk, odds, x1 + odds.getRandomInt(xR) + 1, y1, z1 - 1, BlockFace.NORTH,
					location);
			placeShedChest(generator, chunk, odds, x1 + odds.getRandomInt(xR) + 1, y1, z2, BlockFace.SOUTH, other);
			break;
		}
	}

	private void placeShedTable(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int x, int y, int z,
			BlockFace direction) {
		if (odds.playOdds(oddsOfFurnace))
			chunk.setBlock(x, y, z, Material.FURNACE, direction);
		else if (odds.playOdds(oddsOfCraftingTable))
			chunk.setBlock(x, y, z, Material.CRAFTING_TABLE);
		else {
			chunk.setBlock(x, y, z, Material.SPRUCE_FENCE);
			chunk.setBlock(x, y + 1, z, Material.BIRCH_PRESSURE_PLATE);
		}
	}

	private void placeShedChest(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int x, int y, int z,
			BlockFace direction, LootLocation location) {
		switch (direction) {
		default:
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

	private final static Material matWindow = Material.GLASS_PANE;
	private final static Material matPole = Material.SPRUCE_FENCE;

	public void generateCampground(CityWorldGenerator generator, RealBlocks chunk, DataContext context, Odds odds,
			int baseY) {

		// what are we made of?
		boolean matCamo = odds.playOdds(Odds.oddsSomewhatUnlikely);
		Colors colors = new Colors(odds);
		Material matBed = colors.getBed();
		if (matCamo)
			if (colors.getRandomColor() == DyeColor.PINK)
				colors.setColors(ColorSet.PINK);
			else
				colors.setColors(ColorSet.GREEN);
		else
			colors.fixColor();

		// direction?
		if (odds.flipCoin()) {

			// north/south tent first
			for (int z = 3; z < 9; z++) {
				chunk.setBlock(3, baseY, z, colors.getWool());
				chunk.setBlock(4, baseY + 1, z, colors.getWool());
				chunk.setBlock(5, baseY + 2, z, colors.getWool());
				chunk.setBlock(6, baseY + 3, z, colors.getWool());
				chunk.setBlock(7, baseY + 2, z, colors.getWool());
				chunk.setBlock(8, baseY + 1, z, colors.getWool());
				chunk.setBlock(9, baseY, z, colors.getWool());
			}

			// back wall
			chunk.setBlock(4, baseY, 3, colors.getWool());
			chunk.setBlock(5, baseY, 3, colors.getWool());
			chunk.setBlock(5, baseY + 1, 3, colors.getWool());
			chunk.setBlock(6, baseY, 3, colors.getWool());
			chunk.setBlock(6, baseY + 1, 3, matWindow, BlockFace.EAST, BlockFace.WEST);
			chunk.setBlock(6, baseY + 2, 3, colors.getWool());
			chunk.setBlock(7, baseY + 1, 3, colors.getWool());
			chunk.setBlock(7, baseY, 3, colors.getWool());
			chunk.setBlock(8, baseY, 3, colors.getWool());

			// post
			chunk.setBlocks(6, baseY, baseY + 3, 8, matPole);

			// beds
			if (odds.playOdds(Odds.oddsPrettyLikely))
				chunk.setBed(5, baseY, 4, matBed, BlockFace.SOUTH);
			if (odds.playOdds(Odds.oddsPrettyLikely))
				chunk.setBed(7, baseY, 4, matBed, BlockFace.SOUTH);
		} else {
			// north/south tent first
			for (int x = 3; x < 9; x++) {
				chunk.setBlock(x, baseY, 3, colors.getWool());
				chunk.setBlock(x, baseY + 1, 4, colors.getWool());
				chunk.setBlock(x, baseY + 2, 5, colors.getWool());
				chunk.setBlock(x, baseY + 3, 6, colors.getWool());
				chunk.setBlock(x, baseY + 2, 7, colors.getWool());
				chunk.setBlock(x, baseY + 1, 8, colors.getWool());
				chunk.setBlock(x, baseY, 9, colors.getWool());
			}

			// back wall
			chunk.setBlock(3, baseY, 4, colors.getWool());
			chunk.setBlock(3, baseY, 5, colors.getWool());
			chunk.setBlock(3, baseY + 1, 5, colors.getWool());
			chunk.setBlock(3, baseY, 6, colors.getWool());
			chunk.setBlock(3, baseY + 1, 6, matWindow, BlockFace.NORTH, BlockFace.SOUTH);
			chunk.setBlock(3, baseY + 2, 6, colors.getWool());
			chunk.setBlock(3, baseY + 1, 7, colors.getWool());
			chunk.setBlock(3, baseY, 7, colors.getWool());
			chunk.setBlock(3, baseY, 8, colors.getWool());

			// post
			chunk.setBlocks(8, baseY, baseY + 3, 6, matPole);

			// beds
			if (odds.playOdds(Odds.oddsPrettyLikely))
				chunk.setBed(4, baseY, 5, matBed, BlockFace.EAST);
			if (odds.playOdds(Odds.oddsPrettyLikely))
				chunk.setBed(4, baseY, 7, matBed, BlockFace.EAST);
		}

		// now the fire pit
		generateFirePit(generator, chunk, odds, 10, baseY, 10);
//		if (odds.playOdds(Odds.oddsPrettyLikely)) {
//			// stairs around the fire
////			chunk.setStair(11, baseY - 1, 10, matFireRing, BlockFace.SOUTH);
////			chunk.setStair(12, baseY - 1, 11, matFireRing, BlockFace.WEST);
////			chunk.setStair(11, baseY - 1, 12, matFireRing, BlockFace.NORTH);
////			chunk.setStair(10, baseY - 1, 11, matFireRing, BlockFace.EAST);
////			chunk.setStair(10, baseY - 1, 10, matFireRing, BlockFace.SOUTH, Stairs.Shape.OUTER_LEFT);
////			chunk.setStair(12, baseY - 1, 10, matFireRing, BlockFace.WEST, Stairs.Shape.OUTER_LEFT);
////			chunk.setStair(12, baseY - 1, 12, matFireRing, BlockFace.NORTH, Stairs.Shape.OUTER_LEFT);
////			chunk.setStair(10, baseY - 1, 12, matFireRing, BlockFace.EAST, Stairs.Shape.OUTER_LEFT);
//
//			chunk.setStair(11, baseY - 1, 10, matFireRing, BlockFace.NORTH);
//			chunk.setStair(12, baseY - 1, 11, matFireRing, BlockFace.EAST);
//			chunk.setStair(11, baseY - 1, 12, matFireRing, BlockFace.SOUTH);
//			chunk.setStair(10, baseY - 1, 11, matFireRing, BlockFace.WEST);
//			chunk.setStair(10, baseY - 1, 10, matFireRing, BlockFace.NORTH, Stairs.Shape.INNER_LEFT);
//			chunk.setStair(12, baseY - 1, 10, matFireRing, BlockFace.EAST, Stairs.Shape.INNER_LEFT);
//			chunk.setStair(12, baseY - 1, 12, matFireRing, BlockFace.SOUTH, Stairs.Shape.INNER_LEFT);
//			chunk.setStair(10, baseY - 1, 12, matFireRing, BlockFace.WEST, Stairs.Shape.INNER_LEFT);
//
//			// and the fire itself
////			chunk.setBlock(11, baseY - 1, 11, matFireBase);
//			if (odds.playOdds(Odds.oddsPrettyLikely)) {
////				chunk.clearBlocks(9, 14, baseY, 9, 14); // we do this to keep the grass and such away from the fire so
////				// it doesn't go firebug on us
//				if (odds.flipCoin())
//					chunk.setBlock(11, baseY - 2, 11, matFireSmoke);
//				else
//					chunk.setBlock(11, baseY - 2, 11, matFireBase);
//				chunk.setBlock(11, baseY - 1, 11, matFire, generator.getSettings().includeFires);
//			}
//		}

		// and the logs
		Trees trees = new Trees(odds);
		Material logMat = trees.getRandomWoodLog();
		if (odds.playOdds(Odds.oddsPrettyLikely)) {
			chunk.setBlock(11, baseY, 8, logMat, BlockFace.EAST);
			chunk.setBlock(12, baseY, 8, logMat, BlockFace.EAST);
		}
		if (odds.playOdds(Odds.oddsPrettyLikely)) {
			chunk.setBlock(8, baseY, 11, logMat, BlockFace.NORTH);
			chunk.setBlock(8, baseY, 12, logMat, BlockFace.NORTH);
		}
	}

	private final static Material matFire = Material.CAMPFIRE;
	private final static Material matFireSmoke = Material.HAY_BLOCK;
	private final static Material matFireBase = Material.COBBLESTONE;
	private final static Material matFireRing = Material.COBBLESTONE_STAIRS;
	public void generateFirePit(CityWorldGenerator generator, RealBlocks chunk, Odds odds, int x, int baseY, int z) {

		// now the fire pit
		chunk.clearBlocks(x, x + 3, baseY, baseY + 3, z, z + 3);

		chunk.setStair(x + 1, baseY - 1, z, matFireRing, BlockFace.NORTH);
		chunk.setStair(x + 2, baseY - 1, z + 1, matFireRing, BlockFace.EAST);
		chunk.setStair(x + 1, baseY - 1, z + 2, matFireRing, BlockFace.SOUTH);
		chunk.setStair(x, baseY - 1, z + 1, matFireRing, BlockFace.WEST);
		chunk.setStair(x, baseY - 1, z, matFireRing, BlockFace.NORTH, Stairs.Shape.INNER_LEFT);
		chunk.setStair(x + 2, baseY - 1, z, matFireRing, BlockFace.EAST, Stairs.Shape.INNER_LEFT);
		chunk.setStair(x + 2, baseY - 1, z + 2, matFireRing, BlockFace.SOUTH, Stairs.Shape.INNER_LEFT);
		chunk.setStair(x, baseY - 1, z + 2, matFireRing, BlockFace.WEST, Stairs.Shape.INNER_LEFT);

		if (odds.flipCoin())
			chunk.setBlock(x + 1, baseY - 2, z + 1, matFireSmoke);
		else
			chunk.setBlock(x + 1, baseY - 2, z + 1, matFireBase);
		chunk.setBlock(x + 1, baseY - 1, z + 1, matFire, generator.getSettings().includeFires);
	}

	private enum HouseRoofStyle {
		FLAT, NORTHSOUTH, WESTEAST, ANGLED
	}

	public int generateRuralShack(CityWorldGenerator generator, RealBlocks chunk, DataContext context, Odds odds,
			int baseY, int roomWidth) {

		// what are we made of?
		Material matWall = generator.materialProvider.itemsSelectMaterial_ShackWalls.getRandomMaterial(odds,
				Material.COBBLESTONE);
		Material matFloor = generator.materialProvider.itemsSelectMaterial_ShackWalls.getRandomMaterial(odds,
				Material.COBBLESTONE);
		Material matRoof = generator.materialProvider.itemsSelectMaterial_ShackRoofs.getRandomMaterial(odds,
				Material.COBBLESTONE);
		Material matCeiling = matRoof;
		HouseRoofStyle styleRoof = pickRoofStyle(odds);
		int floors = 1;

		// chunk.setWalls(2, 13, baseY, baseY + ContextData.FloorHeight, 2, 13,
		// Material.SPRUCE_WOOD);
		generateColonial(generator, chunk, context, odds, baseY, matFloor, matWall, matCeiling, matRoof, floors,
				roomWidth, roomWidth, styleRoof, false);
		return floors;
	}

	public int generateHouse(CityWorldGenerator generator, RealBlocks chunk, DataContext context, Odds odds, int baseY,
			int maxFloors, int maxRoomWidth) {

		// what are we made of?
		Material matWall = generator.materialProvider.itemsSelectMaterial_HouseWalls.getRandomMaterial(odds,
				Material.COBBLESTONE);
		Material matFloor = generator.materialProvider.itemsSelectMaterial_HouseFloors.getRandomMaterial(odds,
				Material.COBBLESTONE);
		Material matCeiling = generator.materialProvider.itemsSelectMaterial_HouseCeilings.getRandomMaterial(odds,
				Material.COBBLESTONE);
		Material matRoof = generator.materialProvider.itemsSelectMaterial_HouseRoofs.getRandomMaterial(odds,
				Material.COBBLESTONE);
		HouseRoofStyle styleRoof = pickRoofStyle(odds);
		int floors = odds.getRandomInt(maxFloors) + 1;

		// TODO add bed
		// TODO add kitchen
		// TODO add living room
		// TODO add split level house style

		// draw the house
		generateColonial(generator, chunk, context, odds, baseY, matFloor, matWall, matCeiling, matRoof, floors,
				MinSize, maxRoomWidth, styleRoof, true);
		return floors;
	}

	public int generateHouse(CityWorldGenerator generator, RealBlocks chunk, DataContext context, Odds odds, int baseY,
			int maxFloors) {
		return generateHouse(generator, chunk, context, odds, baseY, maxFloors, MaxSize);
	}

	private void generateColonial(CityWorldGenerator generator, RealBlocks chunk, DataContext context, Odds odds,
			int baseY, Material matFloor, Material matWall, Material matCeiling, Material matRoof, int floors,
			int minRoomWidth, int maxRoomWidth, HouseRoofStyle styleRoof, boolean allowMissingRooms) {

		Trees trees = new Trees(odds);
		Material matTrapDoor = trees.getRandomWoodTrapDoor();

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
					rooms[f][x][z] = new Room(thisRoomMissing, thisRoomWidthZ, thisRoomWidthX, thisRoomHasWalls,
							thisRoomStyle, matTrapDoor);

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

				// if one of the side rooms is missing, make it not missing and make the
				// opposite one is
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
						drawRoom(generator, chunk, context, odds, rooms, f, floors, x, z, roomOffsetX, roomOffsetZ,
								baseY, thisFloor, matWall, matCeiling, matRoof);
				}
			}

			// found an entry
			if (entryX != -1) {
				drawRoom(generator, chunk, context, odds, rooms, f, floors, entryX, entryZ, roomOffsetX, roomOffsetZ,
						baseY, thisFloor, matWall, matCeiling, matRoof);
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
							if (chunk.isOfTypes(x, yAt, z, matRoof, matTrapDoor)
									&& chunk.isOfTypes(x - 1, yAt, z, matRoof, matTrapDoor)
									&& chunk.isOfTypes(x + 1, yAt, z, matRoof, matTrapDoor)
									&& chunk.isOfTypes(x, yAt, z - 1, matRoof, matTrapDoor)
									&& chunk.isOfTypes(x, yAt, z + 1, matRoof, matTrapDoor))
								chunk.setBlock(x, yAt + 1, z, matRoof);
						} else {
							if (chunk.isType(x, yAt, z, matRoof) && chunk.isType(x - 1, yAt, z, matRoof)
									&& chunk.isType(x + 1, yAt, z, matRoof) && chunk.isType(x, yAt, z - 1, matRoof)
									&& chunk.isType(x, yAt, z + 1, matRoof))
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
							if (chunk.isOfTypes(x, yAt, z, matRoof, matTrapDoor)
									&& chunk.isOfTypes(x - 1, yAt, z, matRoof, matTrapDoor)
									&& chunk.isOfTypes(x + 1, yAt, z, matRoof, matTrapDoor))
								chunk.setBlock(x, yAt + 1, z, matRoof);
						} else {
							if (chunk.isType(x, yAt, z, matRoof) && chunk.isType(x - 1, yAt, z, matRoof)
									&& chunk.isType(x + 1, yAt, z, matRoof))
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
							if (chunk.isOfTypes(x, yAt, z, matRoof, matTrapDoor)
									&& chunk.isOfTypes(x, yAt, z - 1, matRoof, matTrapDoor)
									&& chunk.isOfTypes(x, yAt, z + 1, matRoof, matTrapDoor))
								chunk.setBlock(x, yAt + 1, z, matRoof);
						} else {
							if (chunk.isType(x, yAt, z, matRoof) && chunk.isType(x, yAt, z - 1, matRoof)
									&& chunk.isType(x, yAt, z + 1, matRoof))
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
						if (chunk.isOfTypes(x, yAt, z, matRoof, matTrapDoor)
								&& (chunk.isEmpty(x - 1, yAt, z) || chunk.isEmpty(x + 1, yAt, z)
								|| chunk.isEmpty(x, yAt, z - 1) || chunk.isEmpty(x, yAt, z + 1)))
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
						if (/* !chunk.isEmpty(x, yAt + 1, z)) */
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
							if (chunk.isEmpty(x - 1, yAt, z) || chunk.isEmpty(x + 1, yAt, z)
									|| chunk.isEmpty(x, yAt, z - 1) || chunk.isEmpty(x, yAt, z + 1))
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

	private void drawRoom(CityWorldGenerator generator, RealBlocks chunk, DataContext context, Odds odds,
			Room[][][] rooms, int floor, int floors, int x, int z, int roomOffsetX, int roomOffsetZ, int baseY,
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
	private final static Material materialFence = Material.SPRUCE_FENCE;
	private final static Material materialStair = Material.BIRCH_STAIRS;
	private final static Material materialUnderStairs = Material.BIRCH_PLANKS;

	private final static int MinSize = 4;
	private final static int MaxSize = 6;
	private final static int MissingRoomOdds = 5; // 1/n of the time a room is missing

	// the description of a single room
	private final static class Room {
		public enum Style {
			BED, KITCHEN, DINING, ENTRY, LIVING
		}

		int widthX;
		int widthZ;
		boolean missing;
		final boolean walls;
		Style style;
		final Material trapDoor;

		Room(boolean aMissing, int aWidthX, int aWidthZ, boolean aWalls, Style aStyle, Material aTrapDoor) {
			super();

			missing = aMissing;
			widthX = aWidthX;
			widthZ = aWidthZ;
			walls = aWalls;
			style = aStyle;
			trapDoor = aTrapDoor;
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

		void Locate(DataContext context, int floor, int floors, int x, int z, int roomOffsetX,
				int roomOffsetZ, int baseY) {
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

		void DrawWalls(RealBlocks chunk, DataContext context, int floor, int floors, int x, int z,
				int roomOffsetX, int roomOffsetZ, int baseY, Material matWall) {

			// find ourselves
			Locate(context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY);

			// draw the walls
			if (roomEast) {
				chunk.setBlocks(x2, x2 + 1, y1, y2, z1, z2 + 1, matWall); // east wall
				chunk.setBlocks(x2, x2 + 1, y1 + 1, y2 - 1, z1 + 1, z2, materialGlass); // eastern window

				if (roomSouth) {
					chunk.setBlocks(x1, x2 + 1, y1, y2, z2, z2 + 1, matWall); // south wall
					chunk.setBlocks(x1 + 1, x2, y1 + 1, y2 - 1, z2, z2 + 1, materialGlass); // southern window

					chunk.setBlocks(x1, x2 + 1, y1, y2, z1, z1 + 1, matWall); // north wall
					chunk.setBlocks(x1, x1 + 1, y1, y2, z1, z2 + 1, matWall); // west wall

				} else {
					chunk.setBlocks(x1, x2 + 1, y1, y2, z1, z1 + 1, matWall); // north wall
					chunk.setBlocks(x1 + 1, x2, y1 + 1, y2 - 1, z1, z1 + 1, materialGlass); // northern window

					chunk.setBlocks(x1, x2 + 1, y1, y2, z2, z2 + 1, matWall); // south wall
					chunk.setBlocks(x1, x1 + 1, y1, y2, z1, z2 + 1, matWall); // west wall

				}
			} else {
				chunk.setBlocks(x1, x1 + 1, y1, y2, z1, z2 + 1, matWall); // west wall
				chunk.setBlocks(x1, x1 + 1, y1 + 1, y2 - 1, z1 + 1, z2, materialGlass); // western window

				if (roomSouth) {
					chunk.setBlocks(x1, x2 + 1, y1, y2, z2, z2 + 1, matWall); // south wall
					chunk.setBlocks(x1 + 1, x2, y1 + 1, y2 - 1, z2, z2 + 1, materialGlass); // southern window

					chunk.setBlocks(x1, x2 + 1, y1, y2, z1, z1 + 1, matWall); // north wall
					chunk.setBlocks(x2, x2 + 1, y1, y2, z1, z2 + 1, matWall); // east wall

				} else {
					chunk.setBlocks(x1, x2 + 1, y1, y2, z1, z1 + 1, matWall); // north wall
					chunk.setBlocks(x1 + 1, x2, y1 + 1, y2 - 1, z1, z1 + 1, materialGlass); // northern window

					chunk.setBlocks(x1, x2 + 1, y1, y2, z2, z2 + 1, matWall); // south wall
					chunk.setBlocks(x2, x2 + 1, y1, y2, z1, z2 + 1, matWall); // east wall
				}
			}
		}

		void DrawFloor(RealBlocks chunk, DataContext context, int floor, int floors, int x, int z,
				int roomOffsetX, int roomOffsetZ, int baseY, Material matFloor) {

			// find ourselves
			Locate(context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY);

			// put the rug down
			chunk.setBlocks(x1, x2 + 1, y1 - 1, y1, z1, z2 + 1, matFloor);
		}

		void DrawCeiling(RealBlocks chunk, DataContext context, int floor, int floors, int x, int z,
				int roomOffsetX, int roomOffsetZ, int baseY, Material matCeiling) {

			// find ourselves
			Locate(context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY);

			// put the rug down
			chunk.setBlocks(x1, x2 + 1, y2, y2 + 1, z1, z2 + 1, matCeiling);
		}

		void DrawRoof(RealBlocks chunk, DataContext context, int floor, int floors, int x, int z,
				int roomOffsetX, int roomOffsetZ, int baseY, Material matRoof) {

			// find ourselves
			Locate(context, floor, floors, x, z, roomOffsetX, roomOffsetZ, baseY);

			// put roof on top
			// TODO need fancier roofs
			chunk.setBlocks(x1, x2 + 1, y2, y2 + 1, z1, z2 + 1, matRoof);
		}

		void DrawRailing(RealBlocks chunk) {

			// only if we have found ourselves
			if (located) {

				// north and south ones
				chunk.setEmptyBlocks(x1 + 1, x2, y2 + 1, z1, z1 + 1, materialFence, BlockFace.EAST, BlockFace.WEST);
				chunk.setEmptyBlocks(x1 + 1, x2, y2 + 1, z2, z2 + 1, materialFence, BlockFace.EAST, BlockFace.WEST);

				// west and east ones
				chunk.setEmptyBlocks(x1, x1 + 1, y2 + 1, z1 + 1, z2, materialFence, BlockFace.NORTH, BlockFace.SOUTH);
				chunk.setEmptyBlocks(x2, x2 + 1, y2 + 1, z1 + 1, z2, materialFence, BlockFace.NORTH, BlockFace.SOUTH);

				// corners
				chunk.setEmptyBlock(x1, y2 + 1, z1, materialFence, BlockFace.SOUTH, BlockFace.EAST);
				chunk.setEmptyBlock(x1, y2 + 1, z2, materialFence, BlockFace.NORTH, BlockFace.EAST);
				chunk.setEmptyBlock(x2, y2 + 1, z1, materialFence, BlockFace.SOUTH, BlockFace.WEST);
				chunk.setEmptyBlock(x2, y2 + 1, z2, materialFence, BlockFace.NORTH, BlockFace.WEST);
			}
		}

		void DrawStyle(CityWorldGenerator generator, RealBlocks chunk, DataContext context, Odds odds,
				int floor, int floors, int x, int z, int roomOffsetX, int roomOffsetZ, int baseY) {

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

//				chunk.setBlock((x2 - x1) / 2 + x1, y1, (z2 - z1) / 2 + z1, Material.SPRUCE_FENCE);
//				chunk.setBlock((x2 - x1) / 2 + x1, y1 + 1, (z2 - z1) / 2 + z1, Material.BIRCH_PRESSURE_PLATE);
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

							chunk.setBlock(x1 + 2, y1 + 3, z1 + 1, materialStair, BlockFace.WEST);
							chunk.setBlock(x1 + 2, y1 + 2, z1 + 1, materialStair, BlockFace.EAST, Half.TOP);

							chunk.setBlock(x1 + 3, y1 + 2, z1 + 1, materialStair, BlockFace.WEST);
							chunk.setBlock(x1 + 3, y1 + 1, z1 + 1, materialStair, BlockFace.EAST, Half.TOP);

							chunk.setBlock(x1 + 4, y1 + 1, z1 + 1, materialStair, BlockFace.WEST);
							chunk.setBlock(x1 + 4, y1, z1 + 1, materialStair, BlockFace.EAST, Half.TOP);

							chunk.setBlock(x1 + 5, y1, z1 + 1, materialUnderStairs);
							chunk.setBlock(x1 + 5, y1, z1 + 2, materialStair, BlockFace.NORTH);

//							chunk.setBlocks(x1 + 3, y1, y2 + 50, z1 + 3, Material.SPRUCE_FENCE);
//							chunk.setBlock(x1 + 3, y2 + 50, z1 + 3, Material.GOLD_BLOCK);
						} else {
							chunk.setBlocks(x1 + 1, x1 + 2, y2, z1 + 1, z2, materialAir);

							chunk.setBlock(x1 + 1, y1 + 3, z2 - 1, materialUnderStairs);

							chunk.setBlock(x1 + 1, y1 + 3, z2 - 2, materialStair, BlockFace.SOUTH);
							chunk.setBlock(x1 + 1, y1 + 2, z2 - 2, materialStair, BlockFace.NORTH, Half.TOP);

							chunk.setBlock(x1 + 1, y1 + 2, z2 - 3, materialStair, BlockFace.SOUTH);
							chunk.setBlock(x1 + 1, y1 + 1, z2 - 3, materialStair, BlockFace.NORTH, Half.TOP);

							chunk.setBlock(x1 + 1, y1 + 1, z2 - 4, materialStair, BlockFace.SOUTH);
							chunk.setBlock(x1 + 1, y1, z2 - 4, materialStair, BlockFace.NORTH, Half.TOP);

							chunk.setBlock(x1 + 1, y1, z2 - 5, materialUnderStairs);
							chunk.setBlock(x1 + 2, y1, z2 - 5, materialStair, BlockFace.WEST);

//							chunk.setBlocks(x1 + 3, y1, y2 + 50, z1 + 3, Material.SPRUCE_FENCE);
//							chunk.setBlock(x1 + 3, y2 + 50, z1 + 3, Material.LAPIS_BLOCK);
						}
					} else {
						if (roomSouth) {
							chunk.setBlocks(x2 - 1, x2, y2, z1 + 1, z2, materialAir);

							chunk.setBlock(x2 - 1, y1 + 3, z1 + 1, materialUnderStairs);

							chunk.setBlock(x2 - 1, y1 + 3, z1 + 2, materialStair, BlockFace.NORTH);
							chunk.setBlock(x2 - 1, y1 + 2, z1 + 2, materialStair, BlockFace.SOUTH, Half.TOP);

							chunk.setBlock(x2 - 1, y1 + 2, z1 + 3, materialStair, BlockFace.NORTH);
							chunk.setBlock(x2 - 1, y1 + 1, z1 + 3, materialStair, BlockFace.SOUTH, Half.TOP);

							chunk.setBlock(x2 - 1, y1 + 1, z1 + 4, materialStair, BlockFace.NORTH);
							chunk.setBlock(x2 - 1, y1, z1 + 4, materialStair, BlockFace.SOUTH, Half.TOP);

							chunk.setBlock(x2 - 1, y1, z1 + 5, materialUnderStairs);
							chunk.setBlock(x2 - 2, y1, z1 + 5, materialStair, BlockFace.EAST);

//							chunk.setBlocks(x1 + 3, y1, y2 + 50, z1 + 3, Material.SPRUCE_FENCE);
//							chunk.setBlock(x1 + 3, y2 + 50, z1 + 3, Material.DIAMOND_BLOCK);
						} else {
							chunk.setBlocks(x1 + 1, x2, y2, z2 - 1, z2, materialAir);

							chunk.setBlock(x2 - 1, y1 + 3, z2 - 1, materialUnderStairs);

							chunk.setBlock(x2 - 2, y1 + 3, z2 - 1, materialStair, BlockFace.EAST);
							chunk.setBlock(x2 - 2, y1 + 2, z2 - 1, materialStair, BlockFace.WEST, Half.TOP);

							chunk.setBlock(x2 - 3, y1 + 2, z2 - 1, materialStair, BlockFace.EAST);
							chunk.setBlock(x2 - 3, y1 + 1, z2 - 1, materialStair, BlockFace.WEST, Half.TOP);

							chunk.setBlock(x2 - 4, y1 + 1, z2 - 1, materialStair, BlockFace.EAST);
							chunk.setBlock(x2 - 4, y1, z2 - 1, materialStair, BlockFace.WEST, Half.TOP);

							chunk.setBlock(x2 - 5, y1, z2 - 1, materialUnderStairs);
							chunk.setBlock(x2 - 5, y1, z2 - 2, materialStair, BlockFace.SOUTH);

//							chunk.setBlocks(x1 + 3, y1, y2 + 50, z1 + 3, Material.SPRUCE_FENCE);
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
							chunk.setLadder(x1 + 1, y1, y1 + 3, z1 + 1, BlockFace.SOUTH); // fixed
							chunk.setBlock(x1 + 1, y2, z1 + 1, trapDoor, BlockFace.NORTH);

						} else {
							chunk.setLadder(x1 + 1, y1, y1 + 3, z2 - 1, BlockFace.EAST); // fixed
							chunk.setBlock(x1 + 1, y2, z2 - 1, trapDoor, BlockFace.WEST);

						}
					} else {
						if (roomSouth) {
							chunk.setLadder(x2 - 1, y1, y1 + 3, z1 + 1, BlockFace.WEST); // fixed
							chunk.setBlock(x2 - 1, y2, z1 + 1, trapDoor, BlockFace.EAST);

						} else {
							chunk.setLadder(x2 - 1, y1, y1 + 3, z2 - 1, BlockFace.NORTH); // fixed
							chunk.setBlock(x2 - 1, y2, z2 - 1, trapDoor, BlockFace.SOUTH);

						}
					}
				}

				break;
			case LIVING:

//				chunk.setBlock((x2 - x1) / 2 + x1, y1, (z2 - z1) / 2 + z1, Material.DIAMOND_BLOCK);
				break;
			case BED:
//				chunk.setBlock((x2 - x1) / 2 + x1, y1, (z2 - z1) / 2 + z1, Material.WHITE_WOOL);
				break;
			}

			// draw the walls
			if (roomEast) {
				if (roomSouth) {
					if (doorSouth)
						chunk.setDoor(x1 + 3, y1, z2, Material.BIRCH_DOOR, BlockFace.SOUTH_SOUTH_EAST);
					if (doorEast)
						chunk.setDoor(x2, y1, z1 + 3, Material.BIRCH_DOOR, BlockFace.EAST_SOUTH_EAST);

					if (hallNorth)
						chunk.setDoor(x1 + 2, y1, z1, Material.BIRCH_DOOR, BlockFace.NORTH_NORTH_WEST);
					if (hallWest)
						chunk.setDoor(x1, y1, z1 + 2, Material.BIRCH_DOOR, BlockFace.WEST_NORTH_WEST);

				} else {
					if (doorNorth)
						chunk.setDoor(x1 + 3, y1, z1, Material.BIRCH_DOOR, BlockFace.NORTH_NORTH_EAST);
					if (doorEast)
						chunk.setDoor(x2, y1, z2 - 3, Material.BIRCH_DOOR, BlockFace.EAST_NORTH_EAST);

					if (hallSouth)
						chunk.setDoor(x1 + 2, y1, z2, Material.BIRCH_DOOR, BlockFace.SOUTH_SOUTH_WEST);
					if (hallWest)
						chunk.setDoor(x1, y1, z2 - 2, Material.BIRCH_DOOR, BlockFace.WEST_SOUTH_WEST);

				}
			} else {
				if (roomSouth) {
					if (doorSouth)
						chunk.setDoor(x2 - 3, y1, z2, Material.BIRCH_DOOR, BlockFace.SOUTH_SOUTH_WEST);
					if (doorWest)
						chunk.setDoor(x1, y1, z1 + 3, Material.BIRCH_DOOR, BlockFace.WEST_SOUTH_WEST);

					if (hallNorth)
						chunk.setDoor(x2 - 2, y1, z1, Material.BIRCH_DOOR, BlockFace.NORTH_NORTH_EAST);
					if (hallEast)
						chunk.setDoor(x2, y1, z1 + 2, Material.BIRCH_DOOR, BlockFace.EAST_NORTH_EAST);

				} else {
					if (doorNorth)
						chunk.setDoor(x2 - 3, y1, z1, Material.BIRCH_DOOR, BlockFace.NORTH_NORTH_WEST);
					if (doorWest)
						chunk.setDoor(x1, y1, z2 - 3, Material.BIRCH_DOOR, BlockFace.WEST_NORTH_WEST);

					if (hallSouth)
						chunk.setDoor(x2 - 2, y1, z2, Material.BIRCH_DOOR, BlockFace.SOUTH_SOUTH_EAST);
					if (hallEast)
						chunk.setDoor(x2, y1, z2 - 2, Material.BIRCH_DOOR, BlockFace.EAST_SOUTH_EAST);
				}
			}
		}
	}

	public void drawWaterTower(CityWorldGenerator generator, RealBlocks chunk, int x, int y, int z, Odds odds) {
		int y1 = y;
		int y2 = y1 + 7;
		int y3 = y2 + 6;

		Material legMat = generator.materialProvider.itemsSelectMaterial_WaterTowers.getRandomMaterial(odds,
				Material.CLAY);
		Material topMat = generator.materialProvider.itemsSelectMaterial_WaterTowers.getRandomMaterial(odds,
				Material.WHITE_TERRACOTTA);
		Material platformMat = generator.materialProvider.itemsSelectMaterial_WaterTowers.getRandomMaterial(odds,
				topMat);

		chunk.setBlocks(x, y1, y3, z + 1, legMat);
		chunk.setBlocks(x + 1, y1, y3, z, legMat);

		chunk.setBlocks(x + 6, y1, y3, z, legMat);
		chunk.setBlocks(x + 7, y1, y3, z + 1, legMat);

		chunk.setBlocks(x, y1, y3, z + 6, legMat);
		chunk.setBlocks(x + 1, y1, y3, z + 7, legMat);

		chunk.setBlocks(x + 7, y1, y3, z + 6, legMat);
		chunk.setBlocks(x + 6, y1, y3, z + 7, legMat);

		chunk.setCircle(x + 4, x + 4, 3, y3 - 1, topMat, true);
		chunk.setCircle(x + 4, x + 4, 5, y3, platformMat, true);
		chunk.setCircle(x + 4, x + 4, 4, y3 + 1, y3 + 5, topMat, false);
		chunk.setCircle(x + 4, x + 4, 4, y3 + 5, topMat, true);
		chunk.setCircle(x + 4, x + 4, 3, y3 + 6, topMat, true);

		if (generator.getSettings().includeAbovegroundFluids) {
			chunk.setCircle(x + 4, x + 4, 3, y3 + 1, y3 + 2 + odds.getRandomInt(3),
					generator.oreProvider.fluidFluidMaterial, true);
		}
	}
}
