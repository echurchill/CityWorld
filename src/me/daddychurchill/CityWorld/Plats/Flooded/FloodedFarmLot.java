package me.daddychurchill.CityWorld.Plats.Flooded;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Rural.FarmLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider_Flooded;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class FloodedFarmLot extends FarmLot {

	public FloodedFarmLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FloodedFarmLot(platmap, chunkX, chunkZ);
	}

	@Override
	protected Material getNormalCrop() {
		return cropNone;
	}

	@Override
	protected Material getDecayedNormalCrop() {
		return cropNone;
	}

	@Override
	protected Material getNetherCrop() {
		return cropNone;
	}

	@Override
	protected Material getDecayedNetherCrop() {
		return cropNone;
	}
	
	@Override
	protected Material getAirMaterial(WorldGenerator generator, int y) {
		return ShapeProvider_Flooded.floodMaterial;
	}
}
