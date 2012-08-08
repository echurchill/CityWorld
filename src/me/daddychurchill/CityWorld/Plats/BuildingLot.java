package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.Direction.StairWell;
import me.daddychurchill.CityWorld.Support.MaterialFactory;
import me.daddychurchill.CityWorld.Support.OutsideWEWallFactory;
import me.daddychurchill.CityWorld.Support.OutsideNSWallFactory;
import me.daddychurchill.CityWorld.Support.InteriorWallFactory;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SurroundingFloors;
import me.daddychurchill.CityWorld.Support.Direction.Door;

import org.bukkit.Material;

public abstract class BuildingLot extends ConnectedLot {
	
	protected boolean neighborsHaveIdenticalHeights;
	protected int neighborsHaveSimilarHeightsOdds;
	protected int neighborsHaveSimilarRoundedOdds;
	protected int height; // floors up
	protected int depth; // floors down
	protected boolean needStairsUp;
	protected boolean needStairsDown;
	protected boolean rounded; // rounded corners if possible? (only if the insets match)
	protected MaterialFactory windowsWE;
	protected MaterialFactory windowsNS;
	protected MaterialFactory interiorWalls;
	protected final static byte airId = (byte) Material.AIR.getId();
	protected final static byte antennaBaseId = (byte) Material.CLAY.getId();
	protected final static byte antennaId = (byte) Material.FENCE.getId();
	protected final static byte conditionerId = (byte) Material.DOUBLE_STEP.getId();
	protected final static byte conditionerTrimId = (byte) Material.STONE_PLATE.getId();
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
	
	public BuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		style = LotStyle.STRUCTURE;
		
		DataContext context = platmap.context;
		
