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
			context.validateMap(generator, this);
			
			// place and validate the roads
			if (generator.settings.includeRoads) {
				populateRoads();
				validateRoads();
	
				// place the buildings
				if (generator.settings.includeBuildings) {
		
					// recalculate the context based on the "natural-ness" of the platmap
					context = getContext();
					context.populateMap(generator, this);
					context.validateMap(generator, this);
				}
			}
		} catch (Exception e) {
			generator.reportException("NormalMap.populateLots FAILED", e);

		} 
	}
}
