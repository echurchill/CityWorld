package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.OfficeBuildingLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.ParkLot;
import me.daddychurchill.CityWorld.Plats.UnfinishedBuildingLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider;
import me.daddychurchill.CityWorld.Support.Odds;

public abstract class UrbanContext extends DataContext {

	public UrbanContext(WorldGenerator generator) {
		super(generator);

		//TODO: Generalization?
	}
	
	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap) {
		
		// let the user add their stuff first, then plug any remaining holes with our stuff
		mapsSchematics.populate(generator, platmap);
		
		// random fluff!
		Odds platmapOdds = platmap.getOddsGenerator();
		ShapeProvider shapeProvider = generator.shapeProvider;
		
		// backfill with buildings and parks
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current == null) {
					
					//TODO I need to come up with a more elegant way of doing this!
					if (generator.settings.includeBuildings) {

						// what to build?
						if (platmapOdds.playOdds(oddsOfParks))
							current = getPark(generator, platmap, platmapOdds, platmap.originX + x, platmap.originZ + z);
						else if (platmapOdds.playOdds(oddsOfUnfinishedBuildings))
							current = getUnfinishedBuilding(generator, platmap, platmapOdds, platmap.originX + x, platmap.originZ + z);
						//TODO government buildings
						else 
							current = getFinishedBuilding(generator, platmap, platmapOdds, platmap.originX + x, platmap.originZ + z);
						
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
	
	protected PlatLot getPark(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new ParkLot(platmap, chunkX, chunkZ, generator.connectedKeyForParks);
	}
	
	protected PlatLot getFinishedBuilding(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new OfficeBuildingLot(platmap, chunkX, chunkZ);
	}
	
	protected PlatLot getUnfinishedBuilding(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new UnfinishedBuildingLot(platmap, chunkX, chunkZ);
	}
}
