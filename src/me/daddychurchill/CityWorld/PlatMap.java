package me.daddychurchill.CityWorld;

import java.util.Random;
import me.daddychurchill.CityWorld.Context.PlatMapContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatNature;
import me.daddychurchill.CityWorld.Plats.PlatOfficeBuilding;
import me.daddychurchill.CityWorld.Plats.PlatPark;
import me.daddychurchill.CityWorld.Plats.PlatRoad;
import me.daddychurchill.CityWorld.Plats.PlatRoadPaved;
import me.daddychurchill.CityWorld.Plats.PlatStatue;
import me.daddychurchill.CityWorld.Plats.PlatUnfinishedBuilding;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;
import me.daddychurchill.CityWorld.Support.SupportChunk;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class PlatMap {
	
	/* On top of tall mountains put...
	 *    Radio towers
	 *    Telescopes
	 * Inside mountains put...
	 *    Mines
	 *    Bunkers
	 * On top of deep seas put...
	 *    Drilling platforms
	 * Isolated spots of buildable land...
	 *    Houses without roads
	 *    Residential 
	 *    
	 * On top of seas put...
	 *    Boats
	 * Under the sea put...
	 *    Submarines
	 */
	
	// Class Constants
	static final public int Width = 10;
	
	// Instance data
	public World world;
	public WorldGenerator generator;
	public int originX;
	public int originZ;
	public PlatMapContext context;
	public PlatLot[][] platLots;
	private int buildablePlats;

	public PlatMap(WorldGenerator aGenerator, SupportChunk typicalChunk, PlatMapContext aContext, int aOriginX, int aOriginZ) {
		super();
		
		// log.info(String.format("PM: %d x %d create", platX, platZ));

		// populate the instance data
		world = typicalChunk.world;
		generator = aGenerator;
		context = aContext;
		originX = aOriginX;
		originZ = aOriginZ;

		// make room for plat data
		platLots = new PlatLot[Width][Width];
		buildablePlats = Width * Width;
		
		// sprinkle nature, roads and buildings
		populateNature(typicalChunk);
		
		//TODO calculate the platmapcontext
		
		// place the roads and buildings
		populateRoads(typicalChunk);
		populateBuildings(typicalChunk);
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
					if (!generator.isBuildableAt(blockX, blockZ)) {
						platLots[x][z] = new PlatNature(random, this);
						buildablePlats--;
					}
				}
			}
		}
		
		CityWorld.log.info("Buildable plats = " + buildablePlats);
	}
	
	private void populateRoads(SupportChunk typicalChunk) {
		
		// place the big four
		placeIntersection(typicalChunk, PlatRoad.PlatMapRoadInset - 1, PlatRoad.PlatMapRoadInset - 1);
		placeIntersection(typicalChunk, PlatRoad.PlatMapRoadInset - 1, Width - PlatRoad.PlatMapRoadInset);
		placeIntersection(typicalChunk, Width - PlatRoad.PlatMapRoadInset, PlatRoad.PlatMapRoadInset - 1);
		placeIntersection(typicalChunk, Width - PlatRoad.PlatMapRoadInset, Width - PlatRoad.PlatMapRoadInset);
		
//		// place NW intersection 
//		// place NE intersection 
//		// place SW intersection 
//		// place SE intersection 
//		
//		// for each intersection
//		//   if ALL the surrounding chunks are NOT natural then POSSIBLY place a round about
//		//   if ONE or MORE roads connect this to another intersection then this intersection is 
//		
//		// 
//		
//		// for each cardinal direction see if there is a road there
//		boolean northroad = random.nextInt(context.oddsOfMissingRoad) != 0;
//		boolean southroad = random.nextInt(context.oddsOfMissingRoad) != 0;
//		boolean westroad = random.nextInt(context.oddsOfMissingRoad) != 0;
//		boolean eastroad = random.nextInt(context.oddsOfMissingRoad) != 0;
//
//		// calculate the roads
//		for (int i = 0; i < Width; i++) {
//			if (i < PlatRoad.PlatMapRoadInset || i >= Width - PlatRoad.PlatMapRoadInset) {
//				placePlatRoad(random, i, PlatRoad.PlatMapRoadInset - 1);
//				placePlatRoad(random, i, Width - PlatRoad.PlatMapRoadInset);
//				placePlatRoad(random, PlatRoad.PlatMapRoadInset - 1, i);
//				placePlatRoad(random, Width - PlatRoad.PlatMapRoadInset, i);
//			} else {
//				if (northroad)
//					placePlatRoad(random, i, Width - PlatRoad.PlatMapRoadInset);
//				if (southroad)
//					placePlatRoad(random, i, PlatRoad.PlatMapRoadInset - 1);
//				if (westroad)
//					placePlatRoad(random, PlatRoad.PlatMapRoadInset - 1, i);
//				if (eastroad)
//					placePlatRoad(random, Width - PlatRoad.PlatMapRoadInset, i);
//			}
//		}
//		if (random.nextInt(context.oddsOfRoundAbouts) == 0)
//			PlaceRoundAbout(random, PlatRoad.PlatMapRoadInset - 1, PlatRoad.PlatMapRoadInset - 1);
//		if (random.nextInt(context.oddsOfRoundAbouts) == 0)
//			PlaceRoundAbout(random, PlatRoad.PlatMapRoadInset - 1, Width - PlatRoad.PlatMapRoadInset);
//		if (random.nextInt(context.oddsOfRoundAbouts) == 0)
//			PlaceRoundAbout(random, Width - PlatRoad.PlatMapRoadInset, PlatRoad.PlatMapRoadInset - 1);
//		if (random.nextInt(context.oddsOfRoundAbouts) == 0)
//			PlaceRoundAbout(random, Width - PlatRoad.PlatMapRoadInset, Width - PlatRoad.PlatMapRoadInset);
	}
	
	private boolean isEmptyLot(int x, int z) {
		return platLots[x][z] == null;
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
					
					placePlatRoad(random, x - 1, z - 1);
					placePlatRoad(random, x - 1, z    );
					placePlatRoad(random, x - 1, z + 1);
					
					placePlatRoad(random, x    , z - 1);
					platLots[x][z] = new PlatStatue(random, this);
					placePlatRoad(random, x    , z + 1);
			
					placePlatRoad(random, x + 1, z - 1);
					placePlatRoad(random, x + 1, z    );
					placePlatRoad(random, x + 1, z + 1);
				
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
			placePlatRoad(random, x, z);
		if (roadToNorth) {
			placePlatRoad(random, x, z - 1);
			placePlatRoad(random, x, z - 2);
		}
		if (roadToSouth) {
			placePlatRoad(random, x, z + 1);
			placePlatRoad(random, x, z + 2);
		}
		if (roadToEast) {
			placePlatRoad(random, x + 1, z);
			placePlatRoad(random, x + 2, z);
		}
		if (roadToWest) {
			placePlatRoad(random, x - 1, z);
			placePlatRoad(random, x - 2, z);
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
	
	private void placePlatRoad(Random random, int x, int z) {
		platLots[x][z] = new PlatRoadPaved(random, this, generator.connectedKeyForPavedRoads);
	}
	
	private void populateBuildings(SupportChunk typicalChunk) {
		Random random = typicalChunk.random;
		
		// backfill with buildings and parks
		for (int x = 0; x < Width; x++) {
			for (int z = 0; z < Width; z++) {
				PlatLot current = platLots[x][z];
				if (current == null) {

					//TODO I need to come up with a more elegant way of doing this!
					// what to build?
					if (random.nextInt(context.oddsOfParks) == 0)
						current = new PlatPark(random, this, generator.connectedKeyForParks);
					else if (random.nextInt(context.oddsOfUnfinishedBuildings) == 0)
						current = new PlatUnfinishedBuilding(random, this);
					// houses
					// yards
					else
						current = new PlatOfficeBuilding(random, this);
					
					/* for each plot
					 *   randomly pick a plattype
					 *   see if the "previous chunk" is the same type
					 *     if so make the new plattype connected to the previous type
					 *   if the new plot is shorter than the previous plot or
					 *   if the new plot is shallower than the previous slot
					 *     mark the previous plot to have stairs
					 *   if plot does not have neighbors yet
					 *     mark the plot to have stairs
					 */

					// see if the previous chunk is the same type
					PlatLot previous = null;
					if (x > 0 && current.isConnectable(platLots[x - 1][z])) {
						previous = platLots[x - 1][z];
					} else if (z > 0 && current.isConnectable(platLots[x][z - 1])) {
						previous = platLots[x][z - 1];
					}
					
					// if there was a similar previous one then copy it... maybe
					if (previous != null && !previous.isIsolatedLot(random, context.oddsOfIsolatedLots)) {
						current.makeConnected(random, previous);
					}

					// remember what we did
					platLots[x][z] = current;
				}
			}
		}
	}
}
