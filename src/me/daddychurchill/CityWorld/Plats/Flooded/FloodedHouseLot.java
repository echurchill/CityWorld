package me.daddychurchill.CityWorld.Plats.Flooded;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Rural.HouseLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider_Flooded;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class FloodedHouseLot extends HouseLot {

	public FloodedHouseLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FloodedHouseLot(platmap, chunkX, chunkZ);
	}

	
	@Override
	protected byte getAirId(WorldGenerator generator, int y) {
		return ShapeProvider_Flooded.floodId;
	}

	@Override
	protected Material getAirMaterial(WorldGenerator generator, int y) {
		return ShapeProvider_Flooded.floodMat;
	}
}
