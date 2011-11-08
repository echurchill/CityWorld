package me.daddychurchill.CityWorld.Plats;

import java.util.Random;
import java.util.logging.Logger;

import me.daddychurchill.CityWorld.PlatMaps.PlatMap;
import me.daddychurchill.CityWorld.Support.Chunk;

import org.bukkit.Material;

public abstract class PlatLot {
	//TODO debugging
	protected Logger log = Logger.getLogger("Minecraft");
	
	protected Random rand;
	protected long connectedkey;
	protected static byte bedrockId = (byte) Material.BEDROCK.getId();
	
	public PlatLot(Random rand) {
		super();
		this.rand = rand;
		
		//TODO while this is relatively safe, I would feel better to have something airtight
		connectedkey = rand.nextLong();
	}
	
	public abstract void generateChunk(PlatMap platmap, Chunk chunk, int platX, int platZ);
	
	public void makeConnected(Random rand, PlatLot relative) {
		connectedkey = relative.connectedkey;
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
	
	public boolean isConnectable(PlatLot relative) {
		return getClass().isInstance(relative);
	}

	public boolean isConnected(PlatLot relative) {
		return connectedkey == relative.connectedkey;
	}
	
	protected void generateBedrock(Chunk chunk, int uptoY) {
		chunk.setBlocks(0, Chunk.Width, 0, uptoY, 0, Chunk.Width, bedrockId);
	}
}
