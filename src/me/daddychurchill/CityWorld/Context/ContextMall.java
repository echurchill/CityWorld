package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.PlatMap;
import me.daddychurchill.CityWorld.WorldGenerator;

public class ContextMall extends ContextUrban {

	public ContextMall(WorldGenerator generator, PlatMap platmap) {
		super(generator, platmap);
		Random platmapRandom = platmap.getRandomGenerator();
		
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

		setFloorRange(platmapRandom, 2, 1);
	}

}
