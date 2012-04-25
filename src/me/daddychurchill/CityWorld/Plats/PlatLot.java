package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;

import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.Context.PlatMapContext;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

public abstract class PlatLot {

	public PlatLot(Random rand) {
		super();
	}
	
	public abstract void generateChunk(PlatMap platmap, ByteChunk chunk, PlatMapContext context, int platX, int platZ);
	public abstract void generateBiomes(PlatMap platmap, BiomeGrid biomes, PlatMapContext context, int platX, int platZ);
	public abstract void generateBlocks(PlatMap platmap, RealChunk chunk, PlatMapContext context, int platX, int platZ);
	
	public abstract long getConnectedKey();
	public abstract void makeConnected(Random rand, PlatLot relative);
	public abstract boolean isConnectable(PlatLot relative);
	public abstract boolean isIsolatedLot(int oddsOfIsolation);
	public abstract boolean isConnected(PlatLot relative);
	
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
	
}
