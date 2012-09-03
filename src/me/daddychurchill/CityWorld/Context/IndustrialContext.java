package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.StorageLot;
import me.daddychurchill.CityWorld.Plats.WarehouseLot;

public class IndustrialContext extends UrbanContext {

	public IndustrialContext(WorldGenerator generator, PlatMap platmap) {
		super(generator, platmap);
		Random platmapRandom = platmap.getRandomGenerator();
		
		oddsOfParks = oddsUnlikely;
		oddsOfIsolatedLots = oddsVeryUnlikely;
		oddsOfIdenticalBuildingHeights = oddsAlwaysGoingToHappen;
		oddsOfSimilarBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingRounding = oddsNeverGoingToHappen;
		oddsOfUnfinishedBuildings = oddsNeverGoingToHappen;
		oddsOfOnlyUnfinishedBasements = oddsNeverGoingToHappen;
		oddsOfMissingRoad = oddsNeverGoingToHappen;
		oddsOfRoundAbouts = oddsUnlikely;
		 
		oddsOfStairWallMaterialIsWallMaterial = oddsExtremelyLikely;
		oddsOfBuildingWallInset = oddsExtremelyLikely;
		oddsOfFlatWalledBuildings = oddsExtremelyLikely;
		oddsOfSimilarInsetBuildings = oddsExtremelyLikely;
		rangeOfWallInset = 2;

		setFloorRange(platmapRandom, 2, 1);
	}
	
	private final static double oddsOfPowerStation = 0.90;
	private final static double oddsOfWarehouse = 0.75;
	
	@Override
	protected PlatLot getPark(WorldGenerator generator, PlatMap platmap, Random random, int chunkX, int chunkZ) {
		if (random.nextDouble() < oddsOfPowerStation)
			return new StorageLot(platmap, chunkX, chunkZ);
		else
			return super.getPark(generator, platmap, random, chunkX, chunkZ);
	}
	
	@Override
	protected PlatLot getFinishedBuilding(WorldGenerator generator, PlatMap platmap, Random random, int chunkX, int chunkZ) {
		if (random.nextDouble() < oddsOfWarehouse)
			return new WarehouseLot(platmap, chunkX, chunkZ);
		else
			return super.getFinishedBuilding(generator, platmap, random, chunkX, chunkZ);
	}
}
