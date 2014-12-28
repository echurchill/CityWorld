package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.CityWorldGenerator;
import me.daddychurchill.CityWorld.Plats.PlatLot;
import me.daddychurchill.CityWorld.Support.Odds;
import me.daddychurchill.CityWorld.Support.PlatMap;

public abstract class UncivilizedContext extends DataContext {

	public UncivilizedContext(CityWorldGenerator generator) {
		super(generator);

		oddsOfIsolatedLots = Odds.oddsNeverGoingToHappen; 
		oddsOfIsolatedConstructs = Odds.oddsSomewhatLikely;
		
		oddsOfParks = Odds.oddsNeverGoingToHappen; 
		
		oddsOfIdenticalBuildingHeights = Odds.oddsNeverGoingToHappen; 
		oddsOfSimilarBuildingHeights = Odds.oddsNeverGoingToHappen; 
		oddsOfSimilarBuildingRounding = Odds.oddsNeverGoingToHappen; 
		oddsOfStairWallMaterialIsWallMaterial = Odds.oddsNeverGoingToHappen; 
		
		oddsOfUnfinishedBuildings = Odds.oddsNeverGoingToHappen; 
		oddsOfOnlyUnfinishedBasements = Odds.oddsNeverGoingToHappen; 
		oddsOfCranes = Odds.oddsNeverGoingToHappen; 
		
		oddsOfBuildingWallInset = Odds.oddsNeverGoingToHappen; 
		oddsOfSimilarInsetBuildings = Odds.oddsNeverGoingToHappen; 
		oddsOfFlatWalledBuildings = Odds.oddsNeverGoingToHappen; 
		
		//oddsOfMissingRoad = oddsNeverGoingToHappen; 
		oddsOfRoundAbouts = Odds.oddsNeverGoingToHappen; 
		
		oddsOfArt = Odds.oddsNeverGoingToHappen; 
		oddsOfNaturalArt = Odds.oddsNeverGoingToHappen; 
	}

	@Override
	public void validateMap(CityWorldGenerator generator, PlatMap platmap) {
		for (int x = 0; x < PlatMap.Width; x++) {
			for (int z = 0; z < PlatMap.Width; z++) {
				PlatLot current = platmap.getLot(x, z);
				
				// if we are trulyIsolated and one of our neighbors are as well then recycle the lot
				if (current != null && 
					current.trulyIsolated && !platmap.isTrulyIsolatedLot(x, z))
					platmap.recycleLot(x, z);
			}
		}
	}
	
}
