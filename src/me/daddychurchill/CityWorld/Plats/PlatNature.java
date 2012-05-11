package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.CityWorldChunkGenerator;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.Context.PlatMapContext;
import me.daddychurchill.CityWorld.Support.RealChunk;

public class PlatNature extends PlatLot {

	public PlatNature(Random random, PlatMapContext context) {
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
	public void generateBlocks(CityWorldChunkGenerator generator, PlatMap platmap, RealChunk chunk, PlatMapContext context, int platX, int platZ) {
		// TODO add foliage and underground fixtures
	}
}
