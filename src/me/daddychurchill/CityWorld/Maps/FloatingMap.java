package me.daddychurchill.CityWorld.Maps;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.FloatingBlimpLot;

public class FloatingMap extends PlatMap {

	public FloatingMap(WorldGenerator aGenerator, int aOriginX, int aOriginZ) {
		super(aGenerator, aOriginX, aOriginZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void populateLots() {

		try {

			// assume everything is natural for the moment
			context = generator.natureContext;
			context.populateMap(generator, this);
			
			// place and validate the roads
			if (generator.settings.includeRoads) {
				populateRoads();
				validateRoads();
	
				// place the buildings
				if (generator.settings.includeBuildings) {
		
					// recalculate the context based on the "natural-ness" of the platmap
					context = getContext();
					context.populateMap(generator, this);
					
					//TODO need to remove isolated non-nature
					
					// find blimp moorings
					for (int x = 0; x < Width; x++) {
						for (int z = 0; z < Width; z++) {
							if (needBlimpLot(x, z))
								setLot(x, z, new FloatingBlimpLot(this, originX + x, originZ + z));
						}
					}
				}
			}
			
		} catch (Exception e) {
			generator.reportException("FloatingMap.populateLots FAILED", e);
	
		} 
	
		//TODO: nature shouldn't place its special lots until this phase and then only if the lot is surrounded by nature
		
		// recycle all the remaining holes
		for (int x = 0; x < Width; x++) {
			for (int z = 0; z < Width; z++) {
				if (isEmptyLot(x, z))
					recycleLot(x, z);
			}
		}
	}
	
	private boolean needBlimpLot(int x, int z) {
		if (isNaturalLot(x, z)) {
			return isStructureLot(x - 1, z) || isStructureLot(x + 1, z) ||
				   isStructureLot(x, z - 1) || isStructureLot(x, z + 1);
		} else
			return false;
	}

}
