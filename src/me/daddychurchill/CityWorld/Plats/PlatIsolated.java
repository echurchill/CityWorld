package me.daddychurchill.CityWorld.Plats;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMap;

public abstract class PlatIsolated extends PlatLot {

	public PlatIsolated(Random random, PlatMap platmap, int chunkX, int chunkZ) {
		super(random);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public long getConnectedKey() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean makeConnected(Random random, PlatLot relative) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConnectable(PlatLot relative) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isIsolatedLot(Random random, int oddsOfIsolation) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isConnected(PlatLot relative) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public PlatLot[][] getNeighborPlatLots(PlatMap platmap, int platX, int platZ, boolean onlyConnectedNeighbors) {
		return null;
	}

}
