package me.daddychurchill.CityWorld.Plats.Flooded;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Urban.ParkLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider_Flooded;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class FloodedParkLot extends ParkLot {

	public FloodedParkLot(PlatMap platmap, int chunkX, int chunkZ,
			long globalconnectionkey) {
		super(platmap, chunkX, chunkZ, globalconnectionkey);

	}

	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new FloodedParkLot(platmap, chunkX, chunkZ, connectedkey);
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
