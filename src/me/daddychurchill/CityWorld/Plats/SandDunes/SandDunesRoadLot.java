package me.daddychurchill.CityWorld.Plats.SandDunes;

import org.bukkit.Material;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.RoadLot;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class SandDunesRoadLot extends RoadLot {

	public SandDunesRoadLot(PlatMap platmap, int chunkX, int chunkZ,
			long globalconnectionkey, boolean roundaboutPart) {
		super(platmap, chunkX, chunkZ, globalconnectionkey, roundaboutPart);
		// TODO Auto-generated constructor stub
	}


	@Override
	public PlatLot newLike(PlatMap platmap, int chunkX, int chunkZ) {
		return new SandDunesRoadLot(platmap, chunkX, chunkZ, connectedkey, roundaboutRoad);
	}
	
	@Override
	protected byte getAirId(WorldGenerator generator, int y) {
		return generator.shapeProvider.findFloodIdAt(generator, y);
	}

	@Override
	protected Material getAirMaterial(WorldGenerator generator, int y) {
		return generator.shapeProvider.findFloodMaterialAt(generator, y);
	}
	
	@Override
	protected byte getSidewalkId() {
		return (byte) Material.DOUBLE_STEP.getId();
	}
	
	@Override
	protected int getSidewalkLevel(WorldGenerator generator) {
		return generator.streetLevel;
	}
}
