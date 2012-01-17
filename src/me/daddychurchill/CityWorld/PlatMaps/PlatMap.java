package me.daddychurchill.CityWorld.PlatMaps;

import java.util.Random;
import java.util.logging.Logger;

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

}
