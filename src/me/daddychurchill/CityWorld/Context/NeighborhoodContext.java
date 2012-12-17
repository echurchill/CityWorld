package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.HouseLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.Odds;

public class NeighborhoodContext extends RuralContext {

	public NeighborhoodContext(WorldGenerator generator) {
		super(generator);
	}
	
	@Override
	protected void initialize() {
		super.initialize();

		schematicFamily = SchematicFamily.NEIGHBORHOOD;
	}

	@Override
	public void populateMap(WorldGenerator generator, PlatMap platmap) {
		
		// let the user add their stuff first, then plug any remaining holes with our stuff
		mapsSchematics.populate(generator, platmap);
		
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
								platmap.isExistingRoad(x, z - 1) || platmap.isExistingRoad(x, z + 1))
								placeHouse(platmap, x, z);
							
						// just do it then
						} else
							placeHouse(platmap, x, z);
					}
				}
			}
		}
	}
	
	private void placeHouse(PlatMap platmap, int x, int z) {
		platmap.setLot(x, z, new HouseLot(platmap, platmap.originX + x, platmap.originZ + z));
	}
	
	@Override
	protected PlatLot getBackfillLot(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		return null;
	}
}
