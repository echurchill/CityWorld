package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Urban.GovernmentBuildingLot;
import me.daddychurchill.CityWorld.Plats.Urban.MuseumBuildingLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class MunicipalContext extends UrbanContext {

	public MunicipalContext(CityWorldGenerator generator) {
		super(generator);

		oddsOfParks = Odds.oddsExceedinglyUnlikely;
		oddsOfIsolatedLots = Odds.oddsVeryLikely;
		oddsOfIdenticalBuildingHeights = Odds.oddsAlwaysGoingToHappen;
		oddsOfSimilarBuildingHeights = Odds.oddsAlwaysGoingToHappen;
		oddsOfSimilarBuildingRounding = Odds.oddsAlwaysGoingToHappen;
		oddsOfUnfinishedBuildings = Odds.oddsNeverGoingToHappen;
		oddsOfOnlyUnfinishedBasements = Odds.oddsLikely;
		//oddsOfMissingRoad = oddsNeverGoingToHappen;
		oddsOfRoundAbouts = Odds.oddsVeryLikely;
		 
		oddsOfStairWallMaterialIsWallMaterial = Odds.oddsAlwaysGoingToHappen;
		oddsOfFlatWalledBuildings = Odds.oddsAlwaysGoingToHappen;
		oddsOfSimilarInsetBuildings = Odds.oddsAlwaysGoingToHappen;
		oddsOfBuildingWallInset = Odds.oddsAlwaysGoingToHappen;
		rangeOfWallInset = 1;
		
		schematicFamily = SchematicFamily.MUNICIPAL;

		maximumFloorsAbove = 5;
		maximumFloorsBelow = 2;
	}
	
	@Override
	protected PlatLot getBuilding(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		if (generator.settings.includeBones && odds.playOdds(Odds.oddsSomewhatUnlikely))
			return new MuseumBuildingLot(platmap, chunkX, chunkZ);
		else
			return new GovernmentBuildingLot(platmap, chunkX, chunkZ);
	}
}
