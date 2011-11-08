package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMaps.PlatMap;
import me.daddychurchill.CityWorld.Support.Chunk;
import me.daddychurchill.CityWorld.Support.MaterialFactory;
import me.daddychurchill.CityWorld.Support.GlassFactoryX;
import me.daddychurchill.CityWorld.Support.GlassFactoryZ;
import me.daddychurchill.CityWorld.Support.SurroundingFloors;

import org.bukkit.Material;

public abstract class PlatBuilding extends PlatLot {
	
	protected boolean neighborsHaveIdenticalHeights;
	protected int neighborsHaveSimilarHeightsOdds;
	protected int height; // floors up
	protected int depth; // floors down
	protected MaterialFactory windowsX;
	protected MaterialFactory windowsZ;
	
	protected Material[] materials;
	
	public PlatBuilding(Random rand, int maxHeight, int maxDepth, int overallIdenticalHeightsOdds, int overallSimilarHeightsOdds) {
		super(rand);
		
		neighborsHaveIdenticalHeights = rand.nextInt(overallIdenticalHeightsOdds) != 0;
		neighborsHaveSimilarHeightsOdds = overallIdenticalHeightsOdds;
		height = rand.nextInt(maxHeight) + 1;
		depth = rand.nextInt(maxDepth) + 1;
		windowsX = new GlassFactoryX(rand);
		windowsZ = new GlassFactoryZ(rand, windowsX.style);
	}
	
	protected void setMaterials(Material ... data) {
		materials = data.clone();
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

			// copy over the material bits
			if (relativebuilding.materials != null) {
				materials = relativebuilding.materials.clone();
			}
			
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
	
	protected void drawCeilings(Chunk chunk, int blocky, int height, int insetNS, int insetEW, Material material, SurroundingFloors heights) {
		byte materialId = (byte) material.getId();
		
		// center part
		chunk.setBlocks(insetNS, Chunk.Width - insetNS, blocky, blocky + height, insetEW, Chunk.Width - insetEW, materialId);
		
		// only if we are inset
		if (insetNS > 0 || insetEW > 0) {
			
			// cardinal bits
			if (heights.toSouth())
				chunk.setBlocks(0, insetNS, blocky, blocky + height, insetEW, Chunk.Width - insetEW, materialId);
			if (heights.toNorth())
				chunk.setBlocks(Chunk.Width - insetNS, Chunk.Width, blocky, blocky + height, insetEW, Chunk.Width - insetEW, materialId);
			if (heights.toWest())
				chunk.setBlocks(insetNS, Chunk.Width - insetNS, blocky, blocky + height, 0, insetEW, materialId);
			if (heights.toEast())
				chunk.setBlocks(insetNS, Chunk.Width - insetNS, blocky, blocky + height, Chunk.Width - insetEW, Chunk.Width, materialId);
			
			// corner bits
			if (heights.toSouthWest())
				chunk.setBlocks(0, insetNS, blocky, blocky + height, 0, insetEW, materialId);
			if (heights.toSouthEast())
				chunk.setBlocks(0, insetNS, blocky, blocky + height, Chunk.Width - insetEW, Chunk.Width, materialId);
			if (heights.toNorthWest())
				chunk.setBlocks(Chunk.Width - insetNS, Chunk.Width, blocky, blocky + height, 0, insetEW, materialId);
			if (heights.toNorthEast())
				chunk.setBlocks(Chunk.Width - insetNS, Chunk.Width, blocky, blocky + height, Chunk.Width - insetEW, Chunk.Width, materialId);
		}
	}
	
	protected void drawWalls(Chunk chunk, int y1, int height, int insetNS, int insetEW, Material material, Material glass, SurroundingFloors heights) {
		byte materialId = (byte) material.getId();
		byte glassId = (byte) glass.getId();
		int y2 = y1 + height;
		
		// corner columns
		if (!heights.toSouthWest())
			chunk.setBlocks(insetNS, y1, y2, insetEW, materialId);
		if (!heights.toSouthEast())
			chunk.setBlocks(insetNS, y1, y2, Chunk.Width - insetEW - 1, materialId);
		if (!heights.toNorthWest())
			chunk.setBlocks(Chunk.Width - insetNS - 1, y1, y2, insetEW, materialId);
		if (!heights.toNorthEast())
			chunk.setBlocks(Chunk.Width - insetNS - 1, y1, y2, Chunk.Width - insetEW - 1, materialId);
		
		// cardinal walls
		if (!heights.toSouth())
			chunk.setBlocks(insetNS,  insetNS + 1, y1, y2, insetEW + 1, Chunk.Width - insetEW - 1, materialId, glassId, windowsZ);
		if (!heights.toNorth())
			chunk.setBlocks(Chunk.Width - insetNS - 1,  Chunk.Width - insetNS, y1, y2, insetEW + 1, Chunk.Width - insetEW - 1, materialId, glassId, windowsZ);
		if (!heights.toWest())
			chunk.setBlocks(insetNS + 1, Chunk.Width - insetNS - 1, y1, y2, insetEW, insetEW + 1, materialId, glassId, windowsX);
		if (!heights.toEast())
			chunk.setBlocks(insetNS + 1, Chunk.Width - insetNS - 1, y1, y2, Chunk.Width - insetEW - 1, Chunk.Width - insetEW, materialId, glassId, windowsX);
		
		// only if there are insets
		if (insetNS > 0) {
			if (heights.toSouth()) {
				if (!heights.toSouthWest())
					chunk.setBlocks(0, insetNS, y1, y2, insetEW, insetEW + 1, materialId, glassId, windowsZ);
				if (!heights.toSouthEast())
					chunk.setBlocks(0, insetNS, y1, y2, Chunk.Width - insetEW - 1, Chunk.Width - insetEW, materialId, glassId, windowsZ);
			}
			if (heights.toNorth()) {
				if (!heights.toNorthWest())
					chunk.setBlocks(Chunk.Width - insetNS, Chunk.Width, y1, y2, insetEW, insetEW + 1, materialId, glassId, windowsZ);
				if (!heights.toNorthEast())
					chunk.setBlocks(Chunk.Width - insetNS, Chunk.Width, y1, y2, Chunk.Width - insetEW - 1, Chunk.Width - insetEW, materialId, glassId, windowsZ);
			}
		}
		if (insetEW > 0) {
			if (heights.toWest()) {
				if (!heights.toSouthWest())
					chunk.setBlocks(insetNS, insetNS + 1, y1, y2, 0, insetEW, materialId, glassId, windowsX);
				if (!heights.toNorthWest())
					chunk.setBlocks(Chunk.Width - insetNS - 1, Chunk.Width - insetNS, y1, y2, 0, insetEW, materialId, glassId, windowsX);
			}
			if (heights.toEast()) {
				if (!heights.toSouthEast())
					chunk.setBlocks(insetNS, insetNS + 1, y1, y2, Chunk.Width - insetEW, Chunk.Width, materialId, glassId, windowsX);
				if (!heights.toNorthEast())
					chunk.setBlocks(Chunk.Width - insetNS - 1, Chunk.Width - insetNS, y1, y2, Chunk.Width - insetEW, Chunk.Width, materialId, glassId, windowsX);
			}
		}
	}
}
