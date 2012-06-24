package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;

public class ContextLowrise extends ContextUrban {

	public ContextLowrise(WorldGenerator generator, PlatMap platmap) {
		super(generator, platmap);
		Random platmapRandom = platmap.getRandomGenerator();
		
		oddsOfParks = oddsLikely;
		oddsOfIsolatedLots = oddsVeryLikely;
		oddsOfIdenticalBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingRounding = oddsExtremelyLikely;
		oddsOfUnfinishedBuildings = oddsVeryUnlikely;
		oddsOfOnlyUnfinishedBasements = oddsNeverGoingToHappen;
		oddsOfMissingRoad = oddsLikely;
		oddsOfRoundAbouts = oddsLikely;
		 
		oddsOfStairWallMaterialIsWallMaterial = oddsExtremelyLikely;
		oddsOfBuildingWallInset = oddsExtremelyLikely;
		oddsOfFlatWalledBuildings = oddsExtremelyLikely;
		oddsOfSimilarInsetBuildings = oddsExtremelyLikely;
		rangeOfWallInset = 2;

		setFloorRange(platmapRandom, 3, 1);
	}

}
