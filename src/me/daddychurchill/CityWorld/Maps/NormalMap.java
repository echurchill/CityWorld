package me.daddychurchill.CityWorld.Maps;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.ContextCityCenter;
import me.daddychurchill.CityWorld.Context.ContextData;
import me.daddychurchill.CityWorld.Context.ContextFarm;
import me.daddychurchill.CityWorld.Context.ContextHighrise;
import me.daddychurchill.CityWorld.Context.ContextLowrise;
import me.daddychurchill.CityWorld.Context.ContextMall;
import me.daddychurchill.CityWorld.Context.ContextMidrise;
import me.daddychurchill.CityWorld.Context.ContextNature;
import me.daddychurchill.CityWorld.Context.ContextNeighborhood;
import me.daddychurchill.CityWorld.Context.ContextUnconstruction;
import me.daddychurchill.CityWorld.Plats.NatureLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.RoadLot;
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
	protected void populateLots(SupportChunk typicalChunk) {

		// assume everything is natural for the moment
		context = new ContextNature(generator, this);
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
		
		// recycle all the remaining holes
		for (int x = 0; x < Width; x++) {
			for (int z = 0; z < Width; z++) {
				if (isEmptyLot(x, z))
					recycleLot(x, z);
			}
		}
	}

	protected ContextData getContext() {
		
		// how natural is this platmap?
		if (naturalPlats == 0) {
//			if (typicalChunk.random.nextDouble() > oddsOfCentralPark)
//				return new ContextCentralPark(generator, this);
//			else
				return new ContextHighrise(generator, this);
		} else if (naturalPlats < 15)
			return new ContextUnconstruction(generator, this);
		else if (naturalPlats < 25)
			return new ContextMidrise(generator, this);
		else if (naturalPlats < 37)
			return new ContextCityCenter(generator, this);
		else if (naturalPlats < 50)
			return new ContextMall(generator, this);
		else if (naturalPlats < 65)
			return new ContextLowrise(generator, this);
		else if (naturalPlats < 80)
			return new ContextNeighborhood(generator, this);
		else if (naturalPlats < 90 && generator.settings.includeFarms)
			return new ContextFarm(generator, this);
		else if (naturalPlats < 100)
			return new ContextNeighborhood(generator, this);
		
		// otherwise just keep what we have
		else
			return context;
	}

	protected void populateRoads(SupportChunk typicalChunk) {
		
		// place the big four
		placeIntersection(typicalChunk, RoadLot.PlatMapRoadInset - 1, RoadLot.PlatMapRoadInset - 1);
		placeIntersection(typicalChunk, RoadLot.PlatMapRoadInset - 1, Width - RoadLot.PlatMapRoadInset);
		placeIntersection(typicalChunk, Width - RoadLot.PlatMapRoadInset, RoadLot.PlatMapRoadInset - 1);
		placeIntersection(typicalChunk, Width - RoadLot.PlatMapRoadInset, Width - RoadLot.PlatMapRoadInset);
	}
	
}
