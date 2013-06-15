package me.daddychurchill.CityWorld.Plats.SnowDunes;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Urban.ParkLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider_SandDunes;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class SnowDunesParkLot extends ParkLot {

	public SnowDunesParkLot(PlatMap platmap, int chunkX, int chunkZ,
			long globalconnectionkey) {
		super(platmap, chunkX, chunkZ, globalconnectionkey);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new SnowDunesParkLot(platmap, chunkX, chunkZ, connectedkey);
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
