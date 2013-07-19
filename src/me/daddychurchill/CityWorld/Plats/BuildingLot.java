package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.Populators.EmptyWithNothing;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.CurvedWallFactory;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Direction.Stair;
import me.daddychurchill.CityWorld.Support.Direction.StairWell;
import me.daddychurchill.CityWorld.Support.Direction.TrapDoor;
import me.daddychurchill.CityWorld.Support.MaterialFactory;
import me.daddychurchill.CityWorld.Support.OutsideWEWallFactory;
import me.daddychurchill.CityWorld.Support.OutsideNSWallFactory;
import me.daddychurchill.CityWorld.Support.InteriorWallFactory;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;
import me.daddychurchill.CityWorld.Support.SurroundingFloors;
import me.daddychurchill.CityWorld.Support.Direction.Door;
import me.daddychurchill.CityWorld.Support.Surroundings;

import org.bukkit.Material;

public abstract class BuildingLot extends ConnectedLot {
	
	private static RoomProvider contentsNothing = new EmptyWithNothing();
	
	protected boolean neighborsHaveIdenticalHeights;
	protected double neighborsHaveSimilarHeightsOdds;
	protected double neighborsHaveSimilarRoundedOdds;
	protected int height; // floors up
	protected int depth; // floors down
	protected boolean needStairsUp;
	protected boolean needStairsDown;
	protected boolean rounded; // rounded corners if possible? (only if the insets match)
	protected MaterialFactory wallsWE;
	protected MaterialFactory wallsNS;
	protected MaterialFactory wallsInterior;
	protected MaterialFactory wallsCurved;
	protected int aboveFloorHeight;
	protected int basementFloorHeight;
	
	protected final static byte antennaBaseId = (byte) Material.CLAY.getId();
	protected final static byte antennaId = (byte) Material.FENCE.getId();
	protected final static byte conditionerId = (byte) Material.DOUBLE_STEP.getId();
	protected final static byte conditionerTrimId = (byte) Material.STONE_PLATE.getId();
	protected final static byte conditionerGrillId = (byte) Material.RAILS.getId();
	protected final static byte ductId = (byte) Material.STEP.getId();
	protected final static Material tileMaterial = Material.STEP;
	
	public enum RoofStyle {FLATTOP, EDGED, PEAK, TENT_NORTHSOUTH, TENT_WESTEAST};//, SLANT_NORTH, SLANT_SOUTH, SLANT_WEST, SLANT_EAST};
	public enum RoofFeature {ANTENNAS, CONDITIONERS, TILE};
	protected RoofStyle roofStyle;
	protected RoofFeature roofFeature;
	protected int roofScale;
	
	public enum StairStyle {STUDIO_A, CROSSED, LANDING, CORNER};
	protected StairStyle stairStyle;
	protected Direction.Facing stairDirection;
	
	public enum InteriorStyle {COLUMNS_ONLY, WALLS_ONLY, COLUMNS_OFFICES, WALLS_OFFICES, RANDOM};
	protected InteriorStyle interiorStyle;
	protected double oddsOfAnInteriorDoor = DataContext.oddsExtremelyLikely;
	protected double oddsOfAnExteriorDoor = DataContext.oddsSomewhatLikely;
	protected Material columnMaterial;
	protected boolean forceNarrowInteriorMode = false;
	protected double differentInteriorModes = DataContext.oddsUnlikely;
	
	protected int navLightX = 0;
	protected int navLightY = 0;
	protected int navLightZ = 0;
	
	private final static Material fenceMaterial = Material.IRON_FENCE;
	private final static int fenceHeight = 3;
	
	public RoomProvider roomProviderForFloor(WorldGenerator generator, SupportChunk chunk, int floor, int floorY) {
		return contentsNothing;
	}
	
	public BuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		style = LotStyle.STRUCTURE;
		
		DataContext context = platmap.context;
		
