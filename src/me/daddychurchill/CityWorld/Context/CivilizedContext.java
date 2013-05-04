package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public abstract class CivilizedContext extends DataContext {

	public CivilizedContext(WorldGenerator generator) {
		super(generator);
	}
	
	@Override
	protected void initialize() {
		oddsOfIsolatedLots = oddsExtremelyLikely; 
		oddsOfIsolatedConstructs = oddsSomewhatLikely;
		
		oddsOfParks = oddsVeryLikely; 
		
		oddsOfIdenticalBuildingHeights = oddsExtremelyLikely; 
		oddsOfSimilarBuildingHeights = oddsExtremelyLikely; 
		oddsOfSimilarBuildingRounding = oddsExtremelyLikely; 
		oddsOfStairWallMaterialIsWallMaterial = oddsExtremelyLikely; 
		
		oddsOfUnfinishedBuildings = oddsLikely; 
		oddsOfOnlyUnfinishedBasements = oddsVeryLikely; 
		oddsOfCranes = oddsVeryLikely; 
		
		oddsOfBuildingWallInset = oddsExtremelyLikely; 
		oddsOfSimilarInsetBuildings = oddsExtremelyLikely; 
		oddsOfFlatWalledBuildings = oddsExtremelyLikely; 
		
		//TODO oddsOfMissingRoad is current not used... I need to fix this
		//oddsOfMissingRoad = oddsLikely; 
		oddsOfRoundAbouts = oddsLikely; 
		
		oddsOfMissingArt = oddsUnlikely; 
		oddsOfNaturalArt = oddsExtremelyLikely; 
	}

	protected abstract PlatLot getBackfillLot(WorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ);
	
	@Override
	public void validateMap(WorldGenerator generator, PlatMap platmap) {
		Odds platmapOdds = platmap.getOddsGenerator();
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				
				// if we are trulyIsolated and one of our neighbors are as well then recycle the lot
				if (current != null && 
					current.style == LotStyle.STRUCTURE && 
					current.trulyIsolated && !platmap.isTrulyIsolatedLot(x, z)) {
					
					// replace it then
					current = getBackfillLot(generator, platmap, platmapOdds, platmap.originX + x, platmap.originZ + z);
					platmap.setLot(x, z, current);
				}
			}
		}
	}
}
