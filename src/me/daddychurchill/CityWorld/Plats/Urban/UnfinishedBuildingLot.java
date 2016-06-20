package me.daddychurchill.CityWorld.Plats.Urban;

import org.bukkit.Material;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.BuildingLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.InitialBlocks;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.RealBlocks;
import me.daddychurchill.CityWorld.Support.SurroundingFloors;
import me.daddychurchill.CityWorld.Support.Surroundings;
import me.daddychurchill.CityWorld.Support.BadMagic.StairWell;

public class UnfinishedBuildingLot extends BuildingLot {

	private final static int FloorHeight = DataContext.FloorHeight;
	
	private Material girderMaterial;// = Material.CLAY;
	
	private final static Material dirtMaterial = Material.DIRT;
	private final static Material stairMaterial = Material.WOOD_STAIRS;
	private final static Material stairPlatformMaterial = Material.WOOD;
	private final static Material wallMaterial = Material.SMOOTH_BRICK;
	private final static Material ceilingMaterial = Material.STONE;
	private final static Material floorMaterial = ceilingMaterial;
	
	protected final static int inset = 2;
	
	// our special bits
	protected boolean unfinishedBasementOnly;
	protected int floorsBuilt;
	protected int lastHorizontalGirder;
	
	public UnfinishedBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		DataContext context = platmap.context;
		
		// material please
		girderMaterial = platmap.generator.materialProvider.itemsSelectMaterial_UnfinishedBuildings.getRandomMaterial(chunkOdds, Material.CLAY);
		
		// basement only?
		unfinishedBasementOnly = chunkOdds.playOdds(context.oddsOfOnlyUnfinishedBasements);
		
