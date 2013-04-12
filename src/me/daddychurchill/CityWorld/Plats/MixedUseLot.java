package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.Buildings.ApartmentBuildingLot;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class MixedUseLot extends ApartmentBuildingLot {

	public MixedUseLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomProvider roomProviderForFloor(WorldGenerator generator, int floor) {
		if (floor > height / 3)
			return super.roomProviderForFloor(generator, floor);
		else
			return generator.roomProvider_Office;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new MixedUseLot(platmap, chunkX, chunkZ);
	}

}