		neighborsHaveIdenticalHeights = chunkRandom.nextInt(context.oddsOfIdenticalBuildingHeights) == 0;
		neighborsHaveSimilarHeightsOdds = context.oddsOfSimilarBuildingHeights;
		neighborsHaveSimilarRoundedOdds = context.oddsOfSimilarBuildingRounding;
		height = chunkRandom.nextInt(context.maximumFloorsAbove) + 1;
		if (platmap.generator.settings.includeBasements)
			depth = chunkRandom.nextInt(context.maximumFloorsBelow) + 1;
		needStairsDown = true;
		needStairsUp = true;
		rounded = chunkRandom.nextInt(context.oddsOfSimilarBuildingRounding) == 0;
		roofStyle = pickRoofStyle(chunkRandom);
		roofFeature = pickRoofFeature(chunkRandom);
		roofScale = chunkRandom.nextInt(2) + 1;
		windowsWE = new OutsideWEWallFactory(chunkRandom, platmap.generator.settings.includeDecayedBuildings);
		windowsNS = new OutsideNSWallFactory(windowsWE);
		interiorWalls = new InteriorWallFactory(chunkRandom, platmap.generator.settings.includeDecayedBuildings);
	}

	@Override
	protected boolean isShaftableLevel(WorldGenerator generator, DataContext context, int y) {
		return y >= 0 && y < generator.sidewalkLevel - DataContext.FloorHeight * depth - 2 - 16;	
	}

	static public RoofStyle pickRoofStyle(Random rand) {
		switch (rand.nextInt(5)) {
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
	
	static public RoofFeature pickRoofFeature(Random rand) {
		switch (rand.nextInt(3)) {
		case 1:
			return RoofFeature.ANTENNAS;
		case 2:
			return RoofFeature.CONDITIONERS;
		default:
			return RoofFeature.TILE;
		}
	}
	
	static public Material pickGlassMaterial(Random rand) {
		switch (rand.nextInt(2)) {
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
			if (neighborsHaveIdenticalHeights || chunkRandom.nextInt(neighborsHaveSimilarHeightsOdds) != 0) {
				height = relativebuilding.height;
				depth = relativebuilding.depth;
			}
			
			if (chunkRandom.nextInt(neighborsHaveSimilarRoundedOdds) == 0)
				rounded = relativebuilding.rounded;
			
			// any other bits
			roofStyle = relativebuilding.roofStyle;
			roofFeature = relativebuilding.roofFeature;
			roofScale = relativebuilding.roofScale;
			windowsWE = relativebuilding.windowsWE;
			windowsNS = relativebuilding.windowsNS;
			interiorWalls = relativebuilding.interiorWalls;
			
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
			Material material, SurroundingFloors heights) {
		
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
			Material material, Material glass, SurroundingFloors heights) {
		
		// precalculate
		byte materialId = (byte) material.getId();
		byte glassId = (byte) glass.getId();
		int y2 = y1 + height;
		boolean stillNeedWalls = true;
		
		// rounded and square inset and there are exactly two neighbors?
		if (allowRounded && rounded) { 
			
			// hack the glass material if needed
			if (glass == Material.THIN_GLASS)
				glassId = (byte) Material.GLASS.getId();
			
			// raised?
			byte primaryId = materialId;
			byte secondaryId = glassId;
			switch (windowsNS.style) {
			case RANDOM:
			case SINGLE:
			case DOUBLE:
				primaryId = secondaryId;
				break;
			}
			
			// do the sides
			if (heights.toSouth()) {
				if (heights.toWest()) {
					byteChunk.setArcSouthWest(insetNS, y1, y2, primaryId, secondaryId, false);
					if (!heights.toSouthWest()) {
						byteChunk.setBlock(insetNS, y1, byteChunk.width - insetEW - 1, primaryId);
						byteChunk.setBlocks(insetNS, y1 + 1, y2, byteChunk.width - insetEW - 1, secondaryId);
					}
					stillNeedWalls = false;
				} else if (heights.toEast()) {
					byteChunk.setArcSouthEast(insetNS, y1, y2, primaryId, secondaryId, false);
					if (!heights.toSouthEast()) {
						byteChunk.setBlock(byteChunk.width - insetNS - 1, y1, byteChunk.width - insetEW - 1, primaryId);
						byteChunk.setBlocks(byteChunk.width - insetNS - 1, y1 + 1, y2, byteChunk.width - insetEW - 1, secondaryId);
					}
					stillNeedWalls = false;
				}
			} else if (heights.toNorth()) {
				if (heights.toWest()) {
					byteChunk.setArcNorthWest(insetNS, y1, y2, primaryId, secondaryId, false);
					if (!heights.toNorthWest()) {
						byteChunk.setBlock(insetNS, y1, insetEW, primaryId);
						byteChunk.setBlocks(insetNS, y1 + 1, y2, insetEW, secondaryId);
					}
					stillNeedWalls = false;
				} else if (heights.toEast()) {
					byteChunk.setArcNorthEast(insetNS, y1, y2, primaryId, secondaryId, false);
					if (!heights.toNorthEast()) {
						byteChunk.setBlock(byteChunk.width - insetNS - 1, y1, insetEW, primaryId);
						byteChunk.setBlocks(byteChunk.width - insetNS - 1, y1 + 1, y2, insetEW, secondaryId);
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
				byteChunk.setBlocks(insetNS,  insetNS + 1, y1, y2, insetEW + 1, byteChunk.width - insetEW - 1, materialId, glassId, windowsNS);
			if (!heights.toEast())
				byteChunk.setBlocks(byteChunk.width - insetNS - 1,  byteChunk.width - insetNS, y1, y2, insetEW + 1, byteChunk.width - insetEW - 1, materialId, glassId, windowsNS);
			if (!heights.toNorth())
				byteChunk.setBlocks(insetNS + 1, byteChunk.width - insetNS - 1, y1, y2, insetEW, insetEW + 1, materialId, glassId, windowsWE);
			if (!heights.toSouth())
				byteChunk.setBlocks(insetNS + 1, byteChunk.width - insetNS - 1, y1, y2, byteChunk.width - insetEW - 1, byteChunk.width - insetEW, materialId, glassId, windowsWE);
			
//			// interior walls
//			if (!heights.toWest()) {
//				byteChunk.setBlocks(insetNS,  insetNS + 1, y1, y2, insetEW + 1, byteChunk.width - insetEW - 1, materialId, glassId, windowsNS);
//			}
//			if (!heights.toEast()) {
//				byteChunk.setBlocks(byteChunk.width - insetNS - 1,  byteChunk.width - insetNS, y1, y2, insetEW + 1, byteChunk.width - insetEW - 1, materialId, glassId, windowsNS);
//			}
//			if (!heights.toNorth()) {
//				byteChunk.setBlocks(insetNS + 1, byteChunk.width - insetNS - 1, y1, y2, insetEW, insetEW + 1, materialId, glassId, windowsEW);
//			}
//			if (!heights.toSouth()) {
//				byteChunk.setBlocks(insetNS + 1, byteChunk.width - insetNS - 1, y1, y2, byteChunk.width - insetEW - 1, byteChunk.width - insetEW, materialId, glassId, windowsEW);
//			}
		}
			
		// only if there are insets
		if (insetNS > 0) {
			if (heights.toWest()) {
				if (!heights.toNorthWest())
					byteChunk.setBlocks(0, insetNS, y1, y2, insetEW, insetEW + 1, materialId, glassId, windowsNS);
				if (!heights.toSouthWest())
					byteChunk.setBlocks(0, insetNS, y1, y2, byteChunk.width - insetEW - 1, byteChunk.width - insetEW, materialId, glassId, windowsNS);
			}
			if (heights.toEast()) {
				if (!heights.toNorthEast())
					byteChunk.setBlocks(byteChunk.width - insetNS, byteChunk.width, y1, y2, insetEW, insetEW + 1, materialId, glassId, windowsNS);
				if (!heights.toSouthEast())
					byteChunk.setBlocks(byteChunk.width - insetNS, byteChunk.width, y1, y2, byteChunk.width - insetEW - 1, byteChunk.width - insetEW, materialId, glassId, windowsNS);
			}
		}
		if (insetEW > 0) {
			if (heights.toNorth()) {
				if (!heights.toNorthWest())
					byteChunk.setBlocks(insetNS, insetNS + 1, y1, y2, 0, insetEW, materialId, glassId, windowsWE);
				if (!heights.toNorthEast())
					byteChunk.setBlocks(byteChunk.width - insetNS - 1, byteChunk.width - insetNS, y1, y2, 0, insetEW, materialId, glassId, windowsWE);
			}
			if (heights.toSouth()) {
				if (!heights.toSouthWest())
					byteChunk.setBlocks(insetNS, insetNS + 1, y1, y2, byteChunk.width - insetEW, byteChunk.width, materialId, glassId, windowsWE);
				if (!heights.toSouthEast())
					byteChunk.setBlocks(byteChunk.width - insetNS - 1, byteChunk.width - insetNS, y1, y2, byteChunk.width - insetEW, byteChunk.width, materialId, glassId, windowsWE);
			}
		}
	}
	
	//TODO roof fixtures (peak, helipad, air conditioning, stairwells access, penthouse, castle trim, etc.
	protected void drawRoof(ByteChunk chunk, DataContext context, int y1, 
			int insetEW, int insetNS, boolean allowRounded, 
			Material material, SurroundingFloors heights) {
		switch (roofStyle) {
		case PEAK:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < DataContext.FloorHeight; i++) {
					if (i == DataContext.FloorHeight - 1)
						drawCeilings(chunk, context, y1 + i * roofScale, roofScale, insetEW + i, insetNS + i, allowRounded, material, heights);
					else
						drawWalls(chunk, context, y1 + i * roofScale, roofScale, insetEW + i, insetNS + i, allowRounded, material, material, heights);
				}
			} else
				drawEdgedRoof(chunk, context, y1, insetEW, insetNS, allowRounded, material, true, heights);
			break;
		case TENTNS:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < DataContext.FloorHeight; i++) {
					if (i == DataContext.FloorHeight - 1)
						drawCeilings(chunk, context, y1 + i * roofScale, roofScale, insetEW + i, insetNS, allowRounded, material, heights);
					else
						drawWalls(chunk, context, y1 + i * roofScale, roofScale, insetEW + i, insetNS, allowRounded, material, material, heights);
				}
			} else
				drawEdgedRoof(chunk, context, y1, insetEW, insetNS, allowRounded, material, true, heights);
			break;
		case TENTEW:
			if (heights.getNeighborCount() == 0) { 
				for (int i = 0; i < DataContext.FloorHeight; i++) {
					if (i == DataContext.FloorHeight - 1)
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
			Material material, boolean doEdge, SurroundingFloors heights) {
		
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
			} // else go for the conditioners
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
		
		if (chunkRandom.nextBoolean()) {
			int y2 = y + chunkRandom.nextInt(8) + 8;
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
		
		if (chunkRandom.nextBoolean()) {
			chunk.setBlock(x, y, z, conditionerId);
			chunk.setBlock(x, y + 1, z, conditionerTrimId);
			if (chunkRandom.nextBoolean()) {
				chunk.setBlockIfAir(x - 1, y, z, ductId);
				chunk.setBlockIfAir(x - 2, y, z, ductId);
			}
			if (chunkRandom.nextBoolean()) {
				chunk.setBlockIfAir(x + 1, y, z, ductId);
				chunk.setBlockIfAir(x + 2, y, z, ductId);
			}
			if (chunkRandom.nextBoolean()) {
				chunk.setBlockIfAir(x, y, z - 1, ductId);
				chunk.setBlockIfAir(x, y, z - 2, ductId);
			}
			if (chunkRandom.nextBoolean()) {
				chunk.setBlockIfAir(x, y, z + 1, ductId);
				chunk.setBlockIfAir(x, y, z + 2, ductId);
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
			SurroundingFloors heights, Material wallMaterial) {
		
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
			
			if (!heights.toWest() && chunkRandom.nextBoolean())
				drawDoor(chunk, x1, x1, x1, 
						y1, y2, 
						center - 1, center, center + 1, 
						Door.WESTBYNORTHWEST, wallMaterial);
			if (!heights.toEast() && chunkRandom.nextBoolean())
				drawDoor(chunk, x2, x2, x2, 
						y1, y2, 
						center - 1, center, center + 1, 
						Door.EASTBYSOUTHEAST, wallMaterial);
			if (!heights.toNorth() && chunkRandom.nextBoolean())
				drawDoor(chunk, center - 1, center, center + 1, 
						y1, y2, 
						z1, z1, z1, 
						Door.NORTHBYNORTHEAST, wallMaterial);
			if (!heights.toSouth() && chunkRandom.nextBoolean())
				drawDoor(chunk, center - 1, center, center + 1, 
						y1, y2, 
						z2, z2, z2, 
						Door.SOUTHBYSOUTHWEST, wallMaterial);
			break;
		case SOUTHWEST:
			if (chunkRandom.nextBoolean())
				drawDoor(chunk, x2, x2, x2, y1, y2, 0, 1, 2, Door.EASTBYNORTHEAST, wallMaterial); 
			if (chunkRandom.nextBoolean())
				drawDoor(chunk, 0, 1, 2, y1, y2, z2, z2, z2, Door.SOUTHBYSOUTHWEST, wallMaterial); 
			break;
		case SOUTHEAST:
			if (chunkRandom.nextBoolean())
				drawDoor(chunk, 0, 1, 2, y1, y2, z1, z1, z1, Door.NORTHBYNORTHWEST, wallMaterial); 
			if (chunkRandom.nextBoolean())
				drawDoor(chunk, x2, x2, x2, y1, y2, w1, w2, w3, Door.EASTBYSOUTHEAST, wallMaterial); 
			break;
		case NORTHWEST:
			if (chunkRandom.nextBoolean())
				drawDoor(chunk, x1, x1, x1, y1, y2, 0, 1, 2, Door.WESTBYNORTHWEST, wallMaterial); 
			if (chunkRandom.nextBoolean())
				drawDoor(chunk, w1, w2, w3, y1, y2, z2, z2, z2, Door.SOUTHBYSOUTHEAST, wallMaterial); 
			break;
		case NORTHEAST:
			if (chunkRandom.nextBoolean())
				drawDoor(chunk, w1, w2, w3, y1, y2, z1, z1, z1, Door.NORTHBYNORTHEAST, wallMaterial); 
			if (chunkRandom.nextBoolean())
				drawDoor(chunk, x1, x1, x1, y1, y2, w1, w2, w3, Door.WESTBYSOUTHWEST, wallMaterial); 
			break;
		}
	}

	protected boolean stairsHere(SurroundingFloors neighbors, double throwOfDice) {
		return (throwOfDice < 1.0 - ((double) neighbors.getNeighborCount() / 4.0));
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

	public StairWell getStairWellLocation(boolean allowRounded, SurroundingFloors heights) {
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
}
