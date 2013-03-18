package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.CurvedWallFactory;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.Direction.Facing;
import me.daddychurchill.CityWorld.Support.Direction.Stair;
import me.daddychurchill.CityWorld.Support.Direction.StairWell;
import me.daddychurchill.CityWorld.Support.Direction.TrapDoor;
import me.daddychurchill.CityWorld.Support.MaterialFactory;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.OutsideWEWallFactory;
import me.daddychurchill.CityWorld.Support.OutsideNSWallFactory;
import me.daddychurchill.CityWorld.Support.InteriorWallFactory;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SurroundingFloors;
import me.daddychurchill.CityWorld.Support.Direction.Door;
import me.daddychurchill.CityWorld.Support.Surroundings;

import org.bukkit.Material;

public abstract class BuildingLot extends ConnectedLot {
	
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
	
	protected final static byte airId = (byte) Material.AIR.getId();
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
	protected double oddsOfADoor = DataContext.oddsExtremelyLikely;
	protected Material columnMaterial;
	
	protected int navLightX = 0;
	protected int navLightY = 0;
	protected int navLightZ = 0;
	
	private final static Material fenceMaterial = Material.IRON_FENCE;
	private final static int fenceHeight = 3;
	
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
		rounded = chunkOdds.playOdds(context.oddsOfSimilarBuildingRounding);
		roofStyle = pickRoofStyle(chunkOdds);
		roofFeature = pickRoofFeature(chunkOdds);
		roofScale = 1 + chunkOdds.getRandomInt(2);
		stairStyle = pickStairStyle(chunkOdds);
		stairDirection = pickStairDirection(chunkOdds);
		interiorStyle = pickInteriorStyle(chunkOdds);
		columnMaterial = Material.COBBLE_WALL;
		wallsWE = new OutsideWEWallFactory(chunkOdds, platmap.generator.settings.includeDecayedBuildings);
		wallsNS = new OutsideNSWallFactory(wallsWE);
		wallsCurved = new CurvedWallFactory(wallsWE);
		wallsInterior = new InteriorWallFactory(chunkOdds, platmap.generator.settings.includeDecayedBuildings);
	}

	static public Facing pickStairDirection(Odds chunkOdds) {
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

	static public StairStyle pickStairStyle(Odds chunkOdds) {
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

	static public InteriorStyle pickInteriorStyle(Odds chunkOdds) {
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

	@Override
	public boolean isValidStrataY(WorldGenerator generator, int blockX, int blockY, int blockZ) {
		return blockY < generator.streetLevel - basementFloorHeight * depth + 1;
	}

	@Override
	protected boolean isShaftableLevel(WorldGenerator generator, int blockY) {
		return blockY >= 0 && blockY < generator.streetLevel - basementFloorHeight * depth - 2 - 16;	
	}

	static public RoofStyle pickRoofStyle(Odds chunkOdds) {
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
	
	static public RoofFeature pickRoofFeature(Odds chunkOdds) {
		switch (chunkOdds.getRandomInt(3)) {
		case 1:
			return RoofFeature.ANTENNAS;
		case 2:
			return RoofFeature.CONDITIONERS;
		default:
			return RoofFeature.TILE;
		}
	}
	
	static public Material pickGlassMaterial(Odds chunkOdds) {
		switch (chunkOdds.getRandomInt(2)) {
		case 1:
			return Material.THIN_GLASS;
		default:
			return Material.GLASS;
		}
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
	
	protected void drawCeilings(ByteChunk byteChunk, DataContext context, int y1, int height, 
			int insetNS, int insetWE, boolean allowRounded, 
			Material material, Surroundings heights) {
		
		// precalculate
		byte materialId = (byte) material.getId();
		int y2 = y1 + height;
		boolean stillNeedCeiling = true;
		int inset = Math.max(insetNS, insetWE);
		
		// rounded and square inset and there are exactly two neighbors?
		if (allowRounded && rounded) { // already know that... && insetNS == insetWE && heights.getNeighborCount() == 2
			int innerCorner = (byteChunk.width - inset * 2) + inset;
			if (heights.toNorth()) {
				if (heights.toEast()) {
					byteChunk.setArcNorthEast(inset, y1, y2, materialId, true);
					if (!heights.toNorthEast()) 
						byteChunk.setArcNorthEast(innerCorner, y1, y2, airId, true);
					stillNeedCeiling = false;
				} else if (heights.toWest()) {
					byteChunk.setArcNorthWest(inset, y1, y2, materialId, true);
					if (!heights.toNorthWest())
						byteChunk.setArcNorthWest(innerCorner, y1, y2, airId, true);
					stillNeedCeiling = false;
				}
			} else if (heights.toSouth()) {
				if (heights.toEast()) {
					byteChunk.setArcSouthEast(inset, y1, y2, materialId, true);
					if (!heights.toSouthEast())
						byteChunk.setArcSouthEast(innerCorner, y1, y2, airId, true);
					stillNeedCeiling = false;
				} else if (heights.toWest()) {
					byteChunk.setArcSouthWest(inset, y1, y2, materialId, true);
					if (!heights.toSouthWest())
						byteChunk.setArcSouthWest(innerCorner, y1, y2, airId, true);
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
	
	protected void drawExteriorWalls(ByteChunk byteChunk, DataContext context, int y1, int height, 
			int insetNS, int insetWE, boolean allowRounded, 
			Material materialWall, Material materialGlass, Surroundings heights) {
		
		// precalculate
		byte wallId = (byte) materialWall.getId();
		byte glassId = (byte) materialGlass.getId();
		int y2 = y1 + height;
		boolean stillNeedWalls = true;
		int inset = Math.max(insetNS, insetWE);
		
		// rounded and square inset and there are exactly two neighbors?
		if (allowRounded && rounded) { 
			
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
	
	protected void drawInteriorWalls(WorldGenerator generator, RealChunk chunk, DataContext context, 
			int floor, int y1, int height, 
			int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, 
			StairWell where, Surroundings heights) {
		drawInteriorWalls(generator, chunk, context, interiorStyle, floor, y1, height, insetNS, insetWE, allowRounded, materialWall, materialGlass, where, heights);
	}
	
	private void drawInteriorWalls(WorldGenerator generator, RealChunk chunk, DataContext context, InteriorStyle style,
			int floor, int y1, int height, 
			int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, 
			StairWell where, Surroundings heights) {
		switch (style) {
		case COLUMNS_ONLY:
			drawActualInteriorColumns(generator, chunk, context, floor, y1, height, insetNS, insetWE, allowRounded, materialWall, materialGlass, where, heights);
			break;
		case COLUMNS_OFFICES:
			drawActualInteriorColumns(generator, chunk, context, floor, y1, height, insetNS, insetWE, allowRounded, materialWall, materialGlass, where, heights);
			drawActualInteriorOffice(generator, chunk, context, floor, y1, height, insetNS, insetWE, allowRounded, materialWall, materialGlass, where, heights);
			break;
		case WALLS_ONLY:
			drawActualInteriorWalls(generator, chunk, context, floor, y1, height, insetNS, insetWE, allowRounded, materialWall, materialGlass, where, heights);
			break;
		case WALLS_OFFICES:
			drawActualInteriorWalls(generator, chunk, context, floor, y1, height, insetNS, insetWE, allowRounded, materialWall, materialGlass, where, heights);
			drawActualInteriorOffice(generator, chunk, context, floor, y1, height, insetNS, insetWE, allowRounded, materialWall, materialGlass, where, heights);
			break;
		case RANDOM:
			if (chunkOdds.flipCoin())
				drawInteriorWalls(generator, chunk, context, InteriorStyle.COLUMNS_OFFICES, 
						floor, y1, height, insetNS, insetWE, allowRounded, materialWall, materialGlass, where, heights);
			else
				drawInteriorWalls(generator, chunk, context, InteriorStyle.WALLS_OFFICES, 
						floor, y1, height, insetNS, insetWE, allowRounded, materialWall, materialGlass, where, heights);
			break;
		}
	}
		
	protected void drawActualInteriorColumns(WorldGenerator generator, RealChunk chunk, DataContext context, 
			int floor, int y1, int height, 
			int insetNS, int insetWE, boolean allowRounded, 
			Material materialWall, Material materialGlass, 
			StairWell where, Surroundings heights) {

		// precalculate
		int y2 = y1 + height;
		
		// first try the narrow logic (single wall in the middle)
		if (!heights.toNorthWest() && !heights.toNorthEast() && !heights.toSouthWest() && !heights.toSouthEast()) {
			chunk.setBlocks(7, 9, y1, y2, 7, 9, columnMaterial);
			
		// if the narrow logic doesn't handle it, try to use the wide logic (two walls in the middle)
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
	
	protected void drawActualInteriorWalls(WorldGenerator generator, RealChunk chunk, DataContext context, 
			int floor, int y1, int height, 
			int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, 
			StairWell where, Surroundings heights) {
		
		//TODO Atrium in the middle of 2x2
		
		// precalculate
		byte wallId = (byte) materialWall.getId();
		byte glassId = (byte) materialGlass.getId();
		int y2 = y1 + height;
//		int x1 = heights.toWest() ? 0 : insetWE + 1;
//		int x2 = chunk.width - (heights.toEast() ? 0 : (insetWE + 1));
//		int z1 = heights.toNorth() ? 0 : insetNS + 1;
//		int z2 = chunk.width - (heights.toSouth() ? 0 : (insetNS + 1));
		int x1 = heights.toWest() ? 0 : insetWE + 1;
		int x2 = chunk.width - (heights.toEast() ? 0 : (insetWE));
		int z1 = heights.toNorth() ? 0 : insetNS + 1;
		int z2 = chunk.width - (heights.toSouth() ? 0 : (insetNS));

		// first try the narrow logic (single wall in the middle)
		if (!heights.toNorthWest() && !heights.toNorthEast() && !heights.toSouthWest() && !heights.toSouthEast()) {
			
			// Northward
			if (heights.toNorth()) {
//				materialWall = Material.COBBLESTONE;
//				wallId = (byte) materialWall.getId();
				
				// draw out
				if (where == StairWell.NONE) {
					drawInteriorNSWall(chunk, 7, y1, y2, 4, 7, wallId, glassId);
					chunk.setBlocks(7, y1, y2, 7, materialWall);
					drawInteriorNSDoor(chunk, 7, y1, y2, 4, materialWall);
				}
				
				// draw cap
				drawInteriorNSWall(chunk, 7, y1, y2, 1, 4, wallId, glassId);
				drawInteriorWEWall(chunk, x1, 8, y1, y2, 0, wallId, glassId);
				drawInteriorWEDoor(chunk, 5, y1, y2, 0, materialWall);

			} else if (where == StairWell.NONE && !allowRounded) {
//				materialWall = Material.BEDROCK;
//				wallId = (byte) materialWall.getId();
				
				// draw short wall
				drawInteriorNSWall(chunk, 7, y1, y2, z1, 8, wallId, glassId);
				drawInteriorNSDoor(chunk, 7, y1, y2, 5, materialWall);
				
			}
				
			// Eastward
			if (heights.toEast()) {
//				materialWall = Material.CLAY;
//				wallId = (byte) materialWall.getId();
				
				// draw out
				if (where == StairWell.NONE) {
					drawInteriorWEWall(chunk, 9, 12, y1, y2, 7, wallId, glassId);
					chunk.setBlocks(8, y1, y2, 7, materialWall);
					drawInteriorWEDoor(chunk, 9, y1, y2, 7, materialWall);
				}
				
				// draw cap
				drawInteriorWEWall(chunk, 12, 15, y1, y2, 7, wallId, glassId);
				drawInteriorNSWall(chunk, 15, y1, y2, z1, 8, wallId, glassId);
				drawInteriorNSDoor(chunk, 15, y1, y2, 5, materialWall);
				
			} else if (where == StairWell.NONE && !allowRounded) {
//				materialWall = Material.SAND;
//				wallId = (byte) materialWall.getId();
				
				// draw short wall
				drawInteriorWEWall(chunk, 8, x2, y1, y2, 7, wallId, glassId);
				drawInteriorWEDoor(chunk, 8, y1, y2, 7, materialWall);
				
			}
			
			// Westward
			if (heights.toWest()) {
//				materialWall = Material.IRON_BLOCK;
//				wallId = (byte) materialWall.getId();
				
				// draw out
				if (where == StairWell.NONE) {
					drawInteriorWEWall(chunk, 4, 7, y1, y2, 8, wallId, glassId);
					chunk.setBlocks(7, y1, y2, 8, materialWall);
					drawInteriorWEDoor(chunk, 4, y1, y2, 8, materialWall);
				}
				
				// draw cap
				drawInteriorWEWall(chunk, 1, 4, y1, y2, 8, wallId, glassId);
				drawInteriorNSWall(chunk, 0, y1, y2, 8, z2, wallId, glassId);
				drawInteriorNSDoor(chunk, 0, y1, y2, 8, materialWall);
				
			} else if (where == StairWell.NONE && !allowRounded) {
//				materialWall = Material.GOLD_BLOCK;
//				wallId = (byte) materialWall.getId();
				
				// draw short wall
				drawInteriorWEWall(chunk, x1, 8, y1, y2, 8, wallId, glassId);
				drawInteriorWEDoor(chunk, 5, y1, y2, 8, materialWall);
				
			}
			
			// Southward
			if (heights.toSouth()) {
//				materialWall = Material.DIAMOND_BLOCK;
//				wallId = (byte) materialWall.getId();
				
				// draw out
				if (where == StairWell.NONE) {
					drawInteriorNSWall(chunk, 8, 13, y1, y2, 15, wallId, glassId);
					chunk.setBlocks(8, y1, y2, 8, materialWall);
					drawInteriorNSDoor(chunk, 8, y1, y2, 9, materialWall);
				}
				
				// draw cap
				drawInteriorNSWall(chunk, 8, y1, y2, 12, 15, wallId, glassId);
				drawInteriorWEWall(chunk, 8, x2, y1, y2, 15, wallId, glassId);
				drawInteriorWEDoor(chunk, 8, y1, y2, 15, materialWall);

			} else if (where == StairWell.NONE && !allowRounded) {
//				materialWall = Material.LAPIS_BLOCK;
//				wallId = (byte) materialWall.getId();
				
				// draw short wall
				drawInteriorNSWall(chunk, 8, y1, y2, 8, z2, wallId, glassId);
				drawInteriorNSDoor(chunk, 8, y1, y2, 8, materialWall);
				
			}
			
		// if the narrow logic doesn't handle it, try to use the wide logic (two walls in the middle)
		} else {
		
			// NW corner
			if (heights.toNorthWest()) {
//				wallId = (byte) Material.COBBLESTONE.getId();
				if (heights.toNorth())
					drawInteriorNSWall(chunk, 4, y1, y2, 0, wallId, glassId);
				if (heights.toWest()) {
					drawInteriorWEWall(chunk, 0, y1, y2, 4, wallId, glassId);
					if (where == StairWell.NORTHWEST) {
						drawInteriorNSDoor(chunk, 4, y1, y2, 2, materialWall);
						drawInteriorWEDoor(chunk, 2, y1, y2, 4, materialWall);
					} else {
						drawInteriorWEDoors(chunk, 2, y1, y2, 4, materialWall);
					}
				}
				
			} else {
//				wallId = (byte) Material.BEDROCK.getId();
				if (!heights.toNorth() && heights.toSouth() && heights.toWest()) {
					drawInteriorNSWall(chunk, 4, y1, y2, z1, 8, wallId, glassId);
				} else if (!heights.toWest() && heights.toEast() && heights.toNorth()) {
					drawInteriorWEWall(chunk, x1, 8, y1, y2, 4, wallId, glassId);
				}
				if (where == StairWell.NORTHEAST)
					drawInteriorWEDoor(chunk, 6, y1, y2, 4, materialWall);
				if (where == StairWell.SOUTHWEST)
					drawInteriorNSDoor(chunk, 4, y1, y2, 6, materialWall);
				
			}
			
			// NE corner
			if (heights.toNorthEast()) {
//				wallId = (byte) Material.CLAY.getId();
				if (heights.toEast())
					drawInteriorWEWall(chunk, 8, y1, y2, 4, wallId, glassId);
				if (heights.toNorth()) {
					drawInteriorNSWall(chunk, 11, y1, y2, 0, wallId, glassId);
					if (where == StairWell.NORTHEAST) {
						drawInteriorNSDoor(chunk, 11, y1, y2, 2, materialWall);
						drawInteriorWEDoor(chunk, 11, y1, y2, 4, materialWall);
					} else {
						drawInteriorNSDoors(chunk, 11, y1, y2, 2, materialWall);
					}
				}
				
			} else {
//				wallId = (byte) Material.SAND.getId();
				if (!heights.toNorth() && heights.toSouth() && heights.toEast()) {
					drawInteriorNSWall(chunk, 11, y1, y2, z1, 8, wallId, glassId);
				} else if (!heights.toEast() && heights.toWest() && heights.toNorth()) {
					drawInteriorWEWall(chunk, 8, x2, y1, y2, 4, wallId, glassId);
				}
				if (where == StairWell.NORTHWEST)
					drawInteriorWEDoor(chunk, 7, y1, y2, 4, materialWall);
				if (where == StairWell.SOUTHEAST)
					drawInteriorNSDoor(chunk, 11, y1, y2, 6, materialWall);
				
			}
			
			// SW corner
			if (heights.toSouthWest()) {
//				wallId = (byte) Material.IRON_BLOCK.getId();
				if (heights.toWest())
					drawInteriorWEWall(chunk, 0, y1, y2, 11, wallId, glassId);
				if (heights.toSouth()) {
					drawInteriorNSWall(chunk, 4, y1, y2, 8, wallId, glassId);
					if (where == StairWell.SOUTHWEST) {
						drawInteriorNSDoor(chunk, 4, y1, y2, 11, materialWall);
						drawInteriorWEDoor(chunk, 2, y1, y2, 11, materialWall);
					} else {
						drawInteriorNSDoors(chunk, 4, y1, y2, 9, materialWall);
					}
				}
				
			} else {
//				wallId = (byte) Material.GOLD_BLOCK.getId();
				if (!heights.toSouth() && heights.toNorth() && heights.toWest()) {
					drawInteriorNSWall(chunk, 4, y1, y2, 8, z2, wallId, glassId);
				} else if (!heights.toWest() && heights.toEast() && heights.toSouth()) {
					drawInteriorWEWall(chunk, x1, 8, y1, y2, 11, wallId, glassId);
				}
				if (where == StairWell.NORTHWEST)
					drawInteriorNSDoor(chunk, 4, y1, y2, 7, materialWall);
				if (where == StairWell.SOUTHEAST)
					drawInteriorWEDoor(chunk, 6, y1, y2, 11, materialWall);
				
			}
			
			// SE corner
			if (heights.toSouthEast()) {
//				wallId = (byte) Material.DIAMOND_BLOCK.getId();
				if (heights.toSouth()) 
					drawInteriorNSWall(chunk, 11, y1, y2, 8, wallId, glassId);
				if (heights.toEast()) {
					drawInteriorWEWall(chunk, 8, y1, y2, 11, wallId, glassId);
					if (where == StairWell.SOUTHEAST) {
						drawInteriorNSDoor(chunk, 11, y1, y2, 11, materialWall);
						drawInteriorWEDoor(chunk, 11, y1, y2, 11, materialWall);
					} else {
						drawInteriorWEDoors(chunk, 9, y1, y2, 11, materialWall);
					}
				}
			} else {
//				wallId = (byte) Material.LAPIS_BLOCK.getId();
				if (!heights.toSouth() && heights.toNorth() && heights.toEast()) {
					drawInteriorNSWall(chunk, 11, y1, y2, 8, z2, wallId, glassId);
				} else if (!heights.toEast() && heights.toWest() && heights.toSouth()) {
					drawInteriorWEWall(chunk, 8, x2, y1, y2, 11, wallId, glassId);
				}
				if (where == StairWell.NORTHEAST)
					drawInteriorNSDoor(chunk, 11, y1, y2, 7, materialWall);
				if (where == StairWell.SOUTHWEST)
					drawInteriorWEDoor(chunk, 7, y1, y2, 11, materialWall);

			}
		}

//		chunk.setBlocks(x1, y1, y2, z1, Material.IRON_ORE);
//		chunk.setBlocks(x1, y1, y2, z2 - 1, Material.GOLD_ORE);
//		chunk.setBlocks(x2 - 1, y1, y2, z1, Material.LAPIS_ORE);
//		chunk.setBlocks(x2 - 1, y1, y2, z2 - 1, Material.DIAMOND_ORE);
		
//		chunk.setBlocks(0, y1, y2, 0, Material.GLOWING_REDSTONE_ORE);
//		chunk.setBlocks(0, y1, y2, 15, Material.GLOWING_REDSTONE_ORE);
//		chunk.setBlocks(15, y1, y2, 0, Material.GLOWING_REDSTONE_ORE);
//		chunk.setBlocks(15, y1, y2, 15, Material.GLOWING_REDSTONE_ORE);
	
	}
	
	protected void drawActualInteriorOffice(WorldGenerator generator, RealChunk chunk, DataContext context, 
			int floor, int y1, int height, 
			int insetNS, int insetWE, boolean allowRounded,
			Material materialWall, Material materialGlass, 
			StairWell where, Surroundings heights) {
		
		// precalculate
		int x1 = heights.toWest() ? 0 : insetWE + 1;
		int x2 = chunk.width - (heights.toEast() ? 0 : (insetWE + 1));
		int z1 = heights.toNorth() ? 0 : insetNS + 1;
		int z2 = chunk.width - (heights.toSouth() ? 0 : (insetNS + 1));
		
		// first try the narrow logic (single wall in the middle)
		if (!heights.toNorthWest() && !heights.toNorthEast() && !heights.toSouthWest() && !heights.toSouthEast()) {
			
			// Northward
			if (heights.toNorth()) {

				// draw wall offices
				drawOffice(generator, chunk, floor, 3, y1, 1, height, Facing.NORTH, materialWall, materialGlass);
				drawOffice(generator, chunk, floor, 8, y1, 1, height, Facing.WEST, materialWall, materialGlass);
				
			} else if (where == StairWell.NONE && !allowRounded) {
				
				if (!heights.toWest()) {
					
					// draw corner office
					drawOffice(generator, chunk, floor, x1, y1, z1, height, Facing.SOUTH, materialWall, materialGlass);
				}
			}
				
			// Eastward
			if (heights.toEast()) {
				
				// draw wall offices
				drawOffice(generator, chunk, floor, 12, y1, 3, height, Facing.EAST, materialWall, materialGlass);
				drawOffice(generator, chunk, floor, 12, y1, 8, height, Facing.NORTH, materialWall, materialGlass);
				
			} else if (where == StairWell.NONE && !allowRounded) {
				
				if (!heights.toNorth()) {
					
					// draw corner office
					drawOffice(generator, chunk, floor, x2 - 3, y1, z1, height, Facing.WEST, materialWall, materialGlass);
				}
			}
			
			// Westward
			if (heights.toWest()) {
				
				// draw wall offices
				drawOffice(generator, chunk, floor, 1, y1, 5, height, Facing.SOUTH, materialWall, materialGlass);
				drawOffice(generator, chunk, floor, 1, y1, 10, height, Facing.WEST, materialWall, materialGlass);
				
			} else if (where == StairWell.NONE && !allowRounded) {
				
				if (!heights.toSouth()) {
					
					// draw corner office
					drawOffice(generator, chunk, floor, x1, y1, z2 - 3, height, Facing.EAST, materialWall, materialGlass);
				}
			}
			
			// Southward
			if (heights.toSouth()) {

				// draw wall offices
				drawOffice(generator, chunk, floor, 5, y1, 12, height, Facing.EAST, materialWall, materialGlass);
				drawOffice(generator, chunk, floor, 10, y1, 12, height, Facing.SOUTH, materialWall, materialGlass);
				
			} else if (where == StairWell.NONE && !allowRounded) {
				
				if (!heights.toEast()) {
					
					// draw corner office
					drawOffice(generator, chunk, floor, x2 - 3, y1, z2 - 3, height, Facing.NORTH, materialWall, materialGlass);
				}
			}
			
		// if the narrow logic doesn't handle it, try to use the wide logic (two walls in the middle)
		} else {
		
			// NW corner
			if (heights.toNorthWest()) {
				
				// randomly drawn
				drawOffice(generator, chunk, floor, 1, y1, 0, height, Facing.EAST, materialWall, materialGlass);
				drawOffice(generator, chunk, floor, 5, y1, 0, height, Facing.WEST, materialWall, materialGlass);
			} else {
				
				// offices
				if (!allowRounded) {
					if (!heights.toNorth() && !heights.toWest()) {
						drawOffice(generator, chunk, floor, x1, y1, z1, height, Facing.NORTH, materialWall, materialGlass);
						
					} else if (heights.toNorth() && !heights.toWest()) {
						drawOffice(generator, chunk, floor, x1, y1, 1, height, Facing.SOUTH, materialWall, materialGlass);
						drawOffice(generator, chunk, floor, x1, y1, 5, height, Facing.NORTH, materialWall, materialGlass);
						
					} else if (heights.toWest() && !heights.toNorth()) {
						drawOffice(generator, chunk, floor, 1, y1, z1, height, Facing.EAST, materialWall, materialGlass);
						drawOffice(generator, chunk, floor, 5, y1, z1, height, Facing.WEST, materialWall, materialGlass);
					}
				}
			}
			
			// NE corner
			if (heights.toNorthEast()) {
				
				// randomly drawn
				drawOffice(generator, chunk, floor, 13, y1, 1, height, Facing.SOUTH, materialWall, materialGlass);
				drawOffice(generator, chunk, floor, 13, y1, 5, height, Facing.NORTH, materialWall, materialGlass);

			} else {
				
				// offices
				if (!allowRounded) {
					if (!heights.toNorth() && !heights.toEast()) {
						drawOffice(generator, chunk, floor, x2 - 3, y1, z1, height, Facing.EAST, materialWall, materialGlass);
					} else if (heights.toNorth() && !heights.toEast()) {
						drawOffice(generator, chunk, floor, x2 - 3, y1, 1, height, Facing.SOUTH, materialWall, materialGlass);
						drawOffice(generator, chunk, floor, x2 - 3, y1, 5, height, Facing.NORTH, materialWall, materialGlass);
					} else if (heights.toEast() && !heights.toNorth()) {
						drawOffice(generator, chunk, floor, 8, y1, z1, height, Facing.EAST, materialWall, materialGlass);
						drawOffice(generator, chunk, floor, 12, y1, z1, height, Facing.WEST, materialWall, materialGlass);
					}
				}
			}
			
			// SW corner
			if (heights.toSouthWest()) {
				
				// randomly drawn
				drawOffice(generator, chunk, floor, 0, y1, 8, height, Facing.SOUTH, materialWall, materialGlass);
				drawOffice(generator, chunk, floor, 0, y1, 12, height, Facing.NORTH, materialWall, materialGlass);
			} else {
				
				// offices
				if (!allowRounded) {
					if (!heights.toSouth() && !heights.toWest()) {
						drawOffice(generator, chunk, floor, x1, y1, z2 - 3, height, Facing.WEST, materialWall, materialGlass);
					} else if (heights.toSouth() && !heights.toWest()) {
						drawOffice(generator, chunk, floor, x1, y1, 8, height, Facing.SOUTH, materialWall, materialGlass);
						drawOffice(generator, chunk, floor, x1, y1, 12, height, Facing.NORTH, materialWall, materialGlass);
					} else if (heights.toWest() && !heights.toSouth()) {
						drawOffice(generator, chunk, floor, 1, y1, z2 - 3, height, Facing.EAST, materialWall, materialGlass);
						drawOffice(generator, chunk, floor, 5, y1, z2 - 3, height, Facing.WEST, materialWall, materialGlass);
					}
				}
			}
			
			// SE corner
			if (heights.toSouthEast()) {
				
				// randomly drawn
				drawOffice(generator, chunk, floor, 8, y1, 13, height, Facing.EAST, materialWall, materialGlass);
				drawOffice(generator, chunk, floor, 12, y1, 13, height, Facing.WEST, materialWall, materialGlass);
			} else {
				
				// offices
				if (!allowRounded) {
					if (!heights.toSouth() && !heights.toEast()) {
						drawOffice(generator, chunk, floor, x2 - 3, y1, z2 - 3, height, Facing.SOUTH, materialWall, materialGlass);
					} else if (heights.toSouth() && !heights.toEast()) {
						drawOffice(generator, chunk, floor, x2 - 3, y1, 8, height, Facing.SOUTH, materialWall, materialGlass);
						drawOffice(generator, chunk, floor, x2 - 3, y1, 12, height, Facing.NORTH, materialWall, materialGlass);
					} else if (heights.toEast() && !heights.toSouth()) {
						drawOffice(generator, chunk, floor, 8, y1, z2 - 3, height, Facing.EAST, materialWall, materialGlass);
						drawOffice(generator, chunk, floor, 12, y1, z2 - 3, height, Facing.WEST, materialWall, materialGlass);
					}
				}
			}
		}
	}
	
	private void drawOffice(WorldGenerator generator, RealChunk chunk, int floor, int x, int y, 
			int z, int height, Facing sideWithWall, Material materialWall, Material materialGlass) {
		
		generator.roomProvider_Office.draw(chunk, chunkOdds, floor, x, y, z, 3, height, 3, sideWithWall, materialWall, materialGlass);
//		generator.roomProvider_Library.Draw(chunk, chunkOdds, x, y, z, 3, height, 3, sideWithWall, materialWall, materialGlass);
//		generator.roomProvider_Cafe.Draw(chunk, chunkOdds, x, y, z, 3, height, 3, sideWithWall, materialWall, materialGlass);
	}
	
	private void drawInteriorNSWall(RealChunk chunk, int x, int y1, int y2, int z, byte wallId, byte glassId) {
		drawInteriorNSWall(chunk, x, y1, y2, z, z + 8, wallId, glassId);
	}
	
	private void drawInteriorWEWall(RealChunk chunk, int x, int y1, int y2, int z, byte wallId, byte glassId) {
		drawInteriorWEWall(chunk, x, x + 8, y1, y2, z, wallId, glassId);
	}
	
	private void drawInteriorNSWall(RealChunk chunk, int x, int y1, int y2, int z1, int z2, byte wallId, byte glassId) {
		chunk.setBlocks(x, x + 1, y1, y2, z1, z2, wallId, glassId, wallsInterior, false);
	}
	
	private void drawInteriorWEWall(RealChunk chunk, int x1, int x2, int y1, int y2, int z, byte wallId, byte glassId) {
		chunk.setBlocks(x1, x2, y1, y2, z, z + 1, wallId, glassId, wallsInterior, false);
	}
	
	private void drawInteriorNSDoors(RealChunk chunk, int x, int y1, int y2, int z, Material wall) {
		if (chunkOdds.playOdds(oddsOfADoor))
			drawDoor(chunk, x, x, x, y1, y2, z, z + 1, z + 2, Door.WESTBYNORTHWEST, wall);
		if (chunkOdds.playOdds(oddsOfADoor))
			drawDoor(chunk, x, x, x, y1, y2, z + 2, z + 3, z + 4, Door.EASTBYSOUTHEAST, wall);
	}
	
	private void drawInteriorWEDoors(RealChunk chunk, int x, int y1, int y2, int z, Material wall) {
		if (chunkOdds.playOdds(oddsOfADoor))
			drawDoor(chunk, x, x + 1, x + 2, y1, y2, z, z, z, Door.NORTHBYNORTHWEST, wall);
		if (chunkOdds.playOdds(oddsOfADoor))
			drawDoor(chunk, x + 2, x + 3, x + 4, y1, y2, z, z, z, Door.SOUTHBYSOUTHEAST, wall);
	}
	
	private void drawInteriorNSDoor(RealChunk chunk, int x, int y1, int y2, int z, Material wall) {
		if (chunkOdds.playOdds(oddsOfADoor))
			drawDoor(chunk, x, x, x, y1, y2, z, z + 1, z + 2, Door.WESTBYNORTHWEST, wall);
	}
	
	private void drawInteriorWEDoor(RealChunk chunk, int x, int y1, int y2, int z, Material wall) {
		if (chunkOdds.playOdds(oddsOfADoor))
			drawDoor(chunk, x, x + 1, x + 2, y1, y2, z, z, z, Door.NORTHBYNORTHWEST, wall);
	}
	
	//TODO roof fixtures (peak, helipad, air conditioning, stairwells access, penthouse, castle trim, etc.
	protected void drawRoof(ByteChunk chunk, DataContext context, int y1, 
			int insetNS, int insetWE, boolean allowRounded, 
			Material material, Surroundings heights) {
		switch (roofStyle) {
		case PEAK:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < aboveFloorHeight; i++) {
					if (i == aboveFloorHeight - 1)
						drawCeilings(chunk, context, y1 + i * roofScale, roofScale, insetNS + i, insetWE + i, allowRounded, material, heights);
					else
						drawExteriorWalls(chunk, context, y1 + i * roofScale, roofScale, insetNS + i, insetWE + i, allowRounded, material, material, heights);
				}
			} else
				drawEdgedRoof(chunk, context, y1, insetNS, insetWE, allowRounded, material, true, heights);
			break;
		case TENT_NORTHSOUTH:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < aboveFloorHeight; i++) {
					if (i == aboveFloorHeight - 1)
						drawCeilings(chunk, context, y1 + i * roofScale, roofScale, insetNS + i, insetWE, allowRounded, material, heights);
					else
						drawExteriorWalls(chunk, context, y1 + i * roofScale, roofScale, insetNS + i, insetWE, allowRounded, material, material, heights);
				}
			} else
				drawEdgedRoof(chunk, context, y1, insetNS, insetWE, allowRounded, material, true, heights);
			break;
		case TENT_WESTEAST:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < aboveFloorHeight; i++) {
					if (i == aboveFloorHeight - 1)
						drawCeilings(chunk, context, y1 + i * roofScale, roofScale, insetNS, insetWE + i, allowRounded, material, heights);
					else
						drawExteriorWalls(chunk, context, y1 + i * roofScale, roofScale, insetNS, insetWE + i, allowRounded, material, material, heights);
				}
			} else
				drawEdgedRoof(chunk, context, y1, insetNS, insetWE, allowRounded, material, true, heights);
			break;
		case EDGED:
			drawEdgedRoof(chunk, context, y1, insetNS, insetWE, allowRounded, material, true, heights);
			break;
		case FLATTOP:
			drawEdgedRoof(chunk, context, y1, insetNS, insetWE, allowRounded, material, false, heights);
			break;
		}
	}
	
	private void drawEdgedRoof(ByteChunk chunk, DataContext context, int y1, 
			int insetNS, int insetWE, boolean allowRounded, 
			Material material, boolean doEdge, Surroundings heights) {
		
		// a little bit of edge 
		if (doEdge)
			drawExteriorWalls(chunk, context, y1, 1, insetNS, insetWE, allowRounded, material, material, heights);
		
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
			drawCeilings(chunk, context, y1, 1, insetNS + 1, insetWE + 1, allowRounded, tileMaterial, heights);
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
	
	protected void drawDoors(RealChunk chunk, int y1, int floorHeight, 
			int insetNS, int insetWE, StairWell where, 
			Surroundings heights, Material wallMaterial) {
		
		int center = chunk.width / 2;
		int w1 = chunk.width - 1;
		int w2 = chunk.width - 2;
		int w3 = chunk.width - 3;
		int x1 = insetWE;
		int x2 = w1 - insetWE;
		int z1 = insetNS;
		int z2 = w1 - insetNS;
		int y2 = y1 + floorHeight - 1;
		
		switch (where) {
		case NORTHWEST:
			if (chunkOdds.flipCoin())
				drawDoor(chunk, x1, x1, x1, y1, y2, 0, 1, 2, Door.WESTBYNORTHWEST, wallMaterial); 
			if (chunkOdds.flipCoin())
				drawDoor(chunk, w1, w2, w3, y1, y2, z2, z2, z2, Door.SOUTHBYSOUTHEAST, wallMaterial); 
			break;
		case NORTH:	
			if (!heights.toNorth() && chunkOdds.flipCoin())
				drawDoor(chunk, center - 1, center, center + 1, 
						y1, y2, 
						z1, z1, z1, 
						Door.NORTHBYNORTHEAST, wallMaterial);
			break;
		case NORTHEAST:
			if (chunkOdds.flipCoin())
				drawDoor(chunk, w1, w2, w3, y1, y2, z1, z1, z1, Door.NORTHBYNORTHEAST, wallMaterial); 
			if (chunkOdds.flipCoin())
				drawDoor(chunk, x1, x1, x1, y1, y2, w1, w2, w3, Door.WESTBYSOUTHWEST, wallMaterial); 
			break;
		case EAST:
			if (!heights.toEast() && chunkOdds.flipCoin())
				drawDoor(chunk, x2, x2, x2, 
						y1, y2, 
						center - 1, center, center + 1, 
						Door.EASTBYSOUTHEAST, wallMaterial);
			break;
		case SOUTHEAST:
			if (chunkOdds.flipCoin())
				drawDoor(chunk, 0, 1, 2, y1, y2, z1, z1, z1, Door.NORTHBYNORTHWEST, wallMaterial); 
			if (chunkOdds.flipCoin())
				drawDoor(chunk, x2, x2, x2, y1, y2, w1, w2, w3, Door.EASTBYSOUTHEAST, wallMaterial); 
			break;
		case SOUTH:
			if (!heights.toSouth() && chunkOdds.flipCoin())
				drawDoor(chunk, center - 1, center, center + 1, 
						y1, y2, 
						z2, z2, z2, 
						Door.SOUTHBYSOUTHWEST, wallMaterial);
			break;
		case SOUTHWEST:
			if (chunkOdds.flipCoin())
				drawDoor(chunk, x2, x2, x2, y1, y2, 0, 1, 2, Door.EASTBYNORTHEAST, wallMaterial); 
			if (chunkOdds.flipCoin())
				drawDoor(chunk, 0, 1, 2, y1, y2, z2, z2, z2, Door.SOUTHBYSOUTHWEST, wallMaterial); 
			break;
		case WEST:
			if (!heights.toWest() && chunkOdds.flipCoin())
				drawDoor(chunk, x1, x1, x1, 
						y1, y2, 
						center - 1, center, center + 1, 
						Door.WESTBYNORTHWEST, wallMaterial);
			break;
		case CENTER:
		case NONE:
			if (!heights.toWest() && chunkOdds.flipCoin())
				drawDoor(chunk, x1, x1, x1, 
						y1, y2, 
						center - 1, center, center + 1, 
						Door.WESTBYNORTHWEST, wallMaterial);
			if (!heights.toEast() && chunkOdds.flipCoin())
				drawDoor(chunk, x2, x2, x2, 
						y1, y2, 
						center - 1, center, center + 1, 
						Door.EASTBYSOUTHEAST, wallMaterial);
			if (!heights.toNorth() && chunkOdds.flipCoin())
				drawDoor(chunk, center - 1, center, center + 1, 
						y1, y2, 
						z1, z1, z1, 
						Door.NORTHBYNORTHEAST, wallMaterial);
			if (!heights.toSouth() && chunkOdds.flipCoin())
				drawDoor(chunk, center - 1, center, center + 1, 
						y1, y2, 
						z2, z2, z2, 
						Door.SOUTHBYSOUTHWEST, wallMaterial);
			break;
		}
	}

	static class StairAt {
		public int X = 0;
		public int Z = 0;
		
		private static final int stairWidth = 4;
		private static final int centerX = 8;
		private static final int centerZ = 8;
		
		public StairAt(RealChunk chunk, int stairLength, int insetNS, int insetWE, StairWell where) {
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
	
	protected void drawStairs(RealChunk chunk, int y1, int floorHeight, 
			int insetNS, int insetWE, StairWell where, 
			Material stairMaterial, Material platformMaterial) {
		StairAt at = new StairAt(chunk, floorHeight, insetNS, insetWE, where);
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
				chunk.setBlock(at.X + 1, y2, at.Z + i, Material.AIR);
				chunk.setBlock(at.X + 2, y2, at.Z + i, Material.AIR);
				chunk.setStair(at.X + 1, y1 + i, at.Z + i, stairMaterial, Stair.SOUTH);
				chunk.setStair(at.X + 2, y1 + i, at.Z + i, stairMaterial, Stair.SOUTH);
			}
			break;
		case SOUTH:
			for (int i = 0; i < floorHeight; i++) {
				chunk.setBlock(at.X + 1, y2, at.Z + i, Material.AIR);
				chunk.setBlock(at.X + 2, y2, at.Z + i, Material.AIR);
				chunk.setStair(at.X + 1, y1 + i, at.Z + floorHeight - i - 1, stairMaterial, Stair.NORTH);
				chunk.setStair(at.X + 2, y1 + i, at.Z + floorHeight - i - 1, stairMaterial, Stair.NORTH);
			}
			break;
		case WEST:
			for (int i = 0; i < floorHeight; i++) {
				chunk.setBlock(at.X + i, y2, at.Z + 1, Material.AIR);
				chunk.setBlock(at.X + i, y2, at.Z + 2, Material.AIR);
				chunk.setStair(at.X + i, y1 + i, at.Z + 1, stairMaterial, Stair.EAST);
				chunk.setStair(at.X + i, y1 + i, at.Z + 2, stairMaterial, Stair.EAST);
			}
			break;
		case EAST:
			for (int i = 0; i < floorHeight; i++) {
				chunk.setBlock(at.X + i, y2, at.Z + 1, Material.AIR);
				chunk.setBlock(at.X + i, y2, at.Z + 2, Material.AIR);
				chunk.setStair(at.X + floorHeight - i - 1, y1 + i, at.Z + 1, stairMaterial, Stair.WEST);
				chunk.setStair(at.X + floorHeight - i - 1, y1 + i, at.Z + 2, stairMaterial, Stair.WEST);
			}
			break;
		}
	}

	protected void drawStairsWalls(RealChunk chunk, int y1, int floorHeight, 
			int insetNS, int insetWE, StairWell where, 
			Material wallMaterial, boolean isTopFloor, boolean isBottomFloor) {
		StairAt at = new StairAt(chunk, floorHeight, insetNS, insetWE, where);
		int y2 = y1 + floorHeight - 1;
		int yClear = y2 + (isTopFloor ? 0 : 1);
		switch (stairStyle) {
		case CROSSED:
			if (floorHeight == 4) {
				switch (stairDirection) {
				case NORTH:
				case SOUTH:
					chunk.setBlocks(at.X + 1, at.X + 3, y1, yClear, at.Z, at.Z + 4, Material.AIR);
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
					chunk.setBlocks(at.X, at.X + 4, y1, yClear, at.Z + 1, at.Z + 3, Material.AIR);
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
					chunk.setBlocks(at.X + 1, at.X + 3, y1, yClear, at.Z,     at.Z + 3, Material.AIR);
					chunk.setBlocks(at.X,     at.X + 1, y1, y2,     at.Z,     at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 3, at.X + 4, y1, y2,     at.Z,     at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 1, at.X + 3, y1, y2,     at.Z + 3, at.Z + 4, wallMaterial);
					if (isTopFloor) {
						chunk.setTrapDoor(at.X + 1, y1 - 1, at.Z, TrapDoor.TOP_NORTH);
						chunk.setBlocks(at.X + 1, y1, y2, at.Z, wallMaterial);
					}
					break;
				case SOUTH:
					chunk.setBlocks(at.X + 1, at.X + 3, y1, yClear, at.Z + 1, at.Z + 4, Material.AIR);
					chunk.setBlocks(at.X,     at.X + 1, y1, y2, 	at.Z,     at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 3, at.X + 4, y1, y2, 	at.Z,     at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, 	at.Z,     at.Z + 1, wallMaterial);
					if (isTopFloor) {
						chunk.setTrapDoor(at.X + 2, y1 - 1, at.Z + 3, TrapDoor.TOP_SOUTH);
						chunk.setBlocks(at.X + 2, y1, y2, at.Z + 3, wallMaterial);
					}
					break;
				case WEST:
					chunk.setBlocks(at.X,     at.X + 3, y1, yClear, at.Z + 1, at.Z + 3, Material.AIR);
					chunk.setBlocks(at.X,     at.X + 4, y1, y2, 	at.Z,     at.Z + 1, wallMaterial);
					chunk.setBlocks(at.X,     at.X + 4, y1, y2, 	at.Z + 3, at.Z + 4, wallMaterial);
					chunk.setBlocks(at.X + 3, at.X + 4, y1, y2, 	at.Z + 1, at.Z + 3, wallMaterial);
					if (isTopFloor) {
						chunk.setTrapDoor(at.X, y1 - 1, at.Z + 2, TrapDoor.TOP_WEST);
						chunk.setBlocks(at.X, y1, y2, at.Z + 2, wallMaterial);
					}
					break;
				case EAST:
					chunk.setBlocks(at.X + 1, at.X + 4, y1, yClear, at.Z + 1, at.Z + 3, Material.AIR);
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
					chunk.setBlocks(at.X + 1, at.X + 4, y1, yClear, at.Z + 1, at.Z + 2, Material.AIR);
					chunk.setBlocks(at.X + 1, at.X + 2, y1, yClear, at.Z + 2, at.Z + 4, Material.AIR);
					if (isTopFloor) {
						chunk.setTrapDoor(at.X + 3, y1 - 1, at.Z + 1, TrapDoor.TOP_EAST);
						chunk.setBlocks(at.X + 3, y1, y2, at.Z + 1, wallMaterial);
					}
					break;
				case SOUTH:
					chunk.setBlocks(at.X,     at.X + 3, y1, yClear, at.Z + 2, at.Z + 3, Material.AIR);
					chunk.setBlocks(at.X + 2, at.X + 3, y1, yClear, at.Z,     at.Z + 2, Material.AIR);
					if (isTopFloor) {
						chunk.setTrapDoor(at.X, y1 - 1, at.Z + 2, TrapDoor.TOP_WEST);
						chunk.setBlocks(at.X, y1, y2, at.Z + 2, wallMaterial);
					}
					break;
				case WEST:
					chunk.setBlocks(at.X + 1, at.X + 2, y1, yClear, at.Z,     at.Z + 3, Material.AIR);
					chunk.setBlocks(at.X + 2, at.X + 4, y1, yClear, at.Z + 2, at.Z + 3, Material.AIR);
					if (isTopFloor) {
						chunk.setTrapDoor(at.X + 1, y1 - 1, at.Z, TrapDoor.TOP_NORTH);
						chunk.setBlocks(at.X + 1, y1, y2, at.Z, wallMaterial);
					}
					break;
				case EAST:
					chunk.setBlocks(at.X,     at.X + 3, y1, yClear, at.Z + 1, at.Z + 2, Material.AIR);
					chunk.setBlocks(at.X + 2, at.X + 3, y1, yClear, at.Z + 2, at.Z + 4, Material.AIR);
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
			chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, at.Z, at.Z + 1, Material.AIR);
			chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, at.Z + floorHeight - 1, at.Z + floorHeight, Material.AIR);
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
			chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, at.Z, at.Z + 1, Material.AIR);
			chunk.setBlocks(at.X + 1, at.X + 3, y1, y2, at.Z + floorHeight - 1, at.Z + floorHeight, Material.AIR);
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
			chunk.setBlocks(at.X, at.X + 1, y1, y2, at.Z + 1, at.Z + 3, Material.AIR);
			chunk.setBlocks(at.X + floorHeight - 1, at.X + floorHeight, y1, y2, at.Z + 1, at.Z + 3, Material.AIR);
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
			chunk.setBlocks(at.X, at.X + 1, y1, y2, at.Z + 1, at.Z + 3, Material.AIR);
			chunk.setBlocks(at.X + floorHeight - 1, at.X + floorHeight, y1, y2, at.Z + 1, at.Z + 3, Material.AIR);
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
		drawExteriorWalls(chunk, context, y1, fenceHeight, inset, inset, false,
				fenceMaterial, fenceMaterial, neighbors);
		
		// holes in fence
		int i = 4 + chunkOdds.getRandomInt(chunk.width / 2);
		int y2 = y1 + fenceHeight;
		if (chunkOdds.flipCoin() && !neighbors.toWest())
			chunk.setBlocks(inset, y1, y2, i, airId);
		if (chunkOdds.flipCoin() && !neighbors.toEast())
			chunk.setBlocks(chunk.width - 1 - inset, y1, y2, i, airId);
		if (chunkOdds.flipCoin() && !neighbors.toNorth())
			chunk.setBlocks(i, y1, y2, inset, airId);
		if (chunkOdds.flipCoin() && !neighbors.toSouth())
			chunk.setBlocks(i, y1, y2, chunk.width - 1 - inset, airId);
	}
}
