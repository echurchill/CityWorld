package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.CityWorld;

public class ContextAllPark extends PlatMapContext {

	public ContextAllPark(CityWorld plugin, Random rand) {
		super(plugin, rand);

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
