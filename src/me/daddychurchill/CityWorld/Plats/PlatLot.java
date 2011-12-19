package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.Context.ContextUrban;
import me.daddychurchill.CityWorld.PlatMaps.PlatMap;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

import org.bukkit.Material;

public abstract class PlatLot {
	protected Random rand;
	protected long connectedkey;
	
	protected static byte bedrockId = (byte) Material.BEDROCK.getId();
	protected static byte lavaId = (byte) Material.LAVA.getId();
	
	public PlatLot(Random rand, ContextUrban context) {
		super();
		this.rand = rand;
		
		//TODO while this is relatively safe, I would feel better to have something airtight
		connectedkey = rand.nextLong();
	}
	
	public abstract void generateChunk(PlatMap platmap, ByteChunk chunk, ContextUrban context, int platX, int platZ);
	
	public void generateBlocks(PlatMap platmap, RealChunk chunk, ContextUrban context, int platX, int platZ) {
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
	
	protected void generateBedrock(ByteChunk byteChunk, ContextUrban context, int uptoY) {
		
		// bottom of the bottom
		byteChunk.setLayer(0, bedrockId);
		
		// the pillars of the world
		for (int x = 0; x < ByteChunk.Width; x++) {
			for (int z = 0; z < ByteChunk.Width; z++) {
				int x4 = x % 4;
				int z4 = z % 4;
				if ((x4 == 0 || x4 == 3) && (z4 == 0 || z4 == 3))
					byteChunk.setBlocks(x, 1, uptoY - 1, z, bedrockId);
				else if (rand.nextBoolean()) {
					if (rand.nextInt(context.oddsOfLavaDownBelow) == 0)
						byteChunk.setBlock(x, 1, z, lavaId);
					else
						byteChunk.setBlock(x, 1, z, bedrockId);
				}
			}
		}
		
		// top of the bottom
		byteChunk.setLayer(uptoY - 1, bedrockId);
	}
}
