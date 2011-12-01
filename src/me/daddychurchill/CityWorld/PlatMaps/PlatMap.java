package me.daddychurchill.CityWorld.PlatMaps;

import java.util.Hashtable;
import java.util.Random;
import java.util.logging.Logger;

import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.ByteChunk;

import org.bukkit.World;

public abstract class PlatMap {
	
	// debugging
	protected static Logger log = Logger.getLogger("Minecraft");

	// Class Constants
	static final public int Width = 10;
	static final public int FloorHeight = 4;
	static final public int StreetLevel = FloorHeight * 6;
	
	/*
	public class Bla {
		public int oddsOfMissingRoad; // 1/n = odds of a road being missing
		public int oddsOfRoundAbouts; // 1/n = odds of an intersection being a roundabout
	
		public int oddsOfParks; // 1/n = odds of a plat being a park
		public int oddsOfSquaredParks; // 1/n = odds of a neighboring connected parks being rectangular (platwise)
		
		public int oddsOfIsolatedBuildings; // 1/n = odds of isolated buildings
		public int oddsOfIdenticalBuildingHeights; // 1/n = odds of buildings in neighboring plats are the same size
		public int oddsOfSimilarBuildingHeights; // 1/n = if not identical, how similar are the heights
		public int oddsOfSquaredBuildings; // 1/n = odds of neighboring connected buildings being rectangular (plat wise)
		public int oddsOfHiddenBuildings; // 1/n = odds buildings buried behind buildings 
		
		public int oddsOfRoundedCorners; // 1/n = odds of a building having rounded corners
		
		public int floorsTallestBuilding; // n = tallest building possible in a platmap in floors
		public int floorsShortestBuilding; // n = shortest building possible in a platmap in floors
		public int floorsDeepestBasement; // n = deepest basement possible in a platmap in floors
		
		public int oddsOfExtraHighFirstFloor; // 1/n = is the first floor extra tall
		public int oddsOfExtraHighFloors; // 1/n = are all the floors extra tall
		public int multiplierExtraHighFloor; // n = how high are the extra tall floors (even multiple of floor height)
		
		public int oddsOfColumnsForFirstFloor; // does the first floor have columns
		public int oddsOfColumnsForBuilding; // odds the building has lots of columns
		public int oddsOfColumnsToHeight; // if the building has lots of columns, maximum of height that might have columns
		
	}
	 */
	
	// Instance data
	public World theWorld;
	public int X;
	public int Z;
	public Random platRand;
	public PlatLot[][] platLots;

	public PlatMap(World world, Random random, int platX, int platZ) {
		super();
		// log.info(String.format("PM: %d x %d create", platX, platZ));

		// populate the instance data
		theWorld = world;
		X = platX;
		Z = platZ;
		platRand = new Random(world.getSeed() + (long) X * (long) Width + (long) Z);

		// make room for plat data
		platLots = new PlatLot[Width][Width];
	}

	public void generateChunk(ByteChunk byteChunk) {

		// depending on the platchunk's type render a layer
		int platX = byteChunk.X - X;
		int platZ = byteChunk.Z - Z;
		PlatLot platlot = platLots[platX][platZ];
		if (platlot != null) {

			// do what we came here for
			platlot.generateChunk(this, byteChunk, platX, platZ);
		}
	}

	// this function is designed for BlockPopulators...
	//    calling it else where will likely result in nulls being returned
	static public PlatLot getPlatLot(World world, Random random, int chunkX, int chunkZ) {
		PlatLot platlot = null;
		
		// try and find the lot handler for this chunk
		PlatMap platmap = getPlatMap(world, random, chunkX, chunkZ);
		if (platmap != null) {
			
			// calculate the right index
			int platX = chunkX - platmap.X;
			int platZ = chunkZ - platmap.Z;
			
			// see if there is something there yet
			platlot = platmap.platLots[platX][platZ];
		}
		
		// return what we got
		return platlot;
	}

	// ***********
	// Class level manager for handling the city plat maps collection
	static public PlatMap getPlatMap(World world, Random random, int chunkX, int chunkZ) {

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
			
			// figure out the biome for this platmap
			switch (world.getBiome(platX, platZ)) {
			case DESERT:			// industrial zone
			case EXTREME_HILLS:		// tall city
			case FOREST:			// neighborhood
			case FROZEN_OCEAN:		// winter ocean/lake side
			case FROZEN_RIVER:		// ???
			case ICE_DESERT:		// stark industrial zone
			case ICE_MOUNTAINS:		// stark tall city
			case ICE_PLAINS:		// apartments
			case MUSHROOM_ISLAND:	// ???
			case MUSHROOM_SHORE:	// ???
			case OCEAN:				// ocean/lake side
			case PLAINS:			// farm land
			case RAINFOREST:		// ???
			case RIVER:				// ???
			case SAVANNA:			// town
			case SEASONAL_FOREST:	// ???
			case SHRUBLAND:			// ???
			case SWAMPLAND:			// government
			case TAIGA:				// ???
			case TUNDRA:			// recreation
 				// for now do this
				platmap = new PlatMapCity(world, random, platX, platZ);
				break;
			default:
				//case HELL:
				//case SKY:
				platmap = new PlatMapBiome(world, random, platX, platZ);
				break;
			}
			
			// remember it for quicker look up
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
