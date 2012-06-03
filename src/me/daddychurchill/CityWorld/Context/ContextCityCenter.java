package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class ContextCityCenter extends ContextUrban {

	public ContextCityCenter(CityWorld plugin, WorldGenerator generator, SupportChunk typicalChunk) {
		super(plugin, generator, typicalChunk);
		Random random = typicalChunk.random;

		setFloorRange(random, 5, 2);
		
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
