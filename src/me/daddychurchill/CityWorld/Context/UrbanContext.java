package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.OfficeBuildingLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.ParkLot;
import me.daddychurchill.CityWorld.Plats.UnfinishedBuildingLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider;

public abstract class UrbanContext extends DataContext {

	public UrbanContext(WorldGenerator generator, PlatMap platmap) {
		super(generator, platmap);

		//TODO: Generalization?
	}
	
	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap) {
		
		// let the user add their stuff first, then plug any remaining holes with our stuff
		populateWithSchematics(generator, platmap);
		
		// random fluff!
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
							current = getPark(generator, platmap, platmapRandom, platmap.originX + x, platmap.originZ + z);
						else if (platmapRandom.nextInt(oddsOfUnfinishedBuildings) == 0)
							current = getUnfinishedBuilding(generator, platmap, platmapRandom, platmap.originX + x, platmap.originZ + z);
						//TODO government buildings
						else 
							current = getFinishedBuilding(generator, platmap, platmapRandom, platmap.originX + x, platmap.originZ + z);
						
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
	
	protected PlatLot getPark(WorldGenerator generator, PlatMap platmap, Random random, int chunkX, int chunkZ) {
		return new ParkLot(platmap, chunkX, chunkZ, generator.connectedKeyForParks);
	}
	
	protected PlatLot getFinishedBuilding(WorldGenerator generator, PlatMap platmap, Random random, int chunkX, int chunkZ) {
		return new OfficeBuildingLot(platmap, chunkX, chunkZ);
	}
	
	protected PlatLot getUnfinishedBuilding(WorldGenerator generator, PlatMap platmap, Random random, int chunkX, int chunkZ) {
		return new UnfinishedBuildingLot(platmap, chunkX, chunkZ);
	}
}
