package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Rural.HouseLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class NeighborhoodContext extends RuralContext {

	public NeighborhoodContext(CityWorldGenerator generator) {
		super(generator);

		setSchematicFamily(SchematicFamily.NEIGHBORHOOD);
	}
	
	@Override
	public void populateMap(CityWorldGenerator generator, PlatMap platmap) {
		Odds platmapOdds = platmap.getOddsGenerator();
		
		// let the user add their stuff first, then plug any remaining holes with our stuff
		getSchematics(generator).populate(generator, platmap);
		
		/// do we check for roads?
		boolean checkForRoads = platmap.getNumberOfRoads() > 0;
		
		// backfill with buildings and parks
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				if (current == null) {
					
					// make houses? but only if they are right beside a road
					if (generator.settings.includeHouses) {
						
						// check for roads?
						if (checkForRoads) {
							if (platmap.isExistingRoad(x - 1, z) || platmap.isExistingRoad(x + 1, z) || 
								platmap.isExistingRoad(x, z - 1) || platmap.isExistingRoad(x, z + 1)) {
								platmap.setLot(x, z, getHouseLot(generator, platmap, platmapOdds, platmap.originX + x, platmap.originZ + z));
							}
							
						// just do it then
						} else
							platmap.setLot(x, z, getHouseLot(generator, platmap, platmapOdds, platmap.originX + x, platmap.originZ + z));
					}
				}
			}
		}
	}
	
	@Override
	protected PlatLot getBackfillLot(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return null; // this will eventually get filled in with nature
	}
	
	protected PlatLot getHouseLot(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return new HouseLot(platmap, chunkX, chunkZ);
	}

	public void validateMap(CityWorldGenerator generator, PlatMap platmap) {
//		for (int x = 0; x < PlatMap.Width; x++) {
//			for (int z = 0; z < PlatMap.Width; z++) {
//				PlatLot current = platmap.getLot(x, z);
//				if (current != null)
//					if (platmap.isNaturalLot(x, z))
//			}
//		}
	}
}
