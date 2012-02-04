package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.CityWorld;

public class ContextHighrise extends PlatMapContext {
	
	public ContextHighrise(CityWorld plugin, Random rand) {
		super(plugin, rand);
		
		setFloorRange(rand, 11, 4);
		
		oddsOfParks = oddsNeverGoingToHappen;
		oddsOfIsolatedLots = oddsLikely;
		oddsOfIdenticalBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingRounding = oddsExtremelyLikely;
		oddsOfUnfinishedBuildings = oddsVeryUnlikely;
		oddsOfOnlyUnfinishedBasements = oddsNeverGoingToHappen;
		oddsOfMissingRoad = oddsNeverGoingToHappen;
		oddsOfRoundAbouts = oddsNeverGoingToHappen;
		 
		oddsOfStairWallMaterialIsWallMaterial = oddsExtremelyLikely;
		oddsOfBuildingWallInset = oddsExtremelyLikely;
		oddsOfFlatWalledBuildings = oddsExtremelyLikely;
		oddsOfSimilarInsetBuildings = oddsExtremelyLikely;
		rangeOfWallInset = 1;
	}

}
