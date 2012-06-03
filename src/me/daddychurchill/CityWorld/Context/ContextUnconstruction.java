package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public class ContextUnconstruction extends ContextUrban {

	public ContextUnconstruction(CityWorld plugin, WorldGenerator generator, SupportChunk typicalChunk) {
		super(plugin, generator, typicalChunk);
		Random random = typicalChunk.random;

		setFloorRange(random, 9, 4);

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
	}

}
