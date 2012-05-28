package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.CityWorld;
import me.daddychurchill.CityWorld.Support.SupportChunk;

public abstract class ContextRural extends ContextData {

	public ContextRural(CityWorld plugin, SupportChunk typicalChunk) {
		super(plugin, typicalChunk);
		
		Random random = typicalChunk.random;

		setFloorRange(random, 1, 2);
		
		oddsOfParks = oddsNeverGoingToHappen;
		oddsOfIsolatedLots = oddsNeverGoingToHappen;
		oddsOfIdenticalBuildingHeights = oddsNeverGoingToHappen;
		oddsOfSimilarBuildingHeights = oddsNeverGoingToHappen;
		oddsOfSimilarBuildingRounding = oddsNeverGoingToHappen;
		oddsOfUnfinishedBuildings = oddsNeverGoingToHappen;
		oddsOfOnlyUnfinishedBasements = oddsNeverGoingToHappen;
		oddsOfMissingRoad = oddsNeverGoingToHappen;
		oddsOfRoundAbouts = oddsNeverGoingToHappen;
		 
		oddsOfStairWallMaterialIsWallMaterial = oddsNeverGoingToHappen;
		oddsOfBuildingWallInset = oddsNeverGoingToHappen;
		oddsOfFlatWalledBuildings = oddsNeverGoingToHappen;
		oddsOfSimilarInsetBuildings = oddsNeverGoingToHappen;
		rangeOfWallInset = 2;
	}

}
