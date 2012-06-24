package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;

public abstract class ContextRural extends ContextData {

	public ContextRural(WorldGenerator generator, PlatMap platmap) {
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
