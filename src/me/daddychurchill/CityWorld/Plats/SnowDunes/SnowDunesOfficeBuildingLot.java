package me.daddychurchill.CityWorld.Plats.SnowDunes;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Urban.OfficeBuildingLot;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Rooms.Populators.EmptyWithNothing;
import me.daddychurchill.CityWorld.Rooms.Populators.EmptyWithRooms;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportBlocks;

public class SnowDunesOfficeBuildingLot extends OfficeBuildingLot {

	public SnowDunesOfficeBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
	}

	private static RoomProvider contentsEmpty = new EmptyWithNothing();
	private static RoomProvider contentsWalls = new EmptyWithRooms();
	
	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new SnowDunesOfficeBuildingLot(platmap, chunkX, chunkZ);
	}

	@Override
	public RoomProvider roomProviderForFloor(CityWorldGenerator generator, SupportBlocks chunk, int floor, int floorY) {
		if (generator.shapeProvider.findFloodY(generator, chunk.getOriginX(), chunk.getOriginZ()) < floorY)
			return super.roomProviderForFloor(generator, chunk, floor, floorY);
		else {
			switch (contentStyle) {
			case OFFICES:
			case CUBICLES:
				return contentsWalls;
			case RANDOM:
				if (chunkOdds.flipCoin())
					return contentsWalls;
				else
					return contentsEmpty;
			default:
				return contentsEmpty;
			}
		}
	}
}
