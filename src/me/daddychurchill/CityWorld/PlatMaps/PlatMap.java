package me.daddychurchill.CityWorld.PlatMaps;

import java.util.Hashtable;
import java.util.Random;
import java.util.logging.Logger;

import me.daddychurchill.CityWorld.Context.ContextAllPark;
import me.daddychurchill.CityWorld.Context.ContextCityCenter;
import me.daddychurchill.CityWorld.Context.ContextHighrise;
import me.daddychurchill.CityWorld.Context.ContextLowrise;
import me.daddychurchill.CityWorld.Context.ContextMall;
import me.daddychurchill.CityWorld.Context.ContextMidrise;
import me.daddychurchill.CityWorld.Context.ContextUnfinished;
import me.daddychurchill.CityWorld.Context.ContextUrban;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.World;

public abstract class PlatMap {
	
	// debugging
	protected static Logger log = Logger.getLogger("Minecraft");

	// Class Constants
	static final public int Width = 10;
	static final public int FloorHeight = 4;
	static final public int FudgeFloorsBelow = 2;
	static final public int FudgeFloorsAbove = 4;
	static final public int AbsoluteMaximumFloorsBelow = 4;
	static final public int StreetLevel = FloorHeight * (AbsoluteMaximumFloorsBelow + FudgeFloorsBelow);
	static final public int AbsoluteMaximumFloorsAbove = (RealChunk.Height - StreetLevel) / FloorHeight - FudgeFloorsAbove; 
	
	// Instance data
	public World theWorld;
	public int X;
	public int Z;
	public Random platRand;
	public ContextUrban context;
	public PlatLot[][] platLots;

	public PlatMap(World world, Random random, ContextUrban context, int platX, int platZ) {
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
	}

	public void generateChunk(ByteChunk chunk) {

		// depending on the platchunk's type render a layer
		int platX = chunk.X - X;
		int platZ = chunk.Z - Z;
		PlatLot platlot = platLots[platX][platZ];
		if (platlot != null) {

			// do what we came here for
			platlot.generateChunk(this, chunk, context, platX, platZ);
		}
	}

	public void generateBlocks(RealChunk chunk) {

		// depending on the platchunk's type render a layer
		int platX = chunk.X - X;
		int platZ = chunk.Z - Z;
		PlatLot platlot = platLots[platX][platZ];
		if (platlot != null) {

			// do what we came here for
			platlot.generateBlocks(this, chunk, context, platX, platZ);
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
			
			// what is the context for this one?
			ContextUrban context = getContext(world, random, chunkX, chunkZ);
			
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
				platmap = new PlatMapCity(world, random, context, platX, platZ);
				break;
			default:
				//case HELL:
				//case SKY:
				platmap = new PlatMapBiome(world, random, context, platX, platZ);
				break;
			}
			
			// remember it for quicker look up
			platmaps.put(platkey, platmap);
		}
		
		// finally return the plat
		return platmap;
	}
	
	static private ContextUrban getContext(World world, Random random, int chunkX, int chunkZ) {
		switch (random.nextInt(20)) {
		case 0:
		case 1:
		case 2:
		case 3:
			return new ContextLowrise(random);
		case 4:
		case 5:
		case 6:
		case 7:
			return new ContextMidrise(random);
		case 8:
		case 9:
		case 10:
			return new ContextHighrise(random);
		case 11:
		case 12:
			return new ContextAllPark(random);
		case 13:
		case 14:
			return new ContextMall(random);
		case 15:
		case 16:
		case 17:
			return new ContextCityCenter(random);
		case 18:
		case 19:
		default:
			return new ContextUnfinished(random);
		}
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
}
