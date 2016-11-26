package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Support.Odds;

public class ConstructionContext extends UrbanContext {

	public ConstructionContext(CityWorldGenerator generator) {
		super(generator);


		oddsOfParks = Odds.oddsUnlikely;
		oddsOfIsolatedLots = Odds.oddsLikely;
		oddsOfIdenticalBuildingHeights = Odds.oddsExtremelyLikely;
		oddsOfSimilarBuildingHeights = Odds.oddsExtremelyLikely;
		oddsOfSimilarBuildingRounding = Odds.oddsExtremelyLikely;
		//oddsOfMissingRoad = oddsLikely;
		//oddsOfRoundAbouts = Odds.oddsSomewhatLikely;
		 
		oddsOfUnfinishedBuildings = Odds.oddsVeryLikely;
		oddsOfOnlyUnfinishedBasements = Odds.oddsLikely;
		oddsOfCranes = Odds.oddsExtremelyLikely;
		
		oddsOfStairWallMaterialIsWallMaterial = Odds.oddsExtremelyLikely;
		oddsOfBuildingWallInset = Odds.oddsExtremelyLikely;
		oddsOfFlatWalledBuildings = Odds.oddsExtremelyLikely;
		oddsOfSimilarInsetBuildings = Odds.oddsExtremelyLikely;
		rangeOfWallInset = 2;
		
		setSchematicFamily(SchematicFamily.CONSTRUCTION);
		
		minSizeOfBuilding = 2;

		maximumFloorsAbove = absoluteMaximumFloorsAbove;
		maximumFloorsBelow = 3;
	}
}
