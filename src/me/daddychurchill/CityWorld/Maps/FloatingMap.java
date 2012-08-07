package me.daddychurchill.CityWorld.Maps;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class FloatingMap extends PlatMap {

	public FloatingMap(WorldGenerator aGenerator, SupportChunk typicalChunk, int aOriginX, int aOriginZ) {
		super(aGenerator, typicalChunk, aOriginX, aOriginZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void populateLots(SupportChunk typicalChunk) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected PlatLot createNaturalLot(int chunkX, int chunkZ) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected PlatLot createRoadLot(int chunkX, int chunkZ,
			boolean roundaboutPart) {
		// TODO Auto-generated method stub
		return null;
	}

}
