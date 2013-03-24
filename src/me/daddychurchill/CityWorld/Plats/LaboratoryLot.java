package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class LaboratoryLot extends LibraryLot {

	public LaboratoryLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomProvider roomProviderForFloor(WorldGenerator generator, int floor) {
		switch (floor % 3) {
		case 1:
			return generator.roomProvider_Library;
		case 2:
			return generator.roomProvider_Machines;
		default:
			return generator.roomProvider_Office;
		}
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new LaboratoryLot(platmap, chunkX, chunkZ);
	}
}
