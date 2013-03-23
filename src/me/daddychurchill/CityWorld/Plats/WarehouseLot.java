package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plugins.RoomProvider;

public class WarehouseLot extends FinishedBuildingLot {

	public WarehouseLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		firstFloorHeight = firstFloorHeight * 2;
		height = 1;
		depth = 0;
		roofStyle = platmapOdds.flipCoin() ? RoofStyle.EDGED : RoofStyle.FLATTOP;
		roofFeature = roofFeature == RoofFeature.ANTENNAS ? RoofFeature.CONDITIONERS : roofFeature;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new WarehouseLot(platmap, chunkX, chunkZ);
	}

	@Override
	public RoomProvider roomProviderForFloor(WorldGenerator generator, int floor) {
		return generator.roomProvider_Warehouse;
	}
	
}
