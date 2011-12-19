package me.daddychurchill.CityWorld.Context;

import java.util.Random;

public class ContextUnfinished extends ContextUrban {

	public ContextUnfinished(Random rand) {
		super(rand);

		setFloorRange(rand, 9, 4);

		oddsOfParks = oddsUnlikely;
		oddsOfIsolatedLots = oddsLikely;
		oddsOfIdenticalBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingRounding = oddsExtremelyLikely;
		oddsOfMissingRoad = oddsLikely;
		oddsOfRoundAbouts = oddsLikely;
		 
		oddsOfUnfinishedBuildings = oddsExtremelyLikely;
		oddsOfOnlyUnfinishedBasements = oddsLikely;
		oddsOfCranes = oddsExtremelyLikely;
		
		oddsOfStairWallMaterialIsWallMaterial = oddsExtremelyLikely;
		oddsOfBuildingWallInset = oddsExtremelyLikely;
		oddsOfFlatWalledBuildings = oddsExtremelyLikely;
		oddsOfSimilarInsetBuildings = oddsExtremelyLikely;
		rangeOfWallInset = 2;
	}

}
