package me.daddychurchill.CityWorld;

import java.util.Random;

import me.daddychurchill.CityWorld.Context.ContextFarm;
import me.daddychurchill.CityWorld.Context.ContextNature;
import me.daddychurchill.CityWorld.Context.ContextNeighborhood;
import me.daddychurchill.CityWorld.Context.ContextCityCenter;
import me.daddychurchill.CityWorld.Context.ContextHighrise;
import me.daddychurchill.CityWorld.Context.ContextLowrise;
import me.daddychurchill.CityWorld.Context.ContextMall;
import me.daddychurchill.CityWorld.Context.ContextMidrise;
import me.daddychurchill.CityWorld.Context.ContextUnconstruction;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Plats.PlatNature;
import me.daddychurchill.CityWorld.Plats.PlatRoad;
import me.daddychurchill.CityWorld.Plats.PlatRoadPaved;
import me.daddychurchill.CityWorld.Plats.PlatStatue;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class PlatMap {
	
	// Class Constants
	static final public int Width = 10;
	
	// Instance data
	public World world;
	public WorldGenerator generator;
	public int originX;
	public int originZ;
	public ContextData context;
	public PlatLot[][] platLots;
	private int naturalPlats;

	public PlatMap(WorldGenerator aGenerator, SupportChunk typicalChunk, int aOriginX, int aOriginZ) {
		super();
		
		// log.info(String.format("PM: %d x %d create", platX, platZ));

		// populate the instance data
		world = typicalChunk.world;
		generator = aGenerator;
		originX = aOriginX;
		originZ = aOriginZ;

		// make room for plat data
		platLots = new PlatLot[Width][Width];
		naturalPlats = 0;
		
		// assume everything is natural
		context = new ContextNature(generator.getPlugin(), typicalChunk);
		
		// sprinkle nature, roads and buildings
		populateNature(typicalChunk);
		
		// place the roads
		populateRoads(typicalChunk);
		
		// validate the roads
		validateRoads(typicalChunk);
		
		// recalculate the context based on the "natural-ness" of the platmap
		context = getContext(typicalChunk);
		
		// now let the context take over
		context.populateMap(generator, this, typicalChunk);
	}

	/* On top of tall mountains put...
	 *    Radio towers
	 *    Telescopes
	 * On hills/mountains put
	 *    Shacks
	 * Inside mountains put...
	 *    Mines
	 *    Bunkers
	 * On top of deep seas put...
	 *    Drilling platforms
	 * Isolated spots of buildable land...
	 *    Houses without roads
	 *    Residential 
	 *    Farms w/Farm house
	 */
	
	private ContextData getContext(SupportChunk typicalChunk) {
		CityWorld plugin = generator.getPlugin();
		
		// how natural is this platmap?
		if (naturalPlats == 0)
			return new ContextHighrise(plugin, typicalChunk);
		else if (naturalPlats < 15)
			return new ContextUnconstruction(plugin, typicalChunk);
		else if (naturalPlats < 25)
			return new ContextMidrise(plugin, typicalChunk);
		else if (naturalPlats < 40)
			return new ContextCityCenter(plugin, typicalChunk);
		else if (naturalPlats < 55)
			return new ContextMall(plugin, typicalChunk);
		else if (naturalPlats < 70)
			return new ContextLowrise(plugin, typicalChunk);
		else if (naturalPlats < 85)
			return new ContextNeighborhood(plugin, typicalChunk);
		else 
		if (naturalPlats < 95)
			return new ContextFarm(plugin, typicalChunk);
		else 
		if (naturalPlats < 100)
			return new ContextNeighborhood(plugin, typicalChunk);
		
		// otherwise just keep what we have
		else
			return context;
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
	
	private boolean isEmptyLot(int x, int z) {
		return platLots[x][z] == null;
	}
	
	public void recycleLot(Random random, int x, int z) {

		// if it is not natural, make it so
		PlatLot current = platLots[x][z];
		if (current == null || current.style != LotStyle.NATURE) {
		
			// place nature
			platLots[x][z] = new PlatNature(random, this, originX + x, originZ + z);
			naturalPlats++;
		}
	}
	
	public void paveLot(Random random, int x, int z) {

		// keep track of the nature count
		PlatLot current = platLots[x][z];
		if (current != null && current.style == LotStyle.NATURE)
			naturalPlats--;
		
		// place the road
		platLots[x][z] = new PlatRoadPaved(random, this, originX + x, originZ + z, generator.connectedKeyForPavedRoads);
	}
	
	private void populateNature(SupportChunk typicalChunk) {
		Random random = typicalChunk.random;
		
		// is this natural or buildable?
		for (int x = 0; x < Width; x++) {
			for (int z = 0; z < Width; z++) {
				PlatLot current = platLots[x][z];
				if (current == null) {
					
					// what is the world location of the lot?
					int blockX = (originX + x) * typicalChunk.width;
					int blockZ = (originZ + z) * typicalChunk.width;
					
					// is the center and corners at the wrong level?
					if (!generator.isBuildableAt(blockX, blockZ))
						recycleLot(random, x, z);
				}
			}
		}
	}
	
	private void populateRoads(SupportChunk typicalChunk) {
		
		// place the big four
		placeIntersection(typicalChunk, PlatRoad.PlatMapRoadInset - 1, PlatRoad.PlatMapRoadInset - 1);
		placeIntersection(typicalChunk, PlatRoad.PlatMapRoadInset - 1, Width - PlatRoad.PlatMapRoadInset);
		placeIntersection(typicalChunk, Width - PlatRoad.PlatMapRoadInset, PlatRoad.PlatMapRoadInset - 1);
		placeIntersection(typicalChunk, Width - PlatRoad.PlatMapRoadInset, Width - PlatRoad.PlatMapRoadInset);
	}
	
	private void placeIntersection(SupportChunk typicalChunk, int x, int z) {
		Random random = typicalChunk.random;
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
				if (generator.isRoundaboutAt(originX + x, originZ + z) &&
					isEmptyLot(x - 1, z - 1) && isEmptyLot(x - 1, z) &&	isEmptyLot(x - 1, z + 1) &&
					isEmptyLot(x, z - 1) &&	isEmptyLot(x, z + 1) &&
					isEmptyLot(x + 1, z - 1) &&	isEmptyLot(x + 1, z) &&	isEmptyLot(x + 1, z + 1)) {
					
					paveLot(random, x - 1, z - 1);
					paveLot(random, x - 1, z    );
					paveLot(random, x - 1, z + 1);
					
					paveLot(random, x    , z - 1);
					platLots[x][z] = new PlatStatue(random, this, originX + x, originZ + z);
					paveLot(random, x    , z + 1);
			
					paveLot(random, x + 1, z - 1);
					paveLot(random, x + 1, z    );
					paveLot(random, x + 1, z + 1);
				
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
			paveLot(random, x, z);
		if (roadToNorth) {
			paveLot(random, x, z - 1);
			paveLot(random, x, z - 2);
		}
		if (roadToSouth) {
			paveLot(random, x, z + 1);
			paveLot(random, x, z + 2);
		}
		if (roadToEast) {
			paveLot(random, x + 1, z);
			paveLot(random, x + 2, z);
		}
		if (roadToWest) {
			paveLot(random, x - 1, z);
			paveLot(random, x - 2, z);
		}
	}
	
	private boolean isRoadTowards(SupportChunk typicalChunk, int x, int z, int deltaX, int deltaZ) {
		
		// is this a "real" spot?
		boolean result = generator.isBuildableAt((originX + x + deltaX) * typicalChunk.width,
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
	
	private boolean isBridgeTowards(SupportChunk typicalChunk, int x, int z, int deltaX, int deltaZ) {
		
		// how far do we go?
		int offsetX = deltaX * typicalChunk.width;
		int offsetZ = deltaZ * typicalChunk.width;
		
		// where do we test?
		int chunkX = (originX + x) * typicalChunk.width;
		int chunkZ = (originZ + z) * typicalChunk.width;
		
		// what is the polarity of this spot
		boolean originPolarity = generator.getBridgePolarityAt(chunkX, chunkZ);
		boolean currentPolarity = originPolarity;
		
		// short cut things a bit by looking for impossible things
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
			
			// keep going as long it is the same polarity
			currentPolarity = generator.getBridgePolarityAt(chunkX, chunkZ);
			
			// did we found a "real" spot and the polarity is still the same
			if (currentPolarity == originPolarity && generator.isBuildableAt(chunkX, chunkZ))
				return true;
		};
		
		// we have failed to find a real bridge/tunnel
		return false;
	}
	
	private void validateRoads(SupportChunk typicalChunk) {
		
		// any roads leading out?
		if (!(isRoad(0, PlatRoad.PlatMapRoadInset - 1) ||
			  isRoad(0, Width - PlatRoad.PlatMapRoadInset) ||
			  isRoad(Width - 1, PlatRoad.PlatMapRoadInset - 1) ||
			  isRoad(Width - 1, Width - PlatRoad.PlatMapRoadInset) ||
			  isRoad(PlatRoad.PlatMapRoadInset - 1, 0) ||
			  isRoad(Width - PlatRoad.PlatMapRoadInset, 0) ||
			  isRoad(PlatRoad.PlatMapRoadInset - 1, Width - 1) ||
			  isRoad(Width - PlatRoad.PlatMapRoadInset, Width - 1))) {
			
			// reclaim all of the silly roads
			for (int x = 0; x < Width; x++) {
				for (int z = 0; z < Width; z++) {
					this.recycleLot(typicalChunk.random, x, z);
				}
			}
			
		} else {
			
			//TODO any other validation?
		}
	}
	
	private boolean isRoad(int x, int z) {
		PlatLot current = platLots[x][z];
		return current != null && (current.style == LotStyle.ROAD || current.style == LotStyle.ROUNDABOUT);
	}
}
