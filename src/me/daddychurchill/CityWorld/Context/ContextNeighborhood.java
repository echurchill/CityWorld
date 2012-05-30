package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatHouse;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class ContextNeighborhood extends ContextRural {

	public ContextNeighborhood(CityWorld plugin, SupportChunk typicalChunk) {
		super(plugin, typicalChunk);

		//TODO anything else?
	}

	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap, SupportChunk typicalChunk) {
		Random random = typicalChunk.random;
		
		// where do we begin?
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		
		//TODO if there are roads in the map, remove any houses that are not next to a road
		
		// backfill with buildings and parks
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.platLots[x][z];
				if (current == null) {
					platmap.platLots[x][z] = new PlatHouse(random, platmap, originX + x, originZ + z);
				}
			}
		}
	}
}
