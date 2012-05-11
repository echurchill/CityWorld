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
	public World theWorld;
	public int X;
	public int Z;
	public PlatMapContext context;
	public PlatLot[][] platLots;

	public PlatMap(CityWorldChunkGenerator generator, SupportChunk typicalChunk, PlatMapContext context, int platX, int platZ) {
		super();
		
		// log.info(String.format("PM: %d x %d create", platX, platZ));

		// populate the instance data
		this.theWorld = typicalChunk.theWorld;
		this.context = context;
		this.X = platX;
		this.Z = platZ;

		// make room for plat data
		platLots = new PlatLot[Width][Width];
		
		// sprinkle nature, roads and buildings
		populateNature(generator, typicalChunk);
		populateRoads(generator, typicalChunk);
		populateBuildings(generator, typicalChunk);
	}

	public void generateChunk(CityWorldChunkGenerator generator, ByteChunk chunk, BiomeGrid biomes) {

		// depending on the platchunk's type render a layer
		int platX = chunk.chunkX - X;
		int platZ = chunk.chunkZ - Z;
		
		PlatLot platlot = platLots[platX][platZ];
		if (platlot != null) {

			// do what we came here for
			platlot.generateChunk(generator, this, chunk, biomes, context, platX, platZ);
		}
	}
	
	public void generateBlocks(CityWorldChunkGenerator generator, RealChunk chunk) {

		// depending on the platchunk's type render a layer
		int platX = chunk.chunkX - X;
		int platZ = chunk.chunkZ - Z;
		PlatLot platlot = platLots[platX][platZ];
		if (platlot != null) {

			// do what we came here for
			platlot.generateBlocks(generator, this, chunk, context, platX, platZ);
		}
	}
	
	private void populateRoads(CityWorldChunkGenerator generator, SupportChunk typicalChunk) {
		Random random = typicalChunk.random;
		
		// for each cardinal direction see if there is a road there
		boolean northroad = random.nextInt(context.oddsOfMissingRoad) != 0;
		boolean southroad = random.nextInt(context.oddsOfMissingRoad) != 0;
		boolean westroad = random.nextInt(context.oddsOfMissingRoad) != 0;
		boolean eastroad = random.nextInt(context.oddsOfMissingRoad) != 0;

		// calculate the roads
		for (int i = 0; i < Width; i++) {
			if (i < PlatRoad.PlatMapRoadInset || i >= Width - PlatRoad.PlatMapRoadInset) {
				placePlatRoad(random, i, PlatRoad.PlatMapRoadInset - 1);
				placePlatRoad(random, i, Width - PlatRoad.PlatMapRoadInset);
				placePlatRoad(random, PlatRoad.PlatMapRoadInset - 1, i);
				placePlatRoad(random, Width - PlatRoad.PlatMapRoadInset, i);
			} else {
				if (northroad)
					placePlatRoad(random, i, Width - PlatRoad.PlatMapRoadInset);
				if (southroad)
					placePlatRoad(random, i, PlatRoad.PlatMapRoadInset - 1);
				if (westroad)
					placePlatRoad(random, PlatRoad.PlatMapRoadInset - 1, i);
				if (eastroad)
					placePlatRoad(random, Width - PlatRoad.PlatMapRoadInset, i);
			}
		}
		if (random.nextInt(context.oddsOfRoundAbouts) == 0)
			PlaceRoundAbout(random, PlatRoad.PlatMapRoadInset - 1, PlatRoad.PlatMapRoadInset - 1);
		if (random.nextInt(context.oddsOfRoundAbouts) == 0)
			PlaceRoundAbout(random, PlatRoad.PlatMapRoadInset - 1, Width - PlatRoad.PlatMapRoadInset);
		if (random.nextInt(context.oddsOfRoundAbouts) == 0)
			PlaceRoundAbout(random, Width - PlatRoad.PlatMapRoadInset, PlatRoad.PlatMapRoadInset - 1);
		if (random.nextInt(context.oddsOfRoundAbouts) == 0)
			PlaceRoundAbout(random, Width - PlatRoad.PlatMapRoadInset, Width - PlatRoad.PlatMapRoadInset);
	}
	
	private void PlaceRoundAbout(Random random, int x, int z) {
		if (platLots[x][z] == null) {
			placePlatRoad(random, x - 1, z - 1);
			placePlatRoad(random, x - 1, z    );
			placePlatRoad(random, x - 1, z + 1);
			
			placePlatRoad(random, x    , z - 1);
			platLots[x][z] = new PlatStatue(random, context);
			placePlatRoad(random, x    , z + 1);
	
			placePlatRoad(random, x + 1, z - 1);
			placePlatRoad(random, x + 1, z    );
			placePlatRoad(random, x + 1, z + 1);
		}
	}
	
	private void placePlatRoad(Random random, int x, int z) {
		if (platLots[x][z] == null)
			platLots[x][z] = new PlatRoadPaved(random, context);
	}
	
	private void populateNature(CityWorldChunkGenerator generator, SupportChunk typicalChunk) {
		Random random = typicalChunk.random;
		int streetlevel = typicalChunk.streetlevel;
		
		// is this natural or buildable?
		for (int x = 0; x < Width; x++) {
			for (int z = 0; z < Width; z++) {
				PlatLot current = platLots[x][z];
				if (current == null) {
					
					// what is the world location of the lot?
					int blockX = (X + x) * SupportChunk.chunksBlockWidth;
					int blockZ = (Z + z) * SupportChunk.chunksBlockWidth;
					
					// is the center and corners at the wrong level?
					if (!(generator.findBlockY(blockX + 8, blockZ + 8) == streetlevel && // center
						  generator.findBlockY(blockX + 0, blockZ + 0) == streetlevel && // corners
						  generator.findBlockY(blockX + 0, blockZ + 15) == streetlevel && 
						  generator.findBlockY(blockX + 15, blockZ + 0) == streetlevel && 
						  generator.findBlockY(blockX + 15, blockZ + 15) == streetlevel)) {
						platLots[x][z] = new PlatNature(random, context);
					}
				}
			}
		}
	}
	
	private void populateBuildings(CityWorldChunkGenerator generator, SupportChunk typicalChunk) {
		Random random = typicalChunk.random;
		
		// backfill with buildings and parks
		for (int x = 0; x < Width; x++) {
			for (int z = 0; z < Width; z++) {
				PlatLot current = platLots[x][z];
				if (current == null) {

					//TODO I need to come up with a more elegant way of doing this!
					// what to build?
					if (random.nextInt(context.oddsOfParks) == 0)
						current = new PlatPark(random, context);
					else if (random.nextInt(context.oddsOfUnfinishedBuildings) == 0)
						current = new PlatUnfinishedBuilding(random, context);
					// houses
					// yards
					else
						current = new PlatOfficeBuilding(random, context);
					
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
