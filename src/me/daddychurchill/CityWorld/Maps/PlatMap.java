package me.daddychurchill.CityWorld.Maps;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Plats.RoadLot;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.HeightInfo;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public abstract class PlatMap {
	
	// Class Constants
	public static final int Width = 10;
	
	// Instance data
	public World world;
	public WorldGenerator generator;
	public int originX;
	public int originZ;
	public DataContext context;
	protected PlatLot[][] platLots;
	protected int naturalPlats;

	public PlatMap(WorldGenerator aGenerator, SupportChunk typicalChunk, int aOriginX, int aOriginZ) {
		super();
		
		// populate the instance data
		world = typicalChunk.world;
		generator = aGenerator;
		originX = aOriginX;
		originZ = aOriginZ;

		// make room for plat data
		platLots = new PlatLot[Width][Width];
		naturalPlats = 0;
		
		populateLots(typicalChunk);
	}
	
	protected abstract void populateLots(SupportChunk typicalChunk);
	protected abstract PlatLot createNaturalLot(int x, int z);
	protected abstract PlatLot createRoadLot(int x, int z, boolean roundaboutPart);
	protected abstract PlatLot createRoundaboutStatueLot(int x, int z);

	public Random getRandomGenerator() {
		return generator.shapeProvider.getMacroRandomGeneratorAt(originX, originZ);
	}
	
	public Random getChunkRandomGenerator(SupportChunk chunk) {
		return generator.shapeProvider.getMicroRandomGeneratorAt(chunk.chunkX, chunk.chunkZ);
	}
	
	public Random getChunkRandomGenerator(int chunkX, int chunkZ) {
		return generator.shapeProvider.getMicroRandomGeneratorAt(chunkX, chunkZ);
	}
	
	public void generateChunk(ByteChunk chunk, BiomeGrid biomes) {

		// depending on the platchunk's type render a layer
		int platX = chunk.chunkX - originX;
		int platZ = chunk.chunkZ - originZ;
		
		PlatLot platlot = platLots[platX][platZ];
		if (platlot != null) {

			// do what we came here for
			platlot.generateChunk(generator, this, chunk, biomes, context, platX, platZ);
		}
	}
	
	public void generateBlocks(RealChunk chunk) {

		// depending on the platchunk's type render a layer
		int platX = chunk.chunkX - originX;
		int platZ = chunk.chunkZ - originZ;
		PlatLot platlot = platLots[platX][platZ];
		if (platlot != null) {

			// do what we came here for
			platlot.generateBlocks(generator, this, chunk, context, platX, platZ);
		}
	}
	
	public int getNumberOfRoads() {
		int result = 0;
		for (int x = 0; x < Width; x++) {
			for (int z = 0; z < Width; z++) {
				if (platLots[x][z] != null && platLots[x][z].style == LotStyle.ROAD)
					result++;
			}
		}
		return result;
	}
	
	public PlatLot getLot(int x, int z) {
		return platLots[x][z];
	}
	
	public boolean isEmptyLot(int x, int z) {
		if (x >= 0 && x < Width && z >= 0 && z < Width)
			return platLots[x][z] == null;
		else
			return true;
	}
	
	public boolean isNaturalLot(int x, int z) {
		if (x >= 0 && x < Width && z >= 0 && z < Width)
			return platLots[x][z] == null || platLots[x][z].style == LotStyle.NATURE;
		else
			return true;
	}
	
	public boolean isStructureLot(int x, int z) {
		if (x >= 0 && x < Width && z >= 0 && z < Width)
			return platLots[x][z] != null && platLots[x][z].style == LotStyle.STRUCTURE;
		else
			return false;
	}
	
	public void recycleLot(int x, int z) {

		// if it is not natural, make it so
		PlatLot current = platLots[x][z];
		if (current == null || current.style != LotStyle.NATURE) {
		
			// place nature
			platLots[x][z] = createNaturalLot(x, z);
			naturalPlats++;
		}
	}
	
	public boolean paveLot(int x, int z, boolean roundaboutPart) {
		boolean result = generator.settings.inRoadRange(originX + x, originZ + z);
		if (result && (platLots[x][z] == null || roundaboutPart || platLots[x][z].style != LotStyle.ROAD)) {
			
			// clear it please
			emptyLot(x, z);
			
			// place the lot
			platLots[x][z] = createRoadLot(x, z, roundaboutPart);
		}
		return result;
	}
	
	public boolean setLot(int x, int z, PlatLot lot) {
		boolean result = lot.isPlaceableAt(generator, originX + x, originZ + z);
		if (result) {
			
			// clear it please
			emptyLot(x, z);
			
			// place the lot
			platLots[x][z] = lot;
		} 
		return result;
	}
	
	public void emptyLot(int x, int z) {
		
		// keep track of the nature count
		PlatLot current = platLots[x][z];
		if (current != null && current.style == LotStyle.NATURE)
			naturalPlats--;
		
		// empty this one out
		platLots[x][z] = null;
	}
	
	protected void validateRoads(SupportChunk typicalChunk) {
		
		// any roads leading out?
		if (!(isRoad(0, RoadLot.PlatMapRoadInset - 1) ||
			  isRoad(0, Width - RoadLot.PlatMapRoadInset) ||
			  isRoad(Width - 1, RoadLot.PlatMapRoadInset - 1) ||
			  isRoad(Width - 1, Width - RoadLot.PlatMapRoadInset) ||
			  isRoad(RoadLot.PlatMapRoadInset - 1, 0) ||
			  isRoad(Width - RoadLot.PlatMapRoadInset, 0) ||
			  isRoad(RoadLot.PlatMapRoadInset - 1, Width - 1) ||
			  isRoad(Width - RoadLot.PlatMapRoadInset, Width - 1))) {
			
			// reclaim all of the silly roads
			for (int x = 0; x < Width; x++) {
				for (int z = 0; z < Width; z++) {
					this.recycleLot(x, z);
				}
			}
			
		} else {
			
			//TODO any other validation?
		}
	}
	
	protected boolean isRoad(int x, int z) {
		PlatLot current = platLots[x][z];
		return current != null && (current.style == LotStyle.ROAD || current.style == LotStyle.ROUNDABOUT);
	}
	
	public boolean isExistingRoad(int x, int z) {
		if (x >= 0 && x < Width && z >= 0 && z < Width)
			return isRoad(x, z);
		else
			return false;
	}

	protected void placeIntersection(SupportChunk typicalChunk, int x, int z) {
		boolean roadToNorth = false, roadToSouth = false, 
				roadToEast = false, roadToWest = false, 
				roadHere = false;
		
		// is there a road here?
		if (isEmptyLot(x, z)) {
		
			// are there roads from here?
			roadToNorth = isRoadTowards(typicalChunk, x, z, 0, -5);
			roadToSouth = isRoadTowards(typicalChunk, x, z, 0, 5);
			roadToEast = isRoadTowards(typicalChunk, x, z, 5, 0);
			roadToWest = isRoadTowards(typicalChunk, x, z, -5, 0);
			
			// is there a need for this intersection?
			if (roadToNorth || roadToSouth || roadToEast || roadToWest) {
			
				// are the odds in favor of a roundabout? AND..
				// are all the surrounding chunks empty (connecting roads shouldn't be there yet)
				if (generator.settings.includeRoundabouts && 
					generator.shapeProvider.isRoundaboutAt(originX + x, originZ + z) &&
					isEmptyLot(x - 1, z - 1) && isEmptyLot(x - 1, z) &&	isEmptyLot(x - 1, z + 1) &&
					isEmptyLot(x, z - 1) &&	isEmptyLot(x, z + 1) &&
					isEmptyLot(x + 1, z - 1) &&	isEmptyLot(x + 1, z) &&	isEmptyLot(x + 1, z + 1)) {
					
					paveLot(x - 1, z - 1, true);
					paveLot(x - 1, z    , true);
					paveLot(x - 1, z + 1, true);
					
					paveLot(x    , z - 1, true);
					setLot(x, z, createRoundaboutStatueLot(originX + x, originZ + z));
					paveLot(x    , z + 1, true);
			
					paveLot(x + 1, z - 1, true);
					paveLot(x + 1, z    , true);
					paveLot(x + 1, z + 1, true);
				
				// place the intersection then
				} else {
					roadHere = true;
				}
			}
		
		// now figure out if we are within a bridge/tunnel
		} else {
			
			// are there roads from here?
			if (isBridgeTowardsNorth(typicalChunk, x, z) &&
				isBridgeTowardsSouth(typicalChunk, x, z)) {
				roadToNorth = true;
				roadToSouth = true;
				roadHere = true;
				
			} else if (isBridgeTowardsEast(typicalChunk, x, z) && 
					   isBridgeTowardsWest(typicalChunk, x, z)) {
				roadToEast = true;
				roadToWest = true;
				roadHere = true;
			}
		}
		
		// now place any remaining roads we need
		if (roadHere)
			paveLot(x, z, false);
		if (roadToNorth) {
			paveLot(x, z - 1, false);
			paveLot(x, z - 2, false);
		}
		if (roadToSouth) {
			paveLot(x, z + 1, false);
			paveLot(x, z + 2, false);
		}
		if (roadToEast) {
			paveLot(x + 1, z, false);
			paveLot(x + 2, z, false);
		}
		if (roadToWest) {
			paveLot(x - 1, z, false);
			paveLot(x - 2, z, false);
		}
	}
	
	//TODO Why is this code using typicalChunk.width??????
	private boolean isRoadTowards(SupportChunk typicalChunk, int x, int z, int deltaX, int deltaZ) {
		
		// is this a "real" spot?
		boolean result = HeightInfo.isBuildableAt(generator, (originX + x + deltaX) * typicalChunk.width,
									   			 			 (originZ + z + deltaZ) * typicalChunk.width);
		
		// if this isn't a buildable spot, is there a bridge or tunnel that gets us there?
		if (!result)
			result = isBridgeTowards(typicalChunk, x, z, deltaX, deltaZ);
		
		// report back
		return result;
	}
	
	public boolean isBridgeTowardsNorth(SupportChunk typicalChunk, int x, int z) {
		return isBridgeTowards(typicalChunk, x, z, 0, -5);
	}
	
	public boolean isBridgeTowardsSouth(SupportChunk typicalChunk, int x, int z) {
		return isBridgeTowards(typicalChunk, x, z, 0, 5);
	}
	
	public boolean isBridgeTowardsWest(SupportChunk typicalChunk, int x, int z) {
		return isBridgeTowards(typicalChunk, x, z, -5, 0);
	}
	
	public boolean isBridgeTowardsEast(SupportChunk typicalChunk, int x, int z) {
		return isBridgeTowards(typicalChunk, x, z, 5, 0);
	}
	
	//TODO Why is this code using typicalChunk.width??????
	private boolean isBridgeTowards(SupportChunk typicalChunk, int x, int z, int deltaX, int deltaZ) {
		
		// how far do we go?
		int offsetX = deltaX * typicalChunk.width;
		int offsetZ = deltaZ * typicalChunk.width;
		
		// where do we test?
		int chunkX = (originX + x) * typicalChunk.width;
		int chunkZ = (originZ + z) * typicalChunk.width;
		
		// what is the polarity of this spot
		boolean originPolarity = generator.shapeProvider.getBridgePolarityAt(chunkX, chunkZ);
		boolean currentPolarity = originPolarity;
		
		// short cut things a bit by looking for impossible things (polarity doesn't match the delta values)
		if (originPolarity) {
			if (deltaX != 0)
				return false;
		} else {
			if (deltaZ != 0)
				return false;
		}
		
		// keep searching in the delta direction until polarity shifts
		while (originPolarity == currentPolarity) {
			
			// move it along a bit
			chunkX += offsetX;
			chunkZ += offsetZ;
			
			//TODO should test for a maximum length of bridge/tunnel
			
			// keep going as long it is the same polarity
			currentPolarity = generator.shapeProvider.getBridgePolarityAt(chunkX, chunkZ);
			
			// did we found a "real" spot and the polarity is still the same
			if (currentPolarity == originPolarity && HeightInfo.isBuildableAt(generator, chunkX, chunkZ))
				return true;
		};
		
		// we have failed to find a real bridge/tunnel
		return false;
	}
}
