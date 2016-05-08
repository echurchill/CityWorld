package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Plats.PlatLot.LotStyle;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public abstract class CivilizedContext extends DataContext {

	public CivilizedContext(CityWorldGenerator generator) {
		super(generator);

		oddsOfIsolatedLots = Odds.oddsExtremelyLikely; 
		oddsOfIsolatedConstructs = Odds.oddsSomewhatLikely;
		
		oddsOfParks = Odds.oddsVeryLikely; 
		
		oddsOfIdenticalBuildingHeights = Odds.oddsExtremelyLikely; 
		oddsOfSimilarBuildingHeights = Odds.oddsExtremelyLikely; 
		oddsOfSimilarBuildingRounding = Odds.oddsExtremelyLikely; 
		oddsOfStairWallMaterialIsWallMaterial = Odds.oddsExtremelyLikely; 
		
		oddsOfUnfinishedBuildings = Odds.oddsLikely; 
		oddsOfOnlyUnfinishedBasements = Odds.oddsVeryLikely; 
		oddsOfCranes = Odds.oddsVeryLikely; 
		
		oddsOfBuildingWallInset = Odds.oddsExtremelyLikely; 
		oddsOfSimilarInsetBuildings = Odds.oddsExtremelyLikely; 
		oddsOfFlatWalledBuildings = Odds.oddsExtremelyLikely; 
		
		//TODO oddsOfMissingRoad is current not used... I need to fix this
		//oddsOfMissingRoad = oddsLikely; 
		oddsOfRoundAbouts = Odds.oddsSomewhatLikely; 
		
		oddsOfArt = Odds.oddsExtremelyLikely; 
		oddsOfNaturalArt = Odds.oddsExtremelyLikely; 
	}

	protected abstract PlatLot getBackfillLot(CityWorldGenerator generator, PlatMap platmap, Odds odds, int chunkX, int chunkZ);
	
	@Override
	public void validateMap(CityWorldGenerator generator, PlatMap platmap) {
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
