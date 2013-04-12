package me.daddychurchill.CityWorld.Plats.Buildings;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.FinishedBuildingLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class FactoryLot extends FinishedBuildingLot {

	//TODO this guy needs chimneys and storage tanks on the roof
	
	public FactoryLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomProvider roomProviderForFloor(WorldGenerator generator, int floor) {
		return generator.roomProvider_Machines;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FactoryLot(platmap, chunkX, chunkZ);
	}

}
