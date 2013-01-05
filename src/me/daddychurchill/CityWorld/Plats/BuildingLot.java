package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.CurvedWallFactory;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.Direction.StairWell;
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
	
	public enum RoofStyle {FLATTOP, EDGED, PEAK, TENTNS, TENTEW};
	public enum RoofFeature {ANTENNAS, CONDITIONERS, TILE};
	protected RoofStyle roofStyle;
	protected RoofFeature roofFeature;
	protected int roofScale;
	
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
		wallsWE = new OutsideWEWallFactory(chunkOdds, platmap.generator.settings.includeDecayedBuildings);
		wallsNS = new OutsideNSWallFactory(wallsWE);
		wallsCurved = new CurvedWallFactory(wallsWE);
		wallsInterior = new InteriorWallFactory(chunkOdds, platmap.generator.settings.includeDecayedBuildings);
	}

	@Override
	protected boolean isShaftableLevel(WorldGenerator generator, int y) {
		return y >= 0 && y < generator.streetLevel - basementFloorHeight * depth - 2 - 16;	
	}

	static public RoofStyle pickRoofStyle(Odds odds) {
		switch (odds.getRandomInt(5)) {
		case 1:
			return RoofStyle.EDGED;
		case 2:
			return RoofStyle.PEAK;
		case 3:
			return RoofStyle.TENTNS;
		case 4:
			return RoofStyle.TENTEW;
		default:
			return RoofStyle.FLATTOP;
		}
	}
	
	static public RoofFeature pickRoofFeature(Odds odds) {
		switch (odds.getRandomInt(3)) {
		case 1:
			return RoofFeature.ANTENNAS;
		case 2:
			return RoofFeature.CONDITIONERS;
		default:
			return RoofFeature.TILE;
		}
	}
	
	static public Material pickGlassMaterial(Odds odds) {
		switch (odds.getRandomInt(2)) {
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
		
		return neighborBuildings;
	}
	
	protected void drawCeilings(ByteChunk byteChunk, DataContext context, int y1, int height, 
			int insetNS, int insetEW, boolean allowRounded, 
			Material material, Surroundings heights) {
		
		// precalculate
		byte materialId = (byte) material.getId();
		int y2 = y1 + height;
		boolean stillNeedCeiling = true;
		
		// rounded and square inset and there are exactly two neighbors?
		if (allowRounded && rounded) { // already know that... && insetNS == insetEW && heights.getNeighborCount() == 2
			int innerCorner = (byteChunk.width - insetNS * 2) + insetNS;
			if (heights.toNorth()) {
				if (heights.toEast()) {
					byteChunk.setArcNorthEast(insetNS, y1, y2, materialId, true);
					if (!heights.toNorthEast()) 
						byteChunk.setArcNorthEast(innerCorner, y1, y2, airId, true);
					stillNeedCeiling = false;
				} else if (heights.toWest()) {
					byteChunk.setArcNorthWest(insetNS, y1, y2, materialId, true);
					if (!heights.toNorthWest())
						byteChunk.setArcNorthWest(innerCorner, y1, y2, airId, true);
					stillNeedCeiling = false;
				}
			} else if (heights.toSouth()) {
				if (heights.toEast()) {
					byteChunk.setArcSouthEast(insetNS, y1, y2, materialId, true);
					if (!heights.toSouthEast())
						byteChunk.setArcSouthEast(innerCorner, y1, y2, airId, true);
					stillNeedCeiling = false;
				} else if (heights.toWest()) {
					byteChunk.setArcSouthWest(insetNS, y1, y2, materialId, true);
					if (!heights.toSouthWest())
						byteChunk.setArcSouthWest(innerCorner, y1, y2, airId, true);
					stillNeedCeiling = false;
				}
			}
		}
		
		// still need to do something?
		if (stillNeedCeiling) {

			// center part
			byteChunk.setBlocks(insetNS, byteChunk.width - insetNS, y1, y2, insetEW, byteChunk.width - insetEW, materialId);
			
		}
		
		// only if we are inset
		if (insetNS > 0 || insetEW > 0) {
			
			// cardinal bits
			if (heights.toWest())
				byteChunk.setBlocks(0, insetNS, y1, y2, insetEW, byteChunk.width - insetEW, materialId);
			if (heights.toEast())
				byteChunk.setBlocks(byteChunk.width - insetNS, byteChunk.width, y1, y2, insetEW, byteChunk.width - insetEW, materialId);
			if (heights.toNorth())
				byteChunk.setBlocks(insetNS, byteChunk.width - insetNS, y1, y2, 0, insetEW, materialId);
			if (heights.toSouth())
				byteChunk.setBlocks(insetNS, byteChunk.width - insetNS, y1, y2, byteChunk.width - insetEW, byteChunk.width, materialId);

			// corner bits
			if (heights.toNorthWest())
				byteChunk.setBlocks(0, insetNS, y1, y2, 0, insetEW, materialId);
			if (heights.toSouthWest())
				byteChunk.setBlocks(0, insetNS, y1, y2, byteChunk.width - insetEW, byteChunk.width, materialId);
			if (heights.toNorthEast())
				byteChunk.setBlocks(byteChunk.width - insetNS, byteChunk.width, y1, y2, 0, insetEW, materialId);
			if (heights.toSouthEast())
				byteChunk.setBlocks(byteChunk.width - insetNS, byteChunk.width, y1, y2, byteChunk.width - insetEW, byteChunk.width, materialId);
		}
	}
	
	protected void drawWalls(ByteChunk byteChunk, DataContext context, int y1, int height, 
			int insetNS, int insetEW, boolean allowRounded, 
			Material material, Material glass, Surroundings heights) {
		drawWalls(byteChunk, context, y1, height, insetNS, insetEW, allowRounded,
				material, glass, null, heights);
	}
		
	protected void drawWalls(ByteChunk byteChunk, DataContext context, int y1, int height, 
			int insetNS, int insetEW, boolean allowRounded, 
			Material materialExt, Material materialGlass, Material materialInt,
			Surroundings heights) {
		
		// precalculate
		byte materialId = (byte) materialExt.getId();
		byte glassId = (byte) materialGlass.getId();
		int y2 = y1 + height;
		boolean stillNeedWalls = true;
		
		// rounded and square inset and there are exactly two neighbors?
		if (allowRounded && rounded) { 
			
			// hack the glass material if needed
			if (materialGlass == Material.THIN_GLASS)
				glassId = (byte) Material.GLASS.getId();
			
			// do the sides
			if (heights.toSouth()) {
				if (heights.toWest()) {
					byteChunk.setArcSouthWest(insetNS, y1, y2, materialId, glassId, wallsCurved);
					if (!heights.toSouthWest()) {
						byteChunk.setBlocks(insetNS, y1, y2, byteChunk.width - insetEW - 1, materialId, glassId, wallsCurved);
					}
					stillNeedWalls = false;
				} else if (heights.toEast()) {
					byteChunk.setArcSouthEast(insetNS, y1, y2, materialId, glassId, wallsCurved);
					if (!heights.toSouthEast()) {
						byteChunk.setBlocks(byteChunk.width - insetNS - 1, y1, y2, byteChunk.width - insetEW - 1, materialId, glassId, wallsCurved);
					}
					stillNeedWalls = false;
				}
			} else if (heights.toNorth()) {
				if (heights.toWest()) {
					byteChunk.setArcNorthWest(insetNS, y1, y2, materialId, glassId, wallsCurved);
					if (!heights.toNorthWest()) {
						byteChunk.setBlocks(insetNS, y1, y2, insetEW, materialId, glassId, wallsCurved);
					}
					stillNeedWalls = false;
				} else if (heights.toEast()) {
					byteChunk.setArcNorthEast(insetNS, y1, y2, materialId, glassId, wallsCurved);
					if (!heights.toNorthEast()) {
						byteChunk.setBlocks(byteChunk.width - insetNS - 1, y1, y2, insetEW, materialId, glassId, wallsCurved);
					}
					stillNeedWalls = false;
				}
			}
		}
		
		// still need to do something?
		if (stillNeedWalls) {
			
			// corner columns
			if (!heights.toNorthWest())
				byteChunk.setBlocks(insetNS, y1, y2, insetEW, materialId);
			if (!heights.toSouthWest())
				byteChunk.setBlocks(insetNS, y1, y2, byteChunk.width - insetEW - 1, materialId);
			if (!heights.toNorthEast())
				byteChunk.setBlocks(byteChunk.width - insetNS - 1, y1, y2, insetEW, materialId);
			if (!heights.toSouthEast())
				byteChunk.setBlocks(byteChunk.width - insetNS - 1, y1, y2, byteChunk.width - insetEW - 1, materialId);
			
			// cardinal walls
			if (!heights.toWest())
				byteChunk.setBlocks(insetNS,  insetNS + 1, y1, y2, insetEW + 1, byteChunk.width - insetEW - 1, materialId, glassId, wallsNS);
			if (!heights.toEast())
				byteChunk.setBlocks(byteChunk.width - insetNS - 1,  byteChunk.width - insetNS, y1, y2, insetEW + 1, byteChunk.width - insetEW - 1, materialId, glassId, wallsNS);
			if (!heights.toNorth())
				byteChunk.setBlocks(insetNS + 1, byteChunk.width - insetNS - 1, y1, y2, insetEW, insetEW + 1, materialId, glassId, wallsWE);
			if (!heights.toSouth())
				byteChunk.setBlocks(insetNS + 1, byteChunk.width - insetNS - 1, y1, y2, byteChunk.width - insetEW - 1, byteChunk.width - insetEW, materialId, glassId, wallsWE);
			
			//TODO inner corners are drawing walls (NW, NE, SW, SE shouldn't draw interior walls 
			//TODO random "rooms within" these rooms
			
			// interior walls
			if (materialInt != null) {// && (insetNS < 2 || insetEW < 2)) {
				byte interiorId = (byte) materialInt.getId();
//				int x1 = heights.toWest() ? 0 : insetNS + 1;
//				int x2 = byteChunk.width - (heights.toEast() ? 0 : (insetNS + 1));
//				int z1 = heights.toNorth() ? 0 : insetEW + 1;
//				int z2 = byteChunk.width - (heights.toSouth() ? 0 : (insetEW + 1));
				if (heights.toWest()) {
					byteChunk.setBlocks(0, 7, y1, y2, 6, 7, interiorId, glassId, wallsInterior);
					byteChunk.setBlocks(0, 7, y1, y2, 9, 10, interiorId, glassId, wallsInterior);
//					byteChunk.setBlocks(3, 4, y1, y2 + 4, z1, z2, Material.GOLD_BLOCK);
				}
				if (heights.toEast()) {
					byteChunk.setBlocks(9, 16, y1, y2, 6, 7, interiorId, glassId, wallsInterior);
					byteChunk.setBlocks(9, 16, y1, y2, 9, 10, interiorId, glassId, wallsInterior);
//					byteChunk.setBlocks(12, 13, y1, y2 + 4, z1, z2, Material.DIAMOND_BLOCK);
				}
				if (heights.toNorth()) {
					byteChunk.setBlocks(6, 7, y1, y2, 0, 7, interiorId, glassId, wallsInterior);
					byteChunk.setBlocks(9, 10, y1, y2, 0, 7, interiorId, glassId, wallsInterior);
//					byteChunk.setBlocks(x1, x2, y1, y2 + 4, 3, 4, Material.LAPIS_BLOCK);
				}
				if (heights.toSouth()) {
					byteChunk.setBlocks(6, 7, y1, y2, 9, 16, interiorId, glassId, wallsInterior);
					byteChunk.setBlocks(9, 10, y1, y2, 9, 16, interiorId, glassId, wallsInterior);
//					byteChunk.setBlocks(x1, x2, y1, y2 + 4, 12, 13, Material.EMERALD_BLOCK);
				}
			}
		}
			
		// only if there are insets
		if (insetNS > 0) {
			if (heights.toWest()) {
				if (!heights.toNorthWest())
					byteChunk.setBlocks(0, insetNS, y1, y2, insetEW, insetEW + 1, materialId, glassId, wallsWE);
				if (!heights.toSouthWest())
					byteChunk.setBlocks(0, insetNS, y1, y2, byteChunk.width - insetEW - 1, byteChunk.width - insetEW, materialId, glassId, wallsWE);
			}
			if (heights.toEast()) {
				if (!heights.toNorthEast())
					byteChunk.setBlocks(byteChunk.width - insetNS, byteChunk.width, y1, y2, insetEW, insetEW + 1, materialId, glassId, wallsWE);
				if (!heights.toSouthEast())
					byteChunk.setBlocks(byteChunk.width - insetNS, byteChunk.width, y1, y2, byteChunk.width - insetEW - 1, byteChunk.width - insetEW, materialId, glassId, wallsWE);
			}
		}
		if (insetEW > 0) {
			if (heights.toNorth()) {
				if (!heights.toNorthWest())
					byteChunk.setBlocks(insetNS, insetNS + 1, y1, y2, 0, insetEW, materialId, glassId, wallsNS);
				if (!heights.toNorthEast())
					byteChunk.setBlocks(byteChunk.width - insetNS - 1, byteChunk.width - insetNS, y1, y2, 0, insetEW, materialId, glassId, wallsNS);
			}
			if (heights.toSouth()) {
				if (!heights.toSouthWest())
					byteChunk.setBlocks(insetNS, insetNS + 1, y1, y2, byteChunk.width - insetEW, byteChunk.width, materialId, glassId, wallsNS);
				if (!heights.toSouthEast())
					byteChunk.setBlocks(byteChunk.width - insetNS - 1, byteChunk.width - insetNS, y1, y2, byteChunk.width - insetEW, byteChunk.width, materialId, glassId, wallsNS);
			}
		}
	}
	
	//TODO roof fixtures (peak, helipad, air conditioning, stairwells access, penthouse, castle trim, etc.
	protected void drawRoof(ByteChunk chunk, DataContext context, int y1, 
			int insetEW, int insetNS, boolean allowRounded, 
			Material material, Surroundings heights) {
		switch (roofStyle) {
		case PEAK:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < aboveFloorHeight; i++) {
					if (i == aboveFloorHeight - 1)
						drawCeilings(chunk, context, y1 + i * roofScale, roofScale, insetEW + i, insetNS + i, allowRounded, material, heights);
					else
						drawWalls(chunk, context, y1 + i * roofScale, roofScale, insetEW + i, insetNS + i, allowRounded, material, material, heights);
				}
			} else
				drawEdgedRoof(chunk, context, y1, insetEW, insetNS, allowRounded, material, true, heights);
			break;
		case TENTNS:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < aboveFloorHeight; i++) {
					if (i == aboveFloorHeight - 1)
						drawCeilings(chunk, context, y1 + i * roofScale, roofScale, insetEW + i, insetNS, allowRounded, material, heights);
					else
						drawWalls(chunk, context, y1 + i * roofScale, roofScale, insetEW + i, insetNS, allowRounded, material, material, heights);
				}
			} else
				drawEdgedRoof(chunk, context, y1, insetEW, insetNS, allowRounded, material, true, heights);
			break;
		case TENTEW:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < aboveFloorHeight; i++) {
					if (i == aboveFloorHeight - 1)
						drawCeilings(chunk, context, y1 + i * roofScale, roofScale, insetEW, insetNS + i, allowRounded, material, heights);
					else
						drawWalls(chunk, context, y1 + i * roofScale, roofScale, insetEW, insetNS + i, allowRounded, material, material, heights);
				}
			} else
				drawEdgedRoof(chunk, context, y1, insetEW, insetNS, allowRounded, material, true, heights);
			break;
		case EDGED:
			drawEdgedRoof(chunk, context, y1, insetEW, insetNS, allowRounded, material, true, heights);
			break;
		case FLATTOP:
			drawEdgedRoof(chunk, context, y1, insetEW, insetNS, allowRounded, material, false, heights);
			break;
		}
	}
	
	private void drawEdgedRoof(ByteChunk chunk, DataContext context, int y1, 
			int insetEW, int insetNS, boolean allowRounded, 
			Material material, boolean doEdge, Surroundings heights) {
		
		// a little bit of edge 
		if (doEdge)
			drawWalls(chunk, context, y1, 1, insetEW, insetNS, allowRounded, material, material, heights);
		
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
			drawCeilings(chunk, context, y1, 1, insetEW + 1, insetNS + 1, allowRounded, tileMaterial, heights);
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
			int insetNS, int insetEW, StairWell where, 
			Surroundings heights, Material wallMaterial) {
		
		int w1 = chunk.width - 1;
		int w2 = chunk.width - 2;
		int w3 = chunk.width - 3;
		int x1 = insetNS;
		int x2 = w1 - insetNS;
		int z1 = insetEW;
		int z2 = w1 - insetEW;
		int y2 = y1 + floorHeight - 1;
		
		switch (where) {
		case CENTER:
			int center = chunk.width / 2;
			
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
		case SOUTHWEST:
			if (chunkOdds.flipCoin())
				drawDoor(chunk, x2, x2, x2, y1, y2, 0, 1, 2, Door.EASTBYNORTHEAST, wallMaterial); 
			if (chunkOdds.flipCoin())
				drawDoor(chunk, 0, 1, 2, y1, y2, z2, z2, z2, Door.SOUTHBYSOUTHWEST, wallMaterial); 
			break;
		case SOUTHEAST:
			if (chunkOdds.flipCoin())
				drawDoor(chunk, 0, 1, 2, y1, y2, z1, z1, z1, Door.NORTHBYNORTHWEST, wallMaterial); 
			if (chunkOdds.flipCoin())
				drawDoor(chunk, x2, x2, x2, y1, y2, w1, w2, w3, Door.EASTBYSOUTHEAST, wallMaterial); 
			break;
		case NORTHWEST:
			if (chunkOdds.flipCoin())
				drawDoor(chunk, x1, x1, x1, y1, y2, 0, 1, 2, Door.WESTBYNORTHWEST, wallMaterial); 
			if (chunkOdds.flipCoin())
				drawDoor(chunk, w1, w2, w3, y1, y2, z2, z2, z2, Door.SOUTHBYSOUTHEAST, wallMaterial); 
			break;
		case NORTHEAST:
			if (chunkOdds.flipCoin())
				drawDoor(chunk, w1, w2, w3, y1, y2, z1, z1, z1, Door.NORTHBYNORTHEAST, wallMaterial); 
			if (chunkOdds.flipCoin())
				drawDoor(chunk, x1, x1, x1, y1, y2, w1, w2, w3, Door.WESTBYSOUTHWEST, wallMaterial); 
			break;
		}
	}

	//TODO These might go too far by one
	static class StairAt {
		public int X = 0;
		public int Z = 0;
		public StairAt(RealChunk chunk, int floorHeight, int insetNS, int insetEW, StairWell where) {
			switch (where) {
			case CENTER:
				X = (chunk.width - floorHeight) / 2;
				Z = (chunk.width - 4) / 2;
				break;
			case SOUTHWEST:
				X = insetNS;
				Z = insetEW;
				break;
			case SOUTHEAST:
				X = insetNS;
				Z = chunk.width - 4 - insetEW;
				break;
			case NORTHWEST:
				X = chunk.width - floorHeight - insetNS;
				Z = insetEW;
				break;
			case NORTHEAST:
				X = chunk.width - floorHeight - insetNS;
				Z = chunk.width - 4 - insetEW;
				break;
			}
		}
	}

	public StairWell getStairWellLocation(boolean allowRounded, Surroundings heights) {
		if (allowRounded && rounded) {
			if (heights.toWest()) {
				if (heights.toNorth()) {
					return StairWell.SOUTHWEST;
				} else if (heights.toSouth()) {
					return StairWell.SOUTHEAST;
				}
			} else if (heights.toEast()) {
				if (heights.toNorth()) {
					return StairWell.NORTHWEST;
				} else if (heights.toSouth()) {
					return StairWell.NORTHEAST;
				}
			}
		}
		return StairWell.CENTER;
	}
	
	protected void drawStairs(RealChunk chunk, int y, int floorHeight, 
			int insetNS, int insetEW, StairWell where, Material stairMaterial) {
		StairAt at = new StairAt(chunk, floorHeight, insetNS, insetEW, where);
		for (int i = 0; i < floorHeight; i++) {
			chunk.setBlock(at.X + i, y + floorHeight - 1, at.Z + 1, Material.AIR);
			chunk.setBlock(at.X + i, y + floorHeight - 1, at.Z + 2, Material.AIR);
			chunk.setBlock(at.X + i, y + i, at.Z + 1, stairMaterial);
			chunk.setBlock(at.X + i, y + i, at.Z + 2, stairMaterial);
		}
	}

	protected void drawStairsWalls(RealChunk chunk, int y, int floorHeight, 
			int insetNS, int insetEW, StairWell where, 
			Material wallMaterial, boolean drawStartcap, boolean drawEndcap) {
		StairAt at = new StairAt(chunk, floorHeight, insetNS, insetEW, where);
		chunk.setBlocks(at.X, at.X + floorHeight, y, y + floorHeight - 1, at.Z, at.Z + 1, wallMaterial);
		chunk.setBlocks(at.X, at.X + floorHeight, y, y + floorHeight - 1, at.Z + 3, at.Z + 4, wallMaterial);
		if (drawStartcap)
			chunk.setBlocks(at.X - 1, at.X, y, y + floorHeight - 1, at.Z, at.Z + 4, wallMaterial);
		if (drawEndcap)
			chunk.setBlocks(at.X + floorHeight, at.X + floorHeight + 1, y, y + floorHeight - 1, at.Z, at.Z + 4, wallMaterial);
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
		drawWalls(chunk, context, y1, fenceHeight, inset, inset, false,
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
