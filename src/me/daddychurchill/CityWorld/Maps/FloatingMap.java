package me.daddychurchill.CityWorld.Maps;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Context.CityCenterContext;
import me.daddychurchill.CityWorld.Context.DataContext;
import me.daddychurchill.CityWorld.Context.FarmContext;
import me.daddychurchill.CityWorld.Context.HighriseContext;
import me.daddychurchill.CityWorld.Context.LowriseContext;
import me.daddychurchill.CityWorld.Context.MallContext;
import me.daddychurchill.CityWorld.Context.MidriseContext;
import me.daddychurchill.CityWorld.Context.NatureContext_Floating;
import me.daddychurchill.CityWorld.Context.NeighborhoodContext;
import me.daddychurchill.CityWorld.Context.UnderConstructionContext;
import me.daddychurchill.CityWorld.Plats.FloatingBlimpLot;
import me.daddychurchill.CityWorld.Plats.FloatingNothingLot;
import me.daddychurchill.CityWorld.Plats.FloatingRoadLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.RoadLot;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class FloatingMap extends PlatMap {

	public FloatingMap(WorldGenerator aGenerator, SupportChunk typicalChunk, int aOriginX, int aOriginZ) {
		super(aGenerator, typicalChunk, aOriginX, aOriginZ);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void populateLots(SupportChunk typicalChunk) {

		// assume everything is natural for the moment
		context = new NatureContext_Floating(generator, this);
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

	@Override
	protected PlatLot createNaturalLot(int x, int z) {
		return new FloatingNothingLot(this, originX + x, originZ + z);
	}

	@Override
	protected PlatLot createRoadLot(int x, int z, boolean roundaboutPart) {
		return new FloatingRoadLot(this, originX + x, originZ + z, generator.connectedKeyForPavedRoads, roundaboutPart);
	}

	protected DataContext getContext() {
		
		// how natural is this platmap?
		if (naturalPlats == 0) {
//			if (typicalChunk.random.nextDouble() > oddsOfCentralPark)
//				return new ContextCentralPark(generator, this);
//			else
				return new HighriseContext(generator, this);
		} else if (naturalPlats < 15)
			return new UnderConstructionContext(generator, this);
		else if (naturalPlats < 25)
			return new MidriseContext(generator, this);
		else if (naturalPlats < 37)
			return new CityCenterContext(generator, this);
		else if (naturalPlats < 50)
			return new MallContext(generator, this);
		else if (naturalPlats < 65)
			return new LowriseContext(generator, this);
		else if (naturalPlats < 80)
			return new NeighborhoodContext(generator, this);
		else if (naturalPlats < 90 && generator.settings.includeFarms)
			return new FarmContext(generator, this);
		else if (naturalPlats < 100)
			return new NeighborhoodContext(generator, this);
		
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
