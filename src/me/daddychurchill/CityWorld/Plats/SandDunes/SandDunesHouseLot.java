package me.daddychurchill.CityWorld.Plats.SandDunes;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Rural.HouseLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider_SandDunes;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class SandDunesHouseLot extends HouseLot {

	public SandDunesHouseLot(PlatMap platmap, int chunkX, int chunkZ) {
		super(platmap, chunkX, chunkZ);
		// TODO Auto-generated constructor stub
	}


	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new SandDunesHouseLot(platmap, chunkX, chunkZ);
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