		neighborsHaveIdenticalHeights = chunkOdds.playOdds(context.oddsOfIdenticalBuildingHeights);
		neighborsHaveSimilarHeightsOdds = context.oddsOfSimilarBuildingHeights;
		neighborsHaveSimilarRoundedOdds = context.oddsOfSimilarBuildingRounding;
		aboveFloorHeight = DataContext.FloorHeight;
		basementFloorHeight = DataContext.FloorHeight;
		height = 1 + chunkOdds.getRandomInt(context.maximumFloorsAbove);
		depth = 0;
		if (platmap.generator.settings.includeBasements)
			depth = 1 + chunkOdds.getRandomInt(context.maximumFloorsBelow);
		needStairsDown = true;
		needStairsUp = true;
		rounded = chunkOdds.playOdds(context.oddsOfRoundedBuilding);
		roofStyle = pickRoofStyle();
		roofFeature = pickRoofFeature();
		roofScale = 1 + chunkOdds.getRandomInt(2);
		stairStyle = pickStairStyle();
		stairDirection = pickStairDirection();
		interiorStyle = pickInteriorStyle();
		columnMaterial = Material.COBBLE_WALL;
		wallsWE = new OutsideWEWallFactory(chunkOdds, platmap.generator.settings.includeDecayedBuildings);
		wallsNS = new OutsideNSWallFactory(wallsWE);
		wallsCurved = new CurvedWallFactory(wallsWE);
		wallsInterior = new InteriorWallFactory(chunkOdds, platmap.generator.settings.includeDecayedBuildings);
		forceNarrowInteriorMode = chunkOdds.playOdds(context.oddsOfForcedNarrowInteriorMode);
		differentInteriorModes = context.oddsOfDifferentInteriorModes;
	}

	@Override
	public boolean isValidStrataY(WorldGenerator generator, int blockX, int blockY, int blockZ) {
		return blockY < generator.streetLevel - basementFloorHeight * depth;
	}

	@Override
	protected boolean isShaftableLevel(WorldGenerator generator, int blockY) {
		return blockY >= 0 && blockY < generator.streetLevel - basementFloorHeight * depth - 2 - 16;	
	}

	protected RoofStyle pickRoofStyle() {
		switch (chunkOdds.getRandomInt(5)) {
		case 1:
			return RoofStyle.EDGED;
		case 2:
			return RoofStyle.PEAK;
		case 3:
			return RoofStyle.TENT_NORTHSOUTH;
		case 4:
			return RoofStyle.TENT_WESTEAST;
		default:
			return RoofStyle.FLATTOP;
		}
	}
	
	protected RoofFeature pickRoofFeature() {
		switch (chunkOdds.getRandomInt(3)) {
		case 1:
			return RoofFeature.ANTENNAS;
		case 2:
			return RoofFeature.CONDITIONERS;
		default:
			return RoofFeature.TILE;
		}
	}
	
	protected Material pickGlassMaterial() {
		switch (chunkOdds.getRandomInt(2)) {
		case 1:
			return Material.THIN_GLASS;
		default:
			return Material.GLASS;
		}
	}
	
	protected Facing pickStairDirection() {
		switch (chunkOdds.getRandomInt(4)) {
		case 1:
			return Direction.Facing.NORTH;
		case 2:
			return Direction.Facing.SOUTH;
		case 3:
			return Direction.Facing.WEST;
		default:
			return Direction.Facing.EAST;
		}
	}

	protected StairStyle pickStairStyle() {
		switch (chunkOdds.getRandomInt(4)) {
		case 1:
			return StairStyle.CORNER;
		case 2:
			return StairStyle.CROSSED;
		case 3:
			return StairStyle.STUDIO_A;
		default:
			return StairStyle.LANDING;
		}
	}

	protected InteriorStyle pickInteriorStyle() {
		switch (chunkOdds.getRandomInt(10)) {
		case 1:
			return InteriorStyle.RANDOM;
		case 2:
			return InteriorStyle.WALLS_ONLY;
		case 3:
			return InteriorStyle.COLUMNS_ONLY;
		case 4:
		case 5:
			return InteriorStyle.COLUMNS_OFFICES;
		case 6:
		case 7:
		case 8:
		case 9:
		default:
			return InteriorStyle.WALLS_OFFICES;
		}
	}
	
	// descendants can override this to do something special
	protected InteriorStyle getFloorsInteriorStyle(int floor) {
		return interiorStyle;
	}

	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);
		
		// other bits
		if (result && relative instanceof BuildingLot) {
			BuildingLot relativebuilding = (BuildingLot) relative;

			neighborsHaveIdenticalHeights = relativebuilding.neighborsHaveIdenticalHeights;
			if (neighborsHaveIdenticalHeights || chunkOdds.playOdds(neighborsHaveSimilarHeightsOdds)) {
				height = relativebuilding.height;
				depth = relativebuilding.depth;
			}
			
			if (chunkOdds.playOdds(neighborsHaveSimilarRoundedOdds))
				rounded = relativebuilding.rounded;
			
			// any other bits
			roofStyle = relativebuilding.roofStyle;
			roofFeature = relativebuilding.roofFeature;
			roofScale = relativebuilding.roofScale;
//			stairStyle = relativebuilding.stairStyle; // commented out because different parts of the building can have different stair styles
			interiorStyle = relativebuilding.interiorStyle;
			columnMaterial = relativebuilding.columnMaterial;
			wallsWE = relativebuilding.wallsWE;
			wallsNS = relativebuilding.wallsNS;
			wallsCurved = relativebuilding.wallsCurved;
			wallsInterior = relativebuilding.wallsInterior;
			if (!chunkOdds.playOdds(differentInteriorModes))
				forceNarrowInteriorMode = relativebuilding.forceNarrowInteriorMode;
			
			// do we need stairs?
			relativebuilding.needStairsDown = relativebuilding.depth > depth;
			relativebuilding.needStairsUp = relativebuilding.height > height;
		}
		return result;
	}
	
	protected SurroundingFloors getNeighboringFloorCounts(PlatMap platmap, int platX, int platZ) {
		SurroundingFloors neighborBuildings = new SurroundingFloors();
		
		// get a list of qualified neighbors
		PlatLot[][] neighborChunks = getNeighborPlatLots(platmap, platX, platZ, true);
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				if (neighborChunks[x][z] == null) {
					neighborBuildings.floors[x][z] = 0;
				} else {
					
					// in order for this building to be connected to our building they would have to be the same type
					neighborBuildings.floors[x][z] = ((BuildingLot) neighborChunks[x][z]).height;
				}
			}
		}
		neighborBuildings.update();
		
		return neighborBuildings;
	}
	
	protected SurroundingFloors getNeighboringBasementCounts(PlatMap platmap, int platX, int platZ) {
		SurroundingFloors neighborBuildings = new SurroundingFloors();
		
		// get a list of qualified neighbors
		PlatLot[][] neighborChunks = getNeighborPlatLots(platmap, platX, platZ, true);
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				if (neighborChunks[x][z] == null) {
					neighborBuildings.floors[x][z] = 0;
				} else {
					
					// in order for this building to be connected to our building they would have to be the same type
					neighborBuildings.floors[x][z] = ((BuildingLot) neighborChunks[x][z]).depth;
				}
			}
		}
		neighborBuildings.update();
		
		return neighborBuildings;
	}
	
	protected void drawCeilings(WorldGenerator generator, ByteChunk byteChunk, DataContext context, int y1, 
			int height, int insetNS, int insetWE, 
			boolean allowRounded, Material material, Surroundings heights) {
		
		// precalculate
		byte materialId = (byte) material.getId();
		byte emptyId = getAirId(generator, y1);
		int y2 = y1 + height;
		boolean stillNeedCeiling = true;
		int inset = Math.max(insetNS, insetWE);
		
		// rounded and square inset and there are exactly two neighbors?
		if (allowRounded) {// && rounded) { // already know that... && insetNS == insetWE && heights.getNeighborCount() == 2
			int innerCorner = (byteChunk.width - inset * 2) + inset;
			if (heights.toNorth()) {
				if (heights.toEast()) {
					byteChunk.setArcNorthEast(inset, y1, y2, materialId, true);
					if (!heights.toNorthEast()) 
						byteChunk.setArcNorthEast(innerCorner, y1, y2, emptyId, true);
					stillNeedCeiling = false;
				} else if (heights.toWest()) {
					byteChunk.setArcNorthWest(inset, y1, y2, materialId, true);
					if (!heights.toNorthWest())
						byteChunk.setArcNorthWest(innerCorner, y1, y2, emptyId, true);
					stillNeedCeiling = false;
				}
			} else if (heights.toSouth()) {
				if (heights.toEast()) {
					byteChunk.setArcSouthEast(inset, y1, y2, materialId, true);
					if (!heights.toSouthEast())
						byteChunk.setArcSouthEast(innerCorner, y1, y2, emptyId, true);
					stillNeedCeiling = false;
				} else if (heights.toWest()) {
					byteChunk.setArcSouthWest(inset, y1, y2, materialId, true);
					if (!heights.toSouthWest())
						byteChunk.setArcSouthWest(innerCorner, y1, y2, emptyId, true);
					stillNeedCeiling = false;
				}
			}
		}
		
		// still need to do something?
		if (stillNeedCeiling) {

			// center part
			byteChunk.setBlocks(insetWE, byteChunk.width - insetWE, y1, y2, insetNS, byteChunk.width - insetNS, materialId);
			
		}
		
		// only if we are inset
		if (insetWE > 0 || insetNS > 0) {
			
			// cardinal bits
			if (heights.toWest())
				byteChunk.setBlocks(0, insetWE, y1, y2, insetNS, byteChunk.width - insetNS, materialId);
			if (heights.toEast())
				byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width, y1, y2, insetNS, byteChunk.width - insetNS, materialId);
			if (heights.toNorth())
				byteChunk.setBlocks(insetWE, byteChunk.width - insetWE, y1, y2, 0, insetNS, materialId);
			if (heights.toSouth())
				byteChunk.setBlocks(insetWE, byteChunk.width - insetWE, y1, y2, byteChunk.width - insetNS, byteChunk.width, materialId);

			// corner bits
			if (heights.toNorthWest())
				byteChunk.setBlocks(0, insetWE, y1, y2, 0, insetNS, materialId);
			if (heights.toSouthWest())
				byteChunk.setBlocks(0, insetWE, y1, y2, byteChunk.width - insetNS, byteChunk.width, materialId);
			if (heights.toNorthEast())
				byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width, y1, y2, 0, insetNS, materialId);
			if (heights.toSouthEast())
				byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width, y1, y2, byteChunk.width - insetNS, byteChunk.width, materialId);
		}
	}
	
	protected void drawExteriorParts(WorldGenerator generator, ByteChunk byteChunk, DataContext context, int y1, 
			int height, int insetNS, int insetWE, 
			boolean allowRounded, Material materialWall, Material materialGlass, Surroundings heights) {
		
		// precalculate
		byte wallId = (byte) materialWall.getId();
		byte glassId = (byte) materialGlass.getId();
		int y2 = y1 + height;
		boolean stillNeedWalls = true;
		int inset = Math.max(insetNS, insetWE);
		
		// rounded and square inset and there are exactly two neighbors?
		if (allowRounded) {// && rounded) { 
			
			// hack the glass material if needed
			if (materialGlass == Material.THIN_GLASS)
				glassId = (byte) Material.GLASS.getId();
			
			// do the sides
			if (heights.toSouth()) {
				if (heights.toWest()) {
					byteChunk.setArcSouthWest(inset, y1, y2, wallId, glassId, wallsCurved);
					if (!heights.toSouthWest()) {
						byteChunk.setBlocks(insetWE, y1, y2, byteChunk.width - insetNS - 1, wallId, glassId, wallsCurved);
					}
					stillNeedWalls = false;
				} else if (heights.toEast()) {
					byteChunk.setArcSouthEast(inset, y1, y2, wallId, glassId, wallsCurved);
					if (!heights.toSouthEast()) {
						byteChunk.setBlocks(byteChunk.width - insetWE - 1, y1, y2, byteChunk.width - insetNS - 1, wallId, glassId, wallsCurved);
					}
					stillNeedWalls = false;
				}
			} else if (heights.toNorth()) {
				if (heights.toWest()) {
					byteChunk.setArcNorthWest(inset, y1, y2, wallId, glassId, wallsCurved);
					if (!heights.toNorthWest()) {
						byteChunk.setBlocks(insetWE, y1, y2, insetNS, wallId, glassId, wallsCurved);
					}
					stillNeedWalls = false;
				} else if (heights.toEast()) {
					byteChunk.setArcNorthEast(inset, y1, y2, wallId, glassId, wallsCurved);
					if (!heights.toNorthEast()) {
						byteChunk.setBlocks(byteChunk.width - insetWE - 1, y1, y2, insetNS, wallId, glassId, wallsCurved);
					}
					stillNeedWalls = false;
				}
			}
		}
		
		// still need to do something?
		if (stillNeedWalls) {
			
			// corner columns
			if (!heights.toNorthWest())
				byteChunk.setBlocks(insetWE, y1, y2, insetNS, wallId);
			if (!heights.toSouthWest())
				byteChunk.setBlocks(insetWE, y1, y2, byteChunk.width - insetNS - 1, wallId);
			if (!heights.toNorthEast())
				byteChunk.setBlocks(byteChunk.width - insetWE - 1, y1, y2, insetNS, wallId);
			if (!heights.toSouthEast())
				byteChunk.setBlocks(byteChunk.width - insetWE - 1, y1, y2, byteChunk.width - insetNS - 1, wallId);
			
			// cardinal walls
			if (!heights.toWest())
				byteChunk.setBlocks(insetWE,  insetWE + 1, y1, y2, insetNS + 1, byteChunk.width - insetNS - 1, wallId, glassId, wallsNS);
			if (!heights.toEast())
				byteChunk.setBlocks(byteChunk.width - insetWE - 1,  byteChunk.width - insetWE, y1, y2, insetNS + 1, byteChunk.width - insetNS - 1, wallId, glassId, wallsNS);
			if (!heights.toNorth())
				byteChunk.setBlocks(insetWE + 1, byteChunk.width - insetWE - 1, y1, y2, insetNS, insetNS + 1, wallId, glassId, wallsWE);
			if (!heights.toSouth())
				byteChunk.setBlocks(insetWE + 1, byteChunk.width - insetWE - 1, y1, y2, byteChunk.width - insetNS - 1, byteChunk.width - insetNS, wallId, glassId, wallsWE);
			
		}
			
		// only if there are insets
		if (insetWE > 0) {
			if (heights.toWest()) {
				if (!heights.toNorthWest())
					byteChunk.setBlocks(0, insetWE, y1, y2, insetNS, insetNS + 1, wallId, glassId, wallsWE);
				if (!heights.toSouthWest())
					byteChunk.setBlocks(0, insetWE, y1, y2, byteChunk.width - insetNS - 1, byteChunk.width - insetNS, wallId, glassId, wallsWE);
			}
			if (heights.toEast()) {
				if (!heights.toNorthEast())
					byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width, y1, y2, insetNS, insetNS + 1, wallId, glassId, wallsWE);
				if (!heights.toSouthEast())
					byteChunk.setBlocks(byteChunk.width - insetWE, byteChunk.width, y1, y2, byteChunk.width - insetNS - 1, byteChunk.width - insetNS, wallId, glassId, wallsWE);
			}
		}
		if (insetNS > 0) {
			if (heights.toNorth()) {
				if (!heights.toNorthWest())
					byteChunk.setBlocks(insetWE, insetWE + 1, y1, y2, 0, insetNS, wallId, glassId, wallsNS);
				if (!heights.toNorthEast())
					byteChunk.setBlocks(byteChunk.width - insetWE - 1, byteChunk.width - insetWE, y1, y2, 0, insetNS, wallId, glassId, wallsNS);
			}
			if (heights.toSouth()) {
				if (!heights.toSouthWest())
					byteChunk.setBlocks(insetWE, insetWE + 1, y1, y2, byteChunk.width - insetNS, byteChunk.width, wallId, glassId, wallsNS);
				if (!heights.toSouthEast())
					byteChunk.setBlocks(byteChunk.width - insetWE - 1, byteChunk.width - insetWE, y1, y2, byteChunk.width - insetNS, byteChunk.width, wallId, glassId, wallsNS);
			}
		}
	}
	
	protected void drawInteriorParts(WorldGenerator generator, RealChunk chunk, 
			DataContext context, RoomProvider rooms,
			int floor, int floorAt, int floorHeight, 
			int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, 
			StairWell stairLocation, 
			Material materialStair, Material materialStairWall, Material materialPlatform,
			boolean drawStairWall, boolean drawStairs, 
			boolean topFloor, boolean singleFloor,
			Surroundings heights) {
		
		drawInteriorParts(generator, chunk, context, getFloorsInteriorStyle(floor), 
				rooms, floor, floorAt, floorHeight, insetNS, insetWE, 
				allowRounded, materialWall, materialGlass, stairLocation, 
				materialStair, materialStairWall, materialPlatform,
				drawStairWall, drawStairs, topFloor, singleFloor,
				heights);
	}
	
	private void drawInteriorParts(WorldGenerator generator, RealChunk chunk, 
			DataContext context, InteriorStyle style, RoomProvider rooms, 
			int floor, int floorAt, int floorHeight, 
			int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, 
			StairWell stairLocation, 
			Material materialStair, Material materialStairWall, Material materialPlatform,
			boolean drawStairWall, boolean drawStairs, 
			boolean topFloor, boolean singleFloor,
			Surroundings heights) {
		
		// need to do more stuff?
		boolean drawInteriorDoors = false;
		boolean drawExteriorDoors = floor == 0;
		boolean drawNarrowInteriors = forceNarrowInteriorMode || 
									  (!heights.toNorthWest() && 
									   !heights.toNorthEast() && 
									   !heights.toSouthWest() && 
									   !heights.toSouthEast());
		if (drawNarrowInteriors && (insetNS > 1 || insetWE > 1))
			drawNarrowInteriors = false;
		
		// let's do it!
		switch (style) {
		case COLUMNS_ONLY:
			drawInteriorColumns(generator, chunk, context, drawNarrowInteriors,
					floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, 
					stairLocation, heights);
			break;
		case COLUMNS_OFFICES:
			drawInteriorColumns(generator, chunk, context, drawNarrowInteriors,
					floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, 
					stairLocation, heights);
			drawInteriorRooms(generator, chunk, context, drawNarrowInteriors,
					rooms, floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, 
					stairLocation, heights);
			break;
		case WALLS_ONLY:
			drawInteriorWalls(generator, chunk, context, drawNarrowInteriors,
					floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, 
					stairLocation, heights);
			drawInteriorDoors = true;
			break;
		case WALLS_OFFICES:
			drawInteriorWalls(generator, chunk, context, drawNarrowInteriors,
					floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, 
					stairLocation, heights);
			drawInteriorRooms(generator, chunk, context, drawNarrowInteriors,
					rooms, floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, 
					stairLocation, heights);
			drawInteriorDoors = true;
			break;
		case RANDOM:
			if (chunkOdds.flipCoin())
				drawInteriorParts(generator, chunk, context, 
						InteriorStyle.COLUMNS_OFFICES, rooms, floor, floorAt, floorHeight, 
						insetNS, insetWE, allowRounded, 
						materialWall, materialGlass, stairLocation, 
						materialStair, materialStairWall, materialPlatform,
						drawStairWall, drawStairs, topFloor, singleFloor,
						heights);
			else
				drawInteriorParts(generator, chunk, context, 
						InteriorStyle.WALLS_OFFICES, rooms, floor, floorAt, floorHeight, 
						insetNS, insetWE, allowRounded, 
						materialWall, materialGlass, stairLocation, 
						materialStair, materialStairWall, materialPlatform,
						drawStairWall, drawStairs, topFloor, singleFloor,
						heights);
			
			// all done, don't do anymore
			return;
		}
		
		// fancy walls... maybe
		if (drawStairWall) {
			drawStairsWalls(generator, chunk, floorAt, aboveFloorHeight, 
					stairLocation, materialStairWall, 
					floor == height - 1, floor == 0 && depth == 0);
		}
		
		// put up more doors?
		if (drawInteriorDoors) {
			drawInteriorDoors(generator, chunk, context, drawNarrowInteriors,
					floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, stairLocation, heights);
		}
		
		// more stairs and such
		if (drawStairs)
			drawStairs(generator, chunk, floorAt, aboveFloorHeight, 
					stairLocation, materialStair, materialPlatform);
		
		// outside 
		if (drawExteriorDoors)
			drawExteriorDoors(generator, chunk, context, 
					floor, floorAt, floorHeight, insetNS, insetWE, 
					allowRounded, materialWall, materialGlass, 
					stairLocation, heights);
	}
		
	private void drawInteriorColumns(WorldGenerator generator, RealChunk chunk, DataContext context, 
			boolean drawNarrowInteriors, int floor, int y1, int floorHeight, 
			int insetNS, int insetWE, boolean allowRounded, 
			Material materialWall, Material materialGlass, 
			StairWell stairLocation, Surroundings heights) {

		// precalculate
		int y2 = y1 + floorHeight;
		
		// first try the narrow logic (single column in the middle)
		if (drawNarrowInteriors) {
			chunk.setBlocks(7, 9, y1, y2, 7, 9, columnMaterial);
			
		// if the narrow logic doesn't handle it, try to use the wide logic (four columns in the middle)
		} else {
			
			if (heights.toNorthWest())
				chunk.setBlocks(4, y1, y2, 4, columnMaterial);
			if (heights.toNorthEast())
				chunk.setBlocks(11, y1, y2, 4, columnMaterial);
			if (heights.toSouthWest())
				chunk.setBlocks(4, y1, y2, 11, columnMaterial);
			if (heights.toSouthEast())
				chunk.setBlocks(11, y1, y2, 11, columnMaterial);
		}
	}
	
	private void drawInteriorWalls(WorldGenerator generator, RealChunk chunk, DataContext context, 
			boolean drawNarrowInteriors, int floor, int y1, int floorHeight, 
			int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, 
			StairWell stairsLocation, Surroundings heights) {
		
		//TODO Atrium in the middle of 2x2
		
		// precalculate
		byte wallId = (byte) materialWall.getId();
		byte glassId = (byte) materialGlass.getId();
		int y2 = y1 + floorHeight;
		int x1 = heights.toWest() ? 0 : insetWE + 1;
		int x2 = chunk.width - (heights.toEast() ? 0 : (insetWE + 1));
		int z1 = heights.toNorth() ? 0 : insetNS + 1;
		int z2 = chunk.width - (heights.toSouth() ? 0 : (insetNS + 1));

		// first try the narrow logic (single wall in the middle)
		if (drawNarrowInteriors) {
			
			// Northward
			if (heights.toNorth()) {
//				materialWall = Material.COBBLESTONE;
//				wallId = (byte) materialWall.getId();
				
				// draw out
				if (stairsLocation == StairWell.NONE) {
					drawInteriorNSWall(chunk, 7, y1, y2, 4, 7, wallId, glassId);
					chunk.setBlocks(7, y1, y2, 7, materialWall);
				}
				
				// draw cap
				drawInteriorNSWall(chunk, 7, y1, y2, 1, 4, wallId, glassId);
				drawInteriorWEWall(chunk, x1, 8, y1, y2, 0, wallId, glassId);

			} else if (stairsLocation == StairWell.NONE && !allowRounded) {
//				materialWall = Material.BEDROCK;
//				wallId = (byte) materialWall.getId();
				
				// draw short wall
				drawInteriorNSWall(chunk, 7, y1, y2, z1, 8, wallId, glassId);
			}
				
			// Eastward
			if (heights.toEast()) {
//				materialWall = Material.CLAY;
//				wallId = (byte) materialWall.getId();
				
				// draw out
				if (stairsLocation == StairWell.NONE) {
					drawInteriorWEWall(chunk, 9, 12, y1, y2, 7, wallId, glassId);
					chunk.setBlocks(8, y1, y2, 7, materialWall);
				}
				
				// draw cap
				drawInteriorWEWall(chunk, 12, 15, y1, y2, 7, wallId, glassId);
				drawInteriorNSWall(chunk, 15, y1, y2, z1, 8, wallId, glassId);
				
			} else if (stairsLocation == StairWell.NONE && !allowRounded) {
//				materialWall = Material.SAND;
//				wallId = (byte) materialWall.getId();
				
				// draw short wall
				drawInteriorWEWall(chunk, 8, x2, y1, y2, 7, wallId, glassId);
			}
			
			// Westward
			if (heights.toWest()) {
//				materialWall = Material.IRON_BLOCK;
//				wallId = (byte) materialWall.getId();
				
				// draw out
				if (stairsLocation == StairWell.NONE) {
					drawInteriorWEWall(chunk, 4, 7, y1, y2, 8, wallId, glassId);
					chunk.setBlocks(7, y1, y2, 8, materialWall);
				}
				
				// draw cap
				drawInteriorWEWall(chunk, 1, 4, y1, y2, 8, wallId, glassId);
				drawInteriorNSWall(chunk, 0, y1, y2, 8, z2, wallId, glassId);
				
			} else if (stairsLocation == StairWell.NONE && !allowRounded) {
//				materialWall = Material.GOLD_BLOCK;
//				wallId = (byte) materialWall.getId();
				
				// draw short wall
				drawInteriorWEWall(chunk, x1, 8, y1, y2, 8, wallId, glassId);
			}
			
			// Southward
			if (heights.toSouth()) {
//				materialWall = Material.DIAMOND_BLOCK;
//				wallId = (byte) materialWall.getId();
				
				// draw out
				if (stairsLocation == StairWell.NONE) {
					drawInteriorNSWall(chunk, 8, 13, y1, y2, 15, wallId, glassId);
					chunk.setBlocks(8, y1, y2, 8, materialWall);
				}
				
				// draw cap
				drawInteriorNSWall(chunk, 8, y1, y2, 12, 15, wallId, glassId);
				drawInteriorWEWall(chunk, 8, x2, y1, y2, 15, wallId, glassId);

			} else if (stairsLocation == StairWell.NONE && !allowRounded) {
//				materialWall = Material.LAPIS_BLOCK;
//				wallId = (byte) materialWall.getId();
				
				// draw short wall
				drawInteriorNSWall(chunk, 8, y1, y2, 8, z2, wallId, glassId);
			}
			
		// if the narrow logic doesn't handle it, try to use the wide logic (two walls in the middle)
		} else {
		
			// NW corner
			if (heights.toNorthWest()) {
//				wallId = (byte) Material.QUARTZ_ORE.getId();
				if (heights.toNorth()) {
					drawInteriorNSWall(chunk, 4, y1, y2, 0, wallId, glassId);
				}
				if (heights.toWest()) {
					drawInteriorWEWall(chunk, 0, y1, y2, 4, wallId, glassId);
				}
				
			} else {
//				wallId = (byte) Material.BEDROCK.getId();
				if (!heights.toNorth() && heights.toSouth() && heights.toWest()) {
					drawInteriorNSWall(chunk, 4, y1, y2, z1, 8, wallId, glassId);
				} else if (!heights.toWest() && heights.toEast() && heights.toNorth()) {
					drawInteriorWEWall(chunk, x1, 8, y1, y2, 4, wallId, glassId);
				}
			}
			
			// NE corner
			if (heights.toNorthEast()) {
//				wallId = (byte) Material.CLAY.getId();
				if (heights.toEast()) {
					drawInteriorWEWall(chunk, 8, y1, y2, 4, wallId, glassId);
				}
				if (heights.toNorth()) {
					drawInteriorNSWall(chunk, 11, y1, y2, 0, wallId, glassId);
				}
				
			} else {
//				wallId = (byte) Material.SAND.getId();
				if (!heights.toNorth() && heights.toSouth() && heights.toEast()) {
					drawInteriorNSWall(chunk, 11, y1, y2, z1, 8, wallId, glassId);
				} else if (!heights.toEast() && heights.toWest() && heights.toNorth()) {
					drawInteriorWEWall(chunk, 8, x2, y1, y2, 4, wallId, glassId);
				}
			}
			
			// SW corner
			if (heights.toSouthWest()) {
//				wallId = (byte) Material.IRON_BLOCK.getId();
				if (heights.toWest()) {
					drawInteriorWEWall(chunk, 0, y1, y2, 11, wallId, glassId);
				}
				if (heights.toSouth()) {
					drawInteriorNSWall(chunk, 4, y1, y2, 8, wallId, glassId);
				}
				
			} else {
//				wallId = (byte) Material.GOLD_BLOCK.getId();
				if (!heights.toSouth() && heights.toNorth() && heights.toWest()) {
					drawInteriorNSWall(chunk, 4, y1, y2, 8, z2, wallId, glassId);
				} else if (!heights.toWest() && heights.toEast() && heights.toSouth()) {
					drawInteriorWEWall(chunk, x1, 8, y1, y2, 11, wallId, glassId);
				}
			}
			
			// SE corner
			if (heights.toSouthEast()) {
//				wallId = (byte) Material.DIAMOND_BLOCK.getId();
				if (heights.toSouth()) {
					drawInteriorNSWall(chunk, 11, y1, y2, 8, wallId, glassId);
				}
				if (heights.toEast()) {
					drawInteriorWEWall(chunk, 8, y1, y2, 11, wallId, glassId);
				}
			} else {
//				wallId = (byte) Material.LAPIS_BLOCK.getId();
				if (!heights.toSouth() && heights.toNorth() && heights.toEast()) {
					drawInteriorNSWall(chunk, 11, y1, y2, 8, z2, wallId, glassId);
				} else if (!heights.toEast() && heights.toWest() && heights.toSouth()) {
					drawInteriorWEWall(chunk, 8, x2, y1, y2, 11, wallId, glassId);
				}
			}
		}
	}
	
	private void drawInteriorDoors(WorldGenerator generator, RealChunk chunk, DataContext context, 
			boolean drawNarrowInteriors, int floor, int y1, int floorHeight, 
			int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, 
			StairWell stairsLocation, Surroundings heights) {
		
		// precalculate
		int y2 = y1 + floorHeight;

		// first try the narrow logic (single wall in the middle)
		if (drawNarrowInteriors) {
			
			// Northward
			if (heights.toNorth()) {
//				materialWall = Material.COBBLESTONE;
				//2
				if (stairsLocation == StairWell.NONE)
					drawInteriorNSDoor(chunk, 7, y1, y2, 4, materialWall);
				//1
				drawInteriorWEDoor(chunk, 5, y1, y2, 0, materialWall);

			} else if (stairsLocation == StairWell.NONE && !allowRounded) {
//				materialWall = Material.BEDROCK;
				//a
				drawInteriorNSDoor(chunk, 7, y1, y2, 5, materialWall);
			}
				
			// Eastward
			if (heights.toEast()) {
//				materialWall = Material.CLAY;
				//3
				if (stairsLocation == StairWell.NONE)
					drawInteriorWEDoor(chunk, 9, y1, y2, 7, materialWall);
				//4
				drawInteriorNSDoor(chunk, 15, y1, y2, 5, materialWall);
				
			} else if (stairsLocation == StairWell.NONE && !allowRounded) {
//				materialWall = Material.SAND;
				//b
				drawInteriorWEDoor(chunk, 8, y1, y2, 7, materialWall);
			}
			
			// Westward
			if (heights.toWest()) {
//				materialWall = Material.IRON_BLOCK;
				//7
				if (stairsLocation == StairWell.NONE)
					drawInteriorWEDoor(chunk, 4, y1, y2, 8, materialWall);
				//8
				drawInteriorNSDoor(chunk, 0, y1, y2, 8, materialWall);
				
			} else if (stairsLocation == StairWell.NONE && !allowRounded) {
//				materialWall = Material.GOLD_BLOCK;
				//d
				drawInteriorWEDoor(chunk, 5, y1, y2, 8, materialWall);
			}
			
			// Southward
			if (heights.toSouth()) {
//				materialWall = Material.DIAMOND_BLOCK;
				//6
				if (stairsLocation == StairWell.NONE)
					drawInteriorNSDoor(chunk, 8, y1, y2, 9, materialWall);
				//5
				drawInteriorWEDoor(chunk, 8, y1, y2, 15, materialWall);

			} else if (stairsLocation == StairWell.NONE && !allowRounded) {
//				materialWall = Material.LAPIS_BLOCK;
				//c
				drawInteriorNSDoor(chunk, 8, y1, y2, 8, materialWall);
			}
			
		// if the narrow logic doesn't handle it, try to use the wide logic (two walls in the middle)
		} else {
		
			// NW corner
//			materialWall = Material.QUARTZ_ORE;
			if (heights.toNorthWest()) {
				if (heights.toNorth()) //1
					drawInteriorNSDoor(chunk, 4, y1, y2, 2, materialWall);
				if (heights.toWest()) //2
					drawInteriorWEDoor(chunk, 2, y1, y2, 4, materialWall);
				if (stairsLocation != StairWell.NORTHWEST) {
					//8,8
					drawInteriorWEDoor(chunk, 4, y1, y2, 4, materialWall);
					drawInteriorNSDoor(chunk, 4, y1, y2, 4, materialWall);
				}
//			} else {
//				if (stairsLocation == StairWell.NORTHEAST) //8
//					drawInteriorWEDoor(chunk, 5, y1, y2, 4, materialWall);
//				else if (stairsLocation == StairWell.SOUTHWEST) //8
//					drawInteriorNSDoor(chunk, 4, y1, y2, 5, materialWall);
			}
			
			// NE corner
//			materialWall = Material.IRON_ORE;
			if (heights.toNorthEast()) {
				if (heights.toNorth()) //3
					drawInteriorNSDoor(chunk, 11, y1, y2, 2, materialWall);
				if (heights.toEast()) //4
					drawInteriorWEDoor(chunk, 11, y1, y2, 4, materialWall);
				if (stairsLocation != StairWell.NORTHEAST) {
					//9,10
					drawInteriorWEDoor(chunk, 9, y1, y2, 4, materialWall);
					drawInteriorNSDoor(chunk, 11, y1, y2, 4, materialWall);
				}
//			} else {
//				if (stairsLocation == StairWell.NORTHWEST) //9
//					drawInteriorWEDoor(chunk, 8, y1, y2, 4, materialWall);
//				else if (stairsLocation == StairWell.SOUTHEAST) //10
//					drawInteriorNSDoor(chunk, 11, y1, y2, 5, materialWall);
			}
			
			// SW corner
//			materialWall = Material.GOLD_ORE;
			if (heights.toSouthWest()) {
				if (heights.toSouth()) //6
					drawInteriorNSDoor(chunk, 4, y1, y2, 11, materialWall);
				if (heights.toWest()) //5
					drawInteriorWEDoor(chunk, 2, y1, y2, 11, materialWall);
				if (stairsLocation != StairWell.SOUTHWEST) {
					//13,14
					drawInteriorWEDoor(chunk, 4, y1, y2, 11, materialWall);
					drawInteriorNSDoor(chunk, 4, y1, y2, 9, materialWall);
				}
//			} else {
//				if (stairsLocation == StairWell.NORTHWEST) //14
//					drawInteriorNSDoor(chunk, 4, y1, y2, 8, materialWall);
//				else if (stairsLocation == StairWell.SOUTHEAST) //13
//					drawInteriorWEDoor(chunk, 5, y1, y2, 11, materialWall);
			}
			
			// SE corner
//			materialWall = Material.DIAMOND_ORE;
			if (heights.toSouthEast()) {
				if (heights.toSouth()) //7
					drawInteriorNSDoor(chunk, 11, y1, y2, 11, materialWall);
				if (heights.toEast()) //7
					drawInteriorWEDoor(chunk, 11, y1, y2, 11, materialWall);
				if (stairsLocation != StairWell.SOUTHEAST) {
					//11,12
					drawInteriorWEDoor(chunk, 9, y1, y2, 11, materialWall);
					drawInteriorNSDoor(chunk, 11, y1, y2, 9, materialWall);
				}
//			} else {
//				if (stairsLocation == StairWell.SOUTHWEST) //12
//					drawInteriorWEDoor(chunk, 8, y1, y2, 11, materialWall);
//				else if (stairsLocation == StairWell.NORTHEAST) //11
//					drawInteriorNSDoor(chunk, 11, y1, y2, 8, materialWall);
			}
			
			// backfill with doors near stairs
			switch (stairsLocation) {
			case NORTHWEST:
//				materialWall = Material.DIAMOND_ORE;
				if (!heights.toEast()) //15
					drawInteriorWEDoor(chunk, 7, y1, y2, 4, materialWall);
//				materialWall = Material.DIAMOND_BLOCK;
				if (!heights.toSouth()) //16
					drawInteriorNSDoor(chunk, 4, y1, y2, 7, materialWall);
				break;
			case SOUTHEAST: 
//				materialWall = Material.REDSTONE_ORE;
				if (!heights.toNorth()) //17
					drawInteriorNSDoor(chunk, 11, y1, y2, 6, materialWall);
//				materialWall = Material.REDSTONE_BLOCK;
				if (!heights.toWest()) //18
					drawInteriorWEDoor(chunk, 6, y1, y2, 11, materialWall);
				break;
			case NORTHEAST: 
//				materialWall = Material.EMERALD_ORE;
				if (!heights.toWest()) //19
					drawInteriorWEDoor(chunk, 6, y1, y2, 4, materialWall);
//				materialWall = Material.EMERALD_BLOCK;
				if (!heights.toSouth()) //20
					drawInteriorNSDoor(chunk, 11, y1, y2, 7, materialWall);
				break;
			case SOUTHWEST: 
//				materialWall = Material.GOLD_ORE;
				if (!heights.toNorth()) //21
					drawInteriorNSDoor(chunk, 4, y1, y2, 6, materialWall);
//				materialWall = Material.GOLD_BLOCK;
				if (!heights.toEast()) //22
					drawInteriorWEDoor(chunk, 7, y1, y2, 11, materialWall);
				break;
			default:
				// nothing to draw here
			}
		}
	}
	
	private void drawExteriorDoors(WorldGenerator generator, RealChunk chunk, DataContext context, 
			int floor, int y1, int height, 
			int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, 
			StairWell stairsLocation, Surroundings heights) {
		
		// precalculate
		int y2 = y1 + height;
		int x1 = heights.toWest() ? 0 : insetWE + 1;
		int x2 = chunk.width - (heights.toEast() ? 0 : (insetWE + 1));
		int z1 = heights.toNorth() ? 0 : insetNS + 1;
		int z2 = chunk.width - (heights.toSouth() ? 0 : (insetNS + 1));

		// rounded potential?
		if (allowRounded) {
			if (heights.toEast() && heights.toSouth())
				drawExteriorWEDoor(chunk, 13, y1, y2, z1 - 1, materialWall);
			if (heights.toWest() && heights.toNorth())
				drawExteriorEWDoor(chunk, 0, y1, y2, z2, materialWall);
			if (heights.toWest() && heights.toSouth())
				drawExteriorNSDoor(chunk, x2, y1, y2, 13, materialWall);
			if (heights.toEast() && heights.toNorth())
				drawExteriorSNDoor(chunk, x1 - 1, y1, y2, 0, materialWall);
		} else {
			if (!heights.toNorth())
				drawExteriorWEDoor(chunk, 5, y1, y2, z1 - 1, materialWall);
			if (!heights.toSouth())
				drawExteriorEWDoor(chunk, 8, y1, y2, z2, materialWall);
			if (!heights.toWest())
				drawExteriorNSDoor(chunk, x1 - 1, y1, y2, 8, materialWall);
			if (!heights.toEast())
				drawExteriorSNDoor(chunk, x2, y1, y2, 5, materialWall);
		}
	}

	private static int maxInsetForRooms = 2;
	
	private void drawInteriorRooms(WorldGenerator generator, RealChunk chunk, DataContext context, 
			boolean drawNarrowInteriors, RoomProvider rooms, int floor, int y1, int height, 
			int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, 
			StairWell stairsLocation, Surroundings heights) {
		
		// skip the rooms?
		if (!generator.settings.includeBuildingInteriors)
			return;
		
		// outer rooms?
		boolean includeOuterRooms = insetNS <= maxInsetForRooms || insetWE <= maxInsetForRooms;
		
		// first try the narrow logic (single wall in the middle)
		if (drawNarrowInteriors) {
			
			// don't do these for rounded cases
			if (!allowRounded) {

				// Northward
				if (heights.toNorth())
					drawInteriorRoom(generator, chunk, rooms, floor, 3, y1, 1, height, 
							Facing.NORTH, materialWall, materialGlass); //1
				if (insetNS < 2)
					drawInteriorRoom(generator, chunk, rooms, floor, 8, y1, 2, height, 
							Facing.WEST, materialWall, materialGlass); //2
					
				// Eastward
				if (heights.toEast())
					drawInteriorRoom(generator, chunk, rooms, floor, 12, y1, 3, height, 
							Facing.EAST, materialWall, materialGlass); //3
				if (insetWE < 2)
					drawInteriorRoom(generator, chunk, rooms, floor, 11, y1, 8, height, 
							Facing.NORTH, materialWall, materialGlass); //4
				
				// Southward
				if (heights.toSouth())
					drawInteriorRoom(generator, chunk, rooms, floor, 11, y1, 12, height, 
							Facing.SOUTH, materialWall, materialGlass); //5
				if (insetNS < 2)
					drawInteriorRoom(generator, chunk, rooms, floor, 5, y1, 11, height, 
							Facing.EAST, materialWall, materialGlass); //6
				
				// Westward
				if (heights.toWest())
					drawInteriorRoom(generator, chunk, rooms, floor, 1, y1, 10, height, 
							Facing.WEST, materialWall, materialGlass); //7
				if (insetWE < 2)
					drawInteriorRoom(generator, chunk, rooms, floor, 2, y1, 5, height, 
							Facing.SOUTH, materialWall, materialGlass); //8
			}
			
		// if the narrow logic doesn't handle it, try to use the wide logic (two walls in the middle)
		} else {
		
			// NW corner
			if (heights.toNorthWest()) {
				if (heights.toNorth() && heights.toWest()) {
					//1 & 2
					drawInteriorRoom(generator, chunk, rooms, floor, 1, y1, 0, height, 
							Facing.EAST, materialWall, materialGlass);
					drawInteriorRoom(generator, chunk, rooms, floor, 5, y1, 0, height, 
							Facing.WEST, materialWall, materialGlass);
				}
			} else if (includeOuterRooms) {
				if (heights.toNorth()) {
					if (heights.toWest()) {
						//none
					} else if (stairsLocation != StairWell.NORTHWEST ||
							stairsLocation != StairWell.WEST) {
						//n & m
						drawInteriorRoom(generator, chunk, rooms, floor, 4, y1, 1, height, 
								Facing.SOUTH, materialWall, materialGlass);
						drawInteriorRoom(generator, chunk, rooms, floor, 4, y1, 5, height, 
								Facing.NORTH, materialWall, materialGlass);
					}
				} else {
					if (heights.toWest() && 
								(stairsLocation != StairWell.NORTHWEST ||
								 stairsLocation != StairWell.NORTH)) {
						//c & d
						drawInteriorRoom(generator, chunk, rooms, floor, 1, y1, 4, height, 
								Facing.EAST, materialWall, materialGlass);
//						drawInteriorRoom(generator, chunk, rooms, floor, 5, y1, 4, height, 
//								Facing.WEST, materialWall, materialGlass);
					} else if (!allowRounded && stairsLocation == StairWell.SOUTHEAST) {
						//q
						drawInteriorRoom(generator, chunk, rooms, floor, 4, y1, 4, height, 
								Facing.EAST, materialWall, materialGlass);
					}
				}
			}
			
			// NE corner
			if (heights.toNorthEast()) {
				if (heights.toNorth() && heights.toEast()) {
					//3 & 4
					drawInteriorRoom(generator, chunk, rooms, floor, 13, y1, 1, height, 
							Facing.SOUTH, materialWall, materialGlass);
					drawInteriorRoom(generator, chunk, rooms, floor, 13, y1, 5, height, 
							Facing.NORTH, materialWall, materialGlass);
				}
			} else if (includeOuterRooms) {
				if (heights.toNorth()) {
					if (heights.toEast()) {
						//none
					} else if (stairsLocation != StairWell.NORTHEAST ||
							stairsLocation != StairWell.EAST) {
						//g & h
						drawInteriorRoom(generator, chunk, rooms, floor, 9, y1, 1, height, 
								Facing.SOUTH, materialWall, materialGlass);
//						drawInteriorRoom(generator, chunk, rooms, floor, 9, y1, 5, height, 
//								Facing.NORTH, materialWall, materialGlass);
					}
				} else {
					if (heights.toEast() && 
							(stairsLocation != StairWell.NORTHEAST ||
							 stairsLocation != StairWell.NORTH)) {
						//a & b
						drawInteriorRoom(generator, chunk, rooms, floor, 8, y1, 4, height, 
								Facing.EAST, materialWall, materialGlass);
						drawInteriorRoom(generator, chunk, rooms, floor, 12, y1, 4, height, 
								Facing.WEST, materialWall, materialGlass);
					} else if (!allowRounded && stairsLocation == StairWell.SOUTHWEST) {
						//r
						drawInteriorRoom(generator, chunk, rooms, floor, 9, y1, 4, height, 
								Facing.SOUTH, materialWall, materialGlass);
					}
				}
			}
			
			// SW corner
			if (heights.toSouthWest()) {
				if (heights.toSouth() && heights.toWest()) {
					//5 & 6
					drawInteriorRoom(generator, chunk, rooms, floor, 0, y1, 12, height, 
							Facing.NORTH, materialWall, materialGlass);
					drawInteriorRoom(generator, chunk, rooms, floor, 0, y1, 8, height, 
							Facing.SOUTH, materialWall, materialGlass);
				}
			} else if (includeOuterRooms) {
				if (heights.toSouth()) {
					if (heights.toWest()) {
						//none
					} else if (stairsLocation != StairWell.SOUTHWEST ||
							stairsLocation != StairWell.WEST) {
						//p & o
//						drawInteriorRoom(generator, chunk, rooms, floor, 4, y1, 8, height, 
//								Facing.SOUTH, materialWall, materialGlass);
						drawInteriorRoom(generator, chunk, rooms, floor, 4, y1, 12, height, 
								Facing.NORTH, materialWall, materialGlass);
					}
				} else {
					if (heights.toWest() && 
							(stairsLocation != StairWell.SOUTHWEST ||
							 stairsLocation != StairWell.SOUTH)) {

						//j & i
						drawInteriorRoom(generator, chunk, rooms, floor, 1, y1, 9, height, 
								Facing.EAST, materialWall, materialGlass);
						drawInteriorRoom(generator, chunk, rooms, floor, 5, y1, 9, height, 
								Facing.WEST, materialWall, materialGlass);
					} else if (!allowRounded && stairsLocation == StairWell.NORTHEAST) {
						//t
						drawInteriorRoom(generator, chunk, rooms, floor, 4, y1, 9, height, 
								Facing.NORTH, materialWall, materialGlass);
					}
				}
			}
			
			// SE corner
			if (heights.toSouthEast()) {
				if (heights.toSouth() && heights.toEast()) {
					//7 & 8
					drawInteriorRoom(generator, chunk, rooms, floor, 12, y1, 13, height, 
							Facing.WEST, materialWall, materialGlass);
					drawInteriorRoom(generator, chunk, rooms, floor, 8, y1, 13, height, 
							Facing.EAST, materialWall, materialGlass);
				}
			} else if (includeOuterRooms) {
				if (heights.toSouth()) {
					if (heights.toEast()) {
						//none
					} else if (stairsLocation != StairWell.SOUTHEAST ||
							stairsLocation != StairWell.EAST) {
						//e & f
						drawInteriorRoom(generator, chunk, rooms, floor, 9, y1, 8, height, 
								Facing.SOUTH, materialWall, materialGlass);
						drawInteriorRoom(generator, chunk, rooms, floor, 9, y1, 12, height, 
								Facing.NORTH, materialWall, materialGlass);
					}
				} else {
					if (heights.toEast() && 
							(stairsLocation != StairWell.SOUTHEAST ||
							 stairsLocation != StairWell.SOUTH)) {

						//l & k
//						drawInteriorRoom(generator, chunk, rooms, floor, 8, y1, 9, height, 
//								Facing.EAST, materialWall, materialGlass);
						drawInteriorRoom(generator, chunk, rooms, floor, 12, y1, 9, height, 
								Facing.WEST, materialWall, materialGlass);
					} else if (!allowRounded && stairsLocation == StairWell.NORTHWEST) {
						//s
						drawInteriorRoom(generator, chunk, rooms, floor, 9, y1, 9, height, 
								Facing.WEST, materialWall, materialGlass);
					}
				}
			}
		}
	}
	
	//TODO I need to actually make this dynamic based on how much room there is
	private static int roomWidth = 3;
	private static int roomDepth = 3;
	
	private void drawInteriorRoom(WorldGenerator generator, RealChunk chunk, 
			RoomProvider rooms, int floor, int x, int y, int z, int height, 
			Facing sideWithWall, Material materialWall, Material materialGlass) {

		rooms.drawFixtures(generator, chunk, chunkOdds, floor, x, y, z, 
				roomWidth, height, roomDepth, sideWithWall, materialWall, materialGlass);
	}
	
	private void drawInteriorNSWall(RealChunk chunk, int x, int y1, int y2, int z, byte wallId, byte glassId) {
		drawInteriorNSWall(chunk, x, y1, y2, z, z + 8, wallId, glassId);
	}
	
	private void drawInteriorWEWall(RealChunk chunk, int x, int y1, int y2, int z, byte wallId, byte glassId) {
		drawInteriorWEWall(chunk, x, x + 8, y1, y2, z, wallId, glassId);
	}
	
	private void drawInteriorNSWall(RealChunk chunk, int x, int y1, int y2, int z1, int z2, byte wallId, byte glassId) {
		chunk.setBlocks(x, x + 1, y1, y2, z1, z2, wallId, glassId, wallsInterior);
	}
	
	private void drawInteriorWEWall(RealChunk chunk, int x1, int x2, int y1, int y2, int z, byte wallId, byte glassId) {
		chunk.setBlocks(x1, x2, y1, y2, z, z + 1, wallId, glassId, wallsInterior);
	}
	
	private void drawInteriorNSDoor(RealChunk chunk, int x, int y1, int y2, int z, Material wall) {
		if (chunkOdds.playOdds(oddsOfAnInteriorDoor))
			drawDoor(chunk, x, x, x, y1, y2, z, z + 1, z + 2, Door.WESTBYNORTHWEST, wall);
	}
	
	private void drawInteriorWEDoor(RealChunk chunk, int x, int y1, int y2, int z, Material wall) {
		if (chunkOdds.playOdds(oddsOfAnInteriorDoor))
			drawDoor(chunk, x, x + 1, x + 2, y1, y2, z, z, z, Door.NORTHBYNORTHWEST, wall);
	}
	
	private void drawExteriorNSDoor(RealChunk chunk, int x, int y1, int y2, int z, Material wall) {
		if (chunkOdds.playOdds(oddsOfAnExteriorDoor))
			drawDoor(chunk, x, x, x, y1, y2, z, z + 1, z + 2, Door.WESTBYNORTHWEST, wall);
	}
	
	private void drawExteriorSNDoor(RealChunk chunk, int x, int y1, int y2, int z, Material wall) {
		if (chunkOdds.playOdds(oddsOfAnExteriorDoor))
			drawDoor(chunk, x, x, x, y1, y2, z, z + 1, z + 2, Door.EASTBYNORTHEAST, wall);
	}

	private void drawExteriorWEDoor(RealChunk chunk, int x, int y1, int y2, int z, Material wall) {
		if (chunkOdds.playOdds(oddsOfAnExteriorDoor))
			drawDoor(chunk, x, x + 1, x + 2, y1, y2, z, z, z, Door.NORTHBYNORTHWEST, wall);
	}
	
	private void drawExteriorEWDoor(RealChunk chunk, int x, int y1, int y2, int z, Material wall) {
		if (chunkOdds.playOdds(oddsOfAnExteriorDoor))
			drawDoor(chunk, x, x + 1, x + 2, y1, y2, z, z, z, Door.SOUTHBYSOUTHEAST, wall);
	}
	
	//TODO roof fixtures (peak, helipad, air conditioning, stairwells access, penthouse, castle trim, etc.
	protected void drawRoof(WorldGenerator generator, ByteChunk chunk, DataContext context, 
			int y1, int insetNS, int insetWE, 
			boolean allowRounded, Material material, Surroundings heights) {
		switch (roofStyle) {
		case PEAK:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < aboveFloorHeight; i++) {
					if (i == aboveFloorHeight - 1)
						drawCeilings(generator, chunk, context, y1 + i * roofScale, roofScale, insetNS + i, insetWE + i, allowRounded, material, heights);
					else
						drawExteriorParts(generator, chunk, context, y1 + i * roofScale, roofScale, insetNS + i, insetWE + i, allowRounded, material, material, heights);
				}
			} else
				drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, allowRounded, material, true, heights);
			break;
		case TENT_NORTHSOUTH:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < aboveFloorHeight; i++) {
					if (i == aboveFloorHeight - 1)
						drawCeilings(generator, chunk, context, y1 + i * roofScale, roofScale, insetNS + i, insetWE, allowRounded, material, heights);
					else
						drawExteriorParts(generator, chunk, context, y1 + i * roofScale, roofScale, insetNS + i, insetWE, allowRounded, material, material, heights);
				}
			} else
				drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, allowRounded, material, true, heights);
			break;
		case TENT_WESTEAST:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < aboveFloorHeight; i++) {
					if (i == aboveFloorHeight - 1)
						drawCeilings(generator, chunk, context, y1 + i * roofScale, roofScale, insetNS, insetWE + i, allowRounded, material, heights);
					else
						drawExteriorParts(generator, chunk, context, y1 + i * roofScale, roofScale, insetNS, insetWE + i, allowRounded, material, material, heights);
				}
			} else
				drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, allowRounded, material, true, heights);
			break;
		case EDGED:
			drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, allowRounded, material, true, heights);
			break;
		case FLATTOP:
			drawEdgedRoof(generator, chunk, context, y1, insetNS, insetWE, allowRounded, material, false, heights);
			break;
		}
	}
	
	private void drawEdgedRoof(WorldGenerator generator, ByteChunk chunk, DataContext context, 
			int y1, int insetNS, int insetWE, 
			boolean allowRounded, Material material, boolean doEdge, Surroundings heights) {
		
		// a little bit of edge 
		if (doEdge)
			drawExteriorParts(generator, chunk, context, y1, 1, insetNS, insetWE, allowRounded, material, material, heights);
		
		// add the special features
		switch (roofFeature) {
		case ANTENNAS:
			if (heights.getNeighborCount() == 0) {
				drawAntenna(chunk, 6, y1, 6);
				drawAntenna(chunk, 6, y1, 9);
				drawAntenna(chunk, 9, y1, 6);
				drawAntenna(chunk, 9, y1, 9);
				break;
			} // else fall into the conditioners block
		case CONDITIONERS:
			drawConditioner(chunk, 6, y1, 6);
			drawConditioner(chunk, 6, y1, 9);
			drawConditioner(chunk, 9, y1, 6);
			drawConditioner(chunk, 9, y1, 9);
			break;
		case TILE:
			drawCeilings(generator, chunk, context, y1, 1, insetNS + 1, insetWE + 1, allowRounded, tileMaterial, heights);
			break;
		}
	}
	
	private void drawAntenna(ByteChunk chunk, int x, int y, int z) {
		
		if (chunkOdds.flipCoin()) {
			int y2 = y + 8 + chunkOdds.getRandomInt(8);
			chunk.setBlocks(x, y, y + 3, z, antennaBaseId);
			chunk.setBlocks(x, y + 2, y2, z, antennaId);
			if (y2 >= navLightY) {
				navLightX = x;
				navLightY = y2 - 1;
				navLightZ = z;
			}
		}
	}
	
	protected void drawNavLight(RealChunk chunk, DataContext context) {
		if (navLightY > 0)
			chunk.setTorch(navLightX, navLightY, navLightZ, context.torchMat, Direction.Torch.FLOOR);
	}
	
	private void drawConditioner(ByteChunk chunk, int x, int y, int z) {
		
		//TODO air conditioner tracks are not round
		if (chunkOdds.flipCoin()) {
			chunk.setBlock(x, y, z, conditionerId);
			chunk.setBlock(x, y + 1, z, conditionerTrimId);
			if (chunkOdds.flipCoin()) {
				chunk.setBlockIfAir(x - 1, y, z, ductId);
				chunk.setBlockIfAir(x - 2, y, z, ductId);
			}
			if (chunkOdds.flipCoin()) {
				chunk.setBlockIfAir(x + 1, y, z, ductId);
				chunk.setBlockIfAir(x + 2, y, z, ductId);
			}
			if (chunkOdds.flipCoin()) {
				chunk.setBlockIfAir(x, y, z - 1, ductId);
				chunk.setBlockIfAir(x, y, z - 2, ductId);
			}
			if (chunkOdds.flipCoin()) {
				chunk.setBlockIfAir(x, y, z + 1, ductId);
				chunk.setBlockIfAir(x, y, z + 2, ductId);
			}
			if (chunkOdds.flipCoin()) {
				chunk.setBlock(x, y, z, conditionerId);
				chunk.setBlock(x, y + 1, z, conditionerTrimId);
//			} else {
//				chunk.setBlocks(x, x + 2, y, z, z + 2, conditionerId);
//				chunk.setBlocks(x, x + 2, y + 1, z, z + 2, conditionerGrillId);
			}
		}
	}
	
	private void drawDoor(RealChunk chunk, int x1, int x2, int x3, int y1, int y2, int z1, int z2, int z3, 
			Direction.Door direction, Material wallMaterial) {
		
		// frame the door
		chunk.setBlocks(x1, y1, y2, z1, wallMaterial);
		chunk.setBlocks(x2, y1 + 2, y2, z2, wallMaterial);
		chunk.setBlocks(x3, y1, y2, z3, wallMaterial);
		
		// place the door
		chunk.setWoodenDoor(x2, y1, z2, direction);
	}
	
	static class StairAt {
		public int X = 0;
		public int Z = 0;
		
		private static final int stairWidth = 4;
		private static final int centerX = 8;
		private static final int centerZ = 8;
		
		public StairAt(RealChunk chunk, int stairLength, StairWell where) {
			switch (where) {
			case NORTHWEST:
				X = centerX - stairLength;
				Z = centerZ - stairWidth;
				break;
			case NORTH:
				X = centerX - stairLength / 2;
				Z = centerZ - stairWidth;
				break;
			case NORTHEAST:
				X = centerX;
				Z = centerZ - stairWidth;
				break;
			case EAST:
				X = centerX;
				Z = centerZ - stairWidth / 2;
				break;
			case SOUTHEAST:
				X = centerX;
				Z = centerZ;
				break;
			case SOUTH:
				X = centerX - stairLength / 2;
				Z = centerZ;
				break;
			case SOUTHWEST:
				X = centerX - stairLength;
				Z = centerZ;
				break;
			case WEST:
				X = centerX - stairLength;
				Z = centerZ - stairWidth / 2;
				break;
			case CENTER:
			case NONE: 
				X = centerX - stairLength / 2;
				Z = centerZ - stairWidth / 2;
				break;
			}
		}
	}

	public StairWell getStairWellLocation(boolean allowRounded, Surroundings heights) {
		if (heights.toNorth() && heights.toWest() && !heights.toSouth() && !heights.toEast())
			return StairWell.NORTHWEST;
		else if (heights.toNorth() && heights.toEast() && !heights.toSouth() && !heights.toWest())
			return StairWell.NORTHEAST;
		else if (heights.toSouth() && heights.toWest() && !heights.toNorth() && !heights.toEast())
			return StairWell.SOUTHWEST;
		else if (heights.toSouth() && heights.toEast() && !heights.toNorth() && !heights.toWest())
			return StairWell.SOUTHEAST;
		else if (heights.toNorth() && heights.toWest() && heights.toEast() && !heights.toSouth())
			return StairWell.NORTH;
		else if (heights.toSouth() && heights.toWest() && heights.toEast() && !heights.toNorth())
			return StairWell.SOUTH;
		else if (heights.toWest() && heights.toNorth() && heights.toSouth() && !heights.toEast())
			return StairWell.WEST;
		else if (heights.toEast() && heights.toNorth() && heights.toSouth() && !heights.toWest())
			return StairWell.EAST;
		else
			return StairWell.CENTER;
	}
	
	protected void drawStairs(WorldGenerator generator, RealChunk chunk, int y1, 
			int floorHeight, StairWell where, Material stairMaterial, Material platformMaterial) {
		StairAt at = new StairAt(chunk, floorHeight, where);
		switch (stairStyle) {
		case CROSSED:
			if (floorHeight == 4) {
				switch (stairDirection) {
				case NORTH:
				case SOUTH:
					chunk.setStair(at.X + 1, y1, at.Z + 3, stairMaterial, Stair.NORTH);
					chunk.setStair(at.X + 1, y1 + 1, at.Z + 2, stairMaterial, Stair.NORTH);
					chunk.setStair(at.X + 1, y1 + 2, at.Z + 1, stairMaterial, Stair.NORTH);
					chunk.setStair(at.X + 1, y1 + 3, at.Z, stairMaterial, Stair.NORTH);
					chunk.setStair(at.X + 2, y1, at.Z, stairMaterial, Stair.SOUTH);
					chunk.setStair(at.X + 2, y1 + 1, at.Z + 1, stairMaterial, Stair.SOUTH);
					chunk.setStair(at.X + 2, y1 + 2, at.Z + 2, stairMaterial, Stair.SOUTH);
					chunk.setStair(at.X + 2, y1 + 3, at.Z + 3, stairMaterial, Stair.SOUTH);
					break;
				case WEST:
				case EAST:
					chunk.setStair(at.X + 3, y1, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setStair(at.X + 2, y1 + 1, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setStair(at.X + 1, y1 + 2, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setStair(at.X, y1 + 3, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setStair(at.X, y1, at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setStair(at.X + 1, y1 + 1, at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setStair(at.X + 2, y1 + 2, at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setStair(at.X + 3, y1 + 3, at.Z + 2, stairMaterial, Stair.EAST);
					break;
				}
				
				return;
			}
			break;
		case LANDING:
			if (floorHeight == 4) {
				switch (stairDirection) {
				case NORTH:
					chunk.setStair(at.X + 1, y1, 	 at.Z, stairMaterial, Stair.SOUTH);
					chunk.setStair(at.X + 1, y1 + 1, at.Z + 1, stairMaterial, Stair.SOUTH);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setStair(at.X + 2, y1 + 2, at.Z + 1, stairMaterial, Stair.NORTH);
					chunk.setStair(at.X + 2, y1 + 3, at.Z, stairMaterial, Stair.NORTH);
					break;
				case SOUTH:
					chunk.setStair(at.X + 2, y1, 	 at.Z + 3, stairMaterial, Stair.NORTH);
					chunk.setStair(at.X + 2, y1 + 1, at.Z + 2, stairMaterial, Stair.NORTH);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setStair(at.X + 1, y1 + 2, at.Z + 2, stairMaterial, Stair.SOUTH);
					chunk.setStair(at.X + 1, y1 + 3, at.Z + 3, stairMaterial, Stair.SOUTH);
					break;
				case WEST:
					chunk.setStair(at.X, 	 y1, 	 at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setStair(at.X + 1, y1 + 1, at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setStair(at.X + 1, y1 + 2, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setStair(at.X	   , y1 + 3, at.Z + 1, stairMaterial, Stair.WEST);
					break;
				case EAST:
					chunk.setStair(at.X + 3, y1, 	 at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setStair(at.X + 2, y1 + 1, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setStair(at.X + 2, y1 + 2, at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setStair(at.X + 3, y1 + 3, at.Z + 2, stairMaterial, Stair.EAST);
					break;
				}

				return;
			}	
			break;
		case CORNER:
			if (floorHeight == 4) {
				switch (stairDirection) {
				case NORTH:
					chunk.setStair(at.X + 3,     y1, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setStair(at.X + 2, y1 + 1, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setStair(at.X + 1, y1 + 2, at.Z + 2, stairMaterial, Stair.SOUTH);
					chunk.setStair(at.X + 1, y1 + 3, at.Z + 3, stairMaterial, Stair.SOUTH);
					break;
				case SOUTH:
					chunk.setStair(at.X,     y1,     at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setStair(at.X + 1, y1 + 1, at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setStair(at.X + 2, y1 + 2, at.Z + 1, stairMaterial, Stair.NORTH);
					chunk.setStair(at.X + 2, y1 + 3, at.Z,     stairMaterial, Stair.NORTH);
					break;
				case WEST:
					chunk.setStair(at.X + 1,     y1, at.Z,     stairMaterial, Stair.SOUTH);
					chunk.setStair(at.X + 1, y1 + 1, at.Z + 1, stairMaterial, Stair.SOUTH);
					chunk.setBlock(at.X + 1, y1 + 1, at.Z + 2, platformMaterial);
					chunk.setStair(at.X + 2, y1 + 2, at.Z + 2, stairMaterial, Stair.EAST);
					chunk.setStair(at.X + 3, y1 + 3, at.Z + 2, stairMaterial, Stair.EAST);
					break;
				case EAST:
					chunk.setStair(at.X + 2,     y1, at.Z + 3, stairMaterial, Stair.NORTH);
					chunk.setStair(at.X + 2, y1 + 1, at.Z + 2, stairMaterial, Stair.NORTH);
					chunk.setBlock(at.X + 2, y1 + 1, at.Z + 1, platformMaterial);
					chunk.setStair(at.X + 1, y1 + 2, at.Z + 1, stairMaterial, Stair.WEST);
					chunk.setStair(at.X,     y1 + 3, at.Z + 1, stairMaterial, Stair.WEST);
					break;
				}

				return;
			}	
			break;
		case STUDIO_A:
			// fall through to the next generator, the one who can deal with variable heights
			break;
		}
		
		// Studio_A
		int y2 = y1 + floorHeight - 1;
		switch (stairDirection) {
		case NORTH:
			for (int i = 0; i < floorHeight; i++) {
				emptyBlock(generator, chunk, at.X + 1, y2, at.Z + i);
				emptyBlock(generator, chunk, at.X + 2, y2, at.Z + i);
				chunk.setStair(at.X + 1, y1 + i, at.Z + i, stairMaterial, Stair.SOUTH);
				chunk.setStair(at.X + 2, y1 + i, at.Z + i, stairMaterial, Stair.SOUTH);
			}
			break;
		case SOUTH:
			for (int i = 0; i < floorHeight; i++) {
				emptyBlock(generator, chunk, at.X + 1, y2, at.Z + i);
				emptyBlock(generator, chunk, at.X + 2, y2, at.Z + i);
				chunk.setStair(at.X + 1, y1 + i, at.Z + floorHeight - i - 1, stairMaterial, Stair.NORTH);
				chunk.setStair(at.X + 2, y1 + i, at.Z + floorHeight - i - 1, stairMaterial, Stair.NORTH);
			}
			break;
		case WEST:
			for (int i = 0; i < floorHeight; i++) {
				emptyBlock(generator, chunk, at.X + i, y2, at.Z + 1);
				emptyBlock(generator, chunk, at.X + i, y2, at.Z + 2);
				chunk.setStair(at.X + i, y1 + i, at.Z + 1, stairMaterial, Stair.EAST);
				chunk.setStair(at.X + i, y1 + i, at.Z + 2, stairMaterial, Stair.EAST);
			}
			break;
		case EAST:
			for (int i = 0; i < floorHeight; i++) {
				emptyBlock(generator, chunk, at.X + i, y2, at.Z + 1);
				emptyBlock(generator, chunk, at.X + i, y2, at.Z + 2);
				chunk.setStair(at.X + floorHeight - i - 1, y1 + i, at.Z + 1, stairMaterial, Stair.WEST);
				chunk.setStair(at.X + floorHeight - i - 1, y1 + i, at.Z + 2, stairMaterial, Stair.WEST);
			}
			break;
		}
	}
	
	private void emptyBlock(WorldGenerator generator, RealChunk chunk, int x, int y, int z) {
		chunk.setBlock(x, y, z, getAirMaterial(generator, y));
		
	}
	
	private void emptyBlocks(WorldGenerator generator, RealChunk chunk, int x1, int x2, int y1, int y2, int z1, int z2) {
		for (int y = y1; y < y2; y++) {
			chunk.setBlocks(x1, x2, y, y + 1, z1, z2, getAirMaterial(generator, y));
		}
	}

	protected void drawStairsWalls(WorldGenerator generator, RealChunk chunk, int y1, 
			int floorHeight, StairWell where, Material wallMaterial, boolean isTopFloor, boolean isBottomFloor) {
		StairAt at = new StairAt(chunk, floorHeight, where);
		int y2 = y1 + floorHeight - 1;
		int yClear = y2 + (isTopFloor ? 0 : 1);
		switch (stairStyle) {
		case CROSSED:
			if (floorHeight == 4) {
				switch (stairDirection) {
				case NORTH:
				case SOUTH:
					emptyBlocks(generator, chunk, at.X + 1, at.X + 3, y1, yClear, at.Z, at.Z + 4);
					chunk.setBlocks(at.X, at.X + 1, y1, y2, at.Z, at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 3, at.X + 4, y1, y2, at.Z, at.Z + 4, wallMaterial);
					if (isTopFloor) {
						chunk.setTrapDoor(at.X + 2, y1 - 1, at.Z, TrapDoor.TOP_NORTH);
						chunk.setTrapDoor(at.X + 1, y1 - 1, at.Z + 3, TrapDoor.TOP_SOUTH);
						chunk.setBlocks(at.X + 2, y1, y2, at.Z, wallMaterial);
						chunk.setBlocks(at.X + 1, y1, y2, at.Z + 3, wallMaterial);
					}
					break;
				case WEST:
				case EAST:
					emptyBlocks(generator, chunk, at.X, at.X + 4, y1, yClear, at.Z + 1, at.Z + 3);
					chunk.setBlocks(at.X, at.X + 4, y1, y2, at.Z, at.Z + 1, wallMaterial);
					chunk.setBlocks(at.X, at.X + 4, y1, y2, at.Z + 3, at.Z + 4, wallMaterial);
					if (isTopFloor) {
						chunk.setTrapDoor(at.X, y1 - 1, at.Z + 2, TrapDoor.TOP_WEST);
						chunk.setTrapDoor(at.X + 3, y1 - 1, at.Z + 1, TrapDoor.TOP_EAST);
						chunk.setBlocks(at.X, y1, y2, at.Z + 2, wallMaterial);
						chunk.setBlocks(at.X + 3, y1, y2, at.Z + 1, wallMaterial);
					}
					break;
				}
				
				return;
			}
			break;
		case LANDING:
			if (floorHeight == 4) {
				switch (stairDirection) {
				case NORTH:
					emptyBlocks(generator, chunk, at.X + 1, at.X + 3, y1, yClear, at.Z,     at.Z + 3);
					chunk.setBlocks(at.X,     at.X + 1, y1, y2,     at.Z,     at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 3, at.X + 4, y1, y2,     at.Z,     at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 1, at.X + 3, y1, y2,     at.Z + 3, at.Z + 4, wallMaterial);
					if (isTopFloor) {
						chunk.setTrapDoor(at.X + 1, y1 - 1, at.Z, TrapDoor.TOP_NORTH);
						chunk.setBlocks(at.X + 1, y1, y2, at.Z, wallMaterial);
					}
					break;
				case SOUTH:
					emptyBlocks(generator, chunk, at.X + 1, at.X + 3, y1, yClear, at.Z + 1, at.Z + 4);
					chunk.setBlocks(at.X,     at.X + 1, y1, y2, 	at.Z,     at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 3, at.X + 4, y1, y2, 	at.Z,     at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, 	at.Z,     at.Z + 1, wallMaterial);
					if (isTopFloor) {
						chunk.setTrapDoor(at.X + 2, y1 - 1, at.Z + 3, TrapDoor.TOP_SOUTH);
						chunk.setBlocks(at.X + 2, y1, y2, at.Z + 3, wallMaterial);
					}
					break;
				case WEST:
					emptyBlocks(generator, chunk, at.X,     at.X + 3, y1, yClear, at.Z + 1, at.Z + 3);
					chunk.setBlocks(at.X,     at.X + 4, y1, y2, 	at.Z,     at.Z + 1, wallMaterial);
					chunk.setBlocks(at.X,     at.X + 4, y1, y2, 	at.Z + 3, at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 3, at.X + 4, y1, y2, 	at.Z + 1, at.Z + 3, wallMaterial);
					if (isTopFloor) {
						chunk.setTrapDoor(at.X, y1 - 1, at.Z + 2, TrapDoor.TOP_WEST);
						chunk.setBlocks(at.X, y1, y2, at.Z + 2, wallMaterial);
					}
					break;
				case EAST:
					emptyBlocks(generator, chunk, at.X + 1, at.X + 4, y1, yClear, at.Z + 1, at.Z + 3);
					chunk.setBlocks(at.X,     at.X + 4, y1, y2, 	at.Z,     at.Z + 1, wallMaterial);
					chunk.setBlocks(at.X,     at.X + 4, y1, y2, 	at.Z + 3, at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X,     at.X + 1, y1, y2, 	at.Z + 1, at.Z + 3, wallMaterial);
					if (isTopFloor) {
						chunk.setTrapDoor(at.X + 3, y1 - 1, at.Z + 1, TrapDoor.TOP_EAST);
						chunk.setBlocks(at.X + 3, y1, y2, at.Z + 1, wallMaterial);
					}
					break;
				}

				return;
			}	
			break;
		case CORNER:
			if (floorHeight == 4) {
				chunk.setBlocks(at.X, at.X + 4, y1, y2, at.Z, at.Z + 4, wallMaterial);
				switch (stairDirection) {
				case NORTH:
					emptyBlocks(generator, chunk, at.X + 1, at.X + 4, y1, yClear, at.Z + 1, at.Z + 2);
					emptyBlocks(generator, chunk, at.X + 1, at.X + 2, y1, yClear, at.Z + 2, at.Z + 4);
					if (isTopFloor) {
						chunk.setTrapDoor(at.X + 3, y1 - 1, at.Z + 1, TrapDoor.TOP_EAST);
						chunk.setBlocks(at.X + 3, y1, y2, at.Z + 1, wallMaterial);
					}
					break;
				case SOUTH:
					emptyBlocks(generator, chunk, at.X,     at.X + 3, y1, yClear, at.Z + 2, at.Z + 3);
					emptyBlocks(generator, chunk, at.X + 2, at.X + 3, y1, yClear, at.Z,     at.Z + 2);
					if (isTopFloor) {
						chunk.setTrapDoor(at.X, y1 - 1, at.Z + 2, TrapDoor.TOP_WEST);
						chunk.setBlocks(at.X, y1, y2, at.Z + 2, wallMaterial);
					}
					break;
				case WEST:
					emptyBlocks(generator, chunk, at.X + 1, at.X + 2, y1, yClear, at.Z,     at.Z + 3);
					emptyBlocks(generator, chunk, at.X + 2, at.X + 4, y1, yClear, at.Z + 2, at.Z + 3);
					if (isTopFloor) {
						chunk.setTrapDoor(at.X + 1, y1 - 1, at.Z, TrapDoor.TOP_NORTH);
						chunk.setBlocks(at.X + 1, y1, y2, at.Z, wallMaterial);
					}
					break;
				case EAST:
					emptyBlocks(generator, chunk, at.X,     at.X + 3, y1, yClear, at.Z + 1, at.Z + 2);
					emptyBlocks(generator, chunk, at.X + 2, at.X + 3, y1, yClear, at.Z + 2, at.Z + 4);
					if (isTopFloor) {
						chunk.setTrapDoor(at.X + 2, y1 - 1, at.Z + 3, TrapDoor.TOP_SOUTH);
						chunk.setBlocks(at.X + 2, y1, y2, at.Z + 3, wallMaterial);
					} 
					break;
				}
				return;
			}	
			break;
		case STUDIO_A:
			// fall through to the next generator, the one who can deal with variable heights
			break;
		}
		
		// Studio_A
		switch (stairDirection) {
		case NORTH:
			emptyBlocks(generator, chunk, at.X + 1, at.X + 3, y1, y2, at.Z, at.Z + 1);
			emptyBlocks(generator, chunk, at.X + 1, at.X + 3, y1, y2, at.Z + floorHeight - 1, at.Z + floorHeight);
			chunk.setBlocks(at.X, at.X + 1, y1, y2, at.Z, at.Z + floorHeight, wallMaterial);
			chunk.setBlocks(at.X + 3, at.X + 4, y1, y2, at.Z, at.Z + floorHeight, wallMaterial);
			if (isTopFloor) {
				chunk.setTrapDoor(at.X + 1, y1 - 1, at.Z, TrapDoor.TOP_NORTH);
				chunk.setTrapDoor(at.X + 2, y1 - 1, at.Z, TrapDoor.TOP_NORTH);
				chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, at.Z, at.Z + 1, wallMaterial);
			}
			if (isBottomFloor) {
				chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, at.Z + floorHeight - 1, at.Z + floorHeight, wallMaterial);
			}
			break;
		case SOUTH:
			emptyBlocks(generator, chunk, at.X + 1, at.X + 3, y1, y2, at.Z, at.Z + 1);
			emptyBlocks(generator, chunk, at.X + 1, at.X + 3, y1, y2, at.Z + floorHeight - 1, at.Z + floorHeight);
			chunk.setBlocks(at.X, at.X + 1, y1, y2, at.Z, at.Z + floorHeight, wallMaterial);
			chunk.setBlocks(at.X + 3, at.X + 4, y1, y2, at.Z, at.Z + floorHeight, wallMaterial);
			if (isTopFloor) {
				chunk.setTrapDoor(at.X + 1, y1 - 1, at.Z + floorHeight - 1, TrapDoor.TOP_SOUTH);
				chunk.setTrapDoor(at.X + 2, y1 - 1, at.Z + floorHeight - 1, TrapDoor.TOP_SOUTH);
				chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, at.Z + floorHeight - 1, at.Z + floorHeight, wallMaterial);
			}
			if (isBottomFloor) {
				chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, at.Z, at.Z + 1, wallMaterial);
			}
			break;
		case WEST:
			emptyBlocks(generator, chunk, at.X, at.X + 1, y1, y2, at.Z + 1, at.Z + 3);
			emptyBlocks(generator, chunk, at.X + floorHeight - 1, at.X + floorHeight, y1, y2, at.Z + 1, at.Z + 3);
			chunk.setBlocks(at.X, at.X + floorHeight, y1, y2, at.Z, at.Z + 1, wallMaterial);
			chunk.setBlocks(at.X, at.X + floorHeight, y1, y2, at.Z + 3, at.Z + 4, wallMaterial);
			if (isTopFloor) {
				chunk.setTrapDoor(at.X, y1 - 1, at.Z + 1, TrapDoor.TOP_WEST);
				chunk.setTrapDoor(at.X, y1 - 1, at.Z + 2, TrapDoor.TOP_WEST);
				chunk.setBlocks(at.X, at.X + 1, y1, y2, at.Z + 1, at.Z + 3, wallMaterial);
			}
			if (isBottomFloor) {
				chunk.setBlocks(at.X + floorHeight - 1, at.X + floorHeight, y1, y2, at.Z + 1, at.Z + 3, wallMaterial);
			}
			break;
		case EAST:
			emptyBlocks(generator, chunk, at.X, at.X + 1, y1, y2, at.Z + 1, at.Z + 3);
			emptyBlocks(generator, chunk, at.X + floorHeight - 1, at.X + floorHeight, y1, y2, at.Z + 1, at.Z + 3);
			chunk.setBlocks(at.X, at.X + floorHeight, y1, y2, at.Z, at.Z + 1, wallMaterial);
			chunk.setBlocks(at.X, at.X + floorHeight, y1, y2, at.Z + 3, at.Z + 4, wallMaterial);
			if (isTopFloor) {
				chunk.setTrapDoor(at.X + floorHeight - 1, y1 - 1, at.Z + 1, TrapDoor.TOP_EAST);
				chunk.setTrapDoor(at.X + floorHeight - 1, y1 - 1, at.Z + 2, TrapDoor.TOP_EAST);
				chunk.setBlocks(at.X + floorHeight - 1, at.X + floorHeight, y1, y2, at.Z + 1, at.Z + 3, wallMaterial);
			}
			if (isBottomFloor) {
				chunk.setBlocks(at.X, at.X + 1, y1, y2, at.Z + 1, at.Z + 3, wallMaterial);
			}
			break;
		}
	};

	protected void drawOtherPillars(RealChunk chunk, int y1, int floorHeight,
			StairWell where, Material wallMaterial) {
		int y2 = y1 + floorHeight - 1;
		if (where != StairWell.SOUTHWEST)
			chunk.setBlocks(3, 5, y1, y2 , 3, 5, wallMaterial);
		if (where != StairWell.SOUTHEAST)
			chunk.setBlocks(3, 5, y1, y2, 11, 13, wallMaterial);
		if (where != StairWell.NORTHWEST)
			chunk.setBlocks(11, 13, y1, y2, 3, 5, wallMaterial);
		if (where != StairWell.NORTHEAST)
			chunk.setBlocks(11, 13, y1, y2, 11, 13, wallMaterial);
	}

	protected void drawFence(WorldGenerator generator, ByteChunk chunk, DataContext context, int inset, int y1, Surroundings neighbors) {
		
		// actual fence
		drawExteriorParts(generator, chunk, context, y1, fenceHeight, inset, inset,
				false, fenceMaterial, fenceMaterial, neighbors);
		
		// holes in fence
		int i = 4 + chunkOdds.getRandomInt(chunk.width / 2);
		int y2 = y1 + fenceHeight;
		byte emptyId = getAirId(generator, y1);
		if (chunkOdds.flipCoin() && !neighbors.toWest())
			chunk.setBlocks(inset, y1, y2, i, emptyId);
		if (chunkOdds.flipCoin() && !neighbors.toEast())
			chunk.setBlocks(chunk.width - 1 - inset, y1, y2, i, emptyId);
		if (chunkOdds.flipCoin() && !neighbors.toNorth())
			chunk.setBlocks(i, y1, y2, inset, emptyId);
		if (chunkOdds.flipCoin() && !neighbors.toSouth())
			chunk.setBlocks(i, y1, y2, chunk.width - 1 - inset, emptyId);
	}
}
