package me.daddychurchill.CityWorld.Context;

import java.util.Random;

public class ContextCityCenter extends ContextUrban {

	public ContextCityCenter(Random rand) {
		super(rand);

		setFloorRange(rand, 5, 2);
		
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
	}

}
