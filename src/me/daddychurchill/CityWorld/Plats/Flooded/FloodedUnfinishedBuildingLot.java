package me.daddychurchill.CityWorld.Plats.Flooded;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Urban.UnfinishedBuildingLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider_Flooded;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class FloodedUnfinishedBuildingLot extends UnfinishedBuildingLot {

	public FloodedUnfinishedBuildingLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FloodedUnfinishedBuildingLot(platmap, chunkX, chunkZ);
	}
	
	@Override
	protected byte getAirId(WorldGenerator generator, int y) {
		return ShapeProvider_Flooded.floodId;
	}

	@Override
	protected Material getAirMaterial(int y) {
		return ShapeProvider_Flooded.floodMat;
	}
}
