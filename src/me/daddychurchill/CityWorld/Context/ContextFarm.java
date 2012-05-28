package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatFarm;
import me.daddychurchill.CityWorld.Plats.PlatHouse;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class ContextFarm extends ContextRural {

	public ContextFarm(CityWorld plugin, SupportChunk typicalChunk) {
		super(plugin, typicalChunk);

		oddsOfIsolatedLots = oddsVeryLikely;
	}
	
	private boolean isLotEmpty(PlatMap platmap, int x, int z) {
		if (x >= 0 && x < PlatMap.Width && z >= 0 && z < PlatMap.Width)
			return platmap.platLots[x][z] == null;
		else
			return true;
	}

	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap, SupportChunk typicalChunk) {
		Random random = typicalChunk.random;
		boolean housePlaced = false;
		int lastX = 0, lastZ = 0;
		
		// where do we begin?
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		
		// clean up the platmap of singletons and odd road structures
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.platLots[x][z];
				
				// something here?
				if (current == null) {
					
					// but there aren't neighbors
					if (!isLotEmpty(platmap, x - 1, z) && !isLotEmpty(platmap, x + 1, z) &&
						!isLotEmpty(platmap, x, z - 1) && !isLotEmpty(platmap, x, z + 1))
						platmap.recycleLot(random, x, z);
				}
				
				// look for singleton nature and roundabouts
				else if (current != null) {
					
					// if a single natural thing is here but surrounded by four "things"
					if (current.style == LotStyle.NATURE &&
						isLotEmpty(platmap, x - 1, z) && isLotEmpty(platmap, x + 1, z) &&
						isLotEmpty(platmap, x, z - 1) && isLotEmpty(platmap, x, z + 1))
						platmap.platLots[x][z] = null;
					
					// get rid of roundabouts
					else if (current.style == LotStyle.ROUNDABOUT) {
						platmap.paveLot(random, x, z);
						platmap.platLots[x - 1][z - 1] = null;
						platmap.platLots[x - 1][z + 1] = null;
						platmap.platLots[x + 1][z - 1] = null;
						platmap.platLots[x + 1][z + 1] = null;
					}
				}
			}
		}
		
		// backfill with farms and a single house
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.platLots[x][z];
				if (current == null) {
					
					// farm house here?
					if (!housePlaced && generator.isFarmHouseAt(platmap.originX + x, platmap.originZ + z)) {
						platmap.platLots[x][z] = new PlatHouse(random, platmap, originX + x, originZ + z);
						housePlaced = true;
					
					// place the farm
					} else {
						
						// place the farm
						current = new PlatFarm(random, platmap, originX + x, originZ + z);
						
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

						// remember the last place
						lastX = x;
						lastZ = z;
					}
				}
			}
		}
		
		// did we miss out placing the farm house?
		if (!housePlaced && platmap.platLots[lastX][lastZ] == null) {
			platmap.platLots[lastX][lastZ] = new PlatHouse(random, platmap, originX + lastX, originZ + lastZ);
		}
	}
}
