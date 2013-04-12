package me.daddychurchill.CityWorld.Plats.Buildings;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.FinishedBuildingLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class ApartmentBuildingLot extends FinishedBuildingLot {

	public ApartmentBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
	}

	@Override
	public RoomProvider roomProviderForFloor(WorldGenerator generator, int floor) {
		return generator.roomProvider_Apartment;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new ApartmentBuildingLot(platmap, chunkX, chunkZ);
	}

}
