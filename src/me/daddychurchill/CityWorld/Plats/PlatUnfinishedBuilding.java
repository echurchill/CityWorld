package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SurroundingFloors;
import me.daddychurchill.CityWorld.Support.Direction.StairWell;

public class PlatUnfinishedBuilding extends PlatBuilding {

	protected final static int FloorHeight = ContextData.FloorHeight;
	
	protected final static byte girderId = (byte) Material.CLAY.getId();
	
	protected final static Material dirtMaterial = Material.DIRT;
	protected final static Material fenceMaterial = Material.IRON_FENCE;
	protected final static Material stairMaterial = Material.WOOD_STAIRS;
	protected final static Material wallMaterial = Material.SMOOTH_BRICK;
	protected final static Material ceilingMaterial = Material.STONE;
	
	protected final static int fenceHeight = 3;
	protected final static int inset = 2;
	
	// our special bits
	protected boolean unfinishedBasementOnly;
	protected int floorsBuilt;
	protected int lastHorizontalGirder;
	
	public PlatUnfinishedBuilding(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		ContextData context = platmap.context;
		
		// basement only?
		unfinishedBasementOnly = chunkRandom.nextInt(context.oddsOfOnlyUnfinishedBasements) == 0;
		
		// how many floors are finished?
		floorsBuilt = chunkRandom.nextInt(height);
	}

