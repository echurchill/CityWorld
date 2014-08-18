package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Support.Odds;

public class ConstructionContext extends UrbanContext {

	public ConstructionContext(WorldGenerator generator) {
		super(generator);
	}
	
	@Override
	protected void initialize() {
		super.initialize();

		oddsOfParks = Odds.oddsUnlikely;
		oddsOfIsolatedLots = Odds.oddsLikely;
		oddsOfIdenticalBuildingHeights = Odds.oddsExtremelyLikely;
		oddsOfSimilarBuildingHeights = Odds.oddsExtremelyLikely;
		oddsOfSimilarBuildingRounding = Odds.oddsExtremelyLikely;
		//oddsOfMissingRoad = oddsLikely;
		oddsOfRoundAbouts = Odds.oddsLikely;
		 
		oddsOfUnfinishedBuildings = Odds.oddsVeryLikely;
		oddsOfOnlyUnfinishedBasements = Odds.oddsLikely;
		oddsOfCranes = Odds.oddsExtremelyLikely;
		
		oddsOfStairWallMaterialIsWallMaterial = Odds.oddsExtremelyLikely;
		oddsOfBuildingWallInset = Odds.oddsExtremelyLikely;
		oddsOfFlatWalledBuildings = Odds.oddsExtremelyLikely;
		oddsOfSimilarInsetBuildings = Odds.oddsExtremelyLikely;
		rangeOfWallInset = 2;
		
		schematicFamily = SchematicFamily.CONSTRUCTION;

		maximumFloorsAbove = absoluteMaximumFloorsAbove;
		maximumFloorsBelow = 3;
	}

}
