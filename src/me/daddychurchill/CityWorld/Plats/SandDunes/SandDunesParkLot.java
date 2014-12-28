package me.daddychurchill.CityWorld.Plats.SandDunes;

import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Urban.ParkLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class SandDunesParkLot extends ParkLot {

	public SandDunesParkLot(PlatMap platmap, int chunkX, int chunkZ,
			long globalconnectionkey, int waterDepth) {
		super(platmap, chunkX, chunkZ, globalconnectionkey, waterDepth);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new SandDunesParkLot(platmap, chunkX, chunkZ, connectedkey, waterDepth);
	}
}