	@Override
	public void generateChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, ContextData context, int platX, int platZ) {

		// check out the neighbors
		SurroundingFloors neighborBasements = getNeighboringBasementCounts(platmap, platX, platZ);
		SurroundingFloors neighborFloors = getNeighboringFloorCounts(platmap, platX, platZ);

		// starting with the bottom
		int lowestY = context.streetLevel - FloorHeight * (depth - 1) - 3;
		
		// bottom most floor
		drawCeilings(chunk, context, lowestY, 1, 0, 0, false, ceilingMaterial, neighborBasements);
		
		// below ground
		for (int floor = 0; floor < depth; floor++) {
			int floorAt = context.streetLevel - FloorHeight * floor - 2;
			
			// clear it out
			chunk.setLayer(floorAt, FloorHeight, airId);
			
			// at the first floor add a fence to prevent folks from falling in
			if (floor == 0) {
				drawWalls(chunk, context, context.streetLevel + 2, fenceHeight, 0, 0, false,
						fenceMaterial, fenceMaterial, neighborBasements);
				holeFence(chunk, context.streetLevel + 2, neighborBasements);
			}
			
			// one floor please
			drawWalls(chunk, context, floorAt, FloorHeight, 0, 0, false,
					dirtMaterial, dirtMaterial, neighborBasements);
			drawWalls(chunk, context, floorAt, FloorHeight, 1, 1, false,
					wallMaterial, wallMaterial, neighborBasements);
			
			// ceilings if needed
			if (!unfinishedBasementOnly) {
				drawCeilings(chunk, context, floorAt + FloorHeight - 1, 1, 1, 1, false,
						ceilingMaterial, neighborBasements);
			} else {
				drawHorizontalGirders(chunk, floorAt + FloorHeight - 1, neighborBasements);
			}
	
			// hold up the bit we just drew
			drawVerticalGirders(chunk, floorAt, FloorHeight);
			
			// one down, more to go
			neighborBasements.decrement();
		}
		
		// do more?
		if (!unfinishedBasementOnly) {
			lastHorizontalGirder = 0;

			// above ground
			for (int floor = 0; floor < height; floor++) {
				int floorAt = context.streetLevel + FloorHeight * floor + 2;
				
				// floor built yet?
				if (floor <= floorsBuilt) {
					
					// the floor of the next floor
					drawCeilings(chunk, context, floorAt + FloorHeight - 1, 1, 1, 1, false,
							ceilingMaterial, neighborFloors);
				} else {
					
					// sometimes the top most girders aren't there quite yet
					if (floor < height - 1 || chunkRandom.nextBoolean()) {
						drawHorizontalGirders(chunk, floorAt + FloorHeight - 1, neighborFloors);
						lastHorizontalGirder = floorAt + FloorHeight - 1;
					}
				}
	
				// hold up the bit we just drew
				drawVerticalGirders(chunk, floorAt, FloorHeight);
				
				// one down, more to go
				neighborFloors.decrement();
			}
		}
	}

	@Override
	public void generateBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, ContextData context, int platX, int platZ) {
		Random random = chunk.random;
		
		// work on the basement stairs first
		if (!unfinishedBasementOnly) {
			
			if (needStairsDown) {
				for (int floor = 0; floor < depth; floor++) {
					int y = context.streetLevel - FloorHeight * floor - 2;
					
					// place the stairs and such
					drawStairs(chunk, y, FloorHeight, inset, inset, StairWell.CENTER, stairMaterial);

// unfinished buildings don't need walls on the stairs
//					drawStairsWalls(chunk, y, FloorHeight, inset, inset, StairWell.CENTER, 
//							wallMaterial, false, false);
				}
			}
			
			if (needStairsUp) {
				for (int floor = 0; floor < height; floor++) {
					int y = context.streetLevel + FloorHeight * floor + 2;
					
					// floor built yet?
					if (floor <= floorsBuilt) {
						
						// more stairs and such
						if (floor < height - 1)
							drawStairs(chunk, y, FloorHeight, inset, inset, StairWell.CENTER, stairMaterial);
						
// unfinished buildings don't need walls on the stairs
//						if (floor > 0 || (floor == 0 && (depth > 0 || height > 1)))
//							drawStairsWalls(chunk, y, FloorHeight, inset, inset, StairWell.CENTER, 
//									stairWallMaterial, false, false);
					}
				}
			}
			
			// plop a crane on top?
			if (lastHorizontalGirder > 0 && random.nextInt(context.oddsOfCranes) == 0) {
				if (random.nextBoolean())
					chunk.drawCrane(context, inset + 2, lastHorizontalGirder + 1, inset);
				else
					chunk.drawCrane(context, inset + 2, lastHorizontalGirder + 1, chunk.width - inset - 1);
			}
		}
	}
	
	private void drawVerticalGirders(ByteChunk chunk, int y1, int floorHeight) {
		int y2 = y1 + floorHeight;
		chunk.setBlocks(inset, y1, y2, inset, girderId);
		chunk.setBlocks(inset, y1, y2, chunk.width - inset - 1, girderId);
		chunk.setBlocks(chunk.width - inset - 1, y1, y2, inset, girderId);
		chunk.setBlocks(chunk.width - inset - 1, y1, y2, chunk.width - inset - 1, girderId);
	}

	private void drawHorizontalGirders(ByteChunk chunk, int y1, SurroundingFloors neighbors) {
		int x1 = neighbors.toWest() ? 0 : inset;
		int x2 = neighbors.toEast() ? chunk.width - 1 : chunk.width - inset - 1;
		int z1 = neighbors.toNorth() ? 0 : inset;
		int z2 = neighbors.toSouth() ? chunk.width - 1 : chunk.width - inset - 1;
		int i1 = inset;
		int i2 = chunk.width - inset - 1;
		
		chunk.setBlocks(x1, x2 + 1, y1, y1 + 1, i1, i1 + 1, girderId);
		chunk.setBlocks(x1, x2 + 1, y1, y1 + 1, i2, i2 + 1, girderId);
		chunk.setBlocks(i1, i1 + 1, y1, y1 + 1, z1, z2 + 1, girderId);
		chunk.setBlocks(i2, i2 + 1, y1, y1 + 1, z1, z2 + 1, girderId);
	}
	
	private void holeFence(ByteChunk chunk, int y1, SurroundingFloors neighbors) {
		Random random = chunk.random;
		
		int i = random.nextInt(chunk.width / 2) + 4;
		int y2 = y1 + 2;
		if (random.nextBoolean() && !neighbors.toWest())
			chunk.setBlocks(0, y1, y2, i, airId);
		if (random.nextBoolean() && !neighbors.toEast())
			chunk.setBlocks(chunk.width - 1, y1, y2, i, airId);
		if (random.nextBoolean() && !neighbors.toNorth())
			chunk.setBlocks(i, y1, y2, 0, airId);
		if (random.nextBoolean() && !neighbors.toSouth())
			chunk.setBlocks(i, y1, y2, chunk.width - 1, airId);
	}
}
