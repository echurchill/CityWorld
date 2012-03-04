package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.Context.PlatMapContext;
import me.daddychurchill.CityWorld.PlatMaps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;

public abstract class PlatLot {
	protected Random rand;
	protected long connectedkey;
	
	protected static byte bedrockId = (byte) Material.BEDROCK.getId();
	protected static byte stoneId = (byte) Material.STONE.getId();
	protected static byte lavaId = (byte) Material.LAVA.getId();
	
	public PlatLot(Random rand, PlatMapContext context) {
		super();
		this.rand = rand;
		
		//TODO while this is relatively safe, I would feel better to have something airtight
		connectedkey = rand.nextLong();
	}
	
	public abstract void generateChunk(PlatMap platmap, ByteChunk chunk, PlatMapContext context, int platX, int platZ);
	
	public void generateBlocks(PlatMap platmap, RealChunk chunk, PlatMapContext context, int platX, int platZ) {
		// default one does nothing!
	}
	
	public void makeConnected(Random rand, PlatLot relative) {
		connectedkey = relative.connectedkey;
	}
	
	public boolean isConnectable(PlatLot relative) {
		return getClass().isInstance(relative);
	}
	
	public boolean isIsolatedLot(int oddsOfIsolation) {
		return rand.nextInt(oddsOfIsolation) == 0;
	}

	public boolean isConnected(PlatLot relative) {
		return connectedkey == relative.connectedkey;
	}
	
	//TODO move this logic to SurroundingLots, add to it the ability to produce SurroundingHeights and SurroundingDepths
	public PlatLot[][] getNeighborPlatLots(PlatMap platmap, int platX, int platZ, boolean onlyConnectedNeighbors) {
		PlatLot[][] miniPlatMap = new PlatLot[3][3];
		
		// populate the results
		for (int x = 0; x < 3; x++) {
			for (int z = 0; z < 3; z++) {
				
				// which platchunk are we looking at?
				int atX = platX + x - 1;
				int atZ = platZ + z - 1;

				// is it in bounds?
				if (!(atX < 0 || atX > PlatMap.Width - 1 || atZ < 0 || atZ > PlatMap.Width - 1)) {
					PlatLot relative = platmap.platLots[atX][atZ];
					
					if (!onlyConnectedNeighbors || isConnected(relative)) {
						miniPlatMap[x][z] = relative;
					}
				}
			}
		}
		
		return miniPlatMap;
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
						int y = 2 + rand.nextInt(2);
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
				return byteIron;
			case 6:
			case 7:
			case 8:
			case 9:
			case 10:
				return byteCoal;
			case 11:
			case 12:
			case 13:
				return byteGold;
			case 14:
			case 15:
			case 16:
				return byteLapis;
			case 17:
			case 18:
				return byteDiamond;
			case 19:
			case 20:
				return byteRedstone;
			case 21:
			case 22:
			case 23:
			case 24:
			case 25:
				return lavaId;
			default:
				return context.isolationId;
			}
		} else
			return context.isolationId;
	}
}
