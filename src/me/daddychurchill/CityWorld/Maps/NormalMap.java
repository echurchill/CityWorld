package me.daddychurchill.CityWorld.Maps;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.NatureLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.RoadLot;
import me.daddychurchill.CityWorld.Plats.RoundaboutStatueLot;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class NormalMap extends PlatMap {

	public NormalMap(WorldGenerator aGenerator, SupportChunk typicalChunk, int aOriginX, int aOriginZ) {
		super(aGenerator, typicalChunk, aOriginX, aOriginZ);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	protected PlatLot createNaturalLot(int x, int z) {
		return new NatureLot(this, originX + x, originZ + z);
	}

	@Override
	protected PlatLot createRoadLot(int x, int z, boolean roundaboutPart) {
		return new RoadLot(this, originX + x, originZ + z, generator.connectedKeyForPavedRoads, roundaboutPart);
	}

	@Override
	protected PlatLot createRoundaboutStatueLot(int x, int z) {
		//return new SchematicTestLot(this, originX + x, originZ + z);
		return new RoundaboutStatueLot(this, originX + x, originZ + z);
	}

	@Override
	protected void populateLots(SupportChunk typicalChunk) {

		// assume everything is natural for the moment
		context = generator.normalContext;
		context.populateMap(generator, this);
		
		// place and validate the roads
		if (generator.settings.includeRoads) {
			populateRoads(typicalChunk);
			validateRoads(typicalChunk);

			// place the buildings
			if (generator.settings.includeBuildings) {
	
				// recalculate the context based on the "natural-ness" of the platmap
				context = getContext();
				context.populateMap(generator, this);
			}
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

	protected void populateRoads(SupportChunk typicalChunk) {
		
		// place the big four
		placeIntersection(typicalChunk, RoadLot.PlatMapRoadInset - 1, RoadLot.PlatMapRoadInset - 1);
		placeIntersection(typicalChunk, RoadLot.PlatMapRoadInset - 1, Width - RoadLot.PlatMapRoadInset);
		placeIntersection(typicalChunk, Width - RoadLot.PlatMapRoadInset, RoadLot.PlatMapRoadInset - 1);
		placeIntersection(typicalChunk, Width - RoadLot.PlatMapRoadInset, Width - RoadLot.PlatMapRoadInset);
	}
	
}
