package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Support.Odds;

public class ParkContext extends UrbanContext {
	
	public ParkContext(CityWorldGenerator generator) {
		super(generator);

		oddsOfParks = Odds.oddsAlwaysGoingToHappen;
		oddsOfIsolatedLots = Odds.oddsAlwaysGoingToHappen;
		oddsOfIdenticalBuildingHeights = Odds.oddsNeverGoingToHappen;
		oddsOfSimilarBuildingHeights = Odds.oddsNeverGoingToHappen;
		oddsOfSimilarBuildingRounding = Odds.oddsNeverGoingToHappen;
		oddsOfUnfinishedBuildings = Odds.oddsNeverGoingToHappen;
		oddsOfOnlyUnfinishedBasements = Odds.oddsNeverGoingToHappen;
		//oddsOfMissingRoad = oddsNeverGoingToHappen;
		oddsOfRoundAbouts = Odds.oddsNeverGoingToHappen;
		 
		oddsOfStairWallMaterialIsWallMaterial = Odds.oddsNeverGoingToHappen;
		oddsOfBuildingWallInset = Odds.oddsNeverGoingToHappen;
		oddsOfFlatWalledBuildings = Odds.oddsNeverGoingToHappen;
		oddsOfSimilarInsetBuildings = Odds.oddsNeverGoingToHappen;
		rangeOfWallInset = 1;
		
		setSchematicFamily(SchematicFamily.PARK);
	}
}
