package me.daddychurchill.CityWorld.Plats.SnowDunes;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Rural.FarmLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider_SandDunes;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class SnowDunesFarmLot extends FarmLot {

	public SnowDunesFarmLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new SnowDunesFarmLot(platmap, chunkX, chunkZ);
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
	protected byte getAirId(WorldGenerator generator, int y) {
		return ShapeProvider_SandDunes.floodId;
	}

	@Override
	protected Material getAirMaterial(WorldGenerator generator, int y) {
		return ShapeProvider_SandDunes.floodMat;
	}
}
