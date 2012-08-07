package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;

public class ContextUnconstruction extends ContextUrban {

	public ContextUnconstruction(WorldGenerator generator, PlatMap platmap) {
		super(generator, platmap);
		Random platmapRandom = platmap.getRandomGenerator();

		oddsOfParks = oddsUnlikely;
		oddsOfIsolatedLots = oddsLikely;
		oddsOfIdenticalBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingRounding = oddsExtremelyLikely;
		oddsOfMissingRoad = oddsLikely;
		oddsOfRoundAbouts = oddsLikely;
		 
		oddsOfUnfinishedBuildings = oddsVeryLikely;
		oddsOfOnlyUnfinishedBasements = oddsLikely;
		oddsOfCranes = oddsExtremelyLikely;
		
		oddsOfStairWallMaterialIsWallMaterial = oddsExtremelyLikely;
		oddsOfBuildingWallInset = oddsExtremelyLikely;
		oddsOfFlatWalledBuildings = oddsExtremelyLikely;
		oddsOfSimilarInsetBuildings = oddsExtremelyLikely;
		rangeOfWallInset = 2;

		setFloorRange(platmapRandom, 9, 4);
	}

}
