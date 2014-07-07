package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;

public class HighriseContext extends UrbanContext {
	
	public HighriseContext(WorldGenerator generator) {
		super(generator);
	}
	
	@Override
	protected void initialize() {
		super.initialize();

		oddsOfParks = oddsNeverGoingToHappen;
		oddsOfIsolatedLots = oddsLikely;
		oddsOfIdenticalBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingRounding = oddsExtremelyLikely;
		oddsOfUnfinishedBuildings = oddsPrettyUnlikely;
		oddsOfOnlyUnfinishedBasements = oddsNeverGoingToHappen;
		//oddsOfMissingRoad = oddsNeverGoingToHappen;
		oddsOfRoundAbouts = oddsNeverGoingToHappen;
		 
		oddsOfStairWallMaterialIsWallMaterial = oddsExtremelyLikely;
		oddsOfBuildingWallInset = oddsExtremelyLikely;
		oddsOfFlatWalledBuildings = oddsExtremelyLikely;
		oddsOfSimilarInsetBuildings = oddsExtremelyLikely;
		rangeOfWallInset = 1;
		
		schematicFamily = SchematicFamily.HIGHRISE;

		maximumFloorsAbove = absoluteMaximumFloorsAbove;
		maximumFloorsBelow = 3;
	}

}
