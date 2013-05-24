package me.daddychurchill.CityWorld.Plats.Flooded;

import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Urban.OfficeBuildingLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider_Flooded;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class FloodedOfficeBuildingLot extends OfficeBuildingLot {

	public FloodedOfficeBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		airMaterial = ShapeProvider_Flooded.floodMat;
		airId = ShapeProvider_Flooded.floodId;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FloodedOfficeBuildingLot(platmap, chunkX, chunkZ);
	}

}
