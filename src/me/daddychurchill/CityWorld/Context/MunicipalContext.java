package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Support.Odds;

public class MunicipalContext extends UrbanContext {

	public MunicipalContext(CityWorldGenerator generator) {
		super(generator);

		oddsOfParks = Odds.oddsLikely;
		oddsOfIsolatedLots = Odds.oddsVeryLikely;
		oddsOfIdenticalBuildingHeights = Odds.oddsAlwaysGoingToHappen;
		oddsOfSimilarBuildingHeights = Odds.oddsAlwaysGoingToHappen;
		oddsOfSimilarBuildingRounding = Odds.oddsAlwaysGoingToHappen;
		oddsOfUnfinishedBuildings = Odds.oddsNeverGoingToHappen;
		oddsOfOnlyUnfinishedBasements = Odds.oddsLikely;
		//oddsOfMissingRoad = oddsNeverGoingToHappen;
		oddsOfRoundAbouts = Odds.oddsVeryLikely;
		 
		oddsOfStairWallMaterialIsWallMaterial = Odds.oddsAlwaysGoingToHappen;
		oddsOfFlatWalledBuildings = Odds.oddsVeryLikely;
		oddsOfSimilarInsetBuildings = Odds.oddsVeryLikely;
		oddsOfBuildingWallInset = Odds.oddsVeryLikely;
		rangeOfWallInset = 1;
		
		schematicFamily = SchematicFamily.MUNICIPAL;

		maximumFloorsAbove = 5;
		maximumFloorsBelow = 2;
	}
	
}
