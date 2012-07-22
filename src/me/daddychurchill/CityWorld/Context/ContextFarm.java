package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatFarm;
import me.daddychurchill.CityWorld.Plats.PlatHouse;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;

public class ContextFarm extends ContextRural {

	public ContextFarm(WorldGenerator generator, PlatMap platmap) {
		super(generator, platmap);

		oddsOfIsolatedLots = oddsVeryLikely;
	}
	
	private final static double oddsOfFarmHouse = 0.25;
	
	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap) {
		Random platmapRandom = platmap.getRandomGenerator();
		boolean housePlaced = false;
		int lastX = 0, lastZ = 0;
		
		// where do we begin?
		int originX = platmap.originX;
		int originZ = platmap.originZ;
		
		// clean up the platmap of singletons and odd road structures
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				
				// something here?
				if (current == null) {
					
					// but there aren't neighbors
					if (!platmap.isEmptyLot(x - 1, z) && !platmap.isEmptyLot(x + 1, z) &&
						!platmap.isEmptyLot(x, z - 1) && !platmap.isEmptyLot(x, z + 1))
						platmap.recycleLot(x, z);
				}
				
				// look for singleton nature and roundabouts
				else if (current != null) {
					
					// if a single natural thing is here but surrounded by four "things"
					if (current.style == LotStyle.NATURE &&
						platmap.isEmptyLot(x - 1, z) && platmap.isEmptyLot(x + 1, z) &&
						platmap.isEmptyLot(x, z - 1) && platmap.isEmptyLot(x, z + 1))
						platmap.emptyLot(x, z);
					
					// get rid of roundabouts
					else if (current.style == LotStyle.ROUNDABOUT) {
						platmap.paveLot(x, z, false);
						platmap.emptyLot(x - 1, z - 1);
						platmap.emptyLot(x - 1, z + 1);
						platmap.emptyLot(x + 1, z - 1);
						platmap.emptyLot(x + 1, z + 1);
					}
				}
			}
		}
		
		// backfill with farms and a single house
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current == null) {
					
					// farm house here?
					if (!housePlaced && platmapRandom.nextDouble() > oddsOfFarmHouse && generator.settings.includeHouses) {
						housePlaced = platmap.setLot(x, z, new PlatHouse(platmap, platmap.originX + x, platmap.originZ + z)); 
					
					// place the farm
					} else {
						
						// place the farm
						current = new PlatFarm(platmap, originX + x, originZ + z);
						
						// see if the previous chunk is the same type
						PlatLot previous = null;
						if (x > 0 && current.isConnectable(platmap.getLot(x - 1, z))) {
							previous = platmap.getLot(x - 1, z);
						} else if (z > 0 && current.isConnectable(platmap.getLot(x, z - 1))) {
							previous = platmap.getLot(x, z - 1);
						}
						
						// if there was a similar previous one then copy it... maybe
						if (previous != null && !generator.isIsolatedLotAt(platmap.originX + x, platmap.originZ + z, oddsOfIsolatedLots)) {
							current.makeConnected(previous);
						}

						// remember what we did
						platmap.setLot(x, z, current);

						// remember the last place
						lastX = x;
						lastZ = z;
					}
				}
			}
		}
		
		// did we miss out placing the farm house?
		if (!housePlaced && platmap.isEmptyLot(lastX, lastZ) && generator.settings.includeHouses) {
			platmap.setLot(lastX, lastZ, new PlatHouse(platmap, platmap.originX + lastX, platmap.originZ + lastZ)); 
		}
	}
}
