package me.daddychurchill.CityWorld.Plats.SnowDunes;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Rural.FarmLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class SnowDunesFarmLot extends FarmLot {

	public SnowDunesFarmLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		waterMaterial = Material.ICE;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new SnowDunesFarmLot(platmap, chunkX, chunkZ);
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
