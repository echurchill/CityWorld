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
	
	protected final static byte airId = (byte) Material.AIR.getId();
	protected final static byte girderId = (byte) Material.IRON_BLOCK.getId();
	protected final static Material dirtMaterial = Material.DIRT;
	protected final static Material fenceMaterial = Material.IRON_FENCE;
	protected final static Material stairMaterial = Material.SMOOTH_STAIRS;
	protected final static Material stairWallMaterial = Material.SMOOTH_BRICK;
	protected final static Material wallMaterial = Material.SMOOTH_BRICK;
	protected final static Material ceilingMaterial = Material.STONE;
	
	protected final static int onlyUnfinishedBasementsOdds = 8; // unfinished buildings only have basements 1/n of the time
	protected final static int fenceHeight = 3;
	protected final static int inset = 2;
	protected boolean basementOnly;
	protected int floorsBuilt;
	
	public PlatUnfinishedBuilding(Random rand, int maxHeight, int maxDepth,
			int overallIdenticalHeightsOdds, int overallSimilarHeightsOdds,
			int overallSimilarRoundedOdds) {
		super(rand, maxHeight, maxDepth, overallIdenticalHeightsOdds,
				overallSimilarHeightsOdds, overallSimilarRoundedOdds);
		
		// basement only?
		basementOnly = rand.nextInt(onlyUnfinishedBasementsOdds) == 0;
		
		// how many floors are finished?
		floorsBuilt = rand.nextInt(height);
	}
	
	@Override
	public void makeConnected(Random rand, PlatLot relative) {
		super.makeConnected(rand, relative);
		
		// other bits
		if (relative instanceof PlatUnfinishedBuilding) {
			//PlatUnfinishedBuilding relativebuilding = (PlatUnfinishedBuilding) relative;

			// unlike most other plat types, this one doesn't attempt to make its bits similar
			//basementOnly = relativebuilding.basementOnly;
			//floorsBuilt = relativebuilding.floorsBuilt;
		}
	}

	@Override
	public void generateChunk(PlatMap platmap, ByteChunk chunk, int platX, int platZ) {
		// check out the neighbors
		SurroundingFloors neighborBasements = getNeighboringBasementCounts(platmap, platX, platZ);
		SurroundingFloors neighborFloors = getNeighboringFloorCounts(platmap, platX, platZ);

		// starting with the bottom
		int lowestY = PlatMap.StreetLevel - FloorHeight * (depth - 1) - 3;
		generateBedrock(chunk, lowestY);
		
		// bottom most floor
		drawCeilings(chunk, lowestY, 1, 0, 0, false, ceilingMaterial, neighborBasements);
		
		// below ground
		for (int floor = 0; floor < depth; floor++) {
			int floorAt = PlatMap.StreetLevel - FloorHeight * floor - 2;
			
			// at the first floor add a fence to prevent folks from falling in
			if (floor == 0) {
				drawWalls(chunk, PlatMap.StreetLevel + 2, fenceHeight, 0, 0, false,
						fenceMaterial, fenceMaterial, neighborBasements);
				holeFence(chunk, PlatMap.StreetLevel + 2, neighborBasements);
			}
			
			// one floor please
			drawWalls(chunk, floorAt, FloorHeight, 0, 0, false,
					dirtMaterial, dirtMaterial, neighborBasements);
			drawWalls(chunk, floorAt, FloorHeight, 1, 1, false,
					wallMaterial, wallMaterial, neighborBasements);
			
			// ceilings if needed
			if (basementOnly) {
				drawHorizontalGirders(chunk, floorAt + FloorHeight - 1, neighborBasements);
				drawVerticalGirders(chunk, floorAt, FloorHeight);
			} else {
				drawCeilings(chunk, floorAt + FloorHeight - 1, 1, 1, 1, false,
						ceilingMaterial, neighborBasements);
				drawVerticalGirders(chunk, floorAt, FloorHeight);
			}
	
			// one down, more to go
			neighborBasements.decrement();
		}
		
		// do more?
		if (!basementOnly) {

			// above ground
			for (int floor = 0; floor < height; floor++) {
				int floorAt = PlatMap.StreetLevel + FloorHeight * floor + 2;
				
				// floor built yet?
				if (floor <= floorsBuilt) {
					drawCeilings(chunk, floorAt + FloorHeight - 1, 1, 1, 1, false,
							ceilingMaterial, neighborFloors);
					drawVerticalGirders(chunk, floorAt, FloorHeight);
				} else {
					drawHorizontalGirders(chunk, floorAt + FloorHeight - 1, neighborFloors);
					drawVerticalGirders(chunk, floorAt, FloorHeight);
				}
	
				// one down, more to go
				neighborFloors.decrement();
			}
		}
	}

	@Override
	public void generateBlocks(PlatMap platmap, RealChunk chunk, int platX, int platZ) {
		
		// work on the basement stairs first
		if (!basementOnly) {
			
			if (needStairsDown) {
				for (int floor = 0; floor < depth; floor++) {
					int y = PlatMap.StreetLevel - FloorHeight * floor - 2;
					
					// place the stairs and such
					drawStairs(chunk, y, FloorHeight, inset, inset, StairWell.CENTER, stairMaterial);
						
					// plain walls please
					drawStairsWalls(chunk, y, FloorHeight, inset, inset, StairWell.CENTER, wallMaterial, false, floor == depth - 1);
				}
			}
			
			if (needStairsUp) {
				for (int floor = 0; floor < height; floor++) {
					int y = PlatMap.StreetLevel + FloorHeight * floor + 2;
					
					// floor built yet?
					if (floor <= floorsBuilt) {
						
						// more stairs and such
						drawStairs(chunk, y, FloorHeight, inset, inset, StairWell.CENTER, stairMaterial);
						
						// fancy walls... maybe
						drawStairsWalls(chunk, y, FloorHeight, inset, inset, StairWell.CENTER, stairWallMaterial, false, false);
					}
				}
			}
		}
	}
	
	private void drawVerticalGirders(ByteChunk chunk, int y1, int floorHeight) {
		int y2 = y1 + floorHeight;
		chunk.setBlocks(inset, y1, y2, inset, girderId);
		chunk.setBlocks(inset, y1, y2, ByteChunk.Width - inset - 1, girderId);
		chunk.setBlocks(ByteChunk.Width - inset - 1, y1, y2, inset, girderId);
		chunk.setBlocks(ByteChunk.Width - inset - 1, y1, y2, ByteChunk.Width - inset - 1, girderId);
	}

	private void drawHorizontalGirders(ByteChunk chunk, int y1, SurroundingFloors neighbors) {
		int x1 = neighbors.toWest() ? 0 : inset;
		int x2 = neighbors.toEast() ? ByteChunk.Width - 1 : ByteChunk.Width - inset - 1;
		int z1 = neighbors.toNorth() ? 0 : inset;
		int z2 = neighbors.toSouth() ? ByteChunk.Width - 1 : ByteChunk.Width - inset - 1;
		int i1 = inset;
		int i2 = ByteChunk.Width - inset - 1;
		
		chunk.setBlocks(x1, x2 + 1, y1, y1 + 1, i1, i1 + 1, girderId);
		chunk.setBlocks(x1, x2 + 1, y1, y1 + 1, i2, i2 + 1, girderId);
		chunk.setBlocks(i1, i1 + 1, y1, y1 + 1, z1, z2 + 1, girderId);
		chunk.setBlocks(i2, i2 + 1, y1, y1 + 1, z1, z2 + 1, girderId);
	}
	
	private void holeFence(ByteChunk chunk, int y1, SurroundingFloors neighbors) {
		int i = rand.nextInt(ByteChunk.Width / 2) + 4;
		int y2 = y1 + 2;
		if (rand.nextBoolean() && !neighbors.toWest())
			chunk.setBlocks(0, y1, y2, i, airId);
		if (rand.nextBoolean() && !neighbors.toEast())
			chunk.setBlocks(ByteChunk.Width - 1, y1, y2, i, airId);
		if (rand.nextBoolean() && !neighbors.toNorth())
			chunk.setBlocks(i, y1, y2, 0, airId);
		if (rand.nextBoolean() && !neighbors.toSouth())
			chunk.setBlocks(i, y1, y2, ByteChunk.Width - 1, airId);
	}

}