		// how many floors are finished?
		floorsBuilt = chunkOdds.getRandomInt(height);
		
	}
	
	@Override
	public boolean makeConnected(PlatLot relative) {
		boolean result = super.makeConnected(relative);

		// other bits
		if (result && relative instanceof UnfinishedBuildingLot) {
			UnfinishedBuildingLot relativebuilding = (UnfinishedBuildingLot) relative;
			
			this.girderMaterial = relativebuilding.girderMaterial;
			if (neighborsHaveIdenticalHeights) {
				this.unfinishedBasementOnly = relativebuilding.unfinishedBasementOnly;
				this.floorsBuilt = relativebuilding.floorsBuilt;
			}
		}
		return result;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new UnfinishedBuildingLot(platmap, chunkX, chunkZ);
	}

	@Override
	public int getBottomY(CityWorldGenerator generator) {
		return generator.streetLevel - FloorHeight * (depth - 1) - 3;
	}
	
	@Override
	public int getTopY(CityWorldGenerator generator) {
		return generator.streetLevel + FloorHeight * (height + 1) + 10; // crane bit
	}

	@Override
	protected void generateActualChunk(CityWorldGenerator generator, PlatMap platmap, InitialBlocks chunk, BiomeGrid biomes, DataContext context, int platX, int platZ) {

		// check out the neighbors
		SurroundingFloors neighborBasements = getNeighboringBasementCounts(platmap, platX, platZ);
		SurroundingFloors neighborFloors = getNeighboringFloorCounts(platmap, platX, platZ);

		// starting with the bottom
		int lowestY = getBottomY(generator);
		
		// bottom most floor
		drawFoundation(generator, chunk, context, lowestY, 1, false, false, floorMaterial, neighborBasements);
		
		// below ground
		for (int floor = 0; floor < depth; floor++) {
			int floorAt = generator.streetLevel - FloorHeight * floor - 2;
			
			// clear it out
			chunk.airoutLayer(generator, floorAt, FloorHeight);
			
			// at the first floor add a fence to prevent folks from falling in
			if (floor == 0)
				drawFence(generator, chunk, context, 0, generator.streetLevel + 1, floor, neighborBasements, Material.IRON_FENCE, 3);
			
			// one floor please
			drawWallParts(generator, chunk, context, floorAt, FloorHeight, 0, 0, floor,
					false, false, false, dirtMaterial, neighborBasements);
			drawWallParts(generator, chunk, context, floorAt, FloorHeight, 1, 1, floor,
					false, false, false, wallMaterial, neighborBasements);
			
			// ceilings if needed
			if (!unfinishedBasementOnly) {
				drawCeilings(generator, chunk, context, floorAt + FloorHeight - 1, 1, 1, 1,
						false, false, false, ceilingMaterial, neighborBasements);
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
				int floorAt = generator.streetLevel + FloorHeight * floor + 2;
				
				// floor built yet?
				if (floor <= floorsBuilt) {
					
					// the floor of the next floor
					drawCeilings(generator, chunk, context, floorAt + FloorHeight - 1, 1, 1, 1,
							false, false, false, ceilingMaterial, neighborFloors);
				} else {
					
					// sometimes the top most girders aren't there quite yet
					if (floor < height - 1 || chunkOdds.flipCoin()) {
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
	protected void generateActualBlocks(CityWorldGenerator generator, PlatMap platmap, RealBlocks chunk, DataContext context, int platX, int platZ) {
		
		// work on the basement stairs first
		if (!unfinishedBasementOnly) {
			
			if (needStairsDown) {
				for (int floor = 0; floor < depth; floor++) {
					int floorAt = generator.streetLevel - FloorHeight * floor - 2;
					
					// plain walls please
					drawStairsWalls(generator, chunk, floorAt, basementFloorHeight, 
							StairWell.CENTER, Material.AIR, false, floor == depth - 1);

					// place the stairs and such
					drawStairs(generator, chunk, floorAt, FloorHeight, 
							StairWell.CENTER, stairMaterial, stairPlatformMaterial);
				}
			}
			
			if (needStairsUp) {
				for (int floor = 0; floor < height; floor++) {
					int floorAt = generator.streetLevel + FloorHeight * floor + 2;
					
					// floor built yet?
					if (floor <= floorsBuilt) {
						
						// fancy walls... maybe
						if (floor > 0 || (floor == 0 && (depth > 0 || height > 1)))
							drawStairsWalls(generator, chunk, floorAt, aboveFloorHeight, 
									StairWell.CENTER, Material.AIR, floor == height - 1, floor == 0 && depth == 0);
						
						// more stairs and such
						if (floor < height - 1)
							drawStairs(generator, chunk, floorAt, FloorHeight, 
									StairWell.CENTER, stairMaterial, stairPlatformMaterial);
					}
				}
			}
			
			// plop a crane on top?
			boolean craned = drawCrane(generator, chunk, context);
			
			// it looked so nice for a moment... but the moment has passed
			if (generator.settings.includeDecayedBuildings) {

				// what is the top floor?
				int floors = height;
				if (craned)
					floors--;
				
				// work our way up
				for (int floor = 1; floor < floors; floor++) {
					
					// do only floors that aren't top one or do the top one if there isn't a crane
					int y = generator.streetLevel + FloorHeight * floor + 1;
						
					// do we take out a bit of it?
					decayEdge(generator, chunk.getBlockX(7) + chunkOdds.getRandomInt(3) - 1, y, chunk.getBlockZ(inset));
					decayEdge(generator, chunk.getBlockX(8) + chunkOdds.getRandomInt(3) - 1, y, chunk.getBlockZ(chunk.width - inset - 1));
					decayEdge(generator, chunk.getBlockX(inset), y, chunk.getBlockZ(7) + chunkOdds.getRandomInt(3) - 1);
					decayEdge(generator, chunk.getBlockX(chunk.width - inset - 1), y, chunk.getBlockZ(8) + chunkOdds.getRandomInt(3) - 1);
				}
			}
		}
	}
	
	protected boolean drawCrane(CityWorldGenerator generator, RealBlocks chunk, DataContext context) {
		if (lastHorizontalGirder > 0 && chunkOdds.playOdds(context.oddsOfCranes)) {
			if (chunkOdds.flipCoin())
				chunk.drawCrane(context, chunkOdds, inset + 2, lastHorizontalGirder + 1, inset);
			else
				chunk.drawCrane(context, chunkOdds, inset + 2, lastHorizontalGirder + 1, chunk.width - inset - 1);
			return true;
		}
		return false;
	}
	
	private final static double decayedEdgeOdds = 0.20;
	
	private void decayEdge(CityWorldGenerator generator, int x, int y, int z) {
		if (chunkOdds.playOdds(decayedEdgeOdds)) {
			
			// make it go away
			generator.decayBlocks.desperseArea(x, y, z, 2 + chunkOdds.getRandomInt(2));
		}	
	}
	
	private void drawVerticalGirders(InitialBlocks chunk, int y1, int floorHeight) {
		int y2 = y1 + floorHeight;
		chunk.setBlocks(inset, y1, y2, inset, girderMaterial);
		chunk.setBlocks(inset, y1, y2, chunk.width - inset - 1, girderMaterial);
		chunk.setBlocks(chunk.width - inset - 1, y1, y2, inset, girderMaterial);
		chunk.setBlocks(chunk.width - inset - 1, y1, y2, chunk.width - inset - 1, girderMaterial);
	}

	private void drawHorizontalGirders(InitialBlocks chunk, int y1, Surroundings neighbors) {
		int x1 = neighbors.toWest() ? 0 : inset;
		int x2 = neighbors.toEast() ? chunk.width - 1 : chunk.width - inset - 1;
		int z1 = neighbors.toNorth() ? 0 : inset;
		int z2 = neighbors.toSouth() ? chunk.width - 1 : chunk.width - inset - 1;
		int i1 = inset;
		int i2 = chunk.width - inset - 1;
		
		chunk.setBlocks(x1, x2 + 1, y1, y1 + 1, i1, i1 + 1, girderMaterial);
		chunk.setBlocks(x1, x2 + 1, y1, y1 + 1, i2, i2 + 1, girderMaterial);
		chunk.setBlocks(i1, i1 + 1, y1, y1 + 1, z1, z2 + 1, girderMaterial);
		chunk.setBlocks(i2, i2 + 1, y1, y1 + 1, z1, z2 + 1, girderMaterial);
	}
	
}
