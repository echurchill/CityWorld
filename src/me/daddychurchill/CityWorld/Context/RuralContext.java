package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;

public abstract class RuralContext extends DataContext {

	public RuralContext(WorldGenerator generator, PlatMap platmap) {
		super(generator, platmap);
		
		Random platmapRandom = platmap.getRandomGenerator();
		
		oddsOfParks = oddsNeverGoingToHappen;
		oddsOfIsolatedLots = oddsNeverGoingToHappen;
		oddsOfIdenticalBuildingHeights = oddsNeverGoingToHappen;
		oddsOfSimilarBuildingHeights = oddsNeverGoingToHappen;
		oddsOfSimilarBuildingRounding = oddsNeverGoingToHappen;
		oddsOfUnfinishedBuildings = oddsNeverGoingToHappen;
		oddsOfOnlyUnfinishedBasements = oddsNeverGoingToHappen;
		oddsOfMissingRoad = oddsNeverGoingToHappen;
		oddsOfRoundAbouts = oddsNeverGoingToHappen;
		 
		oddsOfStairWallMaterialIsWallMaterial = oddsNeverGoingToHappen;
		oddsOfBuildingWallInset = oddsNeverGoingToHappen;
		oddsOfFlatWalledBuildings = oddsNeverGoingToHappen;
		oddsOfSimilarInsetBuildings = oddsNeverGoingToHappen;
		rangeOfWallInset = 2;

		setFloorRange(platmapRandom, 1, 2);
	}

}
