package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Urban.EmptyBuildingLot;
import me.daddychurchill.CityWorld.Plats.Urban.LibraryBuildingLot;
import me.daddychurchill.CityWorld.Plats.Urban.OfficeBuildingLot;
import me.daddychurchill.CityWorld.Plats.Urban.ParkLot;
import me.daddychurchill.CityWorld.Plats.Urban.StoreBuildingLot;
import me.daddychurchill.CityWorld.Plats.Urban.UnfinishedBuildingLot;
import me.daddychurchill.CityWorld.Plugins.ShapeProvider;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public abstract class UrbanContext extends CivilizedContext {

	public UrbanContext(CityWorldGenerator generator) {
		super(generator);

		maximumFloorsAbove = 2;
		maximumFloorsBelow = 2;
	}
	
	@Override
	public void populateMap(CityWorldGenerator generator, PlatMap platmap) {
		
		// let the user add their stuff first, then plug any remaining holes with our stuff
		getSchematics(generator).populate(generator, platmap);
		
		// random fluff!
		Odds platmapOdds = platmap.getOddsGenerator();
		ShapeProvider shapeProvider = generator.shapeProvider;
		int waterDepth = ParkLot.getWaterDepth(platmapOdds);
		
		// backfill with buildings and parks
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current == null) {
					
					//TODO I need to come up with a more elegant way of doing this!
					if (generator.settings.includeBuildings) {

						// what to build?
						boolean buildPark = platmapOdds.playOdds(oddsOfParks);
						if (buildPark)
							current = getPark(generator, platmap, platmapOdds, platmap.originX + x, platmap.originZ + z, waterDepth);
						else
							current = getBackfillLot(generator, platmap, platmapOdds, platmap.originX + x, platmap.originZ + z);
						
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
							
						// 2 by 2 at a minimum if at all possible
						} else if (!buildPark && x < PlatMap.Width - 1 && z < PlatMap.Width - 1) {
							
							// is there room?
							PlatLot toEast = platmap.getLot(x + 1, z);
							PlatLot toSouth = platmap.getLot(x, z + 1);
							PlatLot toSouthEast = platmap.getLot(x + 1, z + 1);
							if (toEast == null && toSouth == null && toSouthEast == null) {
								toEast = current.newLike(platmap, platmap.originX + x + 1, platmap.originZ + z);
								toEast.makeConnected(current);
								platmap.setLot(x + 1, z, toEast);

								toSouth = current.newLike(platmap, platmap.originX + x, platmap.originZ + z + 1);
								toSouth.makeConnected(current);
								platmap.setLot(x, z + 1, toSouth);

								toSouthEast = current.newLike(platmap, platmap.originX + x + 1, platmap.originZ + z + 1);
								toSouthEast.makeConnected(current);
								platmap.setLot(x + 1, z + 1, toSouthEast);
								
								// @@@@ go for a 2x3, 3x2 or even a 3x3
							}
						}
					}

					// remember what we did
					if (current != null)
						platmap.setLot(x, z, current);
				}
			}
		}

		// validate each lot
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current != null) {
					PlatLot replacement = current.validateLot(platmap, x, z);
					if (replacement != null)
						platmap.setLot(x, z, replacement);
				}
			}
		}
	}
	
	@Override
	protected PlatLot getBackfillLot(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		if (odds.playOdds(oddsOfUnfinishedBuildings))
			return getUnfinishedBuilding(generator, platmap, odds, chunkX, chunkZ);
		else
			return getBuilding(generator, platmap, odds, chunkX, chunkZ);
	}
	
	protected PlatLot getPark(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ, int waterDepth) {
		return new ParkLot(platmap, chunkX, chunkZ, generator.connectedKeyForParks, waterDepth);
	}
	
	protected PlatLot getUnfinishedBuilding(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new UnfinishedBuildingLot(platmap, chunkX, chunkZ);
	}
	
	protected PlatLot getBuilding(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		switch (odds.getRandomInt(8)) {
		case 1:
			return new EmptyBuildingLot(platmap, chunkX, chunkZ);
		case 2:
			return new StoreBuildingLot(platmap, chunkX, chunkZ);
		case 3:
			return new LibraryBuildingLot(platmap, chunkX, chunkZ);
//		case 4:
//			return new ApartmentBuildingLot(platmap, chunkX, chunkZ);
//		case 5:
//			return new BankBuildingLot(platmap, chunkX, chunkZ);
//		case 6:
//			return new FactoryBuildingLot(platmap, chunkX, chunkZ);
//		case 7:
//			return new BlaBlaBuildingLot(platmap, chunkX, chunkZ);
		default:
			return new OfficeBuildingLot(platmap, chunkX, chunkZ);
			}
	}
}
