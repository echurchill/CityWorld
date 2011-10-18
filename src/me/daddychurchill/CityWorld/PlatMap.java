package me.daddychurchill.CityWorld;

import java.util.Hashtable;
import java.util.Random;
import java.util.logging.Logger;

import org.bukkit.World;

public class PlatMap {
	// debugging
	protected Logger log = Logger.getLogger("Minecraft");

	// Constants
	static final public int Width = 10;
	static final public int FloorHeight = 4;
	static final public int StreetLevel = FloorHeight * 4;

	// Odds
	static final public int RoadOdds = 2; // roads are missing 1/2rd of the
											// time
	static final public int BuildingOdds = 6; // buildings are missing 1/6th of
												// the time
	static final public int IsolatedBuildingOdds = 3; // buildings are isolated
														// 1/3 of the time
	static final public int BuildingSimilarHeightOdds = 2; // building's various
														// chunks are about the same
														// height 1/2 of the time
	static final public int BuildingIdenticalHeightOdds = 2; // building's chunk height are identical 1/3 of the time
	// static final int RoundaboutOdds = 8; // roundabouts are created
	// 1/8 of the time

	// Instance data
	private int X;
	private int Z;
	public Random platRand;
	private int floorsMaximumAbove;
	private int floorsMaximumBelow;
	public PlatLot[][] platLots;

	public PlatMap(World world, Random random, int platX, int platZ) {
		super();
		//log.info(String.format("PM: %d x %d create", platX, platZ));

		// populate the instance data
		X = platX;
		Z = platZ;
		platRand = new Random(world.getSeed() + (long) X * (long) Width + (long) Z);
		
		// calculate the extremes for this plat
		floorsMaximumAbove = 3 + platRand.nextInt(3) * 4;
		floorsMaximumBelow = platRand.nextInt(3) + 1;

		// make room for plat data
		platLots = new PlatLot[Width][Width];

		// calculate the roads
		boolean northroad = platRand.nextInt(RoadOdds) != 0;
		boolean southroad = platRand.nextInt(RoadOdds) != 0;
		boolean westroad = platRand.nextInt(RoadOdds) != 0;
		boolean eastroad = platRand.nextInt(RoadOdds) != 0;
		for (int i = 0; i < Width; i++) {
			if (i < PlatRoad.PlatMapRoadInset || i >= Width - PlatRoad.PlatMapRoadInset ) {
				platLots[i][PlatRoad.PlatMapRoadInset - 1] = new PlatPavedRoad(platRand);
				platLots[i][Width - PlatRoad.PlatMapRoadInset] = new PlatPavedRoad(platRand);
				platLots[PlatRoad.PlatMapRoadInset - 1][i] = new PlatPavedRoad(platRand);
				platLots[Width - PlatRoad.PlatMapRoadInset][i] = new PlatPavedRoad(platRand);
			} else {
				if (northroad)
					platLots[i][Width - PlatRoad.PlatMapRoadInset] = new PlatPavedRoad(platRand);
				if (southroad)
					platLots[i][PlatRoad.PlatMapRoadInset - 1] = new PlatPavedRoad(platRand);
				if (westroad)
					platLots[PlatRoad.PlatMapRoadInset - 1][i] = new PlatPavedRoad(platRand);
				if (eastroad)
					platLots[Width - PlatRoad.PlatMapRoadInset][i] = new PlatPavedRoad(platRand);
			}
		}

		// TODO make some roundabouts?

		// TODO make this dependent on biomes (cities, towns, attractions,
		// neighborhoods, farms, etc.)
		// backfill with buildings and parks
		for (int x = 0; x < Width; x++) {
			for (int z = 0; z < Width; z++) {
				PlatLot current = platLots[x][z];
				if (current == null) {
					
					// what to build?
					//TODO need a way for the plat handlers to register themselves... somehow
					//TODO depending on the biome (and its surrounding biomes) we might want a specialized handler here
					//TODO parks and other such stuff
					if (platRand.nextInt(PlatMap.BuildingOdds) != 0)
						current = new PlatOfficeBuilding(platRand, floorsMaximumAbove, floorsMaximumBelow);
					else
						current = new PlatPark(platRand);

					// see if the previous chunk is the same type
					PlatLot previous = null;
					if (x > 0 && current.isConnectable(platLots[x - 1][z])) {
						previous = platLots[x - 1][z];
					} else if (z > 0 && current.isConnectable(platLots[x][z - 1])) {
						previous = platLots[x][z - 1];
					}
					
					//TODO debug
					//if (previous != null)
					//	log.info(String.format("PlatMap: Previous = %s", previous.getClass().getName()));

					// if there was a similar previous one then copy it... maybe
					if (previous != null && platRand.nextInt(IsolatedBuildingOdds) != 0) {
						current.makeConnected(platRand, previous);
					}
					
					// remember what we did
					platLots[x][z] = current;
				}
			}
		}
	}
	
	public void generateChunk(Chunk chunk) {
		// log.info(String.format("GC: %d x %d (%d x %d) is %s",
		// chunk.X, chunk.Z, chunk.X - X, chunk.Z - Z,
		// PlatChunks[chunk.X - X][chunk.Z - Z].above.toString()));

		// depending on the platchunk's type render a layer
		int platX = chunk.X - X;
		int platZ = chunk.Z - Z;
		PlatLot current = platLots[platX][platZ];
		if (current != null) {
			
			// do what we came here for
			current.generateChunk(this, chunk, platX, platZ);
		}

		//chunk.drawCoordinate(chunk.X - X, chunk.Z - Z, StreetLevel + MaximumFloors, chunk.X - X == 0 && chunk.Z - Z == 0);
	}
	
	// ***********
	// Class level manager for handling the city plat maps collection
	static public PlatMap getPlatMap(World world, Random random, int chunkX,
			int chunkZ) {

		// get the plat map collection
		if (platmaps == null) {
			platmaps = new Hashtable<Long, PlatMap>();
		}

		// find the origin for the plat
		int platX = calcOrigin(chunkX);
		int platZ = calcOrigin(chunkZ);

		// calculate the plat's key
		Long platkey = Long.valueOf(((long) platX * (long) Integer.MAX_VALUE + (long) platZ));

		// get the right plat
		PlatMap platmap = platmaps.get(platkey);

		// doesn't exist? then make it!
		if (platmap == null) {
			platmap = new PlatMap(world, random, platX, platZ);
			platmaps.put(platkey, platmap);
		}

		// finally return the plat
		return platmap;
	}

	// Class instance data
	static private Hashtable<Long, PlatMap> platmaps;

	// Supporting code used by getPlatMap
	static private int calcOrigin(int i) {
		if (i >= 0) {
			return i / Width * Width;
		} else {
			return -((Math.abs(i + 1) / Width * Width) + Width);
		}
	}
	// ***********
}
