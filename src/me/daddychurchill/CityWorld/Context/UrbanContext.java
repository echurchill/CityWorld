package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.OfficeBuildingLot;
import me.daddychurchill.CityWorld.Plats.ParkLot;
import me.daddychurchill.CityWorld.Plats.UnfinishedBuildingLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider;

public abstract class UrbanContext extends DataContext {

	public UrbanContext(WorldGenerator generator, PlatMap platmap) {
		super(generator, platmap);

		//TODO anything to generalized?
	}

	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap) {
		Random platmapRandom = platmap.getRandomGenerator();
		ShapeProvider shapeProvider = generator.shapeProvider;
		
		// backfill with buildings and parks
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current == null) {
					
					//TODO I need to come up with a more elegant way of doing this!
					if (generator.settings.includeBuildings) {

						// what to build?
						if (platmapRandom.nextInt(oddsOfParks) == 0)
							current = new ParkLot(platmap, platmap.originX + x, platmap.originZ + z, generator.connectedKeyForParks);
						else if (platmapRandom.nextInt(oddsOfUnfinishedBuildings) == 0)
							current = new UnfinishedBuildingLot(platmap, platmap.originX + x, platmap.originZ + z);
						//TODO warehouses
						//TODO government buildings
						else
							current = new OfficeBuildingLot(platmap, platmap.originX + x, platmap.originZ + z);
						
						// see if the previous chunk is the same type
						PlatLot previous = null;
						if (x > 0 && current.isConnectable(platmap.getLot(x - 1, z))) {
							previous = platmap.getLot(x - 1, z);
						} else if (z > 0 && current.isConnectable(platmap.getLot(x, z - 1))) {
							previous = platmap.getLot(x, z - 1);
						}
						
						// if there was a similar previous one then copy it... maybe
						if (previous != null && !shapeProvider.isIsolatedLotAt(platmap.originX + x, platmap.originZ + z, oddsOfIsolatedLots)) {
							current.makeConnected(previous);
						}
					}

					// remember what we did
					if (current != null)
						platmap.setLot(x, z, current);
				}
			}
		}
	}
}
