package me.daddychurchill.CityWorld.Plats.SandDunes;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Urban.ParkLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class SandDunesParkLot extends ParkLot {

	public SandDunesParkLot(PlatMap platmap, int chunkX, int chunkZ,
			long globalconnectionkey) {
		super(platmap, chunkX, chunkZ, globalconnectionkey);
		// TODO Auto-generated constructor stub
	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new SandDunesParkLot(platmap, chunkX, chunkZ, connectedkey);
	}

	@Override
	protected byte getAirId(WorldGenerator generator, int y) {
		return generator.shapeProvider.findFloodIdAt(generator, y);
	}

	@Override
	protected Material getAirMaterial(WorldGenerator generator, int y) {
		return generator.shapeProvider.findFloodMaterialAt(generator, y);
	}
}
