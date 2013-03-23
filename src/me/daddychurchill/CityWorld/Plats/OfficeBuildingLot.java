package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class OfficeBuildingLot extends FinishedBuildingLot {

	public OfficeBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);

	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new OfficeBuildingLot(platmap, chunkX, chunkZ);
	}

	@Override
	public RoomProvider roomProviderForFloor(WorldGenerator generator, int floor) {
		return generator.roomProvider_Office;
	}

}
