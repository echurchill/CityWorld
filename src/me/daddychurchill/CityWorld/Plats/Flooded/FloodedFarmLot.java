package me.daddychurchill.CityWorld.Plats.Flooded;

import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Rural.FarmLot;
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
	protected CropType setNormalCrop() {
		return CropType.FALLOW;
	}

	@Override
	protected CropType setDecayedNormalCrop() {
		return CropType.FALLOW;
	}

	@Override
	protected CropType setNetherCrop() {
		return CropType.FALLOW;
	}

	@Override
	protected CropType setDecayedNetherCrop() {
		return CropType.FALLOW;
	}
}
