package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatOfficeBuilding;
import me.daddychurchill.CityWorld.Plats.PlatPark;
import me.daddychurchill.CityWorld.Plats.PlatUnfinishedBuilding;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public abstract class ContextUrban extends ContextData {

	public ContextUrban(CityWorld plugin, SupportChunk typicalChunk) {
		super(plugin, typicalChunk);

		//TODO anything to generalized?
	}

	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap, SupportChunk typicalChunk) {
		Random random = typicalChunk.random;
		
		// where do we begin?
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		
		// backfill with buildings and parks
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.platLots[x][z];
				if (current == null) {
					
					//TODO I need to come up with a more elegant way of doing this!
					// what to build?
					if (random.nextInt(oddsOfParks) == 0)
						current = new PlatPark(random, platmap, originX + x, originZ + z, generator.connectedKeyForParks);
					else if (random.nextInt(oddsOfUnfinishedBuildings) == 0)
						current = new PlatUnfinishedBuilding(random, platmap, originX + x, originZ + z);
					// houses
					// yards
					else
						current = new PlatOfficeBuilding(random, platmap, originX + x, originZ + z);
					
					/* for each plot
					 *   randomly pick a plattype
					 *   see if the "previous chunk" is the same type
					 *     if so make the new plattype connected to the previous type
					 *   if the new plot is shorter than the previous plot or
					 *   if the new plot is shallower than the previous slot
					 *     mark the previous plot to have stairs
					 *   if plot does not have neighbors yet
					 *     mark the plot to have stairs
					 */

					// see if the previous chunk is the same type
					PlatLot previous = null;
					if (x > 0 && current.isConnectable(platmap.platLots[x - 1][z])) {
						previous = platmap.platLots[x - 1][z];
					} else if (z > 0 && current.isConnectable(platmap.platLots[x][z - 1])) {
						previous = platmap.platLots[x][z - 1];
					}
					
					// if there was a similar previous one then copy it... maybe
					if (previous != null && !previous.isIsolatedLot(random, oddsOfIsolatedLots)) {
						current.makeConnected(random, previous);
					}

					// remember what we did
					platmap.platLots[x][z] = current;
				}
			}
		}
	}
}
