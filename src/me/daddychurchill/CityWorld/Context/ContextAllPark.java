package me.daddychurchill.CityWorld.Context;

import java.util.Random;

public class ContextAllPark extends ContextUrban {

	public ContextAllPark(Random rand) {
		super(rand);

		setFloorRange(rand, 2, 4);
		
		oddsOfParks = oddsAlwaysGoingToHappen;
		oddsOfIsolatedLots = oddsExtremelyLikely;
		oddsOfIdenticalBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingRounding = oddsExtremelyLikely;
		oddsOfUnfinishedBuildings = oddsExtremelyLikely;
		oddsOfOnlyUnfinishedBasements = oddsLikely;
		oddsOfMissingRoad = oddsLikely;
		oddsOfRoundAbouts = oddsVeryLikely;
		 
		oddsOfStairWallMaterialIsWallMaterial = oddsExtremelyLikely;
		oddsOfBuildingWallInset = oddsExtremelyLikely;
		oddsOfFlatWalledBuildings = oddsExtremelyLikely;
		oddsOfSimilarInsetBuildings = oddsExtremelyLikely;
		rangeOfWallInset = 2;
	}

}
