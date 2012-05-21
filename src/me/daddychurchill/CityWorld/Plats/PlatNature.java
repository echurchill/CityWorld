package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.Context.PlatMapContext;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class PlatNature extends PlatLot {

	public PlatNature(Random random, PlatMap platmap) {
		super(random);
		
	}

	@Override
	public long getConnectedKey() {
		return 0;
	}

	@Override
	public boolean makeConnected(Random random, PlatLot relative) {
		return false;
	}

	@Override
	public boolean isConnectable(PlatLot relative) {
		return false;
	}

	@Override
	public boolean isIsolatedLot(Random random, int oddsOfIsolation) {
		return false;
	}

	@Override
	public boolean isConnected(PlatLot relative) {
		return false;
	}

	@Override
	public PlatLot[][] getNeighborPlatLots(PlatMap platmap, int platX, int platZ, boolean onlyConnectedNeighbors) {
		return null;
	}

	@Override
	public void generateBlocks(WorldGenerator generator, PlatMap platmap, RealChunk chunk, PlatMapContext context, int platX, int platZ) {
		// TODO add foliage and underground fixtures
		
//		// compute offset to start of chunk
//		int blockX = chunk.chunkX * chunk.width;
//		int blockZ = chunk.chunkZ * chunk.width;
//		
//		// plant trees
//		
//		// plant grass or snow
//		for (int x = 0; x < chunk.width; x++) {
//			for (int z = 0; z < chunk.width; z++) {
//				int y = generator.findBlockY(blockX + x, blockZ + z);
//				
//				// is it snow or grass?
//				if (averageHeight >= chunk.snowlevel) {
//					
//				} else {
//					
//				}
//			}
//		}
	}
	
//	@Override
//	public void generateChunk(WorldGenerator generator, PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, PlatMapContext context, int platX, int platZ) {
//		//TODO turn on nature again!
//		chunk.setLayer(0, bedrockId);
//	}
}
