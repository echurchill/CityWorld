package me.daddychurchill.CityWorld.Context;

import java.util.Random;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Clipboard.PasteProvider.AreaTypes;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.BankBuildingLot;
import me.daddychurchill.CityWorld.Plats.PlatLot;

public class MidriseContext extends UrbanContext {

	public MidriseContext(WorldGenerator generator, PlatMap platmap) {
		super(generator, platmap);
		Random platmapRandom = platmap.getRandomGenerator();

		oddsOfParks = oddsUnlikely;
		oddsOfIsolatedLots = oddsLikely;
		oddsOfIdenticalBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingHeights = oddsExtremelyLikely;
		oddsOfSimilarBuildingRounding = oddsExtremelyLikely;
		oddsOfUnfinishedBuildings = oddsVeryUnlikely;
		oddsOfOnlyUnfinishedBasements = oddsNeverGoingToHappen;
		oddsOfMissingRoad = oddsLikely;
		oddsOfRoundAbouts = oddsLikely;
		 
		oddsOfStairWallMaterialIsWallMaterial = oddsExtremelyLikely;
		oddsOfBuildingWallInset = oddsExtremelyLikely;
		oddsOfFlatWalledBuildings = oddsExtremelyLikely;
		oddsOfSimilarInsetBuildings = oddsExtremelyLikely;
		rangeOfWallInset = 2;
		
		areaType = AreaTypes.MIDRISE;
		
		setFloorRange(platmapRandom, 7, 3);
		
	}

	private final static double oddsOfBank = 0.99;
	
	@Override
	protected PlatLot getFinishedBuilding(WorldGenerator generator, PlatMap platmap, Random random, int chunkX, int chunkZ) {
		if (random.nextDouble() < oddsOfBank)
			return new BankBuildingLot(platmap, chunkX, chunkZ);
		else
			return super.getFinishedBuilding(generator, platmap, random, chunkX, chunkZ);
	}
}
