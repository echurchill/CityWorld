package me.daddychurchill.CityWorld.Maps;

import me.daddychurchill.CityWorld.WorldGenerator;

public class NormalMap extends PlatMap {

	public NormalMap(WorldGenerator aGenerator, int aOriginX, int aOriginZ) {
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
				}
			}
		} catch (Exception e) {
			generator.reportException("[3AARRRGGGG]", e);

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

}
