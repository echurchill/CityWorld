package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.CityWorld;

public class ContextMall extends PlatMapContext {

	public ContextMall(CityWorld plugin, Random rand) {
		super(plugin, rand);

		setFloorRange(rand, 2, 1);
		
		oddsOfParks = oddsUnlikely;
		oddsOfIsolatedLots = oddsNeverGoingToHappen;
		oddsOfIdenticalBuildingHeights = oddsAlwaysGoingToHappen;
		oddsOfSimilarBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingRounding = oddsExtremelyLikely;
		oddsOfUnfinishedBuildings = oddsNeverGoingToHappen;
		oddsOfOnlyUnfinishedBasements = oddsNeverGoingToHappen;
		oddsOfMissingRoad = oddsNeverGoingToHappen;
		oddsOfRoundAbouts = oddsUnlikely;
		 
		oddsOfStairWallMaterialIsWallMaterial = oddsExtremelyLikely;
		oddsOfBuildingWallInset = oddsExtremelyLikely;
		oddsOfFlatWalledBuildings = oddsExtremelyLikely;
		oddsOfSimilarInsetBuildings = oddsExtremelyLikely;
		rangeOfWallInset = 2;
	}

}
