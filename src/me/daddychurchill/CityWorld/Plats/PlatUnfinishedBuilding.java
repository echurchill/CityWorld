package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.PlatMaps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SurroundingFloors;
import me.daddychurchill.CityWorld.Support.Direction.StairWell;

public class PlatUnfinishedBuilding extends PlatBuilding {

	protected final static int FloorHeight = PlatMap.FloorHeight;
	
	protected final static Material girderMaterial = Material.IRON_BLOCK;
	protected final static Material dirtMaterial = Material.DIRT;
	protected final static Material fenceMaterial = Material.IRON_FENCE;
	protected final static Material stairMaterial = Material.SMOOTH_STAIRS;
	protected final static Material stairWallMaterial = Material.SMOOTH_BRICK;
	protected final static Material wallMaterial = Material.SMOOTH_BRICK;
	protected final static Material backfillMaterial = Material.COBBLESTONE;
	protected final static Material ceilingMaterial = Material.STONE;
	
	protected final static int onlyUnfinishedBasementsOdds = 8; // unfinished buildings only have basements 1/n of the time
	protected boolean basementOnly;
	
	public PlatUnfinishedBuilding(Random rand, int maxHeight, int maxDepth,
			int overallIdenticalHeightsOdds, int overallSimilarHeightsOdds,
			int overallSimilarRoundedOdds) {
		super(rand, maxHeight, maxDepth, overallIdenticalHeightsOdds,
				overallSimilarHeightsOdds, overallSimilarRoundedOdds);
		
		// basement only?
		basementOnly = rand.nextInt(onlyUnfinishedBasementsOdds) == 0;
	}
	
	@Override
	public void makeConnected(Random rand, PlatLot relative) {
		// TODO Auto-generated method stub
		super.makeConnected(rand, relative);
		
		// other bits
		if (relative instanceof PlatUnfinishedBuilding) {
			PlatUnfinishedBuilding relativebuilding = (PlatUnfinishedBuilding) relative;

			// nudge in a bit
			basementOnly = relativebuilding.basementOnly;
		}
	}

	@Override
	public void generateChunk(PlatMap platmap, ByteChunk chunk, int platX, int platZ) {
		// starting with the bottom
		int lowestY = PlatMap.StreetLevel - FloorHeight * (depth - 1) - 3;
		generateBedrock(chunk, lowestY);
		
		// bottom most floor
		SurroundingFloors neighborBasements = getNeighboringBasementCounts(platmap, platX, platZ);
		drawCeilings(chunk, lowestY, 1, 0, 0, false, ceilingMaterial, neighborBasements);
		
		// below ground
		for (int floor = 0; floor < depth; floor++) {
			int floorAt = PlatMap.StreetLevel - FloorHeight * floor - 2;
			
			// one floor please
			drawWalls(chunk, floorAt, FloorHeight, 0, 0, false,
					wallMaterial, wallMaterial, neighborBasements);
	
			// pillars 
			byte wallMaterialId = (byte) wallMaterial.getId();
			int ceilingAt = floorAt + FloorHeight;
			chunk.setBlocks(3, 5, floorAt, ceilingAt, 3, 5, wallMaterialId);
			chunk.setBlocks(3, 5, floorAt, ceilingAt, 11, 13, wallMaterialId);
			chunk.setBlocks(11, 13, floorAt, ceilingAt, 3, 5, wallMaterialId);
			chunk.setBlocks(11, 13, floorAt, ceilingAt, 11, 13, wallMaterialId);
		
			// one down, more to go
			neighborBasements.decrement();
		}
		
		// do more?
		if (!basementOnly) {

//			// above ground
//			SurroundingFloors neighborFloors = getNeighboringFloorCounts(platmap, platX, platZ);
//			for (int floor = 0; floor < height; floor++) {
//				int floorAt = PlatMap.StreetLevel + FloorHeight * floor + 2;
//	
//				// hold things up
//				int y2 = floorAt + floorHeight;
//				chunk.setBlocks(insetWallNS, floorAt, y2, insetWallEW, ironId);
//				chunk.setBlocks(insetWallNS, floorAt, y2, ByteChunk.Width - 1 - insetWallEW, ironId);
//				chunk.setBlocks(ByteChunk.Width - 1 - insetWallNS, floorAt, y2, insetWallEW, ironId);
//				chunk.setBlocks(ByteChunk.Width - 1 - insetWallNS, floorAt, y2, ByteChunk.Width - 1 - insetWallEW, ironId);
//				
//				// hold things together
//				if (neighbors.toWest()) {
//					if (neighbors.toEast()) {
//						chunk.setBlocks(x1, x2, y1, y2, z1, z2, materialId);
//					else
//						chunk.setBlocks(x1, x2, y1, y2, z1, z2, materialId)
//						if (neighbors.toWest())
//				chunk.setBlocks(x1, x2, y1, y2, z1, z2, materialId)
//	
//				// one down, more to go
//				neighborFloors.decrement();
//			}
		}
	}

	@Override
	public void generateBlocks(PlatMap platmap, RealChunk chunk, int platX, int platZ) {
		// work on the basement stairs first
		if (!basementOnly) {
			for (int floor = 0; floor < depth; floor++) {
				
				// place the stairs and such
				drawStairs(chunk, PlatMap.StreetLevel - FloorHeight * floor - 2, FloorHeight, 0, 0, StairWell.CENTER, stairMaterial);
					
				// plain walls please
				drawStairsWalls(chunk, PlatMap.StreetLevel - FloorHeight * floor - 2, FloorHeight, 0, 0, StairWell.CENTER, wallMaterial, false, floor == depth - 1);
			}
		}
	}
}
