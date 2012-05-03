package me.daddychurchill.CityWorld;

import java.util.Random;
import java.util.logging.Logger;

import me.daddychurchill.CityWorld.Context.PlatMapContext;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatOfficeBuilding;
import me.daddychurchill.CityWorld.Plats.PlatPark;
import me.daddychurchill.CityWorld.Plats.PlatRoad;
import me.daddychurchill.CityWorld.Plats.PlatRoadPaved;
import me.daddychurchill.CityWorld.Plats.PlatStatue;
import me.daddychurchill.CityWorld.Plats.PlatUnfinishedBuilding;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator.BiomeGrid;

public class PlatMap {
	
	// debugging
	protected static Logger log = Logger.getLogger("Minecraft");

	// Class Constants
	static final public int Width = 10;
	
	// Instance data
	public World theWorld;
	public int X;
	public int Z;
	public Random platRand;
	public PlatMapContext context;
	public PlatLot[][] platLots;

	public PlatMap(World world, Random random, PlatMapContext context, int platX, int platZ) {
		super();
		// log.info(String.format("PM: %d x %d create", platX, platZ));

		// populate the instance data
		this.theWorld = world;
		this.context = context;
		this.X = platX;
		this.Z = platZ;
		this.platRand = new Random(world.getSeed() + (long) X * (long) Width + (long) Z);

		// make room for plat data
		platLots = new PlatLot[Width][Width];
		
		// sprinkle nature, roads and buildings
		populateRoads();
		populateBuildings();
	}

	public void generateChunk(ByteChunk chunk) {

		// depending on the platchunk's type render a layer
		int platX = chunk.chunkX - X;
		int platZ = chunk.chunkZ - Z;
		PlatLot platlot = platLots[platX][platZ];
		if (platlot != null) {

			// do what we came here for
			platlot.generateChunk(this, chunk, context, platX, platZ);
		}
	}
	
	public void generateBiome(ByteChunk chunk, BiomeGrid biomes) {

		// depending on the platchunk's type render a layer
		int platX = chunk.chunkX - X;
		int platZ = chunk.chunkZ - Z;
		PlatLot platlot = platLots[platX][platZ];
		if (platlot != null) {

			// do what we came here for
			platlot.generateBiomes(this, biomes, context, platX, platZ);
		}
	}

	public void generateBlocks(RealChunk chunk) {

		// depending on the platchunk's type render a layer
		int platX = chunk.chunkX - X;
		int platZ = chunk.chunkZ - Z;
		PlatLot platlot = platLots[platX][platZ];
		if (platlot != null) {

			// do what we came here for
			platlot.generateBlocks(this, chunk, context, platX, platZ);
		}
	}
	
	private void populateRoads() {
		
		// for each cardinal direction see if there is a road there
		boolean northroad = platRand.nextInt(context.oddsOfMissingRoad) != 0;
		boolean southroad = platRand.nextInt(context.oddsOfMissingRoad) != 0;
		boolean westroad = platRand.nextInt(context.oddsOfMissingRoad) != 0;
		boolean eastroad = platRand.nextInt(context.oddsOfMissingRoad) != 0;

		// calculate the roads
		for (int i = 0; i < Width; i++) {
			if (i < PlatRoad.PlatMapRoadInset || i >= Width - PlatRoad.PlatMapRoadInset) {
				placePlatRoad(i, PlatRoad.PlatMapRoadInset - 1);
				placePlatRoad(i, Width - PlatRoad.PlatMapRoadInset);
				placePlatRoad(PlatRoad.PlatMapRoadInset - 1, i);
				placePlatRoad(Width - PlatRoad.PlatMapRoadInset, i);
			} else {
				if (northroad)
					placePlatRoad(i, Width - PlatRoad.PlatMapRoadInset);
				if (southroad)
					placePlatRoad(i, PlatRoad.PlatMapRoadInset - 1);
				if (westroad)
					placePlatRoad(PlatRoad.PlatMapRoadInset - 1, i);
				if (eastroad)
					placePlatRoad(Width - PlatRoad.PlatMapRoadInset, i);
			}
		}
		
		// for each intersection see if a roundabout exists
		if (platRand.nextInt(context.oddsOfRoundAbouts) == 0)
			PlaceRoundAbout(PlatRoad.PlatMapRoadInset - 1, PlatRoad.PlatMapRoadInset - 1);
		if (platRand.nextInt(context.oddsOfRoundAbouts) == 0)
			PlaceRoundAbout(PlatRoad.PlatMapRoadInset - 1, Width - PlatRoad.PlatMapRoadInset);
		if (platRand.nextInt(context.oddsOfRoundAbouts) == 0)
			PlaceRoundAbout(Width - PlatRoad.PlatMapRoadInset, PlatRoad.PlatMapRoadInset - 1);
		if (platRand.nextInt(context.oddsOfRoundAbouts) == 0)
			PlaceRoundAbout(Width - PlatRoad.PlatMapRoadInset, Width - PlatRoad.PlatMapRoadInset);
	}
	
	private void PlaceRoundAbout(int x, int z) {
		placePlatRoad(x - 1, z - 1);
		placePlatRoad(x - 1, z    );
		placePlatRoad(x - 1, z + 1);
		
		placePlatRoad(x    , z - 1);
		platLots[x][z] = new PlatStatue(platRand, context);
		placePlatRoad(x    , z + 1);

		placePlatRoad(x + 1, z - 1);
		placePlatRoad(x + 1, z    );
		placePlatRoad(x + 1, z + 1);
	}
	
	private void placePlatRoad(int x, int z) {
		if (platLots[x][z] == null)
			platLots[x][z] = new PlatRoadPaved(platRand, context);
	}
	
	private void populateBuildings() {
		// backfill with buildings and parks
		for (int x = 0; x < Width; x++) {
			for (int z = 0; z < Width; z++) {
				PlatLot current = platLots[x][z];
				if (current == null) {

					//TODO I need to come up with a more elegant way of doing this!
					// what to build?
					if (platRand.nextInt(context.oddsOfParks) == 0)
						current = new PlatPark(platRand, context);
					else if (platRand.nextInt(context.oddsOfUnfinishedBuildings) == 0)
						current = new PlatUnfinishedBuilding(platRand, context);
					// houses
					// yards
					else
						current = new PlatOfficeBuilding(platRand, context);
					
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
					if (previous != null && !previous.isIsolatedLot(context.oddsOfIsolatedLots)) {
						current.makeConnected(platRand, previous);
					}

					// remember what we did
					platLots[x][z] = current;
				}
			}
		}
	}
}
