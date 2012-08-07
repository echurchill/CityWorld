package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;

public class ContextCityCenter extends ContextUrban {

	public ContextCityCenter(WorldGenerator generator, PlatMap platmap) {
		super(generator, platmap);
		
		Random platmapRandom = platmap.getRandomGenerator();
		
		oddsOfParks = oddsLikely;
		oddsOfIsolatedLots = oddsVeryLikely;
		oddsOfIdenticalBuildingHeights = oddsAlwaysGoingToHappen;
		oddsOfSimilarBuildingHeights = oddsAlwaysGoingToHappen;
		oddsOfSimilarBuildingRounding = oddsAlwaysGoingToHappen;
		oddsOfUnfinishedBuildings = oddsNeverGoingToHappen;
		oddsOfOnlyUnfinishedBasements = oddsLikely;
		oddsOfMissingRoad = oddsNeverGoingToHappen;
		oddsOfRoundAbouts = oddsAlwaysGoingToHappen;
		 
		oddsOfStairWallMaterialIsWallMaterial = oddsAlwaysGoingToHappen;
		oddsOfFlatWalledBuildings = oddsVeryLikely;
		oddsOfSimilarInsetBuildings = oddsVeryLikely;
		oddsOfBuildingWallInset = oddsVeryLikely;
		rangeOfWallInset = 1;

		setFloorRange(platmapRandom, 5, 2);
	}

}
