package me.daddychurchill.CityWorld.Plats.Urban;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.Populators.EmptyWithRooms;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class EmptyBuildingLot extends LibraryBuildingLot {

	private static RoomProvider contentsRooms = new EmptyWithRooms();

	public EmptyBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public RoomProvider roomProviderForFloor(CityWorldGenerator generator, SupportBlocks chunk, int floor, int floorY) {
		return contentsRooms;
	}
	
	@Override
	protected InteriorStyle getFloorsInteriorStyle(int floor) {
		int range = height / 4;
		if (floor < range)
			return InteriorStyle.WALLS_OFFICES;
		else if (floor < range * 2)
			return InteriorStyle.WALLS_ONLY;
		else if (floor < range * 3)
			return InteriorStyle.COLUMNS_OFFICES;
		else 
			return InteriorStyle.COLUMNS_ONLY;
	}
	
	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new EmptyBuildingLot(platmap, chunkX, chunkZ);
	}
}
