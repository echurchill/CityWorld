package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.Urban.FactoryBuildingLot;
import me.daddychurchill.CityWorld.Plats.Urban.StorageLot;
import me.daddychurchill.CityWorld.Plats.Urban.WarehouseBuildingLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public class IndustrialContext extends UrbanContext {

	public IndustrialContext(CityWorldGenerator generator) {
		super(generator);

		oddsOfParks = Odds.oddsUnlikely;
		oddsOfIsolatedLots = Odds.oddsPrettyUnlikely;
		oddsOfIdenticalBuildingHeights = Odds.oddsAlwaysGoingToHappen;
		oddsOfSimilarBuildingHeights = Odds.oddsExtremelyLikely;
		oddsOfSimilarBuildingRounding = Odds.oddsNeverGoingToHappen;
		oddsOfUnfinishedBuildings = Odds.oddsNeverGoingToHappen;
		oddsOfOnlyUnfinishedBasements = Odds.oddsNeverGoingToHappen;
		//oddsOfMissingRoad = oddsNeverGoingToHappen;
		oddsOfRoundAbouts = Odds.oddsUnlikely;
		 
		oddsOfStairWallMaterialIsWallMaterial = Odds.oddsExtremelyLikely;
		oddsOfBuildingWallInset = Odds.oddsExtremelyLikely;
		oddsOfFlatWalledBuildings = Odds.oddsExtremelyLikely;
		oddsOfSimilarInsetBuildings = Odds.oddsExtremelyLikely;
		rangeOfWallInset = 2;
		
		setSchematicFamily(SchematicFamily.INDUSTRIAL);

		maximumFloorsAbove = 2;
		maximumFloorsBelow = 1;
	}
	
	@Override
	protected PlatLot getPark(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ, int waterDepth) {
		if (odds.playOdds(Odds.oddsLikely))
			return new StorageLot(platmap, chunkX, chunkZ);
		else
			return new FactoryBuildingLot(platmap, chunkX, chunkZ);
	}
	
	@Override
	protected PlatLot getBuilding(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		if (odds.playOdds(Odds.oddsSomewhatLikely))
			return new WarehouseBuildingLot(platmap, chunkX, chunkZ);
		else
			return new FactoryBuildingLot(platmap, chunkX, chunkZ);
	}
}
