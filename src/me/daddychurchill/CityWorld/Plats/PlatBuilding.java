package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMaps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.MaterialFactory;
import me.daddychurchill.CityWorld.Support.GlassFactoryX;
import me.daddychurchill.CityWorld.Support.GlassFactoryZ;
import me.daddychurchill.CityWorld.Support.SurroundingFloors;

import org.bukkit.Material;

public abstract class PlatBuilding extends PlatLot {
	
	protected boolean neighborsHaveIdenticalHeights;
	protected int neighborsHaveSimilarHeightsOdds;
	protected int neighborsHaveSimilarRoundedOdds;
	protected int height; // floors up
	protected int depth; // floors down
	protected boolean rounded; // rounded corners if possible? (only if the insets match)
	protected MaterialFactory windowsX;
	protected MaterialFactory windowsZ;
	
	public PlatBuilding(Random rand, int maxHeight, int maxDepth, 
			int overallIdenticalHeightsOdds, int overallSimilarHeightsOdds, 
			int overallSimilarRoundedOdds) {
		super(rand);
		
		neighborsHaveIdenticalHeights = rand.nextInt(overallIdenticalHeightsOdds) == 0;
		neighborsHaveSimilarHeightsOdds = overallIdenticalHeightsOdds;
		neighborsHaveSimilarRoundedOdds = overallSimilarRoundedOdds;
		height = rand.nextInt(maxHeight) + 1;
		depth = rand.nextInt(maxDepth) + 1;
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
		if (allowRounded && rounded && insetNS == insetEW && heights.getNeighborCount() == 2) {
			if (heights.toSouth()) {
				if (heights.toWest()) {
					byteChunk.setArcNorthEast(insetNS, y1, y2, materialId, true);
					stillNeedCeiling = false;
				} else if (heights.toEast()) {
					byteChunk.setArcNorthWest(insetNS, y1, y2, materialId, true);
					stillNeedCeiling = false;
				}
			} else if (heights.toNorth()) {
				if (heights.toWest()) {
					byteChunk.setArcSouthEast(insetNS, y1, y2, materialId, true);
					stillNeedCeiling = false;
				} else if (heights.toEast()) {
					byteChunk.setArcSouthWest(insetNS, y1, y2, materialId, true);
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
		if (allowRounded && rounded && insetNS == insetEW && heights.getNeighborCount() == 2) {
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
	}
}
