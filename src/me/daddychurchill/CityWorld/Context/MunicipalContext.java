package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Urban.GovernmentBuildingLot;
import me.daddychurchill.CityWorld.Plats.Urban.GovernmentMonumentLot;
import me.daddychurchill.CityWorld.Plats.Urban.ParkLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class MunicipalContext extends UrbanContext {

	public MunicipalContext(CityWorldGenerator generator) {
		super(generator);

		oddsOfParks = Odds.oddsVeryUnlikely;
		oddsOfIsolatedLots = Odds.oddsVeryLikely;
		oddsOfIdenticalBuildingHeights = Odds.oddsAlwaysGoingToHappen;
		oddsOfSimilarBuildingHeights = Odds.oddsAlwaysGoingToHappen;
		oddsOfSimilarBuildingRounding = Odds.oddsAlwaysGoingToHappen;
		oddsOfUnfinishedBuildings = Odds.oddsExtremelyUnlikely;
		oddsOfOnlyUnfinishedBasements = Odds.oddsUnlikely;
		//oddsOfMissingRoad = oddsNeverGoingToHappen;
		oddsOfRoundAbouts = Odds.oddsVeryLikely;
		 
		oddsOfStairWallMaterialIsWallMaterial = Odds.oddsAlwaysGoingToHappen;
		oddsOfFlatWalledBuildings = Odds.oddsAlwaysGoingToHappen;
		oddsOfSimilarInsetBuildings = Odds.oddsAlwaysGoingToHappen;
		oddsOfBuildingWallInset = Odds.oddsAlwaysGoingToHappen;
		rangeOfWallInset = 1;
		
		setSchematicFamily(SchematicFamily.MUNICIPAL);

		maximumFloorsAbove = 5;
		maximumFloorsBelow = 2;
		minSizeOfBuilding = 3;
		
		oddsOfFloodFill = Odds.oddsAlwaysGoingToHappen;
		oddsOfFloodDecay = Odds.oddsAlwaysGoingToHappen;
	}
	
	@Override
	protected PlatLot getBuilding(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		if (odds.playOdds(Odds.oddsVeryUnlikely))
			return new GovernmentMonumentLot(platmap, chunkX, chunkZ);
		else
			return new GovernmentBuildingLot(platmap, chunkX, chunkZ);
	}
	
	@Override
	protected PlatLot getPark(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ, int waterDepth) {
		if (odds.playOdds(Odds.oddsVeryUnlikely))
			return new ParkLot(platmap, chunkX, chunkZ, generator.connectedKeyForParks, waterDepth);
		else
			return new GovernmentMonumentLot(platmap, chunkX, chunkZ);
	}
	

}
