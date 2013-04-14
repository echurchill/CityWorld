package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Buildings.WarehouseLot;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.SchematicFamily;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.StorageLot;
import me.daddychurchill.CityWorld.Support.Odds;

public class IndustrialContext extends UrbanContext {

	public IndustrialContext(WorldGenerator generator) {
		super(generator);
	}
	
	@Override
	protected void initialize() {
		super.initialize();

		oddsOfParks = oddsUnlikely;
		oddsOfIsolatedLots = oddsVeryUnlikely;
		oddsOfIdenticalBuildingHeights = oddsAlwaysGoingToHappen;
		oddsOfSimilarBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingRounding = oddsNeverGoingToHappen;
		oddsOfUnfinishedBuildings = oddsNeverGoingToHappen;
		oddsOfOnlyUnfinishedBasements = oddsNeverGoingToHappen;
		//oddsOfMissingRoad = oddsNeverGoingToHappen;
		oddsOfRoundAbouts = oddsUnlikely;
		 
		oddsOfStairWallMaterialIsWallMaterial = oddsExtremelyLikely;
		oddsOfBuildingWallInset = oddsExtremelyLikely;
		oddsOfFlatWalledBuildings = oddsExtremelyLikely;
		oddsOfSimilarInsetBuildings = oddsExtremelyLikely;
		rangeOfWallInset = 2;
		
		schematicFamily = SchematicFamily.INDUSTRIAL;

		maximumFloorsAbove = 2;
		maximumFloorsBelow = 1;
	}
	
	private final static double oddsOfStorageLot = DataContext.oddsVeryLikely;
	private final static double oddsOfWarehouse = DataContext.oddsVeryLikely;
	
	@Override
	protected PlatLot getPark(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		if (odds.playOdds(oddsOfStorageLot))
			return new StorageLot(platmap, chunkX, chunkZ);
		else
			return super.getPark(generator, platmap, odds, chunkX, chunkZ);
	}
	
	@Override
	protected PlatLot getBuilding(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ) {
		if (odds.playOdds(oddsOfWarehouse))
			return new WarehouseLot(platmap, chunkX, chunkZ);
		else
			return super.getBuilding(generator, platmap, odds, chunkX, chunkZ);
	}
}
