package me.daddychurchill.CityWorld.Plats;

import me.daddychurchill.CityWorld.Maps.PlatMap;

public class WarehouseLot extends FinishedBuildingLot {

	public WarehouseLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		firstFloorHeight = firstFloorHeight * 2;
		height = 1;
//		depth = 0;
		roofStyle = platmapOdds.flipCoin() ? RoofStyle.EDGED : RoofStyle.FLATTOP;
		roofFeature = roofFeature == RoofFeature.ANTENNAS ? RoofFeature.CONDITIONERS : roofFeature;
	}
}
