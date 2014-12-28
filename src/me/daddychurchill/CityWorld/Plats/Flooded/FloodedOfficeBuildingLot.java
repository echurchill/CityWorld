package me.daddychurchill.CityWorld.Plats.Flooded;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Urban.OfficeBuildingLot;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider_Flooded;
import me.daddychurchill.CityWorld.Rooms.Populators.EmptyWithNothing;
import me.daddychurchill.CityWorld.Rooms.Populators.EmptyWithRooms;
import me.daddychurchill.CityWorld.Support.PlatMap;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class FloodedOfficeBuildingLot extends OfficeBuildingLot {

	public FloodedOfficeBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		floodY = platmap.generator.shapeProvider.findLowestFloodY(platmap.generator);
	}

	private static RoomProvider contentsEmpty = new EmptyWithNothing();
	private static RoomProvider contentsWalls = new EmptyWithRooms();
	private int floodY;
	
	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FloodedOfficeBuildingLot(platmap, chunkX, chunkZ);
	}

	@Override
	public RoomProvider roomProviderForFloor(CityWorldGenerator generator, SupportChunk chunk, int floor, int floorY) {
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
	
	@Override
	protected Material getAirMaterial(CityWorldGenerator generator, int y) {
		if (y < floodY)
			return ShapeProvider_Flooded.floodMaterial;
		else
			return super.getAirMaterial(generator, y);
	}
}
