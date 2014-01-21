package me.daddychurchill.CityWorld.Plats.SandDunes;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Rural.FarmLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class SandDunesFarmLot extends FarmLot {

	public SandDunesFarmLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		
		waterMaterial = Material.SAND;
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new SandDunesFarmLot(platmap, chunkX, chunkZ);
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
