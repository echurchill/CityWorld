package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import org.bukkit.generator.ChunkGenerator.BiomeGrid;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.Context.PlatMapContext;
import me.daddychurchill.CityWorld.Support.ByteChunk;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class PlatNature extends PlatLot {

	public PlatNature(Random rand, PlatMapContext context) {
		super(rand);
		
	}

	@Override
	public void generateChunk(PlatMap platmap, ByteChunk chunk, BiomeGrid biomes, PlatMapContext context, int platX, int platZ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void generateBlocks(PlatMap platmap, RealChunk chunk, PlatMapContext context, int platX, int platZ) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public long getConnectedKey() {
		return 0;
	}

	@Override
	public boolean makeConnected(Random rand, PlatLot relative) {
		return false;
	}

	@Override
	public boolean isConnectable(PlatLot relative) {
		return false;
	}

	@Override
	public boolean isIsolatedLot(int oddsOfIsolation) {
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
}
