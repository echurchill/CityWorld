package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.Material;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.Context.PlatMapContext;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

public abstract class PlatUrban extends PlatLot {

	protected Random rand;
	protected long connectedkey;
	
	protected static byte bedrockId = (byte) Material.BEDROCK.getId();
	protected static byte stoneId = (byte) Material.STONE.getId();
	protected static byte lavaId = (byte) Material.LAVA.getId();
	public static final int underworldLevel = 3;
	
	public PlatUrban(Random rand, PlatMapContext context) {
		super(rand);
		
		this.rand = rand;
		
		//TODO while this is relatively safe, I would feel better to have something airtight
		connectedkey = rand.nextLong();
	}

	@Override
	public void generateBlocks(PlatMap platmap, RealChunk chunk, PlatMapContext context, int platX, int platZ) {
		// default one does nothing!
	}
	
	@Override
	public long getConnectedKey() {
		return connectedkey;
	}
	
	@Override
	public boolean makeConnected(Random rand, PlatLot relative) {
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
	public boolean isIsolatedLot(int oddsOfIsolation) {
		return rand.nextInt(oddsOfIsolation) == 0;
	}

	@Override
	public boolean isConnected(PlatLot relative) {
		if (relative == null)
			return false;
		return connectedkey == relative.getConnectedKey();
	}
	
	protected void generateBedrock(ByteChunk byteChunk, PlatMapContext context, int uptoY) {
		
		// bottom of the bottom
		byteChunk.setLayer(0, bedrockId);
		
		// draw the underworld
		if (context.doUnderworld) {
		
			// the pillars of the world
			for (int x = 0; x < byteChunk.width; x++) {
				for (int z = 0; z < byteChunk.width; z++) {
					int x4 = x % 4;
					int z4 = z % 4;
					if ((x4 == 0 || x4 == 3) && (z4 == 0 || z4 == 3))
						byteChunk.setBlocks(x, 1, uptoY - 1, z, context.isolationId);
					else {
						int y = underworldLevel + rand.nextInt(2);
						if (rand.nextBoolean()) {
							if (rand.nextInt(context.oddsOfLavaDownBelow) == 0)
								byteChunk.setBlocks(x, 1, y + 1, z, lavaId);
							else {
								byteChunk.setBlocks(x, 1, y, z, pickIsolationOre(context));
								byteChunk.setBlock(x, y, z, stoneId);
							}
						} else
							byteChunk.setBlocks(x, 1, y + 1, z, stoneId);
					}
				}
			}
			
			// top of the bottom
			byteChunk.setLayer(uptoY - 1, context.isolationId);
		} else {
			
			// back fill with stone
			byteChunk.setLayer(1, uptoY - 1, stoneId);
		}
	}
	
	private static byte byteIron = (byte) Material.IRON_ORE.getId();
	private static byte byteCoal = (byte) Material.COAL_ORE.getId();
	private static byte byteGold = (byte) Material.GOLD_ORE.getId();
	private static byte byteLapis = (byte) Material.LAPIS_ORE.getId();
	private static byte byteDiamond = (byte) Material.DIAMOND_ORE.getId();
	private static byte byteRedstone = (byte) Material.REDSTONE_ORE.getId();
	
	private byte pickIsolationOre(PlatMapContext context) {
		if (context.doOresInUnderworld) {
			switch (rand.nextInt(30)) {
			// raw ores
			case 1:
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
				return byteIron;
			case 7:
			case 8:
			case 9:
			case 10:
			case 11:
			case 12:
				return byteCoal;
			case 13:
			case 14:
			case 15:
				return byteGold;
			case 16:
			case 17:
			case 18:
				return byteLapis;
			case 19:
			case 20:
				return byteDiamond;
			case 21:
			case 22:
				return byteRedstone;
			case 23:
			case 24:
			case 25:
			case 26:
				return lavaId;
			default:
				return context.isolationId;
			}
		} else
			return context.isolationId;
	}
}
