package me.daddychurchill.CityWorld.Context;

import me.daddychurchill.CityWorld.WorldGenerator;
import me.daddychurchill.CityWorld.Maps.PlatMap;
import me.daddychurchill.CityWorld.Plats.PlatLot;

public abstract class UncivilizedContext extends DataContext {

	public UncivilizedContext(WorldGenerator generator) {
		super(generator);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void initialize() {
		oddsOfIsolatedLots = oddsNeverGoingToHappen; 
		oddsOfIsolatedConstructs = oddsNeverGoingToHappen;
		
		oddsOfParks = oddsNeverGoingToHappen; 
		
		oddsOfIdenticalBuildingHeights = oddsNeverGoingToHappen; 
		oddsOfSimilarBuildingHeights = oddsNeverGoingToHappen; 
		oddsOfSimilarBuildingRounding = oddsNeverGoingToHappen; 
		oddsOfStairWallMaterialIsWallMaterial = oddsNeverGoingToHappen; 
		
		oddsOfUnfinishedBuildings = oddsNeverGoingToHappen; 
		oddsOfOnlyUnfinishedBasements = oddsNeverGoingToHappen; 
		oddsOfCranes = oddsNeverGoingToHappen; 
		
		oddsOfBuildingWallInset = oddsNeverGoingToHappen; 
		oddsOfSimilarInsetBuildings = oddsNeverGoingToHappen; 
		oddsOfFlatWalledBuildings = oddsNeverGoingToHappen; 
		
		//oddsOfMissingRoad = oddsNeverGoingToHappen; 
		oddsOfRoundAbouts = oddsNeverGoingToHappen; 
		
		oddsOfMissingArt = oddsNeverGoingToHappen; 
		oddsOfNaturalArt = oddsNeverGoingToHappen; 
	}
	
	@Override
	public void validateMap(WorldGenerator generator, PlatMap platmap) {
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
