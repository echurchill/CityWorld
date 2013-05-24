package me.daddychurchill.CityWorld.Plats.Flooded;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Rural.FarmLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider_Flooded;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class FloodedFarmLot extends FarmLot {

	public FloodedFarmLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		airMaterial = ShapeProvider_Flooded.floodMat;
		airId = ShapeProvider_Flooded.floodId;
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
}
