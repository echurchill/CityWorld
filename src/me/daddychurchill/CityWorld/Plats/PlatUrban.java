package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Context.PlatMapContext;

public abstract class PlatUrban extends PlatLot {

	protected long connectedkey;
	
	protected static byte bedrockId = (byte) Material.BEDROCK.getId();
	protected static byte stoneId = (byte) Material.STONE.getId();
	protected static byte lavaId = (byte) Material.LAVA.getId();
	public static final int underworldLevel = 3;
	
	public PlatUrban(Random random, PlatMapContext context) {
		super(random);
		
		//TODO while this is relatively safe, I would feel better to have something airtight
		connectedkey = random.nextLong();
	}

//	@Override
//	public void generateBlocks(CityWorldChunkGenerator generator, PlatMap platmap, RealChunk chunk, PlatMapContext context, Random random, int platX, int platZ) {
//		// default one does nothing!
//	}
//	
	@Override
	public long getConnectedKey() {
		return connectedkey;
	}
	
	@Override
	public boolean makeConnected(Random random, PlatLot relative) {
		if (relative == null)
			return false;
		connectedkey = relative.getConnectedKey();
		return isConnected(relative);
	}
	
	@Override
	public boolean isConnectable(PlatLot relative) {
		if (relative == null)
			return false;
		return getClass().isInstance(relative);
	}
	
	@Override
	public boolean isIsolatedLot(Random random, int oddsOfIsolation) {
		return random.nextInt(oddsOfIsolation) == 0;
	}

	@Override
	public boolean isConnected(PlatLot relative) {
		if (relative == null)
			return false;
		return connectedkey == relative.getConnectedKey();
	}
}
