package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMaps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.Direction;
import me.daddychurchill.CityWorld.Support.Direction.StairWell;
import me.daddychurchill.CityWorld.Support.MaterialFactory;
import me.daddychurchill.CityWorld.Support.GlassFactoryX;
import me.daddychurchill.CityWorld.Support.GlassFactoryZ;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SurroundingFloors;
import me.daddychurchill.CityWorld.Support.Direction.Door;

import org.bukkit.Material;

public abstract class PlatBuilding extends PlatLot {
	
	//TODO remove this once rounding buildings really works
	public static final boolean enableRounding = true;
	
	protected boolean neighborsHaveIdenticalHeights;
	protected int neighborsHaveSimilarHeightsOdds;
	protected int neighborsHaveSimilarRoundedOdds;
	protected int height; // floors up
	protected int depth; // floors down
	protected boolean needStairsUp;
	protected boolean needStairsDown;
	protected boolean rounded; // rounded corners if possible? (only if the insets match)
	protected MaterialFactory windowsX;
	protected MaterialFactory windowsZ;
	protected final static byte airId = (byte) Material.AIR.getId();
	protected final static byte ironId = (byte) Material.IRON_BLOCK.getId();
	
	public PlatBuilding(Random rand, int maxHeight, int maxDepth, 
			int overallIdenticalHeightsOdds, int overallSimilarHeightsOdds, 
			int overallSimilarRoundedOdds) {
		super(rand);
		
		neighborsHaveIdenticalHeights = rand.nextInt(overallIdenticalHeightsOdds) == 0;
		neighborsHaveSimilarHeightsOdds = overallIdenticalHeightsOdds;
		neighborsHaveSimilarRoundedOdds = overallSimilarRoundedOdds;
		height = rand.nextInt(maxHeight) + 1;
		depth = rand.nextInt(maxDepth) + 1;
		needStairsDown = true;
		needStairsUp = true;
		rounded = rand.nextInt(overallSimilarRoundedOdds) == 0;
		windowsX = new GlassFactoryX(rand);
		windowsZ = new GlassFactoryZ(rand, windowsX.style);
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
	public void makeConnected(Random rand, PlatLot relative) {
		//log.info("PlatBuilding's makeConnected");
		super.makeConnected(rand, relative);
		
		// other bits
		if (relative instanceof PlatBuilding) {
			PlatBuilding relativebuilding = (PlatBuilding) relative;

			neighborsHaveIdenticalHeights = relativebuilding.neighborsHaveIdenticalHeights;
			if (neighborsHaveIdenticalHeights || rand.nextInt(neighborsHaveSimilarHeightsOdds) != 0) {
				height = relativebuilding.height;
				depth = relativebuilding.depth;
			}
			
			if (rand.nextInt(neighborsHaveSimilarRoundedOdds) == 0)
				rounded = relativebuilding.rounded;
			
			// any other bits
			windowsX = relativebuilding.windowsX;
			windowsZ = relativebuilding.windowsZ;
			
			// do we need stairs?
			relativebuilding.needStairsDown = relativebuilding.depth > depth;
			relativebuilding.needStairsUp = relativebuilding.height > height;
		}
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
					neighborBuildings.floors[x][z] = ((PlatBuilding) neighborChunks[x][z]).height;
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
					neighborBuildings.floors[x][z] = ((PlatBuilding) neighborChunks[x][z]).depth;
				}
			}
		}
		
		return neighborBuildings;
	}
	
	protected void drawCeilings(ByteChunk byteChunk, int y1, int height, 
			int insetNS, int insetEW, boolean allowRounded, 
			Material material, SurroundingFloors heights) {
		
		// precalculate
		byte materialId = (byte) material.getId();
		int y2 = y1 + height;
		boolean stillNeedCeiling = true;
		
		// rounded and square inset and there are exactly two neighbors?
		if (allowRounded && rounded) { // && insetNS == insetEW && heights.getNeighborCount() == 2
			int innerCorner = (ByteChunk.Width - insetNS * 2) + insetNS; // iiWwwwwwWii
			if (heights.toSouth()) {
				if (heights.toWest()) {
					byteChunk.setArcNorthEast(insetNS, y1, y2, materialId, true);
					if (!heights.toSouthWest())
						byteChunk.setArcNorthEast(innerCorner, y1, y2, airId, true);
					stillNeedCeiling = false;
				} else if (heights.toEast()) {
					byteChunk.setArcNorthWest(insetNS, y1, y2, materialId, true);
					if (!heights.toSouthEast())
						byteChunk.setArcNorthWest(innerCorner, y1, y2, airId, true);
					stillNeedCeiling = false;
				}
			} else if (heights.toNorth()) {
				if (heights.toWest()) {
					byteChunk.setArcSouthEast(insetNS, y1, y2, materialId, true);
					if (!heights.toNorthWest())
						byteChunk.setArcSouthEast(innerCorner, y1, y2, airId, true);
					stillNeedCeiling = false;
				} else if (heights.toEast()) {
					byteChunk.setArcSouthWest(insetNS, y1, y2, materialId, true);
					if (!heights.toNorthEast())
						byteChunk.setArcSouthWest(innerCorner, y1, y2, airId, true);
					stillNeedCeiling = false;
				}
			}
		}
		
		// still need to do something?
		if (stillNeedCeiling) {

			// center part
			byteChunk.setBlocks(insetNS, ByteChunk.Width - insetNS, y1, y2, insetEW, ByteChunk.Width - insetEW, materialId);
			
			// only if we are inset
			if (insetNS > 0 || insetEW > 0) {
				
				// cardinal bits
				if (heights.toSouth())
					byteChunk.setBlocks(0, insetNS, y1, y2, insetEW, ByteChunk.Width - insetEW, materialId);
				if (heights.toNorth())
					byteChunk.setBlocks(ByteChunk.Width - insetNS, ByteChunk.Width, y1, y2, insetEW, ByteChunk.Width - insetEW, materialId);
				if (heights.toWest())
					byteChunk.setBlocks(insetNS, ByteChunk.Width - insetNS, y1, y2, 0, insetEW, materialId);
				if (heights.toEast())
					byteChunk.setBlocks(insetNS, ByteChunk.Width - insetNS, y1, y2, ByteChunk.Width - insetEW, ByteChunk.Width, materialId);
				
				// corner bits
				if (heights.toSouthWest())
					byteChunk.setBlocks(0, insetNS, y1, y2, 0, insetEW, materialId);
				if (heights.toSouthEast())
					byteChunk.setBlocks(0, insetNS, y1, y2, ByteChunk.Width - insetEW, ByteChunk.Width, materialId);
				if (heights.toNorthWest())
					byteChunk.setBlocks(ByteChunk.Width - insetNS, ByteChunk.Width, y1, y2, 0, insetEW, materialId);
				if (heights.toNorthEast())
					byteChunk.setBlocks(ByteChunk.Width - insetNS, ByteChunk.Width, y1, y2, ByteChunk.Width - insetEW, ByteChunk.Width, materialId);
			}
		}
	}
	
	protected void drawWalls(ByteChunk byteChunk, int y1, int height, 
			int insetNS, int insetEW, boolean allowRounded, 
			Material material, Material glass, SurroundingFloors heights) {
		
		// precalculate
		byte materialId = (byte) material.getId();
		byte glassId = (byte) glass.getId();
		int y2 = y1 + height;
		boolean stillNeedWalls = true;
		
		// rounded and square inset and there are exactly two neighbors?
		if (allowRounded && rounded) { //TODO && insetNS == insetEW && heights.getNeighborCount() == 2
			glassId = (byte) Material.GLASS.getId();
			if (heights.toSouth()) {
				if (heights.toWest()) {
					byteChunk.setArcNorthEast(insetNS, y1, y2, glassId, false);
					stillNeedWalls = false;
				} else if (heights.toEast()) {
					byteChunk.setArcNorthWest(insetNS, y1, y2, glassId, false);
					stillNeedWalls = false;
				}
			} else if (heights.toNorth()) {
				if (heights.toWest()) {
					byteChunk.setArcSouthEast(insetNS, y1, y2, glassId, false);
					stillNeedWalls = false;
				} else if (heights.toEast()) {
					byteChunk.setArcSouthWest(insetNS, y1, y2, glassId, false);
					stillNeedWalls = false;
				}
			}
		}
		
		// still need to do something?
		if (stillNeedWalls) {
			
			// corner columns
			if (!heights.toSouthWest())
				byteChunk.setBlocks(insetNS, y1, y2, insetEW, materialId);
			if (!heights.toSouthEast())
				byteChunk.setBlocks(insetNS, y1, y2, ByteChunk.Width - insetEW - 1, materialId);
			if (!heights.toNorthWest())
				byteChunk.setBlocks(ByteChunk.Width - insetNS - 1, y1, y2, insetEW, materialId);
			if (!heights.toNorthEast())
				byteChunk.setBlocks(ByteChunk.Width - insetNS - 1, y1, y2, ByteChunk.Width - insetEW - 1, materialId);
			
			// cardinal walls
			if (!heights.toSouth())
				byteChunk.setBlocks(insetNS,  insetNS + 1, y1, y2, insetEW + 1, ByteChunk.Width - insetEW - 1, materialId, glassId, windowsZ);
			if (!heights.toNorth())
				byteChunk.setBlocks(ByteChunk.Width - insetNS - 1,  ByteChunk.Width - insetNS, y1, y2, insetEW + 1, ByteChunk.Width - insetEW - 1, materialId, glassId, windowsZ);
			if (!heights.toWest())
				byteChunk.setBlocks(insetNS + 1, ByteChunk.Width - insetNS - 1, y1, y2, insetEW, insetEW + 1, materialId, glassId, windowsX);
			if (!heights.toEast())
				byteChunk.setBlocks(insetNS + 1, ByteChunk.Width - insetNS - 1, y1, y2, ByteChunk.Width - insetEW - 1, ByteChunk.Width - insetEW, materialId, glassId, windowsX);
		}
			
		// only if there are insets
		if (insetNS > 0) {
			if (heights.toSouth()) {
				if (!heights.toSouthWest())
					byteChunk.setBlocks(0, insetNS, y1, y2, insetEW, insetEW + 1, materialId, glassId, windowsZ);
				if (!heights.toSouthEast())
					byteChunk.setBlocks(0, insetNS, y1, y2, ByteChunk.Width - insetEW - 1, ByteChunk.Width - insetEW, materialId, glassId, windowsZ);
			}
			if (heights.toNorth()) {
				if (!heights.toNorthWest())
					byteChunk.setBlocks(ByteChunk.Width - insetNS, ByteChunk.Width, y1, y2, insetEW, insetEW + 1, materialId, glassId, windowsZ);
				if (!heights.toNorthEast())
					byteChunk.setBlocks(ByteChunk.Width - insetNS, ByteChunk.Width, y1, y2, ByteChunk.Width - insetEW - 1, ByteChunk.Width - insetEW, materialId, glassId, windowsZ);
			}
		}
		if (insetEW > 0) {
			if (heights.toWest()) {
				if (!heights.toSouthWest())
					byteChunk.setBlocks(insetNS, insetNS + 1, y1, y2, 0, insetEW, materialId, glassId, windowsX);
				if (!heights.toNorthWest())
					byteChunk.setBlocks(ByteChunk.Width - insetNS - 1, ByteChunk.Width - insetNS, y1, y2, 0, insetEW, materialId, glassId, windowsX);
			}
			if (heights.toEast()) {
				if (!heights.toSouthEast())
					byteChunk.setBlocks(insetNS, insetNS + 1, y1, y2, ByteChunk.Width - insetEW, ByteChunk.Width, materialId, glassId, windowsX);
				if (!heights.toNorthEast())
					byteChunk.setBlocks(ByteChunk.Width - insetNS - 1, ByteChunk.Width - insetNS, y1, y2, ByteChunk.Width - insetEW, ByteChunk.Width, materialId, glassId, windowsX);
			}
		}
	}
	
	protected void drawSingleDoor(RealChunk chunk, int x1, int x2, int x3, int z1, int z2, int z3, int y1, int floorHeight, 
			Direction.Door direction, Material wallMaterial) {
		int y2 = y1 + floorHeight - 1;
		
		// frame the door
		chunk.setBlocks(x1, y1, y2, z1, wallMaterial);
		chunk.setBlocks(x2, y1 + 2, y2, z2, wallMaterial);
		chunk.setBlocks(x3, y1, y2, z3, wallMaterial);
		
		// place the door
		chunk.setWoodenDoor(x2, y1, z2, direction);
	}
	
	//TODO add doors that support rounded corners (i.e. SW allows for doors at the NW and SE corners)
	
	protected void drawCenteredDoors(RealChunk chunk, int x1, int x2, int z1, int z2, int y1, int floorHeight,
			boolean doorToSouth, boolean doorToNorth, boolean doorToWest, boolean doorToEast, 
			Material wallMaterial) {
		int center = RealChunk.Width / 2;
		
		//TODO Need to assure that there is at least one door... but how is the question... humm?

		if (doorToSouth && rand.nextBoolean())
			drawSingleDoor(chunk, x1, x1, x1, center - 1, center, center + 1, 
					y1, floorHeight, Door.WESTBYSOUTHWEST, wallMaterial);
		if (doorToNorth && rand.nextBoolean())
			drawSingleDoor(chunk, x2, x2, x2, center - 1, center, center + 1, 
					y1, floorHeight, Door.EASTBYNORTHEAST, wallMaterial);
		if (doorToWest && rand.nextBoolean())
			drawSingleDoor(chunk, center - 1, center, center + 1, z1, z1, z1, 
					y1, floorHeight, Door.NORTHBYNORTHWEST, wallMaterial);
		if (doorToEast && rand.nextBoolean())
			drawSingleDoor(chunk, center - 1, center, center + 1, z2, z2, z2, 
					y1, floorHeight, Door.SOUTHBYSOUTHEAST, wallMaterial);
	}
	
	protected boolean stairsHere(SurroundingFloors neighbors, double throwOfDice) {
		return (throwOfDice < 1.0 - ((double) neighbors.getNeighborCount() / 4.0));
	}
	
	class StairAt {
		public int X = 0;
		public int Z = 0;
		public StairAt(int floorHeight, int insetNS, int insetEW, StairWell where) {
			switch (where) {
			case CENTER:
				X = (RealChunk.Width - floorHeight) / 2;
				Z = (RealChunk.Width - 4) / 2;
				break;
			case SOUTHWEST:
				X = insetNS;
				Z = insetEW;
				break;
			case SOUTHEAST:
				X = insetNS;
				Z = RealChunk.Width - 4 - insetEW;
				break;
			case NORTHWEST:
				X = RealChunk.Width - floorHeight - insetNS;
				Z = insetEW;
				break;
			case NORTHEAST:
				X = RealChunk.Width - floorHeight - insetNS;
				Z = RealChunk.Width - 4 - insetEW;
				break;
			}
		}
	}

	public StairWell getStairWellLocation(boolean allowRounded, SurroundingFloors heights) {
		if (allowRounded && rounded) { //TODO && insetNS == insetEW && heights.getNeighborCount() == 2
			if (heights.toSouth()) {
				if (heights.toWest()) {
					return StairWell.SOUTHWEST;
				} else if (heights.toEast()) {
					return StairWell.SOUTHEAST;
				}
			} else if (heights.toNorth()) {
				if (heights.toWest()) {
					return StairWell.NORTHWEST;
				} else if (heights.toEast()) {
					return StairWell.NORTHEAST;
				}
			}
		}
		return StairWell.CENTER;
	}
	
	protected void drawStairs(RealChunk chunk, int y, int floorHeight, 
			int insetNS, int insetEW, StairWell where, Material stairMaterial) {
		StairAt at = new StairAt(floorHeight, insetNS, insetEW, where);
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
		StairAt at = new StairAt(floorHeight, insetNS, insetEW, where);
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
